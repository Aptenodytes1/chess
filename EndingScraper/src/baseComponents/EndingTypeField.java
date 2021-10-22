package baseComponents;

import javax.swing.JTextField;

/**
 * エンディングタイプを保持するテキストフィールドを作成します。
 * EndingTypeField.getDocument().addDocumentListener(DocumentListener);を実行してリスナーを登録し、
 * 入力内容に更新がある場合、実行ボタンに通知します。
 * 
 * @author Aptenodytes
 *
 */
public class EndingTypeField extends JTextField implements Exportable {

	/**
	 * コンストラクタ
	 */
	public EndingTypeField() {
		super(5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object export() {
		return this.getText();
	}
}
