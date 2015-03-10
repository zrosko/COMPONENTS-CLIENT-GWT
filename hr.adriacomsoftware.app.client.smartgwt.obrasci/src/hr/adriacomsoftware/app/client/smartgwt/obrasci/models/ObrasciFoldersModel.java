package hr.adriacomsoftware.app.client.smartgwt.obrasci.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;

import java.util.HashMap;

public class ObrasciFoldersModel extends AS2RestJSONDataSource {

	private static ObrasciFoldersModel instance = new ObrasciFoldersModel(
			"ObrasciFoldersModel");

	public static ObrasciFoldersModel getInstance() {
		return instance;
	}

	public static final String _defaultServletURL = "json_servlet_folders";

	public ObrasciFoldersModel(String id) {
		super(id, _defaultServletURL);
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
