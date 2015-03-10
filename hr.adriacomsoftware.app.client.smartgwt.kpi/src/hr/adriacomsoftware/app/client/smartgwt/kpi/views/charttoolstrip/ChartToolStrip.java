package hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ChartToolStrip extends ToolStrip {
	protected AS2FormItem/*SelectItem*/ _chartToolStripItem1;
	protected AS2FormItem _chartToolStripItem2;
	protected AS2FormItem/*SelectItem*/ _chartToolStripItem3;
	protected AS2FormItem _chartToolStripItem4;
	protected AS2FormItem/*SelectItem*/ _chartToolStripItem5;
	protected AS2FormItem _chartToolStripItem6;
	protected AS2FormItem _chartToolStripItem7;
	protected Record _pokazatelj;

	public ChartToolStrip() {
		this.setPadding(0);
		this.setMargin(0);
		this.setTop(5);
	}

	public ChartToolStrip(Record pokazatelj) {
		_pokazatelj = pokazatelj;
		this.setPadding(0);
		this.setMargin(0);
		this.setTop(5);
		if(pokazatelj!=null){
			initDefaultToolStrip();
			addItemsToToolStrip();
		}
	}

	protected void addItemsToToolStrip() {
		if(_chartToolStripItem1!=null){
			this.addFormItem(_chartToolStripItem1.getField());
		}
		if(_chartToolStripItem2!=null){
			this.addFormItem(_chartToolStripItem2.getField());
		}
		if(_chartToolStripItem3!=null){
			this.addFormItem(_chartToolStripItem3.getField());
		}
		if(_chartToolStripItem4!=null){
			this.addFormItem(_chartToolStripItem4.getField());
		}
		if(_chartToolStripItem5!=null){
			this.addFormItem(_chartToolStripItem5.getField());
		}
		if(_chartToolStripItem6!=null){
			this.addFormItem(_chartToolStripItem6.getField());
		}
		if(_chartToolStripItem7!=null){
			this.addFormItem(_chartToolStripItem7.getField());
		}
	}

	private void initDefaultToolStrip() {
		getDefaultToolStripItems();
	}

	protected void getDefaultToolStripItems() {
		_chartToolStripItem1 = new AS2FormItem/*SelectItem*/("profitni_centar",AS2Field.FORM_SELECT,"Profitni Centar");
		_chartToolStripItem1.setWidth(80);
		_chartToolStripItem1.setValueMap(KpiContext.getProfitniCentriValueMap());
		_chartToolStripItem1.getField().setWrapTitle(false);
		_chartToolStripItem1.getField().setDefaultValue(KpiContext.PROFITNI_CENTAR);
		_chartToolStripItem1.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem2 = new  AS2FormItem/*SelectItem*/("godina",AS2Field.FORM_SELECT, "Godina");
		_chartToolStripItem2.setWidth(60);
		_chartToolStripItem2.getField().setWrapTitle(false);
		_chartToolStripItem2.setValueMap(KpiContext.getGodineValueMap());
		_chartToolStripItem2.getField().setDefaultValue(KpiContext.GODINA);
		_chartToolStripItem2.getField().addChangedHandler(changedHandler());
	}

	public Criteria getToolStripItemValuesAsCriteria(){
		Criteria values = new Criteria();
		if(_chartToolStripItem1!=null && _chartToolStripItem1.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem1.getField().getName(),_chartToolStripItem1.getField().getValue().toString());///getValueAsString());
			values.setAttribute("chartToolStripItem1",_chartToolStripItem1.getField().getName());
		}
		if(_chartToolStripItem2!=null && _chartToolStripItem2.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem2.getField().getName(),_chartToolStripItem2.getField().getValue());
			values.setAttribute("chartToolStripItem2",_chartToolStripItem2.getField().getName());
		}
		if(_chartToolStripItem3!=null && _chartToolStripItem3.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem3.getField().getName(),_chartToolStripItem3.getField().getValue());
			values.setAttribute("chartToolStripItem3",_chartToolStripItem3.getField().getName());
		}
		if(_chartToolStripItem4!=null && _chartToolStripItem4.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem4.getField().getName(),_chartToolStripItem4.getField().getValue());
			values.setAttribute("chartToolStripItem4",_chartToolStripItem4.getField().getName());
		}
		if(_chartToolStripItem5!=null && _chartToolStripItem5.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem5.getField().getName(),_chartToolStripItem5.getField().getValue().toString());//getValueAsString());
			values.setAttribute("chartToolStripItem5",_chartToolStripItem5.getField().getName());
		}
		if(_chartToolStripItem6!=null && _chartToolStripItem6.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem6.getField().getName(),_chartToolStripItem6.getField().getValue());
			values.setAttribute("_chartToolStripItem6",_chartToolStripItem6.getField().getName());
		}
		if(_chartToolStripItem7!=null && _chartToolStripItem7.getField().getValue()!=null){
			values.setAttribute(_chartToolStripItem7.getField().getName(),_chartToolStripItem7.getField().getValue());
			values.setAttribute("_chartToolStripItem7",_chartToolStripItem7.getField().getName());
		}
		return values;
	}

	public HashMap<String,String>  getToolStripItemValuesAsMap(){
		HashMap<String,String> values = new HashMap<String,String>();
		if(_chartToolStripItem1!=null && _chartToolStripItem1.getField().getValue()!=null){
			values.put(_chartToolStripItem1.getField().getName(),_chartToolStripItem1.getField().getValue().toString());//AsString());
		}
		if(_chartToolStripItem2!=null && _chartToolStripItem2.getField().getValue()!=null){
			values.put(_chartToolStripItem2.getField().getName(),_chartToolStripItem2.getField().getValue().toString());
		}
		if(_chartToolStripItem3!=null && _chartToolStripItem3.getField().getValue()!=null){
			values.put(_chartToolStripItem3.getField().getName(),_chartToolStripItem3.getField().getValue().toString());//AsString());
		}
		if(_chartToolStripItem4!=null && _chartToolStripItem4.getField().getValue()!=null){
			values.put(_chartToolStripItem4.getField().getName(),_chartToolStripItem4.getField().getValue().toString());
		}
		if(_chartToolStripItem5!=null && _chartToolStripItem5.getField().getValue()!=null){
			values.put(_chartToolStripItem5.getField().getName(),_chartToolStripItem5.getField().getValue().toString());//AsString());
		}
		if(_chartToolStripItem6!=null && _chartToolStripItem6.getField().getValue()!=null){
			values.put(_chartToolStripItem6.getField().getName(),_chartToolStripItem6.getField().getValue().toString());
		}
		if(_chartToolStripItem7!=null && _chartToolStripItem7.getField().getValue()!=null){
			values.put(_chartToolStripItem7.getField().getName(),_chartToolStripItem7.getField().getValue().toString());
		}
		return values;

	}

	public ChangedHandler changedHandler(){
		return new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				ChartsTransportModel.callServer(true,getToolStripItemValuesAsCriteria());
			}
		};
	}

