package apex.javatool.parallel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import apex.javatool.console.gui.ProgressBar;
import apex.javatool.function.Function;

public class ParallelMap<A, B> {

	public HashMap<A, B> run(String name, Collection<A> jobs, Function<A, B> func, int nThread) {
		ProgressBar progressBar = new ProgressBar(name, jobs.size());
		HashMap<A, B> result = new HashMap<A, B>();
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
						B res = func.call(job);
						synchronized (result) {
							result.put(job, res);
						}
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
		return result;
	}
}