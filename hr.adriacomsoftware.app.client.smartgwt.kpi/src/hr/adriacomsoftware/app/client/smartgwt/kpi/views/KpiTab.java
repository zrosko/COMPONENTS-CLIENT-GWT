package hr.adriacomsoftware.app.client.smartgwt.kpi.views;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;

import com.smartgwt.client.widgets.tab.Tab;

public class KpiTab extends Tab{
	
	private static KpiTab instance = new KpiTab();
	public static KpiTab getInstance() {
		return instance;
	}
	
	public KpiTab() {
		super(KpiContext.KPI_TAB_TITLE);
	}
	
}
