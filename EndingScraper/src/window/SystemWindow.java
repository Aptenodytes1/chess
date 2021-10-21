package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
		ExecuteButton buttonExecute = new ExecuteButton("execute");
		ResultPanel resultPanel = new ResultPanel(400, 500, new JTextArea(""));

		// Relate
		buttonClear.addActionListener(dropArea);
		buttonExecute.addActionListener(resultPanel);
		buttonExecute.setDataSource(dropArea);
		dropArea.addStateChangeListener(buttonExecute);
		resultPanel.setPathsDatasource(dropArea);

		// Placer
		JPanel dropAreaPanel = new JPanel();
		{
			// ポイント2．「DropTargetListener」は「DropTarget」に設定します。
			// new DropTarget(コンポーネント,
			// リスナー);を実行すると、DropTargetの内部でaddDropTargetListenerされます。
			new DropTarget(this, (DropTargetListener) dropArea);
			dropAreaPanel.add(dropArea, BorderLayout.NORTH);
			dropAreaPanel.add(buttonClear, BorderLayout.SOUTH);
		}

		// Place
		Container contentPane = getContentPane();
		contentPane.add(dropAreaPanel, BorderLayout.WEST);
		contentPane.add(buttonExecute, BorderLayout.SOUTH);
		contentPane.add(resultPanel, BorderLayout.EAST);

	}

}
