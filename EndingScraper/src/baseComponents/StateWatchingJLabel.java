package baseComponents;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

/**
 * 保持内容を変更した際に、登録されたStateChangeListenerのstateChangedメソッドを実行することができるJLabelです。
 * 
 * @author Aptenodytes
 */
public class StateWatchingJLabel extends JLabel {
	/**
	 * リスナーオブジェクト
	 */
	List<StateChangeListener> listener = new ArrayList<StateChangeListener>();

	/**
	 * コンストラクタ
	 * 
	 * @param value 表示文字列
	 */
	public StateWatchingJLabel(String value) {
		super(value);
	}

	/**
	 * {@inheritDoc} 直前の保持内容から内容が更新される場合、リスナーオブジェクトのstateChangedメソッドを実行する。
	 */
	@Override
	public void setText(String newText) {
		String formerText = this.getText();
		super.setText(newText);
		if (!newText.equals(formerText)) {
			if (listener != null) {
				for (StateChangeListener l : listener) {
					l.stateChanged(new StateChangeEvent(this, StateChangeEvent.STATE_CHANGED));
				}
			}
		}
	}

	/**
	 * StateChangeListenerを追加する。
	 * 
	 * @param l リスナーオブジェクト
	 */
	public void addStateChangeListener(StateChangeListener l) {
		this.listener.add(l);
	}
}
