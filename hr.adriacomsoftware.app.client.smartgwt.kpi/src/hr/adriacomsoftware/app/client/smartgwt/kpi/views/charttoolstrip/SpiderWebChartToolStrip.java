package hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;

import com.smartgwt.client.data.Record;

public class SpiderWebChartToolStrip extends ChartToolStrip {


	public SpiderWebChartToolStrip(Record pokazatelj) {
		super();
		_pokazatelj = pokazatelj;
		if(pokazatelj!=null){
			initSpiderWebToolStrip();
			super.addItemsToToolStrip();
		}
	}
	private void initSpiderWebToolStrip() {
		_chartToolStripItem1 = new AS2FormItem/*SelectItem*/("profitni_centar",AS2Field.FORM_SELECT,"Profitni Centar");
		_chartToolStripItem1.setWidth(80);
		_chartToolStripItem1.setValueMap(KpiContext.getProfitniCentriValueMap());
		_chartToolStripItem1.getField().setWrapTitle(false);
		_chartToolStripItem1.getField().setDefaultValue(KpiContext.PROFITNI_CENTAR);
		_chartToolStripItem1.getField().addChangedHandler(changedHandler());
		_chartToolStripItem2 = new  AS2FormItem/*SelectItem*/("mjesec",AS2Field.FORM_SELECT,"Mjesec do");
		_chartToolStripItem2.setWidth(80);
		_chartToolStripItem2.getField().setWrapTitle(false);
		_chartToolStripItem2.setValueMap(KpiContext.getMjeseciValueMap());
		_chartToolStripItem2.getField().setDefaultValue(KpiContext.MJESEC);
		_chartToolStripItem2.getField().addChangedHandler(changedHandler());
		_chartToolStripItem3 = new AS2FormItem/*SelectItem*/("godina", AS2Field.FORM_SELECT,"Godina od");
		_chartToolStripItem3.setWidth(60);
		_chartToolStripItem3.getField().setWrapTitle(false);
		_chartToolStripItem3.setValueMap(KpiContext.getGodineValueMap());
		_chartToolStripItem3.getField().setDefaultValue(KpiContext.GODINA-1);
		_chartToolStripItem3.getField().addChangedHandler(changedHandler());
		_chartToolStripItem4 = new AS2FormItem/*SelectItem*/("godina2",AS2Field.FORM_SELECT,"Godina do");
		_chartToolStripItem4.setWidth(70);
		_chartToolStripItem4.getField().setWrapTitle(false);
		_chartToolStripItem4.setValueMap(KpiContext.getGodineValueMap());
		_chartToolStripItem4.getField().setDefaultValue(KpiContext.GODINA_DO);
		_chartToolStripItem4.getField().addChangedHandler(changedHandler());
	}
}
