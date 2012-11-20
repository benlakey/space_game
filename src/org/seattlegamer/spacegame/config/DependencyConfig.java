package org.seattlegamer.spacegame.config;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.seattlegamer.spacegame.AWTInput;
import org.seattlegamer.spacegame.AWTRenderer;
import org.seattlegamer.spacegame.Engine;
import org.seattlegamer.spacegame.GameCanvas;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.Renderer;
import org.seattlegamer.spacegame.StateManager;
import org.seattlegamer.spacegame.resources.ImageResourceLoader;
import org.seattlegamer.spacegame.resources.InMemoryResourceCache;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.resources.ResourceLoader;
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
		
		String title = GameSettings.getTitle();
		GameCanvas screen = new GameCanvas(title);
		Input input = input();
		if(input instanceof MouseListener) {
			screen.addMouseListener((MouseListener)input);
		}
		if(input instanceof MouseMotionListener) {
			screen.addMouseMotionListener((MouseMotionListener)input);
		}
		if(input instanceof KeyListener) {
			screen.addKeyListener((KeyListener)input);
		}
		return screen;
		
	}

	public @Bean Engine engine() {
		return new Engine(framerateThrottle(), input(), renderer(), stateManager());
	}
	
	public @Bean Renderer renderer() {
		return new AWTRenderer(canvas());
	}
	
	public @Bean StateManager stateManager() {
		return new StateManager();
	}
	
	public @Bean Throttle framerateThrottle() {
		int targetFramerate = GameSettings.getTargetFramerate();
		return new Throttle(1000 / targetFramerate);
	}

	public @Bean Input input() {
		return new AWTInput();
	}

	public @Bean ResourceCache resourceCache() {
		return new InMemoryResourceCache(imageResourceLoader());
	}

	public @Bean ResourceLoader<Image> imageResourceLoader() {
		return new ImageResourceLoader();
	}

}
