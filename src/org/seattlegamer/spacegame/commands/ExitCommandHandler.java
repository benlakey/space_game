package org.seattlegamer.spacegame.commands;

import org.seattlegamer.spacegame.Handler;

public class ExitCommandHandler implements Handler {

	@Override
	public void handle(Command command) {
		if(command instanceof ExitCommand) {
			ExitCommand exitCommand = (ExitCommand)command;
			System.exit(exitCommand.getExitCode());
		}
	}

}
