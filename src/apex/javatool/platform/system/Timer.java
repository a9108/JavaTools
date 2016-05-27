package apex.javatool.platform.system;

public class Timer {
	private long startTime;

	public Timer() {
		startTime = System.currentTimeMillis();
	}

	public long getMillis() {
		return System.currentTimeMillis() - startTime;
	}

	public static String formatTime(long millis) {
		String res = "";
		double second = millis / 1000.;
		int min = (int) second / 60;
		second -= min * 60;
		res = String.format("%.2fs", second);
		if (min == 0)
			return res;
		int hour = (int) min / 60;
		min -= hour * 60;
		res = min + "m" + res;
		if (hour > 0)
			res = hour + "h" + res;
		return res;
	}

}
