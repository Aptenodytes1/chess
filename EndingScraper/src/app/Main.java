package app;

import java.io.FileNotFoundException;
import java.io.IOException;

import window.SystemWindow;

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

		String[] endingType1 = { "BB", "OB", "SB", "Q", "R", "N", "BN" };
		String[] endingType2 = { "BB" };
		String[] endingType = endingType1;

		GameInfo game;
		EndingDetector ed = new EndingDetector();

		SystemWindow window = new SystemWindow();
//		DropAndListFileName_scroll2 window2 = new DropAndListFileName_scroll2();
//		JScrollPaneTest4 frame = new JScrollPaneTest4();
//		frame.method();

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < endingType.length; i++) {
			System.out.println(endingType[i] + " ending games:");
			for (int j = 0; j < path.length; j++) {
				GamePusher pusher = null;
				try {
					pusher = new GamePusher(path[j]);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found.");
					break;
				}

				try {
					do {
						game = pusher.pushGame();
						if (game != null) {
							int ply = ed.getEndingPly(game.getScore(), endingType[i]);
							if (ply != 0) {
								System.out.println(game.getSite());
//								System.out.println(game.getSite() + "#" + ply);
							}
						}
					} while (game != null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("開始時刻：" + startTime + " ms");
		System.out.println("終了時刻：" + endTime + " ms");
		System.out.println("処理時間：" + (endTime - startTime) + " ms");
		System.out.println("finish.");
	}
}