package baseComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 検索実行ボタンを作成します。ファイルパスが空でないときに実行すると再度押下不可となり、
 * ファイルパスまたはエンディングタイプに変更があれば再度押下可能になります。
 * 
 * @author Aptenodytes
 *
 */
public class ExecuteButton extends JButton implements StateChangeListener, ActionListener, DocumentListener {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setEnabled(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setEnabled(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
