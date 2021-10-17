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

		char toChar() {
			return nameChar;
		}

		public String toString() {
			return nameString;
		}

		boolean exists(int n) {
			return num == n;
		}

		boolean exists() {
			return num > 0;
		}
	};

	public Pieces() {
		setDefaultPiece();
	}

	/* ピースの初期配置 */
	private char[][] defaultPiecePlace = { { WR, WN, WB, WQ, WK, WB, WN, WR }, { WP, WP, WP, WP, WP, WP, WP, WP },
			EMPTYRANK, EMPTYRANK, EMPTYRANK, EMPTYRANK, { BP, BP, BP, BP, BP, BP, BP, BP },
			{ BR, BN, BB, BQ, BK, BB, BN, BR } };

	public void removePieceOnDefaultSquare(String square) {
		if (isValidSquare(square)) {
			int fileIndex = fileToIndex(square);
			int rankIndex = rankToIndex(square);
			char removePiece = defaultPiecePlace[rankIndex][fileIndex];
			PiecesNum p = translatePiece(removePiece, square);
			if (p != null)
				p.remove();
		}
	}

	public void remove(boolean isWhitePieceRemoved, char removedPiece, String removedSquare) {
		removedPiece = isWhitePieceRemoved ? Character.toUpperCase(removedPiece) : Character.toLowerCase(removedPiece);
		PiecesNum p = translatePiece(removedPiece, removedSquare);
		p.remove();
	}

	public void promote(boolean isWhitePiecePromoted, char promotedPiece, String promotedSquare) {
		promotedPiece = isWhitePiecePromoted ? Character.toUpperCase(promotedPiece)
				: Character.toLowerCase(promotedPiece);
		PiecesNum p = translatePiece(promotedPiece, promotedSquare);
		PiecesNum deleteP = isWhitePiecePromoted ? PiecesNum.WP : PiecesNum.BP;
		p.generate();
		deleteP.remove();
	}

	public boolean isEnding(String endingType) {
		int WNnum = 0;
		int WRBnum = 0;
		int WDBnum = 0;
		int WRnum = 0;
		int WQnum = 0;
		int BNnum = 0;
		int BRBnum = 0;
		int BDBnum = 0;
		int BRnum = 0;
		int BQnum = 0;

		if (!isValidEndingType(endingType)) {
			System.out.println("Invalid endgameType.");
			return false;
		}
		if (isPawnEndingType(endingType)) {
			return isPawnEnding(endingType);
		}

		if (endingType.contains(N)) {
			WNnum = Util.countStr(endingType, N);
			BNnum = Util.countStr(endingType, N);
			endingType = endingType.replace(N, "");
		}
		if (endingType.contains(R)) {
			WRnum = Util.countStr(endingType, R);
			BRnum = Util.countStr(endingType, R);
			endingType = endingType.replace(R, "");
		}
		if (endingType.contains(Q)) {
			WQnum = Util.countStr(endingType, Q);
			BQnum = Util.countStr(endingType, Q);
			endingType = endingType.replace(Q, "");
		}
		boolean NRQexists = PiecesNum.WN.exists(WNnum) && PiecesNum.WR.exists(WRnum) && PiecesNum.WQ.exists(WQnum)
				&& PiecesNum.BN.exists(BNnum) && PiecesNum.BR.exists(BRnum) && PiecesNum.BQ.exists(BQnum);

		if (!NRQexists) {
			return false;
		}

		if (!endingType.contains(B)) {
			return NRQexists && PiecesNum.WRB.exists(WRBnum) && PiecesNum.WDB.exists(WDBnum)
					&& PiecesNum.BRB.exists(BRBnum) && PiecesNum.BDB.exists(BDBnum);
		}

		if (endingType.contains(DOUBLEB)) {
			WRBnum = Util.countStr(endingType, DOUBLEB);
			WDBnum = Util.countStr(endingType, DOUBLEB);
			BRBnum = Util.countStr(endingType, DOUBLEB);
			BDBnum = Util.countStr(endingType, DOUBLEB);
			return NRQexists && PiecesNum.WRB.exists(WRBnum) && PiecesNum.WDB.exists(WDBnum)
					&& PiecesNum.BRB.exists(BRBnum) && PiecesNum.BDB.exists(BDBnum);
		}

		if (endingType.contains(OB)) {
			return existsOB();
		} else if (endingType.contains(SB)) {
			return existsSB();
		} else if (Util.countStr(endingType, B) >= 2) {
			return false;
		} else if (Util.countStr(endingType, B) == 1) {
			return existsOB() || existsSB();
		}

		return false;
	}

	private boolean existsOB() {
		if (PiecesNum.WRB.exists(1) && PiecesNum.WDB.exists(0) && PiecesNum.BRB.exists(0) && PiecesNum.BDB.exists(1))
			return true;
		else if (PiecesNum.WRB.exists(0) && PiecesNum.WDB.exists(1) && PiecesNum.BRB.exists(1)
				&& PiecesNum.BDB.exists(0))
			return true;
		else
			return false;
	}

	private boolean existsSB() {
		if (PiecesNum.WRB.exists(1) && PiecesNum.WDB.exists(0) && PiecesNum.BRB.exists(1) && PiecesNum.BDB.exists(0))
			return true;
		else if (PiecesNum.WRB.exists(0) && PiecesNum.WDB.exists(1) && PiecesNum.BRB.exists(0)
				&& PiecesNum.BDB.exists(1))
			return true;
		else
			return false;
	}

	private boolean isPawnEndingType(String endingType) {
		if (!endingType.contains(P))
			return false;
		if (endingType.contains(N) || endingType.contains(B) || endingType.contains(R) || endingType.contains(Q))
			return false;
		return true;
	}

	private boolean isPawnEnding(String endingType) {
		if (PiecesNum.WN.exists() || PiecesNum.WRB.exists() || PiecesNum.WDB.exists() || PiecesNum.WR.exists()
				|| PiecesNum.WQ.exists() || PiecesNum.BN.exists() || PiecesNum.BRB.exists() || PiecesNum.BDB.exists()
				|| PiecesNum.BR.exists() || PiecesNum.BQ.exists())
			return false;
		return PiecesNum.WP.exists() || PiecesNum.BP.exists();
	}

	public boolean isValidEndingType(String endingType) {
		if (endingType.contains(SB) && endingType.contains(OB))
			return false;
		if (Util.countStr(endingType, SB) >= 2 || Util.countStr(endingType, OB) >= 2)
			return false;

		if (endingType.contains(SB)) {
			endingType = endingType.replace(SB, "");
			if (endingType.contains(B))
				return false;
		}
		if (endingType.contains(OB)) {
			endingType = endingType.replace(OB, "");
			if (endingType.contains(B))
				return false;
		}
		return true;
	}

	// スタンダードの駒の初期配置と個数を設定する
	public void setDefaultPiece() {
		String rank1 = new String(new char[] { WR, WN, WB, WQ, WK, WB, WN, WR });
		String rank2 = new String(new char[] { WP, WP, WP, WP, WP, WP, WP, WP });
		String rankEmpty = new String(EMPTYRANK);
		String rank7 = new String(new char[] { BP, BP, BP, BP, BP, BP, BP, BP });
		String rank8 = new String(new char[] { BR, BN, BB, BQ, BK, BB, BN, BR });

		String pieces[] = { rank1, rank2, rankEmpty, rankEmpty, rankEmpty, rankEmpty, rank7, rank8 };
		setDefaultPiece(pieces);
	}

	// 駒の初期配置と個数を設定する
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

	public void printStatus() {
		for (int i = defaultPiecePlace.length - 1; i >= 0; i--)
			System.out.println(defaultPiecePlace[i]);
		printNums();
	}

	public void printNums() {
		for (PiecesNum p : PiecesNum.values()) {
			System.out.print(p.toString());
			System.out.print(":" + p.num + " ");
			if (p == PiecesNum.WK)
				System.out.println();
		}
		System.out.println();
	}

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

	private boolean isValidSquare(String square) {
		if (square.length() == 2) {
			if ('a' <= square.charAt(0) && square.charAt(0) <= 'h' && '1' <= square.charAt(1)
					&& square.charAt(1) <= '8')
				return true;
			else
				return false;
		} else
			return false;
	}

	private int fileToIndex(String square) {
		return square.charAt(0) - 'a';
	}

	private int rankToIndex(String square) {
		return square.charAt(1) - '1';
	}

	private String indexToSquare(int fileIndex, int rankIndex) {
		char file = (char) ('a' + fileIndex);
		char rank = (char) ('1' + rankIndex);
		return new String(new char[] { file, rank });
	}

	private boolean isDarkSquare(String square) {
		int fileIndex = fileToIndex(square);
		int rankIndex = rankToIndex(square);
		return (fileIndex + rankIndex) % 2 == 0;
	}

}
