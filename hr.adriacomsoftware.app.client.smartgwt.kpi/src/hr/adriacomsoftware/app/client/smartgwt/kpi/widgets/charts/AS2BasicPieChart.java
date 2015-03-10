package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2Chart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ResizeableChartCanvas;
import hr.as2.inf.common.charts.AS2ChartConstants;

import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.labels.PieDataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;


public class AS2BasicPieChart extends AS2Chart{

//	NumberFormat noDecimal = NumberFormat.getDecimalFormat()
//			.overrideFractionDigits(0);

	public AS2BasicPieChart() {
		super();
	}

	@Override
	public ResizeableChartCanvas getChart(ChartValueObject chartVo) {
		_chartVo = chartVo;
		this.setType(Series.Type.PIE)
				.setCredits(new Credits()
		                .setEnabled(ENABLE_CREDITS)
		            )
				.setShadow(SHOW_SHADOW)
				.setPlotBorderWidth(PLOT_BORDER_WIDTH)
				.setChartSubtitle(
						new ChartSubtitle().setText(chartVo.getSubtitle()))
				.setChartTitleText(AS2ChartConstants.CHART_TITLE_PREFIX+chartVo.getChartTitle()+AS2ChartConstants.CHART_TITLE_SUFIX)
//				.setPlotBackgroundColor((String) null)
//				.setPlotBorderWidth(null)
//				.setPlotShadow(false)
				.setPiePlotOptions(
						new PiePlotOptions()
								.setAllowPointSelect(true)
								.setCursor(PlotOptions.Cursor.POINTER)
								.setPieDataLabels(
										new PieDataLabels()
												.setConnectorColor("#000000")
												.setEnabled(true)
												.setColor("#000000")
												.setFormatter(getBasicPieDataLabelsFormatter())))
//														new DataLabelsFormatter() {
//															public String format(DataLabelsData dataLabelsData) {
//																return "<b>"
//																		+ dataLabelsData.getPointName()
//																		+ "</b>: "
//																		+ KpiContext.formatDoubleNoDecimals(dataLabelsData.getYAsDouble());
////																		+ noDecimal.format(dataLabelsData.getYAsDouble());
//															}
//														})))
				.setLegend(
						new Legend().setLayout(Legend.Layout.VERTICAL)
								.setAlign(Legend.Align.RIGHT)
								.setVerticalAlign(Legend.VerticalAlign.TOP)
								.setX(-100).setY(100).setFloating(true)
								.setBorderWidth(1)
								.setBackgroundColor("#FFFFFF").setShadow(true))
				.setToolTip(new ToolTip().setFormatter(getChartTooltipFormatter(chartVo.getChartType())));
						
//						new ToolTipFormatter() {
//					public String format(ToolTipData toolTipData) {
//						return "<b>" + toolTipData.getPointName() + "</b>: "
////								+ noDecimal.format(toolTipData.getYAsDouble());
//								+ KpiContext.formatDoubleNoDecimals(toolTipData.getYAsDouble());
//					}
//				}));

		if (chartVo.getSeries() != null) {
			addSeriesToChart(chartVo);
		}
		
		
		this.setSeriesPlotOptions(new SeriesPlotOptions()
		.setSeriesClickEventHandler(getChartSeriesClickEventHandler(chartVo.getChartType())));
//				
//				new SeriesClickEventHandler() {
//
//			@Override
//			public boolean onClick(SeriesClickEvent seriesClickEvent) {
//				chartPieBarNegStackSeriesClickEventHandler(seriesClickEvent);
//				return true;
//			}
//		}));
		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(this);
		return chartCanvas;
	}

	private void addSeriesToChart(ChartValueObject chartVo) {
		Point[] points = new Point[chartVo.getSeries().getLength()];
		int j = 0;
		RecordList series = chartVo.getSeries();
		for(int i=0;i<series.getLength();i++){
			Record record=series.get(i);
			Record currentSeries = record.getAttributeAsRecord("record");
			String currentSeriesName= chartVo.getCurrentSeriesName(currentSeries);
			if(currentSeries.getAttributeAsDouble("y")!=null){
				points[j] = new Point(currentSeriesName,currentSeries.getAttributeAsDouble("y"));
			}else{
				points[j] = new Point(currentSeriesName,currentSeries.getAttributeAsDouble(currentSeriesName));
			}
			j++;
		}
		this.addSeries(this.createSeries().setPoints(points));
		
	}

}
