package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveParser {
	/**
	 * 手の文字列をオブジェクトに変換する
	 * 
	 * @param move    手
	 * @param isWhite 白番であるか
	 * @return 手の情報オブジェクト
	 */
	public MoveInfo getMoveInfo(String move, boolean isWhite, MoveInfo lastMove) {
		MoveInfo m = new MoveInfo();
		m.setMove(move);
		m.setWhite(isWhite);
		m.setPiece(getMovedPiece(move));
		m.setSquare(getDestination(move, isWhite));
		m.setCapture(isMoveCapture(move));
		m.setPromotion(isMovePromotion(move));
		m.setPromotionPiece(getPromotionPiece(move));
		m.setEnPassant(isEnPassant(move, lastMove));
		return m;
	}

	/**
	 * 動かした駒名を取得する。
	 * 
	 * @param move 手
	 * @return 駒名
	 */
	public char getMovedPiece(String move) {
		if (move.equals("O-O") || move.equals("O-O-O"))
			return 'R';

		char c = move.charAt(0);
		if ('a' <= c && c <= 'h')
			return 'P';
		else
			return c;
	}

	/**
	 * 行き先のマスを取得する 取得できなかった場合は空文字列を返す
	 * 
	 * @param move 手
	 * @return 行き先のマス
	 */
	public String getDestination(String move, boolean isWhite) {
		String value = "";
		if (move.equals("O-O")) {
			String rookRank = isWhite ? "1" : "8";
			value = "f" + rookRank;
		} else if (move.equals("O-O-O")) {
			String rookRank = isWhite ? "1" : "8";
			value = "d" + rookRank;
		} else {
			for (int i = move.length() - 1; i >= 0; i--) {
				if (ChessUtil.isValidRank(move.charAt(i))) {
					value = move.substring(i - 1, i + 1);
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 手が駒取りであるか
	 * 
	 * @param move 手
	 * @return 駒取りである場合はtrue
	 */
	public boolean isMoveCapture(String move) {
		return move.contains("x");
	}

	/**
	 * 手がプロモーションであるか
	 * 
	 * @param move 手
	 * @return プロモーションである場合はtrue
	 */
	public boolean isMovePromotion(String move) {
		return move.contains("=");
	}

	/**
	 * プロモーションした先の駒を取得する
	 * 
	 * @param move 手
	 * @return プロモーションした先の駒名
	 */
	public char getPromotionPiece(String move) {
		if (!move.contains("="))
			return '-';
		String patternResult = "(.*)=(.)(.*)";
		Pattern p = Pattern.compile(patternResult);
		return extract(move, p, 2).charAt(0);
	}

	/**
	 * ポーンの手であるか
	 * 
	 * @param move 手
	 * @return ポーンの手である場合はtrue
	 */
	private boolean isPawnMove(String move) {
		char c = move.charAt(0);
		return 'a' <= c && c <= 'h';
	}

	/**
	 * 手がアンパッサンであるか
	 * 
	 * @param move     手
	 * @param lastMove 一手前の手
	 * @return アンパッサンである場合はtrue
	 */
	public boolean isEnPassant(String move, MoveInfo lastMove) {
		if (lastMove == null)
			return false;
		if (!isPawnMove(move) || !isMoveCapture(move) || lastMove.isCapture())
			return false;
		if (lastMove.getPiece() != 'P' && lastMove.getPiece() != 'p')
			return false;

		String value = "";
		String lmSquare = lastMove.getSquare();
		char pushedRank = lastMove.isWhite() ? '4' : '5';
		char captureRank = lastMove.isWhite() ? '3' : '6';
		// 行き先のマスを取得
		for (int i = move.length() - 1; i >= 0; i--) {
			if (ChessUtil.isValidRank(move.charAt(i))) {
				value = move.substring(i - 1, i + 1);
				break;
			}
		}

		if (lmSquare.charAt(1) != pushedRank)
			return false;
		else if (value.charAt(0) != lmSquare.charAt(0))
			return false;
		else
			return (move.charAt(3) == captureRank);
	}

	/**
	 * 正規表現で合致する文字列を取得する
	 * 
	 * @param str   文字列
	 * @param p     検索パターン
	 * @param group 抜き出し箇所
	 * @return 抜き出した文字列
	 */
	private String extract(String str, Pattern p, int group) {
		Matcher m = p.matcher(str);
		return m.matches() ? m.group(group) : null;
	}
}
