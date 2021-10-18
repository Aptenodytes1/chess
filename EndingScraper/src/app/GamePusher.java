package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GamePusher {
	private String path;
	// Charset
	Charset charset = StandardCharsets.UTF_8;
	// 読み込んだ前回位置
	long pointer = 0;
	// 読み込んだ最終位置
	long nextReturn;
	RandomAccessFile reader = null;
	int lineNumber = 0;
	GameDataMatcher gdm = new GameDataMatcher();

	public GamePusher(String path) {
		this.path = path;
	}

	public GameInfo pushGame() throws FileNotFoundException, IOException {
		String line = "";
		GameInfo game = new GameInfo("", "");
		try {
			reader = new RandomAccessFile(new File(path), "r");

			// 初回でない場合は前回読み込んだところを探す
			if (pointer != 0) {
				reader.seek(pointer);
			}

			while (!gdm.matchScore(line)) {
				line = reader.readLine();
				// 読み込んだ位置を記録する
				nextReturn = reader.getFilePointer();
				if (line == null) {
					return null;
				}

				// 前回読み込んだ位置に戻り、今回の読み取り範囲をbyte配列で取得する(任意の文字コードで文字列に変換するため)
				{
					reader.seek(pointer);
					byte[] bytes = new byte[(int) (nextReturn - pointer)];
					reader.read(bytes);
					pointer = reader.getFilePointer();
					line = new String(bytes, charset);
					// 末尾の改行コードを除去
					while (line.endsWith("\r") || line.endsWith("\n")) {
						line = line.substring(0, line.length() - 1);
					}
				}
				if (gdm.matchSite(line)) {
					game.setSite(gdm.extractSite(line));
				} else if (gdm.matchScore(line)) {
					game.setScore(line);
				}

				++lineNumber;
			}
			reader.close();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return game;
	}
}