package org.seattlegamer.spacegame;

public class ExitCommandHandler implements Handler {

	@Override
	public void handle(Command command) {
		if(command instanceof ExitCommand) {
			ExitCommand exitCommand = (ExitCommand)command;
			System.exit(exitCommand.getExitCode());
		}
	}

}
