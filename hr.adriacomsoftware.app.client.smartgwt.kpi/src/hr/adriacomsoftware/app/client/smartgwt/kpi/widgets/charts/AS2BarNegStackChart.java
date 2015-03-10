package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2Chart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ResizeableChartCanvas;
import hr.as2.inf.common.charts.AS2ChartConstants;

import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

public class AS2BarNegStackChart extends AS2Chart {

	public AS2BarNegStackChart() {
		super();
	}

	@Override
	public ResizeableChartCanvas getChart(ChartValueObject chartVo) {
		_chartVo = chartVo;
		this.setType(Series.Type.BAR)
				.setCredits(new Credits()
		                .setEnabled(ENABLE_CREDITS)
		            )
				.setPlotBorderWidth(PLOT_BORDER_WIDTH)
				.setShadow(SHOW_SHADOW)
				.setChartTitleText(
						AS2ChartConstants.CHART_TITLE_PREFIX + chartVo.getChartTitle()
								+ AS2ChartConstants.CHART_TITLE_SUFIX)
				.setChartSubtitleText(chartVo.getSubtitle())
				.setSeriesPlotOptions(
						new SeriesPlotOptions()
								.setStacking(PlotOptions.Stacking.NORMAL))
				.setToolTip(new ToolTip().setFormatter(getChartTooltipFormatter(chartVo.getChartType())));
//						
//						new ToolTipFormatter() {
//					public String format(ToolTipData toolTipData) {
////						com.google.gwt.i18n.client.NumberFormat nfGWT = com.google.gwt.i18n.client.NumberFormat
////								.getDecimalFormat().overrideFractionDigits(2);
////						String ytitle = nfGWT.format(new BigDecimal(toolTipData
////								.getYAsDouble()));
//						String ytitle = KpiContext.formatDoubleDefault(toolTipData
//								.getYAsDouble());
////								AS2ClientContext.formatDouble(toolTipData
////								.getYAsDouble());
//						return "<b>" + toolTipData.getSeriesName()
//								+ ", godine " + toolTipData.getXAsString()
//								+ "</b><br/>" + "Populacija: "
//								+ ytitle.replace("-", "") + "%";
//					}
//				}));

		this.getXAxis(0).setCategories(chartVo.getXCategories())
				.setReversed(false);

		this.getXAxis(1).setCategories(chartVo.getXCategories()).setLinkedTo(0)
				.setOpposite(true).setReversed(false);

		this.getYAxis().setAxisTitleText(chartVo.getYTitle())
				// .setMin(-25)
				// .setMax(25);
				.setLabels(
						new YAxisLabels()
								.setFormatter(getBarNegStackAxisLabelsFormatter()));
										
//										new AxisLabelsFormatter() {
//									public String format(
//											AxisLabelsData axisLabelsData) {
//										return NumberFormat
//												.getDecimalFormat()
//												.overrideFractionDigits(0)
//												.format(Math.abs(axisLabelsData
//														.getValueAsDouble()))
//												+ "%";
//									}
//								}));

		_chartVo.addSeriesToChart(this);
//		//TODO testirati
		this.setSeriesPlotOptions(new SeriesPlotOptions()
		.setSeriesClickEventHandler(getChartSeriesClickEventHandler(chartVo.getChartType())));
				
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

}
