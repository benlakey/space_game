package org.seattlegamer.spacegame.activities;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.seattlegamer.spacegame.MenuItem;
import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public abstract class MenuActivity extends Activity {

	protected List<MenuItem> menuItems;
	protected int selectedIndex;
	protected final Bus bus;
	
	public MenuActivity(Bus bus) {
		this.bus = bus;
		this.menuItems = new ArrayList<MenuItem>();
		this.selectedIndex = 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.selectedIndex++;
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.selectedIndex--;
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			MenuItem currentMenuItem = this.menuItems.get(this.selectedIndex);
			Command command = currentMenuItem.getCommand();
			if(command != null) {
				this.bus.send(command);
			}
		}
		
		int wrap = this.menuItems.size();
		this.selectedIndex = (this.selectedIndex % wrap + wrap) % wrap;
		
		for(int i = 0; i < this.menuItems.size(); i++) {
			MenuItem menuItem = this.menuItems.get(i);
			if(i == this.selectedIndex) {
				menuItem.setSelected(true);
			} else {
				menuItem.setSelected(false);
			}
		}
		
	}
	
	@Override
	public void render(Graphics2D graphics) {

		for(MenuItem menuItem : this.menuItems) {
			
			RenderableText renderableText = menuItem.getRenderableText();

			Dimension textSize = GraphicsUtils.measureTextPixels(graphics, renderableText.getFont(), renderableText.getText());

			int centerScreenX = GraphicsUtils.getCenterScreenX();
			int drawPositionX = centerScreenX - (textSize.width / 2);
			
			int index = this.menuItems.indexOf(menuItem);
			
			int drawPositionY = textSize.height * (index + 1);
			
			renderableText.setPosition(new Point(drawPositionX, drawPositionY));
			renderableText.render(graphics);

		}
		
	}

}
