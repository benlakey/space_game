package org.seattlegamer.spacegame.config;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Image;
import java.util.Properties;

import org.seattlegamer.spacegame.core.CanvasRenderer;
import org.seattlegamer.spacegame.core.ComponentBus;
import org.seattlegamer.spacegame.core.Engine;
import org.seattlegamer.spacegame.core.GameCanvas;
import org.seattlegamer.spacegame.core.Input;
import org.seattlegamer.spacegame.core.KeyInput;
import org.seattlegamer.spacegame.core.PointerInput;
import org.seattlegamer.spacegame.core.Renderer;
import org.seattlegamer.spacegame.core.StateManager;
import org.seattlegamer.spacegame.resources.ImageResourceLoader;
import org.seattlegamer.spacegame.resources.InMemoryResourceCache;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.resources.ResourceLoader;
import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.utils.PropertiesAccessor;
import org.seattlegamer.spacegame.utils.PropertiesLoader;
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
	
	private static final String PROPERTIES_FILE_PATH = "/spacegame.properties";

	public @Bean GameSettings settings() {
		Properties properties = PropertiesLoader.loadProperties(PROPERTIES_FILE_PATH);
		PropertiesAccessor propertiesAccessor = new PropertiesAccessor(properties);
		return new GameSettings(propertiesAccessor);
	}
	
	public @Bean Canvas canvas() {
		
		GameSettings settings = settings();

		DisplayMode displayMode = new DisplayMode(
				settings.getDisplayModeWidth(),
				settings.getDisplayModeHeight(),
				settings.getDisplayModeBitDepth(),
				settings.getDisplayModeRefreshRate());

		return new GameCanvas(input(), settings.getTitle(), displayMode);

	}
	
	public @Bean StateManager stateManager() {
		return new StateManager(resourceCache(), settings(), new MenuState(componentBus()));
	}

	public @Bean Engine engine() {
		return new Engine(framerateThrottle(), keyInput(), pointerInput(), renderer(), stateManager());
	}
	
	public @Bean Renderer renderer() {
		return new CanvasRenderer(canvas());
	}

	public @Bean Throttle framerateThrottle() {
		GameSettings settings = settings();
		return new Throttle(1000 / settings.getTargetFramerate());
	}

	public @Bean KeyInput keyInput() {
		return input();
	}
	
	public @Bean PointerInput pointerInput() {
		return input();
	}
	
	public @Bean Input input() {
		return new Input();
	}

	public @Bean ResourceCache resourceCache() {
		return new InMemoryResourceCache(imageResourceLoader());
	}

	public @Bean ResourceLoader<Image> imageResourceLoader() {
		return new ImageResourceLoader(settings());
	}
	
	public @Bean ComponentBus componentBus() {
		return new ComponentBus();
	}

}
