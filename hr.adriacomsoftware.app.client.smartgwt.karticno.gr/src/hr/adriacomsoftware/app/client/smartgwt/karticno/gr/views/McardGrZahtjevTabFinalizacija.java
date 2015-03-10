package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabFinalizacija extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabFinalizacija view;
	private McardGrZahtjevMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "finalizacija";
	public static final String FAZA_ZAHTJEVA_NEXT = "zavrseno";
	public static final String FAZA_ZAHTJEVA_PREVIOUS = "odluka";
	Record _record;
    boolean refresh=false;
    
    AS2FormItem broj_zahtjeva;
    AS2FormItem section_odluka;

    public DataSource _dataSource;
	private IButton _button_spremi;
	private HLayout _buttons_layout;

	public McardGrZahtjevTabFinalizacija(McardGrZahtjevMWindow parent,Record record) {
		_record = record;
		_parent = parent;
		_parent.getButtonNastavak().setDisabled(true);
		createComponents();
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 2;
		McardGrZahtjevTabFinalizacija.view=this;
	}
	
	public void showWidgets() {
	}
	
	@Override
	public DataSource getModel() {
		return McardGrZahtjevModel.getInstance();
	}

	@Override
	public DataSource getSifrarnikModel() {
		return McardGrZahtjevSifrarnikModel.getInstance();
	}

	public void createComponents() {
		_dataSource = getModel();
		_buttons_layout = getFormButtons();
		_form = getDynamicForm();
		_form.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				_record = dsResponse.getDataAsRecordList().get(0);
				_form.editRecord(_record);
				if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO))
					_form.setCanEdit(false);
			}
		},new DSRequest(DSOperationType.CUSTOM,"procitajMcardGrZahtjev"));
		this.setWidth100();
		this.setHeight100();
		this.setStyleName("");
		this.addMembers(_form, _buttons_layout);
	}

	@Override
	public AS2ListGrid getListGrid() {
		return null;
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setColWidths("20%", "80%");
		form.setWrapItemTitles(true);
		form.setStyleNameGray();
		form.setSaveOnEnter(false);
		form.addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				if(_button_spremi.getDisabled())
					_button_spremi.setDisabled(false);		
			}
		});
		broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
	    AS2FormItem status_odobrenja = new  AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,AS2Field.FORM_RADIOGROUP,"Odluka");
	    //status_odobrenja.setValueMap(AS2Field.getComboData(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA));
        status_odobrenja.getField().setDisabled(true);
        AS2FormItem osoba_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA);
        osoba_odobravanja.setWidth(400);
        osoba_odobravanja.setDropDownList("svi_korisnici");
        osoba_odobravanja.getField().setDisabled(true);
        AS2FormItem datum_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA);
        datum_odobravanja.getField().setDisabled(true);
        datum_odobravanja.setWidth(150);
        AS2FormItem odobreni_iznos = new AS2FormItem(MCARD_GR_ZAHTJEV__ODOBRENI_IZNOS);
        odobreni_iznos.getField().setDisabled(true);
        odobreni_iznos.setWidth(150);
        AS2FormItem komentar =  new AS2FormItem(MCARD_GR_ZAHTJEV__KOMENTAR);
        komentar.getField().setRowSpan(3);
        komentar.getField().setDisabled(true);
        AS2FormItem broj_partije_mcard = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD);
        broj_partije_mcard.setWidth(150);
//        broj_partije_mcard.getField().setAlign(Alignment.LEFT);
        AS2FormItem broj_kartice = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_KARTICE);
        broj_kartice.setWidth(150);
        AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
        section_odluka = new AS2FormItem("section_odluka",AS2Field.FORM_SECTION);
        section_odluka.setDefaultValue("Finalizacija");
        section_odluka.setSectionItems(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,
        		MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA,MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA,
        		MCARD_GR_ZAHTJEV__ODOBRENI_IZNOS,MCARD_GR_ZAHTJEV__KOMENTAR,
        		MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD,MCARD_GR_ZAHTJEV__BROJ_KARTICE);
        AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
        form.setDataSource(_dataSource);
        form.setFields(broj_zahtjeva,section_odluka,status_odobrenja,
        		odobreni_iznos,osoba_odobravanja,datum_odobravanja,komentar,
        		broj_partije_mcard,broj_kartice,faza_zahtjeva,status_zahtjeva);
        form.focusInItem(broj_partije_mcard);
        form.setRequiredFields(broj_partije_mcard,broj_kartice);
        form.setDropDownModel(getSifrarnikModel(), status_odobrenja);
        return form;
	}
	protected AS2DynamicForm getForm(){
		return _form;
	}
    public HLayout getFormButtons() {
    	HLayout buttons_layout = new HLayout(5);
    	buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
        _button_spremi = new IButton("Spremi");
        _button_spremi.setIcon(AS2Resources.SAVE_PATH);
    	_button_spremi.setDisabled(true);		
        _button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveData("save");
			}
        });
        buttons_layout.setMembers(_button_spremi);
        return buttons_layout;
    }
    
//    protected boolean saveDataNoviKorak(String akcija) {
//    	if(akcija.equals("previous")){
//    		_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_PREVIOUS);
//    		saveData();
//    		return true;
//    	}else if(akcija.equals("end") && _form.validate()){
//    		_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO);
//    		saveData();
//    		return true;
//    	}
//    	else 
//    		return false;
//    }

	protected void saveData(final String akcija) {
		_form.saveData(new DSCallback() {
			public void execute(DSResponse dsResponse, Object rawData,DSRequest request) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				for(String attribute: server.getAttributes()){
					_record.setAttribute(attribute, server.getAttributeAsObject(attribute));
				}
				_parent._refresh=true;
				_parent.refreshWindow(_record);
				if(akcija.equals("next") || akcija.equals("previous"))
					_parent.tabSaveCallback();
				else if(akcija.equals("end")){
					_parent.endCallback();
				}
				_button_spremi.setDisabled(true);
			}
		});
	}

//	    public String getWindowFormTitle(){
//	        return "<b style=\"color:black;font-size:10pt;\">Obrada / uvjeti za zahtjev broj: "
//	                + _record.getAttributeAsString("broj_zahtjeva")
//	                + "</b></br>"
//	                + _record.getAttributeAsString("ime_prezime");
//	    }
	@Override
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
