package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.HomeTab;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation.PokazateljiTreeNode;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2BarNegStackChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2BasicPieChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2LineChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2SpiderWebChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2StockLineChart;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.PortalLayout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AS2ChartsEngine {

	private static String CHART = "chart";

	public AS2ChartsEngine() {
	}

	public static RecordList getChartsFromServer(RecordList data) {
		RecordList list = new RecordList();
		List<RecordList> chartList = new ArrayList<RecordList>();
		for (int i = 0; i < data.toArray().length; i++) {
			Record current = data.get(i);
			current.getAttributes();
			if (current.getAttributeAsRecordList(CHART) != null) {
				chartList.add(current.getAttributeAsRecordList(CHART));
			}
		}
		for (RecordList charts : chartList) {
			ChartValueObject chartVo = new ChartValueObject();
			for (int j = 0; j < charts.toArray().length; j++) {
				Record chartData = charts.get(j);
				chartData.getAttributes();
				chartVo.setChart(chartData);
			}
			list.add(chartVo);

		}
		return list;
	}

	public static void serverCallBack(RecordList dsResponseData) {
		if(ChartsTransportModel._selectedPokazatelj!=null)
			((PokazateljiTreeNode) ChartsTransportModel._selectedPokazatelj).setChartRs(getChartsFromServer(dsResponseData));
		AS2ChartsEngine.createCharts(getChartsFromServer(dsResponseData));
	}

	public static void createCharts(RecordList chartRs) {
		KpiContext.setBreadCrumbs();
		VLayout chartLayout  = new VLayout();
		chartLayout.setMembersMargin(0);
		if(ChartsTransportModel._selectedPokazatelj!=null){
			if (KpiContext.USE_PORTLETS) {
				chartLayout = putChartsInPortlets(chartRs);
			} else {
				chartLayout = putChartsInVLayout(chartRs);
			}
			ChartsTransportModel._selectedPokazatelj.getTab().setPane(chartLayout);
			ChartsTransportModel._selectedPokazatelj.getTab().getTabSet()
				.selectTab(ChartsTransportModel._selectedPokazatelj.getTab());
		}
		else{
			chartLayout = putChartsInHomeVLayout(chartRs);
//			KpiContext._homeTab.setPane(chartLayout);
			HomeTab.getInstance().setPane(chartLayout);
		}
	}
	
	//TODO to remove
//	private static ResizeableChartCanvas createChart3() {

//        final StockChart chart = new StockChart()
//			        .setCredits(new Credits()
//			        	.setEnabled(false)
//			        	)
//			        	.setPlotBorderWidth(1)
//            .setChartTitle(new ChartTitle()
//                .setText("USD to EUR")
//                .setFloating(true)
//                .setAlign(ChartTitle.Align.RIGHT)
//                .setX(-20)
//                .setY(20)
//            )
//		      .setRangeSelector(new RangeSelector()
//                .setSelected(1)
//                .setInputEnabled(false)
//                .setButtons(
//                		new Button().setCount(1).setText("1g").setType(ButtonType.YEAR),
//                		new Button().setCount(3).setText("3g").setType(ButtonType.YEAR),
//                		new Button().setText("YTD").setType(ButtonType.YTD),
//                		new Button().setText("Sve").setType(ButtonType.ALL)
//                )
//            )
//				.setPlotBorderWidth(1)
//				.setChartTitle(new ChartTitle()
//						.setText(AS2Chart.CHART_TITLE_PREFIX+"CIR"+AS2Chart.CHART_TITLE_SUFIX)
//				)
//				.setChartSubtitle(
//						new ChartSubtitle().setText("Profitni centar: 0,Godina: 2008-2011")
//				)
//            .setExporting(new Exporting()
//                .setEnabled(false)
//            )
//            .setWidth100()
//            .setHeight100()
//            .setReflow(false);
//        chart.getXAxis()
//            .setMaxZoom(14 * 24 * 3600000); // fourteen days
//        chart.addSeries(chart.createSeries()
//            .setName("USD to EUR")
//            .setPoints(getUSDtoEURData())
//        );
//        
//        ResizeableChartCanvas canvas = new ResizeableChartCanvas(chart);
//		canvas.setHeight100();
//		canvas.setWidth100();
//		return canvas;

//        return createCanvas(chart,0,0);
//    }

//	 private static Canvas createCanvas(final BaseChart chart, int requestedWidth, int requestedHeight) {
//	        final Canvas chartContainer = new Canvas();
//	        final WidgetCanvas widgetCanvas = new WidgetCanvas(chart);
//	        widgetCanvas.setBorder("1px solid #C0C3C7");
//
//	        /*
//	        widgetCanvas.addResizedHandler(new ResizedHandler() {
//	            public void onResized(ResizedEvent event) {
//	                chart.setSize(widgetCanvas.getWidth(), widgetCanvas.getHeight(), false);
//	            }
//	        });
//	        */
//
//	        chartContainer.addResizedHandler(new ResizedHandler() {
//	            public void onResized(ResizedEvent event) {
//	                chart.setSize(chartContainer.getWidth() - 4, chartContainer.getHeight() - 4, false);
//	            }
//	        });
//	        chartContainer.addDrawHandler(new DrawHandler() {
//	            public void onDraw(DrawEvent event) {
//	                chart.setSize(chartContainer.getWidth() - 4, chartContainer.getHeight() - 4, false);
//	            }
//	        });
//	        if (requestedWidth > 0) {
//	            widgetCanvas.setWidth(requestedWidth);
//	            chartContainer.setWidth(requestedWidth);
//	        } else {
//	            widgetCanvas.setWidth100();
//	            chartContainer.setWidth("*");
//	        }
//	        if (requestedHeight > 0) {
//	            widgetCanvas.setHeight(requestedHeight);
//	            chartContainer.setHeight(requestedHeight);
//	        } else {
//	            widgetCanvas.setHeight100();
//	            chartContainer.setHeight("*");
//	        }
//	        chartContainer.addChild(widgetCanvas);
//	        return chartContainer;
//	    }
	
	
	private static VLayout putChartsInHomeVLayout(RecordList chartRs) {
		VLayout chartLayout = new VLayout();
		chartLayout.setHeight(450);
		HLayout firstRow = new HLayout();
		firstRow.setHeight(180);
		for (int i=0;i<chartRs.getLength();i++) {
			ChartValueObject chartVo = (ChartValueObject) chartRs.get(i);
			for(String attribute: chartVo.getAttributes()){
				chartVo.getAttribute(attribute);
			}
			ResizeableChartCanvas chartCanvas = null;
			chartCanvas = putChartInCanvas(chartVo);
			if(i<=1){
				firstRow.addMember(chartCanvas);
			}else{
				//UNCOMMENT when theres 3 charts in home tab
				if(chartVo.getChartType().contains("StockLineChart")){
					chartCanvas.setWidth100();
					chartCanvas.setHeight100();
					chartLayout.addMember(firstRow);
					chartLayout.addMember(chartCanvas);
				}
			}
		}
		//TODO to remove
//		chartLayout.addMember(firstRow);
//		chartLayout.addMember(createChart3());
		//to remove
		chartLayout.setOverflow(Overflow.VISIBLE);
		return chartLayout;
//		layout.addMember(chartLayout);
//		return layout;

	}

	private static VLayout putChartsInVLayout(RecordList chartRs) {
//		VLayout layout = new VLayout();
		VLayout chartLayout = new VLayout();
		if(ChartsTransportModel._selectedPokazatelj!=null)
//			layout.addMember((ToolStrip) ((PokazateljiTreeNode) ChartsTransportModel._selectedPokazatelj)
//					.getChartToolstrip());
			chartLayout.addMember((ToolStrip) ((PokazateljiTreeNode) ChartsTransportModel._selectedPokazatelj)
					.getChartToolstrip());
//		VLayout chartLayout = new VLayout();
		for (int i=0;i<chartRs.getLength();i++) {
			ChartValueObject chartVo = (ChartValueObject) chartRs.get(i);
			ResizeableChartCanvas chartCanvas = null;
			chartCanvas = putChartInCanvas(chartVo);
			int height = 550;
			int userHeight = com.google.gwt.user.client.Window
					.getClientHeight() - 165;
			chartCanvas.setHeight(userHeight < height ? userHeight : height);
			if (chartRs.getLength() > 1)
				chartCanvas.setShowResizeBar(true);
			chartLayout.addMember(chartCanvas);

		}
		chartLayout.setOverflow(Overflow.AUTO);
		return chartLayout;
//		layout.addMember(chartLayout);
//		return layout;
	}

	private static VLayout putChartsInPortlets(RecordList chartRs) {
		VLayout layout = new VLayout();
		if(ChartsTransportModel._selectedPokazatelj!=null)
			layout.addMember((ToolStrip) ((PokazateljiTreeNode) ChartsTransportModel._selectedPokazatelj).getChartToolstrip());
		PortalLayout portalLayout;
		if (chartRs.getLength() > 1)
			portalLayout = new PortalLayout(2);
		else {
			portalLayout = new PortalLayout(0);
		}
		portalLayout.setWidth100();
		portalLayout.setHeight100();
		portalLayout.setShowColumnMenus(false);
		portalLayout.setShowEdges(false);
		portalLayout.setCanResizePortlets(true);
		portalLayout.setColumnBorder("0px");
		int colCount = 0;
		int rowCount = 0;
		for (int i=0;i<chartRs.getLength();i++) {
			ChartValueObject chartVo = (ChartValueObject) chartRs.get(i);
			ResizeableChartCanvas chartCanvas = null;
			chartCanvas = putChartInCanvas(chartVo);
			final Portlet portlet = new Portlet();
			portlet.addItem(chartCanvas);
			portlet.setTitle(chartVo.getTitle());
			portlet.setHeaderControls(HeaderControls.HEADER_LABEL,HeaderControls.MAXIMIZE_BUTTON);
			portlet.setShowTitle(true);
			portlet.setCloseConfirmationMessage("Je ste li sigurni da Ĺľelite zatvoriti dijagram?");
			if (colCount == 2){
				colCount = 0;
				rowCount++;
			}
			portalLayout.addPortlet(portlet, colCount++, rowCount);

		}
		layout.addMember(portalLayout);
		return layout;
	}

	private static ResizeableChartCanvas putChartInCanvas(ChartValueObject chartVo){
			ResizeableChartCanvas chartCanvas = null;
			if (chartVo.getChartType().equals("BarNegStack")) {
				chartCanvas = createBarNegStackChart(chartVo);
			} else if (chartVo.getChartType().equals("BasicPie")) {
				chartCanvas = createBasicPieChart(chartVo);
			} else if (chartVo.getChartType().equals("SpiderWeb")) {
				chartCanvas = createSpiderWebChart(chartVo);
			} else if (chartVo.getChartType().equals("StockLineChart")) {
				chartCanvas = createStockLineChart(chartVo);
			}else {
				chartCanvas = createLineChart(chartVo);
			}
		
		KpiContext._currentChart = chartCanvas.getChart();
		chartCanvas.setHeight100();
		chartCanvas.setWidth100();
		return chartCanvas;
	}

	private static ResizeableChartCanvas createStockLineChart(
			ChartValueObject chartVo) {
		AS2StockLineChart chart = new AS2StockLineChart();
		return chart.getChart(chartVo);
	}

	public static ResizeableChartCanvas createLineChart(
			final ChartValueObject chartVo) {
		AS2LineChart chart = new AS2LineChart();
		return chart.getChart(chartVo);
	}

	public static ResizeableChartCanvas createBarNegStackChart(
			final ChartValueObject chartVo) {
		AS2BarNegStackChart chart = new AS2BarNegStackChart();
		return chart.getChart(chartVo);
	}

	public static ResizeableChartCanvas createBasicPieChart(
			final ChartValueObject chartVo) {
		AS2BasicPieChart chart = new AS2BasicPieChart();
		return chart.getChart(chartVo);
	}

	public static ResizeableChartCanvas createSpiderWebChart(
			final ChartValueObject chartVo) {
		AS2SpiderWebChart chart = new AS2SpiderWebChart();
		return chart.getChart(chartVo);
	}

//	protected static void chartPointClickEvent(PointClickEvent clickEvent,
//			ChartValueObject chartVo) {
//		AS2PrintableGridWindow win = new AS2PrintableGridWindow(clickEvent,
//				chartVo);
//		ChartsTransportModel._selectedPokazatelj.getTab().getPane()
//				.addChild(win);
//		win.show();
//	}
}