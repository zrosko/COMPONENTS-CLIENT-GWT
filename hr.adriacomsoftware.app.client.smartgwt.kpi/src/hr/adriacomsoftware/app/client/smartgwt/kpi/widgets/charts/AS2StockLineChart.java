package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2ChartProperties;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2StockChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ResizeableChartCanvas;
import hr.as2.inf.common.charts.AS2ChartConstants;

import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Exporting;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.RangeSelector;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.StockChart;
import org.moxieapps.gwt.highcharts.client.events.SeriesCheckboxClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesCheckboxClickEventHandler;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEventHandler;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;


public class AS2StockLineChart extends AS2StockChart implements AS2ChartProperties{
	
	public AS2StockLineChart() {
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
				.setRangeSelector(new RangeSelector()
						.setEnabled(false)
//                		.setInputEnabled(true)
//                		.setInputEditDateFormat("%d.%m.%y")
//                		.setButtons(
//					       new RangeSelector.Button()
//					          .setType(RangeSelector.ButtonType.MONTH)
//					          .setCount(1)
//					          .setText("1m"),
//					       new RangeSelector.Button()
//					          .setType(RangeSelector.ButtonType.MONTH)
//					          .setCount(3)
//					          .setText("3m"),
//					       new RangeSelector.Button()
//					          .setType(RangeSelector.ButtonType.YTD)
//					          .setText("YTD"),
//					       new RangeSelector.Button()
//					          .setType(RangeSelector.ButtonType.YEAR)
//					          .setCount(1)
//					          .setText("1g"),
//					       new RangeSelector.Button()
//					          .setType(RangeSelector.ButtonType.ALL)
//					          .setText("Sve")
					    )
//                		.setButtons(
//					        new RangeSelector.Button()
//					           .setType(RangeSelector.ButtonType.DAY)
//					           .setCount(1)
//					           .setText("1d"),
//					        new RangeSelector.Button()
//					           .setType(RangeSelector.ButtonType.WEEK)
//					           .setCount(2)
//					           .setText("2w"),
//					        new RangeSelector.Button()
//					           .setType(RangeSelector.ButtonType.MONTH)
//					           .setCount(3)
//					           .setText("2m")
//					     )
//					    .setSelected(1)
//					    .setButtonSpacing(10)
//					    )
				.setChartTitle(new ChartTitle()
						.setText(AS2ChartConstants.CHART_TITLE_PREFIX+chartVo.getChartTitle()+AS2ChartConstants.CHART_TITLE_SUFIX)
				)
				.setChartSubtitle(
						new ChartSubtitle().setText(chartVo.getSubtitle())
				)
				.setExporting(new Exporting()
                		.setEnabled(false)
					)
				.setReflow(false)
				.setLegend(
						new Legend().setLayout(Legend.Layout.HORIZONTAL)
								.setAlign(Legend.Align.CENTER)
								.setVerticalAlign(Legend.VerticalAlign.BOTTOM)
				);
								
		this.getXAxis().setMaxZoom(14 * 24 * 3600000); // fourteen days
		this.getYAxis().setAxisTitleText(chartVo.getYTitle());
		chartVo.addSeriesToChart(this);

		//TODO testirati
		this.setSeriesPlotOptions(new SeriesPlotOptions()
		.setSeriesCheckboxClickEventHandler(new SeriesCheckboxClickEventHandler() {
			
			@Override
			public boolean onClick(SeriesCheckboxClickEvent seriesCheckboxClickEvent) {
				System.out.println("KLIKUNO SAM seriesCheckboxClickEvent");
				return false;
			}
		})	
		.setSeriesClickEventHandler(new SeriesClickEventHandler() {
//
			@Override
			public boolean onClick(SeriesClickEvent seriesClickEvent) {
				System.out.println("KLIKUNO SAM SeriesClickEvent");
//				chartSeriesClickEventHandler(seriesClickEvent);
				return true;
			}
		}));
		
        ResizeableChartCanvas canvas = new ResizeableChartCanvas(this);
		return canvas;
	}

	public static Series createCurrentSeriesForChart(StockChart stockChart,
			String currentSeriesTitle, Number[][] currentSeriesPoints) {
		return stockChart.createSeries()
				.setName(currentSeriesTitle)
				.setPoints(currentSeriesPoints);
	}
	

//	            {t(2008, 1, 31), 0.6728},
//	            {t(2008, 2, 29), 0.6588},
//	            {t(2008, 3, 31), 0.6346},
//	            {t(2008, 4, 30), 0.64},
//	            {t(2008, 5, 31), 0.643},
//	            {t(2008, 6, 30), 0.6345},
//	            {t(2008, 7, 31), 0.6418},
//	            {t(2008, 8, 31), 0.6816},
//	            {t(2008, 9, 30), 0.7083},
//	            {t(2008, 10, 31), 0.7855},
//	            {t(2008, 11, 30), 0.7073},
//	            {t(2008, 12, 31), 0.7074},
//	            {t(2009, 1, 31), 0.7896},
//	            {t(2009, 2, 28), 0.7583},
//	            {t(2009, 3, 31), 0.7552},
//	            {t(2009, 4, 30), 0.7065},
//	            {t(2009, 5, 31), 0.7069},
//	            {t(2009, 6, 30), 0.71},
//	            {t(2009, 7, 31), 0.6975},
//	            {t(2009, 8, 31), 0.6825},
//	            {t(2009, 9, 30), 0.6789},
//	            {t(2009, 10, 31), 0.6651},
//	            {t(2009, 11, 30), 0.6974},
//	            {t(2009, 12, 31), 0.6979},
//	            {t(2010, 1, 31), 0.7337},
//	            {t(2010, 2, 28), 0.7393},
//	            {t(2010, 3, 31), 0.7524},
//	            {t(2010, 4, 30), 0.8149},
//	            {t(2010, 5, 31), 0.8179},
//	            {t(2010, 6, 30), 0.7663},
//	            {t(2010, 7, 31), 0.7891},
//	            {t(2010, 8, 31), 0.7337},
//	            {t(2010, 9, 30), 0.7171},
//	            {t(2010, 10, 31), 0.7695},
//	            {t(2010, 11, 30), 0.7519},
//	            {t(2010, 12, 31), 0.7292},
//	            {t(2011, 1, 31), 0.7235},
//	            {t(2011, 2, 28), 0.7082},
//	            {t(2011, 3, 31), 0.6753},
//	            {t(2011, 4, 30), 0.6945}
//	        };
//	    }
//
//	    private static Number d(String number) {
//	    	return new BigDecimal(number);
//	    }
//
//		static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
//	    
//	    private static long t(String date) {
//	        return AS2ClientContext.formatStringDateToDate(date).getTime();
//	    }
//
//	    private static long t(int year, int month, int day) {
//	        return dateTimeFormat.parse(year + "-" + (month + 1) + "-" + day).getTime();
//	    }


}
