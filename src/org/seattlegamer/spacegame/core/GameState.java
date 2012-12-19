package org.seattlegamer.spacegame.core;

import java.awt.DisplayMode;
import java.io.IOException;
import java.util.Random;

import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class GameState extends State {

	private final DisplayMode displayMode;

	//TODO: drop displayMode here when we have a map that isn't just the current pixel space on screen (a viewport)
	public GameState(Bus bus, ResourceCache resourceCache, DisplayMode displayMode) {
		super(bus, resourceCache);
		this.displayMode = displayMode;
	}

	public void loadNewGame(NewGameManifest manifest) throws IOException {

		int playerNumber = 1;
		
		PlayerCreator playerCreator = new PlayerCreator(this.bus, this.resourceCache, this.displayMode);
		
		for(String player : manifest.getPlayers()) {
			playerCreator.createPlayer(playerNumber, player);
			playerNumber++;
		}
		
		//TODO: this stuff is just here for testing.
		Random random = new Random();

		AnimationCreator animationCreator = new AnimationCreator(this.bus, resourceCache);
		
		for(int i = 0; i < 20; i++) {
			
			Iterable<Component> animationComponents = animationCreator.createAnimation(
					"assets/explosion.png", random.nextInt(this.displayMode.getWidth()), random.nextInt(this.displayMode.getHeight()));
			
			for(Component component : animationComponents) {
				//this.bus.register(component, component.getEntityId());
				this.bus.broadcast(new ComponentAddition(component));
			}
			
		}
		
		this.bus.broadcast(new AnimationStart());

	}

}
