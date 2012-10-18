package org.seattlegamer.spacegame;

import java.awt.Font;

public class MainMenuActivity implements Activity {
	
	private Renderable[] renderables;
	
	public MainMenuActivity() {
		this.renderables = new Renderable[3];
		this.setMenuItem("Start Game", 0);
		this.setMenuItem("Options", 1);
		this.setMenuItem("Exit", 2);
	}
	
	private void setMenuItem(String text, int index) {
		Font font = new Font("Comic Sans MS", Font.PLAIN, 40);
		RenderableText renderableText = new RenderableText(text, font);
		renderableText.setPosition(200, 200 + (index * 100));
		this.renderables[index] = renderableText;
	}
	
	@Override
	public Renderable[] getRenderables() {
		return this.renderables;
	}

	@Override
	public void sendCommand(Command command) {
		
	}

}
