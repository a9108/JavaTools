package apex.javatool.data.structure;

import apex.javatool.data.DataInitializer;

public class Vector {
	protected double[] data;

	public Vector(Vector vec) {
		data = new double[vec.size()];
		for (int i = 0; i < data.length; i++)
			data[i] = vec.data[i];
	}

	public Vector(int n) {
		data = new double[n];
	}

	public Vector(int n, DataInitializer init) {
		data = new double[n];
		initialize(init);
	}

	public void initialize(DataInitializer init) {
		for (int i = 0; i < data.length; i++)
			data[i] = init.next();
	}

	public int size() {
		return data.length;
	}

	public double get(int i) {
		return data[i];
	}

	public void set(int i, double v) {
		data[i] = v;
	}

	public void update(int i, double v) {
		data[i] += v;
	}

	public void add(Vector vec) {
		if (data.length != vec.data.length) {
			System.err.println("Vector Size Mis-Match (add)");
			return;
		}
		for (int i = 0; i < data.length; i++)
			data[i] += vec.data[i];
	}

	public void minus(Vector vec) {
		if (data.length != vec.data.length) {
			System.err.println("Vector Size Mis-Match (minus)");
			return;
		}
		for (int i = 0; i < data.length; i++)
			data[i] -= vec.data[i];
	}

	public void mult(double v) {
		for (int i = 0; i < data.length; i++)
			data[i] *= v;
	}

	public static Vector mult(Vector vec, double v) {
		Vector result = new Vector(vec);
		result.mult(v);
		return result;
	}

	public static Vector add(Vector vecA, Vector vecB) {
		Vector result = new Vector(vecA);
		result.add(vecB);
		return result;
	}

	public static Vector minus(Vector vecA, Vector vecB) {
		Vector result = new Vector(vecA);
		result.minus(vecB);
		return result;
	}

	public static double dot(Vector vecA, Vector vecB) {
		if (vecA.data.length != vecB.data.length) {
			System.err.println("Vector Size Mis-Match (dot)");
			return 0;
		}
		double s = 0;
		for (int i = 0; i < vecA.data.length; i++)
			s += vecA.data[i] * vecB.data[i];
		return s;
	}

	public double norm(double degree) {
		double s = 0;
		for (double v : data)
			s += Math.pow(Math.abs(v), degree);
		return s;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < data.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(data[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	public static Vector max(Vector vecA, Vector vecB) {
		if (vecA.data.length != vecB.data.length) {
			System.err.println("Vector Size Mis-Match (max)");
			return null;
		}
		Vector res = new Vector(vecA.size());
		for (int i = 0; i < vecA.data.length; i++)
			res.set(i, Math.max(vecA.get(i), vecB.get(i)));
		return res;
	}

	public void serialDivide(Vector vec) {
		if (data.length != vec.data.length) {
			System.err.println("Vector Size Mis-Match (serialDivide)");
			return;
		}
		for (int i = 0; i < data.length; i++)
			data[i] /= vec.get(i);
	}

	public static Double CosineSimilarity(Vector a, Vector b) {
		if (a.size() != b.size())
			return null;
		double d = dot(a, b);
		d /= Math.sqrt(a.norm(2));
		d /= Math.sqrt(b.norm(2));
		return d;
	}
}
