package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;


import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.StockChart;
import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;

public abstract class AS2StockChart extends StockChart {
	

	public Chart _chart;
	public ChartValueObject _chartVo;
	
	public AS2StockChart() {
	}
	
	protected void chartPointClickEventHandler(PointClickEvent pointClickEvent) {
		new AS2ChartsOnClickWindow(
				pointClickEvent, _chartVo);
	}

	protected void chartSeriesClickEventHandler(
			SeriesClickEvent seriesClickEvent) {
		new AS2ChartsOnClickWindow(
				seriesClickEvent, _chartVo);
	}
	
	public abstract ResizeableChartCanvas getChart(
			final ChartValueObject chartVo);

}
