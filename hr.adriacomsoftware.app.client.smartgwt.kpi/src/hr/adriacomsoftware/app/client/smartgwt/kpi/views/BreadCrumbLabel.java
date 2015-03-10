package hr.adriacomsoftware.app.client.smartgwt.kpi.views;

import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ApplicationMenu;
import hr.as2.inf.common.charts.AS2ChartConstants;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.tree.TreeNode;

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
				for(int i=0;i<ChartsTransportModel._pokazateljiTreeGrid.getRecords().length;i++){
					TreeNode node = ChartsTransportModel._pokazateljiTreeGrid.getRecord(i);
					if(node.getAttribute(AS2ChartConstants.AS2_CHART_TREE__NAME).equals(_contents)){
						if(node.getAttributeAsBoolean("isFolder")){
							ChartsTransportModel._pokazateljiTreeGrid.toggleFolder(node);
						}else{
							ChartsTransportModel._pokazateljiTreeGrid.selectRecord(node,false);
						}
					}
				}
//				AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
//						      new Criterion("Pokazatelj", OperatorId.EQUALS, _contents),
//						      new AdvancedCriteria(OperatorId.OR, new Criterion[]{
//						      new Criterion("pokazateljId", OperatorId.EQUALS, _criteria.getAttribute("pokazateljId")),
//						      new Criterion("chartType", OperatorId.EQUALS,_criteria.getAttribute("pokazateljId"))
//						      })
//			 });
//
//				for(String attribute:criteria.getAttributes()){
//					criteria.getAttribute(attribute);
//				}
//
//				Record record = KpiContext._pokazateljiTreeGrid.find(criteria);
//				for(String attribute:record.getAttributes()){
//					record.getAttribute(attribute);
//				}


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
