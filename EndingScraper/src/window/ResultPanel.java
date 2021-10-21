package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.EndingDetector;
import app.GameInfo;
import app.GamePusher;
import baseComponents.Exportable;

/**
 * 検索結果の表示部分を作成します。
 * 
 * @author Aptenodytes
 *
 */
public class ResultPanel extends JScrollPane implements ActionListener, Exportable {
	JTextArea outputText;
	Exportable pathsSource;
	List<Exportable> endingTypeSource = new ArrayList<Exportable>();

	/**
	 * コンストラクタ
	 * 
	 * @param width      幅
	 * @param height     高さ
	 * @param outputText JTextAreaインスタンス
	 */
	public ResultPanel(int width, int height, JTextArea outputText) {
		super(outputText);
		this.outputText = outputText;

		this.setPreferredSize(new Dimension(width, height));
		this.outputText.setOpaque(true);
		this.outputText.setBackground(Color.WHITE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> paths = (List<String>) pathsSource.export();
		String endingType = "R";

		outputText.setText("");
		if (!paths.isEmpty()) {
			showResult(outputText, paths, endingType);
		} else
			outputText.setText("No file selected.\n");
	}

	/**
	 * 検索結果を画面に出力する。
	 * 
	 * @param outputText 出力先コンポーネントオブジェクト
	 * @param paths      検索対象ファイルパス
	 * @param endingType エンディングタイプ
	 */
	private void showResult(JTextArea outputText, List<String> paths, String endingType) {
		GameInfo game;
		EndingDetector ed = new EndingDetector();

		outputText.append(endingType + " ending games:\n");
		for (String path : paths) {
			try {
				GamePusher pusher = new GamePusher(path);
				do {
					game = pusher.pushGame();
					if (game != null) {
						int ply = ed.getEndingPly(game.getScore(), endingType);
						if (ply != 0) {
							outputText.append(game.getSite() + "#" + ply + "\n");
						}
					}
				} while (game != null);
			} catch (FileNotFoundException e1) {
				outputText.append("File not found.\n");
			} catch (IOException e1) {
				outputText.append("IOException.\n");
			} catch (NullPointerException e1) {
				outputText.append("NullPointerException.\n");
			} catch (Exception e1) {
				outputText.append("Exception.\n");
			}
		}
	}

	/**
	 * データソースに表示されているエンディングタイプを取得する。
	 * 
	 * @param source データソース
	 * @return エンディングタイプのリスト
	 */
	private List<String> getEndingType(List<Exportable> source) {
		// TODO implement
		return null;
	}

	/**
	 * パスのデータソースを設定する。
	 * 
	 * @param source データソース
	 */
	public void setPathsDatasource(Exportable source) {
		if (source instanceof DropFileLabel)
			this.pathsSource = source;
	}

	/**
	 * エンディングタイプのデータソースを設定する。
	 * 
	 * @param source データソース
	 */
	public void setEndingTypeDatasource(Exportable source) {
		if (source instanceof DropFileLabel)
			this.endingTypeSource.add(source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object export() {
		return outputText;
	}
}
