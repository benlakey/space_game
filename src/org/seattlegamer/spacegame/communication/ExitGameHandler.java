package org.seattlegamer.spacegame.communication;


public class ExitGameHandler implements Handler {

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ExitGame;
	}
	
	@Override
	public void handle(Command command) {
		if(command instanceof ExitGame) {
			ExitGame exitCommand = (ExitGame)command;
			System.exit(exitCommand.getExitCode());
		}
	}

}
