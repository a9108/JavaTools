package apex.javatool.ml.recommender;

import apex.javatool.data.structure.Pair;

import java.util.LinkedList;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/12/16.
 */
public abstract class Recommender {
    protected boolean quiet = false;
    protected int nThread = 1;
    protected LinkedList<Pair<Double, Pair<Integer, Integer>>> train = new LinkedList<>();

    public void addEntry(int i, int j, double v) {
        train.add(new Pair<>(v, new Pair<>(i, j)));
    }

    public abstract void train();

    public abstract double predict(int i, int j);

    public void setnThread(int nThread) {
        this.nThread = nThread;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

}
