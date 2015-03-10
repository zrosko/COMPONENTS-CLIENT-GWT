package hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip;

import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class ReportChartToolStrip extends ChartToolStrip {


	public ReportChartToolStrip(Record pokazatelj) {
		super();
		_pokazatelj = pokazatelj;
		if(pokazatelj!=null){
			initReportChartToolStrip();
			super.addItemsToToolStrip();
		}
	}
	private void initReportChartToolStrip() {
		super.getDefaultToolStripItems();
		_chartToolStripItem6 = new AS2FormItem/*ButtonItem*/("izvjestaj", AS2Field.FORM_BUTTON,"Izvještaj");
		_chartToolStripItem6.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_chartToolStripItem6.setWidth(100);
		_chartToolStripItem6.getField().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				HashMap<String,String> items =  getToolStripItemValuesAsMap();
				for(String key : items.keySet()){
					_pokazatelj.setAttribute(key, items.get(key));
				}
				//Postavljanje Razina
				if(ChartsTransportModel._selectedPokazatelj.getRazine()!=null){
					HashMap<String,String> razine = ChartsTransportModel._selectedPokazatelj.getRazine();
					for(String razina:razine.keySet()){
						_pokazatelj.setAttribute(razina, razine.get(razina));
					}
				}
				SC.askforValue("Unesite mjesec u " + _chartToolStripItem2.getField().getValue()+ ". godini", new ValueCallback() {
					@Override
					public void execute(String value) {
						if(value.matches("[1-9]|0[1-9]|1[012]")){
							_pokazatelj.setAttribute("mjesec", value);
							new AS2PrintableGridWindow(_pokazatelj);
						}else{
							SC.warn("Unijeli ste krivi mjesec!");
						}
						
						
					}
				});
			
				
			}
		});
	}
}
