package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2PieBarNegStackChartOnClickWindow;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2SpiderWebChartOnClickWindow;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEventHandler;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsFormatter;

public abstract class AS2Chart extends Chart implements AS2ChartProperties {

	public Chart _chart;
	public ChartValueObject _chartVo;

	public AS2Chart() {
	}

	protected void getChartPointClickEventHandler(PointClickEvent pointClickEvent) {
		new AS2ChartsOnClickWindow(
				pointClickEvent, _chartVo);
	}

//	protected void chartSeriesClickEventHandler(
//			SeriesClickEvent seriesClickEvent) {
//		new AS2ChartsOnClickWindow(
//				seriesClickEvent, _chartVo);
//	}

//	protected void chartPieBarNegStackSeriesClickEventHandler(
//			SeriesClickEvent seriesClickEvent) {
//		new AS2PieBarNegStackChartOnClickWindow(
//				seriesClickEvent, _chartVo);
//
//	}

//	protected SeriesClickEventHandler getPieBarNegStackSeriesClickEventHandler() {
//		return new SeriesClickEventHandler() {
//			@Override
//			public boolean onClick(SeriesClickEvent seriesClickEvent) {
//				new AS2PieBarNegStackChartOnClickWindow(seriesClickEvent,_chartVo);
//				return true;
//			}
//		};
//
//	}

	protected SeriesClickEventHandler getChartSeriesClickEventHandler(final String type) {
		return new SeriesClickEventHandler() {
			@Override
			public boolean onClick(SeriesClickEvent seriesClickEvent) {
				switch (type) {
				case BASIC_PIE_HIGHCHART:
//					new AS2PieBarNegStackChartOnClickWindow(seriesClickEvent,_chartVo);
					break;
				case BAR_NEG_STACK_HIGHCHART:
					new AS2PieBarNegStackChartOnClickWindow(seriesClickEvent,_chartVo);
					break;
				case SPIDER_WEB_HIGHCHART:
					new AS2SpiderWebChartOnClickWindow(seriesClickEvent, _chartVo);
					break;
				default:
//					new AS2ChartsOnClickWindow(seriesClickEvent, _chartVo);
					break;
				}
				return true;
			}
		};
	}

//	protected void chartSpiderWebSeriesClickEventHandler(
//			SeriesClickEvent seriesClickEvent) {
//		new AS2SpiderWebChartOnClickWindow(seriesClickEvent, _chartVo);
//	}
	protected ToolTipFormatter getChartTooltipFormatter(final String type){
		switch (type) {
		case LINE_HIGHCHART:
			return getLineTooltipFormatter();
		case AREA_LINE_HIGHCHART:
			return getLineTooltipFormatter();
		case BASIC_PIE_HIGHCHART:
			return getBasicPieTooltipFormatter();
		case BAR_NEG_STACK_HIGHCHART:
			return getBarNegStackTooltipFormatter();
		default:
			return null;
		}
	}

	/************************************Line Highchart*******************************/
	protected ToolTipFormatter getLineTooltipFormatter(){
		return new ToolTipFormatter() {
			public String format(ToolTipData toolTipData) {
//				com.google.gwt.i18n.client.NumberFormat nfGWT = com.google.gwt.i18n.client.NumberFormat
//						.getDecimalFormat().overrideFractionDigits(2);
				return "<b>"
						+ toolTipData.getSeriesName()
						+ "</b><br/>"
						+ toolTipData.getXAsString()
						+ ": "
//						+ nfGWT.format(new BigDecimal(toolTipData
//								.getYAsDouble()));
						+KpiContext.formatDoubleDefault(toolTipData.getYAsDouble());
			};
		};
	}
	/************************************BasicPie Highchart*******************************/
	protected ToolTipFormatter getBasicPieTooltipFormatter(){
		return new ToolTipFormatter() {
			public String format(ToolTipData toolTipData) {
				return "<b>" + toolTipData.getPointName() + "</b>: "
						+ KpiContext.formatDoubleNoDecimals(toolTipData.getYAsDouble());
			}
		};
	}
	
	protected DataLabelsFormatter getBasicPieDataLabelsFormatter(){
		return new DataLabelsFormatter() {
			public String format(DataLabelsData dataLabelsData) {
				return "<b>"
						+ dataLabelsData.getPointName()
						+ "</b>: "
						+ KpiContext.formatDoubleNoDecimals(dataLabelsData.getYAsDouble());
			}
		};
	}
	
	/************************************BarNegStack Highchart*******************************/
	protected ToolTipFormatter getBarNegStackTooltipFormatter() {
		return new ToolTipFormatter() {
			public String format(ToolTipData toolTipData) {
				// com.google.gwt.i18n.client.NumberFormat nfGWT =
				// com.google.gwt.i18n.client.NumberFormat
				// .getDecimalFormat().overrideFractionDigits(2);
				// String ytitle = nfGWT.format(new BigDecimal(toolTipData
				// .getYAsDouble()));
				String ytitle = KpiContext.formatDoubleDefault(toolTipData
						.getYAsDouble());
				// AS2ClientContext.formatDouble(toolTipData
				// .getYAsDouble());
				return "<b>" + toolTipData.getSeriesName() + ", godine "
						+ toolTipData.getXAsString() + "</b><br/>"
						+ "Populacija: " + ytitle.replace("-", "") + "%";
			}
		};
	}

	protected AxisLabelsFormatter getBarNegStackAxisLabelsFormatter() {
		return new AxisLabelsFormatter() {
			public String format(AxisLabelsData axisLabelsData) {
				return KpiContext.formatDoubleNoDecimals(Math.abs(axisLabelsData.getValueAsDouble()))+ "%";
						
						
//						NumberFormat.getDecimalFormat()
//						.overrideFractionDigits(0)
//						.format(Math.abs(axisLabelsData.getValueAsDouble()))
//						+ "%";
			}
		};
	}

	/************************************BarNegStack Highchart*******************************/

	
	
	
	
	
	
	
	
	
	
}
