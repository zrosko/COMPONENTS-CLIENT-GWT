package hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation;

import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.HomeTab;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.ChartToolStrip;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.as2.inf.common.charts.AS2ChartConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class PokazateljiNavigationTreeGrid extends TreeGrid {

	public PokazateljiNavigationTreeGrid() {
		this.setWidth100();
		this.setHeight100();
		this.setDataSource(ChartsTransportModel.getInstance());
		this.setAnimateFolderTime(100);
		this.setAnimateFolders(true);
		this.setAnimateFolderSpeed(1000);
		this.setShowSortArrow(SortArrow.CORNER);
		this.setShowAllRecords(true);
		this.setLoadDataOnDemand(false);
		this.setCanSort(false);
		this.setLeaveScrollbarGap(false);
		this.setShowHeader(false);
		
		TreeGridField field = new TreeGridField("name", "Pokazatelji");
		field.setCanSort(false);
		field.setCanFilter(true);

		this.setFields(field);
		Criteria criteria = new Criteria();
		for(String key:AS2ClientContext.getSession().getAttributes())
			criteria.addCriteria(key,AS2ClientContext.getSession().getAttribute(key));
//		DSRequest dsRequest = new DSRequest();
//		dsRequest.setAttribute("service", "servicePokazatelji");
		this.setID("pokazateljiTreeGrid");
		//Dohvaćanje pokazatelja za chartTree
		this.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
//				boolean error = ChartsTransportModel.checkErrorsFormServer(dsResponse);
//				if (!error) {
				if(dsResponse.getStatus()>=0){
					serverCallBack(dsResponse.getDataAsRecordList());
				}
			}
		},null);

		//Poziva se kad kliknemo na pokazatelj na drvu
		this.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if (event.getSelectedRecord() != null) {
					PokazateljiTreeNode selectedRecord = (PokazateljiTreeNode) event
							.getSelectedRecord();
					if (!selectedRecord.getAttributeAsBoolean("isFolder")) {
						if (selectedRecord.getIcon() != null) {
							selectedRecord.setAttribute(PokazateljiTreeNode.RAZINE,getRazine(PokazateljiNavigationTreeGrid.this.getSelectedPaths()));
							ChartsTransportModel.callServer(selectedRecord,false, ((ChartToolStrip) selectedRecord.getChartToolstrip()).getToolStripItemValuesAsCriteria());
						}
					} else {
						PokazateljiNavigationTreeGrid.this.toggleFolder(selectedRecord);
					}
				}

			}
		});
		ChartsTransportModel._pokazateljiTreeGrid = this;
	}
	
	public void serverCallBack(RecordList dsResponseData) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if (dsResponseData.get(0)!=null && dsResponseData.get(0).getAttributeAsRecordArray("records") != null) {
			for (Record pokazatelj : dsResponseData.toArray()) {
				PokazateljiTreeNode node = new PokazateljiTreeNode(pokazatelj);
				nodes.add(node);
			}
		}
		if (nodes != null && !nodes.isEmpty()) {
			Tree pokazateljiTree = new Tree();
			pokazateljiTree.setModelType(TreeModelType.PARENT);
			pokazateljiTree.setNameProperty(AS2ChartConstants.AS2_CHART_TREE__NAME);
			pokazateljiTree.setIdField(AS2ChartConstants.AS2_CHART_TREE__ID);
			pokazateljiTree.setParentIdField(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID);
			pokazateljiTree.setShowRoot(false);
			pokazateljiTree.setData(nodes.toArray(new TreeNode[nodes.size()]));
			this.setData(pokazateljiTree);
		}
		
		//Naslov tab
		HomeTab.getInstance().fetchData();
	}
	//old
//	public void serverCallBack1(RecordList dsResponseData) {
//		List<TreeNode> nodes = new ArrayList<TreeNode>();
//		if (dsResponseData.get(0)!=null && dsResponseData.get(0).getAttributeAsRecordArray("records") != null) {
//			Record[] lista = dsResponseData.get(0).getAttributeAsRecordArray("records");
//			for (Record rec : lista) {
//				Record pokazatelj = rec.getAttributeAsRecord("record");
//				PokazateljiTreeNode node = new PokazateljiTreeNode(pokazatelj);
//				nodes.add(node);
//			}
//		}
//		if (nodes != null && !nodes.isEmpty()) {
//			Tree pokazateljiTree = new Tree();
//			pokazateljiTree.setModelType(TreeModelType.PARENT);
//			pokazateljiTree.setNameProperty(AS2ChartConstants.AS2_CHART_TREE__NAME);
//			pokazateljiTree.setIdField(AS2ChartConstants.AS2_CHART_TREE__ID);
//			pokazateljiTree.setParentIdField(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID);
//			pokazateljiTree.setShowRoot(false);
//			pokazateljiTree.setData(nodes.toArray(new TreeNode[nodes.size()]));
//			this.setData(pokazateljiTree);
//		}
//		
//		//Naslov tab
//		HomeTab.getInstance().fetchData();
//	}
	
	//older
//	public void serverCallBack(RecordList dsResponseData) {
//		List<TreeNode> nodes = new ArrayList<TreeNode>();
//		if (dsResponseData.get(0)!=null && dsResponseData.get(0).getAttributeAsRecordArray("List") != null) {
//			Record[] lista = dsResponseData.get(0).getAttributeAsRecordArray("List");
//			for (Record rec : lista) {
//				Record pokazatelj = rec.getAttributeAsRecord("pokazatelj");
//				PokazateljiTreeNode node = new PokazateljiTreeNode(pokazatelj);
//				nodes.add(node);
//			}
//		}
//		if (nodes != null && !nodes.isEmpty()) {
//			Tree pokazateljiTree = new Tree();
//			pokazateljiTree.setModelType(TreeModelType.PARENT);
//			pokazateljiTree.setNameProperty(PokazateljiTreeNode.NAME);
//			pokazateljiTree.setIdField(PokazateljiTreeNode.ID);
//			pokazateljiTree.setParentIdField(PokazateljiTreeNode.PARENT_ID);
//			pokazateljiTree.setShowRoot(false);
//			pokazateljiTree.setData(nodes.toArray(new TreeNode[nodes.size()]));
//			this.setData(pokazateljiTree);
//		}
//	}

	public HashMap<String, String> getRazine(String selectedPaths) {
		HashMap<String, String> razine = new HashMap<String, String>();
		String path = selectedPaths.substring(selectedPaths.indexOf("/"),
				selectedPaths.length() - 3);
		int countRazina = 1;
		int start = 0;
		int end = 0;
		while (path.contains("/")) {
			start = path.indexOf("/") + 1;
			if (path.substring(start, path.length()).indexOf("/") == -1) {
				end = path.length();
			} else {
				end = path.substring(start, path.length()).indexOf("/") + 1;
			}
			String temp = path.substring(start, end);
			razine.put("razina" + countRazina++, temp);
			path = path.substring(end, path.length());
		}
		return razine;
	}
}
