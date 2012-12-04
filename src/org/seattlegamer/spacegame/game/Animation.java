package org.seattlegamer.spacegame.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.PositionQuery;

public class Animation extends Component {
	
	private static final int FRAME_COUNT = 8;

	private Image[] frames;
	private int currentFrameIndex;
	private boolean renderedLastTime;
	
	public Animation(Bus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.storeFrames(image);
		this.currentFrameIndex = -1;
		this.bus.register(AnimationInitiation.class, this.getAnimationInitiationHandler());
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
		
		this.frames = new Image[FRAME_COUNT];
		
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
	public void render(Graphics2D graphics) {
		
		if(!this.isCurrentlyAnimating()) {
			return;
		}

		if(this.renderedLastTime) {
			this.renderedLastTime = false;
			return;
		}
		
		Image image = this.frames[this.currentFrameIndex];
		
		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);
		graphics.drawImage(image, currentPosition.x, currentPosition.y, null);
		
		this.renderedLastTime = true;
		
		if(this.isAtEndOfAnimation()) {
			//TODO: temporary just so I could see the animation continuously for testing
			//this.completeAnimationCycle();
			this.currentFrameIndex = 0;
		} else {
			
			this.currentFrameIndex++;
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

//	private void completeAnimationCycle() {
//		this.currentFrameIndex = -1;
//	}
	
	//TODO: this is duplicated in several places. consolidate.
	private Point getCurrentPosition(Rectangle screenSize) {
		
		PositionQuery query = new PositionQuery(screenSize);
		
		this.bus.send(query, this.entityId);
		
		Point reply = query.getReply();
		if(reply == null) {
			reply = new Point();
		}
		
		return reply;

	}

}
