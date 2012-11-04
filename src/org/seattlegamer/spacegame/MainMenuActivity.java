package org.seattlegamer.spacegame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MainMenuActivity implements Activity {

	private static final Font MENU_STANDARD_FONT = new Font("Courier", Font.PLAIN, 32);
	private static final Font MENU_SELECTED_FONT = new Font("Courier", Font.BOLD, 32);

	private Collection<Renderable> renderables;
	private int selectedIndex;
	
	public MainMenuActivity() {
		this.renderables = new LinkedList<Renderable>();
		
		this.renderables.add(new MenuItem("New Game", MENU_STANDARD_FONT, 0));
		this.renderables.add(new MenuItem("Credits", MENU_STANDARD_FONT, 1));
		this.renderables.add(new MenuItem("Exit", MENU_STANDARD_FONT, 2));

		this.selectedIndex = 0;
	}

	@Override
	public Iterable<Renderable> getRenderables() {
		return this.renderables;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.selectedIndex++;
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			this.selectedIndex--;
		}
		
		int wrap = this.renderables.size();
		this.selectedIndex = (this.selectedIndex % wrap + wrap) % wrap;

	}

	private class MenuItem extends RenderableText {

		private int index;
		
		public MenuItem(String text, Font font, int index) {
			super(text, font);
			this.index = index;
		}
		
		@Override
		public void render(Graphics2D graphics) {
			
			Dimension textSize = GraphicsUtils.measureTextPixels(graphics, this.getFont(), this.getText());

			int drawPositionY = textSize.height * (this.index + 1);
			
			int centerScreenX = GraphicsUtils.getCenterScreenX();
			int drawPositionX = centerScreenX - (textSize.width / 2);
			
			this.setPositionX(drawPositionX);
			this.setPositionY(drawPositionY);
			
			if(selectedIndex == this.index) {
				this.setFont(MENU_SELECTED_FONT);
			} else {
				this.setFont(MENU_STANDARD_FONT);
			}
			
			super.render(graphics);
		}

	}
	

}
