package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.UUID;

import org.seattlegamer.spacegame.utils.NumberUtils;

public class Physics extends Component {

	private final Point position;
	private double angleDegrees;
	private int width;
	private int height;
	private double speed;
	
	public Physics(ComponentBus bus, UUID entityId, Point position, double angleDegrees, int width, int height) {
		super(bus, entityId);
		this.position = position;
		this.angleDegrees = angleDegrees;
		this.width = width;
		this.height = height;
		
		AffineTransform transform = this.createTransform(0);
		this.bus.send(transform, this.getEntityId());
	}

	@Subscription
	public void updatePosition(PositionChange change) {
		this.position.x += change.getPositionDiffX();
		this.position.y += change.getPositionDiffY();
	}
	
	@Subscription
	public void updateRotation(RotationChange change) {
		this.angleDegrees += change.getRotationDegreeDiff();
		this.angleDegrees %= 359;
	}
	
	@Subscription
	public void adjustSpeed(SpeedChange change) {
		this.speed += change.getSpeedDiff();
		this.speed = NumberUtils.clamp(this.speed, -0.5D, 0.5D);
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		AffineTransform transform = this.createTransform(elapsedMillis);
		this.bus.send(transform, this.getEntityId());

	}
	
	private AffineTransform createTransform(long elapsedMillis) {

		AffineTransform transform = new AffineTransform();
		
		double radians = NumberUtils.toRadians(this.angleDegrees);
		
		double diffX = Math.sin(radians) * this.speed * elapsedMillis;
		double diffY = 0 - Math.cos(radians) * this.speed * elapsedMillis;
		
		this.position.x += diffX;
		this.position.y += diffY;
		
		transform.setToTranslation(this.position.x, this.position.y);
		transform.rotate(radians, this.width / 2, this.height / 2);
		
		return transform;
		
	}

}