package app;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String[] path = { "F:\\Downloads\\Blitz_2019-03-30_2020-03-29.pgn", // [0]
				"F:\\Downloads\\Blitz_2020-03-30_2020-04-29.pgn", "F:\\Downloads\\Blitz_2020-04-30_2020-05-29.pgn",
				"F:\\Downloads\\Blitz_2020-05-30_2020-06-29.pgn", "F:\\Downloads\\Blitz_2020-06-30_2020-07-29.pgn",
				"F:\\Downloads\\Blitz_2020-07-30_2020-08-29.pgn", // [5]
				"F:\\Downloads\\Blitz_2020-08-30_2020-09-29.pgn", "F:\\Downloads\\Blitz_2020-09-30_2020-10-29.pgn",
				"F:\\Downloads\\Blitz_2020-10-30_2020-11-29.pgn", "F:\\Downloads\\Blitz_2020-11-30_2020-12-29.pgn",
				"F:\\Downloads\\Blitz_2020-12-30_2021-01-29.pgn", // [10]
				"F:\\Downloads\\Blitz_2021-01-30_2021-02-29.pgn", "F:\\Downloads\\Blitz_2021-02-30_2021-03-29.pgn" // [12]
		};

		GamePusher pusher = new GamePusher(path[2]);
		GameInfo game;
		EndingDetector ed = new EndingDetector();
		String endingType = "BB";

		try {
			do {
				game = pusher.pushGame();
//				System.out.println(game);
				if (game != null) {
//					System.out.println(game.getScore());
					boolean b = ed.isEnding_noComment(game.getScore(), endingType);
					if (b) {
						System.out.println(game.getSite());
					}
				}
			} while (game != null);
			System.out.println("finish.");

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}