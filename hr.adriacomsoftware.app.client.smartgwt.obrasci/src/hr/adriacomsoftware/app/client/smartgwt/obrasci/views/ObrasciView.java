package hr.adriacomsoftware.app.client.smartgwt.obrasci.views;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

public class ObrasciView extends VLayout {


	public ObrasciView() {
		this.setStyleName("crm-ContextArea");
		this.setOverflow(Overflow.HIDDEN);
//		this.setShowResizeBar(true);
		 OContext._obrasciView = this;
	}

//	private HLayout getBreadCrumbLabel() {
//		HLayout layout = new HLayout();
//		layout.setMembers(new BreadCrumbLabel(""));
//		OContext._breadCrumbLabel = layout;
//		return layout;
//	}

}
