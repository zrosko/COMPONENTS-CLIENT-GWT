package hr.adriacomsoftware.app.client.smartgwt.obrasci.models;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.engine.AS2ObrasciEngine;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciNavigationTreeGrid;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;

import java.util.HashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ObrasciTransportModel extends AS2RestJSONDataSource {

	private static ObrasciTransportModel instance = new ObrasciTransportModel(
			"ObrasciTransportModel");

	public static ObrasciTransportModel getInstance() {
		return instance;
	}

	public static Criteria _criteriaForServer;
	// public static DataSource _dataSource;
	public static String SERVER_COMPONENT = "hr.adriacomsoftware.app.server.obrasci.facade.DokumentFacadeServer";
	public static String SERVER_SERVICE = "ListajObrasceIzDirektorija";
	public static String SERVER_TRANSFORMTO = "hr.adriacomsoftware.inf.common.dto.J2EEValueObject";
	public static final String ERROR_NO_DATA_FROM_SERVER = "Nema Podataka";
	public static ObrasciNavigationTreeGrid _obrasciTreeGrid;
	public static TreeNode _selectedObrazacTree;
	public static Record _selectedObrazacMoj;
	public static final String _defaultServletURL = "json_servlet";//servletjson

	public ObrasciTransportModel(String id) {
		super(id, _defaultServletURL);
		// _dataSource = this;
	}

	// public static boolean checkErrorsFormServer(DSResponse dsResponse){
	// if (dsResponse.getTotalRows() != 0) {
	// boolean error=false;
	// RecordList records = dsResponse.getDataAsRecordList();
	// for(int i=0;i<records.toArray().length;i++){
	// Record record = records.get(i);
	// if(record.getAttributeAsString("error")!=null){
	// error=true;
	// SC.warn("Problem", record.getAttributeAsString("error"));
	// }
	// }
	// if(!error){
	// return false;
	// }
	// } else {
	// noData();
	// }
	// return true;
	// }
	//
	// public static void noData(){
	// SC.warn("Problem", ERROR_NO_DATA_FROM_SERVER);
	// if(_selectedObrazacTree!=null)
	// _obrasciTreeGrid.deselectRecord(_selectedObrazacTree);
	// }
	public static boolean checkErrorFromServer(RecordList records) {
		boolean error = false;
		Record record = records.get(0);
		record.getAttributes();
		if (record.getAttributeAsObject("error") != null) {
			error = true;
			SC.warn("Problem", record.getAttributeAsString("error"));
		}
		if (!error) {
			return false;
		}
		return true;
	}

	public static void callServer(Record obrazac, Criteria criteria) {
		if (OContext._obrasciNavigationTreeGrid.getSelectedRecord() != null
				&& OContext._obrasciNavigationTreeGrid.getSelectedRecord()
						.equals(obrazac)) {
			_selectedObrazacTree = (TreeNode) obrazac;
		} else {
			_selectedObrazacMoj = obrazac;
		}
		_criteriaForServer = new Criteria();
		if (criteria != null)
			_criteriaForServer.addCriteria(criteria);
		for (String attribute : obrazac.getAttributes()) {
			_criteriaForServer.addCriteria(attribute,
					obrazac.getAttribute(attribute));
		}
		// Dodavanje usera
		_criteriaForServer.addCriteria("korisnik",
				OContext._obrasciDesktop._userId);
		// Setting initial data
		// _criteriaForServer.addCriteria("obrazacId",_selectedObrazacTree.getId());
		// _criteriaForServer.addCriteria("obrazac",_selectedObrazacTree.getName());
		// _criteriaForServer.addCriteria("rootUrl",OContext.URL);

		// Default server settings
		defaultCallServerSettings(obrazac, _criteriaForServer);
	}

	private static void defaultCallServerSettings(Record obrazac,
			Criteria criteriaForServer) {
		if (criteriaForServer.getAttribute("Component") == null)
			criteriaForServer.addCriteria("Component", SERVER_COMPONENT);
		if (criteriaForServer.getAttribute("Service") == null)
			criteriaForServer.addCriteria("Service", SERVER_SERVICE);
		if (criteriaForServer.getAttribute("transform_to") == null)
			criteriaForServer.addCriteria("transform_to", SERVER_TRANSFORMTO);
		_criteriaForServer = criteriaForServer;
		ObrasciTransportModel.getInstance().fetchData(_criteriaForServer,
				new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data,
							DSRequest dsRequest) {
						if(dsResponse.getStatus()>=0)
							AS2ObrasciEngine.serverCallBack(dsResponse.getDataAsRecordList());
					}
				});

	}
	
	public static RecordDoubleClickHandler mojiObrasciViewRecordClickHandler() {
		RecordDoubleClickHandler handler = new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				callServer(event.getRecord(), new Criteria("Service","prikaziMojiObrasciFormu"));
			}
		};
		return handler;

	}

	@Override
	protected Object getRecordType() {
		return new ListGridRecord();
	}
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		return null;
	}

	@Override
	public String getRemoteObject() {
		return null;
	}

	@Override
	public String getTransformTo() {
		return null;
	}

	// public static void callServer(ObrasciTreeNode selectedPokazatelj,
	// Criteria criteria) {
	// _selectedObrazacTree = selectedPokazatelj;
	// _criteriaForServer = new Criteria();
	// if(criteria!=null)
	// _criteriaForServer.addCriteria(criteria);
	// // //Postavljanje Razina
	// // if(_selectedObrazacTree.getRazine()!=null){
	// // HashMap<String,String> razine = _selectedObrazacTree.getRazine();
	// // for(String razina:razine.keySet()){
	// // _criteriaForServer.addCriteria(razina,razine.get(razina));
	// // }
	// // }
	// for(String attribute:_selectedObrazacTree.getAttributes()){
	// _criteriaForServer.addCriteria(attribute,_selectedObrazacTree.getAttribute(attribute));
	// }
	// //Setting initial data
	// //
	// _criteriaForServer.addCriteria("obrazacId",_selectedObrazacTree.getId());
	// //
	// _criteriaForServer.addCriteria("obrazac",_selectedObrazacTree.getName());
	// _criteriaForServer.addCriteria("rootUrl",OContext.URL);
	//
	// //Default server settings
	// if(_criteriaForServer.getAttribute("Component")==null)
	// _criteriaForServer.addCriteria("Component",SERVER_COMPONENT);
	// _criteriaForServer.getAttribute("Service");
	// if(_selectedObrazacTree.getAttribute("Service")==null)
	// _criteriaForServer.addCriteria("Service",SERVER_SERVICE);
	// else{
	// _criteriaForServer.addCriteria(ObrasciTreeNode.SERVICE,_selectedObrazacTree.getService());
	// }
	// if(_criteriaForServer.getAttribute("transform_to")==null)
	// _criteriaForServer.addCriteria("transform_to",SERVER_TRANSFORMTO);
	// System.out.println("ZOVEM SERVER");
	// ObrasciTransportModel._dataSource.fetchData(_criteriaForServer, new
	// DSCallback() {
	// @Override
	// public void execute(DSResponse dsResponse, Object data, DSRequest
	// dsRequest) {
	// boolean error = ObrasciTransportModel.checkErrorsFormServer(dsResponse);
	// if(!error){
	// AS2ObrasciEngine.serverCallBack(dsResponse.getDataAsRecordList());
	// }
	// }
	// });
	// }

}
