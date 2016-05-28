package apex.javatool.ml.classifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import apex.javatool.data.structure.Feature;
import apex.javatool.data.structure.Tuple;

public abstract class Classifier {
	protected LinkedList<Tuple<Integer, Feature>> train = new LinkedList<Tuple<Integer, Feature>>();
	protected int NFeature;

	public int getNFeature() {
		return NFeature;
	}

	public void setNFeature(int nFeature) {
		NFeature = nFeature;
	}

	public void addTrain(int label, Feature feature) {
		train.add(new Tuple<Integer, Feature>(label, feature));
	}

	public abstract void clear();

	public abstract void train();

	public abstract double predict(Feature data);

	public abstract void destroy();

	public ArrayList<Double> predict(List<Feature> data) {
		ArrayList<Double> res = new ArrayList<Double>();
		for (Feature f : data)
			res.add(predict(f));
		return res;
	}
}
