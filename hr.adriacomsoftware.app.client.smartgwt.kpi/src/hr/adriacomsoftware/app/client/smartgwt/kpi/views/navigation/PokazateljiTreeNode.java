package hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation;

import hr.adriacomsoftware.app.client.smartgwt.kpi.views.HomeTab;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.KpiTab;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.ChartToolStrip;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.ExcelChartToolStrip;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.ReportChartToolStrip;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.SpiderWebChartToolStrip;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.as2.inf.common.charts.AS2ChartConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.TreeNode;

public class PokazateljiTreeNode extends TreeNode {

	public static final String CHART_RS="chartRs";
	public static final String CHART_TOOLSTRIP="chartToolstrip";
	public static final String RAZINE="razine";

	public PokazateljiTreeNode(Record pokazatelj) {
		//Ako ima više od jednog grafa u tabu
		if (!pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO).equals("")) {
			this.setId(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ID));
			this.setParentId(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID));
			this.setName(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__NAME));
			this.setChartType(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE));
			this.setChartTypes(pokazatelj);
			this.setService(pokazatelj);
		//Samo jedan graf u tabu
		} else {	
			this.setId(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ID));
			this.setParentId(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID));
			this.setName(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__NAME));
			this.setChartType(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE));
			this.setService(pokazatelj);
		}
		this.setChartToolstrip(pokazatelj);
		this.setIcon(pokazatelj);
		this.setTab(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TAB));

//		if (pokazatelj.getAttribute(ICON) != null&& !pokazatelj.getAttribute(ICON).equalsIgnoreCase("folder")) {
//			this.setIcon("./icons/graph/16/"+ pokazatelj.getAttribute(ICON) + ".png");
//		}
	}

	public PokazateljiTreeNode(String id, String parentId, String name,
			Record pokazatelj, Integer level) {

	}

	public PokazateljiTreeNode(String id, String parentId, String name,
			String type, Integer level) {

	}

	public void setId(String value) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__ID, value);
	}
	public String getId() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__ID);
	}

	public String getPokazateljId() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__ID);
	}


	public void setParentId(String value) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID, value);
	}

	public String getParentId() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__PARENT_ID);
	}

	public void setName(String name) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__NAME, name);
	}

	public String getName() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__NAME);
	}

	public String getPokazatelj() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__NAME);
	}

	public void setChartType(String type) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE, type);
	}

	public String getChartType() {
		return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE);
	}

	public void setService(String service) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__SERVICE, service);
	}

	public void setService(Record pokazatelj) {
		if(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__SERVICE)!=null)
			this.setService(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__SERVICE));
	}


	public String getService() {
		if(getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__SERVICE)!=null)
			return getAttributeAsString(AS2ChartConstants.AS2_CHART_TREE__SERVICE);
		else
			return "";
	}

	public void setIcon(Record pokazatelj){
		if (pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ICON) != null&& !pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ICON).equalsIgnoreCase("folder")) {
			String icon = pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ICON);
			switch (icon) {
			case "Line":
				super.setIcon(AS2Resources.INSTANCE.line_highchart().getSafeUri().asString());
				break;
			case "Bar":
				super.setIcon(AS2Resources.INSTANCE.bar_highchart().getSafeUri().asString());
				break;
			case "BarNegStack":
				super.setIcon(AS2Resources.INSTANCE.barNegStack_highchart().getSafeUri().asString());
				break;
			case "BasicPie":
				super.setIcon(AS2Resources.INSTANCE.basicPie_highchart().getSafeUri().asString());
				break;
			case "SpiderWeb":
				super.setIcon(AS2Resources.INSTANCE.spiderWeb_highchart().getSafeUri().asString());
				break;
			case "Area":
				super.setIcon(AS2Resources.INSTANCE.area_highchart().getSafeUri().asString());
				break;
			default:
				break;
			}
//			super.setIcon("./icons/graph/16/"+ pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ICON) + ".png");
		}
	}

	public void setTab(String tab) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__TAB,tab);
	}
	public Tab getTab() {
		if(this.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TAB)!=null){
			if(this.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TAB).contains("home"))
//				return KpiContext._homeTab;
				return HomeTab.getInstance();
			else if(this.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TAB).contains("kpi"))
//				return KpiContext._kpiTab;
				return KpiTab.getInstance();
		}
//		return KpiContext._kpiTab;
		return KpiTab.getInstance();
	}

	public void setChartTypeNo(int chartTypeNo) {
		setAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO, chartTypeNo);
	}

	public Object getChartTypeNo() {
		return getAttributeAsObject(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO);
	}

	public void setChartRs(RecordList chartRs) {
		setAttribute(CHART_RS, chartRs);
	}

	public void setChartToolstrip(Record pokazatelj) {
		pokazatelj.getAttributes();
		
		if(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ID).equals("bnk_klijenti_prodaja")){
			this.setChartToolstrip(new ExcelChartToolStrip(pokazatelj));
		}else if(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__ID).equals("bnk_financije_rdg")){
			this.setChartToolstrip(new ExcelChartToolStrip(pokazatelj));
		}else if(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE).equals("SpiderWeb"))
			this.setChartToolstrip(new SpiderWebChartToolStrip(pokazatelj));
		else if(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TOOL_STRIP_TYPE)!=null && 
			pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__TOOL_STRIP_TYPE).equals("ReportChartToolStrip")){
			this.setChartToolstrip(new ReportChartToolStrip(pokazatelj));
		}else{
			if(!pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE).equals("folder"))
				this.setChartToolstrip(new ChartToolStrip(pokazatelj));
		}
	}

	public void setChartToolstrip(ChartToolStrip toolstrip) {
		setAttribute(CHART_TOOLSTRIP, toolstrip);
	}

	public ChartToolStrip getChartToolstrip() {
		return (ChartToolStrip)getAttributeAsObject(CHART_TOOLSTRIP);
	}


	@SuppressWarnings("unchecked")
	public List<ChartValueObject>  getChartRs() {
		return (List<ChartValueObject>) getAttributeAsObject(CHART_RS);
	}

	public void setRazine(HashMap<String,String> razine) {
		setAttribute(RAZINE, razine);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String,String>  getRazine() {
		return (HashMap<String, String>) getAttributeAsMap(RAZINE);
	}

	// If more than one chart
	public void setChartTypes(Record pokazatelj) {
		int chartTypeNo = Integer.parseInt(pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE_NO));
		this.setChartTypeNo(chartTypeNo);
		for (int i = 0; i < chartTypeNo; i++) {
			if (pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE + (i + 1)) != null)
				setAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE + (i + 1),
						pokazatelj.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE + (i + 1)));
		}
	}

	// If more than one chart
	public List<String> getChartTypes() {
		List<String> chartTypes = new ArrayList<String>();
		chartTypes.add(getChartType());
		for (int i = 0; i < Integer.parseInt(getChartTypeNo()+""); i++) {
			if (this.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE + (i + 1)) != null)
				chartTypes.add(this.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE + (i + 1)));
		}
		return chartTypes;
	}
}
