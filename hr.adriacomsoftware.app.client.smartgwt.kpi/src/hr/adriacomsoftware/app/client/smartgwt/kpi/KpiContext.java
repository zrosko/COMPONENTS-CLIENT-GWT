package hr.adriacomsoftware.app.client.smartgwt.kpi;

import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.BreadCrumbLabel;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2GwtDate;

import java.util.Date;
import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.layout.HLayout;

public class KpiContext {

	public static boolean USE_PORTLETS = true;
//	public static TabSet _tabSet;
	public static Object _currentChart;
//	public static Tab _kpiTab;
//	public static Tab _homeTab;
	public static HLayout _breadCrumbLabel;
	public static String SERVER_COMPONENT = "hr.adriacomsoftware.app.server.kpi.facade.KpiFacadeServer";
	public static String SERVER_SERVICE = "citajVrijednostiPokazatelja";
	public static String SERVER_TRANSFORMTO = "hr.as2.inf.common.data.AS2Record";
	public static Integer PROFITNI_CENTAR = 0;
	public static Integer GODINA = 2014;//overridden below
	public static Integer GODINA_DO = 2014;//overridden below
	public static Integer MJESEC = 1;
	public static String HOME_TAB_TITLE ="Naslov";
	public static String KPI_TAB_TITLE = "KPI";


	public static void setBreadCrumbs() {
		if(ChartsTransportModel._criteriaForServer!=null){
			HLayout breadCrumbsLayout = new HLayout();
			BreadCrumbLabel razina1 = new BreadCrumbLabel(ChartsTransportModel._criteriaForServer.getAttribute("razina1"),ChartsTransportModel._criteriaForServer);
			BreadCrumbLabel razina2 = new BreadCrumbLabel(ChartsTransportModel._criteriaForServer.getAttribute("razina2"),ChartsTransportModel._criteriaForServer);
			breadCrumbsLayout.addMember(razina1);
			breadCrumbsLayout.addMember(BreadCrumbLabel.addStrelica());
			breadCrumbsLayout.addMember(razina2);
			if (ChartsTransportModel._criteriaForServer.getAttribute("razina3") != null){
				BreadCrumbLabel razina3 = new BreadCrumbLabel(ChartsTransportModel._criteriaForServer.getAttribute("razina3"),ChartsTransportModel._criteriaForServer);
				breadCrumbsLayout.addMember(BreadCrumbLabel.addStrelica());
				breadCrumbsLayout.addMember(razina3);
			}
			_breadCrumbLabel.setMembers(breadCrumbsLayout);
		}
	}
	/*
	 * List of years starting from 2010 untill current year.
	 */
	public static String[]  getGodineValueMap() {		
		int start_year = 2010;
		int end_year = AS2GwtDate.getCurrentYear();
		//override defaults
		GODINA_DO = end_year;
		GODINA = end_year;
		String[] array = new String[end_year-start_year+1];
		int pos = 0;
		for(int i=start_year; i<=end_year; i++){
			array[pos] = (end_year-pos)+"";
			pos++;			
		}
		return array;
	}
	public static LinkedHashMap<String, Object>  getMjeseciValueMap() {
		LinkedHashMap<String, Object> mjeseci = new LinkedHashMap<String, Object>();
		mjeseci.put("1", "Siječanj");
		mjeseci.put("2", "Veljača");
		mjeseci.put("3", "Ožujak");
		mjeseci.put("4", "Travanj");
		mjeseci.put("5", "Svibanj");
		mjeseci.put("6", "Lipanj");
		mjeseci.put("7", "Srpanj");
		mjeseci.put("8", "Kolovoz");
		mjeseci.put("9", "Rujan");
		mjeseci.put("10", "Listopad");
		mjeseci.put("11", "Studeni");
		mjeseci.put("12", "Prosinac");
		return mjeseci;
	}
	public static LinkedHashMap<String, Object> getProfitniCentriValueMap() {
		return AS2ClientContext.getProfitniCentriValueMap();
	}
	public static String formatDoubleDefault(double value) {
		return AS2ClientContext.formatDouble(value);
	}
	//Koristimo kod Pie chartova
	public static String formatDoubleNoDecimals(double value) {
		return AS2ClientContext.formatDoubleNoDecimals(value);
	}
	public static String formatNumberDefault(String value) {
		return AS2ClientContext.formatNumber(value);
	}
	public static Date formatStringToDate(String stringDate) {
		return AS2ClientContext.formatStringDateToDate(stringDate);
	}
	
	
}
