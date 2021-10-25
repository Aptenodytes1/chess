package app;

public class EndingDetector {
	/**
	 * 棋譜が指定したエンディングタイプに突入したときのプライ数を返す
	 * 
	 * @param score      棋譜
	 * @param endingType エンディングタイプ
	 * @return プライ数 エンドゲームが不適合である場合、0を返す
	 */
	public int getEndingPly(String score, String endingType) {
		MoveInfo[] moves = scoreToMoves(score);
		MoveInfo[] whiteMoves = getWBMoves(moves)[0];
		MoveInfo[] blackMoves = getWBMoves(moves)[1];
		Pieces p = new Pieces();

		if (!p.isValidEndingType(endingType)) {
			System.out.println("Invalid endgameType.");
			return 0;
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
			if (m.isEnPassant()) {
				if (isWhiteTurn)
					p.removeBP();
				else
					p.removeWP();
			} else if (m.isCapture()) {
				for (int i = opponentsLastTurn; i >= 0; i--) {
					MoveInfo checkMove = opponentsMoves[i];
					if (m.getSquare().equals(checkMove.getSquare())) {
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
				return ply + 1;
		}
		return 0;

////		System.out.println(score);
//		PieceCountingBoard board = new PieceCountingBoard();
//		board.setBoard();
////		board.printBoard();
////		System.out.println();
//		for (int ply = 0; ply < moves.length; ply++) {
//			board.move(moves[ply]);
//			if (board.isEnding(endingType))
//				return ply + 1;
//		}
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
			if (moves[i].isWhite())
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
		String s = ChessUtil.removeResult(score);
		s = ChessUtil.removeAnnotation(s);
		s = ChessUtil.removeCheckAndMate(s);
		s = ChessUtil.removeTurn(s);

		String[] sections = s.split(" ");
		MoveInfo[] moves = new MoveInfo[sections.length];
		MoveParser parser = new MoveParser();
		MoveInfo lastMove;
		for (int i = 0; i < sections.length; i++) {
			lastMove = (i == 0) ? null : moves[i - 1];
			boolean whiteTurn = (i % 2 == 0);
			moves[i] = parser.getMoveInfo(sections[i], whiteTurn, lastMove);
		}
		return moves;
	}
}
