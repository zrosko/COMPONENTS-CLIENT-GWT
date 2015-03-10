package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziGrOsobaAdresaModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziPartijaMcardModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.views.TraziWindow;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItemIcon;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;
import java.util.LinkedHashMap;

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
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabPripremaPodatakaDodatni extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabPripremaPodatakaDodatni view;
	private McardGrZahtjevDodatniMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "priprema";
	public static final  String FAZA_ZAHTJEVA_NEXT = "odluka";
	Record _record;
	private HLayout _buttons_layout;
	public DataSource _dataSource;
	protected AS2View _parent_view;
//	private IButton _button_print;
	private IButton _button_spremi;
	private PickerIcon _lookupGr;
	private PickerIcon _lookupGrVeza;
	private PickerIcon _lookupBrojKreditnogRacuna;

	public McardGrZahtjevTabPripremaPodatakaDodatni(McardGrZahtjevDodatniMWindow parent, Record record){
		_record = record;
		_parent = parent;
		_parent.getButtonPrethodno().setDisabled(true);
		if (_parent._novi_zahtjev) {
			_parent.getButtonZavrsi().setDisabled(true);
			_parent.getButtonNastavak().setDisabled(false);
		}
		createComponents();
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 2;
		McardGrZahtjevTabPripremaPodatakaDodatni.view=this;
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
		if (_parent._novi_zahtjev) {
			_form = getDynamicForm();
			_form.editNewRecord();
			_form.getField(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA).setValue("D");
		} else {
			_form = getDynamicForm();
			_form.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					_record = dsResponse.getDataAsRecordList().get(0);
					_form.editRecord(_record);
					if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevDodatniMWindow.STATUS_ZAHTJEVA_ZATVORENO))
						_form.setCanEdit(false);
				}
			},new DSRequest(DSOperationType.CUSTOM,"procitajMcardGrZahtjev"));
		}
		this.setWidth100();
		this.setHeight100();
		this.setStyleName("");
		this.addMembers(_form, _buttons_layout);
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		getLookUpsAndButtons();
		AS2DynamicForm form =  new AS2DynamicForm(null, number_of_columns);
		form.setColWidths("20%", "80%");
		form.setWrapItemTitles(true);
		form.setStyleNameGray();
		form.setSaveOnEnter(false);
		form.addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				_button_spremi.setDisabled(false);				
			}
		});
		//Broj zahtjeva dobijemo sa viewa (iz _record)
		AS2FormItem broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
		AS2FormItem oib_veza = new AS2FormItem(MCARD_GR_ZAHTJEV__OIB_VEZA);
		oib_veza.setWidth(150);
		oib_veza.getField().setIcons(_lookupGrVeza);
		oib_veza.setKeyPressFilter("[0-9]");
		AS2FormItem jmbg_veza = new AS2FormItem(MCARD_GR_ZAHTJEV__JMBG_VEZA);
		jmbg_veza.setWidth(150);
		jmbg_veza.getField().setIcons(_lookupGrVeza);
		jmbg_veza.setKeyPressFilter("[0-9]");
		AS2FormItem ime_prezime_veza = new AS2FormItem(MCARD_GR_ZAHTJEV__IME_PREZIME_VEZA);
		ime_prezime_veza.getField().setIcons(_lookupGrVeza);
		AS2FormItem broj_partije_mcard = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD);
        broj_partije_mcard.setWidth(150);
        broj_partije_mcard.getField().setIcons(_lookupBrojKreditnogRacuna);
        AS2FormItem section_osnovni_korisnik = new AS2FormItem("section_osnovni_korisnik",AS2Field.FORM_SECTION);
		section_osnovni_korisnik.setDefaultValue("Osnovni korisnik");
        section_osnovni_korisnik.setSectionItems(MCARD_GR_ZAHTJEV__OIB_VEZA, MCARD_GR_ZAHTJEV__JMBG_VEZA,
        		MCARD_GR_ZAHTJEV__IME_PREZIME_VEZA,MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD);
		
        AS2FormItem oib = new AS2FormItem(MCARD_GR_ZAHTJEV__OIB);
		oib.setWidth(150);
		oib.getField().setIcons(_lookupGr);
		oib.setKeyPressFilter("[0-9]");
		AS2FormItem jmbg = new AS2FormItem(MCARD_GR_ZAHTJEV__JMBG);
		jmbg.setWidth(150);
		jmbg.getField().setIcons(_lookupGr);
		jmbg.setKeyPressFilter("[0-9]");
		AS2FormItem ime_prezime = new AS2FormItem(MCARD_GR_ZAHTJEV__IME_PREZIME);
		ime_prezime.getField().setIcons(_lookupGr);
		AS2FormItem ime_oca_majke = new AS2FormItem(MCARD_GR_ZAHTJEV__IME_OCA_MAJKE);
		AS2FormItem ulica = new AS2FormItem(MCARD_GR_ZAHTJEV__ULICA);
		AS2FormItem postanski_broj = new AS2FormItem(MCARD_GR_ZAHTJEV__POSTANSKI_BROJ);
		postanski_broj.setWidth(150);
		postanski_broj.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.PB_ICON));
		AS2FormItem mjesto = new AS2FormItem(MCARD_GR_ZAHTJEV__MJESTO);
		mjesto.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.SI_ICON));
		AS2FormItem drzava = new AS2FormItem(MCARD_GR_ZAHTJEV__DRZAVA);
		drzava.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.HR_ICON));
		AS2FormItem telefon = new AS2FormItem(MCARD_GR_ZAHTJEV__TELEFON);
		AS2FormItem mobitel = new AS2FormItem(MCARD_GR_ZAHTJEV__MOBITEL);
		AS2FormItem email = new AS2FormItem(MCARD_GR_ZAHTJEV__EMAIL);
		AS2FormItem naziv_isprave = new AS2FormItem(MCARD_GR_ZAHTJEV__NAZIV_ISPRAVE,AS2Field.FORM_SELECT);
		naziv_isprave.setWidth(150);
		AS2FormItem broj_isprave = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ISPRAVE);
		broj_isprave.setWidth(400);
		AS2FormItem izdavatelj_isprave = new AS2FormItem(MCARD_GR_ZAHTJEV__IZDAVATELJ_ISPRAVE);
		izdavatelj_isprave.setWidth(400);
		AS2FormItem datum_rodenja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_RODENJA);
		datum_rodenja.setWidth(150);
		AS2FormItem mjesto_rodenja = new AS2FormItem(MCARD_GR_ZAHTJEV__MJESTO_RODENJA);
		mjesto_rodenja.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.SI_ICON));
		AS2FormItem bracno_stanje = new AS2FormItem(MCARD_GR_ZAHTJEV__BRACNO_STANJE,AS2Field.FORM_SELECT,"Srodstvo");
		bracno_stanje.setWidth(150);
		AS2FormItem drzavljanstvo = new AS2FormItem(MCARD_GR_ZAHTJEV__DRZAVLJANSTVO);
		drzavljanstvo.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.HR_ICON));
		AS2FormItem section_dodatni_korisnik = new AS2FormItem("section_osobni_podaci",AS2Field.FORM_SECTION);
		section_dodatni_korisnik.setDefaultValue("Osobni Podaci");
		section_dodatni_korisnik.setSectionItems(MCARD_GR_ZAHTJEV__OIB, MCARD_GR_ZAHTJEV__JMBG, MCARD_GR_ZAHTJEV__IME_PREZIME,
				MCARD_GR_ZAHTJEV__IME_OCA_MAJKE, MCARD_GR_ZAHTJEV__ULICA, MCARD_GR_ZAHTJEV__POSTANSKI_BROJ, MCARD_GR_ZAHTJEV__MJESTO, MCARD_GR_ZAHTJEV__DRZAVA,
				MCARD_GR_ZAHTJEV__TELEFON, MCARD_GR_ZAHTJEV__MOBITEL, MCARD_GR_ZAHTJEV__EMAIL, MCARD_GR_ZAHTJEV__NAZIV_ISPRAVE, MCARD_GR_ZAHTJEV__BROJ_ISPRAVE,
				MCARD_GR_ZAHTJEV__IZDAVATELJ_ISPRAVE, MCARD_GR_ZAHTJEV__DATUM_RODENJA, MCARD_GR_ZAHTJEV__BRACNO_STANJE, MCARD_GR_ZAHTJEV__MJESTO_RODENJA, 
				MCARD_GR_ZAHTJEV__DRZAVLJANSTVO);

		AS2FormItem zaprimatelj = new AS2FormItem(MCARD_GR_ZAHTJEV__ZAPRIMATELJ,AS2Field.COMBO,"Zahtjev zaprimio");
		zaprimatelj.setWidth(400);
		zaprimatelj.setDropDownList("svi_korisnici");
		AS2FormItem datum_zaprimanja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA);
		datum_zaprimanja.setWidth(150);
		datum_zaprimanja.getField().setDefaultValue(new Date());
		AS2FormItem zahtjev_obradio = new AS2FormItem(MCARD_GR_ZAHTJEV__ZAHTJEV_OBRADIO);
        zahtjev_obradio.setDropDownList("korisnici_aplikacije");
        //TODO onaj tko je ulogiran
        zahtjev_obradio.setDefaultValue("");
        zahtjev_obradio.setWidth(400);
        AS2FormItem datum_obrade = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_OBRADE);
        datum_obrade.setWidth("150");
        datum_obrade.getField().setDefaultValue(new Date());
        AS2FormItem preporuka =  new AS2FormItem(MCARD_GR_ZAHTJEV__PREPORUKA);
        preporuka.getField().setRowSpan(3);
		AS2FormItem section_popunjava_banka = new AS2FormItem("section_popunjava_banka",AS2Field.FORM_SECTION);
		section_popunjava_banka.setDefaultValue("<b style=\"color:brown;\">Popunjava banka</b>");
		section_popunjava_banka.setSectionItems(MCARD_GR_ZAHTJEV__ZAPRIMATELJ, MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA,MCARD_GR_ZAHTJEV__ZAHTJEV_OBRADIO, 
				MCARD_GR_ZAHTJEV__DATUM_OBRADE,MCARD_GR_ZAHTJEV__PREPORUKA);
		
		AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
		AS2FormItem vrsta_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA);
		vrsta_zahtjeva.getField().setVisible(false);
		AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
		
		form.setDataSource(_dataSource);
		form.setFields(broj_zahtjeva, section_osnovni_korisnik, oib_veza, jmbg_veza,
				ime_prezime_veza, broj_partije_mcard, section_dodatni_korisnik,
				oib, jmbg, ime_prezime, ime_oca_majke, ulica,
				postanski_broj, mjesto, drzava, telefon, mobitel, email,
				naziv_isprave, broj_isprave, izdavatelj_isprave,
				datum_rodenja, bracno_stanje, mjesto_rodenja,drzavljanstvo,
				section_popunjava_banka,zaprimatelj,datum_zaprimanja,
				zahtjev_obradio,datum_obrade,preporuka,vrsta_zahtjeva,
				faza_zahtjeva,status_zahtjeva);
		form.setRequiredFields(oib_veza,oib,zaprimatelj,datum_zaprimanja,zahtjev_obradio,datum_obrade);
		form.focusInItem(oib_veza);
		return form;
	}
	protected AS2DynamicForm getForm(){
		return _form;
	}
	public void getLookUpsAndButtons() {
		_lookupGr = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						LinkedHashMap<String,String> remap = new LinkedHashMap<String,String>();
						remap.put(MCARD_GR_ZAHTJEV__OIB, "oib");
						remap.put(MCARD_GR_ZAHTJEV__JMBG, "jmbg_mb");
						remap.put(MCARD_GR_ZAHTJEV__IME_PREZIME, "ime_prezime_naziv");
						String[] polja = {"oib","jmbg_mb","ime_prezime_naziv"};
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupGr");
						if(event.getItem().getValue()!=null){
							criteria.addCriteria(remap.get(event.getItem().getName()), event.getItem().getValue().toString());
						}
						 new TraziWindow(TraziGrOsobaAdresaModel.getInstance(),McardGrZahtjevTabPripremaPodatakaDodatni.view,criteria,polja);
					}
				});
