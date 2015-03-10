package hr.adriacomsoftware.app.client.smartgwt.obrasci.views;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ApplicationMenu;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class BreadCrumbLabel extends Label {
	Criteria _criteria;
	String _contents;

	public BreadCrumbLabel(String contents) {
		this(contents,null);
	}

	public BreadCrumbLabel(String contents,Criteria criteriaForServer) {
		super(contents);
		this._criteria = criteriaForServer;
		this._contents = contents;
		this.setStyleName("crm-BreadCrumbsLabel");
		this.setHeight(AS2ApplicationMenu.APPLICATION_MENU_HEIGHT);
		this.setCursor(Cursor.HAND);
		this.setShowRollOver(true);
		this.setWrap(false);
		this.setWidth(contents.length()+10);
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}
		});
	}

	public static Label addStrelica(){
		Label label =  new Label(" > ") ;
		label.setStyleName("crm-BreadCrumbLabel");
		label.setHeight(AS2ApplicationMenu.APPLICATION_MENU_HEIGHT);
		label.setWidth(5);
		label.setWrap(false);
		return label;
	}

}
