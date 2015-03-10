package hr.adriacomsoftware.app.client.smartgwt.kpi.models;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation.PokazateljiNavigationTreeGrid;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation.PokazateljiTreeNode;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2ChartsEngine;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.as2.inf.common.charts.AS2ChartConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;

public class ChartsTransportModel extends AS2RestJSONDataSource {

	  private static ChartsTransportModel instance =  new ChartsTransportModel("ChartsTransportModel");
		
	    public static ChartsTransportModel getInstance() {
	        return instance;
	    }
		public static Criteria _criteriaForServer;
		public static DataSource _dataSource;
		public static PokazateljiNavigationTreeGrid _pokazateljiTreeGrid;
		public static PokazateljiTreeNode _selectedPokazatelj;

	public ChartsTransportModel(String id) {
		super(id,CHARTS_DATA_URL);
//		this.setDataFormat(DSDataFormat.JSON);
//		this.setID(id);
//		this.setDataURL("servletjson");
		_dataSource = this;
	}

//	@Override
//	protected Object transformRequest(DSRequest dsRequest) {
//		return super.transformRequest(dsRequest);
//	}
//
//	@Override
//	protected void transformResponse(DSResponse response,
//			DSRequest request, Object data) {
//		super.transformResponse(response, request, data);
//	}

//	public static boolean checkErrorsFormServer(DSResponse dsResponse){
//		if (dsResponse.getTotalRows() != 0) {
//			boolean error=false;
//			RecordList records = dsResponse.getDataAsRecordList();
//			for(int i=0;i<records.toArray().length;i++){
//				Record record = records.get(i);
//				if(record.getAttributeAsString("error")!=null){
//					error=true;
//					SC.warn("Problem", record.getAttributeAsString("error"));
//				}
//			}
//			if(!error){
//				return false;
//			}
//		} else {
//			 noData1();
//		}
//		return true;
//
//	}

	public static void noData1(){
//		SC.warn("Problem", KpiContext.ERROR_NO_DATA_FROM_SERVER);
//		noData();
		if(_selectedPokazatelj!=null && _pokazateljiTreeGrid!=null)
		_pokazateljiTreeGrid.deselectRecord(_selectedPokazatelj);
	}

	public static String getCharTitle(){
		if (_criteriaForServer.getAttribute("razina3") != null){
			return (_criteriaForServer.getAttribute("razina1") + " > "
					+ _criteriaForServer.getAttribute("razina2") + " > " + _criteriaForServer
						.getAttribute("razina3"));
		}else {
			return (ChartsTransportModel._criteriaForServer.getAttribute("razina1") + " > " + _criteriaForServer
					.getAttribute("razina2"));
		}

	}


	public static void callServer(boolean toolStripChanged,Criteria criteria) {
		callServer(_selectedPokazatelj,toolStripChanged,criteria);
	}

	public static void callServer(PokazateljiTreeNode selectedPokazatelj, boolean toolStripChanged,Criteria criteria) {
		_selectedPokazatelj = selectedPokazatelj;
		_criteriaForServer = new Criteria();
		if(criteria!=null)
			_criteriaForServer.addCriteria(criteria);
		//Postavljanje Razina
		if(_selectedPokazatelj.getRazine()!=null){
			HashMap<String,String> razine = _selectedPokazatelj.getRazine();
			for(String razina:razine.keySet()){
				_criteriaForServer.addCriteria(razina,razine.get(razina));
			}
		}
		//Setting initial data
		_criteriaForServer.addCriteria("pokazateljId",_selectedPokazatelj.getPokazateljId());
		_criteriaForServer.addCriteria("pokazatelj",_selectedPokazatelj.getPokazatelj());
		_criteriaForServer.addCriteria("chartTitle",getCharTitle());
		_criteriaForServer.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE,_selectedPokazatelj.getChartType());
		//If more chartTypes
		if(_selectedPokazatelj.getChartTypeNo()!=null){
			_criteriaForServer.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO,Integer.parseInt(_selectedPokazatelj.getChartTypeNo()+""));
			List<String> chartTypes = new ArrayList<String>();
			chartTypes = _selectedPokazatelj.getChartTypes();
			for(int i=0;i<chartTypes.size();i++){
				_criteriaForServer.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE+(i+1),_selectedPokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE+(i+1)));
			}
		}
		//Default server settings
		if(_criteriaForServer.getAttribute("Component")==null)
			_criteriaForServer.addCriteria("remoteobject",KpiContext.SERVER_COMPONENT);
//		_criteriaForServer.getAttribute("Service");
		if(_selectedPokazatelj.getAttribute("Service")==null)
			_criteriaForServer.addCriteria("remotemethod",KpiContext.SERVER_SERVICE);
		else{
			_criteriaForServer.addCriteria(AS2ChartConstants.AS2_CHART_TREE__SERVICE,_selectedPokazatelj.getService());
		}
		if(_criteriaForServer.getAttribute("transform_to")==null)
			_criteriaForServer.addCriteria("transform_to",KpiContext.SERVER_TRANSFORMTO);
//		System.out.println("ZOVEM SERVER");		
		ChartsTransportModel._dataSource.fetchData(_criteriaForServer, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				boolean error = ChartsTransportModel.checkErrorsFormServer(dsResponse);
//				if(!error){
					AS2ChartsEngine.serverCallBack(dsResponse.getDataAsRecordList());
//				}
			}
		});
	}

	@Override
	public String getRemoteObject() {
		return KpiContext.SERVER_COMPONENT;
	}

	@Override
	public String getTransformTo() {
		return KpiContext.SERVER_TRANSFORMTO;
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
	

}
