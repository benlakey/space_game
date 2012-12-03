package org.seattlegamer.spacegame;

import java.util.UUID;

public class ExitStateCommand extends Message {

	public ExitStateCommand(UUID sourceEntityId) {
		super(sourceEntityId);
	}

}
