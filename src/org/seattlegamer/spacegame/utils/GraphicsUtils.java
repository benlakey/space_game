package org.seattlegamer.spacegame.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.lang.reflect.Method;

public class GraphicsUtils {
	
	private static Component window;

	//TODO: yea, this is gross, but the alternative is passing around a window reference everywhere, and besides 
	//this is only going to be used while we are still using windowed mode for debugging. When we ship it will be 
	//full screen only.
	public static void setWindowReference(Component gameWindow) {
		window = gameWindow;
	}
	
	public static Dimension measureTextPixels(Graphics2D graphics, Font font, String text) {
		FontMetrics metrics = graphics.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		return new Dimension(adv, hgt);
	}

	public static Dimension getCurrentScreenDimension() {

		if(window != null) {
			return window.getSize();
		}

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
	    
	    DisplayMode displayMode = graphicsDevice.getDisplayMode();

	    int width = displayMode.getWidth();
	    int height = displayMode.getHeight();
	    
	    return new Dimension(width, height);

	}

	public static int getCenterScreenX() {
		Dimension currentScreenDimension = getCurrentScreenDimension();
		double screenWidth = currentScreenDimension.getWidth();
		double centerX = screenWidth / 2;
		return (int) centerX;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void enableOSXFullscreen(Window window) throws IllegalArgumentException, ReflectiveOperationException, SecurityException {
		Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
        Class params[] = new Class[]{Window.class, Boolean.TYPE};
        Method method = util.getMethod("setWindowCanFullScreen", params);
        method.invoke(util, window, true);
	}

}
