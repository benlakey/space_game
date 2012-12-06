package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

public class Position extends Component {

	private final Point offset;
	private HorizontalAlignment horizontalAlignment;
	private VerticalAlignment verticalAlignment;
	
	private final Handler<PositionChange> positionChangeHandler = this.getPositionChangeHandler();
	private final Handler<PositionQuery> positionQueryHandler = this.getPositionQueryHandler();
	private final Handler<PositionInitialization> positionInitializationHandler = this.getPositionInitializationHandler();
	
	public Position(Bus bus, UUID entityId) {
		super(bus, entityId);
		this.offset = new Point();
	}
	
	@Override
	public void registerHandlers() {
		this.bus.register(PositionChange.class, positionChangeHandler);
		this.bus.register(PositionQuery.class, positionQueryHandler);
		this.bus.register(PositionInitialization.class, positionInitializationHandler);
	}
	
	@Override
	public void deregisterHandlers() {
		this.bus.deregister(PositionChange.class, positionChangeHandler);
		this.bus.deregister(PositionQuery.class, positionQueryHandler);
		this.bus.deregister(PositionInitialization.class, positionInitializationHandler);
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
			
			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
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

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
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

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
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
