package org.seattlegamer.spacegame.communication;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.apache.log4j.Logger;

public class CommunicationBus implements Bus {

	private static Logger logger = Logger.getLogger(CommunicationBus.class);
	
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final Collection<Handler> handlers = new LinkedList<Handler>();
	private final Object handlersLock = new Object();
	
	public CommunicationBus() {
		this.register(this);
	}
	
	@Override
	public void register(Handler handler) {
		synchronized(handlersLock) {
			if(this.handlers.contains(handler)) {
				return;
			}
			this.handlers.add(handler);
		}
	}

	@Override
	public void deregister(Handler handler) {
		synchronized(handlersLock) {
			this.handlers.remove(handler);
		}
	}

	@Override
	public void send(final Command command) {
		
		logger.info(command.toString());
		
		synchronized(handlersLock) {
			for(final Handler handler : this.handlers) {
				if(handler.canHandle(command)) {
					try {
						executor.execute(new Runnable() {
							@Override
							public void run() {
								handler.handle(command);
							}
						});
					} catch(RejectedExecutionException e) {
						logger.warn("Bus faulted on " + command.toString());
					}
				}
			}

		}

	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ExitGame;
	}

	@Override
	public void handle(Command command) {
		
		ExitGame exitGameCommand = (ExitGame)command;
		
		if(exitGameCommand.isBusStopped()) {
			return;
		}
		
		if(!exitGameCommand.isEngineStopped()) {
			return;
		}
		
		if(!exitGameCommand.isCanvasDisposed()) {
			return;
		}

		this.executor.shutdown();
		
	}

}
