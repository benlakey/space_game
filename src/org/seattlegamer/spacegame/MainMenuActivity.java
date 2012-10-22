package org.seattlegamer.spacegame;

import java.awt.Font;
import java.util.Collection;
import java.util.LinkedList;

public class MainMenuActivity implements Activity {
	
	private static final Font MENU_FONT = new Font("Courier", Font.PLAIN, 32);
	
	private Collection<Renderable> renderables;
	
	public MainMenuActivity() {
		this.renderables = new LinkedList<Renderable>();
		this.addMenuItem("Start Game");
		this.addMenuItem("Options");
		this.addMenuItem("Exit");
	}
	
	private void addMenuItem(String text) {
		RenderableText renderableText = this.constructNewMenuItem(text);
		this.renderables.add(renderableText);
	}
	
	private int getNewMenuItemPositionY() {
		int currentMenuItemsCount = this.renderables.size();
		int fontPointSize = MENU_FONT.getSize(); //TODO: normalize to pixels
		int positionY = fontPointSize + currentMenuItemsCount * fontPointSize;
		return positionY;
	}
	
	private RenderableText constructNewMenuItem(String text) {
		
		int positionY = this.getNewMenuItemPositionY();
		
		RenderableText renderableText = new RenderableText(text, MENU_FONT);
		renderableText.setPositionY(positionY);
		renderableText.center();
		return renderableText;

	}
	
	@Override
	public Iterable<Renderable> getRenderables() {
		return this.renderables;
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return false;
	}

	@Override
	public <T extends Command> void handle(T command) {
	}

}
