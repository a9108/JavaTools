package apex.javatool.ml.classifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import apex.javatool.data.structure.DenseFeature;
import apex.javatool.data.structure.Feature;
import apex.javatool.data.structure.SparseFeature;
import apex.javatool.data.structure.Tuple;

public abstract class Classifier {
	protected ArrayList<Tuple<Integer, Feature>> train = new ArrayList<Tuple<Integer, Feature>>();
	protected int NFeature;

	public int getNFeature() {
		return NFeature;
	}

	public void setNFeature(int nFeature) {
		NFeature = nFeature;
	}

	public void addTrain(int label, Feature feature) {
		train.add(new Tuple<Integer, Feature>(label, new SparseFeature(feature)));
	}

	public abstract void clear();

	public abstract void train();

	public abstract double predict(Feature data);

	public void validate() {
		LinkedList<Feature> trainFeatures = new LinkedList<Feature>();
		for (Tuple<Integer, Feature> cur : train)
			trainFeatures.add(cur.getSecond());
		ArrayList<Double> pred = predict(trainFeatures);
		int hit = 0;
		double loss = 0;
		for (int i = 0; i < pred.size(); i++) {
			int truth = train.get(i).getFirst();
			double p = pred.get(i);
			int res = 0;
			if (p >= 0.5)
				res = 1;
			if (res == truth)
				hit++;
			if (truth == 1)
				loss -= Math.log(p);
			else
				loss -= Math.log(1 - p);
		}
		System.out.println("Precison = " + hit / (0.0 + train.size()) + " , LogLoss = " + loss / train.size());
	}

	public abstract void destroy();

	public ArrayList<Double> predict(List<Feature> data) {
		ArrayList<Double> res = new ArrayList<Double>();
		for (Feature f : data)
			res.add(predict(f));
		return res;
	}
}
