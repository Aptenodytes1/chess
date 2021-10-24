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
		GameInfo game = new GameInfo();

		for (line = ""; !gdm.matchScore(line);) {
			line = br.readLine();
			if (line == null) {
				return null;
			}

			if (gdm.matchSite(line)) {
				game.setSite(gdm.extractSite(line));
			} else if (gdm.matchScore(line)) {
				game.setScore(line);
//			} else if (gdm.matchEvent(line)) {
//				game.setEvent(gdm.extractEvent(line));
//			} else if (gdm.matchDate(line)) {
//				game.setDate(gdm.extractDate(line));
//			} else if (gdm.matchWhite(line)) {
//				game.setWhitePlayer(gdm.extractWhite(line));
//			} else if (gdm.matchBlack(line)) {
//				game.setBlackPlayer(gdm.extractBlack(line));
//			} else if (gdm.matchResult(line)) {
//				game.setResult(gdm.extractResult(line));
//			} else if (gdm.matchWhiteElo(line)) {
//				game.setWhiteRating(gdm.extractWhiteElo(line));
//			} else if (gdm.matchBlackElo(line)) {
//				game.setBlackRating(gdm.extractBlackElo(line));
//			} else if (gdm.matchEco(line)) {
//				game.setOpening(gdm.extractEco(line));
			}
		}
		if (line == null) {
			br.close();
		}

		return game;
	}
}