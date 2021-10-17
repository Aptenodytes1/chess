package app;

public class Pieces {
	public static final String P = "P";
	public static final String N = "N";
	public static final String B = "B";
	public static final String R = "R";
	public static final String Q = "Q";
	public static final String K = "K";
	public static final String DOUBLEB = "BB";
	public static final String OB = "OB";
	public static final String SB = "SB";
	public static final char WP = 'P';
	public static final char WN = 'N';
	public static final char WB = 'B';
	public static final char WR = 'R';
	public static final char WQ = 'Q';
	public static final char WK = 'K';
	public static final char BP = 'p';
	public static final char BN = 'n';
	public static final char BB = 'b';
	public static final char BR = 'r';
	public static final char BQ = 'q';
	public static final char BK = 'k';
	public static final char EMPTY = '-';
	private static final char[] EMPTYRANK = { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY };

	enum PiecesNum {
		WP(8, Pieces.WP, "WP"), WN(2, Pieces.WN, "WN"), WRB(1, Pieces.WB, "WRB"), WDB(1, Pieces.WB, "WDB"),
		WR(2, Pieces.WR, "WR"), WQ(1, Pieces.WQ, "WQ"), WK(1, Pieces.WK, "WK"), BP(8, Pieces.BP, "BP"),
		BN(2, Pieces.BN, "BN"), BRB(1, Pieces.BB, "BRB"), BDB(1, Pieces.BB, "BDB"), BR(2, Pieces.BR, "BR"),
		BQ(1, Pieces.BQ, "BQ"), BK(1, Pieces.BK, "BK");

		private int num;
		private char nameChar;
		private String nameString;

		private PiecesNum(int num, char nameChar, String nameString) {
			this.num = num;
			this.nameChar = nameChar;
			this.nameString = nameString;
		}

		void remove() {
			num--;
		}

		void generate() {
			num++;
		}

		void clear() {
			num = 0;
		}

		boolean exists(int n) {
			return num == n;
		}

		boolean exists() {
			return num > 0;
		}
	};

	/**
	 * コンストラクタ
	 */
	public Pieces() {
		setDefaultPiece();
	}

	/* ピースの初期配置 */
	private char[][] defaultPiecePlace = { { WR, WN, WB, WQ, WK, WB, WN, WR }, { WP, WP, WP, WP, WP, WP, WP, WP },
			EMPTYRANK, EMPTYRANK, EMPTYRANK, EMPTYRANK, { BP, BP, BP, BP, BP, BP, BP, BP },
			{ BR, BN, BB, BQ, BK, BB, BN, BR } };

	/**
	 * スタンダードの駒の初期配置と個数を設定する
	 */
	public void setDefaultPiece() {
		String rank1 = new String(new char[] { WR, WN, WB, WQ, WK, WB, WN, WR });
		String rank2 = new String(new char[] { WP, WP, WP, WP, WP, WP, WP, WP });
		String rankEmpty = new String(EMPTYRANK);
		String rank7 = new String(new char[] { BP, BP, BP, BP, BP, BP, BP, BP });
		String rank8 = new String(new char[] { BR, BN, BB, BQ, BK, BB, BN, BR });

		String pieces[] = { rank1, rank2, rankEmpty, rankEmpty, rankEmpty, rankEmpty, rank7, rank8 };
		setDefaultPiece(pieces);
	}

	/**
	 * 駒の初期配置と個数を設定する
	 * 
	 * @param pieces 初期配置される駒
	 */
	public void setDefaultPiece(String[] pieces) {
		if (pieces.length != 8 || pieces[0].length() != 8)
			return;
		for (PiecesNum p : PiecesNum.values()) {
			p.clear();
		}
		for (int rankIndex = 0; rankIndex < pieces.length; rankIndex++) {
			defaultPiecePlace[rankIndex] = pieces[rankIndex].toCharArray();
			for (int fileIndex = 0; fileIndex < pieces[rankIndex].length(); fileIndex++) {
				String square = indexToSquare(fileIndex, rankIndex);
				PiecesNum p = translatePiece(pieces[rankIndex].charAt(fileIndex), square);
				if (p != null) {
					p.generate();
				}
			}
		}
	}

