package app;

/**
 * 手の情報を保持するvalue object
 * 
 * @author Aptenodytes
 *
 */
public class MoveInfo {
	/**
	 * 手を表した棋譜
	 */
	private String move;

	/**
	 * 白番の手である場合はtrue, 黒番の手である場合はfalse
	 */
	private boolean isWhite = true;
	/**
	 * 動かした駒名 PNBRQKのいずれか
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
	 * プロモーション先の駒名 NBRQのいずれか
	 */
	private char promotionPiece = '-';
	/**
	 * アンパッサンである場合はtrue, それ以外はfalse
	 */
	private boolean isEnPassant = false;

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}

	public String getSquare() {
		return square;
	}

	public void setSquare(String square) {
		this.square = square;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public void setCapture(boolean isCapture) {
		this.isCapture = isCapture;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public char getPromotionPiece() {
		return promotionPiece;
	}

	public void setPromotionPiece(char promotionPiece) {
		this.promotionPiece = promotionPiece;
	}

	public boolean isEnPassant() {
		return isEnPassant;
	}

	public void setEnPassant(boolean isEnPassant) {
		this.isEnPassant = isEnPassant;
	}

}
