package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.UUID;


public class Animation extends Component {
	
	private static final int FRAME_COUNT = 8;
	private static final int SAMPLE_DELAY_MILLIS = 70;
	
	private final Handler<AnimationInitiation> animationInitiationHandler;

	private Image[] frames;
	private int millisSinceLastSample;
	private int currentFrameIndex;

	public Animation(Bus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.frames = new Image[FRAME_COUNT];
		this.storeFrames(image);
		this.currentFrameIndex = -1;
		this.animationInitiationHandler = this.getAnimationInitiationHandler();
	}
	
	@Override
	public void registerHandlers() {
		this.bus.register(AnimationInitiation.class, this.animationInitiationHandler);
	}
	
	@Override
	public void deregisterHandlers() {
		this.bus.deregister(AnimationInitiation.class, this.animationInitiationHandler);
	}
	
	private Handler<AnimationInitiation> getAnimationInitiationHandler() {
		return new Handler<AnimationInitiation>() {
			
			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
			}
			
			@Override
			public void handle(AnimationInitiation message) {
				initiateAnimationCycle();
			}
			
		};
	}
	
	private void storeFrames(Image image) {

		int frameWidth = image.getWidth(null) / FRAME_COUNT;
		int frameHeight = image.getHeight(null);

		for(int i = 0; i < FRAME_COUNT; i++) {
			this.storeFrame(image, i, frameWidth, frameHeight);
		}

	}
	
	private void storeFrame(Image original, int index, int frameWidth, int frameHeight) {
		
		BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D graphics = null;
        try {
			graphics = frame.createGraphics();
	        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics.drawImage(original, 0, 0, frameWidth, frameHeight, frameWidth * index, 0, frameWidth * index + frameWidth, frameHeight, null);
        } finally {
        	graphics.dispose();
        }
        
        this.frames[index] = frame;
		
	}
	
	@Override
	public void update(Input input, long elapsedMillis) {
		this.millisSinceLastSample += elapsedMillis;
	}

	@Override
	public void render(Graphics2D graphics) {
		
		if(!this.isCurrentlyAnimating()) {
			return;
		}

		Image image = this.frames[this.currentFrameIndex];
		
		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);
		if(currentPosition == null) {
			currentPosition = new Point();
		}
		graphics.drawImage(image, currentPosition.x, currentPosition.y, null);

		if(this.isAtEndOfAnimation()) {
			this.completeAnimationCycle();
		} else {
			if(this.millisSinceLastSample > SAMPLE_DELAY_MILLIS) {
				this.currentFrameIndex++;
				this.millisSinceLastSample = 0;
			}
		}

	}
	
	private void initiateAnimationCycle() {
		this.currentFrameIndex = 0;
	}
	
	private boolean isCurrentlyAnimating() {
		return this.currentFrameIndex != -1;
	}
	
	private boolean isAtEndOfAnimation() {
		return this.currentFrameIndex == FRAME_COUNT - 1;
	}

	private void completeAnimationCycle() {
		this.currentFrameIndex = -1;
	}
	
	private Point getCurrentPosition(Rectangle screenSize) {
		PositionQuery query = new PositionQuery(screenSize);
		this.bus.send(query, this.entityId);		
		return query.getReply();
	}

}
