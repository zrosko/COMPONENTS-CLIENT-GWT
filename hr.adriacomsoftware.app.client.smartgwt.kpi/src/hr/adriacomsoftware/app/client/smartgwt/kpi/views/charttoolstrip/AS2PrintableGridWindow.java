package hr.adriacomsoftware.app.client.smartgwt.kpi.views.charttoolstrip;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.models.ChartsTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;

public class AS2PrintableGridWindow extends AS2Window{

	private Record _value;
	private IButton _button_ispis;

	public AS2PrintableGridWindow(Record value) {
		super();
		this._value = value;
		createComponents();
	}
	@Override
	public void createComponents(){
		this.setShowResizeBar(false);
		this.setShowResizer(true);
		this.setIsModal(false);
//		this.setKeepInParentRect(true);
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
		this.setCanDragResize(true);
//		int width = 800;
//		int height = 600;
//		int userWidth = com.google.gwt.user.client.Window.getClientWidth() - 20;
//		this.setWidth(userWidth < width ? userWidth : width);
//
//		int userHeight = com.google.gwt.user.client.Window.getClientHeight() - 96;
//		this.setHeight(userHeight < height ? userHeight : height);
//		this.centerInPage();
		_listGrid = getListGrid();
		_buttons_layout = getButtonsLayout();
		windowLayout();
	}
	
	@Override
	public void windowLayout(){
		this.addItem(_listGrid);
		this.addItem(_listGrid.getStatusBar());
		this.addItem(_buttons_layout);
		this.show();
	}
	@Override
	public DataSource getModel() {
		return ChartsTransportModel.getInstance();
	}
	@Override
	public AS2ListGrid getListGrid() {
		AS2ListGrid listGrid = new AS2ListGrid(true,null);
//		listGrid.setID("printableGridWindow");
        listGrid.setAutoFetchData(false);
        AS2ListGridField naziv_sektora = new AS2ListGridField("naziv_sektora",AS2Field.TEXT,"Naziv sektora");
//        naziv_sektora.getField().setAutoFitWidth(true);
        AS2ListGridField datum = new AS2ListGridField("datum",AS2Field.DATE,"Datum od");
        AS2ListGridField stanje = new AS2ListGridField("stanje",AS2Field.AMOUNT,"Stanje");
        AS2ListGridField broj_partija = new AS2ListGridField("broj_partija",AS2Field.DATE,"Broj partija");
        AS2ListGridField datum2 = new AS2ListGridField("datum2",AS2Field.DATE,"Datum do");
        AS2ListGridField stanje2 = new AS2ListGridField("stanje2",AS2Field.AMOUNT,"Stanje");
        AS2ListGridField broj_partija2 = new AS2ListGridField("broj_partija2",AS2Field.DATE,"Broj partija");
        AS2ListGridField promjena = new AS2ListGridField("promjena",AS2Field.AMOUNT,"Promjena");
        AS2ListGridField indeks_promjene = new AS2ListGridField("indeks_promjene",AS2Field.AMOUNT,"Indeks promjene");
        //novo
        AS2ListGridField kamatni_prihod = new AS2ListGridField("kamatni_prihod",AS2Field.AMOUNT,"Kamatni prihod");
        AS2ListGridField kamatni_prihod2 = new AS2ListGridField("kamatni_prihod2",AS2Field.AMOUNT,"Kamatni prihod");
        AS2ListGridField kamatni_prihod_promjena = new AS2ListGridField("kamatni_prihod_promjena",AS2Field.AMOUNT,"Kamatni prihod promjena");
        AS2ListGridField kamatni_prihod_indeks = new AS2ListGridField("kamatni_prihod_indeks",AS2Field.AMOUNT,"Kamatni prihod indeks");
        //novo
        listGrid.setFields(naziv_sektora,datum,stanje,broj_partija,datum2,stanje2,broj_partija2,
        		promjena, indeks_promjene, kamatni_prihod,kamatni_prihod2,kamatni_prihod_promjena,kamatni_prihod_indeks );
        listGrid.setDataSource(getModel());
        listGrid.fetchData(getFetchCriteria(),serverCallback());
		return listGrid;
	}
	
