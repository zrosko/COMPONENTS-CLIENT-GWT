package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaGoConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaGoModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * @author msekso, astrikoman
 *
 */ 
public class EvidencijaGoView extends AS2View2 implements  EvidencijaGoConstants {

	private HLayout rollOverCanvas;
	//private ToolStrip _buttons_layout;
	private ToolStripButton _button_ispis;
	private String ulogirani_korisnik;
	private String uloga;
	
	public EvidencijaGoView(String name) {
		super(name);
	}
	

	@Override
	public void customWindowPreferences() {
		this.setWidth100();
		this.setHeight100();
		number_of_columns = 5;
		use_form=true;
		use_listgrid=true;
		ulogirani_korisnik = AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME);
		uloga =  AS2ClientContext.getSessionValue(AS2ClientContext.ROLE_ID);
	}

	@Override
	public void windowLayout() {
		_toolStrip=getToolStrip();
		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
	}
	
	@Override
	public DataSource getModel() {
		return EvidencijaGoModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
	}
	
	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		final AS2FormItem godina = new AS2FormItem(EVIDENCIJAGO__GODINA);
		godina.setWidth(60);
		godina.getField().setAttribute("length", "4");
//		godina.getField().setDefaultValue(DateTimeFormat.getFormat(PredefinedFormat.YEAR).format(new Date()).getYear()+1900);
		godina.getField().setDefaultValue(Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date())));
		final AS2FormItem button_prikazi = new AS2FormItem("button_prikazi", AS2Field.FORM_BUTTON, "Prikaži");
		button_prikazi.setStartRow(false);
		button_prikazi.setWidth(100);
		AS2FormItem id_spica_oj = new AS2FormItem(EVIDENCIJAGO__ID_SPICA_OJ, AS2Field.COMBO, "Odjel");
		if(AS2GwtDesktop.getCacheData(getSifrarnikModel(),EVIDENCIJAGO__ID_SPICA_OJ+"__"+ulogirani_korisnik)!=null && !uloga.equals("4"))
			id_spica_oj.setDropDownList(EVIDENCIJAGO__ID_SPICA_OJ+"__"+ulogirani_korisnik);
//		id_spica_oj.getField().setColSpan(3);
		id_spica_oj.setWidth(230);
		id_spica_oj.setDefaultToFirstOption(true);
		
		//handlers
		button_prikazi.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = _form.getValuesAsCriteria();
				_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.FETCH));
			}
		});
		form.setDataSource(getModel());
		form.setFields(godina, id_spica_oj, button_prikazi);
		form.focusInItem(button_prikazi);
		form.setDropDownModel(getSifrarnikModel(), id_spica_oj);
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
//			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
//				if (getFieldName(colNum).equals(EVIDMJ__UKUPNI_BROJ_SATI) && record.getAttribute(EVIDMJ__UKUPNI_BROJ_SATI)!=null) {  
//						return "color:#15428B;";  
//				}else if (getFieldName(colNum).equals("napomena_spica") && record.getAttribute("napomena_spica")!=null) {  
//					if (!record.getAttribute("napomena_spica").startsWith("0")||  record.getAttribute("napomena_spica").contains("Trajanje 1")) {  
//						return "color:red;";  
//					}
//				}
//				return super.getCellCSSText(record, rowNum, colNum);  
//			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setAutoFetchData(true);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
//		listGrid.setCellHeight(40);
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		listGrid.setBaseStyle("myAltRecGridCell");  
		
		// filtering
