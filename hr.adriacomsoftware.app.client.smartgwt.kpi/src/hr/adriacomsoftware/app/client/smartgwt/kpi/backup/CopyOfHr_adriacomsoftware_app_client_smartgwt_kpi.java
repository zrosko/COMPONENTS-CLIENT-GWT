package hr.adriacomsoftware.app.client.smartgwt.kpi.backup;

import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ResizeableChartCanvas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.fields.IPickTreeItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CopyOfHr_adriacomsoftware_app_client_smartgwt_kpi implements EntryPoint {

	HLayout mainLayout;
	ToolStrip eastLayoutToolStrip;
	VLayout eastLayout;
	VLayout westLayout;
	DataSource _dataSource;

	SelectItem _profitniCentar;
	SelectItem _godina;
	LinkedHashMap<Integer, String> profitni_centri;
//	Tree pokazateljiTree;
	TreeGrid pokazateljiTreeGrid;
//	ResizeableChartCanvas chartCanvas;
	static Criteria _criteriaForServer;
	static List<ChartValueObject> chartsRs;
//	List<RecordList> chartList;
//	Chart chart;

	public void onModuleLoad() {
		mainLayout = new HLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		_dataSource = getRestModel();
		westLayout = getWestLayout();
		eastLayoutToolStrip = getEastLayoutToolStrip();
		eastLayout = getEastLayout();
		eastLayout.addMember(eastLayoutToolStrip);
		mainLayout.addMember(westLayout);
		mainLayout.addMember(eastLayout);
		nativeSetGlobalOptions();
		mainLayout.draw();
	}

	private RestDataSource getRestModel() {
		RestDataSource dataSource = new RestDataSource() {
			@Override
			protected Object transformRequest(DSRequest dsRequest) {
				return super.transformRequest(dsRequest);
			}

			@Override
			protected void transformResponse(DSResponse response,
					DSRequest request, Object data) {
				super.transformResponse(response, request, data);
				if (response.getTotalRows() != 0) {
					chartsRs=getChartsFromServer(response);
					createCharts(chartsRs,_criteriaForServer.getAttribute("chartTitle"));
				} else {
					SC.say("Greška");
				}

			}
		};

		// dataSource = new DataSource();
		dataSource.setDataFormat(DSDataFormat.JSON);
		dataSource.setID("servletJson");
		// dataSource.setClientOnly(false);
		// dataSource.setUseStrictJSON(true);
		dataSource.setDataURL(GWT.getModuleBaseURL() + "servletjson");
		// dataSource.fetchData();
		// // set up FETCH to use GET requests
		// OperationBinding fetch = new OperationBinding();
		// fetch.setOperationType(DSOperationType.FETCH);
		// fetch.setDataProtocol(DSProtocol.GETPARAMS);
		// DSRequest fetchProps = new DSRequest();
		// fetchProps.setHttpMethod("GET");
		// fetch.setRequestProperties(fetchProps);
		//
		// // set up ADD to use POST requests
		// OperationBinding add = new OperationBinding();
		// add.setOperationType(DSOperationType.ADD);
		// add.setDataProtocol(DSProtocol.POSTMESSAGE);
		// DSRequest addProps = new DSRequest();
		// addProps.setHttpMethod("POST");
		// addProps.setContentType("application/json");
		// add.setRequestProperties(addProps);
		//
		// // set up UPDATE to use PUT
		// OperationBinding update = new OperationBinding();
		// update.setOperationType(DSOperationType.UPDATE);
		// update.setDataProtocol(DSProtocol.POSTMESSAGE);
		// DSRequest updateProps = new DSRequest();
		// updateProps.setHttpMethod("PUT");
		// update.setRequestProperties(updateProps);
		//
		// // set up REMOVE to use DELETE
		// OperationBinding remove = new OperationBinding();
		// remove.setOperationType(DSOperationType.REMOVE);
		// DSRequest removeProps = new DSRequest();
		// removeProps.setHttpMethod("DELETE");
		// remove.setRequestProperties(removeProps);
		//
		// dataSource.setOperationBindings(fetch,add,update,remove);

		// chartSelector.setDataSource(dataSource);
		return dataSource;
	}

	protected void createCharts(List<ChartValueObject> chartsRs, String chartTitle) {
		List<Layout> chartCanvasList = new ArrayList<Layout>();
		chartCanvasList.add(eastLayoutToolStrip);
		for(ChartValueObject chartVo:chartsRs){
			chartVo.setTitle(chartTitle);
			ResizeableChartCanvas chartCanvas = createLineChart(chartVo);
			VLayout chartVLayout = new VLayout();
	        chartVLayout.addMember(chartCanvas);
			chartCanvasList.add(chartVLayout);
		}
		eastLayout.setMembers(chartCanvasList.toArray(new Layout[chartCanvasList.size()]));
		mainLayout.setMembers(westLayout,eastLayout);
	}

	protected List<ChartValueObject> getChartsFromServer(DSResponse response) {
		List<ChartValueObject> list = new ArrayList<ChartValueObject>();
		RecordList data = response.getDataAsRecordList();
		List<RecordList> chartList = new ArrayList<RecordList>();
		for (int i = 0; i < data.toArray().length; i++) {
			Record current = data.get(i);
			current.getAttributes();
			if (current.getAttributeAsRecordList("chart") != null) {
				chartList.add(current.getAttributeAsRecordList("chart"));
			}
		}
		for (RecordList charts : chartList) {
			ChartValueObject chartVo = new ChartValueObject();
			for (int j = 0; j < charts.toArray().length; j++) {
				Record chartData = charts.get(j);
				chartVo.setChart(chartData);
			}
			list.add(chartVo);

		}

		return list;
	}

	private ToolStrip getEastLayoutToolStrip() {
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setPadding(0);
		toolStrip.setMargin(0);
		_profitniCentar = new SelectItem("profitni_centar", "Profitni Centar");
		_profitniCentar.setWidth(200);
		profitni_centri = new LinkedHashMap<Integer, String>();
		profitni_centri.put(0, "Svi");
		profitni_centri.put(22000, "Šibenik");
		profitni_centri.put(10000, "Zagreb");
		profitni_centri.put(21000, "Split");
		_profitniCentar.setAnimatePickList(true);
		_profitniCentar.setValueMap(profitni_centri);
		_profitniCentar.setDefaultToFirstOption(true);
		_profitniCentar.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				if (_criteriaForServer != null)
					callServer(event.getValue().toString(),_godina.getValueAsString(), _criteriaForServer);
			}
		});

		_godina = new SelectItem("_godina", "Godina");
		_godina.setValueMap("2013", "2012", "2011", "2010");
		_godina.setDefaultToFirstOption(true);
		_godina.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				if (_criteriaForServer != null)
					callServer(_profitniCentar.getValueAsString(), event.getValue().toString(), _criteriaForServer);
			}
		});

		final Tree timeTree = new Tree();
		timeTree.setModelType(TreeModelType.PARENT);
		timeTree.setRootValue("root");
		timeTree.setShowRoot(Boolean.TRUE);
		timeTree.setData(ChartData.getData());

		IPickTreeItem timeSelector = new IPickTreeItem("pokazatelji",
				"Izaberi dijagram");
		timeSelector.setWidth(60);
		timeSelector.setValueTree(timeTree);
		timeSelector.setCanSelectParentItems(Boolean.TRUE);
		timeSelector.setDisplayField("title");
		timeSelector.setValueField("id");
		timeSelector.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				// Criteria c = new Criteria("Products", "Prod01");
				// c.addCriteria("Regions", new String[] {"North", "South",
				// "East", "West"});
				// c.addCriteria("Time", (String) event.getValue());
				// final String newTitle = "Revenue for " +
				// timeTree.findById((String) event.getValue()).getTitle();
				// ds.fetchData(c, new DSCallback() {
				// public void execute(DSResponse response, Object rawData,
				// DSRequest request) {
				// chart.setTitle(newTitle);
				// chart.setData(response.getData());
				// }
				// });
			}
		});

		toolStrip.addFormItem(_profitniCentar);
		toolStrip.addSeparator();
		toolStrip.addFormItem(_godina);
		// chartSelector.setFields(chartType, timeSelector);
		// VLayout layout = new VLayout(15);
		return toolStrip;

	}

	private VLayout getEastLayout() {
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setOverflow(Overflow.AUTO);
		layout.setStyleName("crm-ContextArea");
		return layout;

	}

	private VLayout getWestLayout() {
		VLayout layout = new VLayout();
		layout.setWidth("20%");
		layout.setShowResizeBar(true);
		layout.setStyleName("crm-NavigationPane");

		pokazateljiTreeGrid = new TreeGrid();
		pokazateljiTreeGrid.setLoadDataOnDemand(false);
		pokazateljiTreeGrid.setWidth100();
		pokazateljiTreeGrid.setHeight100();

		TreeGridField field = new TreeGridField("Name", "Pokazatelji");
		field.setCanSort(false);

		pokazateljiTreeGrid.setFields(field);

		final Tree pokazateljiTree = new Tree();
		pokazateljiTree.setModelType(TreeModelType.PARENT);
		pokazateljiTree.setNameProperty("Name");
		pokazateljiTree.setIdField("Id");
		pokazateljiTree.setParentIdField("ParentId");
		pokazateljiTree.setShowRoot(false);

		PokazateljiTreeNode gradani = new PokazateljiTreeNode("gradani",
				"root", "Građani");
		PokazateljiTreeNode gr_klijenti = new PokazateljiTreeNode(
				"gr_klijenti", "gradani", "Klijenti");
		PokazateljiTreeNode gr_financije = new PokazateljiTreeNode(
				"gr_financije", "gradani", "Financije");
		PokazateljiTreeNode pravneosobe = new PokazateljiTreeNode(
				"pravneosobe", "root", "Pravne osobe");
		PokazateljiTreeNode po_klijenti = new PokazateljiTreeNode(
				"po_klijenti", "pravneosobe", "Klijenti");
		PokazateljiTreeNode po_financije = new PokazateljiTreeNode(
				"po_financije", "pravneosobe", "Financije");

		pokazateljiTree.setData(new TreeNode[] { gradani, gr_klijenti,
				gr_financije, pravneosobe, po_klijenti, po_financije });

		pokazateljiTreeGrid.addDrawHandler(new DrawHandler() {
			public void onDraw(DrawEvent event) {
				pokazateljiTree.openAll();
			}
		});
		pokazateljiTreeGrid.addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(NodeClickEvent event) {
				if (!event.getNode().getAttributeAsBoolean("isFolder")) {
					callServer(_profitniCentar.getValueAsString(),_godina.getValueAsString(),null);
//					_criteriaForServer = new Criteria();
//					String pokazateljId = event.getNode().getAttribute("Id");
//					_criteriaForServer.addCriteria("pokazateljId",pokazateljId );
//					String pokazatelj = event.getNode().getAttribute("Name");
//					_criteriaForServer.addCriteria("pokazatelj",pokazatelj);
//					_criteriaForServer.addCriteria("chartTitle", "<p style=\"font-weight:bold; font-size:16px;\">"
//							+ (pokazateljId.contains("gr") ? "Građani" : "Pravne osobe") + " - "
//							+ pokazatelj + " - "
//							+ _profitniCentar.getDisplayValue() + " - "
//							+ _godina.getValueAsString() + "</p>");
//					callServer(_profitniCentar.getValueAsString(),
//							_godina.getValueAsString(), _criteriaForServer);
				}
			}
		});
		pokazateljiTreeGrid.setData(pokazateljiTree);
		layout.addMember(pokazateljiTreeGrid);
		return layout;
	}

	private void callServer(String profitni_centar, String godina,Criteria criteria) {
		_criteriaForServer = new Criteria();
		if(criteria!=null)
			_criteriaForServer.addCriteria(criteria);
		String pokazateljId = pokazateljiTreeGrid.getSelectedRecord().getAttribute("Id");
		_criteriaForServer.addCriteria("pokazateljId",pokazateljId );
		String pokazatelj =  pokazateljiTreeGrid.getSelectedRecord().getAttribute("Name");
		_criteriaForServer.addCriteria("pokazatelj",pokazatelj);
		_criteriaForServer.addCriteria("chartTitle", "<p style=\"font-weight:bold; font-size:16px;\">"
				+ (pokazateljId.contains("gr") ? "Građani" : "Pravne osobe") + " - "
				+ pokazatelj + " - "
				+ _profitniCentar.getDisplayValue() + " - "
				+ _godina.getValueAsString() + "</p>");
		_criteriaForServer.addCriteria("profitni_centar", profitni_centar);
		_criteriaForServer.addCriteria("godina", godina);
		if (chartsRs != null && chartsRs.size() != 0) {
			boolean contains = false;
			for (ChartValueObject chartVo : chartsRs) {
				if (chartVo.getTitle() != null) {
					if (chartVo.getTitle().contains(_criteriaForServer.getAttribute("chartTitle"))) {
						contains = true;
					} else {
						contains = false;
					}
				}

			}
			if (contains) {
				System.out.println("NE ZOVEM SERVER");
				createCharts(chartsRs, _criteriaForServer.getAttribute("chartTitle"));
			}
			else {
				System.out.println("ZOVEM SERVER");
				_dataSource.fetchData(_criteriaForServer);
			}
		} else {
			System.out.println("ZOVEM SERVER");
			_dataSource.fetchData(_criteriaForServer);
		}
	}

	public static class PokazateljiTreeNode extends TreeNode {

		public PokazateljiTreeNode(String id, String parentId, String name) {
			setId(id);
			setParentId(parentId);
			setName(name);
		}

		public void setId(String value) {
			setAttribute("Id", value);
		}

		public void setParentId(String value) {
			setAttribute("ParentId", value);
		}

		public void setName(String name) {
			setAttribute("Name", name);
		}
	}

	private void getChartDataFromServer(DSResponse dsResponse){

				//				if (details.getAttribute("x") != null) {
//					chartDetails.setAttribute("x", chartDetails.getAttributeAsStringArray("x"));
////					x = chartDetails.getAttributeAsStringArray("x");
//				} else if (details.getAttribute("yTitle") != null) {
//					yTitle = details.getAttributeAsString("yTitle");
//				} else if (details.getAttribute("title") != null) {
//					title = details.getAttribute("title");
//				} else if (details.getAttribute("subtitle") != null) {
//					subtitle = details.getAttribute("subtitle");
//				}

//			}

//		}
//		data.get
//		Record[] chartDetails = data.getProperty("chartDetails");
//		recordsList = data.getProperty("chart");
//		data.get
//		for(Record rec:data){
//			rec.getAttributes();
//			if(rec.getAttribute(property))

//		}
//		Record chartDetails = dsResponse.getAttributeAsRecord("chartDetails");
//		for (Record chartDetail : chartDetails) {
//			if (chartDetail.getAttribute("x") != null) {
//				x = chartDetail.getAttributeAsStringArray("x");
//			}else if (chartDetail.getAttribute("yTitle") != null) {
//				yTitle = chartDetail.getAttributeAsString("yTitle");
//			}else if (chartDetail.getAttribute("title") != null) {
//				title = chartDetail.getAttribute("title");
//			}else if (chartDetail.getAttribute("subtitle") != null) {
//				subtitle = chartDetail.getAttribute("subtitle");
//			}
//		}
////		Record[] data = dsResponse.getData();
//		recordsList = dsResponse.getData();;
		// series = new HashMap<String, List<Float>>();
//		for (Record record : data) {
//			record.getAttributes();
////			if (data.getAttributeAsRecordArray("chartDetails").length != 0) {
////				// chartDetails =
////				// data.getAttributeAsRecordArray("chartDetails");
////				Record[] chartDetails = data
////						.getAttributeAsRecordArray("chartDetails");
////				for (Record details : chartDetails) {
////
////				}
////			}
//			if (record.getAttributeAsRecord("record").length != 0) {
//				recordsList = data.getAttributeAsRecordArray("records");
//				// for(Record aa : records.toArray()){
//				// aa.getAttributes();
//				// }
//				//
//				// for(String attribute:data.getAttributes()){
//				// if(!attribute.equalsIgnoreCase("chartDetails")){
//				// recordsList.add(data.getAttributeAsRecord(attribute));
//				// }
//				// }
//			}
//			// recordsArray = data.getAttributeAsRecordArray("record");
//			// for(Record serie: series){
//			// if(!attribute.equals("chartDetails")){
//			// for(String seriesName: serie.getAttributes()){
//			// seriesNames.add(seriesName);
//			// List<Float> listY = new ArrayList<Float>();
//			// for(String x:xAxis){
//			// listY.add(Float.parseFloat(serie.getAttribute(x)));
//			//
//			// }
//			// series.put(seriesName,listY);
//
//			// }
//			// }
//			// for(Record attribute: data.getAttributeAsRecordArray("record")){
//			// if(!attribute.equals("chartDetails")){
//			// attributes.add(attribute);
//			// Record serie = data.getAttributeAsRecord(attribute);
//			// List<Float> listY = new ArrayList<Float>();
//			// for(String x:xAxis){
//			// listY.add(Float.parseFloat(serie.getAttribute(x)));
//			//
//			// }
//			// series.put(attribute,listY);
//			//
//			//
//			// }
//			// }
//			// data.getAttributes();
//			// for (int i = 0; i < dsResponse.getdata().getLength(); i++) {
//			// final Record from_server =
//			// dsResponse.getDataAsRecordList().get(i);
//			// records =
//			// from_server.getAttributeAsRecord("response").getAttributeAsRecordArray("data");
//			// chartDetails = records.("chartDetails");
//
//		}
	}

	public ResizeableChartCanvas createLineChart(ChartValueObject chartVo) {
		Chart chart = new Chart();
		chart.setShadow(true);
		chart.setType(Series.Type.LINE)
				.setPlotBorderWidth(1)
				.setMarginBottom(80)
				.setMarginTop(50)
				.setMarginLeft(50)
				.setMarginRight(50)
				.setChartTitle(
						new ChartTitle()
								// .setStyle("<p style='font-size:16px;'>"+titleStyle+"</p>")
								.setText(chartVo.getTitle())
								.setY(20) // center
				)
				.setChartSubtitle(new ChartSubtitle().setText(chartVo.getSubtitle())
				// .setX(-20)
				)
				.setLegend(
						new Legend().setLayout(Legend.Layout.HORIZONTAL)
								.setAlign(Legend.Align.CENTER)
								.setVerticalAlign(Legend.VerticalAlign.BOTTOM)
								// .setBorderWidth(1)
								// .setX(-10)
								.setY(-10)
				// .setBorderWidth(0)
				).setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						com.google.gwt.i18n.client.NumberFormat nfGWT = com.google.gwt.i18n.client.NumberFormat
								.getDecimalFormat().overrideFractionDigits(2);
						return "<b>"
								+ toolTipData.getSeriesName()
								+ "</b><br/>"
								+ toolTipData.getXAsString()
								+ ": "
								+ nfGWT.format(new BigDecimal(toolTipData
										.getYAsDouble()));
					}
				}));
