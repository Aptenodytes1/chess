package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChessUtil {
	/**
	 * 試合結果を削除する
	 * 
	 * @param score 棋譜
	 * @return 削除対象を削除した棋譜
	 */
	public static String removeResult(String score) {
		String patternResult = "(.+) (1-0|0-1|1/2-1/2|\\*)";
		Pattern p = Pattern.compile(patternResult);
		Matcher matcher = p.matcher(score);
		return matcher.replaceAll("$1");
	}

	/**
	 * +と#をの記載を削除する
	 * 
	 * @param score 棋譜
	 * @return 削除対象を削除した棋譜
	 */
	public static String removeCheckAndMate(String score) {
		score = score.replaceAll("\\+", "");
		score = score.replaceAll("#", "");
		return score;
	}

	/**
	 * !と?を削除する
	 * 
	 * @param score 棋譜
	 * @return 削除対象を削除した棋譜
	 */
	public static String removeAnnotation(String score) {
		score = score.replaceAll("!", "");
		score = score.replaceAll("\\?", "");
		return score;
	}

	/**
	 * 手番表記を削除する
	 * 
	 * @param score 棋譜
	 * @return 削除対象を削除した棋譜
	 */
	public static String removeTurn(String score) {
		score = score.replaceAll("[0-9]+\\. ", "");
		score = score.replaceAll("[0-9]+\\.", "");
		return score;
	}

	/**
	 * char引数がファイルとして適切であるかを判定する
	 * 
	 * @param file ファイル
	 * @return ファイルとして適切であればtrue
	 */
	public static boolean isValidFile(char file) {
		return 'a' <= file && file <= 'h';
	}

	/**
	 * char引数がランクとして適切であるかを判定する
	 * 
	 * @param rank ランク
	 * @return ランクとして適切であればtrue
	 */
	public static boolean isValidRank(char rank) {
		return '1' <= rank && rank <= '8';
	}
}
