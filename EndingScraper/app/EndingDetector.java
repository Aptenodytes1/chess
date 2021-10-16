package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndingDetector {

	MoveInfo[] whiteMoves;
	MoveInfo[] blackMoves;

	private String removeResult(String score) {

		String patternResult = "(.+) (1-0|0-1|1/2-1/2|\\*)";
		Pattern p = Pattern.compile(patternResult);
		Matcher matcher = p.matcher(score);
		return matcher.replaceAll("$1");
	}

	private String removeCheckAndMate(String score) {
		score = score.replaceAll("\\+", "");
		score = score.replaceAll("#", "");
		return score;
	}

	private String removeAnnotation(String score) {
		score = score.replaceAll("!", "");
		score = score.replaceAll("\\?", "");
		return score;
	}

	private String removeTurn(String score) {
		score = score.replaceAll("[0-9]+\\. ", "");
		score = score.replaceAll("[0-9]+\\.", "");
		return score;
	}

	public void test2() {
		String ts1 = "1. e4 e5 2. Nf3 d6 3. d4 Nd7 4. Bc4 Qf6 5. Bg5 Qg6 6. Nc3 h6 7. Bh4 Ngf6 "
				+ "8. dxe5 dxe5 9. O-O Be7 10. Re1 Bd6 11. Nd5 Nxd5 12. Qxd5 Nb6 13. Nxe5 Bxe5 14. Qxe5+ Be6 "
				+ "15. Bb3 O-O 16. Bxe6 Qxe6 17. f4 Nc4 18. Qxe6 fxe6 19. b3 Nd6 20. Bg3 Nb5 21. Re3 Nd4 22. Rc1 Rad8 "
				+ "23. e5 c6 24. c4 Nf5 25. Rf3 Nd4 26. Rd3 Ne2+ 27. Kf2 Nxc1 28. Rxd8 Rxd8 29. Bh4 Rd2+ 30. Kg3 Rxa2 "
				+ "31. Bd8 Nxb3 32. Kh3 Nd4 33. g3 Ra4 34. c5 Nb3 35. Be7 Rc4 36. Bd6 Nxc5 37. Bxc5 Rxc5 38. Kg4 b5 "
				+ "39. h4 b4 40. Kh5 b3 41. g4 b2 42. Kg6 h5 43. g5 Kh8 44. Kf7 b1=Q 45. Kxe6 Qb7 46. Kd6 Rc4 47. f5 Rd4+ 48. Kc5 Qb6# 0-1";
		String ts2 = "1. e4 c5 2. Nf3 d6 3. Bb5+ Nd7 4. O-O a6 5. Be2 g6 6. d4 cxd4 7. Qxd4 Ngf6 8. Rd1 Bg7 9. e5 dxe5 "
				+ "10. Nxe5 O-O 11. Bg4 Nxg4 12. Nxf7 Rxf7 13. Qxg4 Qb6 14. Be3 Qxb2 15. Nd2 Qxc2 16. Rac1 Ne5 17. Qb4 Qf5 "
				+ "18. Nf3 Nxf3+ 19. gxf3 Qxf3 20. Rd8+ Rf8 21. Qc4+ Kh8 22. Qxc8 Rxc8 23. Rdxc8 h5 24. R1c3 Bxc3 "
				+ "25. Rxc3 h4 26. Bd4+ Kh7 27. Rxf3 Rxf3 28. Be3 b5 29. Kf1 a5 30. Ke2 Rh3 31. Kd3 Rxh2 32. Kd4 Rh1 "
				+ "33. Kc5 Rb1 34. Kb6 h3 35. Bf4 g5 36. Be5 a4 37. Ka5 Re1 38. f4 h2 0-1";
		String ts3 = "1.d4 d5 2.Nf3 Nf6 3.c4 e6 4.Bg5 c5 5.cxd5 exd5 6.Nc3 cxd4 7.Nxd4 Nc6 8.e3 Be7 9.Bb5 Bd7 10.Bxf6 Bxf6 "
				+ "11.Nxd5 Bxd4 12.exd4 Qg5 13.Bxc6 Bxc6 14.Ne3 O-O-O 15.O-O Rhe8 16.Rc1 Rxe3 17.Rxc6+ bxc6 18.Qc1 Rxd4 "
				+ "19.fxe3 Rd7 20.Qxc6+ Kd8 21.Rf4 f5 22.Qc5 Qe7 23.Qxe7+ Kxe7 24.Rxf5 Rd1+ 25.Kf2 Rd2+ 26.Kf3 Rxb2 "
				+ "27.Ra5 Rb7 28.Ra6 Kf8 29.e4 Rc7 30.h4 Kf7 31.g4 Kf8 32.Kf4 Ke7 33.h5 h6 34.Kf5 Kf7 35.e5 Rb7 36.Rd6 Ke7 "
				+ "37.Ra6 Kf7 38.Rd6 Kf8 39.Rc6 Kf7 40.a3 1-0";
		String testScore = ts3;

		System.out.println();
		setWBMoves(testScore);
		System.out.println(isEnding_noComment("N", whiteMoves, blackMoves));
		System.out.println(isEnding_noComment("R", whiteMoves, blackMoves));
		System.out.println(isEnding_noComment("Q", whiteMoves, blackMoves));
		System.out.println();
		System.out.println(isEnding_noComment(testScore, "N"));
		System.out.println(isEnding_noComment(testScore, "R"));
		System.out.println(isEnding_noComment(testScore, "Q"));
		System.out.println();
		System.out.println(isEnding_noComment(testScore, "SB"));
	}

	private boolean isEnding(String endingType, MoveInfo[] whiteMoves, MoveInfo[] blackMoves) {
		Pieces p = new Pieces();
		p.printStatus();

		System.out.println();
		for (int ply = 0; ply < whiteMoves.length + blackMoves.length; ply++) {
			boolean isWhiteTurn = (ply % 2 == 0) ? true : false;
			String turnPlayer = isWhiteTurn ? "W" : "B";
			int turn = ply / 2;
			MoveInfo m = isWhiteTurn ? whiteMoves[turn] : blackMoves[turn];

			if (m.isPromotion()) {
				System.out.println("move turn:" + turnPlayer + (turn + 1));
				System.out.println("promote to:" + m.getPromotionPiece());
				p.promote(isWhiteTurn, m.getPromotionPiece(), m.getSquare());
				p.printNums();
				System.out.println();
			}

			boolean removeDefaultSquare = true;
			if (m.isCapture()) {
				int i;
				char removedPiece = '-';
				String removedSquare = null;

				int startTurn = isWhiteTurn ? turn - 1 : turn;
				for (i = startTurn; i >= 0; i--) {
					MoveInfo checkMove = isWhiteTurn ? blackMoves[i] : whiteMoves[i];
					if (m.isEqualSquare(checkMove)) {
						removedPiece = checkMove.getPiece();
						removedSquare = checkMove.getSquare();
						p.remove(!isWhiteTurn, removedPiece, removedSquare);
						removeDefaultSquare = false;
						break;
					}
				}
				if (removeDefaultSquare) {
					removedPiece = m.getPiece();
					removedSquare = m.getSquare();
					p.removePieceOnDefaultSquare(m.getSquare());
				}
				System.out.println("move turn:" + turnPlayer + (turn + 1));
				System.out.println("removed turn:" + (i + 1));
				System.out.println("removed piece:" + removedPiece);
				System.out.println("removed square:" + removedSquare);
				p.printNums();
				System.out.println();
			}

			if (p.isEnding(endingType))
				return true;
		}
		return false;
	}

	public boolean isEnding_noComment(String endingType, MoveInfo[] whiteMoves, MoveInfo[] blackMoves) {
		Pieces p = new Pieces();

		for (int ply = 0; ply < whiteMoves.length + blackMoves.length; ply++) {
			boolean isWhiteTurn = (ply % 2 == 0) ? true : false;
			int turn = ply / 2;
			MoveInfo m = isWhiteTurn ? whiteMoves[turn] : blackMoves[turn];

			if (m.isPromotion()) {
				p.promote(isWhiteTurn, m.getPromotionPiece(), m.getSquare());
			}

			boolean removeDefaultSquare = true;
			if (m.isCapture()) {
				int i;

				int startTurn = isWhiteTurn ? turn - 1 : turn;
				for (i = startTurn; i >= 0; i--) {
					MoveInfo checkMove = isWhiteTurn ? blackMoves[i] : whiteMoves[i];
					if (m.isEqualSquare(checkMove)) {
						p.remove(!isWhiteTurn, checkMove.getPiece(), checkMove.getSquare());
						removeDefaultSquare = false;
						break;
					}
				}
				if (removeDefaultSquare) {
					p.removePieceOnDefaultSquare(m.getSquare());
				}
			}

			if (p.isEnding(endingType))
				return true;
		}
		return false;
	}

	public boolean isEnding_noComment(String score, String endingType) {
		setWBMoves(score);
		Pieces p = new Pieces();

		for (int ply = 0; ply < whiteMoves.length + blackMoves.length; ply++) {
			boolean isWhiteTurn = (ply % 2 == 0) ? true : false;
			int turn = ply / 2;
			MoveInfo m = isWhiteTurn ? whiteMoves[turn] : blackMoves[turn];

			if (m.isPromotion()) {
				p.promote(isWhiteTurn, m.getPromotionPiece(), m.getSquare());
			}

			boolean removeDefaultSquare = true;
			if (m.isCapture()) {
				int i;

				int startTurn = isWhiteTurn ? turn - 1 : turn;
				for (i = startTurn; i >= 0; i--) {
					MoveInfo checkMove = isWhiteTurn ? blackMoves[i] : whiteMoves[i];
					if (m.isEqualSquare(checkMove)) {
						p.remove(!isWhiteTurn, checkMove.getPiece(), checkMove.getSquare());
						removeDefaultSquare = false;
						break;
					}
				}
				if (removeDefaultSquare) {
					p.removePieceOnDefaultSquare(m.getSquare());
				}
			}

			if (p.isEnding(endingType))
				return true;
		}
		return false;
	}

	private void setWBMoves(String score) {
		MoveInfo[] moves = scoreToMoves(score);
		int ply = moves.length;
		int whitePly = (ply + 1) / 2;
		int blackPly = ply - whitePly;

		this.whiteMoves = new MoveInfo[whitePly];
		this.blackMoves = new MoveInfo[blackPly];
		for (int i = 0; i < ply; i++) {
			if (moves[i].isWhiteMove())
				whiteMoves[i / 2] = moves[i];
			else
				blackMoves[i / 2] = moves[i];
		}
	}

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