	private DSCallback serverCallback() {
		DSCallback callback = new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if(dsResponse.getStatus()>=0){
//				ListGridField[] lgridFields = null;
				RecordList recordList = dsResponse.getDataAsRecordList();/*.get(0).getAttributeAsRecordList("records");*/
//				int columnCount=0;
				for(int i=0;i<recordList.getLength();i++){
					Record record = recordList.get(i);/*.getAttributeAsRecord("record");*/
					record.getAttributes();
//					if(i==0){
//						lgridFields = new ListGridField[record.getAttributes().length];
						//Imena stupaca
//						for (String attribute : record.getAttributes()) {
//								ListGridField field = new ListGridField();
//								field.setType(ListGridFieldType.TEXT);
//								field.setName(attribute);
//								field.setTitle((attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length())).replace("_"," "));
//								if(attribute.equalsIgnoreCase("stanje") ){
//								 	field.setType(ListGridFieldType.FLOAT);
//								}else if(attribute.equalsIgnoreCase("stanje2")){
//									field.setType(ListGridFieldType.FLOAT);
//								}else if(attribute.equalsIgnoreCase("promjena")){
//									field.setType(ListGridFieldType.FLOAT);
//								}else if(attribute.equalsIgnoreCase("datum")){
//									field.setType(ListGridFieldType.DATE);
//								}else if(attribute.equalsIgnoreCase("datum2")){
//									field.setType(ListGridFieldType.DATE);
//								}else if(attribute.equalsIgnoreCase("Opis")){
//									field.setWidth(100);
//								}
//								lgridFields[columnCount++] = field;
//							}
//					}
					//vrijednosti
					if(record.getAttributeAsObject("indeks_promjene")!=null){
						if(!record.getAttributeAsObject("indeks_promjene").equals(""))
							record.setAttribute("indeks_promjene", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("indeks_promjene")))+"%");
						if(!record.getAttributeAsObject("stanje").equals(""))
							record.setAttribute("stanje",AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("stanje"))));
						if(!record.getAttribute("stanje2").equals(""))
							record.setAttribute("stanje2", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("stanje2"))));
						if(!record.getAttribute("promjena").equals(""))
							record.setAttribute("promjena", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("promjena"))));
						if(!record.getAttributeAsObject("datum").equals(""))
							record.setAttribute("datum", AS2ClientContext._defaultDateFormatter.format(DateTimeFormat.getFormat("yyyy-MM-dd").parse(record.getAttribute("datum"))));
						if(!record.getAttributeAsObject("datum2").equals(""))
							record.setAttribute("datum2", AS2ClientContext._defaultDateFormatter.format(DateTimeFormat.getFormat("yyyy-MM-dd").parse(record.getAttribute("datum2"))));
						//novo kamatni_prihod,kamatni_prihod2,kamatni_prihod_promjena,kamatni_prihod_indeks 
						if(!record.getAttribute("kamatni_prihod").equals(""))
							record.setAttribute("kamatni_prihod", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("kamatni_prihod"))));
						if(!record.getAttribute("kamatni_prihod2").equals(""))
							record.setAttribute("kamatni_prihod2", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("kamatni_prihod2"))));
						if(!record.getAttribute("kamatni_prihod_promjena").equals(""))
							record.setAttribute("kamatni_prihod_promjena", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("kamatni_prihod_promjena"))));
						if(!record.getAttribute("kamatni_prihod_indeks").equals(""))
							record.setAttribute("kamatni_prihod_indeks", AS2ClientContext.nfGWT.format(new BigDecimal(record.getAttribute("kamatni_prihod_indeks")))+"%");
						//novo
						recordList.set(i, record);
					}else{
						recordList= new RecordList();
					}
					
				}
				_listGrid.setData(recordList);
				_listGrid.updateStatusBar();
//				_listGrid.setFields(lgridFields);
				
				}/*else{
					SC.say("nema podataka");
				}*/
			}
		};
		return callback;
	}

	private Criteria getFetchCriteria() {
		Criteria criteria = new Criteria();
		for(String attribute : _value.getAttributes()){
			criteria.addCriteria(attribute,_value.getAttribute(attribute));
		}
		criteria.addCriteria(AS2RestJSONDataSource.REMOTE_METHOD,"citajIzvjestaj");
		criteria.addCriteria(AS2RestJSONDataSource.REMOTE_OBJECT, KpiContext.SERVER_COMPONENT);
		criteria.addCriteria(AS2RestJSONDataSource.TRANSFORM_TO, KpiContext.SERVER_TRANSFORMTO);
		return criteria;
	}
	@Override
	protected void getFormButtons() {
		// define Buttons
		_button_ispis = new IButton("Ispis");
		_button_ispis.setWidth(75);
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
//				"toolbar/printpreview.png");
		_button_ispis.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Canvas.showPrintPreview(_listGrid);
			}
		});

		final IButton _button_excel = new IButton("Excel");
		_button_excel.setDisabled(true);
//		_button_excel.setIcon(AS2Resources.INSTANCE.excelx_icon().getSafeUri().asString());
//				"icons/s16/folder_document.png");
		_button_excel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				downloadFile();
			}
		});

        _button_izlaz = new IButton("Odustani");
        _button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
//        		"icons/s16/cancel.png");
        _button_izlaz.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               closeWindow(false);
            }
        });
        _buttons_layout.setMembers(_button_ispis, _button_excel,_button_izlaz);
    }
}
