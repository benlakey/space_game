package org.seattlegamer.spacegame.activities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.HeadsUpDisplay;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.communication.ActivityTransition;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.sprites.Sprite;

public class GameActivity extends Activity {

	private final Bus bus;
	private List<Player> players;
	//private Player currentPlayer;
	private GameMap gameMap;
	private HeadsUpDisplay headsUpDisplay;
	private Point mouseDragLast;
	
	public GameActivity(Bus bus, List<Player> players, GameMap gameMap) {
		this.bus = bus;
		this.players = players;
		//this.currentPlayer = players.get(0);
		this.gameMap = gameMap;
		
		this.initializeHUD();
	}
	
	private void initializeHUD() {
		this.headsUpDisplay = new HeadsUpDisplay();
		for(Player player : players) {
			this.headsUpDisplay.update(player.getName(), player.getHealth());
		}
	}
	
	@Override
	public void update(long elapsedTimeMillis) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ActivityTransition(new MainMenuActivity(this.bus)));
		}
		
		//TODO: if is game controlling character press, controls this.players[this.currentControllingPlayerIndex]
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseDragLast = e.getPoint();
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

	}

	@Override
	public void render(Graphics2D graphics) {
		
		for(Renderable renderable : this.gameMap.getSprites()) {
			renderable.render(graphics);
		}
		
		this.headsUpDisplay.render(graphics);
		
	}

}
