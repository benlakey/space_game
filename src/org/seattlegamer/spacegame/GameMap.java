package org.seattlegamer.spacegame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.seattlegamer.spacegame.components.ComponentBase;
import org.seattlegamer.spacegame.sprites.FilesystemBasedSpriteCache;
import org.seattlegamer.spacegame.sprites.Sprite;
import org.seattlegamer.spacegame.sprites.SpriteCache;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class GameMap extends ComponentBase {

	private final String name;
	private Point mouseDragLast;
	private SpaceBody player1SpaceBody;
	private SpaceBody player2SpaceBody;
	private Collection<SpaceBody> collidables;
	private Collection<SpaceBody> nonCollidables;
	
	private GameMap(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public SpaceBody getPlayer1SpaceBody() {
		return this.player1SpaceBody;
	}
	
	public SpaceBody getPlayer2SpaceBody() {
		return this.player2SpaceBody;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseDragLast = e.getPoint();
		super.mousePressed(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {

		int distanceDraggedX = e.getPoint().x - this.mouseDragLast.x;
		int distanceDraggedY = e.getPoint().y - this.mouseDragLast.y;
		
		this.updatePositions(distanceDraggedX, distanceDraggedY);

		this.mouseDragLast = e.getPoint();
		
		super.mouseDragged(e);

	}
	
	@Override
	public void render(Graphics2D graphics) {
		
		this.player1SpaceBody.getSprite().render(graphics);
		this.player2SpaceBody.getSprite().render(graphics);
		
		super.render(graphics);
		
	}
	
	private void updatePositions(int distanceX, int distanceY) {
		this.updatePosition(this.player1SpaceBody, distanceX, distanceY);
		this.updatePosition(this.player2SpaceBody, distanceX, distanceY);
		this.updatePositions(this.collidables, distanceX, distanceY);
		this.updatePositions(this.nonCollidables, distanceX, distanceY);
	}
	
	private void updatePositions(Iterable<SpaceBody> spaceBodies, int distanceX, int distanceY) {
		for(SpaceBody spaceBody : spaceBodies) {
			this.updatePosition(spaceBody, distanceX, distanceY);
		}
	}
	
	private void updatePosition(SpaceBody spaceBody, int distanceX, int distanceY) {
		Sprite sprite = spaceBody.getSprite();
		Point currentPosition = sprite.getPosition();
		currentPosition.x += distanceX;
		currentPosition.y += distanceY;
	}

	public static GameMap load(String mapResource) {
		//TODO: loaded from the file/serialized map, not hardcoded.
		GameMap gameMap = new GameMap("Test Map Name");
		gameMap.player1SpaceBody = loadHardcodedPlanet();
		gameMap.player2SpaceBody = loadHardcodedPlanet();
		gameMap.collidables = new LinkedList<SpaceBody>();
		gameMap.nonCollidables = new LinkedList<SpaceBody>();
		return gameMap;
	}

	private static SpaceBody loadHardcodedPlanet() {
		SpriteCache cache = FilesystemBasedSpriteCache.get();
		Sprite sprite = cache.getSprite(PlanetType.MARS.getAssetPath());
		Point randomPosition = getRandomPointOnScreen();
		sprite.setPosition(randomPosition);
		return new SpaceBody(sprite);
	}
	
	private static Point getRandomPointOnScreen() {
		Dimension currentScreenDimension = GraphicsUtils.getCurrentScreenDimension();
		Random random = new Random();
		int x = random.nextInt(currentScreenDimension.width);
		int y = random.nextInt(currentScreenDimension.height);
		return new Point(x, y);
	}

}
