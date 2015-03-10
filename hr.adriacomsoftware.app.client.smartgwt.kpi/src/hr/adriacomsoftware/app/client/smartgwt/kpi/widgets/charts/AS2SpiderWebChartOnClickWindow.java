package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip.ChartToolStrip;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.AS2ChartsOnClickWindow;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core.ChartValueObject;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;

import java.util.Date;
import java.util.HashMap;

import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AS2SpiderWebChartOnClickWindow extends AS2ChartsOnClickWindow{

	public AS2SpiderWebChartOnClickWindow(SeriesClickEvent seriesClickEvent, ChartValueObject chartVo) {
		super(seriesClickEvent,chartVo);
	}

	public AS2SpiderWebChartOnClickWindow(PointClickEvent pointClickEvent, ChartValueObject chartVo) {
		super(pointClickEvent,chartVo);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public AS2ListGrid getListGrid() {
		String seriesName = "";
		int xPosition = 0;
		double y = 0;
		if(_clickedSeries!=null){
			seriesName = _clickedSeries.getSeriesName();
			xPosition= _clickedSeries.getNearestPoint().getX().intValue();
			y = _clickedSeries.getNearestPoint().getY().doubleValue();
		}else{
			seriesName = _clickedPoint.getSeriesName();
			xPosition= _clickedPoint.getPoint().getX().intValue();
			y = _clickedPoint.getPoint().getY().doubleValue();
		}
		String[] xCategories = 	_chartVo.getXCategories();
		String x = xCategories[xPosition];
		this.setTitle("Detalji: "+seriesName);

		AS2ListGrid listGrid = new AS2ListGrid(true,null);
        listGrid.setAutoFetchData(false);
        ListGridField field = new ListGridField("polje","Detalji");
        listGrid.setFields(field);

        HashMap<String,String> oznake = null;
        if(_chartVo.getAttributeAsObject(ChartValueObject.OZNAKE)!=null){
        	oznake = (HashMap<String, String>) _chartVo.getAttributeAsMap(ChartValueObject.OZNAKE);
        }

//        String godina_pocetak = 
//        		ChartsTransportModel._criteriaForServer.getAttribute("godina_pocetak");
//        String godina_kraj = ChartsTransportModel._criteriaForServer.getAttribute("godina_kraj");
//        String mjesec_kraj = ChartsTransportModel._criteriaForServer.getAttribute("mjesec_kraj");
//        String profitni_centar = ChartsTransportModel._criteriaForServer.getAttribute("profitni_centar");
        String oznaka = oznake.get(x);

        listGrid.setDataSource(ChartsTransportModel.getInstance());
        Criteria criteria = new Criteria();
        criteria.addCriteria("oznaka",oznaka);
         criteria.addCriteria(((ChartToolStrip) ChartsTransportModel._selectedPokazatelj.getChartToolstrip()).getToolStripItemValuesAsCriteria());
         criteria.addCriteria(AS2RestJSONDataSource.REMOTE_METHOD,"listajOperativniTroskoviDetalji");
 		criteria.addCriteria(AS2RestJSONDataSource.REMOTE_OBJECT, KpiContext.SERVER_COMPONENT);
 		criteria.addCriteria(AS2RestJSONDataSource.TRANSFORM_TO, KpiContext.SERVER_TRANSFORMTO);
//        criteria.addCriteria("godina_pocetak",godina_pocetak);
//        criteria.addCriteria("godina_kraj", godina_kraj);
//        criteria.addCriteria("mjesec_kraj",mjesec_kraj);
//        criteria.addCriteria("profitni_centar",profitni_centar);
        listGrid.fetchData(criteria,new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if(dsResponse.getStatus()>=0)
					serverCallBack(dsResponse);
			}
		});
		return listGrid;
	}

	protected void serverCallBack(DSResponse dsResponse) {
		ListGridField[] lgridFields = null;
		RecordList recordList = dsResponse.getDataAsRecordList()/*.get(0).getAttributeAsRecordList("records")*/;
		int columnCount=0;
		System.out.println("START " + new Date());
		if(recordList==null){
			ChartsTransportModel.noData1();
			this.closeWindow(false);
		}else{
			for(int i=0;i<recordList.getLength();i++){
				Record record = recordList.get(i);/*.getAttributeAsRecord("record");*/
				if(i==0){
					lgridFields = new ListGridField[record.getAttributes().length];
					for (String attribute : record.getAttributes()) {
						if(!attribute.contains("$")){
							ListGridField field = new ListGridField();
							field.setType(ListGridFieldType.TEXT);
							field.setName(attribute);
							if(attribute.equals("dobavljac")){
								field.setTitle("Dobavljač");
							}else
								field.setTitle(attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length()));
							if(attribute.equalsIgnoreCase("Iznos") ){
							 	field.setType(ListGridFieldType.FLOAT);
							}else if(attribute.equalsIgnoreCase("Konto")){
								field.setType(ListGridFieldType.INTEGER);
							}else if(attribute.equalsIgnoreCase("Datum")){
								field.setType(ListGridFieldType.DATE);
							}else if(attribute.equalsIgnoreCase("Opis")){
								field.setWidth(100);
							}
							lgridFields[columnCount++] = field;
						}
					}
				}
//				record.setAttribute("iznos", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("iznos"))));
				record.setAttribute("iznos", KpiContext.formatNumberDefault(record.getAttribute("iznos")));
				record.setAttribute("datum", AS2ClientContext._defaultDateFormatter.format(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").parse(record.getAttribute("datum"))));
				recordList.set(i, record);
			}
			_listGrid.setFields(lgridFields);
			_listGrid.setData(recordList);
			_listGrid.updateStatusBar();
		}
	}
}
