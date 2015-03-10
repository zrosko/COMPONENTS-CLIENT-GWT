package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2ChartsOnClickWindow;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;

import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AS2PieBarNegStackChartOnClickWindow extends AS2ChartsOnClickWindow{

	public AS2PieBarNegStackChartOnClickWindow(SeriesClickEvent seriesClickEvent, ChartValueObject chartVo) {
		super(seriesClickEvent, chartVo);
	}

	public AS2ListGrid getListGrid() {
		int xPosition= _seriesClickEvent.getNearestPoint().getX().intValue();
		double y = _seriesClickEvent.getNearestPoint().getY().doubleValue();
		Record series = _chartVo.getSeries().get(xPosition).getAttributeAsRecord("record");
		String seriesName = series.getAttribute(ChartValueObject.TITLE);
		String x = _chartVo.getXCategories()[0];
		this.setTitle("Detalji: "+seriesName.toString());
		AS2ListGrid listGrid = new AS2ListGrid(true,null);
        listGrid.setAutoFetchData(false);
        ListGridField field1 = new ListGridField("vrijednost","Vrijednost x osi");
        ListGridField field2 = new ListGridField(seriesName, seriesName);
        if(!_chartVo.getYTitle().equals(""))
        	field2.setTitle(_chartVo.getYTitle()+": " +seriesName);
        field2.setType(ListGridFieldType.FLOAT);
        listGrid.setFields(field1,field2);
        ListGridRecord rec = new ListGridRecord();
		rec.setAttribute("vrijednost", x);
//		rec.setAttribute(seriesName,AS2ClientContext.nfGWT.format(y));
		rec.setAttribute(seriesName,KpiContext.formatDoubleDefault(y));
		listGrid.setData(new Record[]{rec});
		return listGrid;
	}
}
