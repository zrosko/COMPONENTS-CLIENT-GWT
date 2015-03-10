package hr.adriacomsoftware.app.client.smartgwt.obrasci.views;
//package hr.adriacomsoftware.app.client.smartgwt.obrasci.client.views;
//
//import hr.adriacomsoftware.app.client.smartgwt.kpi.client.BSContext;
//import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
//import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
//
//import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
//import org.moxieapps.gwt.highcharts.client.events.SeriesClickEvent;
//
//import com.smartgwt.client.data.Record;
//import com.smartgwt.client.types.ListGridFieldType;
//import com.smartgwt.client.widgets.events.KeyPressEvent;
//import com.smartgwt.client.widgets.events.KeyPressHandler;
//import com.smartgwt.client.widgets.grid.ListGridField;
//import com.smartgwt.client.widgets.grid.ListGridRecord;
//
//public class ObrasciParametersWindow extends AS2Window{
//
//	protected SeriesClickEvent _clickedSeries;
//	protected PointClickEvent _clickedPoint;
//	protected ChartValueObject _chartVo;
//	protected SeriesClickEvent _seriesClickEvent;
//
//	public ObrasciParametersWindow(SeriesClickEvent seriesClickEvent, ChartValueObject chartVo) {
//		super();
//		this._clickedSeries = seriesClickEvent;
//		this._chartVo = chartVo;
//		this._seriesClickEvent = seriesClickEvent;
//		createComponents();
//		windowLayout();
//	}
//
//	public ObrasciParametersWindow(PointClickEvent pointClickEvent, ChartValueObject chartVo) {
//		super();
//		this._clickedPoint = pointClickEvent;
//		this._chartVo = chartVo;
//		createComponents();
//		windowLayout();
//	}
//
//	public void createComponents(){
//		this.setShowResizeBar(false);
//		this.setIsModal(false);
//		this.setKeepInParentRect(true);
//		this.addKeyPressHandler(new KeyPressHandler() {
//
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if (event.getKeyName() != null) {
//					if (event.getKeyName().equals("Esc")) {
//						closeWindow(false);
//					}
//				}
//
//			}
//		});
//		this.setShowMaximizeButton(true);
//		int width = 500;
//		int height = 320;
//		int userWidth = com.google.gwt.user.client.Window.getClientWidth() - 20;
//		this.setWidth(userWidth < width ? userWidth : width);
//
//		int userHeight = com.google.gwt.user.client.Window.getClientHeight() - 96;
//		this.setHeight(userHeight < height ? userHeight : height);
//		_listGrid = getListGrid();
//	}
//
//	protected AS2ListGrid getListGrid() {
//		String seriesName = "";
//		int xPosition = 0;
//		double y = 0;
//		if(_clickedSeries!=null){
//			seriesName = _clickedSeries.getSeriesName();
//			xPosition= _clickedSeries.getNearestPoint().getX().intValue();
//			y = _clickedSeries.getNearestPoint().getY().doubleValue();
//		}else{
//			seriesName = _clickedPoint.getSeriesName();
//			xPosition= _clickedPoint.getPoint().getX().intValue();
//			y = _clickedPoint.getPoint().getY().doubleValue();
//		}
//		String[] xCategories = 	_chartVo.getXCategories();
//		String x = xCategories[xPosition];
//		this.setTitle("Detalji: "+seriesName);
//
//		AS2ListGrid listGrid = new AS2ListGrid(true,null);
//		listGrid.setID("chartsWindowListGrid");
//        listGrid.setAutoFetchData(false);
//        ListGridField field1 = new ListGridField("vrijednost","Vrijednost x osi");
//        ListGridField field2 = new ListGridField(seriesName, seriesName);
//        if(!_chartVo.getYTitle().equals(""))
//        	field2.setTitle(_chartVo.getYTitle()+": " +seriesName);
//        field2.setType(ListGridFieldType.FLOAT);
//        listGrid.setFields(field1,field2);
//
//        ListGridRecord rec = new ListGridRecord();
//		rec.setAttribute("vrijednost", x);
//		rec.setAttribute(seriesName,BSContext.nfGWT.format(y));
//		listGrid.setData(new Record[]{rec});
//		return listGrid;
//	}
//
//
//	protected void windowLayout(){
////		this.addItem(_window_form_title);
//		this.addItem(_listGrid);
//		this.addItem(_listGrid.getStatusBar());
//		this.show();
//
//	}
//
////	public String getWindowFormTitle(){
////		return "<b style=\"color:black;font-size:10pt;\">Obrada / uvjeti za zahtjev broj: "
//////				+ _listGrid.getSelectedRecord().getAttribute("broj_zahtjeva")
////				+ "aaaaaaaaaaaaaaaaaaaa"
////				+ "</b></br>";
////	}
//
//
//}
