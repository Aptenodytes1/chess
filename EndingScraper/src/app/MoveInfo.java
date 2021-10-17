package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveInfo {
	/**
	 * 白番の手である場合はtrue, 黒番の手である場合はfalse
	 */
	private boolean isWhite = true;
	/**
	 * 動かした駒名
	 */
	private char piece;
	/**
	 * 駒を動かした先のマス目
	 */
	private String square;
	/**
	 * 駒を取る手である場合はtrue, それ以外はfalse
	 */
	private boolean isCapture = false;
	/**
	 * プロモーションする手である場合はtrue, それ以外はfalse
	 */
	private boolean isPromotion = false;
	/**
	 * プロモーション先の駒名
	 */
	private char promotionPiece = '-';

	/**
	 * コンストラクタ
	 * 
	 * @param move    手
	 * @param isWhite 白番であるか
	 */
	public MoveInfo(String move, boolean isWhite) {
		super();
		this.isWhite = isWhite;
		if (isCastle(move)) {
			this.piece = 'R';
			this.isCapture = false;
			this.isPromotion = false;

			String rookFile, rookRank;
			rookRank = isWhite ? "1" : "8";
			rookFile = isKingsideCastle(move) ? "f" : "d";

			this.square = rookFile + rookRank;
			this.promotionPiece = '-';
		} else {
			this.piece = getMovedPiece(move);
			this.square = getDestination(move);
			this.isCapture = isMoveCapture(move);
			this.isPromotion = isMovePromotion(move);

			this.promotionPiece = isPromotion ? getPromotionPiece(move) : '-';
		}
	}

	private boolean isMoveCapture(String move) {
		return move.contains("x");
	}

	private boolean isMovePromotion(String move) {
		return move.contains("=");
	}

	private boolean isCastle(String move) {
		return move.charAt(0) == 'O';
	}

	private boolean isKingsideCastle(String move) {
		return move.equals("O-O");
	}

	public boolean isEqualSquare(MoveInfo m) {
		return (this.square).equals(m.getSquare());
	}

	public boolean isWhiteMove() {
		return this.isWhite;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public char getPiece() {
		return piece;
	}

	public char getPromotionPiece() {
		return promotionPiece;
	}

	/**
	 * 動かした駒名を取得する。
	 * 
	 * @param move 手
	 * @return 駒名
	 */
	private char getMovedPiece(String move) {
		char c = move.charAt(0);
		if ('a' <= c && c <= 'h')
			return 'P';
		else
			return c;
	}

	/**
	 * インスタンスが保持するマスを取得する。
	 * 
	 * @return 座標
	 */
	public String getSquare() {
		return square;
	}

	public char getPromotionPiece(String move) {
		String patternResult = "(.*)=(.)(.*)";
		Pattern p = Pattern.compile(patternResult);
		return extract(move, p, 2).charAt(0);
	}

	public String getDestination(String move) {
		String patternDest = ".*([a-h][1-8]).*";
		Pattern p = Pattern.compile(patternDest);
		return extract(move, p, 1);
	}

	private String extract(String str, Pattern p, int group) {
		Matcher m = p.matcher(str);
		return m.matches() ? m.group(group) : null;
	}

	public String toString() {
		String color = (isWhite ? "W" : "B");
		String captureInfo = (isCapture ? ", x" : "");
		String promoteInfo = (isPromotion ? ", =" + promotionPiece : "");
		return "[" + color + ", " + piece + ", " + getSquare() + captureInfo + promoteInfo + "]";
	}

}
