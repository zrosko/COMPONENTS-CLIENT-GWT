package hr.adriacomsoftware.app.client.smartgwt.kpi.views;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class EastLayoutTabSet extends TabSet {

	TabSet eastLayoutTabSet;
	Tab _kpiTab;
	Tab _homeTab;

	public EastLayoutTabSet() {
		this.setWidth100();
		this.setHeight100();
		this.setBorder("0px");
//		this.setStyleName("crm-ContextArea");
		this.setOverflow(Overflow.HIDDEN);
		_kpiTab = KpiTab.getInstance();
		_homeTab = HomeTab.getInstance();
//		KpiContext._kpiTab = getKpiTab();
//		KpiContext._homeTab = getHomeTab();
//		this.setTabs(KpiContext._homeTab,KpiContext._kpiTab);
		this.setTabs(_homeTab,_kpiTab);
		this.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(event.getTab() == _homeTab)
					KpiContext._breadCrumbLabel.setMembers(new BreadCrumbLabel(event.getTab().getTitle()));
//				else if(event.getTab() == KpiContext._kpiTab){
//					if(KpiContext._kpiTab.getPane()!=null)
				else if(event.getTab() == _kpiTab){
					if(_kpiTab.getPane()!=null)
						KpiContext.setBreadCrumbs();
					else{
						KpiContext._breadCrumbLabel.setMembers(new BreadCrumbLabel(event.getTab().getTitle()));
					}
				}
			}
		});
	}

//	private Tab getKpiTab() {
//		Tab tab = new Tab("KPI");
////		VLayout layout = new VLayout();
////		layout.setStyleName("crm-ContextArea");
////		layout.setHeight100();
////		layout.setHeight100();
////		tab.setPane(layout);
//		return tab;
//	}

//	private Tab getHomeTab() {
//		HomeTab tab = new HomeTab();
//	
////    	Criteria criteria = new Criteria();
////		criteria.addCriteria("razina1",tab.getTitle());
////		criteria.addCriteria("tab","naslov");
////		criteria.addCriteria("chart_type","Line");
////		ChartsTransportModel.getInstance().fetchData(criteria,new DSCallback() {
////			
////			@Override
////			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
////				boolean error = ChartsTransportModel.checkErrorsFormServer(dsResponse);
////				if(!error){
////					AS2ChartsEngine.serverCallBack(dsResponse.getDataAsRecordList());
////				}
////			}
////		});
////        hLayout.addMember(createChart1());
////        hLayout.addMember(createChart2());
////        layout.addMember(hLayout);
////		tab.setPane(layout);
//		return tab;
//	}

//	private ResizeableChartCanvas getChartInCanvas(BaseChart<?> chart){
//		ResizeableChartCanvas canvas = new ResizeableChartCanvas(chart);
//		canvas.setHeight100();
//		canvas.setWidth100();
//		return canvas;
//
//	}

//	private ResizeableChartCanvas createChart1() {
//
//        final Chart chart = new Chart()
//            .setType(Series.Type.LINE)
//           	.setPlotBorderWidth(1)
//           	.setShadow(true)
//            .setChartTitleText("Fruit Consumption By Day")
//            .setCredits(new Credits()
//                .setEnabled(false)
//            )
//            .setToolTip(new ToolTip()
//                .setFormatter(new ToolTipFormatter() {
//                    public String format(ToolTipData toolTipData) {
//                        return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +
//                            toolTipData.getXAsString() + ": " + toolTipData.getYAsLong();
//                    }
//                })
//            )
//            .setReflow(false);
//
//        chart.getXAxis()
//            .setCategories(
//                "Mon", "Tue", "Wed", "Thu", "Fri"
//            );
//
//        chart.addSeries(chart.createSeries()
//            .setName("Jane")
//            .setType(Series.Type.AREA)
//            .setPoints(new Number[]{3, 2, 3, 2, 4})
//        );
//        chart.addSeries(chart.createSeries()
//            .setName("John")
//            .setType(Series.Type.SPLINE)
//            .setPoints(new Number[]{2, 3, 5, 6, 6})
//        );
//        chart.addSeries(chart.createSeries()
//            .setName("Joe")
//            .setPoints(new Number[]{5, 5, 3, 0, 9})
//        );
//
//    	ResizeableChartCanvas canvas = new ResizeableChartCanvas(chart);
//		canvas.setHeight100();
//		canvas.setWidth100();
//		return canvas;
//    }

//    private ResizeableChartCanvas createChart2() {
//
//        final Chart chart = new Chart()
//            .setType(Series.Type.PIE)
//            .setPlotBorderWidth(1)
//           	.setShadow(true)
//            .setChartTitleText("Totals")
//            .setMargin(30, 0, 0, 0)
//            .setLegend(new Legend()
//                .setEnabled(false)
//            )
//            .setCredits(new Credits()
//                .setEnabled(false)
//            )
//            .setExporting(new Exporting()
//                .setEnabled(false)
//            )
//            .setToolTip(new ToolTip()
//                .setFormatter(new ToolTipFormatter() {
//                    public String format(ToolTipData toolTipData) {
//                        return "<b>" + toolTipData.getPointName() + "</b>:<br/>" +
//                            toolTipData.getYAsLong() + " fruit items<br/>" +
//                            "(" + NumberFormat.getFormat("#.#").format(toolTipData.getPercentage()) + "%)";
//                    }
//                })
//            )
//            .setWidth(200)
//            .setHeight(200)
//            .setReflow(false);
//
//        chart.addSeries(chart.createSeries()
//            .setName("Total consumption")
//            .setPoints(new Point[]{
//                new Point(13).setName("Jane"),
//                new Point(23).setName("John"),
//                new Point(19).setName("Joe"),
//            })
//            .setPlotOptions(new PiePlotOptions()
//                .setAllowPointSelect(true)
//                .setCursor(PlotOptions.Cursor.POINTER)
//                .setCenter(0.5, 0.5)
//                .setSize(123)
//                .setShowInLegend(false)
//                .setPieDataLabels(new PieDataLabels()
//                    .setEnabled(true)
//                    .setDistance(-30)
//                    .setY(-2)
//                    .setColor("#F0F0F0")
//                    .setStyle(new Style()
//                        .setFontWeight("bold")
//                    )
//                    .setFormatter(new DataLabelsFormatter() {
//                        public String format(DataLabelsData dataLabelsData) {
//                            return dataLabelsData.getPointName() + ":<br/>" + dataLabelsData.getYAsLong();
//                        }
//                    })
//                )
//
//            )
//        );
//    	ResizeableChartCanvas canvas = new ResizeableChartCanvas(chart);
//		canvas.setHeight100();
//		canvas.setWidth100();
//		return canvas;
//    }
}
