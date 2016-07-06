package apex.javatool.function;

import apex.javatool.console.gui.ProgressBar;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Procedure<A> {
	public abstract void call(A data);

	public void parallel(String name, Collection<A> jobs, int nThread) {
		ProgressBar progressBar = new ProgressBar(name, jobs.size());
		LinkedList<A> Q = new LinkedList<A>(jobs);
		Thread[] workers = new Thread[nThread];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new Thread() {
				public void run() {
					for (;;) {
						A job;
						synchronized (Q) {
							if (Q.isEmpty())
								return;
							job = Q.pop();
						}
						call(job);
						progressBar.tick();
					}
				};
			};
			workers[i].start();
		}
		for (Thread worker : workers)
			try {
				worker.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