//		String[] x =
		chart.getXAxis().setCategories(chartVo.getXCategories());
		chart.getYAxis().setAxisTitleText(chartVo.getYTitle());
		if(chartVo.getSeries()!=null){
			for (Record record : chartVo.getSeries()) {
			Record currentSeries = record.getAttributeAsRecord("record");
			chart.addSeries(chart.createSeries()
					.setName(chartVo.getCurrentSeriesName(currentSeries))
					.setPoints(chartVo.getCurrentSeriesPoints(currentSeries))
			// .setPoints(series.get(key).toArray(new
			// Number[series.get(key).size()]))
			// .setPoints(new
			// Number[record.getAttributeAsRecord(seriesName).getAttributeAsDoubleArray("y").length])

			);
//			}

			// chart.addSeries(chart.createSeries()
			// .setName("New York")
			// .setPoints(new Number[]{
			// -0.2,null, 5.7, 11.3, 17.0, null, 24.8, 24.1, 20.1, 14.1, 8.6,
			// 2.5
			// })
			// );
			// //
			//
			// chart.addSeries(chart.createSeries()
			// .setName("Tokyo")
			// .setPoints(new Number[]{
			// 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9,
			// 9.6
			// })
			// );
			// chart.addSeries(chart.createSeries()
			// .setName("New York")
			// .setPoints(new Number[]{
			// -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6,
			// 2.5
			// })
			// );
			// chart.addSeries(chart.createSeries()
			// .setName("Berlin")
			// .setPoints(new Number[]{
			// -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0
			// })
			// );
			// chart.addSeries(chart.createSeries()
			// .setName("London")
			// .setPoints(new Number[]{
			// 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8
			// })
			// );
		}
		}
		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(chart);
		chartCanvas.setWidth100();
		chartCanvas.setHeight(500);
		chartCanvas.setOverflow(Overflow.AUTO);
		return chartCanvas;
	}


	// GWT-HIGHCHARTS Internationalization
	private native void nativeSetGlobalOptions() /*-{
													$wnd.Highcharts.setOptions({
													lang : {
													months : [ 'siječanj', 'veljača', 'ožujak', 'travanj',
													'svibanj', 'lipanj', 'srpanj', 'kolovoz', 'rujan',
													'listopad', 'studeni', 'prosinac' ],
													weekdays : [ 'ponedjeljak', 'utorak', 'srijeda', 'četvrtak',
													'petak', 'subota', 'nedjelja' ],
													decimalPoint : ",",
													thousandsSep : ".",
													printChart : "Ispiši dijagram",
													downloadJPEG : "Spremi kao JPEG sliku",
													downloadPDF : "Spremi kao PDF dokument",
													downloadPNG : "Spremi kao PNG sliku",
													downloadSVG : "Spremi kao SVG vektor",
													drillUpText : "Povratak na {series.name}",
													resetZoom : "Poništi povećavanje",
													resetZoomTitle : "Poništi povećavanje 1:1",
													shortMonths :  [ 'sij' , 'velj' , 'ožu' , 'tra' , 'svi' , 'lip' , 'srp' , 'kol' , 'ruj' , 'lis' , 'stu' , 'pro'],
													loading : "učitavanje ...",
													}
													});
													}-*/;

	public class ChartValueObject extends HashMap<String,Object>{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final String TITLE="title";
		private static final String SUBTITLE="subtitle";
		private static final String YTITLE="yTitle";
		private static final String XCATEGORIES="x";
		private static final String SERIES="series";

//		private List<Record> chartRecordList;

		public ChartValueObject(){
//			chartRecordList = new ArrayList<Record>();
		}

		public void setChart(Record chart){
//			chartData.getAttributes();
			if (chart.getAttributeAsRecord("chartDetails") != null) {
				HashMap<String, String> chartDetails = new HashMap<String, String>();
				Record details = chart.getAttributeAsRecord("chartDetails");
				for (String attribute : details.getAttributes()) {
					chartDetails.put(attribute, details.getAttribute(attribute));
				}
				setChartDetails(chartDetails);
			} else if (chart.getAttributeAsRecordArray("records") != null) {
				for (Record record : chart
						.getAttributeAsRecordArray("records"))
					addSeries(record);
//				setSeries(chartRecordList);
			}

		}
		public void setTitle(String title){
			put(TITLE,title);
		}
		public String getTitle(){
			return get(TITLE).toString();
		}
		public void setSubtitle(String subtitle){
			put(SUBTITLE,subtitle);
		}
		public String getSubtitle(){
			return get(SUBTITLE).toString();
		}
		public void setYTitle(String yTitle){
			put(YTITLE,yTitle);
		}
		public String getYTitle(){
			return get(YTITLE).toString();
		}
		public void setXCategories(String x){
			put(XCATEGORIES,x.toString().split(","));
		}
		public String[] getXCategories(){
			return (String[])get(XCATEGORIES);
		}
		public void setSeries(List<Record> series){
			put(SERIES,series);
		}

		public void addSeries(Record record) {
			List<Record> chartRecordList;
			if (getSeries() == null) {
				chartRecordList = new ArrayList<Record>();
			} else {
				chartRecordList = getSeries();
			}

			chartRecordList.add(record);
			put(SERIES, chartRecordList);
		}
		@SuppressWarnings("unchecked")
		public List<Record> getSeries(){
			return (List<Record>)get(SERIES);
		}

//		@SuppressWarnings("unchecked")
//		public List<Record> getSeriesList(){
//			return chartRecordList;
//		}

		public Double[] getCurrentSeriesPoints(Record currentSeries){
			currentSeries.getAttributes();
			Double[] points = new Double[currentSeries.getAttributeAsDoubleArray("y").length];
			int j = 0;
			for (Double point : currentSeries.getAttributeAsDoubleArray("y")) {
				points[j] = point;
				j++;
			}
			return points;
		}

		public String getCurrentSeriesName(Record currentSeries){
			return currentSeries.getAttribute("title");
		}

		public void setChartDetails(HashMap<String, String> chartDetails){
			setTitle(chartDetails.get(TITLE));
			setSubtitle(chartDetails.get(SUBTITLE));
			setYTitle(chartDetails.get(YTITLE));
			setXCategories(chartDetails.get(XCATEGORIES));
		}




	}

}



