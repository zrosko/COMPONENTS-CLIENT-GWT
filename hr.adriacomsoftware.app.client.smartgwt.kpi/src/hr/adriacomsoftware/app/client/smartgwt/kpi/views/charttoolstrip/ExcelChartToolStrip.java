package hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class ExcelChartToolStrip extends ChartToolStrip {


	public ExcelChartToolStrip(Record pokazatelj) {
		super();
		_pokazatelj = pokazatelj;
		if(pokazatelj!=null){
			initExcelToolStrip();
			super.addItemsToToolStrip();
		}
	}
	private void initExcelToolStrip() {
		_chartToolStripItem1 = new AS2FormItem/*SelectItem*/("profitni_centar",AS2FormItem.FORM_SELECT,"Profitni Centar");
		_chartToolStripItem1.setWidth(80);
		_chartToolStripItem1.setValueMap(KpiContext.getProfitniCentriValueMap());
		_chartToolStripItem1.getField().setWrapTitle(false);
//		_chartToolStripItem1.getField().setAnimatePickList(true);
		_chartToolStripItem1.getField().setDefaultValue(KpiContext.PROFITNI_CENTAR);
		_chartToolStripItem1.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem2 = new AS2FormItem/*SelectItem*/("godina",AS2Field.FORM_SELECT,"Godina od");
		_chartToolStripItem2.setWidth(70);
		_chartToolStripItem2.getField().setWrapTitle(false);
		_chartToolStripItem2.setValueMap(KpiContext.getGodineValueMap());
		_chartToolStripItem2.getField().setDefaultValue(KpiContext.GODINA-1);
		_chartToolStripItem2.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem3 = new AS2FormItem/*SelectItem*/("mjesec",AS2Field.FORM_SELECT,"Mjesec od");
		_chartToolStripItem3.setWidth(80);
		_chartToolStripItem3.getField().setWrapTitle(false);
		_chartToolStripItem3.setValueMap(KpiContext.getMjeseciValueMap());
//		_chartToolStripItem3.setAnimatePickList(true);
		_chartToolStripItem3.getField().setDefaultValue(KpiContext.MJESEC);
		_chartToolStripItem3.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem4 = new AS2FormItem/*SelectItem*/("godina2",AS2Field.FORM_SELECT, "Godina do");
		_chartToolStripItem4.setWidth(70);
		_chartToolStripItem4.getField().setWrapTitle(false);
		_chartToolStripItem4.setValueMap(KpiContext.getGodineValueMap());
		_chartToolStripItem4.getField().setDefaultValue(KpiContext.GODINA_DO);
		_chartToolStripItem4.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem5 = new AS2FormItem/*SelectItem*/("mjesec2",AS2Field.FORM_SELECT,"Mjesec do");
		_chartToolStripItem5.setWidth(80);
		_chartToolStripItem5.getField().setWrapTitle(false);
		_chartToolStripItem5.setValueMap(KpiContext.getMjeseciValueMap());
//		_chartToolStripItem5.setAnimatePickList(true);
		_chartToolStripItem5.getField().setDefaultValue(KpiContext.MJESEC);
		_chartToolStripItem5.getField().addChangedHandler(changedHandler());
		
		_chartToolStripItem6 = new AS2FormItem/*ButtonItem*/("izvjestaj",AS2Field.FORM_BUTTON, "Izvještaj");
		_chartToolStripItem6.setIcon(AS2Resources.EXCELX_ICON_PATH);
		_chartToolStripItem6.setWidth(100);
		_chartToolStripItem6.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			
				AS2ReportingServices report = new AS2ReportingServices(ExcelChartToolStrip.this, "hr.adriacomsoftware.app.server.kpi.reports.KpiReportServer", "citajIzvjestajExcel");
				HashMap<String,String> items =  getToolStripItemValuesAsMap();
				for(String key : items.keySet()){
					report.setParameter(key, items.get(key));
//					_pokazatelj.setAttribute(key, items.get(key));
				}
//				report.setParameter(AS2ReportingServices.AS2_RESPONSE_TYPE, "bytes");
//				report.setParameter(AS2ReportingServices.REPORT_FORMAT, "xls");
				//Postavljanje Razina
				if(ChartsTransportModel._selectedPokazatelj.getRazine()!=null){
					HashMap<String,String> razine = ChartsTransportModel._selectedPokazatelj.getRazine();
					for(String razina:razine.keySet()){
						report.setParameter(razina, razine.get(razina));
//						_pokazatelj.setAttribute(razina, razine.get(razina));
					}
				}
				report.getExcelReport("xls");
//				_pokazatelj.setAttribute("as2_response_type", "bytes");//TODO definirati konstante
//				_pokazatelj.setAttribute("as2_report_format", "xls");
//				_pokazatelj.setAttribute("remoteobject", "hr.adriacomsoftware.app.server.kpi.reports.KpiReportServer");
//				_pokazatelj.setAttribute("remotemethod", "citajIzvjestajExcel");
//				DynamicForm _downloadForm = new DynamicForm();
//				_downloadForm.setEncoding(Encoding.MULTIPART);
//				String actionUrl=GWT.getHostPageBaseURL()+"json_servlet"+"?";
//				for(String key:_pokazatelj.getAttributes()){
//					actionUrl=actionUrl+key+"="+_pokazatelj.getAttribute(key)+"&";
//				}
//				actionUrl = actionUrl.substring(0,actionUrl.length()-1);//Delets last & in actionURL
//				Window.open(actionUrl, "_blank", null);
//				_downloadForm.setAction(actionUrl);
//				_downloadForm.draw();
//				_downloadForm.setVisible(false);
//				_downloadForm.submitForm();
//				_downloadForm.destroy();
			}
//				SC.askforValue("Unesite mjesec u " + _chartToolStripItem2.getValueAsString()+ ". godini", new ValueCallback() {
//					@Override
//					public void execute(String value) {
//						if(value.matches("[1-9]|0[1-9]|1[012]")){
//							_pokazatelj.setAttribute("mjesec", value);
//							new AS2PrintableGridWindow(_pokazatelj);
//						}else{
//							SC.warn("Unijeli ste krivi mjesec!");
//						}
//						
//						
//					}
//				});
			
				
			});
	}
}