	/**
	 * 初期配置の駒を取り除く
	 * 
	 * @param square 取り除くマス
	 */
	public void removePieceOnDefaultSquare(String square) {
		int fileIndex = fileToIndex(square);
		int rankIndex = rankToIndex(square);
		char removePiece = defaultPiecePlace[rankIndex][fileIndex];
		PiecesNum p = translatePiece(removePiece, square);
		if (p != null)
			p.remove();
	}

	/**
	 * 駒を取り除く
	 * 
	 * @param isWhitePieceRemoved 取り除かれた駒が白であるか
	 * @param removedPiece        取り除かれた駒名
	 * @param removedSquare       取り除かれたマス
	 */
	public void remove(boolean isWhitePieceRemoved, char removedPiece, String removedSquare) {
		removedPiece = isWhitePieceRemoved ? Character.toUpperCase(removedPiece) : Character.toLowerCase(removedPiece);
		PiecesNum p = translatePiece(removedPiece, removedSquare);
		p.remove();
	}

	/**
	 * 駒をプロモーションさせる
	 * 
	 * @param isWhitePiecePromoted プロモーションした駒が白であるか
	 * @param promotedPiece        プロモーションした先の駒
	 * @param promotedSquare       プロモーションしたマス
	 */
	public void promote(boolean isWhitePiecePromoted, char promotedPiece, String promotedSquare) {
		promotedPiece = isWhitePiecePromoted ? Character.toUpperCase(promotedPiece)
				: Character.toLowerCase(promotedPiece);
		PiecesNum p = translatePiece(promotedPiece, promotedSquare);
		PiecesNum deleteP = isWhitePiecePromoted ? PiecesNum.WP : PiecesNum.BP;
		p.generate();
		deleteP.remove();
	}

	/**
	 * 適正なエンディングタイプであるか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 適正なエンディングタイプである場合はtrue
	 */
	public boolean isValidEndingType(String endingType) {
		if (endingType.contains(SB) && endingType.contains(OB))
			return false;
		if (Util.countStr(endingType, SB) >= 2 || Util.countStr(endingType, OB) >= 2)
			return false;
	
		if (endingType.contains(SB) || endingType.contains(OB)) {
			if (Util.countStr(endingType, B) >= 2)
				return false;
		}
		return true;
	}

	/**
	 * 残っている駒がエンディングタイプと合っているか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 合っている場合はtrue
	 */
	public boolean isEnding(String endingType) {
		boolean checkResult = true;
	
		if (endingType.equals(P)) {
			return isPawnEnding();
		}
	
		checkResult &= existsN(endingType);
		checkResult &= existsR(endingType);
		checkResult &= existsQ(endingType);
		checkResult &= existsB(endingType);
	
		return checkResult;
	}

	/**
	 * ポーンエンディングであるか
	 * 
	 * @return ポーンエンディングである場合はtrue
	 */
	private boolean isPawnEnding() {
		if (PiecesNum.WN.exists() || PiecesNum.WRB.exists() || PiecesNum.WDB.exists() || PiecesNum.WR.exists()
				|| PiecesNum.WQ.exists() || PiecesNum.BN.exists() || PiecesNum.BRB.exists() || PiecesNum.BDB.exists()
				|| PiecesNum.BR.exists() || PiecesNum.BQ.exists())
			return false;
		return PiecesNum.WP.exists() || PiecesNum.BP.exists();
	}

	/**
	 * ナイトが適切な数残っているか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 適切な数残っている場合はtrue
	 */
	private boolean existsN(String endingType) {
		int num = Util.countStr(endingType, N);
		return PiecesNum.WN.exists(num) && PiecesNum.BN.exists(num);
	}

	/**
	 * ルークが適切な数残っているか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 適切な数残っている場合はtrue
	 */
	private boolean existsR(String endingType) {
		int num = Util.countStr(endingType, R);
		return PiecesNum.WR.exists(num) && PiecesNum.BR.exists(num);
	}

	/**
	 * クイーンが適切な数残っているか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 適切な数残っている場合はtrue
	 */
	private boolean existsQ(String endingType) {
		int num = Util.countStr(endingType, Q);
		return PiecesNum.WQ.exists(num) && PiecesNum.BQ.exists(num);
	}

