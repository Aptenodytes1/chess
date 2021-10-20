package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import baseComponents.Exportable;

public class DropFilePanel extends JPanel implements DropTargetListener, Exportable, ActionListener {
	JLabel dropAreaLabel;
	List<String> paths = new ArrayList<String>();

	/**
	 * コンストラクタ
	 * 
	 * @param width  幅
	 * @param height 高さ
	 */
	public DropFilePanel(int width, int height) {
		dropAreaLabel = new JLabel("ドロップ待ちです。");
		dropAreaLabel.setForeground(Color.GRAY);
		dropAreaLabel.setOpaque(true);
		dropAreaLabel.setBackground(Color.WHITE);

		JScrollPane dropAreaScrollpane = new JScrollPane(dropAreaLabel);
		dropAreaScrollpane.setPreferredSize(new Dimension(width, height));

		this.add(dropAreaScrollpane);
	}

	/**
	 * 保存されているパスをクリアする 表示内容もクリア
	 */
	public void clear() {
		this.paths.clear();
		dropAreaLabel.setForeground(Color.GRAY);
		dropAreaLabel.setText("ドロップ待ちです。");
	}

	// ポイント1．ドロップイベントを受け取るには「DropTargetListener.drop」を実装します。
	@Override
	public void drop(DropTargetDropEvent dtde) {

		// ポイント3．ドロップすると1.で実装した「drop」が実行されます。
		// 「DropTargetDropEvent.acceptDrop」でドロップを受け取る準備をします。
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		boolean flg = false;
		String str = "<html><pre>";
		try {
			// 「DropTargetDropEvent.getTransferable」で転送クラスを取得します。
			Transferable tr = dtde.getTransferable();

			// 「DropTargetDropEvent.isDataFlavorSupported」で、受け取り可能なフレーバーを調べます。
			// 標準では文字列用の「stringFlavor」、ファイル用の「javaFileListFlavor」、画像イメージ用の「imageFlavor」があります。
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// ファイルをドロップされた場合
				// ドロップされたファイルを文字列に入れてラベルに表示します。
				// 「Transferable.getTransferData」でドロップされたオブジェクトを受け取ります。
				// ファイルは「List<File>」にキャストして操作するとよいです。
				List<File> list = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : list) {
					if (!paths.contains(file.getPath()))
						paths.add(file.getPath());
				}
				for (String path : paths) {
					str += path + "\n";
				}
				flg = true;
			}
			str += "</pre></html>";

		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 「DropTargetDropEvent.dropComplete」で転送完了を通知して終了です。
			dtde.dropComplete(flg);

			if (flg) {
				// ドロップされたオブジェクトをJLabelに設定します。
				dropAreaLabel.setText(str);
			} else {
				// ドロップを受け取れなかった場合はこちらで。
				dropAreaLabel.setText("ドロップを受け取りできませんでした。");
			}
		}

	}

	// drop以外のメソッドは今回使わないので実装しません。
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public Object export() {
		// TODO 自動生成されたメソッド・スタブ
		return paths;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		clear();
	}
}
