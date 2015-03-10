package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AS2ChartsOnClickWindow extends AS2Window{

	protected SeriesClickEvent _clickedSeries;
	protected PointClickEvent _clickedPoint;
	protected ChartValueObject _chartVo;
	protected SeriesClickEvent _seriesClickEvent;

	public AS2ChartsOnClickWindow(SeriesClickEvent seriesClickEvent, ChartValueObject chartVo) {
		super();
		this._clickedSeries = seriesClickEvent;
		this._chartVo = chartVo;
		this._seriesClickEvent = seriesClickEvent;

		createComponents();
		windowLayout();
	}

	public AS2ChartsOnClickWindow(PointClickEvent pointClickEvent, ChartValueObject chartVo) {
		super();
		this._clickedPoint = pointClickEvent;
		this._chartVo = chartVo;
		createComponents();
		windowLayout();
	}

	public void createComponents(){
		this.setShowResizeBar(false);
		this.setIsModal(false);
		this.setKeepInParentRect(true);
		this.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Esc")) {
						closeWindow(false);
					}
				}

			}
		});
		this.setShowMaximizeButton(true);
		int width = 500;
		int height = 320;
		int userWidth = com.google.gwt.user.client.Window.getClientWidth() - 20;
		this.setWidth(userWidth < width ? userWidth : width);

		int userHeight = com.google.gwt.user.client.Window.getClientHeight() - 96;
		this.setHeight(userHeight < height ? userHeight : height);
		_listGrid = getListGrid();
		
	}

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
		listGrid.setID("chartsWindowListGrid");
        listGrid.setAutoFetchData(false);
//        listGrid.setAutoHeightListGrid();
        AS2ListGridField field1 = new AS2ListGridField("vrijednost",AS2Field.TEXT,"Vrijednost x osi");
        AS2ListGridField field2 = new AS2ListGridField(seriesName,AS2Field.AMOUNT, seriesName);
        if(!_chartVo.getYTitle().equals(""))
        	field2.setTitle(_chartVo.getYTitle()+": " +seriesName);
//        field2.setType(ListGridFieldType.FLOAT);
        listGrid.setFields(field1,field2);

        ListGridRecord rec = new ListGridRecord();
		rec.setAttribute("vrijednost", x);
//		rec.setAttribute(seriesName,AS2ClientContext.nfGWT.format(y));
		rec.setAttribute(seriesName,KpiContext.formatDoubleDefault(y));
		listGrid.setData(new Record[]{rec});
		return listGrid;
	}


	public void windowLayout(){
		this.addItem(_listGrid);
		this.addItem(_listGrid.getStatusBar());
		this.show();

	}
}
