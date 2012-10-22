package org.seattlegamer.spacegame.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GraphicsUtils {

	public static Dimension getCurrentScreenDimension() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		return toolkit.getScreenSize();
	}
	
	
}
