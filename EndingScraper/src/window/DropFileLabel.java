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

import baseComponents.Exportable;
import baseComponents.StateWatchingJLabel;

/**
 * ファイルをドロップし、ドロップされたファイルパスを表示するラベルを作成します。
 * 
 * @author Aptenodytes
 *
 */
public class DropFileLabel extends StateWatchingJLabel implements DropTargetListener, Exportable, ActionListener {
	/**
	 * ファイルパス
	 */
	List<String> paths = new ArrayList<String>();

	/**
	 * コンストラクタ
	 * 
	 * @param width  幅
	 * @param height 高さ
	 */
	public DropFileLabel(int width, int height) {
		super("ドロップ待ちです。");
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * 保存されているパスと表示内容をクリアする
	 */
	private void clear() {
		this.paths.clear();
		this.setForeground(Color.GRAY);
		this.setText("ドロップ待ちです。");
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
					if (!paths.contains(file.getPath()) && file.getPath().endsWith(".pgn"))
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
				this.setText(str);
			} else {
				// ドロップを受け取れなかった場合はこちらで。
				this.setText("ドロップを受け取りできませんでした。");
			}
		}

	}

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object export() {
		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		clear();
	}
}
