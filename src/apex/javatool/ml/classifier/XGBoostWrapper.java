package apex.javatool.ml.classifier;

import apex.javatool.data.structure.Feature;
import apex.javatool.io.FileOps;
import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XGBoostWrapper extends Classifier {
    private static int synID = 1001;
    private static int myID;
    private String trainFile;

    public XGBoostWrapper(String workspace) {
        myID = synID++;
        trainFile = workspace + "train." + myID + ".xgb";
    }

    @Override
    public void clear() {
    }

    private Booster model;

    @Override
    public void train() {
        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("booster", "gblinear");
                put("eta", 0.3);
                put("lambda", 0);
                put("max_depth", 5);
                put("silent", 1);
                put("objective", "reg:linear");
                put("eval_metric", "logloss");
            }
        };
        try {
            int nFeatures = train.get(0).getSecond().size();
            float[] values = new float[train.size() * nFeatures];
            float[] labels = new float[train.size()];
            for (int i = 0; i < train.size(); i++) {
                for (int j = 0; j < train.get(i).getSecond().size(); j++)
                    values[i * nFeatures + j] = (float) train.get(i).getSecond().getValue(j);
                labels[i] = (float) train.get(i).getFirst();
            }
            DMatrix trainMat = new DMatrix(values, train.size(), nFeatures);
            trainMat.setLabel(labels);

            HashMap<String, DMatrix> watches = new HashMap<>();
//            watches.put("train", trainMat);
            model = XGBoost.train(trainMat, params, 100, watches, null, null);
        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }
        FileOps.remove(trainFile);
    }

    @Override
    public double predict(Feature data) {
        ArrayList<Feature> datas = new ArrayList<Feature>();
        datas.add(data);
        return predict(datas).get(0);
    }

    @Override
    public ArrayList<Double> predict(List<Feature> data) {
        try {
            int nFeatures = data.get(0).size();
            float[] values = new float[data.size() * nFeatures];
            for (int i = 0; i < data.size(); i++)
                for (int j = 0; j < data.get(i).size(); j++)
                    values[i * nFeatures + j] = (float) data.get(i).getValue(j);

            DMatrix matrix = new DMatrix(values, data.size(), nFeatures);
            float[][] predict = model.predict(matrix);
            ArrayList<Double> result = new ArrayList<>();
            for (int i = 0; i < predict.length; i++)
                result.add((double) predict[i][0]);
            return result;
        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroy() {
    }

    public void describeContent() {

    }
}