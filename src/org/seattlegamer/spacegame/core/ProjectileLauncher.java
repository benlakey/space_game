package org.seattlegamer.spacegame.core;

import java.awt.Image;
import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.PhysicsCloning;
import org.seattlegamer.spacegame.messages.ProjectileLaunch;
import org.seattlegamer.spacegame.messages.SpeedChange;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class ProjectileLauncher extends Component {

	private static final Logger logger = Logger.getLogger(ProjectileLauncher.class);
	
	private final ResourceCache resourceCache;

	public ProjectileLauncher(Bus bus, UUID entityId, ResourceCache resourceCache) {
		super(bus, entityId);
		this.resourceCache = resourceCache;
	}

	@Subscription
	public void launchProjectile(ProjectileLaunch launch) {

		String asset = "assets/projectile.png";
		
		Image projectileImage;
		try {
			projectileImage = resourceCache.getImage(asset);
		} catch (IOException e) {
			logger.error("Failed to launch projectile because an assset could not be loaded! Asset: " + asset);
			return;
		}

		int projectileWidth = projectileImage.getWidth(null);
		int projectileHeight = projectileImage.getHeight(null);
		
		UUID projectileId = UUID.randomUUID();
		
		this.bus.broadcast(new ComponentAddition(new Sprite(this.bus, projectileId, projectileImage)));
		
		Physics physics = new Physics(this.bus, projectileId);
		this.bus.send(new PhysicsCloning(this.getEntityId(), physics), this.getEntityId());
		
		physics.setWidth(projectileWidth);
		physics.setHeight(projectileHeight);
		
		this.bus.broadcast(new ComponentAddition(physics));

		this.bus.send(new SpeedChange(0.4), projectileId);
		
		//TODO: weapon type will determine launch speed and launch image

	}

}
