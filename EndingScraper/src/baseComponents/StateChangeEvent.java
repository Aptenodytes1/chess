package baseComponents;

import java.awt.AWTEvent;

public class StateChangeEvent extends AWTEvent {
	public static final int STATE_CHANGED = 1;

	public StateChangeEvent(Object source, int id) {
		super(source, id);
	}

}
