package apex.javatool.data.structure;

import java.util.Set;

public abstract class Feature {
	protected int nFeature;

	public abstract void setSize(int n);

	public int size() {
		return nFeature;
	}

	public abstract void setValue(int i, double v);

	public abstract double getValue(int i);

	public abstract Set<Integer> getIds();

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int offset) {
		StringBuilder sb = new StringBuilder();
		for (Integer id : getIds())
			sb.append((id + offset) + ":" + getValue(id) + "\t");
		return sb.toString();
	}

	public abstract void append(Feature f);
}
