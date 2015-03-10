package hr.adriacomsoftware.app.client.smartgwt.obrasci.views;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class EastLayoutTabSet extends TabSet {

	TabSet eastLayoutTabSet;

	public EastLayoutTabSet() {
		this.setWidth100();
		this.setHeight100();
		this.setBorder("0px");
//		this.setStyleName("crm-ContextArea");
		this.setOverflow(Overflow.HIDDEN);
		OContext._homeTab = getHomeTab();
		this.setTabs(OContext._homeTab);
		this.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
//				if(event.getTab() == OContext._homeTab)
//					OContext._breadCrumbLabel.setMembers(new BreadCrumbLabel(event.getTab().getTitle()));
//				else if(event.getTab() == BSContext._kpiTab){
//					if(BSContext._kpiTab.getPane()!=null)
//						BSContext.setBreadCrumbs();
//					else{
//						BSContext._breadCrumbLabel.setMembers(new BreadCrumbLabel(event.getTab().getTitle()));
//					}
//				}
			}
		});
	}

	private Tab getHomeTab() {
		Tab tab = new Tab("Home");
		VLayout layout = new VLayout();
	    HLayout hLayout = new HLayout();
        hLayout.setWidth100();
        hLayout.setHeight(200);
        hLayout.setMembersMargin(8);
        layout.addMember(hLayout);
		tab.setPane(layout);
		return tab;
	}

//	private ResizeableChartCanvas getChartInCanvas(BaseChart<?> chart){
//		ResizeableChartCanvas canvas = new ResizeableChartCanvas(chart);
//		canvas.setHeight100();
//		canvas.setWidth100();
//		return canvas;
//
//	}

}
