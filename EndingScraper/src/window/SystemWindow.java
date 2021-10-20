package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import baseComponents.ExecuteButton;

public class SystemWindow extends JFrame {
	public SystemWindow() {
		Container contentPane = getContentPane();
		// ポイント2．「DropTargetListener」は「DropTarget」に設定します。
		// new DropTarget(コンポーネント,
		// リスナー);を実行すると、DropTargetの内部でaddDropTargetListenerされます。
		DropFilePanel dropArea = new DropFilePanel(400, 400);
		new DropTarget(this, (DropTargetListener) dropArea);
		ExecuteButton buttonClear = new ExecuteButton("clear");
		JPanel dropAreaPanel = new JPanel();
		dropAreaPanel.add(dropArea, BorderLayout.NORTH);
		dropAreaPanel.add(buttonClear, BorderLayout.SOUTH);

		ExecuteButton buttonExecute = new ExecuteButton("execute");
		ResultPanel resultPanel = new ResultPanel(400, 500);

		buttonClear.addActionListener(dropArea);
		buttonExecute.addActionListener(resultPanel);
		resultPanel.setDatasource(dropArea);

		setTitle("system");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setVisible(true);

		contentPane.add(dropAreaPanel, BorderLayout.WEST);
//		contentPane.add(buttonClear, BorderLayout.WEST);
		contentPane.add(buttonExecute, BorderLayout.SOUTH);
		contentPane.add(resultPanel, BorderLayout.EAST);
	}

}
