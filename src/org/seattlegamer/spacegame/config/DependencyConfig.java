package org.seattlegamer.spacegame.config;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Image;

import org.seattlegamer.spacegame.CanvasRenderer;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.Engine;
import org.seattlegamer.spacegame.GameCanvas;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.Renderer;
import org.seattlegamer.spacegame.StateManager;
import org.seattlegamer.spacegame.resources.ImageResourceLoader;
import org.seattlegamer.spacegame.resources.InMemoryResourceCache;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.resources.ResourceLoader;
import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.utils.Throttle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Important to note: While it may seem that the below configuration is new-ing up 
 * instances of the same class multiple times, it is not. Spring is managing the 
 * lifespans and caching them as singletons for repeat use. Treat this file as a 
 * manifest of how an object is created, not as literal code paths.
 */

@Configuration
public class DependencyConfig {

	public @Bean Canvas canvas() {

		DisplayMode displayMode = new DisplayMode(
				GameSettings.current().getDisplayModeWidth(),
				GameSettings.current().getDisplayModeHeight(),
				GameSettings.current().getDisplayModeBitDepth(),
				GameSettings.current().getDisplayModeRefreshRate());

		return new GameCanvas(input(), GameSettings.current().getTitle(), displayMode);

	}
	
	public @Bean StateManager stateManager() {
		return StateManager.from(componentBus(), resourceCache(), new MenuState());
	}

	public @Bean Engine engine() {
		return new Engine(framerateThrottle(), input(), renderer(), stateManager());
	}
	
	public @Bean Renderer renderer() {
		return new CanvasRenderer(canvas());
	}

	public @Bean Throttle framerateThrottle() {
		return new Throttle(1000 / GameSettings.current().getTargetFramerate());
	}

	public @Bean Input input() {
		return new Input();
	}

	public @Bean ResourceCache resourceCache() {
		return new InMemoryResourceCache(imageResourceLoader());
	}

	public @Bean ResourceLoader<Image> imageResourceLoader() {
		return new ImageResourceLoader();
	}
	
	public @Bean ComponentBus componentBus() {
		return new ComponentBus();
	}

}
