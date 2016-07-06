package apex.javatool.ml.recommender;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/12/16.
 */
public class ItemAvg extends Recommender {
    private double[] biasItem;

    public ItemAvg(int M) {
        biasItem = new double[M];
    }

    @Override
    public void train() {
        for (int i = 0; i < biasItem.length; i++)
            biasItem[i] = 0;
        train.stream().forEach(cur -> biasItem[cur.getSecond().getSecond()] += cur.getFirst());
    }

    @Override
    public double predict(int i, int j) {
        return biasItem[j];
    }
}
