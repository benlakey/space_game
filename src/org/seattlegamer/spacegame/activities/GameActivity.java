package org.seattlegamer.spacegame.activities;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.communication.ActivityTransition;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.HealthReport;
import org.seattlegamer.spacegame.entities.GameMap;
import org.seattlegamer.spacegame.entities.Player;
import org.seattlegamer.spacegame.sprites.Sprite;

public class GameActivity extends Activity {

	private final Bus bus;
	@SuppressWarnings("unused")
	private List<Player> players;
	private Player currentPlayer;
	private GameMap gameMap;

	private Point mouseDragLast;
	
	public GameActivity(Bus bus, List<Player> players, GameMap gameMap) {
		this.bus = bus;
		this.players = players;
		this.currentPlayer = players.get(0);
		this.gameMap = gameMap;
	}
	
	@Override
	public void update(long elapsedTimeMillis) {
		//TODO: this is just an example. should only need to send this when health changes.
		this.bus.send(new HealthReport(this.currentPlayer));
	}
	
	@Override
	public Iterable<? extends Renderable> getRenderables() {
		return this.gameMap.getSprites();
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

}
