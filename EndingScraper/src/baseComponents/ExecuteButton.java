package baseComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

public class ExecuteButton extends JButton implements StateChangeListener, ActionListener {
	/**
	 * データソース
	 */
	private Exportable dataSource;

	/**
	 * コンストラクタ
	 * 
	 * @param buttonName ボタン名
	 */
	public ExecuteButton(String buttonName) {
		super(buttonName);
		addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(StateChangeEvent e) {
		this.setEnabled(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (dataSource != null && ((List<String>) dataSource.export()).size() != 0) {
			this.setEnabled(false);
		}
	}

	/**
	 * データソースを設定する。
	 * 
	 * @param source データソース
	 */
	public void setDataSource(Exportable dataSource) {
		this.dataSource = dataSource;
	}

}
