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

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.EditorExitEvent;
import com.smartgwt.client.widgets.form.fields.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabObrada extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabObrada view;
	private McardGrZahtjevMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "obrada";
	public static final String FAZA_ZAHTJEVA_NEXT = "kreditna_sposobnost";
	public static final String FAZA_ZAHTJEVA_PREVIOUS = "priprema";
	Record _record;
	IButton _button_ucitaj_podatke;
    boolean refresh=false;
    int _placa_granica = 2000;
    NumberFormat nf;
    
    FormItemIcon _yes_icon;
    FormItemIcon _no_icon;
    AS2FormItem broj_zahtjeva;
    AS2FormItem osigurano_depozitom;
    AS2FormItem section_osiguranje_depozitom;
    AS2FormItem radni_odnos;
    AS2FormItem prosjecna_neto_placa;
    AS2FormItem neto_placa;
    AS2FormItem godine_zivota;
    AS2FormItem dospjeli_dug;
    AS2FormItem broj_dana_kasnjenja;
    AS2FormItem stanje_tekuci_racun;
    AS2FormItem saldo_tekuci_racun;
    AS2FormItem broj_mjeseci_tekuci_racun;
    AS2FormItem pokrenuta_ovrha;
    AS2FormItem hrok_rate_kredita;
    AS2FormItem odluka;
    AS2FormItem zahtjev_obradio;
    AS2FormItem datum_obrade;
    AS2FormItem section_odluka;

    public DataSource _dataSource;
	private IButton _button_spremi;
	private HLayout _buttons_layout;
	private Label odluka_label;

	public McardGrZahtjevTabObrada(McardGrZahtjevMWindow parent,Record record) {
		_record = record;
		_parent = parent;
		createComponents();
	}
	@Override
	public void customWindowPreferences() {
		number_of_columns = 2;
		McardGrZahtjevTabObrada.view=this;
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
		_yes_icon = new FormItemIcon();
		_yes_icon.setSrc(AS2Resources.IMAGE_PATH + "icons/s16/yes.png");
		_yes_icon.setTabIndex(-1);
		_no_icon = new FormItemIcon();
		_no_icon.setTabIndex(-1);
		_no_icon.setSrc(AS2Resources.IMAGE_PATH + "icons/s16/no.png");
		_dataSource = getModel();
		_buttons_layout = getFormButtons();
		_form = getDynamicForm();
		_form.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				_record = dsResponse.getDataAsRecordList().get(0);
				_form.editRecord(_record);
				 osiguranoDepozitomCondition(_record.getAttributeAsObject(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM)!=null ? 
			        		_record.getAttributeAsString(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM) : osigurano_depozitom.getField().getValue().toString());
				 if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO)){
						_form.setCanEdit(false);
						_button_ucitaj_podatke.setDisabled(true);				
				}
			}
		},new DSRequest(DSOperationType.CUSTOM,"procitajMcardGrZahtjev"));
		this.setWidth100();
		this.setHeight100();
		this.setStyleName("");
		this.addMembers(_form, _buttons_layout);
	}
	//Primjer drugog načina fetcha
	//		DSRequest ds_request = _dataSource.getRequestProperties();
	//		ds_request.setOperationType(DSOperationType.FETCH);
	//		ds_request.setOperationId("single");
	//		_dataSource.setRequestProperties(ds_request);
	//		_dataSource.fetchRecord(_listGrid.getSelectedRecord().getAttributeAsInt("broj_zahtjeva"));

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
		AS2FormItem broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
		osigurano_depozitom = new AS2FormItem(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM);
		osigurano_depozitom.getField().setDefaultValue("ne");
		osigurano_depozitom.setWidth(150);
		osigurano_depozitom.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				 osiguranoDepozitomCondition(event.getItem().getValue().toString());
			}
		});
		section_osiguranje_depozitom = new AS2FormItem("section_osiguranje_depozitom",AS2Field.FORM_SECTION);
        section_osiguranje_depozitom.setDefaultValue("Osiguranje depozitom <i>(zadovoljavanje ovog uvjeta dovoljno je za odobravanje iznosa 1:1,1)</i>");
        section_osiguranje_depozitom.setSectionItems(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM);
        radni_odnos = new AS2FormItem(MCARD_GR_ZAHTJEV__RADNI_ODNOS);
        radni_odnos.getField().setHint("<nobr>neodređeno vrijeme</nobr>");
        radni_odnos.setWidth("150");
        prosjecna_neto_placa = new AS2FormItem(MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA);
        prosjecna_neto_placa.setWidth("150");
        neto_placa = new AS2FormItem(MCARD_GR_ZAHTJEV__NETO_PLACA);
        neto_placa.setTitle("(nakon svih odbitaka)");
        neto_placa.setWidth("150");
        godine_zivota = new AS2FormItem(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA);
        godine_zivota.getField().setHint("<nobr>manje od 75 godina</nobr>");
        godine_zivota.getField().setCanEdit(false);
        godine_zivota.setWidth("150");
        dospjeli_dug = new AS2FormItem(MCARD_GR_ZAHTJEV__DOSPJELI_DUG);
        dospjeli_dug.setTitle("Trenutni dospjeli dug");
        dospjeli_dug.getField().setHint("<nobr>nije veći od 100,00kn</nobr>");
        dospjeli_dug.setWidth("150");
        broj_dana_kasnjenja = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA);
        broj_dana_kasnjenja.getField().setHint("<nobr>ročnost kraća od 90 dana</nobr>");
        broj_dana_kasnjenja.setWidth("150");
        stanje_tekuci_racun = new AS2FormItem(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN);
        stanje_tekuci_racun.setTitle("Nedozvoljeno prekoračenje");
        stanje_tekuci_racun.getField().setHint("<nobr>nema nedozvoljeno prekoračenje</nobr>");
        stanje_tekuci_racun.setWidth("150");
        saldo_tekuci_racun = new AS2FormItem(MCARD_GR_ZAHTJEV__SALDO_TEKUCI_RACUN,AS2Field.AMOUNT,"Stanje na tekućem računu");
        saldo_tekuci_racun.setWidth("150");
        broj_mjeseci_tekuci_racun = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN);
        broj_mjeseci_tekuci_racun.setTitle("Broj mjeseci od otvaranja");
        broj_mjeseci_tekuci_racun.getField().setHint("<nobr>aktivan tekući račun u banci</nobr>");
        broj_mjeseci_tekuci_racun.setWidth("150");
        pokrenuta_ovrha = new AS2FormItem(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA);
        pokrenuta_ovrha.setWidth("150");
        hrok_rate_kredita = new AS2FormItem(MCARD_GR_ZAHTJEV__HROK_RATE_KREDITA);
        hrok_rate_kredita.setWidth("150");
        AS2FormItem odluka = new AS2FormItem(MCARD_GR_ZAHTJEV__ODLUKA,AS2Field.FORM_CANVAS,"");
        odluka_label = new Label();  
        odluka_label.setHeight(30);  
        odluka_label.setPadding(10);  
        odluka_label.setAlign(Alignment.CENTER);  
        odluka_label.setValign(VerticalAlignment.CENTER);  
        odluka_label.setWrap(false);  
        odluka_label.setShowEdges(true);  
        odluka_label.setContents(_record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));  
        odluka.getField().setShowTitle(false);
        odluka.setCanvas(odluka_label);
        odluka.getField().setColSpan("*");
        zahtjev_obradio = new AS2FormItem(MCARD_GR_ZAHTJEV__ZAHTJEV_OBRADIO);
        zahtjev_obradio.setDropDownList("korisnici_aplikacije");
        //TODO onaj tko je ulogiran
        zahtjev_obradio.setDefaultValue("");
        zahtjev_obradio.setWidth(400);
        datum_obrade = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_OBRADE);
        datum_obrade.setWidth("150");
        datum_obrade.getField().setDefaultValue(new Date());
        //        AS2FormItem status_zahtjeva = new AS2FormItem("status_zahtjeva","select", "Status zahtjeva",1);
        //        status_zahtjeva.setWidth("150");
        section_odluka = new AS2FormItem("section_odluka",AS2Field.FORM_SECTION);
        section_odluka.setDefaultValue("Preporuka");
        section_odluka.setSectionItems(MCARD_GR_ZAHTJEV__ODLUKA,MCARD_GR_ZAHTJEV__ZAHTJEV_OBRADIO,
        		MCARD_GR_ZAHTJEV__DATUM_OBRADE/*,"status_zahtjeva"*/);
        AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
		AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
        form.setDataSource(_dataSource);
        form.setFields(broj_zahtjeva,section_osiguranje_depozitom, osigurano_depozitom,
        		radni_odnos, prosjecna_neto_placa,neto_placa, godine_zivota, dospjeli_dug,
        		broj_dana_kasnjenja, stanje_tekuci_racun,saldo_tekuci_racun,
        		broj_mjeseci_tekuci_racun, pokrenuta_ovrha,hrok_rate_kredita,section_odluka,
        		odluka, zahtjev_obradio, datum_obrade, faza_zahtjeva,status_zahtjeva);
        form.focusInItem(osigurano_depozitom);
        form.setRequiredFields(osigurano_depozitom, zahtjev_obradio);
        setConditionIcons(_record);
        addConditionHandlers();
        return form;
	}
	protected AS2DynamicForm getForm(){
		return _form;
	}
	private boolean condition(String attribute,Record record){
		if(record.getAttributeAsObject(attribute)==null || record.getAttributeAsString(attribute).equals(""))
			return false;
		else
			return true;
	}
	
	private void setConditionIcons(Record record){
		//granica plaće
		if(condition(MCARD_GR_ZAHTJEV__VRSTA_KARTICE,record)){
			if(record.getAttribute(MCARD_GR_ZAHTJEV__VRSTA_KARTICE).equalsIgnoreCase("REVOLVING"))
				_placa_granica=2500;
			prosjecna_neto_placa.getField().setHint("<nobr>iznad "+_placa_granica+"</nobr>");
		}
		if(condition(MCARD_GR_ZAHTJEV__RADNI_ODNOS,record))
			radni_odnos.setIcons(uvjetSlika(record.getAttribute(MCARD_GR_ZAHTJEV__RADNI_ODNOS).equalsIgnoreCase("neodređeno")));
		else
			radni_odnos.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA,record))
			prosjecna_neto_placa.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA))>=_placa_granica));
		else
			prosjecna_neto_placa.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA,record))
			godine_zivota.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA))<=75));
		else
			godine_zivota.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__DOSPJELI_DUG,record))
			dospjeli_dug.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__DOSPJELI_DUG))<=100));
		else
			dospjeli_dug.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA,record))
			broj_dana_kasnjenja.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA))<=90));
		else
			broj_dana_kasnjenja.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN,record))
			stanje_tekuci_racun.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN))>=0));
		else
			stanje_tekuci_racun.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN,record))
			broj_mjeseci_tekuci_racun.setIcons(uvjetSlika(parse(record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN))>0));
		else
			broj_mjeseci_tekuci_racun.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA,record))
			pokrenuta_ovrha.setIcons(uvjetSlika(record.getAttribute(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA).equalsIgnoreCase("ne")));
		else
			pokrenuta_ovrha.setIcons(uvjetSlika(false));
		if(condition(MCARD_GR_ZAHTJEV__ODLUKA,record))
			odlukaSlika(record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));
	}

	private double parse(String value){
		return Double.parseDouble(AS2Field.parseAmountValue(value, AS2Field.AMOUNT).toString());

	}

	private FormItemIcon uvjetSlika(boolean value) {
		if (value == true)
			return _yes_icon;
		else
			return _no_icon;
	}
	private void odlukaSlika(String tekst){
		if(tekst.contains("Odbija")){
			odluka_label.setIcon(AS2Resources.CANCEL_PATH);
		}else if(tekst.contains("Odobrava")){
			odluka_label.setIcon(AS2Resources.SAVE_PATH);
		}else{
			odluka_label.setIcon(AS2Resources.WARNING_PATH);
		}
		odluka_label.setContents("<b style=\"font-size:12px;\">"+tekst+"</b>");
	}

	private void addConditionHandlers(){
		EditorExitHandler yesNoHandler = new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				setConditionIcons(_form.getValuesAsRecord());
			}
		};
		radni_odnos.getField().addEditorExitHandler(yesNoHandler);
		prosjecna_neto_placa.getField().addEditorExitHandler(yesNoHandler);
		godine_zivota.getField().addEditorExitHandler(yesNoHandler);
		dospjeli_dug.getField().addEditorExitHandler(yesNoHandler);
		broj_dana_kasnjenja.getField().addEditorExitHandler(yesNoHandler);
		stanje_tekuci_racun.getField().addEditorExitHandler(yesNoHandler);
		broj_mjeseci_tekuci_racun.getField().addEditorExitHandler(yesNoHandler);
		pokrenuta_ovrha.getField().addEditorExitHandler(yesNoHandler);
	}

	private void osiguranoDepozitomCondition(String osigurano_depozitom) {
		if(osigurano_depozitom.equals("da")){
			section_osiguranje_depozitom.setDefaultValue("Osiguranje depozitom <i>(zadovoljavanje ovog uvjeta dovoljno je za odobravanje iznosa 1:1,1)</i>");
			radni_odnos.getField().hide();
			prosjecna_neto_placa.getField().hide();
			neto_placa.getField().hide();
			godine_zivota.getField().hide();
			dospjeli_dug.getField().hide();
			broj_dana_kasnjenja.getField().hide();
			stanje_tekuci_racun.getField().hide();
			saldo_tekuci_racun.getField().hide();
			broj_mjeseci_tekuci_racun.getField().hide();
			pokrenuta_ovrha.getField().hide();
			hrok_rate_kredita.getField().hide();
		}else{
			section_osiguranje_depozitom.getField().setDefaultValue("Osnovni uvjeti <i>(treba zadovoljiti u slučaju kada odobreni iznos nije osiguran depozitom)</i>");
			radni_odnos.getField().show();
			prosjecna_neto_placa.getField().show();
			neto_placa.getField().show();
			godine_zivota.getField().show();
			dospjeli_dug.getField().show();
			broj_dana_kasnjenja.getField().show();
			stanje_tekuci_racun.getField().show();
			saldo_tekuci_racun.getField().show();
			broj_mjeseci_tekuci_racun.getField().show();
			pokrenuta_ovrha.getField().show();
			hrok_rate_kredita.getField().show();
		}

	}

    public HLayout getFormButtons() {
    	HLayout buttons_layout = new HLayout(5);
    	buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
		_button_ucitaj_podatke = new IButton("Učitaj raspoložive podatke(iz sustava Banke)");
	    _button_ucitaj_podatke.setIcon(AS2Resources.IMPORT_DATA_PATH);
	    _button_ucitaj_podatke.setAutoFit(true);
	    _button_ucitaj_podatke.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = new Criteria();
            	criteria.addCriteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
            	criteria.addCriteria(MCARD_GR_ZAHTJEV__DATUM_OBRADE,_form.getValue(MCARD_GR_ZAHTJEV__DATUM_OBRADE));
            	criteria.addCriteria(MCARD_GR_ZAHTJEV__OIB,_record.getAttribute(MCARD_GR_ZAHTJEV__OIB));
				_form.fetchData(criteria,new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						Record server = dsResponse.getDataAsRecordList().get(0);
						_record.setAttribute(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA,server.getAttribute(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA));
						_record.setAttribute(MCARD_GR_ZAHTJEV__DOSPJELI_DUG,server.getAttribute(MCARD_GR_ZAHTJEV__DOSPJELI_DUG));
						_record.setAttribute(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN,server.getAttribute(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN));
						_record.setAttribute(MCARD_GR_ZAHTJEV__SALDO_TEKUCI_RACUN,server.getAttribute(MCARD_GR_ZAHTJEV__SALDO_TEKUCI_RACUN));
						_record.setAttribute(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN,server.getAttribute(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN));
						_record.setAttribute(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA,server.getAttribute(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA));
						_record.setAttribute(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA,server.getAttribute(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA));
						_record.setAttribute(MCARD_GR_ZAHTJEV__RADNI_ODNOS,server.getAttribute(MCARD_GR_ZAHTJEV__RADNI_ODNOS));
						_record.setAttribute(MCARD_GR_ZAHTJEV__PREPORUCENI_ODOBREN_IZNOS,server.getAttribute(MCARD_GR_ZAHTJEV__PREPORUCENI_ODOBREN_IZNOS));
						setConditionIcons(_record);
						_form.editRecord(_record);
						_button_spremi.setDisabled(false);
					}
				},new DSRequest(DSOperationType.CUSTOM,"ucitajPodatkeZaMcardGrZahtjev"));
			}
		});
        _button_spremi = new IButton("Spremi i izračunaj preporučeni iznos");
        _button_spremi.setIcon(AS2Resources.SAVE_PATH);
    	_button_spremi.setDisabled(true);				
    	_button_spremi.setAutoFit(true);
        _button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveData("save");
			}
        });
        buttons_layout.setMembers(_button_ucitaj_podatke, _button_spremi);
        return buttons_layout;
    }
//    protected boolean saveDataNoviKorak(String akcija) {
//    	if(akcija.equals("previous")){
//    		_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_PREVIOUS);
//    		saveData();
//    		return true;
//    	}else if(akcija.equals("next") && _form.validate()){
//    		_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_NEXT);
//    		saveData();
//    		return true;
//    	}else 
//    		return false;
//    }
//    
	protected void saveData(final String akcija) {
		_form.saveData(new DSCallback() {
			public void execute(DSResponse dsResponse, Object rawData,DSRequest request) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				for(String attribute: server.getAttributes()){
					_record.setAttribute(attribute, server.getAttributeAsObject(attribute));
				}
				odlukaSlika(_record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));
				_parent._refresh=true;
				_parent.refreshWindow(_record);
				if(akcija.equals("next") || akcija.equals("previous"))
					_parent.tabSaveCallback();
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
