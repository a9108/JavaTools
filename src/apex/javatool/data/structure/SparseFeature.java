package apex.javatool.data.structure;

import java.util.HashMap;
import java.util.Set;

public class SparseFeature extends Feature {
	private HashMap<Integer, Double> values = new HashMap<Integer, Double>();

	public SparseFeature(int n) {
		setSize(n);
	}

	public SparseFeature(Feature curF) {
		setSize(curF.size());
		for (int id : curF.getIds())
			setValue(id, curF.getValue(id));
	}

	@Override
	public void setSize(int n) {
		nFeature = n;
	}

	@Override
	public void setValue(int i, double v) {
		values.put(i, v);
	}

	@Override
	public double getValue(int i) {
		if (values.containsKey(i))
			return values.get(i);
		return 0;
	}

	@Override
	public Set<Integer> getIds() {
		return values.keySet();
	}

	@Override
	public void append(Feature f) {
		for (int id : f.getIds())
			setValue(id + size(), f.getValue(id));
		setSize(nFeature + f.size());
	}
}
