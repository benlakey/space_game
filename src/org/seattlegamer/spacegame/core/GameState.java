package org.seattlegamer.spacegame.core;

import java.awt.DisplayMode;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class GameState extends State {
	
	private static final Logger logger = Logger.getLogger(GameState.class);

	private final DisplayMode displayMode;
	private NewGameManifest manifest;

	//TODO: drop displayMode here when we have a map that isn't just the current pixel space on screen (a viewport)
	public GameState(Bus bus, ResourceCache resourceCache, DisplayMode displayMode) {
		super(bus, resourceCache);
		this.displayMode = displayMode;
	}
	
	public void loadNewGame(NewGameManifest manifest) {
		this.manifest = manifest;
	}
	
	@Override
	public void onActivate() {

		super.onActivate();
		
		if(this.manifest == null){
			return;
		}
		
		try {
			this.clear();
			this.loadStarField();
			this.loadPlayers();
			this.loadTestExplosions();
			this.manifest = null;
		} catch (IOException e) {
			logger.error("Unable to load game state!", e);
			throw new RuntimeException(e);
		}

	}
	
	private void loadStarField() {
		StarField starField = new StarField(this.bus, UUID.randomUUID(), this.displayMode);
		this.bus.broadcast(new ComponentAddition(starField));
	}

	private void loadPlayers() throws IOException {

		int playerNumber = 1;
		
		PlayerCreator playerCreator = new PlayerCreator(this.bus, this.resourceCache, this.displayMode);
		
		for(String player : this.manifest.getPlayers()) {
			playerCreator.createPlayer(playerNumber, player);
			playerNumber++;
		}

	}
	
	private void loadTestExplosions() throws IOException {
		
		//TODO: this stuff is just here for testing.
		Random random = new Random();

		for(int i = 0; i < 20; i++) {
			
			Point position = new Point(
					random.nextInt(this.displayMode.getWidth()), 
					random.nextInt(this.displayMode.getHeight()));
			
			UUID explosionEntityId = UUID.randomUUID();
			Image explosionImage = resourceCache.getImage("assets/explosion.png");
			this.bus.broadcast(new ComponentAddition(new Animation(bus, explosionEntityId, explosionImage, position)));
			
		}
		
		this.bus.broadcast(new AnimationStart());
		
	}

}
