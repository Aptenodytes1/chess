package app;

public class Util {
	public static int countStr(String target, String search) {
		int targetLength = target.length();
		target = target.replace(search, "");
		int lostLength = targetLength - target.length();
		return lostLength / search.length();
	}
}
