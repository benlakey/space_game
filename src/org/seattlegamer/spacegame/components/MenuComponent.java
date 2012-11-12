package org.seattlegamer.spacegame.components;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public abstract class MenuComponent extends ComponentBase {

	private List<MenuItem> menuItems;
	protected int selectedIndex;
	protected final Bus bus;
	
	public MenuComponent(Bus bus) {
		this.bus = bus;
		this.menuItems = new ArrayList<MenuItem>();
	}
	
	public void addMenuItem(MenuItem menuItem) {
		this.menuItems.add(menuItem);
		this.subComponents.add(menuItem);
	}
	
	public void selectIndex(int index) {
		
		this.selectedIndex = index;
		
		for(int i = 0; i < this.menuItems.size(); i++) {
			this.menuItems.get(i).setSelected(i == this.selectedIndex);
		}
		
	}
	
	public MenuItem getIndex(int index) {
		return this.menuItems.get(index);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int newIndex = this.selectedIndex;
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			MenuItem currentMenuItem = this.menuItems.get(this.selectedIndex);
			Command command = currentMenuItem.getCommand();
			if(command != null) {
				this.bus.send(command);
				return;
			} else {
				newIndex++;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			newIndex++;
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			newIndex--;
		}
		
		this.wrapAndSetIndex(newIndex);
		
		super.keyPressed(e);

	}
	
	private void wrapAndSetIndex(int newIndex) {
		int wrap = this.menuItems.size();
		newIndex = (newIndex % wrap + wrap) % wrap;

		this.selectIndex(newIndex);
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
		
		super.render(graphics);
		
	}

}
