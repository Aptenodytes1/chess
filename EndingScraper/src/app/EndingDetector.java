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

	public boolean isEnding(String score, String endingType) {
		MoveInfo[] moves = scoreToMoves(score);
		setWBMoves(moves);
		Pieces p = new Pieces();

		for (int ply = 0; ply < moves.length; ply++) {
			boolean isWhiteTurn = (ply % 2 == 0) ? true : false;
			int turn = ply / 2;
			MoveInfo m = moves[ply];

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

	private void setWBMoves(MoveInfo[] moves) {
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