//		_lookupGr.setTabIndex(-1);
		
		_lookupGrVeza = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						LinkedHashMap<String,String> remap = new LinkedHashMap<String,String>();
						remap.put(MCARD_GR_ZAHTJEV__OIB_VEZA, "oib");
						remap.put(MCARD_GR_ZAHTJEV__JMBG_VEZA, "jmbg_mb");
						remap.put(MCARD_GR_ZAHTJEV__IME_PREZIME_VEZA, "ime_prezime_naziv");
						String[] polja = {"oib","jmbg_mb","ime_prezime_naziv"};
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupGrVeza");
						if(event.getItem().getValue()!=null){
							criteria.addCriteria(remap.get(event.getItem().getName()), event.getItem().getValue().toString());
						}
						 new TraziWindow(TraziGrOsobaAdresaModel.getInstance(),McardGrZahtjevTabPripremaPodatakaDodatni.view,criteria,polja);
					}
				});
//		_lookupGrVeza.setTabIndex(-1);

		_lookupBrojKreditnogRacuna = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupBrojKreditnogRacuna");
						if(_form.getField(MCARD_GR_ZAHTJEV__OIB_VEZA).getValue()!=null){
							criteria.addCriteria("value", _form.getField(MCARD_GR_ZAHTJEV__OIB_VEZA).getValue().toString());
						}
						new TraziWindow(TraziPartijaMcardModel.getInstance(),McardGrZahtjevTabPripremaPodatakaDodatni.view,criteria);
					}
				});