//	public ChartToolStrip(PokazateljiNavigationTreeGrid pokazateljiTreeGrid) {
//		this._pokazateljiTreeGrid = pokazateljiTreeGrid;
//		this.setPadding(0);
//		this.setMargin(0);
//		this.setTop(5);
//		Record selectedPokazatelj = _pokazateljiTreeGrid.getSelectedRecord();
//		selectedPokazatelj.getAttributes();
//		if (selectedPokazatelj.getAttribute("ChartType").equalsIgnoreCase("SpiderWeb")) {
//			initSpiderWebToolStrip();
//		} else {
//			initDefaultToolStrip();
//		}
//		addItemsToToolStrip();
//	}
//	private void initSpiderWebToolStrip() {
//		getDefaultToolStripItems();
//		_chartToolStripItem2.setTitle("Godina od");
//		_chartToolStripItem3 = new SelectItem("godina2","Godina do");
//		_chartToolStripItem3.setWidth(70);
//		_chartToolStripItem3.setWrapTitle(false);
//		_chartToolStripItem3.setValueMap("2014","2013", "2012", "2011", "2010");
//		_chartToolStripItem3.setAnimatePickList(true);
//		_chartToolStripItem3.setDefaultValue(KpiContext.GODINA2);
//		_chartToolStripItem3.addChangedHandler(new ChangedHandler() {
//			public void onChanged(ChangedEvent event) {
//				KpiContext.callServer(_pokazatelj,true);
//			}
//		});
//		_chartToolStripItem4 = new SelectItem("mjesec","Mjesec");
//		LinkedHashMap<Integer, String> mjeseci = new LinkedHashMap<Integer, String>();
//		mjeseci.put(1, "Siječanj");
//		mjeseci.put(2, "Veljača");
//		mjeseci.put(3, "Ožujak");
//		mjeseci.put(4, "Travanj");
//		mjeseci.put(5, "Svibanj");
//		mjeseci.put(6, "Lipanj");
//		mjeseci.put(7, "Srpanj");
//		mjeseci.put(8, "Kolovoz");
//		mjeseci.put(9, "Rujan");
//		mjeseci.put(10, "Listopad");
//		mjeseci.put(11, "Studeni");
//		mjeseci.put(12, "Prosinac");
//		_chartToolStripItem4.setWidth(80);
//		_chartToolStripItem4.setWrapTitle(false);
//		_chartToolStripItem4.setValueMap(mjeseci);
//		_chartToolStripItem4.setAnimatePickList(true);
//		_chartToolStripItem4.setDefaultValue(KpiContext.MJESEC);
//		_chartToolStripItem4.addChangedHandler(new ChangedHandler() {
//			public void onChanged(ChangedEvent event) {
//				KpiContext.callServer(_pokazatelj,true);
//			}
//		});
//
//	}


//	private void setDefaultToolStripItems(List<SelectItem> defaultItems){
//		for(SelectItem defaultItem: getDefaultToolStripItems()){
//			if(_chartToolStrip_chartToolStripItem1!=null){
//				_chartToolStrip_chartToolStripItem1=defaultItem;
//			}else if(_chartToolStrip_chartToolStripItem2!=null){
//				_chartToolStrip_chartToolStripItem2=defaultItem;
//			}else if(_chartToolStripItem3!=null){
//				_chartToolStripItem3=defaultItem;
//			}else if(_chartToolStripItem4!=null){
//				_chartToolStripItem4=defaultItem;
//			}else if(_chartToolStripItem5!=null){
//				_chartToolStripItem5=defaultItem;
//			}
//		}
//		}
//	}
}
