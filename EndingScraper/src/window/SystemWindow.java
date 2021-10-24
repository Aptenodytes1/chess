package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import baseComponents.EndingTypeField;
import baseComponents.ExecuteButton;

/**
 * 表示されるウィンドウを作成します。
 * 
 * @author Aptenodytes
 *
 */
public class SystemWindow extends JFrame {
	/**
	 * コンストラクタ
	 */
	public SystemWindow() {
		setContentPane();
		setTitle("system");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setVisible(true);
	}

	/**
	 * ウィンドウ内部を作成する。
	 */
	private void setContentPane() {
		// Components
		DropFileLabel dropArea = new DropFileLabel(400, 400);
		JButton buttonClear = new JButton("clear");
		ExecuteButton buttonExecute = new ExecuteButton("検索");
		ResultPanel resultPanel = new ResultPanel(400, 500, new JTextArea(""));
		EndingTypeField[] endingType = new EndingTypeField[5];
		for (int i = 0; i < endingType.length; i++) {
			endingType[i] = new EndingTypeField();
		}

		// Relate
		buttonClear.addActionListener(dropArea);
		buttonExecute.addActionListener(resultPanel);
		buttonExecute.setDataSource(dropArea);
		dropArea.addStateChangeListener(buttonExecute);
		resultPanel.setPathsDatasource(dropArea);
		for (EndingTypeField e : endingType) {
			resultPanel.setEndingTypeDatasource(e);
			e.getDocument().addDocumentListener(buttonExecute);
		}

		// Placer
		JPanel dropAreaPanel = new JPanel();
		{
			// ポイント2．「DropTargetListener」は「DropTarget」に設定します。
			// new DropTarget(コンポーネント,
			// リスナー);を実行すると、DropTargetの内部でaddDropTargetListenerされます。
			new DropTarget(this, (DropTargetListener) dropArea);
			dropAreaPanel.setPreferredSize(new Dimension(400, 450));
			dropAreaPanel.add(dropArea, BorderLayout.NORTH);
			dropAreaPanel.add(buttonClear, BorderLayout.SOUTH);
		}
		JPanel endingTypePanel = new JPanel();
		{
			endingTypePanel.setLayout(null);
			for (int i = 0; i < endingType.length; i++) {
				endingType[i].setBounds(10, 10 + 20 * i, 150, 20);
				endingTypePanel.add(endingType[i]);
			}
		}

		// Place
		Container contentPane = getContentPane();
		contentPane.add(dropAreaPanel, BorderLayout.WEST);
		contentPane.add(endingTypePanel);
		contentPane.add(buttonExecute, BorderLayout.SOUTH);
		contentPane.add(resultPanel, BorderLayout.EAST);

	}

}
