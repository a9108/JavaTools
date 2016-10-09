package apex.javatool.ml.classifier;

import apex.javatool.data.structure.Feature;
import apex.javatool.data.structure.Tuple;
import apex.javatool.math.Functions;
import apex.javatool.math.RandomOps;

import java.util.Collections;

public class LinearRegression extends Classifier {

    private double var;
    private double[] w, ma, mi;
    private double bias = 0;
    private double rate, initRate;
    private int TrainNum;

    public LinearRegression(int nFeature, int TrainNum, double rate, double var) {
        setNFeature(nFeature);
        this.var = var;
        this.TrainNum = TrainNum;
        this.rate = rate;
        initRate = rate;
    }

    private void normalize() {
        ma = new double[NFeature];
        mi = new double[NFeature];
        boolean[] vi = new boolean[NFeature];
        for (int i = 0; i < NFeature; i++) {
            ma[i] = 0;
            mi[i] = 0;
            vi[i] = false;
        }
        for (Tuple<Integer, Feature> tuple : train) {
            Feature feature = tuple.getSecond();
            for (int id : feature.getIds()) {
                if (!vi[id]) {
                    vi[id] = true;
                    ma[id] = mi[id] = feature.getValue(id);
                }
                ma[id] = Math.max(ma[id], feature.getValue(id));
                mi[id] = Math.min(mi[id], feature.getValue(id));
            }
        }
        for (int i = 0; i < NFeature; i++)
            ma[i] = Math.max(ma[i], mi[i] + 0.1);
        for (Tuple<Integer, Feature> tuple : train) {
            for (int id : tuple.getSecond().getIds())
                tuple.getSecond().setValue(id, (tuple.getSecond().getValue(id) - mi[id]) / (ma[id] - mi[id]));
        }
    }

    @Override
    public void train() {
        normalize();

        w = new double[NFeature];
        for (int i = 0; i < NFeature; i++)
            w[i] = RandomOps.genNormal(0, 1. / NFeature);

        double lacost = getCost();
        int[] cnt = new int[NFeature];
        for (Tuple<Integer, Feature> tuple : train)
            for (int q : tuple.getSecond().getIds())
                cnt[q]++;
        double[] law = new double[NFeature];
        for (int r = 0; r < TrainNum && rate > 1e-8; r++) {
            for (int q = 0; q < NFeature; q++)
                law[q] = w[q];
            Collections.shuffle(train);
            for (Tuple<Integer, Feature> tuple : train) {
                double p = predictLocal(tuple.getSecond());
                // double err = f.getResult() - p;
                double g = tuple.getFirst() - p;

                for (int q : tuple.getSecond().getIds())
                    w[q] += rate * (tuple.getSecond().getValue(q) * g - w[q] / cnt[q] / var);
                bias += g * rate;
            }

            double cost = getCost();

            if (cost < lacost) {
                rate *= 1.1;
                lacost = cost;
            } else {
                for (int q = 0; q < NFeature; q++)
                    w[q] = law[q];
                rate /= 2;
            }
        }
        System.out.println(bias);
        for (int i = 0; i < NFeature; i++)
            System.out.println(w[i]);
    }

    @Override
    public double predict(Feature data) {
        double res = bias;
        for (int i : data.getIds())
            res += w[i] * (data.getValue(i) - mi[i]) / (ma[i] - mi[i]);
        return Functions.sigmoid(res);
    }

    @Override
    public void destroy() {
    }

    private double getCost() {
        double c = 0;
        for (Tuple<Integer, Feature> tuple : train)
            if (tuple.getFirst() == 1)
                c += Math.log(predictLocal(tuple.getSecond()));
            else
                c += Math.log(1 - predictLocal(tuple.getSecond()));
        double norm = 0;
        for (int i = 0; i < NFeature; i++)
            norm += w[i] * w[i];
        return -c + norm / var;
    }

    private double predictLocal(Feature data) {
        double res = bias;
        for (int i : data.getIds())
            res += w[i] * data.getValue(i);
        return Functions.sigmoid(res);
    }

    @Override
    public void clear() {
        train.clear();
        rate = initRate;
    }

}
