package app;

import java.util.Date;

public class GameInfo {
	String whitePlayer;
	String blackPalyer;
	String score;
	String result;
	String site;
	String opening;
	Date date;
	int whiteRating;
	int blackRating;

	public String getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public String getBlackPalyer() {
		return blackPalyer;
	}

	public void setBlackPalyer(String blackPalyer) {
		this.blackPalyer = blackPalyer;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getWhiteRating() {
		return whiteRating;
	}

	public void setWhiteRating(int whiteRating) {
		this.whiteRating = whiteRating;
	}

	public int getBlackRating() {
		return blackRating;
	}

	public void setBlackRating(int blackRating) {
		this.blackRating = blackRating;
	}

	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public GameInfo(String score, String site) {
		this.score = score;
		this.site = site;
	}

	public String toString() {
		return site;
	}
}