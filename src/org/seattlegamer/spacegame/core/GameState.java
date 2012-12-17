package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.messages.PlayerStatsReport;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class GameState implements State {
	
	private static GameState currentGame;
	
	public static GameState getCurrentGame() {
		return currentGame; //can be null
	}

	//TODO: move to a component that handles player health, when such a component exists (will be the component that takes damage and reports health to HUD)
	private static final int STARTING_HEALTH = 100;

	private final NewGameManifest manifest;
	private final Collection<Component> components;
	private boolean loaded;

	public GameState(NewGameManifest manifest) {
		this.manifest = manifest;
		this.components = new LinkedList<Component>();
	}

	@Override
	public void load(ComponentBus bus, ResourceCache resourceCache, StateManager stateManager) throws IOException {
		
		if(this.loaded) {
			return;
		}
		
		int playerNumber = 1;
		
		for(String player : manifest.getPlayers()) {
			this.addPlayer(bus, resourceCache, stateManager, playerNumber, player);
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
		
		currentGame = this;
		
		this.loaded = true;
		
	}

	private void addPlayer(
			ComponentBus bus, 
			ResourceCache resourceCache, 
			StateManager stateManager, 
			int playerNumber, 
			String name) throws IOException {

		UUID playerEntityId = UUID.randomUUID();
		UUID hudEntityId = UUID.randomUUID();
		
		Image player = TestImageCreator.buildPlayer(Color.BLUE);
		Image scaledPlayer = GraphicsUtils.getScaledImage(player, GameSettings.current().getScale());

		int maxX = GameSettings.current().getDisplayModeWidth();
		int maxY = GameSettings.current().getDisplayModeHeight();
		
		Point position = new Point(new Random().nextInt(maxX), new Random().nextInt(maxY));
		
		this.components.add(new HeadsUpDisplayEntryRenderer(bus, hudEntityId, playerEntityId, playerNumber));
		this.components.add(new Sprite(bus, playerEntityId, scaledPlayer));
		this.components.add(new Physics(bus, playerEntityId, position, 0, scaledPlayer.getWidth(null), scaledPlayer.getHeight(null)));
		this.components.add(new ProjectileLauncher(bus, playerEntityId, resourceCache, stateManager));
		
		//TODO: hack for testing. input will be assigned turn-based.
		if(playerNumber == 1) {
			this.components.add(new PlayerInput(bus, playerEntityId, stateManager));
		}
		
		bus.send(new PlayerStatsReport(playerEntityId, name, STARTING_HEALTH), hudEntityId);

	}

	//TODO: just here for testing
	private void addExplosion(ComponentBus bus, ResourceCache resourceCache, int x, int y) throws IOException {

		final UUID explosionEntityId = UUID.randomUUID();
		
		Image explosionImage = resourceCache.getImage("assets/explosion.png");
		Image scaledExplosionImage = GraphicsUtils.getScaledImage(explosionImage, GameSettings.current().getScale());

		AnimationLoader animationLoader = new AnimationLoader(scaledExplosionImage);
		Image[] frames = animationLoader.getFrames();

		this.components.add(new Animation(bus, explosionEntityId, frames, new Point(x, y)));

		bus.send(new AnimationStart(), explosionEntityId);

	}

	@Override
	public void addComponent(Component component) {
		this.components.add(component);
	}
	
	@Override
	public void removeComponent(Component component) {
		this.components.remove(component);
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
