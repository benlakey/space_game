package org.seattlegamer.spacegame.commands;

public class ExitCommand implements Command {
	
	private int exitCode;
	
	public ExitCommand(int exitCode) {
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return this.exitCode;
	}

}
