package apex.javatool.data.structure;

import apex.javatool.data.DataInitializer;

public class Matrix {
	protected Vector[] data;
	protected int nrow, ncol;

	private void initialize(int nrow, int ncol) {
		data = new Vector[nrow];
		for (int i = 0; i < data.length; i++)
			data[i] = new Vector(ncol);
		this.ncol = ncol;
		this.nrow = nrow;
	}

	public Matrix(Matrix mat) {
		nrow = mat.nrow;
		ncol = mat.ncol;
		data = new Vector[nrow];
		for (int i = 0; i < nrow; i++)
			data[i] = new Vector(mat.data[i]);
	}

	public Matrix(int nrow, int ncol) {
		initialize(nrow, ncol);
	}

	public Matrix(int nrow, int ncol, DataInitializer init) {
		initialize(nrow, ncol);
		initialize(init);
	}

	public void initialize(DataInitializer init) {
		for (int i = 0; i < nrow; i++)
			data[i].initialize(init);
	}

	public Vector get(int i) {
		return data[i];
	}

	public double get(int i, int j) {
		return data[i].get(j);
	}

	public void set(int i, int j, double v) {
		data[i].set(j, v);
	}

	public void update(int i, int j, double v) {
		data[i].update(j, v);
	}

	public void update(int i, Vector v) {
		data[i].add(v);
	}

	public int getNcol() {
		return ncol;
	}

	public int getNrow() {
		return nrow;
	}

	public void add(Matrix mat) {
		if (ncol != mat.getNcol() || nrow != mat.getNrow()) {
			System.err.println("Matrix Not Match (Add)");
			return;
		}
		for (int i = 0; i < nrow; i++)
			data[i].add(mat.data[i]);
	}

	public void mult(double v) {
		for (int i = 0; i < nrow; i++)
			data[i].mult(v);
	}

	public static Matrix mult(Matrix mat, double v) {
		Matrix result = new Matrix(mat);
		result.mult(v);
		return result;
	}
}
