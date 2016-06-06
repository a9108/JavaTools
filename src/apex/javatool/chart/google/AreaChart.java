package apex.javatool.chart.google;

public class AreaChart extends GoogleChart{

	@Override
	protected String template() {
		return "<html>"
				+ "<head>"
				+ "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
				+ "<script type=\"text/javascript\">"
				+ "google.charts.load('current', {'packages':['corechart']});"
				+ "google.charts.setOnLoadCallback(drawChart);"
				+ "function drawChart() {"
				+ "var data = google.visualization.arrayToDataTable(["
				+"@DATA@"
				+ "]);"
				+ "var options = {"
				+ "title: '@TITLE@',"
				+ "hAxis: {title: '@XLABEL',  titleTextStyle: {color: '#333'}},"
				+ "vAxis: {title: '@YLABEL', minValue: 0}"
				+ "};"
				+ "var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));"
				+ "chart.draw(data, options);"
				+ "}"
				+ "</script>"
				+ "</head>"
				+ "<body>"
				+ "<div id=\"chart_div\" style=\"width: @WIDTHpx; height: @HEIGHTpx;\"></div>"
						+ "</body>"
						+ "</html>";
	}
	
}