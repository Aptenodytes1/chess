package baseComponents;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class StateWatchingJLabel extends JLabel {
	private String text;
	List<StateChangeListener> listener = new ArrayList<StateChangeListener>();

	@Override
	public void setText(String newText) {
		String formerText = this.text;
		if (!newText.equals(formerText)) {
			if (listener != null)
				for (StateChangeListener l : listener) {
					l.stateChanged(new StateChangeEvent(this, StateChangeEvent.STATE_CHANGED));
				}
		}
		this.text = newText;
	}

	public void addStateChangeListener(StateChangeListener l) {
		this.listener.add(l);
	}
}
