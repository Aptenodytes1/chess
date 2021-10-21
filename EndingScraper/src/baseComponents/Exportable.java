package baseComponents;

/**
 * 保持データをエクスポート可能なオブジェクトが持つインターフェイスです。
 * 
 * @author Aptenodytes
 *
 */
public interface Exportable {
	/**
	 * データをエクスポートする。
	 * 
	 * @return データオブジェクト
	 */
	public Object export();
}
