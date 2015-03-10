package hr.adriacomsoftware.app.client.smartgwt.kpi.views;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2ChartsEngine;
import hr.as2.inf.common.charts.AS2ChartConstants;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.tab.Tab;

public class HomeTab extends Tab{
	
	private static HomeTab instance = new HomeTab();
	public static HomeTab getInstance() {
		return instance;
	}
	
	public HomeTab() {
		super(KpiContext.HOME_TAB_TITLE);
	}
	
	public void fetchData(){
		Criteria criteria = new Criteria();
		criteria.addCriteria("razina1","Naslov");
		criteria.addCriteria("tab","naslov");
		criteria.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO,3);
		criteria.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE,"AreaLine");
		criteria.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE1,"BasicPie");
		criteria.addCriteria(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE2,"StockLineChart");
		ChartsTransportModel.getInstance().fetchData(criteria,new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				boolean error = ChartsTransportModel.checkErrorsFormServer(dsResponse);
//				if(!error){
					AS2ChartsEngine.serverCallBack(dsResponse.getDataAsRecordList());
//				}
			}
		});

		
	}
	
	
}
