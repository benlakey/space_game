package org.seattlegamer.spacegame.communication;

public class ExitGame implements Command {
	
	private int exitCode;
	
	public ExitGame(int exitCode) {
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return this.exitCode;
	}

}
