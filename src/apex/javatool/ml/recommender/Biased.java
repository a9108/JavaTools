package apex.javatool.ml.recommender;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/12/16.
 */
public class Biased extends IterativeRecommender {
    private double globalBias = 0;
    private double[] biasUser, biasItem;

    @Override
    public void setAdaptive(boolean adaptive) {
        System.err.println("Biased does not support adaptive learning rate.");
    }

    public Biased(int N, int M) {
        biasUser = new double[N];
        biasItem = new double[M];
        globalBias = 0;
        adaptive = false;
    }

    @Override
    protected double calcCost() {
        return 0;
    }

    @Override
    protected void trainTuple(int i, int j, double v) {
        double err = predict(i, j) - v;
        globalBias -= err * rate;
        biasUser[i] -= err * rate;
        biasItem[j] -= err * rate;
    }

    @Override
    public double predict(int i, int j) {
        return biasUser[i] + biasItem[j] + globalBias;
    }
}
