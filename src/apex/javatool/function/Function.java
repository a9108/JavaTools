package apex.javatool.function;

import apex.javatool.console.gui.ProgressBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Function<A, B> {
    public abstract B call(A data);

    public HashMap<A, B> parallelMap(String name, Collection<A> jobs, int nThread) {
        ProgressBar progressBar = new ProgressBar(name, jobs.size());
        HashMap<A, B> result = new HashMap<>();
        LinkedList<A> Q = new LinkedList<>(jobs);
        Thread[] workers = new Thread[nThread];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread() {
                public void run() {
                    for (; ; ) {
                        A job;
                        synchronized (Q) {
                            if (Q.isEmpty())
                                return;
                            job = Q.pop();
                        }
                        B res = call(job);
                        synchronized (result) {
                            result.put(job, res);
                        }
                        progressBar.tick();
                    }
                }

                ;
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

    public ArrayList<B> parallelList(String name, Collection<A> jobs, int nThread) {
        ProgressBar progressBar = new ProgressBar(name, jobs.size());
        ArrayList<B> result = new ArrayList<B>();
        LinkedList<A> Q = new LinkedList<>(jobs);
        Thread[] workers = new Thread[nThread];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread() {
                public void run() {
                    for (; ; ) {
                        A job;
                        synchronized (Q) {
                            if (Q.isEmpty())
                                return;
                            job = Q.pop();
                        }
                        B res = call(job);
                        synchronized (result) {
                            result.add(res);
                        }
                        progressBar.tick();
                    }
                }

                ;
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
