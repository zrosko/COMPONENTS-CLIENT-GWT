package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaBuduceRazdobljeModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaBuduceRazdobljeMWindow extends AS2Window implements EvidencijaDolazakaConstants{
	
	private Record _record;
	private HLayout rollOverCanvas;
	
	public EvidencijaBuduceRazdobljeMWindow(AS2View2 parent, Record record) {
		_parent_view = parent;
		_record = record;
		_dataSource = _parent_view.getModel();
		createComponents();
	}
	
	public DataSource getModel(){
		return EvidencijaBuduceRazdobljeModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
	}
	
	public void createComponents() {
		_buttons_layout = getButtonsLayout();
		_form=getDynamicForm();
		_listGrid=getListGrid();
		windowLayout();
	}
	
	@Override
	public void windowLayout() {
		this.setTitle("Unos kategorije prisutnosti budućeg razdoblja");
		this.setWidth("80%");
		this.setHeight("80%");
		this.addItem(_form);
		this.addItem(_listGrid);
		this.show();
	}
	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(5);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		form.setPadding(10);
		Date today = new Date(System.currentTimeMillis());
		AS2FormItem radnik_id = new AS2FormItem(EVIDENCIJA__RADNIK_ID,AS2Field.COMBO/*,"Djelatnik"*/);
		radnik_id.getField().setCanEdit(false);
		radnik_id.getField().setColSpan(4);
		radnik_id.setWidth(340);
		radnik_id.setDefaultValue(_record.getAttribute(EVIDENCIJA__RADNIK_ID));
		AS2FormItem datum_od = new AS2FormItem(EVIDENCIJA__DATUM_OD,AS2Field.DATE,"Razdoblje od");
		datum_od.setWidth(125);
		datum_od.getField().setDefaultValue(_record.getAttributeAsDate(EVIDENCIJA__DATUM_OD));
		datum_od.getField().setDefaultValue(today);
		datum_od.getField().setColSpan(1);
		AS2FormItem datum_do = new AS2FormItem(EVIDENCIJA__DATUM_DO,AS2Field.DATE,"Razdoblje do");
		datum_do.setWidth(125);
		datum_do.getField().setDefaultValue(_record.getAttributeAsDate(EVIDENCIJA__DATUM_DO));
		datum_do.getField().setDefaultValue(today);
		datum_do.getField().setColSpan(1);
		AS2FormItem napomena = new AS2FormItem("napomena",AS2Field.FORM_LABEL,"U obzir se uzima najranije današnji dan");
		napomena.setTextBoxStyle("formHint");
		AS2FormItem element_obracuna_id = new AS2FormItem(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID,AS2Field.COMBO);
		element_obracuna_id.getField().setColSpan(3);
		element_obracuna_id.setWidth(340);
		AS2FormItem button_dodaj = new AS2FormItem("button_dodaj", AS2Field.FORM_BUTTON, "Dodaj");
		button_dodaj.setStartRow(false);
		button_dodaj.setWidth(100);
		
		//handlers
		button_dodaj.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(_form.validate()){
					DataSource dataSource = getModel();
					Criteria criteria = _form.getValuesAsCriteria();
					dataSource.fetchData(criteria,new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							Criteria criteria = _form.getValuesAsCriteria();
							_listGrid.fetchData(criteria);
//							if(dsResponse.getStatus()>=0)
//								SC.say(dsResponse.getDataAsRecordList().get(0).getAttribute("napomena"));
						}
					},new DSRequest(DSOperationType.CUSTOM,"dodajViseEvidencija"));
				}
			}
		});
		
		
		form.setDataSource(getModel());
		form.setFields(radnik_id, datum_od, datum_do, napomena, element_obracuna_id,button_dodaj);
		form.setRequiredFields(datum_od, datum_do, element_obracuna_id);
		form.setDropDownModel(getSifrarnikModel(),radnik_id,element_obracuna_id);
		return form;
	}
	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				AS2RolloverButtonFactory._rollOverRecord = this.getRecord(rowNum);
				if(AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject("groupValue")==null){
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
		listGrid.setAutoFetchData(true);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		listGrid.setBaseStyle("myAltRecGridCell");  
		
		// inline editting
		listGrid.setCanEdit(true);
		
		//listGrid.setAutoFetchData(false);
		Criteria criteria = _form.getValuesAsCriteria();
        listGrid.setCriteria(criteria);

        AS2ListGridField id_dnevne_evidencije = new AS2ListGridField(EVIDENCIJA__ID_DNEVNE_EVIDENCIJE);
		id_dnevne_evidencije.getField().setHidden(true);
		AS2ListGridField element_obracuna_id = new AS2ListGridField(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID,AS2Field.COMBO);
//		element_obracuna_id.getField().setCanEdit(true);
		AS2ListGridField datum = new AS2ListGridField(EVIDENCIJA__DATUM,"20%");
		datum.getField().setCanEdit(false);
		datum.getField().setAlign(Alignment.CENTER);
		AS2ListGridField korisnik = new AS2ListGridField(EVIDENCIJA__KORISNIK,"30%");
		korisnik.getField().setCanEdit(false);
		
		listGrid.setFields(id_dnevne_evidencije, element_obracuna_id, datum,
				korisnik);
		listGrid.setDropDownModel(getSifrarnikModel(),element_obracuna_id);
			
//		//handlers
//		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
//			@Override
//			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
//				editRecord();
//			}
//		});

		
		return listGrid;
	}
	/*********** ROLLOVER ************/
	private void prepareRollOver(final ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
//		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
//				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null));
	}

