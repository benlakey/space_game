package org.seattlegamer.spacegame.game;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.seattlegamer.spacegame.Animation;
import org.seattlegamer.spacegame.AnimationInitiation;
import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;
import org.seattlegamer.spacegame.PositionInitialization;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntry;

public class GameState extends State {

	private final NewGameManifest gameManifest;
	
	public GameState(Bus bus, NewGameManifest gameManifest) {
		super(bus);
		this.gameManifest = gameManifest;
	}

	public void load(ResourceCache resourceCache) throws IOException {
		
		this.addPlayer(bus, resourceCache, 1, this.gameManifest.getPlayer1Name());
		this.addPlayer(bus, resourceCache, 2, this.gameManifest.getPlayer2Name());
		
		this.addExplosion(bus, resourceCache, new Random().nextInt(800), new Random().nextInt(600));
		this.addExplosion(bus, resourceCache, new Random().nextInt(800), new Random().nextInt(600));
		this.addExplosion(bus, resourceCache, new Random().nextInt(800), new Random().nextInt(600));
		this.addExplosion(bus, resourceCache, new Random().nextInt(800), new Random().nextInt(600));
		this.addExplosion(bus, resourceCache, new Random().nextInt(800), new Random().nextInt(600));
		
	}
	
	private void addPlayer(final Bus bus, ResourceCache resourceCache, int playerNumber, String name) throws IOException {
		
		final UUID playerId = UUID.randomUUID();
		UUID hudId = UUID.randomUUID();

		this.components.add(new HeadsUpDisplayEntry(bus, hudId, playerId, playerNumber, name));
		this.components.add(new Position(bus, hudId));

		this.components.add(new PlayerStatus(bus, playerId));
		this.components.add(new Position(bus, playerId));
		this.components.add(new Sprite(bus, playerId, resourceCache.getImage("assets/mars.png")));
		this.components.add(new PlayerInput(bus, playerId));

		this.activationCommands.add(new ActivationCommand() {
			@Override
			public void execute() {
				bus.send(new PositionInitialization(new Point(0, 0), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE), playerId);
			}
		});

	}
	
	private void addExplosion(final Bus bus, ResourceCache resourceCache, final int x, final int y) throws IOException {
		
		final UUID explosionId = UUID.randomUUID();

		this.components.add(new Position(bus, explosionId));
		this.components.add(new Animation(bus, explosionId, resourceCache.getImage("assets/explosion.png")));

		this.activationCommands.add(new ActivationCommand() {
			@Override
			public void execute() {
				bus.send(new PositionInitialization(new Point(x, y), HorizontalAlignment.LEFT, VerticalAlignment.TOP), explosionId);
			}
		});
		
		//TODO: this is just here for testing out explosions
		this.activationCommands.add(new ActivationCommand() {
			@Override
			public void execute() {
				bus.send(new AnimationInitiation(), explosionId);
			}
		});
	}

}
