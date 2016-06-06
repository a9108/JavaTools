package apex.javatool.data;

import apex.javatool.math.RandomOps;

public class DataInitializer {
	private int type;
	private double[] params;
	static public int CONSTANT = 0;
	static public int UNIFORM = 1;
	static public int GAUSSION = 2;

	public double next() {
		if (type == 0)
			return params[0];
		if (type == 1)
			return RandomOps.genDouble(params[0], params[1]);
		if (type == 2)
			return RandomOps.genNormal(params[1], params[0]);
		System.err.println("Unknown Initializer Type");
		return 0;
	}

	public DataInitializer(int type, double[] params) {
		this.type = type;
		this.params = params;
	}
}
