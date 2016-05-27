package apex.javatool.console.gui;

import java.io.IOException;

import apex.javatool.platform.system.Timer;

public class ProgressBar {

	private String name;
	private int totalTick;
	private int finished;
	private Timer timer;
	private int maxLen = 0;

	public ProgressBar(String name, int totalTick) {
		this.name = name;
		this.totalTick = totalTick;
		finished = 0;
		timer = new Timer();
		updateGUI();
	}

	public void tick() {
		synchronized (this) {
			finished++;
			updateGUI();
		}
	}

	private void updateGUI() {
		if (name == null)
			return;
		String eta = "---";
		if (finished > 0)
			eta = Timer.formatTime(timer.getMillis() * (totalTick - finished) / finished);
		String head = name;
		String progress = "[";
		double p = 100.0 * finished / totalTick;
		for (int i = 0; i < 100; i += 2)
			if (i < p)
				progress += "#";
			else
				progress += "-";
		progress += String.format(" %.2f%%", p);
		progress += "]";
		String all = head + " " + progress;
		if (finished == totalTick) {
			all += " Finished, total time : " + Timer.formatTime(timer.getMillis());
			for (; all.length() < maxLen; all += " ")
				;
			System.out.println(all);
		} else {
			all += " run:" + Timer.formatTime(timer.getMillis()) + ",eta:" + eta;
			for (; all.length() < maxLen; all += " ")
				;
			maxLen = Math.max(maxLen, all.length());
			System.out.print(all + "\r");
		}
	}

	public static void main(String[] args) throws IOException {
		ProgressBar testBar = new ProgressBar("Test Progress Bar", 100);
		for (int i = 0; i < 100; i++)
			try {
				testBar.tick();
				Thread.sleep(100);
			} catch (Exception e) {
			}
	}
}
