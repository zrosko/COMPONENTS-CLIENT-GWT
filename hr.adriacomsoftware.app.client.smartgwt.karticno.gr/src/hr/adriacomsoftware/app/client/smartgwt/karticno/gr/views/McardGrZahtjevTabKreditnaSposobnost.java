package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevOcjenaConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevOcjenaModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabKreditnaSposobnost extends AS2View2 implements McardGrZahtjevOcjenaConstants, McardGrZahtjevConstants {

	public static McardGrZahtjevTabKreditnaSposobnost view;
	private McardGrZahtjevMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "kreditna_sposobnost";
	public static final String FAZA_ZAHTJEVA_NEXT = "preporuka";
	public static final String FAZA_ZAHTJEVA_PREVIOUS = "obrada";
	private HLayout _buttons_layout;
	private HLayout rollOverCanvas;
	private Record _record;
	private IButton _button_brisi_sve;
	private IButton _button_ispis;

	public McardGrZahtjevTabKreditnaSposobnost(McardGrZahtjevMWindow parent,Record record){
		_record = record;
		_parent = parent;
		createComponents();
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 3;
		McardGrZahtjevTabKreditnaSposobnost.view=this;
	}

	public void showWidgets() {
	}
	
	public void createComponents() {
		this.setWidth100();
		this.setHeight100();	
		if(!_record.getAttribute(MCARD_GR_ZAHTJEV__VRSTA_KARTICE).equalsIgnoreCase("REVOLVING")){
			Label label = new Label();  
			label.setHeight(30);  
			label.setPadding(10);  
			label.setAlign(Alignment.CENTER);  
			label.setValign(VerticalAlignment.CENTER);  
			label.setWrap(false);  
			label.setBackgroundColor("white");
			label.setShowEdges(true);  
			label.setContents("Izračun ocjena (skoring) se radi samo za REVOLVING kartice.<br>Nastavite na sljedeći korak!");  
			this.addMembers(label);
		}else{
			_dataSource = getModel();
			_buttons_layout = getFormButtons();
			_form = getDynamicForm();
			_listGrid = getListGrid();
			_listGrid.setCriteria(new Criteria(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)));
			if(_record.getAttribute(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO)){
				_form.setCanEdit(false);
				_listGrid.setCanEdit(false);
				_button_brisi_sve.setDisabled(true);
			}
			this.addMembers(_form,_listGrid, _listGrid.getStatusBar(), _buttons_layout);
		}
	}

	@Override
	public DataSource getModel() {
		return McardGrZahtjevOcjenaModel.getInstance();
	}

	@Override
	public DataSource getSifrarnikModel() {
		return null;//return PrnSifrarnikModel.getInstance();
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		AS2FormItem datum_izracunaj = new AS2FormItem("datum_izracunaj", AS2Field.DATE, "Novi izračun pokazatelja za klijenta");
		datum_izracunaj.setWidth(100);
		datum_izracunaj.getField().setDefaultValue(new Date());
		AS2FormItem button_izracunaj = new AS2FormItem("button_izracunaj", AS2Field.FORM_BUTTON, "Izračunaj");
		button_izracunaj.setStartRow(false);
		button_izracunaj.setWidth(100);
		button_izracunaj.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = form.getValuesAsCriteria();
				for(String attribute: _record.getAttributes()){
					criteria.addCriteria(attribute,_record.getAttributeAsObject(attribute));
				}
				_listGrid.fetchData(criteria, null, new DSRequest(DSOperationType.CUSTOM,"pripremaOcjena"));
			}
		});
		form.setDataSource(getModel());
		form.setFields(datum_izracunaj,button_izracunaj);
		form.focusInItem(button_izracunaj);
		return form;
	}


	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true) {
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				AS2RolloverButtonFactory._rollOverRecord = this.getRecord(rowNum);
				if(!_record.getAttribute(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO) && AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject("groupValue")==null){
					if (rollOverCanvas == null) {
						prepareRollOver(this);
					}
					return rollOverCanvas;
				}else{
					HLayout layout = new HLayout();
					layout.setWidth(1);
					layout.setHeight(1);
					return layout;
				}
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setCellHeight(56);
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		// summary
		listGrid.setShowGridSummary(true);
		// inline editting
		listGrid.setCanEdit(true);
		listGrid.setModalEditing(true);
		listGrid.setListEndEditAction(RowEndEditAction.STOP);
		listGrid.setSaveByCell(false);
		
		AS2ListGridField naziv = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__NAZIV);
		naziv.getField().setSummaryFunction(new SummaryFunction() {
			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				return new String("Ukupno");
			}
		});
		naziv.getField().setCanEdit(false);
		AS2ListGridField ukupno_ocjena = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_OCJENA,AS2Field.AMOUNT_4);
		ukupno_ocjena.getField().setHidden(true);
		AS2ListGridField ocjena = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__OCJENA, AS2Field.AMOUNT);
		ocjena.getField().setSummaryFunction(new SummaryFunction() {
			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				if(listGrid.getDataAsRecordList().get(0)!=null)
					return listGrid.getDataAsRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_OCJENA);
				return 0;
			}
		});
		AS2ListGridField ukupno_ponder = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDER,AS2Field.AMOUNT);
		ukupno_ponder.getField().setHidden(true);
		AS2ListGridField ponder = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__PONDER);
		ponder.getField().setCanEdit(false);
		ponder.getField().setSummaryFunction(new SummaryFunction() {
			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				if(listGrid.getDataAsRecordList().get(0)!=null)
					return listGrid.getDataAsRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDER);
				return 0;
			}
		});
		AS2ListGridField ukupno_ponderirana_ocjena = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDERIRANA_OCJENA);
		ukupno_ponderirana_ocjena.getField().setHidden(true);
		AS2ListGridField ponderirana_ocjena = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__PONDERIRANA_OCJENA,AS2Field.AMOUNT_4);
		ponderirana_ocjena.getField().setCanEdit(false);
		ponderirana_ocjena.getField().setSummaryFunction(new SummaryFunction() {
			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				if(listGrid.getDataAsRecordList().get(0)!=null)
					return listGrid.getDataAsRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDERIRANA_OCJENA);
				return 0;
			}
		});
		AS2ListGridField vrijednost_pokazatelja = new AS2ListGridField(MCARD_GR_ZAHTJEV_OCJENA__VRIJEDNOST_POKAZATELJA,AS2Field.AMOUNT_4);
		vrijednost_pokazatelja.getField().setShowGridSummary(false);
		vrijednost_pokazatelja.getField().setCanEdit(false);
		listGrid.addEditCompleteHandler(new EditCompleteHandler() {
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				listGrid.invalidateCache();				
			}
		});
		listGrid.setFields(naziv,ukupno_ocjena,ocjena,ukupno_ponder,ponder,ukupno_ponderirana_ocjena,ponderirana_ocjena,vrijednost_pokazatelja);
		return listGrid;
	}

	/*********** ROLLOVER ************/
	private void prepareRollOver(ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
	}
	
	protected HLayout getFormButtons() {
		HLayout buttons_layout = new HLayout(5);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
		_button_brisi_sve = new IButton("Briši sve");
		_button_brisi_sve.setIcon(AS2Resources.DELETE_PATH);
		_button_brisi_sve.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_listGrid.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),null,new DSRequest(DSOperationType.CUSTOM,"brisiMcardGrZahtjevSveOcjene"));
			}	
		});
		_button_ispis = new IButton("Ispis");
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_ispis.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(McardGrZahtjevTabKreditnaSposobnost.this, McardGrZahtjevOcjenaModel.REPORT_SERVER, "izvjestajPokazateljiSamostalno");
				report.setParameter(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA, _record.getAttribute(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA));
				report.getJasperReport("pokazatelji_samostalno_cn");
				//old
