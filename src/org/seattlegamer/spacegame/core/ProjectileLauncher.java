package org.seattlegamer.spacegame.core;

import java.awt.Image;
import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.messages.PhysicsCloning;
import org.seattlegamer.spacegame.messages.ProjectileLaunch;
import org.seattlegamer.spacegame.messages.SpeedChange;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class ProjectileLauncher extends Component {

	private static final Logger logger = Logger.getLogger(ProjectileLauncher.class);
	
	private final ResourceCache resourceCache;
	private final StateManager stateManager;
	private final GameSettings settings;

	public ProjectileLauncher(
			ComponentBus bus, 
			UUID entityId, 
			ResourceCache resourceCache, 
			StateManager stateManager,
			GameSettings settings) {
		super(bus, entityId);
		this.resourceCache = resourceCache;
		this.stateManager = stateManager;
		this.settings = settings;
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
		
		Image scaledProjectileImage = GraphicsUtils.getScaledImage(projectileImage, this.settings.getScale());
		
		int projectileWidth = projectileImage.getWidth(null);
		int projectileHeight = projectileImage.getHeight(null);
		
		UUID projectileId = UUID.randomUUID();
		
		this.stateManager.addComponent(new Sprite(this.bus, projectileId, scaledProjectileImage));
		
		Physics physics = new Physics(this.bus, projectileId);
		this.bus.send(new PhysicsCloning(this.getEntityId(), physics), this.getEntityId());
		
		physics.setWidth(projectileWidth);
		physics.setHeight(projectileHeight);
		
		this.stateManager.addComponent(physics);

		this.bus.send(new SpeedChange(0.4), projectileId);
		
		//TODO: weapon type will determine launch speed and launch image

	}

}
