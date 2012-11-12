package org.seattlegamer.spacegame.config;

import java.awt.Dimension;

import org.seattlegamer.spacegame.CanvasRenderer;
import org.seattlegamer.spacegame.Engine;
import org.seattlegamer.spacegame.FullScreenGameCanvas;
import org.seattlegamer.spacegame.GameCanvas;
import org.seattlegamer.spacegame.KeyboardInput;
import org.seattlegamer.spacegame.MouseInput;
import org.seattlegamer.spacegame.RateLimiter;
import org.seattlegamer.spacegame.Renderer;
import org.seattlegamer.spacegame.WindowedGameCanvas;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.CommunicationBus;
import org.seattlegamer.spacegame.communication.NewGameHandler;
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
	
	public @Bean RateLimiter rateLimiter() {
		return new RateLimiter(GameSettings.getTargetFramerate());
	}
	
	public @Bean GameCanvas gameCanvas() {
		
		Bus bus = bus();
		
		GameCanvas gameCanvas = null;
		
		if(GameSettings.shouldUseFullscreen()) {
			gameCanvas = new FullScreenGameCanvas(bus, GameSettings.getTitle(), keyboardInput(), mouseInput());
		} else {
			Dimension dimension = new Dimension(800, 600);
			gameCanvas = new WindowedGameCanvas(bus, GameSettings.getTitle(), keyboardInput(), mouseInput(), dimension);
		}
		
		
		bus.register(gameCanvas);
		
		return gameCanvas;
	}
	
	public @Bean KeyboardInput keyboardInput() {
		return new KeyboardInput();
	}
	
	public @Bean MouseInput mouseInput() {
		return new MouseInput();
	}
	
	public @Bean Renderer renderer() { 
		GameCanvas gameCanvas = gameCanvas();
		return new CanvasRenderer(gameCanvas);
	}
	
	public @Bean Engine engine() {
		
		Renderer renderer = renderer();
		KeyboardInput keyboardInput = keyboardInput();
		MouseInput mouseInput = mouseInput();
		RateLimiter rateLimiter = rateLimiter();
		Bus bus = bus();
		
		Engine engine = new Engine(renderer, bus, keyboardInput, mouseInput, rateLimiter);

		bus.register(engine);
		bus.register(new NewGameHandler(bus));

		return engine;

	}

	public @Bean Bus bus() {
		return new CommunicationBus();
	}
	
}
