package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndingDetector {

	/**
	 * 試合結果を削除する
	 * 
	 * @param score 棋譜
	 * @return 削除対象を削除した棋譜
	 */
	private String removeResult(String score) {
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
	private String removeCheckAndMate(String score) {
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
	private String removeAnnotation(String score) {
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
	private String removeTurn(String score) {
		score = score.replaceAll("[0-9]+\\. ", "");
		score = score.replaceAll("[0-9]+\\.", "");
		return score;
	}

	/**
	 * 棋譜のエンドゲームが指定したエンディングタイプと適合しているか
	 * 
	 * @param score      棋譜
	 * @param endingType エンディングタイプ
	 * @return 適合している場合はtrue
	 */
	public boolean isEnding(String score, String endingType) {
		MoveInfo[] moves = scoreToMoves(score);
		MoveInfo[] whiteMoves = getWBMoves(moves)[0];
		MoveInfo[] blackMoves = getWBMoves(moves)[1];
		Pieces p = new Pieces();

		if (!p.isValidEndingType(endingType)) {
			System.out.println("Invalid endgameType.");
			return false;
		}

		for (int ply = 0; ply < moves.length; ply++) {
			MoveInfo m = moves[ply];
			boolean isWhiteTurn = (ply % 2 == 0) ? true : false;
			int turn = ply / 2;
			int opponentsLastTurn = isWhiteTurn ? turn - 1 : turn;
			MoveInfo[] opponentsMoves = isWhiteTurn ? blackMoves : whiteMoves;

			if (m.isPromotion()) {
				p.promote(m);
			}

			boolean pieceIsMovedInGame = false;
			if (m.isCapture()) {
				for (int i = opponentsLastTurn; i >= 0; i--) {
					MoveInfo checkMove = opponentsMoves[i];
					if (m.isEqualSquare(checkMove)) {
						p.remove(checkMove);
						pieceIsMovedInGame = true;
						break;
					}
				}
				if (!pieceIsMovedInGame) {
					p.removePieceOnDefaultSquare(m.getSquare());
				}
			}

			if (p.isEnding(endingType))
				return true;
		}
		return false;
	}

	/**
	 * 手の配列を白の手と黒の手に振り分ける
	 * 
	 * @param moves 手の配列
	 * @return [0]:白の手の配列 [1]:黒の手の配列
	 */
	private MoveInfo[][] getWBMoves(MoveInfo[] moves) {
		int ply = moves.length;
		int whitePly = (ply + 1) / 2;
		MoveInfo[][] WBMoves = new MoveInfo[2][whitePly];

		for (int i = 0; i < ply; i++) {
			if (moves[i].isWhiteMove())
				WBMoves[0][i / 2] = moves[i];
			else
				WBMoves[1][i / 2] = moves[i];
		}
		return WBMoves;
	}

	/**
	 * 棋譜を手(MoveInfo型)の配列に変換する
	 * 
	 * @param score 棋譜
	 * @return 手の配列
	 */
	private MoveInfo[] scoreToMoves(String score) {
		String s = removeResult(score);
		s = removeAnnotation(s);
		s = removeCheckAndMate(s);
		s = removeTurn(s);

		String[] sections = s.split(" ");
		MoveInfo[] moves = new MoveInfo[sections.length];
		for (int i = 0; i < sections.length; i++) {
			boolean whiteTurn = (i % 2 == 0);
			moves[i] = new MoveInfo(sections[i], whiteTurn);
		}
		return moves;
	}
}
