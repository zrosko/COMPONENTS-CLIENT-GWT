package hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation;
//package hr.adriacomsoftware.app.client.smartgwt.kpi.client.views.navigation;
//
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.views.charttoolstrip.ChartToolStrip;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.views.charttoolstrip.SpiderWebChartToolStrip;
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.widgets.charts.core.ChartValueObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.smartgwt.client.data.Record;
//import com.smartgwt.client.widgets.tree.TreeNode;
//
//public class CopyOfPokazateljiTreeNode extends TreeNode {
//
//
//	public CopyOfPokazateljiTreeNode(Record pokazatelj) {
//		if (pokazatelj.getAttribute("ChartTypeNo") != null) {
//			this.setId(pokazatelj.getAttribute("Id"));
//			this.setParentId(pokazatelj.getAttribute("ParentId"));
//			this.setName(pokazatelj.getAttribute("Name"));
//			this.setChartType(pokazatelj.getAttribute("ChartType"));
//			this.setChartTypes(pokazatelj);
//			if(pokazatelj.getAttribute("Service")!=null)
//				this.setService(pokazatelj.getAttribute("Service"));
//
//		} else {
//			this.setId(pokazatelj.getAttribute("Id"));
//			this.setParentId(pokazatelj.getAttribute("ParentId"));
//			this.setName(pokazatelj.getAttribute("Name"));
//			this.setChartType(pokazatelj.getAttribute("ChartType"));
//			if(pokazatelj.getAttribute("Service")!=null)
//				this.setService(pokazatelj.getAttribute("Service"));
//
//		}
//		if(pokazatelj.getAttribute("ChartType").equals("SpiderWeb"))
//			this.setAttribute("chartToolstrip",new SpiderWebChartToolStrip(pokazatelj));
//		else{
//			if(!pokazatelj.getAttribute("ChartType").equals("folder"))
//				this.setAttribute("chartToolstrip",new ChartToolStrip(pokazatelj));
//		}
//
//		if (pokazatelj.getAttribute("Icon") != null
//				&& !pokazatelj.getAttribute("Icon").equalsIgnoreCase(
//						"folder")) {
////			this.setIcon("./icons/graph/gwt-highcharts/16/"
////					+ pokazatelj.getAttribute("Icon") + ".png");
//			this.setIcon("./icons/graph/16/"
//					+ pokazatelj.getAttribute("Icon") + ".png");
//		}
//
//	}
//
//	public CopyOfPokazateljiTreeNode(String id, String parentId, String name,
//			Record pokazatelj, Integer level) {
//
//	}
//
//	public CopyOfPokazateljiTreeNode(String id, String parentId, String name,
//			String type, Integer level) {
//
//	}
//
//	public void setId(String value) {
//		setAttribute("Id", value);
//	}
//
//	public void setParentId(String value) {
//		setAttribute("ParentId", value);
//	}
//
//	public void setName(String name) {
//		setAttribute("Name", name);
//	}
//
//	public void setChartType(String type) {
//		setAttribute("ChartType", type);
//	}
//
//	public void setChartRs(List<ChartValueObject> chartRs) {
//		setAttribute("ChartRs", chartRs);
//	}
//
//	public List<ChartValueObject>  getChartRs() {
//		return (List<ChartValueObject>) getAttributeAsObject("ChartRs");
//	}
//
//	// If more than one chart type
//	public void setChartTypes(Record pokazatelj) {
//		int chartTypeNo = Integer.parseInt(pokazatelj
//				.getAttribute("ChartTypeNo"));
//		this.setAttribute("ChartTypeNo", chartTypeNo);
//		for (int i = 0; i < chartTypeNo; i++) {
//			if (pokazatelj.getAttribute("ChartType" + (i + 1)) != null)
//				setAttribute("ChartType" + (i + 1),
//						pokazatelj.getAttribute("ChartType" + (i + 1)));
//		}
//	}
//
//	public List<String> getChartTypes() {
//		List<String> chartTypes = new ArrayList<String>();
//		int chartTypeNo = Integer.parseInt(getAttribute("ChartTypeNo"));
//		chartTypes.add(getAttribute("ChartType"));
//		for (int i = 0; i < chartTypeNo; i++) {
//			if (this.getAttribute("ChartType" + (i + 1)) != null)
//				chartTypes.add(this.getAttribute("ChartType" + (i + 1)));
//		}
//		return chartTypes;
//
//	}
//	private void setService(String service) {
//		setAttribute("Service", service);
//	}
//
//}
