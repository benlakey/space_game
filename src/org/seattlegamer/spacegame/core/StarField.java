package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

public class StarField extends Component {
	
	private static final int MAX_STAR_SIZE = 4;
	private static final int NUM_STARS = 300;

	private final DisplayMode displayMode;
	private final Collection<Star> stars;

	public StarField(Bus bus, UUID entityId, DisplayMode displayMode) {
		super(bus, entityId);
		this.displayMode = displayMode;
		this.stars = new LinkedList<Star>();
		this.generateStarField(NUM_STARS);
	}
	
	private void generateStarField(int numStars) {
		
		Random random = new Random();
		
		for(int i = 0; i < numStars; i++) {
			
			int x = random.nextInt(this.displayMode.getWidth());
			int y = random.nextInt(this.displayMode.getHeight());
			int size = random.nextInt(MAX_STAR_SIZE);
			
			Star star = new Star(new Point(x, y), size);
			this.stars.add(star);

		}
		
	}
	
	@Override
	public void render(Graphics2D graphics) {
		
		graphics.setColor(Color.WHITE);

		for(Star star : this.stars) {
			graphics.drawOval(star.position.x, star.position.y, star.size, star.size);
		}

	}
	
	private class Star {
		public Point position;
		public int size;
		public Star(Point position, int size) {
			this.position = position;
			this.size = size;
		}
	}
	
}
