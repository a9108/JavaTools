package apex.javatool.ml.recommender;

import apex.javatool.data.DataInitializer;
import apex.javatool.data.structure.Matrix;
import apex.javatool.data.structure.Pair;
import apex.javatool.data.structure.Vector;
import apex.javatool.math.Functions;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/12/16.
 */
public class BiasedMF extends IterativeRecommender {
    private Matrix U, V;
    private double[] biasU, biasV;
    private double bias;
    private double lambda = 0;

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }


    public BiasedMF(int K, int N, int M) {
        double[] param = {-1. / Math.sqrt(K), 1. / Math.sqrt(K)};
        DataInitializer initializer = new DataInitializer(DataInitializer.CONSTANT, param);
        U = new Matrix(N, K, initializer);
        V = new Matrix(M, K, initializer);
        bias = 0;
        biasU = new double[N];
        biasV = new double[M];
    }

    @Override
    public void train() {
        train(100, 1e-6);
    }

    public void train(int maxRound) {
        train(maxRound, 0);
    }

    @Override
    protected void trainTuple(int i, int j, double v) {
        double y = predict(i, j);
        double g = 2 * (y - v);// * y * (1 - y);
        Vector u = new Vector(U.get(i));
        U.get(i).minus(Vector.mult(Vector.add(Vector.mult(V.get(j), g), Vector.mult(U.get(i), lambda)), rate));
        V.get(j).minus(Vector.mult(Vector.add(Vector.mult(u, g), Vector.mult(V.get(j), lambda)), rate));
        biasU[i] -= rate * g;
        biasV[j] -= rate * g;
        bias -= rate * g;
    }

    @Override
    protected double calcCost() {
        double norm = U.norm(2) + V.norm(2);
        double loss = 0;
        for (Pair<Double, Pair<Integer, Integer>> data : train) {
            double predict = predict(data.getSecond().getFirst(), data.getSecond().getSecond());
            if (data.getFirst() == 1)
                loss -= Math.log(predict);
            else loss -= Math.log(1 - predict);
//            double err = predict - data.getFirst();
//            loss += Math.pow(err, 2);
        }
        return loss + lambda * norm;
    }

    @Override
    public double predict(int i, int j) {
        return Functions.sigmoid(bias + biasU[i] + biasV[j] + Vector.dot(U.get(i), V.get(j)));
    }

    public Vector getUserEmbedding(int i) {
        return U.get(i);
    }

    public Vector getItemEmbeeding(int j) {
        return V.get(j);
    }
}
