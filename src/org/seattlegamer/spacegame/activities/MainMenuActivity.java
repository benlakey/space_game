package org.seattlegamer.spacegame.activities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.commands.ActivityTransitionCommand;
import org.seattlegamer.spacegame.commands.Command;
import org.seattlegamer.spacegame.commands.ExitCommand;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MainMenuActivity extends Activity {

	private static final Font MENU_FONT = new Font("Courier", Font.BOLD, 32);

	private List<MenuItem> menuItems;
	private int selectedIndex;
	
	public MainMenuActivity() {
		this.menuItems = new ArrayList<MenuItem>();

		this.menuItems.add(new MenuItem("New Game", 0, new ActivityTransitionCommand(new GameActivity())));
		this.menuItems.add(new MenuItem("Credits", 1, null));
		this.menuItems.add(new MenuItem("Exit", 2, new ExitCommand(0)));

		this.selectedIndex = 0;
	}
	
	private MenuItem getCurrentMenuItem() {
		return this.menuItems.get(this.selectedIndex);
	}
	
	@Override
	public void update(long elapsedTimeMillis) {
	}

	@Override
	public Iterable<? extends Renderable> getRenderables() {
		return this.menuItems;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.selectedIndex++;
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.selectedIndex--;
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			MenuItem currentMenuItem = this.getCurrentMenuItem();
			Command command = currentMenuItem.getCommand();
			this.notifyListeners(command);
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.notifyListeners(new ExitCommand(0));
		}
		
		int wrap = this.menuItems.size();
		this.selectedIndex = (this.selectedIndex % wrap + wrap) % wrap;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private class MenuItem extends RenderableText {

		private final int index;
		private final Command command;
		
		public MenuItem(String text, int index, Command command) {
			super(text, MENU_FONT);
			this.index = index;
			this.command = command;
		}
		
		public Command getCommand() {
			return this.command;
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
				this.setColor(Color.ORANGE);
			} else {
				this.setColor(Color.WHITE);
			}
			
			super.render(graphics);

		}

	}

}
