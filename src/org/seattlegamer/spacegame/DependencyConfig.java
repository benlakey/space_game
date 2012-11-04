package org.seattlegamer.spacegame;

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
	
	public @Bean GameSettings gameSettings() {
		return GameSettingsLoader.loadFromFile(PROPERTIES_FILE_PATH);
	}
	
	public @Bean RateLimiter rateLimiter() {
		GameSettings gameSettings = gameSettings();
		return new RateLimiter(gameSettings.getTargetFramerate());
	}
	
	public @Bean GameCanvas gameCanvas() {
		GameSettings gameSettings = gameSettings();
		GameCanvas gameCanvas = null;
		if(gameSettings.shouldUseFullscreen()) {
			gameCanvas = new FullScreenGameCanvas(gameSettings.getTitle(), keyboardInput());
		} else {
			gameCanvas = new WindowedGameCanvas(gameSettings.getTitle(), keyboardInput(), 800, 600);
		}
		return gameCanvas;
	}
	
	public @Bean KeyboardInput keyboardInput() {
		return new KeyboardInput();
	}
	
	public @Bean Renderer renderer() { 
		GameCanvas gameCanvas = gameCanvas();
		return new CanvasRenderer(gameCanvas);
	}
	
	public @Bean Engine engine() {
		Renderer renderer = renderer();
		KeyboardInput keyboardInput = keyboardInput();
		RateLimiter rateLimiter = rateLimiter();
		return new Engine(renderer, keyboardInput, rateLimiter);
	}
	
}
