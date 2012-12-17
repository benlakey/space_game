package org.seattlegamer.spacegame.core;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class GameState implements State {
	
	private static GameState currentGame;
	
	public static GameState getCurrentGame() {
		return currentGame; //can be null
	}

	private final ComponentBus bus;
	private final NewGameManifest manifest;
	private final Collection<Component> components;
	private boolean loaded;

	public GameState(ComponentBus bus, NewGameManifest manifest) {
		this.bus = bus;
		this.manifest = manifest;
		this.components = new LinkedList<Component>();
	}

	@Override
	public void load(ResourceCache resourceCache, StateManager stateManager, GameSettings settings) throws IOException {
		
		if(this.loaded) {
			return;
		}
		
		this.addPlayers(resourceCache, stateManager, settings);
		this.addTestExplosions(resourceCache, stateManager, settings);

		currentGame = this;
		
		this.loaded = true;
		
	}
	
	private void addPlayers(ResourceCache resourceCache, StateManager stateManager, GameSettings settings) throws IOException {
		
		int playerNumber = 1;
		
		PlayerCreator playerCreator = new PlayerCreator(this.bus, resourceCache, stateManager, settings);
		
		for(String player : manifest.getPlayers()) {
			Iterable<Component> playerComponents = playerCreator.createPlayer(playerNumber, player);
			for(Component playerComponent : playerComponents) {
				this.components.add(playerComponent);
			}
			playerNumber++;
		}
		
	}
	
	//TODO: this stuff is just here for testing.
	private void addTestExplosions(ResourceCache resourceCache, StateManager stateManager, GameSettings settings) throws IOException {

		int maxX = settings.getDisplayModeWidth();
		int maxY = settings.getDisplayModeHeight();
		
		Random random = new Random();

		AnimationCreator animationCreator = new AnimationCreator(this.bus, resourceCache, settings);
		
		for(int i = 0; i < 20; i++) {
			
			Iterable<Component> animationComponents = animationCreator.createAnimation(
					"assets/explosion.png", random.nextInt(maxX), random.nextInt(maxY));
			
			for(Component component : animationComponents) {
				this.components.add(component);
			}
			
		}
		
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
