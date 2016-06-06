package apex.javatool.chart.google;

import apex.javatool.io.FileOps;

public abstract class GoogleChart {
	private String title;
	private String xlabel;
	private String ylabel;
	private int width;
	private int height;
	private String[][] data;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setXlabel(String xlabel) {
		this.xlabel = xlabel;
	}

	public void setYlabel(String ylabel) {
		this.ylabel = ylabel;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public void save(String dir) {
		FileOps.SaveFile(dir, generate());
	}

	protected abstract String template();

	protected String generate() {
		String content = template();
		content.replace("@TITLE@", title);
		content.replace("@WIDTH@", "" + width);
		content.replace("@HEIGHT@", "" + height);
		content.replace("@XLABEL@", xlabel);
		content.replace("@YLABEL@", ylabel);
		content.replace("@DATA@", formatData());
		return content;
	}

	private CharSequence formatData() {
		String res = "";
		for (int i = 0; i < data.length; i++) {
			if (i > 0)
				res += ",";
			String cur = "[";
			for (int j = 0; j < data[i].length; j++) {
				if (j > 0)
					cur += ",";
				cur += data[i][j];
			}
			cur += "]";
			res += cur;
		}
		return res;
	}
}