//				AS2Report report = new AS2Report("pokazatelji_samostalno_cn");
//				report.setComponent("hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer");
//				report.setService("procitajSveMcardGrZahtjevOcjena");
//				report.setValueObject("hr.adriacomsoftware.app.common.karticno.gr.dto.McardGrZahtjevOcjenaVo");
//				report.setParameter(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA));
//				report.printReport();
			}
		});
		buttons_layout.setMembers(_button_brisi_sve,_button_ispis);
		return buttons_layout;
	}
//	protected boolean saveDataNoviKorak(String akcija) {
//    	if(akcija.equals("previous")){
//    		saveData(FAZA_ZAHTJEVA_PREVIOUS);
//    		return true;
//    	}else if(akcija.equals("next")){
//    		saveData(FAZA_ZAHTJEVA_NEXT);
//    		return true;
//    	}else 
//    		return false;
//    }
	
	protected void saveData(String faza, final String akcija) {
		AS2DynamicForm tempForm = new AS2DynamicForm();
		tempForm.setSaveOnEnter(false);
		tempForm.setDataSource(_parent._dataSource);
		tempForm.setFields(new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA),new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA));
		Record data = new Record();
		data.setAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,  _record.getAttributeAsObject(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
		data.setAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA,  faza);
		tempForm.editRecord(data);
		tempForm.saveData(new DSCallback() {
			public void execute(DSResponse dsResponse, Object rawData,DSRequest request) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				_record.setAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA, server.getAttributeAsObject(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA));
				_parent._refresh=true;
				_parent.refreshWindow(_record);
				if(akcija.equals("next") || akcija.equals("previous"))
					_parent.tabSaveCallback();
			}
		});
	}
	@Override
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
