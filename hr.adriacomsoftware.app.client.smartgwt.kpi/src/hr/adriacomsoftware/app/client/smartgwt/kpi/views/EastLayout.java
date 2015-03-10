package hr.adriacomsoftware.app.client.smartgwt.kpi.views;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class EastLayout extends VLayout {


	public EastLayout() {
		this.setStyleName("crm-ContextArea");
		this.addMember(getBreadCrumbLabel());
		this.addMember(new EastLayoutTabSet());
		this.setOverflow(Overflow.HIDDEN);
	}

	private HLayout getBreadCrumbLabel() {
		HLayout layout = new HLayout();
		layout.setMembers(new BreadCrumbLabel(""));
		KpiContext._breadCrumbLabel = layout;
		return layout;
	}

}