//	@Override
//	protected HLayout getButtonsLayout() {
//		HLayout buttons_layout = new HLayout();
//		buttons_layout.setAlign(Alignment.RIGHT);
//		buttons_layout.setPadding(10);
//		_button_dodaj = new IButton("Dodaj");
//		_button_dodaj.setIcon(AS2Resources.ADD_FILES_PATH);
//		_button_dodaj.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
//			@Override
//			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				if(_form.validate()){
//					DataSource dataSource = getModel();
//					Criteria criteria = _form.getValuesAsCriteria();
//					dataSource.fetchData(criteria,new DSCallback() {
//						@Override
//						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//							Criteria criteria = _form.getValuesAsCriteria();
//							_listGrid.fetchData(criteria);
////							if(dsResponse.getStatus()>=0)
////								SC.say(dsResponse.getDataAsRecordList().get(0).getAttribute("napomena"));
//						}
//					},new DSRequest(DSOperationType.CUSTOM,"dodajViseEvidencija"));
//					
//					
//					
////					Record form = _form.getValuesAsRecord();
////					ListGridRecord[] records = _parent_view._listGrid.getRecords();
////					int rowNum = 0;
////				    for(ListGridRecord record:records){
////				    	if(record.getAttributeAsObject(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID) != null &&
////				    	   record.getAttributeAsObject(EVIDENCIJA__DATUM) != null &&
////				    	   form.getAttributeAsObject(EVIDENCIJA__DATUM_OD) != null &&
////				    	   form.getAttributeAsObject(EVIDENCIJA__DATUM_DO) != null &&
////				    	   record.getAttributeAsString(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID).equals(form.getAttributeAsString(EVIDENCIJA__STARA_SIFRA)) &&
////				    	   checkIsDateInRange(record.getAttributeAsDate(EVIDENCIJA__DATUM),form.getAttributeAsDate(EVIDENCIJA__DATUM_OD),form.getAttributeAsDate(EVIDENCIJA__DATUM_DO))==true
////				    	   ){
////				    		_parent_view._listGrid.setEditValue(rowNum, 2, form.getAttributeAsString(EVIDENCIJA__NOVA_SIFRA));
////				    	}
////				    	rowNum++;
////					
////				    }
//				}
//			}
//		});
//
//		buttons_layout.setMembers(_button_dodaj);
//		return buttons_layout;
//	}
	@Override
	public String getWindowFormTitle() {
		String contents ="Izmjena kategorije za razdoblje";
		return contents;
	}
	
	
	//TODO - prebaciti u infrastrukturu
	public Boolean checkIsDateInRange(Date date, Date date_from, Date date_to) {
		if (date.equals(date_from) || date.equals(date_to)) {
			return true;
		} else if (date.after(date_from) && date.before(date_to)) {
			return true;
		} else
			return false;
	}
	
}

