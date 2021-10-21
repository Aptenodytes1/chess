package baseComponents;

import java.awt.AWTEvent;

/**
 * 状態が変更された場合に発報されるイベントです。
 * 
 * @author Aptenodytes
 *
 */
public class StateChangeEvent extends AWTEvent {
	/**
	 * 状態が初期値である場合を表す定数
	 */
	public static final int STATE_DEFAULT = 0;
	/**
	 * 状態が変更された場合を表す定数
	 */
	public static final int STATE_CHANGED = 1;
	/**
	 * 状態が変更されていない場合を表す定数
	 */
	public static final int STATE_NOTCHANGED = 2;

	/**
	 * コンストラクタ
	 * 
	 * @param source イベント発生元オブジェクト
	 * @param id     ID
	 */
	public StateChangeEvent(Object source, int id) {
		super(source, id);
	}

}
