package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.EndingDetector;
import app.GameInfo;
import app.GamePusher;
import baseComponents.Exportable;

public class ResultPanel extends JPanel implements ActionListener {
	JTextArea outputText = new JTextArea("");
	Exportable source;

	/**
	 * コンストラクタ
	 * 
	 * @param width  幅
	 * @param height 高さ
	 */
	public ResultPanel(int width, int height) {
		outputText.setOpaque(true);
		outputText.setBackground(Color.WHITE);

		JScrollPane dropAreaScrollpane = new JScrollPane(outputText);
		dropAreaScrollpane.setPreferredSize(new Dimension(width, height));

		this.add(dropAreaScrollpane);
	}

	/**
	 * 検索結果を出力する
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		GameInfo game;
		EndingDetector ed = new EndingDetector();
		List<String> paths = (List<String>) source.export();
		// TODO 自動生成されたメソッド・スタブ
		outputText.setText("clicked\n");
		if (!paths.isEmpty()) {
			outputText.append("BB ending games:\n");
			for (String path : paths) {
				GamePusher pusher = null;
				try {
					pusher = new GamePusher(path);

					do {
						game = pusher.pushGame();
						if (game != null) {
							int ply = ed.getEndingPly(game.getScore(), "BB");
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
	}

	public void setDatasource(Exportable source) {
		this.source = source;
	}
}
