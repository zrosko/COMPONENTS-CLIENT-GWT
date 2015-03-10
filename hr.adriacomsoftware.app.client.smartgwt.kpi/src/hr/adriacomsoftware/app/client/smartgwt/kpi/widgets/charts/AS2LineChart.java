package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2Chart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ResizeableChartCanvas;
import hr.as2.inf.common.charts.AS2ChartConstants;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;


public class AS2LineChart extends AS2Chart{


	public AS2LineChart() {
		super();
	}

	@Override
	public ResizeableChartCanvas getChart(final ChartValueObject chartVo) {
		_chartVo = chartVo;
		this.setShadow(true);
		this.setType(Series.Type.LINE)
				.setCredits(new Credits()
		                .setEnabled(ENABLE_CREDITS)
		            )
				.setPlotBorderWidth(PLOT_BORDER_WIDTH)
				.setChartTitle(new ChartTitle()
						.setText(AS2ChartConstants.CHART_TITLE_PREFIX+chartVo.getChartTitle()+AS2ChartConstants.CHART_TITLE_SUFIX)
				)
				.setChartSubtitle(
						new ChartSubtitle().setText(chartVo.getSubtitle())
				)
				.setLegend(
						new Legend().setLayout(Legend.Layout.HORIZONTAL)
								.setAlign(Legend.Align.CENTER)
								.setVerticalAlign(Legend.VerticalAlign.BOTTOM)
				).setToolTip(new ToolTip().setFormatter(getChartTooltipFormatter(chartVo.getChartType())));
//						
//						new ToolTipFormatter() {
//					public String format(ToolTipData toolTipData) {
////						com.google.gwt.i18n.client.NumberFormat nfGWT = com.google.gwt.i18n.client.NumberFormat
////								.getDecimalFormat().overrideFractionDigits(2);
//						return "<b>"
//								+ toolTipData.getSeriesName()
//								+ "</b><br/>"
//								+ toolTipData.getXAsString()
//								+ ": "
////								+ nfGWT.format(new BigDecimal(toolTipData
////										.getYAsDouble()));
//								+KpiContext.formatDoubleDefault(toolTipData.getYAsDouble());
//					}
//				}));
		this.getXAxis().setCategories(chartVo.getXCategories());
		this.getYAxis().setAxisTitleText(chartVo.getYTitle());
		chartVo.addSeriesToChart(this);

		//TODO testirati
		this.setSeriesPlotOptions(new SeriesPlotOptions()
		.setSeriesClickEventHandler(
				getChartSeriesClickEventHandler(chartVo.getChartType())));
//				new SeriesClickEventHandler() {
//
//			@Override
//			public boolean onClick(SeriesClickEvent seriesClickEvent) {
//				chartSeriesClickEventHandler(seriesClickEvent);
//				return true;
//			}
//		}));
		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(this);
		return chartCanvas;
	}

	public static Series createCurrentSeriesForChart(Chart chart,
			String currentSeriesTitle, Double[] currentSeriesPoints) {
		if(currentSeriesTitle.contains("*")){
			currentSeriesTitle = currentSeriesTitle.replace("*","");
			return chart.createSeries()
				.setName(currentSeriesTitle)
				.setPoints(currentSeriesPoints)
				.setType(Series.Type.AREA);
		}else{
			return chart.createSeries()
					.setName(currentSeriesTitle)
					.setPoints(currentSeriesPoints)
					.setType(Series.Type.LINE);
		}
	}




}
