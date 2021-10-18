package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GamePusher {
	BufferedReader br;

	/**
	 * コンストラクタ
	 * 
	 * @param path ファイルパス
	 * @throws FileNotFoundException 指定のファイルが見つからない場合
	 */
	public GamePusher(String path) throws FileNotFoundException {
		FileReader fileReader = new FileReader(new File(path));
		br = new BufferedReader(fileReader);
	}

	/**
	 * ファイルに記録されたゲームを取得する ファイルの最後まで読み込んだ場合、ファイルストリームをクローズする
	 * 
	 * @return ゲーム情報
	 * @throws IOException ファイル読み込みでエラーが発生した場合
	 */
	@SuppressWarnings("unused")
	public GameInfo pushGame() throws IOException {
		String line = "";
		GameDataMatcher gdm = new GameDataMatcher();
		GameInfo game = new GameInfo("", "");

		for (line = ""; !gdm.matchScore(line);) {
			line = br.readLine();
			if (line == null) {
				return null;
			}

			if (gdm.matchSite(line)) {
				game.setSite(gdm.extractSite(line));
			} else if (gdm.matchScore(line)) {
				game.setScore(line);
			}
		}
		if (line == null) {
			br.close();
		}

		return game;
	}
}