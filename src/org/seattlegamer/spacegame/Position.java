package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.Rectangle;

public class Position extends Component {

	private final Point offset;
	private HorizontalAlignment horizontalAlignment;
	private VerticalAlignment verticalAlignment;
	
	public Position(Entity entity) {
		super(entity);
		this.offset = new Point();
		this.entity.register(PositionChange.class, this.getPositionChangeHandler());
		this.entity.register(PositionQuery.class, this.getPositionQueryHandler());
		this.entity.register(PositionInitialization.class, this.getPositionInitializationHandler());
	}

	private Handler<PositionQuery> getPositionQueryHandler() {
		return new Handler<PositionQuery>() {
			@Override
			public void handle(PositionQuery message) {
				
				Rectangle screenSize = message.getScreenSize();
				
				Point position = new Point();
				position.x = this.getX(screenSize);
				position.y = this.getY(screenSize);

				message.setReply(position);

			}
			
			private int getX(Rectangle screenSize) {
				if(horizontalAlignment == HorizontalAlignment.CENTER) {
					return (screenSize.width / 2) + offset.x;
				} else if(horizontalAlignment == HorizontalAlignment.RIGHT) {
					return screenSize.width + offset.x;
				}
				return 0 + offset.x;
			}
			
			private int getY(Rectangle screenSize) {
				if(verticalAlignment == VerticalAlignment.MIDDLE) {
					return (screenSize.height / 2) + offset.y;
				} else if(verticalAlignment == VerticalAlignment.BOTTOM) {
					return screenSize.height + offset.y;
				}
				return 0 + offset.y;
			}
			
		};
	}

	private Handler<PositionChange> getPositionChangeHandler() {
		return new Handler<PositionChange>() {
			@Override
			public void handle(PositionChange message) {
				offset.x += message.getOffset().x;
				offset.y += message.getOffset().y;
			}
		};
	}
	
	private Handler<PositionInitialization> getPositionInitializationHandler() {
		return new Handler<PositionInitialization>() {
			@Override
			public void handle(PositionInitialization message) {
				offset.x = message.getOffset().x;
				offset.y = message.getOffset().y;
				horizontalAlignment = message.getHorizontalAlignment();
				verticalAlignment = message.getVerticalAlignment();
			}
		};
	}
	
	public enum HorizontalAlignment {
		LEFT,
		CENTER,
		RIGHT
	}
	
	public enum VerticalAlignment {
		TOP,
		MIDDLE,
		BOTTOM
	}
	
}
