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

import java.util.Date;

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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabOdlukaDodatni extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabOdlukaDodatni view;
	private McardGrZahtjevDodatniMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "odluka";
	public static final String FAZA_ZAHTJEVA_NEXT = "finalizacija";
	public static final String FAZA_ZAHTJEVA_PREVIOUS = "priprema";
	Record _record;
    boolean refresh=false;
    
    AS2FormItem broj_zahtjeva;
    AS2FormItem section_odluka;

    public DataSource _dataSource;
	private IButton _button_spremi;
	private HLayout _buttons_layout;

	public McardGrZahtjevTabOdlukaDodatni(McardGrZahtjevDodatniMWindow parent,Record record) {
		_record = record;
		_parent = parent;
		createComponents();
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 2;
		McardGrZahtjevTabOdlukaDodatni.view=this;
	}
	
	public void showWidgets() {}
	
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
				statusOdobrenjaChanged(_record.getAttribute(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA));
				_form.editRecord(_record);
				if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevDodatniMWindow.STATUS_ZAHTJEVA_ZATVORENO))
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
		AS2FormItem preporuka =  new AS2FormItem(MCARD_GR_ZAHTJEV__PREPORUKA);
        preporuka.getField().setRowSpan(3);
        preporuka.getField().setDisabled(true);
        AS2FormItem status_odobrenja = new  AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,AS2Field.FORM_RADIOGROUP,"Odluka");
//        status_odobrenja.setValueMap(AS2Field.getComboData(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA));
        status_odobrenja.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				statusOdobrenjaChanged(event.getItem().getValue().toString());
			}
		});
        AS2FormItem osoba_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA);
        osoba_odobravanja.setDropDownList("svi_korisnici");
        osoba_odobravanja.setWidth(400);
        AS2FormItem datum_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA);
        datum_odobravanja.setWidth(150);
        datum_odobravanja.getField().setDefaultValue(new Date());
        AS2FormItem komentar =  new AS2FormItem(MCARD_GR_ZAHTJEV__KOMENTAR);
        komentar.getField().setRowSpan(3);
        section_odluka = new AS2FormItem("section_odluka",AS2Field.FORM_SECTION);
        section_odluka.setDefaultValue("Odluka");
        section_odluka.setSectionItems(MCARD_GR_ZAHTJEV__PREPORUKA,
        		MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,
        		MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA,MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA,
        		MCARD_GR_ZAHTJEV__KOMENTAR);
        AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
		AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
        form.setDataSource(_dataSource);
        form.setFields(broj_zahtjeva,section_odluka,preporuka,status_odobrenja,
        		osoba_odobravanja,datum_odobravanja,komentar,faza_zahtjeva,status_zahtjeva);
        form.focusInItem(preporuka);
        form.setRequiredFields(status_odobrenja,osoba_odobravanja,datum_odobravanja);
        form.setDropDownModel(getSifrarnikModel(), status_odobrenja);
        return form;
	}
	protected AS2DynamicForm getForm(){
		return _form;
	}
	//TODO testirati
	protected void statusOdobrenjaChanged(String odabir) {
		if(odabir==null) odabir="";
		boolean zahtjev_zatvoren = _form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO);
		if(odabir.equalsIgnoreCase("odobren") || odabir.equals("")){
    		_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(true);
			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(true);
			if(!zahtjev_zatvoren){
				_parent.getButtonZavrsi().setDisabled(true);
				_parent.getButtonNastavak().setDisabled(false);
			}
    	}else{
    		_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(false);
    		_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(false);
    		if(!zahtjev_zatvoren){
				_parent.getButtonZavrsi().setDisabled(false);
				_parent.getButtonNastavak().setDisabled(true);
			}
    	}
    	_form.redraw();
	}
	
//    protected void statusOdobrenjaChanged(String odabir) {
//    	if(_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).equals(FAZA_ZAHTJEVA) && 
//    			!_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO)){
//    		if(odabir!=null && odabir.equalsIgnoreCase("odobren")){
//    			_parent.getButtonZavrsi().setDisabled(true);
//    			_parent.getButtonNastavak().setDisabled(false);
//    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(true);
//    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(true);
//    		
//    		}else{
//    			_parent.getButtonZavrsi().setDisabled(false);
//    			_parent.getButtonNastavak().setDisabled(true);
//    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(false);
//    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(false);
//    		
//    		}
//    		_form.redraw();
//    	}
//	}

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
	@Override
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
