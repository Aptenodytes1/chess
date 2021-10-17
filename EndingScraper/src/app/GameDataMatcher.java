package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameDataMatcher {
	String patternEvent = "^\\[Event.+";
	String patternSite = "^\\[Site \"(.+)\"\\]";
	String patternDate = "^\\[Date \"(.+)\"\\]";
	String patternWhite = "^\\[White \"(.+)\"\\]";
	String patternBlack = "^\\[Black \"(.+)\"\\]";
	String patternResult = "^\\[Result \"(.+)\"\\]";
	String patternWhiteElo = "^\\[WhiteElo \"(.+)\"\\]";
	String patternBlackElo = "^\\[BlackElo \"(.+)\"\\]";
	String patternEco = "^\\[ECO \"(.+)\"\\]";
	String patternScore = "^1\\..+";

	Pattern pEvent = Pattern.compile(patternEvent);
	Pattern pSite = Pattern.compile(patternSite);
	Pattern pDate = Pattern.compile(patternDate);
	Pattern pWhite = Pattern.compile(patternWhite);
	Pattern pBlack = Pattern.compile(patternBlack);
	Pattern pResult = Pattern.compile(patternResult);
	Pattern pWhiteElo = Pattern.compile(patternWhiteElo);
	Pattern pBlackElo = Pattern.compile(patternBlackElo);
	Pattern pEco = Pattern.compile(patternEco);
	Pattern pScore = Pattern.compile(patternScore);

	public boolean matchEvent(String str) {
		return pEvent.matcher(str).find();
	}

	public boolean matchSite(String str) {
		return pSite.matcher(str).find();
	}

	public boolean matchDate(String str) {
		return pDate.matcher(str).find();
	}

	public boolean matchWhite(String str) {
		return pWhite.matcher(str).find();
	}

	public boolean matchBlack(String str) {
		return pBlack.matcher(str).find();
	}

	public boolean matchResult(String str) {
		return pResult.matcher(str).find();
	}

	public boolean matchWhiteElo(String str) {
		return pWhiteElo.matcher(str).find();
	}

	public boolean matchBlackElo(String str) {
		return pBlackElo.matcher(str).find();
	}

	public boolean matchEco(String str) {
		return pEco.matcher(str).find();
	}

	public boolean matchScore(String str) {
		return pScore.matcher(str).find();
	}

	private String extract(String str, Pattern p) {
		Matcher m = p.matcher(str);
		return m.matches() ? m.group(1) : null;
	}

	public String extractSite(String str) {
		return extract(str, pSite);
	}

	public String extractDate(String str) {
		return extract(str, pDate);
	}

	public String extractWhite(String str) {
		return extract(str, pWhite);
	}

	public String extractBlack(String str) {
		return extract(str, pBlack);
	}

	public String extractResult(String str) {
		return extract(str, pResult);
	}

	public String extractWhiteElo(String str) {
		return extract(str, pWhiteElo);
	}

	public String extractBlackElo(String str) {
		return extract(str, pBlackElo);
	}

	public String extractEco(String str) {
		return extract(str, pEco);
	}

}
