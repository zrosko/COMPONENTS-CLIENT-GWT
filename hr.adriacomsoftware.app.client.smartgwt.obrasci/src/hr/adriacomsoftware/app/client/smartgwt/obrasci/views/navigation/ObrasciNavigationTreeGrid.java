package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciFoldersModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTreeModel;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class ObrasciNavigationTreeGrid extends TreeGrid {
	
	private DataSource getModel() {
		return ObrasciTransportModel.getInstance();
	}

	private DataSource getFoldersModel() {
		return ObrasciFoldersModel.getInstance();
	}

	public ObrasciNavigationTreeGrid() {
		this.setWidth100();
		this.setHeight100();
		this.setDataSource(getModel());
		this.setAnimateFolderTime(100);
		this.setAnimateFolders(true);
		this.setAnimateFolderSpeed(1000);
		this.setShowSortArrow(SortArrow.CORNER);
		this.setShowAllRecords(true);
		this.setLoadDataOnDemand(false);
		this.setCanSort(false);
		this.setLeaveScrollbarGap(false);
		this.setShowHeader(false);
		this.setAutoFetchData(false);

		TreeGridField field = new TreeGridField("name", "Obrasci");
		field.setCanSort(false);
		field.setCanFilter(true);

		this.setFields(field);
		Criteria criteria = new Criteria("url", OContext.URL);
		criteria.addCriteria("service","obrasciTreeGrid");
		getFoldersModel().fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
				serverCallBack(dsResponse.getDataAsRecordList());
			}
		});

		this.addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(NodeClickEvent event) {
				if (event.getNode() != null) {
					TreeNode selectedRecord = event.getNode();
					if (!selectedRecord.getAttributeAsBoolean("isFolder")) {
						if (selectedRecord.getIcon() != null) {
							Criteria criteria = new Criteria("Service","prikaziPdfFormu");
							criteria.addCriteria("rootUrl",OContext.URL);
							ObrasciTransportModel.callServer(selectedRecord,criteria);
						}
					} else {
						ObrasciNavigationTreeGrid.this.toggleFolder(selectedRecord);
					}
				}
			}
		});
		ObrasciTransportModel._obrasciTreeGrid = this;
	}

	public void serverCallBack(RecordList dsResponseData) {
		List<ObrasciTreeNode> nodes = new ArrayList<ObrasciTreeNode>();
		for (Record obrazac : dsResponseData.toArray()) {
			ObrasciTreeNode node = new ObrasciTreeNode(obrazac);
			nodes.add(node);
		}
		if (nodes != null && !nodes.isEmpty()) {
			ObrasciTreeModel obrasciTreeModel = ObrasciTreeModel.getInstance();
			obrasciTreeModel.setData(nodes);
			this.setData(obrasciTreeModel);
		}
	}
}
