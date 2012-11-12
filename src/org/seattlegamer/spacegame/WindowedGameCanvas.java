package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.communication.ExitGame;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class WindowedGameCanvas extends Canvas implements GameCanvas {

	private static final long serialVersionUID = 1L;
	private static final int bufferCount = 2;
	
	private final Frame frame;
	private final Bus bus;

	public WindowedGameCanvas(Bus bus, String title, KeyboardInput keyboardInput, MouseInput mouseInput, Dimension dimension) {

		this.bus = bus;

		this.setBounds(0, 0, dimension.width, dimension.height);
		this.setIgnoreRepaint(true);

		this.frame = new Frame(title);
		this.frame.add(this);
		this.frame.setIgnoreRepaint(true);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);

		this.addKeyListener(keyboardInput);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);

		this.createBufferStrategy(bufferCount);
		this.requestFocus();
		
		GraphicsUtils.setWindowReference(frame);

	}

	@Override
	public Graphics2D getGraphics() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		return (Graphics2D)bufferStrategy.getDrawGraphics();
	}

	@Override
	public void showNextBuffer() {
		this.getBufferStrategy().show();
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ExitGame;
	}

	@Override
	public void handle(Command command) {

		ExitGame exitGameCommand = (ExitGame)command;
		
		if(exitGameCommand.isCanvasDisposed()) {
			return;
		}
		
		if(!exitGameCommand.isEngineStopped()) {
			return;
		}
		
		this.frame.dispose();
		
		ExitGame newExitGameCommand = new ExitGame();
		newExitGameCommand.setEngineStopped(true);
		newExitGameCommand.setCanvasDisposed(true);

		this.bus.send(newExitGameCommand);

	}

}