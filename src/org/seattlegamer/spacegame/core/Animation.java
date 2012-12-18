package org.seattlegamer.spacegame.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.messages.PositionChange;

public class Animation extends Component {

	private static final int SAMPLE_DELAY_MILLIS = 70;

	private Image[] frames;
	private final AffineTransform transform;
	private int millisSinceLastSample;
	private int currentFrameIndex;

	public Animation(ComponentBus bus, UUID entityId, Image[] frames, Point position) {
		super(bus, entityId);
		this.frames = frames;
		this.transform = new AffineTransform();
		this.transform.setToTranslation(position.x, position.y);
		this.transform.rotate(0, this.frames[0].getWidth(null) / 2, this.frames[0].getHeight(null) / 2);
		this.currentFrameIndex = -1;
	}

	@Subscription
	public void updatePosition(PositionChange change) {

		double translateX = this.transform.getTranslateX();
		double translateY = this.transform.getTranslateY();
		
		translateX += change.getPositionDiffX();
		translateY += change.getPositionDiffY();

		this.transform.setToTranslation(translateX, translateY);

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
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
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

		graphics.drawImage(image, this.transform, null);

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
