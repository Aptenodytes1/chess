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

	/**
	 * 手が駒取りであるか
	 * 
	 * @param move 手
	 * @return 駒取りである場合はtrue
	 */
	private boolean isMoveCapture(String move) {
		return move.contains("x");
	}

	/**
	 * 手がプロモーションであるか
	 * 
	 * @param move 手
	 * @return プロモーションである場合はtrue
	 */
	private boolean isMovePromotion(String move) {
		return move.contains("=");
	}

	/**
	 * 手がキャスリングであるか
	 * 
	 * @param move 手
	 * @return キャスリングである場合はtrue
	 */
	private boolean isCastle(String move) {
		return move.charAt(0) == 'O';
	}

	/**
	 * 手がO-Oであるか
	 * 
	 * @param move 手
	 * @return O-Oである場合はtrue
	 */
	private boolean isKingsideCastle(String move) {
		return move.equals("O-O");
	}

	/**
	 * 比較先の手と同じマスに移動しているか
	 * 
	 * @param m 比較する手
	 * @return 同じマスに移動している場合はtrue
	 */
	public boolean isEqualSquare(MoveInfo m) {
		return (this.square).equals(m.getSquare());
	}

	/**
	 * インスタンスが保存している手が白番であるか
	 * 
	 * @return 白番である場合はtrue
	 */
	public boolean isWhiteMove() {
		return this.isWhite;
	}

	/**
	 * インスタンスが保存している手が駒取りであるか
	 * 
	 * @return 駒取りである場合はtrue
	 */
	public boolean isCapture() {
		return isCapture;
	}

	/**
	 * インスタンスが保存している手がプロモーションであるか
	 * 
	 * @return プロモーションである場合はtrue
	 */
	public boolean isPromotion() {
		return isPromotion;
	}

	/**
	 * インスタンスが保存している駒名を取得する
	 * 
	 * @return 駒名
	 */
	public char getPiece() {
		return piece;
	}

	/**
	 * インスタンスが保存しているプロモーション先の駒名を取得する
	 * 
	 * @return プロモーション先の駒
	 */
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

	/**
	 * プロモーションした先の駒を取得する
	 * 
	 * @param move 手
	 * @return プロモーションした先の駒名
	 */
	private char getPromotionPiece(String move) {
		String patternResult = "(.*)=(.)(.*)";
		Pattern p = Pattern.compile(patternResult);
		return extract(move, p, 2).charAt(0);
	}

	/**
	 * 行き先のマスを取得する
	 * 
	 * @param move 手
	 * @return 行き先のマス
	 */
	private String getDestination(String move) {
		String patternDest = ".*([a-h][1-8]).*";
		Pattern p = Pattern.compile(patternDest);
		return extract(move, p, 1);
	}

	/**
	 * @param str
	 * @param p
	 * @param group
	 * @return
	 */
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
