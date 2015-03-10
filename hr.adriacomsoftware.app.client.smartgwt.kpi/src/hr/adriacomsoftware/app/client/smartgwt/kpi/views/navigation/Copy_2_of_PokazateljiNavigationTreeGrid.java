package hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation;
//package hr.adriacomsoftware.app.client.smartgwt.kpi.client.views.navigation;
//
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.KpiContext;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.models.ChartsTransportModel;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.views.charttoolstrip.ChartToolStrip;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.widgets.ChartValueObject;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.widgets.ChartsEngine;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.smartgwt.client.data.DSCallback;
//import com.smartgwt.client.data.DSRequest;
//import com.smartgwt.client.data.DSResponse;
//import com.smartgwt.client.data.Record;
//import com.smartgwt.client.data.RecordList;
//import com.smartgwt.client.types.SortArrow;
//import com.smartgwt.client.types.TreeModelType;
//import com.smartgwt.client.util.SC;
//import com.smartgwt.client.widgets.tree.Tree;
//import com.smartgwt.client.widgets.tree.TreeGrid;
//import com.smartgwt.client.widgets.tree.TreeGridField;
//import com.smartgwt.client.widgets.tree.TreeNode;
//import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
//import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
//
//public class Copy_2_of_PokazateljiNavigationTreeGrid extends TreeGrid {
//
//	public Copy_2_of_PokazateljiNavigationTreeGrid() {
//		this.setLoadDataOnDemand(false);
//		this.setWidth100();
//		this.setHeight100();
//		this.setDataSource(ChartsTransportModel._dataSource);
//
//		//ADDED
////		this.setCustomIconProperty("icon");
//		this.setAnimateFolderTime(100);
//		this.setAnimateFolders(true);
//		this.setAnimateFolderSpeed(1000);
////		this.setNodeIcon("icons/16/line_chart.png");
//		this.setShowSortArrow(SortArrow.CORNER);
//		this.setShowAllRecords(true);
//		this.setLoadDataOnDemand(false);
//		this.setCanSort(false);
//		this.setLeaveScrollbarGap(false);
//
//		TreeGridField field = new TreeGridField("Name", "Pokazatelji");
//
//		//ADDED
//		field.setCanSort(false);
//	    field.setCanFilter(true);
//
//
//		this.setFields(field);
//		DSRequest dsRequest = new DSRequest();
//		dsRequest.setAttribute("service", "servicePokazatelji");
//		this.setID("pokazateljiTreeGrid");
////		ChartsTransportModel._pokazateljiTreeGrid = this;
//		this.fetchData(null, new DSCallback() {
//			@Override
//			public void execute(DSResponse dsResponse, Object data,
//					DSRequest dsRequest) {
//				boolean error = ChartsTransportModel.checkErrorsFormServer(dsResponse);
//				if(!error){
//					serverCallBack(dsResponse.getDataAsRecordList());
//				}
////				if (dsResponse.getTotalRows() != 0) {
////					boolean error = false;
////					RecordList records = dsResponse.getDataAsRecordList();
////					for (int i = 0; i < records.toArray().length; i++) {
////						Record record = records.get(i);
////						if (record.getAttributeAsString("error") != null) {
////							error = true;
////							SC.warn("Problem",record.getAttributeAsString("error"));
////						}
////					}
////					if (!error) {
////						serverCallBack(dsResponse.getDataAsRecordList());
////					}
////				} else {
////					SC.warn("Problem", KpiContext.ERROR_NO_DATA_FROM_SERVER);
////				}
//
//			}
//		}, dsRequest);
//
//		// PokazateljiTreeNode gradani = new PokazateljiTreeNode("gradani",
//		// "root", "Građani",1);
//		// PokazateljiTreeNode gr_klijenti = new
//		// PokazateljiTreeNode("gr_klijenti", "gradani", "Klijenti",2);
//		// PokazateljiTreeNode gr_klijenti_racuni = new
//		// PokazateljiTreeNode("gr_klijenti_racuni", "gr_klijenti", "Računi",3);
//		// PokazateljiTreeNode gr_klijenti_demografija = new
//		// PokazateljiTreeNode("gr_klijenti_demografija", "gr_klijenti",
//		// "Demografija",3);
//		// PokazateljiTreeNode gr_financije = new
//		// PokazateljiTreeNode("gr_financije", "gradani", "Financije",2);
//		// PokazateljiTreeNode pravneosobe = new
//		// PokazateljiTreeNode("pravneosobe", "root", "Pravne osobe",1);
//		// PokazateljiTreeNode po_klijenti = new
//		// PokazateljiTreeNode("po_klijenti", "pravneosobe", "Klijenti",2);
//		// PokazateljiTreeNode po_financije = new
//		// PokazateljiTreeNode("po_financije", "pravneosobe", "Financije",2);
//		// PokazateljiTreeNode controlling = new
//		// PokazateljiTreeNode("controlling", "root", "Controlling",1);
//		// PokazateljiTreeNode cont_financije = new
//		// PokazateljiTreeNode("cont_financije", "controlling", "Financije",2);
//		// PokazateljiTreeNode cont_financije_oper_place = new
//		// PokazateljiTreeNode("cont_financije_oper_place", "cont_financije",
//		// "Oper. tr. plaće",3);
//		// PokazateljiTreeNode cont_financije_oper_ostalo = new
//		// PokazateljiTreeNode("cont_financije_oper_ostalo", "cont_financije",
//		// "Oper. tr. ostalo",3);
//		//
//		// pokazateljiTree.setData(new TreeNode[] { gradani,
//		// gr_klijenti,gr_klijenti_racuni,gr_klijenti_demografija,
//		// gr_financije, pravneosobe, po_klijenti,
//		// po_financije,controlling,cont_financije,cont_financije_oper_place,cont_financije_oper_ostalo});
//
//		// pokazateljiTreeGrid.addDrawHandler(new DrawHandler() {
//		// public void onDraw(DrawEvent event) {
//		// pokazateljiTree.openAll();
//		// }
//		// });
//		// pokazateljiTreeGrid.setData(pokazateljiTree);
//		this.addLeafClickHandler(new LeafClickHandler() {
//			@Override
//			public void onLeafClick(LeafClickEvent event) {
//				if (event.getLeaf().getIcon() != null){
//						event.getLeaf().setAttribute("razine", getRazine(Copy_2_of_PokazateljiNavigationTreeGrid.this.getSelectedPaths()));
////						if( event.getLeaf().getAttributeAsObject("ChartRs")!=null){
////							Record record = event.getLeaf();
////							List<ChartValueObject> chartRs = new ArrayList<ChartValueObject>();
////							chartRs = (List<ChartValueObject>) record.getAttributeAsObject("ChartRs");
////							AS2ChartsEngine.createCharts(chartRs);
//////							List<ChartValueObject> chartRs = (List<ChartValueObject>) event.getLeaf().getAttributeAsObject("ChartRs");
////						}else{
//						ChartsTransportModel.callServer(event.getLeaf(),false,((ChartToolStrip)event.getLeaf().getAttributeAsObject("chartToolstrip")).getToolStripItemValuesAsCriteria());
//
////						}
//				}
//
//
//			}
//		});
//		// pokazateljiTreeGrid.addNodeClickHandler(new NodeClickHandler() {
//		// @Override
//		// public void onNodeClick(NodeClickEvent event) {
//		// if (!event.getNode().getAttributeAsBoolean("isFolder") &&
//		// event.getNode().getIcon()!=null) {
//		// //
//		// if(event.getNode().getAttribute("Id").equals("gr_klijenti_demografijaStablo")){
//		// // KpiContext.callServer(pokazateljiTreeGrid,false,new
//		// Criteria("Service","listajKpiGrDemografijaStablo"));
//		// // }else{
//		// KpiContext.callServer(pokazateljiTreeGrid,false);
//		// // }
//		// }
//		// }
//		// });
//
//	}
//
//	public void serverCallBack(RecordList dsResponseData) {
//		List<TreeNode> nodes = new ArrayList<TreeNode>();
//		Record[] lista = null;
//		for (int i = 0; i < dsResponseData.toArray().length; i++) {
//			Record record = dsResponseData.get(i);
//			if (record.getAttribute("error") != null) {
//				SC.warn("Problem", record.getAttributeAsString("error"));
//			} else {
//				lista = record.getAttributeAsRecordArray("List");
//			}
//		}
//		if (lista != null) {
//			for (Record rec : lista) {
//				Record pokazatelj = rec.getAttributeAsRecord("pokazatelj");
//				PokazateljiTreeNode node = new PokazateljiTreeNode(pokazatelj);
//				nodes.add(node);
//			}
//		}
//		if (nodes != null && !nodes.isEmpty()) {
//			Tree pokazateljiTree = new Tree();
//			pokazateljiTree.setModelType(TreeModelType.PARENT);
//			pokazateljiTree.setNameProperty("Name");
//			pokazateljiTree.setIdField("Id");
//			pokazateljiTree.setParentIdField("ParentId");
//			pokazateljiTree.setShowRoot(false);
//			pokazateljiTree.setData(nodes.toArray(new TreeNode[nodes.size()]));
//			this.setData(pokazateljiTree);
//		}
//	}
//
//	public HashMap<String,String> getRazine(String selectedPaths){
//		HashMap<String,String> razine = new HashMap<String,String>();
//		String path = selectedPaths.substring(selectedPaths.indexOf("/"),selectedPaths.length()-3);
//		int countRazina = 1;
//		int start=0;
//		int end=0;
//		while(path.contains("/")){
//			start = path.indexOf("/")+1;
//			if(path.substring(start, path.length()).indexOf("/")==-1){
//				end = path.length();
//			}
//			else{
//				end = path.substring(start, path.length()).indexOf("/")+1;
//			}
//			String temp = path.substring(start,end);
//			razine.put("razina"+countRazina++, temp);
//			path = path.substring(end,path.length());
//		}
//		return razine;
//	}
//
////	public void getLevels(HashMap<String,String>razine, Record[] records,Record selectedRecord){
////		for(Record record: records ){
////			if(record.getAttribute("Id").equals(selectedRecord.getAttribute("ParentId"))){
////				if(record.getAttributeAsInt("Level")==1){
////					razine.put("razina1",record.getAttribute("Name"));
////					razine.put("razina2",selectedRecord.getAttribute("Name"));
////					break;
////				}
////				if(record.getAttributeAsInt("Level")==2){
////					razine.put("razina2",record.getAttribute("Name"));
////					razine.put("razina3",selectedRecord.getAttribute("Name"));
////					getLevels(razine,records,record);
////				}
////			}
////		}
//
////	}
//
////	public static class PokazateljiTreeNode extends TreeNode {
////
////		public PokazateljiTreeNode(Record pokazatelj) {
////			if (pokazatelj.getAttribute("ChartTypeNo") != null) {
////				this.setId(pokazatelj.getAttribute("Id"));
////				this.setParentId(pokazatelj.getAttribute("ParentId"));
////				this.setName(pokazatelj.getAttribute("Name"));
////				this.setChartType(pokazatelj.getAttribute("ChartType"));
////				this.setChartTypes(pokazatelj);
////				this.setLevel(Integer.parseInt(pokazatelj
////						.getAttributeAsString("Level")));
////
////			} else {
////				this.setId(pokazatelj.getAttribute("Id"));
////				this.setParentId(pokazatelj.getAttribute("ParentId"));
////				this.setName(pokazatelj.getAttribute("Name"));
////				this.setChartType(pokazatelj.getAttribute("ChartType"));
////				this.setLevel(Integer.parseInt(pokazatelj
////						.getAttributeAsString("Level")));
////
////			}
////			this.getAttributes();
////			if (pokazatelj.getAttribute("Icon") != null
////					&& !pokazatelj.getAttribute("Icon").equalsIgnoreCase(
////							"folder")) {
////				this.setIcon("./icons/graph/16/"
////						+ pokazatelj.getAttribute("Icon") + ".png");
////			}
////
////		}
////
////		public PokazateljiTreeNode(String id, String parentId, String name,
////				Record pokazatelj, Integer level) {
////
////		}
////
////		public PokazateljiTreeNode(String id, String parentId, String name,
////				String type, Integer level) {
////
////		}
////
////		public void setId(String value) {
////			setAttribute("Id", value);
////		}
////
////		public void setParentId(String value) {
////			setAttribute("ParentId", value);
////		}
////
////		public void setName(String name) {
////			setAttribute("Name", name);
////		}
////
////		public void setChartType(String type) {
////			setAttribute("ChartType", type);
////		}
////
////		// If more than one chart type
////		public void setChartTypes(Record pokazatelj) {
////			int chartTypeNo = Integer.parseInt(pokazatelj
////					.getAttribute("ChartTypeNo"));
////			this.setAttribute("ChartTypeNo", chartTypeNo);
////			for (int i = 0; i < chartTypeNo; i++) {
////				if (pokazatelj.getAttribute("ChartType" + (i + 1)) != null)
////					setAttribute("ChartType" + (i + 1),
////							pokazatelj.getAttribute("ChartType" + (i + 1)));
////			}
////		}
////
////		public List<String> getChartTypes() {
////			List<String> chartTypes = new ArrayList<String>();
////			int chartTypeNo = Integer.parseInt(getAttribute("ChartTypeNo"));
////			chartTypes.add(getAttribute("ChartType"));
////			for (int i = 0; i < chartTypeNo; i++) {
////				if (this.getAttribute("ChartType" + (i + 1)) != null)
////					chartTypes.add(this.getAttribute("ChartType" + (i + 1)));
////			}
////			return chartTypes;
////
////		}
////
////		public void setLevel(Integer level) {
////			setAttribute("Level", level);
////		}
////	}
//
//}