	/**
	 * ビショップが適切な数残っているか
	 * 
	 * @param endingType エンディングタイプ
	 * @return 適切な数残っている場合はtrue
	 */
	private boolean existsB(String endingType) {
		if (endingType.contains(OB)) {
			return existsOB();
		} else if (endingType.contains(SB)) {
			return existsSB();
		} else if (Util.countStr(endingType, B) == 2) {
			return PiecesNum.WRB.exists(1) && PiecesNum.WDB.exists(1) && PiecesNum.BRB.exists(1)
					&& PiecesNum.BDB.exists(1);
		} else if (Util.countStr(endingType, B) == 1) {
			return existsOB() || existsSB();
		} else
			return PiecesNum.WRB.exists(0) && PiecesNum.WDB.exists(0) && PiecesNum.BRB.exists(0)
					&& PiecesNum.BDB.exists(0);
	}

	/**
	 * 異色ビショップが残っているか
	 * 
	 * @return 異色ビショップが残っている場合true
	 */
	private boolean existsOB() {
		if (PiecesNum.WRB.exists(1) && PiecesNum.WDB.exists(0) && PiecesNum.BRB.exists(0) && PiecesNum.BDB.exists(1))
			return true;
		else if (PiecesNum.WRB.exists(0) && PiecesNum.WDB.exists(1) && PiecesNum.BRB.exists(1)
				&& PiecesNum.BDB.exists(0))
			return true;
		else
			return false;
	}

	/**
	 * 同色ビショップが残っているか
	 * 
	 * @return 同色ビショップが残っている場合true
	 */
	private boolean existsSB() {
		if (PiecesNum.WRB.exists(1) && PiecesNum.WDB.exists(0) && PiecesNum.BRB.exists(1) && PiecesNum.BDB.exists(0))
			return true;
		else if (PiecesNum.WRB.exists(0) && PiecesNum.WDB.exists(1) && PiecesNum.BRB.exists(0)
				&& PiecesNum.BDB.exists(1))
			return true;
		else
			return false;
	}

	/**
	 * 駒名を個数オブジェクトに変換する
	 * 
	 * @param piece  駒名
	 * @param square 駒のいるマス
	 * @return 個数を保存するオブジェクト
	 */
	private PiecesNum translatePiece(char piece, String square) {
		switch (piece) {
		case WP:
			return PiecesNum.WP;
		case WN:
			return PiecesNum.WN;
		case WB:
			if (isDarkSquare(square)) {
				return PiecesNum.WDB;
			} else
				return PiecesNum.WRB;
		case WR:
			return PiecesNum.WR;
		case WQ:
			return PiecesNum.WQ;
		case WK:
			return PiecesNum.WK;
		case BP:
			return PiecesNum.BP;
		case BN:
			return PiecesNum.BN;
		case BB:
			if (isDarkSquare(square)) {
				return PiecesNum.BDB;
			} else
				return PiecesNum.BRB;
		case BR:
			return PiecesNum.BR;
		case BQ:
			return PiecesNum.BQ;
		case BK:
			return PiecesNum.BK;
		}
		return null;
	}

	/**
	 * ファイルを数値に変換する
	 * 
	 * @param square マス
	 * @return aファイル=0, hファイル=7
	 */
	private int fileToIndex(String square) {
		return square.charAt(0) - 'a';
	}

	/**
	 * ランクを数値に変換する
	 * 
	 * @param square マス
	 * @return 1ランク=0, 8ランク=7
	 */
	private int rankToIndex(String square) {
		return square.charAt(1) - '1';
	}

	/**
	 * ファイルとランクを数値からマスに変換する
	 * 
	 * @param fileIndex ファイル
	 * @param rankIndex ランク
	 * @return マス
	 */
	private String indexToSquare(int fileIndex, int rankIndex) {
		char file = (char) ('a' + fileIndex);
		char rank = (char) ('1' + rankIndex);
		return new String(new char[] { file, rank });
	}

	/**
	 * 黒マスであるか
	 * 
	 * @param square マス
	 * @return 黒マスであればtrue
	 */
	private boolean isDarkSquare(String square) {
		int fileIndex = fileToIndex(square);
		int rankIndex = rankToIndex(square);
		return (fileIndex + rankIndex) % 2 == 0;
	}

}
