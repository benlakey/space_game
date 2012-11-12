package org.seattlegamer.spacegame.communication;

public class ExitGame implements Command {

	private boolean engineStopped;
	private boolean canvasDisposed;
	private boolean busStopped;

	public boolean isEngineStopped() {
		return engineStopped;
	}

	public void setEngineStopped(boolean engineStopped) {
		this.engineStopped = engineStopped;
	}

	public boolean isCanvasDisposed() {
		return canvasDisposed;
	}

	public void setCanvasDisposed(boolean canvasDisposed) {
		this.canvasDisposed = canvasDisposed;
	}

	public boolean isBusStopped() {
		return busStopped;
	}

	public void setBusStopped(boolean busStopped) {
		this.busStopped = busStopped;
	}
	
	@Override
	public String toString() {
		return String.format("ExitGame - engineStopped: %s, canvasDisposed: %s, busStopped: %s", this.engineStopped, this.canvasDisposed, this.busStopped);
	}

}
