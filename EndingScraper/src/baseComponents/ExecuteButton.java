package baseComponents;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ExecuteButton extends JPanel {
	JButton button = new JButton("");

	public ExecuteButton(String buttonName) {
		super();
		this.add(button);
		button.setText(buttonName);
		button.addActionListener(null);
	}

	public void addActionListener(ActionListener l) {
		button.addActionListener(l);
	}

}
