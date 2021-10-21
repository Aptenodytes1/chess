package baseComponents;

/**
 * 状態変更イベントを受け取るためのリスナー・インタフェースです。 状態変更イベントの処理に関連するクラスは、このインタフェースを実装します。
 * さらに、そうしたクラスによって作成されたオブジェクトは、コンポーネントのaddStateChangeListenerメソッドを使用することによってコンポーネントに登録されます。
 * 状態変更イベントが発生すると、オブジェクトのstateChangedメソッドが呼び出されます。
 * 
 * @author Aptenodytes
 *
 */
public interface StateChangeListener {
	/**
	 * 内容が更新された場合実行する。
	 * 
	 * @param e イベント
	 */
	public void stateChanged(StateChangeEvent e);
}
