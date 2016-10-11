package apex.javatool.data.structure;

import java.util.HashSet;
import java.util.List;

public class DenseFeature extends Feature {
    private double[] value;
    private double result;
    private HashSet<Integer> ids;

    public DenseFeature(int n) {
        setSize(n);
    }

    public DenseFeature(Feature feature) {
        setSize(feature.size());
        for (int i = 0; i < nFeature; i++)
            value[i] = feature.getValue(i);
    }

    public DenseFeature(List<Double> values) {
        setSize(values.size());
        for (int i = 0; i < nFeature; i++)
            value[i] = values.get(i);
    }

    public void setSize(int n) {
        value = new double[n];
        ids = new HashSet<Integer>();
        for (int i = 0; i < n; i++)
            ids.add(i);
        nFeature = n;
    }

    public void setValue(int i, double v) {
        value[i] = v;
    }

    public double getValue(int i) {
        return value[i];
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public HashSet<Integer> getIds() {
        return ids;
    }

    @Override
    public void append(Feature f) {
        double[] ovalue = new double[value.length];
        for (int i = 0; i < value.length; i++)
            ovalue[i] = value[i];
        value = new double[nFeature + f.size()];
        for (int i = 0; i < nFeature; i++)
            value[i] = ovalue[i];
        for (int id : f.getIds())
            value[id + nFeature] = f.getValue(id);
        nFeature = value.length;
    }
}
