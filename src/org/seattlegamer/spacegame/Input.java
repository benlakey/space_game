package org.seattlegamer.spacegame;

public interface Input {
	int getPointerX();
	int getPointerY();
	boolean isKeyInputActive(Integer keyCode);
	boolean isPointerInputActive(Integer pointerCode);
}
