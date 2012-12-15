package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class GameState implements State {

	//TODO: move to a component that handles player health, when such a component exists.
	private static final int STARTING_HEALTH = 100;
	
	private static GameState currentGameState;
	
	public static GameState newGame(NewGameManifest manifest) {
		currentGameState = new GameState(manifest);
		return currentGameState;
	}
	
	public static GameState getCurrent() {
		return currentGameState;
	}
	
	private final NewGameManifest manifest;
	private final Collection<Component> components;
	private boolean loaded;

	private GameState(NewGameManifest manifest) {
		this.manifest = manifest;
		this.components = new LinkedList<Component>();
	}

	@Override
	public void load(ComponentBus bus, ResourceCache resourceCache) throws IOException {
		
		if(this.loaded) {
			return;
		}
		
		int playerNumber = 1;
		
		for(String player : manifest.getPlayers()) {
			this.addPlayer(bus, resourceCache, playerNumber, player);
			playerNumber++;
		}
		
		int maxX = GameSettings.current().getDisplayModeWidth();
		int maxY = GameSettings.current().getDisplayModeHeight();
		
		Random random = new Random();

		//TODO: this stuff is just here for testing.
		this.addExplosion(bus, resourceCache, random.nextInt(maxX), random.nextInt(maxY));
		this.addExplosion(bus, resourceCache, random.nextInt(maxX), random.nextInt(maxY));
		this.addExplosion(bus, resourceCache, random.nextInt(maxX), random.nextInt(maxY));
		this.addExplosion(bus, resourceCache, random.nextInt(maxX), random.nextInt(maxY));
		this.addExplosion(bus, resourceCache, random.nextInt(maxX), random.nextInt(maxY));
		
		this.loaded = true;
		
	}

	private void addPlayer(ComponentBus bus, ResourceCache resourceCache, int playerNumber, String name) throws IOException {

		UUID playerEntityId = UUID.randomUUID();
		UUID hudEntityId = UUID.randomUUID();
		
		Image player = TestImageCreator.buildPlayer(Color.BLUE);
		Image scaledPlayer = GraphicsUtils.getScaledImage(player, GameSettings.current().getScale());

		this.components.add(new HeadsUpDisplayEntryRenderer(bus, hudEntityId, playerEntityId, playerNumber));
		this.components.add(new Sprite(bus, playerEntityId, scaledPlayer));
		
		//TODO: hack for testing. input will be assigned turn-based.
		if(playerNumber == 1) {
			this.components.add(new PlayerInput(bus, playerEntityId));
		}
		
		bus.send(new PlayerStatsReport(playerEntityId, name, STARTING_HEALTH), hudEntityId);
		bus.send(new SpritePositioning(new Point(new Random().nextInt(800), new Random().nextInt(800))), playerEntityId);

	}

	private void addExplosion(ComponentBus bus, ResourceCache resourceCache, int x, int y) throws IOException {

		final UUID explosionEntityId = UUID.randomUUID();

		AnimationLoader animationLoader = new AnimationLoader(resourceCache.getImage("assets/explosion.png"));
		Image[] frames = animationLoader.getFrames();

		this.components.add(new Animation(bus, explosionEntityId, frames));

		bus.send(new SpritePositioning(new Point(x, y)), explosionEntityId);
		bus.send(new AnimationStart(), explosionEntityId);

	}

	@Override
	public void update(Input input, long elapsedMillis) {
		for(Component component : this.components) {
			component.update(input, elapsedMillis);
		}
	}

	@Override
	public void render(Renderer renderer) {
		renderer.render(components);
	}

}
