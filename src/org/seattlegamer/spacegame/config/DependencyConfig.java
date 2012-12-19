package org.seattlegamer.spacegame.config;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Image;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.BusImpl;
import org.seattlegamer.spacegame.core.CanvasRenderer;
import org.seattlegamer.spacegame.core.Engine;
import org.seattlegamer.spacegame.core.GameCanvas;
import org.seattlegamer.spacegame.core.GameLauncher;
import org.seattlegamer.spacegame.core.GameState;
import org.seattlegamer.spacegame.core.Input;
import org.seattlegamer.spacegame.core.KeyInput;
import org.seattlegamer.spacegame.core.MenuState;
import org.seattlegamer.spacegame.core.PointerInput;
import org.seattlegamer.spacegame.core.Renderer;
import org.seattlegamer.spacegame.core.State;
import org.seattlegamer.spacegame.core.StateManager;
import org.seattlegamer.spacegame.core.TestImageCreator;
import org.seattlegamer.spacegame.messages.StateChange;
import org.seattlegamer.spacegame.resources.InMemoryResourceCache;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.resources.ResourceLoader;
import org.seattlegamer.spacegame.resources.ScaledImageResourceLoader;
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
	
	public @Bean PropertiesAccessor propertiesAccessor() {
		Properties properties = PropertiesLoader.loadProperties(PROPERTIES_FILE_PATH);
		return new PropertiesAccessor(properties);
	}
	
	public @Bean DisplayMode displayMode() {
		
		PropertiesAccessor propertiesAccessor = propertiesAccessor();
		
		return new DisplayMode(
			propertiesAccessor.getInteger("displaymode.width", 800),
			propertiesAccessor.getInteger("displaymode.width", 600),
			propertiesAccessor.getInteger("displaymode.bit_depth", 16),
			propertiesAccessor.getInteger("displaymode.refresh_rate", 60));

	}
	
	public @Bean Canvas canvas() {
		return new GameCanvas(input(), propertiesAccessor().getString("title", "Space Game!"), displayMode());
	}
	
	public @Bean StateManager stateManager() {
		Bus bus = bus();
		Collection<State> states = new LinkedList<State>();
		states.add(gameState());
		states.add(menuState());
		StateManager stateManager = new StateManager(states);
		bus.register(stateManager, null);
		bus.broadcast(new StateChange(MenuState.class));
		return stateManager;

	}
	
	public @Bean GameState gameState() {
		return new GameState(bus(), resourceCache(), displayMode());
	}
	
	public @Bean MenuState menuState() {
		return new MenuState(bus(), resourceCache());
	}
	
	public @Bean GameLauncher gameLauncher() {
		Bus bus = bus();
		GameLauncher gameLauncher = new GameLauncher(bus(), gameState());
		bus.register(gameLauncher, null);
		return gameLauncher;
	}

	public @Bean Engine engine() {
		return new Engine(framerateThrottle(), keyInput(), pointerInput(), renderer(), stateManager());
	}
	
	public @Bean Renderer renderer() {
		return new CanvasRenderer(canvas());
	}

	public @Bean Throttle framerateThrottle() {
		PropertiesAccessor propertiesAccessor = propertiesAccessor();
		int targetFramerate = propertiesAccessor.getInteger("target_framerate", 100);
		return new Throttle(1000 / targetFramerate);
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
		InMemoryResourceCache inMemoryResourceCache = new InMemoryResourceCache(scaledImageResourceLoader());
		PropertiesAccessor propertiesAccessor = propertiesAccessor();
		int scale = propertiesAccessor.getInteger("scale", 80);

		//TODO: remove this hack when planet generation is in
		TestImageCreator testImageCreator = new TestImageCreator(scale);
		Image player = testImageCreator.buildPlayer(Color.BLUE);
		inMemoryResourceCache.putImage("replaceme_hardcoded_planet", player);
		
		return inMemoryResourceCache;
	}

	public @Bean ResourceLoader<Image> scaledImageResourceLoader() {
		PropertiesAccessor propertiesAccessor = propertiesAccessor();
		int scale = propertiesAccessor.getInteger("scale", 80);
		return new ScaledImageResourceLoader(scale);
	}
	
	public @Bean Bus bus() {
		return new BusImpl();
	}

}
