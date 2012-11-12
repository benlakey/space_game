package org.seattlegamer.spacegame.components;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.sprites.Sprite;

public class GameComponent extends ComponentBase {

	private final Bus bus;
	private final List<Player> players;
	private final GameMap gameMap;
	private final HeadsUpDisplay headsUpDisplay;
	private Point mouseDragLast;
	
	public GameComponent(Bus bus, List<Player> players, GameMap gameMap) {
		this.bus = bus;
		this.players = players;
		this.gameMap = gameMap;
		this.headsUpDisplay = new HeadsUpDisplay();
		this.initializeHUD();
	}
	
	private void initializeHUD() {
		for(Player player : players) {
			this.headsUpDisplay.updatePlayerHealth(player.getName(), player.getHealth());
		}
		this.subComponents.add(this.headsUpDisplay);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ComponentTransition(new MainMenu(this.bus)));
		}

		super.keyPressed(e);
		
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
		
		for(Sprite sprite : this.gameMap.getSprites()) {
			Point currentPosition = sprite.getPosition();
			currentPosition.x += distanceDraggedX;
			currentPosition.y += distanceDraggedY;
		}
		
		this.mouseDragLast = e.getPoint();
		
		super.mouseDragged(e);

	}

	@Override
	public void render(Graphics2D graphics) {
		
		for(Renderable renderable : this.gameMap.getSprites()) {
			renderable.render(graphics);
		}
		
		this.headsUpDisplay.render(graphics);
		
	}

}
