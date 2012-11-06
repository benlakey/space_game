package org.seattlegamer.spacegame;

import org.seattlegamer.spacegame.commands.Command;

public interface Handler {
	void handle(Command command);
}
