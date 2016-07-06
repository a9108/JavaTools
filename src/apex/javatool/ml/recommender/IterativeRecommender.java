package apex.javatool.ml.recommender;

import apex.javatool.data.structure.Pair;
import apex.javatool.function.Procedure;

import java.util.Collections;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/12/16.
 */
public abstract class IterativeRecommender extends Recommender {
    protected double rate = 1e-4;
    protected boolean adaptive = true;

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAdaptive(boolean adaptive) {
        this.adaptive = adaptive;
    }

    protected abstract double calcCost();

    protected abstract void trainTuple(int i, int j, double v);

    protected void trainIteration(int r) {
        Collections.shuffle(train);
        new Procedure<Pair<Double, Pair<Integer, Integer>>>() {
            @Override
            public void call(Pair<Double, Pair<Integer, Integer>> data) {
                trainTuple(data.getSecond().getFirst(), data.getSecond().getSecond(), data.getFirst());
            }
        }.parallel("Round " + r, train, nThread);
    }

    @Override
    public void train() {
        train(100, 1e-6);
    }

    public void train(int maxRound) {
        train(maxRound, 0);
    }

    public void train(int maxRound, double rateThres) {
        double lastCost = calcCost();
        if (!quiet)
            System.out.println("Initial Cost : " + lastCost);
        for (int r = 0; r < maxRound && rate > rateThres; r++) {
            trainIteration(r);
            double cur = calcCost();
            if (adaptive) {
                if (cur < lastCost)
                    rate = Math.min(rate * 1.1, 1e-2);
                else
                    rate /= 2;
            }
            lastCost = cur;
            if (!quiet)
                System.out.println("Round " + r + "\tCost = " + lastCost
                        + "\tRate = " + rate);
        }
    }
}