//		listGrid.setShowFilterEditor(true);
//		listGrid.setFilterOnKeypress(true);
		
		// inline editting
		listGrid.setCanEdit(false);
		
		//listGrid.setAutoFetchData(false);
		Criteria criteria = _form.getValuesAsCriteria();
        listGrid.setCriteria(criteria);

        AS2ListGridField id_godisnji = new AS2ListGridField(EVIDENCIJAGO__ID_GODISNJI);
        AS2ListGridField radnik_id = new AS2ListGridField(EVIDENCIJAGO__RADNIK_ID,AS2Field.COMBO);
		AS2ListGridField prezime_ime = new AS2ListGridField(EVIDENCIJAGO__PREZIME_IME,"40%");
		prezime_ime.getField().setCanEdit(false);
		AS2ListGridField ostatak_prethodna_godina = new AS2ListGridField(EVIDENCIJAGO__OSTATAK_PRETHODNA_GODINA,"10%");
		AS2ListGridField pravo_tekuca_godina = new AS2ListGridField(EVIDENCIJAGO__PRAVO_TEKUCA_GODINA,"10%");
		AS2ListGridField ukupno_koristeno_u_godini = new AS2ListGridField(EVIDENCIJAGO__UKUPNO_KORISTENO_U_GODINI,"10%");
		ukupno_koristeno_u_godini.getField().setCanEdit(false);
		AS2ListGridField potroseno_starog = new AS2ListGridField(EVIDENCIJAGO__POTROSENO_STAROG,"10%");
		potroseno_starog.getField().setCanEdit(false);
		AS2ListGridField potroseno_novog = new AS2ListGridField(EVIDENCIJAGO__POTROSENO_NOVOG,"10%");
		potroseno_novog.getField().setCanEdit(false);
		AS2ListGridField ostatak_stari = new AS2ListGridField(EVIDENCIJAGO__OSTATAK_STARI,"10%");
		ostatak_stari.getField().setCanEdit(false);
		AS2ListGridField ostatak_novi = new AS2ListGridField(EVIDENCIJAGO__OSTATAK_NOVI,"10%");
		ostatak_novi.getField().setCanEdit(false);
		AS2ListGridField stari_potroseno_ostatak = new AS2ListGridField(EVIDENCIJAGO__STARI_POTROSENO_OSTATAK);
		AS2ListGridField novi_potroseno_ostatak = new AS2ListGridField(EVIDENCIJAGO__NOVI_POTROSENO_OSTATAK);
		
		AS2ListGridField godina = new AS2ListGridField(EVIDENCIJAGO__GODINA);
		AS2ListGridField id_spica_oj = new AS2ListGridField(EVIDENCIJAGO__ID_SPICA_OJ,AS2Field.COMBO);
		
		listGrid.setFields(id_godisnji, radnik_id, prezime_ime,
				ostatak_prethodna_godina, pravo_tekuca_godina,
				ukupno_koristeno_u_godini, potroseno_starog, potroseno_novog,
				ostatak_stari, ostatak_novi, stari_potroseno_ostatak,
				novi_potroseno_ostatak, godina, id_spica_oj);
		listGrid.setDropDownModel(getSifrarnikModel(),radnik_id,id_spica_oj);
		listGrid.setAlignFields(Alignment.CENTER, ostatak_prethodna_godina,
				pravo_tekuca_godina, ukupno_koristeno_u_godini,
				potroseno_starog, potroseno_novog, ostatak_stari, ostatak_novi);
		
		listGrid.setHiddenFields(true, id_godisnji, radnik_id, id_spica_oj,
				godina, stari_potroseno_ostatak, novi_potroseno_ostatak);
	
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				moreDetails();
			}
		});

		
		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	private void prepareRollOver(final ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverCustomButton(
				AS2RolloverButtonFactory.CUSTOM_BUTTON, listGrid, AS2Resources.MORE_DETAILS_PATH,"Detalji",
				new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						moreDetails();
					}
				}));
		if(uloga.equals("1")||uloga.equals("4")){ //admin i kadrovska
			rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
					AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
		}
	}
	
	private void moreDetails() {
		Record new_record = _listGrid.getRecord(_listGrid.getDataAsRecordList().indexOf(AS2RolloverButtonFactory._rollOverRecord));
		new EvidencijaGoMWindow(EvidencijaGoView.this, new_record);//TODO
	}
	@Override
	protected ToolStrip getToolStrip() {
		ToolStrip buttons_layout = new ToolStrip();
//		buttons_layout.setAlign(Alignment.RIGHT);
		_button_ispis = new ToolStripButton("Ispis");
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_ispis.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout print = new VLayout();
				print.addMember(_listGrid);
				Canvas.showPrintPreview(print);
				EvidencijaGoView.this.setMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());//TODO ?
			}
		});

		buttons_layout.setMembers(new LayoutSpacer(),_button_ispis);
		return buttons_layout;
	}


	@Override
	protected String getRecordIdName() {
		return EVIDENCIJAGO__RADNIK_ID;
	}
	
}
