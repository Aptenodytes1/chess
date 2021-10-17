package app;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String[] path1 = { "F:\\Downloads\\Blitz_2019-03-30_2020-03-29.pgn", // [0]
				"F:\\Downloads\\Blitz_2020-03-30_2020-04-29.pgn", "F:\\Downloads\\Blitz_2020-04-30_2020-05-29.pgn",
				"F:\\Downloads\\Blitz_2020-05-30_2020-06-29.pgn", "F:\\Downloads\\Blitz_2020-06-30_2020-07-29.pgn",
				"F:\\Downloads\\Blitz_2020-07-30_2020-08-29.pgn", // [5]
				"F:\\Downloads\\Blitz_2020-08-30_2020-09-29.pgn", "F:\\Downloads\\Blitz_2020-09-30_2020-10-29.pgn",
				"F:\\Downloads\\Blitz_2020-10-30_2020-11-29.pgn", "F:\\Downloads\\Blitz_2020-11-30_2020-12-29.pgn",
				"F:\\Downloads\\Blitz_2020-12-30_2021-01-29.pgn", // [10]
				"F:\\Downloads\\Blitz_2021-01-30_2021-02-29.pgn", "F:\\Downloads\\Blitz_2021-02-30_2021-03-29.pgn" // [12]
		};
		String[] path2 = { "F:\\Downloads\\Blitz_2019-03-30_2020-03-29.pgn" };
		String[] path = path1;

		String[] endingType = { "BB", "OB", "SB", "Q", "R", "N", "BN" };
//		String[] endingType = { "BB" };

		GameInfo game;
		EndingDetector ed = new EndingDetector();

		for (int i = 0; i < endingType.length; i++) {
			System.out.println(endingType[i] + " ending games:");
			for (int j = 0; j < path.length; j++) {
				GamePusher pusher = new GamePusher(path[j]);

				try {
					do {
						game = pusher.pushGame();
						if (game != null) {
							boolean b = ed.isEnding(game.getScore(), endingType[i]);
							if (b) {
								System.out.println(game.getSite());
							}
						}
					} while (game != null);

				} catch (FileNotFoundException e) {
					System.out.println("File not found.");
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
			System.out.println();
		}
		System.out.println("finish.");
	}
}