//		_lookupBrojKreditnogRacuna.setTabIndex(-1);
	}

	
	@Override
	public AS2ListGrid getListGrid() {
		return null;
	}

	protected HLayout getFormButtons() {
		HLayout buttons_layout = new HLayout(5);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
		_button_spremi = new IButton("Spremi izmjene");
		_button_spremi.setIcon(AS2Resources.SAVE_PATH);
		_button_spremi.setDisabled(true);
		_button_spremi.setAutoFit(true);
		_button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveData("save");
			}
		});
		buttons_layout.setMembers(_button_spremi);
		return buttons_layout;
	}

//	public String getWindowFormTitle() {
//		return "<b style=\"color:black;font-size:10pt;\">Podaci za zahtjev broj: "
//				+ _record.getAttributeAsString("broj_zahtjeva")
//				+ "</b></br>"
//				+ _record.getAttributeAsString("ime_prezime");
//	}
//	protected boolean saveDataNoviKorak(String akcija) {
//		if(akcija.equals("next") && _form.validate()){
//			_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_NEXT);
//			saveData();
//			return true;
//		}
//		else
//			return false;
//	}

	protected void saveData(final String akcija) {
		_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_POCETNI);
		_form.saveData(new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				for(String attribute: server.getAttributes()){
					_record.setAttribute(attribute, server.getAttributeAsObject(attribute));
				}
				_parent._novi_zahtjev=false;
				_parent._refresh = true;
				_parent.refreshWindow(_record);
				if(akcija.equals("next") || akcija.equals("previous"))
					_parent.tabSaveCallback();
				_button_spremi.setDisabled(true);
				_parent.getButtonZavrsi().setDisabled(false);
			}
		});
	}

	/*********** Povratak sa Trazi window ************/
	@Override
	public void getValuesFromTrazi(Record selectedRecord) {
		_button_spremi.setDisabled(false);
		if(selectedRecord.getAttribute("lookup_type").equals("lookupGr")){
			setFieldValue(MCARD_GR_ZAHTJEV__JMBG,selectedRecord.getAttributeAsObject("jmbg_mb"));
			setFieldValue(MCARD_GR_ZAHTJEV__OIB,selectedRecord.getAttributeAsObject("oib"));
			setFieldValue(MCARD_GR_ZAHTJEV__IME_PREZIME,selectedRecord.getAttributeAsObject("ime_prezime_naziv"));
			setFieldValue(MCARD_GR_ZAHTJEV__ULICA,selectedRecord.getAttributeAsObject("ulica"));
			setFieldValue(MCARD_GR_ZAHTJEV__MJESTO,selectedRecord.getAttributeAsObject("mjesto"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSTANSKI_BROJ,selectedRecord.getAttributeAsObject("postanski_broj"));
			setFieldValue(MCARD_GR_ZAHTJEV__DRZAVA,selectedRecord.getAttributeAsObject("drzava"));
			setFieldValue(MCARD_GR_ZAHTJEV__TELEFON,selectedRecord.getAttributeAsObject("telefon"));
			setFieldValue(MCARD_GR_ZAHTJEV__DATUM_RODENJA,selectedRecord.getAttributeAsObject("datum_rodenja"));
			setFieldValue(MCARD_GR_ZAHTJEV__IME_OCA_MAJKE,selectedRecord.getAttributeAsObject("ime_oca_majke"));
		}else if(selectedRecord.getAttribute("lookup_type").equals("lookupGrVeza")){
			setFieldValue(MCARD_GR_ZAHTJEV__JMBG_VEZA,selectedRecord.getAttributeAsObject("jmbg_mb"));
			setFieldValue(MCARD_GR_ZAHTJEV__OIB_VEZA,selectedRecord.getAttributeAsObject("oib"));
			setFieldValue(MCARD_GR_ZAHTJEV__IME_PREZIME_VEZA,selectedRecord.getAttributeAsObject("ime_prezime_naziv"));
		}else if(selectedRecord.getAttribute("lookup_type").equals("lookupBrojKreditnogRacuna")){
			setFieldValue(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD,selectedRecord.getAttributeAsObject("broj_partije_mcard"));
		}
	}
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
