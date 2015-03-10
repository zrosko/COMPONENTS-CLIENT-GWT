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
import org.moxieapps.gwt.highcharts.client.Pane;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.XAxis;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

public class AS2SpiderWebChart extends AS2Chart {

	public AS2SpiderWebChart() {
		super();
	}

	@Override
	public ResizeableChartCanvas getChart(ChartValueObject chartVo) {
		_chart = this;
		_chartVo = chartVo;
		this.setPolar(true)
				.setCredits(new Credits()
		                .setEnabled(ENABLE_CREDITS)
		            )
				.setShadow(SHOW_SHADOW)
				.setPlotBorderWidth(PLOT_BORDER_WIDTH)
				.setType(Series.Type.LINE)
				.setChartSubtitle(
						new ChartSubtitle().setText(chartVo.getSubtitle()))
				.setChartTitle(
						new ChartTitle().setText(AS2ChartConstants.CHART_TITLE_PREFIX
								+ chartVo.getChartTitle() + AS2ChartConstants.CHART_TITLE_SUFIX)
				)
				.setToolTip(new ToolTip().setShared(true).setValueSuffix(" kn"))
				.setLegend(
						new Legend().setAlign(Legend.Align.RIGHT)
								.setVerticalAlign(Legend.VerticalAlign.TOP)
								.setY(100).setLayout(Legend.Layout.VERTICAL))
				.setPane(new Pane().setOption("size", "80%"));

		this.getXAxis().setTickmarkPlacement(XAxis.TickmarkPlacement.ON)
				.setLineWidth(0).setCategories(chartVo.getXCategories());

		this.getYAxis().setOption("gridLineInterpolation", "polygon")
				.setLineWidth(0).setMin(0);

		chartVo.addSeriesToChart(this);
		this.setSeriesPlotOptions(new SeriesPlotOptions()
			.setSeriesClickEventHandler(getChartSeriesClickEventHandler(chartVo.getChartType())));
				
//				new SeriesClickEventHandler() {
//			@Override
//			public boolean onClick(SeriesClickEvent seriesClickEvent) {
//				chartSpiderWebSeriesClickEventHandler(seriesClickEvent);
//				return true;
//			}
//		}));
		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(this);
		return chartCanvas;
	}

	public static Series createCurrentSeriesForChart(Chart chart,String currentSeriesTitle,
			Double[] currentSeriesPoints) {
		return chart.createSeries()
			.setName(currentSeriesTitle)
			.setPoints(currentSeriesPoints)
			.setOption("pointPlacement", "on");
		}

}
