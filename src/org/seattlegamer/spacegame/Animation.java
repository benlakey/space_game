package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.UUID;

public class Animation extends Component {

	private static final int SAMPLE_DELAY_MILLIS = 70;

	private Image[] frames;
	private Point position;
	private int millisSinceLastSample;
	private int currentFrameIndex;

	public Animation(ComponentBus bus, UUID entityId, Image[] frames) {
		super(bus, entityId);
		this.frames = frames;
		this.position = new Point();
		this.currentFrameIndex = -1;
	}
	
	@Subscription
	public void setPosition(SpritePositioning positioning) {
		this.position.x = positioning.getPosition().x;
		this.position.y = positioning.getPosition().y;
	}

	@Subscription
	public void startPlaying(AnimationStart start) {
		this.currentFrameIndex = 0;
	}
	
	private boolean isPlaying() {
		return this.currentFrameIndex != -1;
	}

	private boolean isAtEndOfAnimation() {
		return this.currentFrameIndex == this.frames.length - 1;
	}

	private void completeAnimationCycle() {
		this.currentFrameIndex = -1;
	}
	
	@Override
	public void update(Input input, long elapsedMillis) {
		if(this.isPlaying()) {
			this.millisSinceLastSample += elapsedMillis;
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		
		if(!this.isPlaying()) {
			return;
		}

		Image image = this.frames[this.currentFrameIndex];

		graphics.drawImage(image, this.position.x, this.position.y, null);

		if(this.isAtEndOfAnimation()) {
			this.completeAnimationCycle();
			return;
		}
		
		if(this.millisSinceLastSample > SAMPLE_DELAY_MILLIS) {
			this.currentFrameIndex++;
			this.millisSinceLastSample = 0;
		}

	}
	


}
