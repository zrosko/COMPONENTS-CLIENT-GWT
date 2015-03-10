package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziGrOsobaAdresaModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziPartijaStednjaModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziPartijaTekuciModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziPoOsobaAdresaJrrModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.views.TraziWindow;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabPripremaPodataka extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabPripremaPodataka view;
	private McardGrZahtjevMWindow _parent;
	public static String FAZA_ZAHTJEVA = "priprema";
	public static String FAZA_ZAHTJEVA_NEXT = "obrada";
	Record _record;
	private HLayout _buttons_layout;
	public DataSource _dataSource;
	protected AS2View _parent_view;
	private IButton _button_print;
	private IButton _button_spremi;
	private AS2FormItem nacin_primanja_izvatka;
	private AS2FormItem izvadak_ulica;
	private AS2FormItem izvadak_postanski_broj;
	private AS2FormItem izvadak_mjesto;
	private AS2FormItem izvadak_drzava;
	private AS2FormItem dohvati_adresu;
	private AS2FormItem izvadak_email;
	private AS2FormItem blank_dohvati_adresu;
	private PickerIcon _lookupGr;
	private PickerIcon _lookupPo;
	private PickerIcon _lookupPartijaStednja;
	private PickerIcon _lookupPartijaTekuci;
	private AS2FormItem nacin_koristenja_gotovina;
	private AS2FormItem nacin_koristenja_kupnja;
	private AS2FormItem visina_tereta;

	public McardGrZahtjevTabPripremaPodataka(McardGrZahtjevMWindow parent, Record record){
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
		McardGrZahtjevTabPripremaPodataka.view=this;
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
			_form.getField(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA).setValue("O");
			nacinKoristenjaOdabir(nacin_koristenja_gotovina,nacin_koristenja_kupnja,null);
			nacinPrimanjaIzvatkaOdabir("");
		} else {
			_form = getDynamicForm();
			_form.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					_record = dsResponse.getDataAsRecordList().get(0);
					nacinKoristenjaOdabir(nacin_koristenja_gotovina,nacin_koristenja_kupnja,_record);
					nacinPrimanjaIzvatkaOdabir(_record.getAttributeAsString(MCARD_GR_ZAHTJEV__NACIN_PRIMANJA_IZVATKA));
					_form.editRecord(_record);
					if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO))
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
				if(_button_spremi.getDisabled())
					_button_spremi.setDisabled(false);			
			}
		});
		//Broj zahtjeva dobijemo sa viewa (iz _record)
		AS2FormItem broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
		AS2FormItem oib = new AS2FormItem(MCARD_GR_ZAHTJEV__OIB);
		oib.setWidth(150);
		oib.getField().setIcons(_lookupGr);
		oib.setKeyPressFilter("[0-9]");
		AS2FormItem jmbg = new AS2FormItem(MCARD_GR_ZAHTJEV__JMBG);
		jmbg.setWidth(150);
		jmbg.getField().setIcons(_lookupGr);
		jmbg.setKeyPressFilter("[0-9]");
		AS2FormItem datum_rodenja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_RODENJA);
		datum_rodenja.setWidth(150);
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
		AS2FormItem broj_uzdrzavanih = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_UZDRZAVANIH);
		broj_uzdrzavanih.setWidth(150);
		AS2FormItem bracno_stanje = new AS2FormItem(MCARD_GR_ZAHTJEV__BRACNO_STANJE,AS2Field.FORM_SELECT);
		bracno_stanje.setWidth(150);
		AS2FormItem section_osobni_podaci = new AS2FormItem("section_osobni_podaci",AS2Field.FORM_SECTION);
		section_osobni_podaci.setDefaultValue("Osobni Podaci");
		section_osobni_podaci.setSectionItems(MCARD_GR_ZAHTJEV__OIB, MCARD_GR_ZAHTJEV__JMBG, MCARD_GR_ZAHTJEV__DATUM_RODENJA, MCARD_GR_ZAHTJEV__IME_PREZIME,
				MCARD_GR_ZAHTJEV__IME_OCA_MAJKE, MCARD_GR_ZAHTJEV__ULICA, MCARD_GR_ZAHTJEV__POSTANSKI_BROJ, MCARD_GR_ZAHTJEV__MJESTO, MCARD_GR_ZAHTJEV__DRZAVA,
				MCARD_GR_ZAHTJEV__TELEFON, MCARD_GR_ZAHTJEV__MOBITEL, MCARD_GR_ZAHTJEV__EMAIL, MCARD_GR_ZAHTJEV__NAZIV_ISPRAVE, MCARD_GR_ZAHTJEV__BROJ_ISPRAVE,
				MCARD_GR_ZAHTJEV__IZDAVATELJ_ISPRAVE, MCARD_GR_ZAHTJEV__BROJ_UZDRZAVANIH, MCARD_GR_ZAHTJEV__BRACNO_STANJE);
		AS2FormItem neto_placa = new AS2FormItem(MCARD_GR_ZAHTJEV__NETO_PLACA,AS2Field.AMOUNT,"Posljednja neto plaća (nakon svih odbitaka)");
		neto_placa.setWidth(150);
		AS2FormItem mjesecna_obiteljska_primanja = new AS2FormItem(MCARD_GR_ZAHTJEV__MJESECNA_OBITELJSKA_PRIMANJA,AS2Field.AMOUNT,"Iznos ukupnih mjesečnih obiteljskih primanja");
		mjesecna_obiteljska_primanja.setWidth(150);
		AS2FormItem prosjecna_neto_placa = new AS2FormItem(MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA,AS2Field.AMOUNT,"Prosjećna neto plaća/mirovine u poslj. 3 mj.");
		prosjecna_neto_placa.setWidth(150);
		AS2FormItem mjesecni_izvanredni_prihodi = new AS2FormItem(MCARD_GR_ZAHTJEV__MJESECNI_IZVANREDNI_PRIHODI,AS2Field.AMOUNT,"Iznos mjesečnih izvanrednih prihoda");
		mjesecni_izvanredni_prihodi.setWidth(150);
		AS2FormItem section_financijski_podaci = new AS2FormItem("section_financijski_podaci",AS2Field.FORM_SECTION);
		section_financijski_podaci.setDefaultValue("Financijski Podaci");
		section_financijski_podaci.setSectionItems(MCARD_GR_ZAHTJEV__NETO_PLACA,
				MCARD_GR_ZAHTJEV__MJESECNA_OBITELJSKA_PRIMANJA, MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA,
				MCARD_GR_ZAHTJEV__MJESECNI_IZVANREDNI_PRIHODI);
		AS2FormItem poslodavac_naziv = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_NAZIV,AS2Field.TEXT,"Naziv poduzeća/ustanove");
		poslodavac_naziv.getField().setIcons(_lookupPo);
		AS2FormItem poslodavac_ulica = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_ULICA,AS2Field.TEXT,"Ulica poslodavca");
		AS2FormItem poslodavac_postanski_broj = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_POSTANSKI_BROJ,AS2Field.INTEGER,"Poštanski broj");
		poslodavac_postanski_broj.setWidth(150);
		poslodavac_postanski_broj.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.PB_ICON));
		AS2FormItem poslodavac_mjesto = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_MJESTO,AS2Field.TEXT,"Mjesto");
		AS2FormItem poslodavac_drzava = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_DRZAVA,AS2Field.COMBO,"Država");
		poslodavac_drzava.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.HR_ICON));
		poslodavac_drzava.setDropDownList("drzava");
		AS2FormItem poslodavac_oib = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_OIB,AS2Field.INTEGER,"OIB");
		poslodavac_oib.setWidth(150);
		poslodavac_oib.getField().setIcons(_lookupPo);
		AS2FormItem poslodavac_maticni_broj = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_MATICNI_BROJ,AS2Field.INTEGER,"MB poslodavca");
		poslodavac_maticni_broj.setWidth(150);
		poslodavac_maticni_broj.getField().setIcons(_lookupPo);
		AS2FormItem poslodavac_telefon = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_TELEFON,AS2Field.TEXT,"Telefon");
		poslodavac_telefon.setWidth(400);
		AS2FormItem radni_odnos = new AS2FormItem(MCARD_GR_ZAHTJEV__RADNI_ODNOS,AS2Field.FORM_SELECT);
		radni_odnos.setWidth(150);
		AS2FormItem strucna_sprema = new AS2FormItem(MCARD_GR_ZAHTJEV__STRUCNA_SPREMA,AS2Field.FORM_SELECT);
		strucna_sprema.setWidth(150);
		AS2FormItem poslodavac_radni_staz_godina = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_GODINA,AS2Field.INTEGER,"Radni staž kod poslodavca");
		poslodavac_radni_staz_godina.setWidth(50);
		poslodavac_radni_staz_godina.getField().setHint("godina");
		AS2FormItem blank_poslodavac_radni_staz_mjesec = new AS2FormItem("blank_poslodavac_radni_staz_mjesec",AS2Field.FORM_STATIC_TEXT,"");
		blank_poslodavac_radni_staz_mjesec.getField().setShowTitle(false);
		AS2FormItem poslodavac_radni_staz_mjesec = new AS2FormItem(MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_MJESEC,AS2Field.INTEGER,"");
		poslodavac_radni_staz_mjesec.setWidth(50);
		poslodavac_radni_staz_mjesec.getField().setHint("mjeseci");
		poslodavac_radni_staz_mjesec.getField().setShowTitle(false);
		AS2FormItem ukupni_radni_staz_godina = new AS2FormItem(MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_GODINA,AS2Field.INTEGER,"Ukupni radni staž");
		ukupni_radni_staz_godina.setWidth(50);
		ukupni_radni_staz_godina.getField().setHint("godina");
		AS2FormItem blank_ukupni_radni_staz_mjesec = new AS2FormItem("blank_ukupni_radni_staz_mjesec",AS2Field.FORM_STATIC_TEXT,"");
		blank_ukupni_radni_staz_mjesec.getField().setShowTitle(false);
		AS2FormItem ukupni_radni_staz_mjesec = new AS2FormItem(MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_MJESEC,AS2Field.INTEGER, "");
		ukupni_radni_staz_mjesec.setWidth(50);
		ukupni_radni_staz_mjesec.getField().setHint("mjeseci");
		ukupni_radni_staz_mjesec.getField().setShowTitle(false);
		AS2FormItem status_zaposlenja = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAPOSLENJA,AS2Field.FORM_SELECT);
		status_zaposlenja.setWidth(400);
		AS2FormItem vrsta_poslodavca = new AS2FormItem(MCARD_GR_ZAHTJEV__VRSTA_POSLODAVCA,AS2Field.FORM_SELECT);
		vrsta_poslodavca.setWidth(400);
		AS2FormItem potvrda_poslodavca = new AS2FormItem(MCARD_GR_ZAHTJEV__POTVRDA_POSLODAVCA,AS2Field.FORM_SELECT,"Potvrda poslodavca o ispravnosti podataka o zaposlenju i neto plaći");
		potvrda_poslodavca.setWidth(150);
		AS2FormItem section_podaci_o_zaposlenju = new AS2FormItem("section_podaci_o_zaposlenju",AS2Field.FORM_SECTION);
		section_podaci_o_zaposlenju.setDefaultValue("Podaci o zaposlenju");
		section_podaci_o_zaposlenju.setSectionItems(MCARD_GR_ZAHTJEV__POSLODAVAC_NAZIV, MCARD_GR_ZAHTJEV__POSLODAVAC_ULICA,
				MCARD_GR_ZAHTJEV__POSLODAVAC_POSTANSKI_BROJ, MCARD_GR_ZAHTJEV__POSLODAVAC_MJESTO,
				MCARD_GR_ZAHTJEV__POSLODAVAC_DRZAVA, MCARD_GR_ZAHTJEV__POSLODAVAC_OIB,
				MCARD_GR_ZAHTJEV__POSLODAVAC_MATICNI_BROJ, MCARD_GR_ZAHTJEV__POSLODAVAC_TELEFON, MCARD_GR_ZAHTJEV__RADNI_ODNOS,
				MCARD_GR_ZAHTJEV__STRUCNA_SPREMA, MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_GODINA,"blank_poslodavac_radni_staz_mjesec",
				MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_MJESEC, MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_GODINA,"blank_ukupni_radni_staz_mjesec",
				MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_MJESEC, MCARD_GR_ZAHTJEV__STATUS_ZAPOSLENJA,
				MCARD_GR_ZAHTJEV__VRSTA_POSLODAVCA, MCARD_GR_ZAHTJEV__POTVRDA_POSLODAVCA);
		AS2FormItem vrsta_kartice = new AS2FormItem(MCARD_GR_ZAHTJEV__VRSTA_KARTICE,AS2Field.FORM_SELECT);
		vrsta_kartice.getField().setRequired(true);
		vrsta_kartice.setWidth(150);
		vrsta_kartice.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String value = event.getItem().getValue().toString();
				if(value.equalsIgnoreCase("charge")){
					visina_tereta.setDropDownList("visina_tereta_charge");
				}else{
					visina_tereta.setDropDownList("visina_tereta");
				}
			}
		});
		AS2FormItem section_trazeni_proizvod = new AS2FormItem("section_trazeni_proizvod",AS2Field.FORM_SECTION);
		section_trazeni_proizvod.setDefaultValue("Traženi proizvod");
		section_trazeni_proizvod.setSectionItems(MCARD_GR_ZAHTJEV__VRSTA_KARTICE);
		AS2FormItem nacin_podmirenja_obveza = new AS2FormItem(MCARD_GR_ZAHTJEV__NACIN_PODMIRENJA_OBVEZA,AS2Field.FORM_SELECT);
		nacin_podmirenja_obveza.setWidth(400);
		AS2FormItem broj_partije_tekuceg = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_TEKUCEG);
		broj_partije_tekuceg.setWidth(150);
		broj_partije_tekuceg.getField().setIcons(_lookupPartijaTekuci);
		broj_partije_tekuceg.getField().setHint("odaberite partiju");
		visina_tereta = new AS2FormItem(MCARD_GR_ZAHTJEV__VISINA_TERETA,AS2Field.FORM_SELECT);
		visina_tereta.setWidth(400);
		visina_tereta.setDefaultToFirstOption(true);
		AS2FormItem osigurano_depozitom = new AS2FormItem(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM,AS2Field.FORM_SELECT);
		osigurano_depozitom.setWidth(150);
		AS2FormItem partija_depozita = new AS2FormItem(MCARD_GR_ZAHTJEV__PARTIJA_DEPOZITA, AS2FormItem.BIGINT);
		partija_depozita.getField().setIcons(_lookupPartijaStednja);
		partija_depozita.setWidth(150);
		partija_depozita.getField().setHint("odaberite partiju");
		AS2FormItem stanje_depozita = new AS2FormItem(MCARD_GR_ZAHTJEV__STANJE_DEPOZITA);
		stanje_depozita.setWidth(150);
		AS2FormItem nacin_koristenja = new AS2FormItem(MCARD_GR_ZAHTJEV__NACIN_KORISTENJA);
		nacin_koristenja.getField().setVisible(false);
		AS2FormItem nacin_koristenja_title = new AS2FormItem("nacin_koristenja_title",AS2Field.FORM_STATIC_TEXT,"Plan korištenja kartice");
		nacin_koristenja_gotovina = new AS2FormItem("nacin_koristenja_gotovina",AS2Field.FORM_CHECKBOX,"Podizanje gotovine");
		nacin_koristenja_gotovina.getField().setDefaultValue(false);
		nacin_koristenja_kupnja = new AS2FormItem("nacin_koristenja_kupnja",AS2Field.FORM_CHECKBOX,"Kupnja na prodajnim mjestima");
		nacin_koristenja_kupnja.getField().setDefaultValue(false);
		AS2FormItem izvor_sredstava = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVOR_SREDSTAVA,AS2Field.FORM_SELECT,"Izvor sredstava za podmirenje mjesečne potrošnje");
		izvor_sredstava.setWidth(150);
		AS2FormItem ocekivana_potrosnja = new AS2FormItem(MCARD_GR_ZAHTJEV__OCEKIVANA_POTROSNJA,AS2Field.FORM_SELECT,"Iznos očekivane mjesečne potrošnje");
		ocekivana_potrosnja.setWidth(150);
		AS2FormItem section_nacin_podmirivanja_obveza = new AS2FormItem("section_nacin_podmirivanja_obveza",AS2Field.FORM_SECTION);
		section_nacin_podmirivanja_obveza.setDefaultValue("Način podmirivanja obveza");
		section_nacin_podmirivanja_obveza.setSectionItems(MCARD_GR_ZAHTJEV__NACIN_PODMIRENJA_OBVEZA,
				MCARD_GR_ZAHTJEV__BROJ_PARTIJE_TEKUCEG, MCARD_GR_ZAHTJEV__VISINA_TERETA, MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM,
				MCARD_GR_ZAHTJEV__PARTIJA_DEPOZITA, MCARD_GR_ZAHTJEV__STANJE_DEPOZITA,
				"nacin_koristenja_title", "nacin_koristenja_gotovina",
				"nacin_koristenja_kupnja", MCARD_GR_ZAHTJEV__IZVOR_SREDSTAVA,
				MCARD_GR_ZAHTJEV__OCEKIVANA_POTROSNJA);
		nacin_primanja_izvatka = new AS2FormItem(MCARD_GR_ZAHTJEV__NACIN_PRIMANJA_IZVATKA,AS2Field.FORM_RADIOGROUP);
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("adresa","poštom na adresu");
		map.put("email","e-mailom na adresu");
		map.put("jabanet","putem usluge JABAnet Internet bankarstva i/ili mJABE mobilnog bankarstva");
		nacin_primanja_izvatka.setDefaultValue("adresa");
		nacin_primanja_izvatka.getField().setValueMap(map);//TODO testirati
		nacin_primanja_izvatka.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				nacinPrimanjaIzvatkaOdabir(event.getValue().toString());
			}
		});
		izvadak_ulica = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVADAK_ULICA,AS2Field.TEXT,"Ulica");
		izvadak_postanski_broj = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVADAK_POSTANSKI_BROJ,AS2Field.INTEGER,"Poštanski broj");
		izvadak_postanski_broj.setWidth(150);
		izvadak_postanski_broj.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.PB_ICON));
		izvadak_mjesto = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVADAK_MJESTO,AS2Field.TEXT,"Mjesto");
		izvadak_mjesto.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.SI_ICON));
		izvadak_drzava = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVADAK_DRZAVA,AS2Field.COMBO,"Država");
		izvadak_drzava.getField().setIcons(new AS2FormItemIcon(AS2FormItemIcon.HR_ICON));
		izvadak_drzava.setDropDownList("drzava");
		blank_dohvati_adresu = new AS2FormItem("blank_dohvati_adresu",AS2Field.FORM_STATIC_TEXT,"");
		blank_dohvati_adresu.getField().setShowTitle(false);
		dohvati_adresu = new AS2FormItem("dohvati_adresu",AS2Field.FORM_BUTTON,"Dohvati adresu prebivališta");
		dohvati_adresu.setWidth(200);
		dohvati_adresu.getField().setStartRow(false);
		dohvati_adresu.getField().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				_form.getField(MCARD_GR_ZAHTJEV__IZVADAK_ULICA).setValue(_form.getField(MCARD_GR_ZAHTJEV__ULICA).getValue());
				_form.getField(MCARD_GR_ZAHTJEV__IZVADAK_POSTANSKI_BROJ).setValue(_form.getField(MCARD_GR_ZAHTJEV__POSTANSKI_BROJ).getValue());
				_form.getField(MCARD_GR_ZAHTJEV__IZVADAK_MJESTO).setValue(_form.getField(MCARD_GR_ZAHTJEV__MJESTO).getValue());
				_form.getField(MCARD_GR_ZAHTJEV__IZVADAK_DRZAVA).setValue(_form.getField(MCARD_GR_ZAHTJEV__DRZAVA).getValue());
			}
		});
		izvadak_email = new AS2FormItem(MCARD_GR_ZAHTJEV__IZVADAK_EMAIL,AS2Field.TEXT,"Email");
		AS2FormItem section_primanje_izvadaka = new AS2FormItem("section_primanje_izvadaka",AS2Field.FORM_SECTION);
		section_primanje_izvadaka.setDefaultValue("Primanje Izvadaka");
		section_primanje_izvadaka.setSectionItems(MCARD_GR_ZAHTJEV__NACIN_PRIMANJA_IZVATKA, MCARD_GR_ZAHTJEV__IZVADAK_ULICA,
				MCARD_GR_ZAHTJEV__IZVADAK_POSTANSKI_BROJ, MCARD_GR_ZAHTJEV__IZVADAK_MJESTO, MCARD_GR_ZAHTJEV__IZVADAK_DRZAVA,
				MCARD_GR_ZAHTJEV__IZVADAK_EMAIL,"blank_dohvati_adresu", "dohvati_adresu");
		AS2FormItem zaprimatelj = new AS2FormItem(MCARD_GR_ZAHTJEV__ZAPRIMATELJ,AS2Field.COMBO,"Zahtjev zaprimio");
		zaprimatelj.setWidth(400);
		zaprimatelj.setDropDownList("svi_korisnici");
		AS2FormItem datum_zaprimanja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA);
		datum_zaprimanja.setWidth(150);
		datum_zaprimanja.getField().setDefaultValue(new Date());
		AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
		AS2FormItem vrsta_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA);
		vrsta_zahtjeva.getField().setVisible(false);
		AS2FormItem section_popunjava_banka = new AS2FormItem("section_popunjava_banka",AS2Field.FORM_SECTION);
		section_popunjava_banka.setDefaultValue("<b style=\"color:brown;\">Popunjava banka</b>");
		section_popunjava_banka.setSectionItems(MCARD_GR_ZAHTJEV__ZAPRIMATELJ, MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA);
		AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
		form.setDataSource(_dataSource);
		form.setFields(broj_zahtjeva, section_osobni_podaci, oib, jmbg,
				datum_rodenja, ime_prezime, ime_oca_majke, ulica,
				postanski_broj, mjesto, drzava, telefon, mobitel, email,
				naziv_isprave, broj_isprave, izdavatelj_isprave,
				broj_uzdrzavanih, bracno_stanje, section_financijski_podaci,
				neto_placa, mjesecna_obiteljska_primanja, prosjecna_neto_placa,
				mjesecni_izvanredni_prihodi, section_podaci_o_zaposlenju,
				poslodavac_naziv, poslodavac_ulica, poslodavac_postanski_broj,
				poslodavac_mjesto, poslodavac_drzava, poslodavac_oib,
				poslodavac_maticni_broj, poslodavac_telefon, radni_odnos,
				strucna_sprema, poslodavac_radni_staz_godina, blank_poslodavac_radni_staz_mjesec,
				poslodavac_radni_staz_mjesec, ukupni_radni_staz_godina, blank_ukupni_radni_staz_mjesec,
				ukupni_radni_staz_mjesec, status_zaposlenja, vrsta_poslodavca,
				potvrda_poslodavca, section_trazeni_proizvod, vrsta_kartice,
				section_nacin_podmirivanja_obveza, nacin_podmirenja_obveza,
				broj_partije_tekuceg, visina_tereta, osigurano_depozitom,
				partija_depozita, stanje_depozita, nacin_koristenja,nacin_koristenja_title,
				nacin_koristenja_gotovina, nacin_koristenja_kupnja,
				izvor_sredstava, ocekivana_potrosnja, section_primanje_izvadaka,
				nacin_primanja_izvatka,izvadak_ulica, izvadak_postanski_broj,
				izvadak_mjesto, izvadak_drzava,blank_dohvati_adresu,dohvati_adresu,izvadak_email,
				section_popunjava_banka, zaprimatelj, datum_zaprimanja,vrsta_zahtjeva,
				faza_zahtjeva,status_zahtjeva);
		form.setRequiredFields(oib,neto_placa,prosjecna_neto_placa,mjesecni_izvanredni_prihodi,radni_odnos,vrsta_poslodavca,vrsta_kartice,osigurano_depozitom,zaprimatelj, datum_zaprimanja);
		form.focusInItem(oib);
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
						 new TraziWindow(TraziGrOsobaAdresaModel.getInstance(),McardGrZahtjevTabPripremaPodataka.view,criteria,polja);
					}
				});
//		_lookupGr.setTabIndex(-1);
		_lookupPo = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						LinkedHashMap<String,String> remap = new LinkedHashMap<String,String>();
						remap.put(MCARD_GR_ZAHTJEV__POSLODAVAC_OIB, "oib");
						remap.put(MCARD_GR_ZAHTJEV__POSLODAVAC_MATICNI_BROJ, "maticni_broj");
						remap.put(MCARD_GR_ZAHTJEV__POSLODAVAC_NAZIV, "naziv");
						String[] polja = {"oib","maticni_broj","naziv"};
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupPo");
						if(event.getItem().getValue()!=null){
							criteria.addCriteria(remap.get(event.getItem().getName()), event.getItem().getValue().toString());
							criteria.addCriteria("value",event.getItem().getValue().toString());
						}
						new TraziWindow(TraziPoOsobaAdresaJrrModel.getInstance(),McardGrZahtjevTabPripremaPodataka.view,criteria,polja);
					}
				});
//		_lookupPo.setTabIndex(-1);
		_lookupPartijaStednja = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupPartijaStednja");
						if(_form.getField(MCARD_GR_ZAHTJEV__OIB).getValue()!=null){
							criteria.addCriteria("value", _form.getField(MCARD_GR_ZAHTJEV__OIB).getValue().toString());
						}
						new TraziWindow(TraziPartijaStednjaModel.getInstance(),McardGrZahtjevTabPripremaPodataka.view,criteria);
					}
				});
//		_lookupPartijaStednja.setTabIndex(-1);
		_lookupPartijaTekuci = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						Criteria criteria = new Criteria();
						criteria.addCriteria("lookup_type","lookupPartijaTekuci");
						if(_form.getField(MCARD_GR_ZAHTJEV__OIB).getValue()!=null){
							criteria.addCriteria("value", _form.getField(MCARD_GR_ZAHTJEV__OIB).getValue().toString());
						}
						new TraziWindow(TraziPartijaTekuciModel.getInstance(),McardGrZahtjevTabPripremaPodataka.view,criteria);
					}
				});
//		_lookupPartijaTekuci.setTabIndex(-1);
	}

	private void nacinKoristenjaOdabir(AS2FormItem nacin_koristenja_gotovina, AS2FormItem nacin_koristenja_kupnja, Record record) {
		String nacinKoristenja;
		if (record!= null && record.getAttributeAsObject(MCARD_GR_ZAHTJEV__NACIN_KORISTENJA) != null) {
			nacinKoristenja = record.getAttribute(MCARD_GR_ZAHTJEV__NACIN_KORISTENJA);
			if (nacinKoristenja.contains("gotovine"))
				nacin_koristenja_gotovina.getField().setDefaultValue(true);
			else if (nacinKoristenja.contains("Kupnja"))
				nacin_koristenja_kupnja.getField().setDefaultValue(true);
		} else {
			nacin_koristenja_gotovina.getField().setDefaultValue(false);
			nacin_koristenja_kupnja.getField().setDefaultValue(false);
		}
	}

	private void nacinPrimanjaIzvatkaOdabir(String value){
		if(value!=null && value.length()>0){
			if (value.equals("poštom na adresu") || value.equals("adresa")) {
				nacin_primanja_izvatka.getField().setValue("adresa");
				izvadak_ulica.getField().show();
				izvadak_postanski_broj.getField().show();
				izvadak_mjesto.getField().show();
				izvadak_drzava.getField().show();
				blank_dohvati_adresu.getField().show();
				dohvati_adresu.getField().show();
				izvadak_email.getField().hide();
			} else if (value.contains("e-mailom na adresu") || value.equals("email")) {
				nacin_primanja_izvatka.getField().setValue("email");
				izvadak_ulica.getField().hide();
				izvadak_postanski_broj.getField().hide();
				izvadak_mjesto.getField().hide();
				izvadak_drzava.getField().hide();
				blank_dohvati_adresu.getField().hide();
				dohvati_adresu.getField().hide();
				izvadak_email.getField().show();
			} else if (value.contains("jabanet") || value.contains("JABAnet Internet bankarstva")) {
				nacin_primanja_izvatka.getField().setValue("jabanet");
				izvadak_ulica.getField().hide();
				izvadak_postanski_broj.getField().hide();
				izvadak_mjesto.getField().hide();
				izvadak_drzava.getField().hide();
				blank_dohvati_adresu.getField().hide();
				dohvati_adresu.getField().hide();
				izvadak_email.getField().hide();
			}
		} else {
			nacin_primanja_izvatka.getField().setValue("adresa");
			izvadak_ulica.getField().show();
			izvadak_postanski_broj.getField().show();
			izvadak_mjesto.getField().show();
			izvadak_drzava.getField().show();
			blank_dohvati_adresu.getField().show();
			dohvati_adresu.getField().show();
			izvadak_email.getField().hide();
		}
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
		_button_print = new IButton("Podaci o klijentu/partijama");
		_button_print.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_print.setAutoFit(true);
		_button_print.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(McardGrZahtjevTabPripremaPodataka.this, McardGrZahtjevModel.REPORT_SERVER, "izvjestaji");
				report.setParameter("reportSelected", "podaci_o_klijentu");
				if(_record!=null){
					report.setParameter(MCARD_GR_ZAHTJEV__OIB,_record.getAttribute(MCARD_GR_ZAHTJEV__OIB));
					report.setParameter(MCARD_GR_ZAHTJEV__JMBG,_record.getAttribute(MCARD_GR_ZAHTJEV__JMBG));
					report.setParameter(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
				}else{
					report.setParameter(MCARD_GR_ZAHTJEV__OIB,_form.getRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV__OIB));
					report.setParameter(MCARD_GR_ZAHTJEV__JMBG,_form.getRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV__JMBG));
				}
					report.getJasperReport("podaci_o_klijentu_cn");
				
				//old
//				AS2Report report = new AS2Report("podaci_o_klijentu_cn","podaci_o_klijentu");
//				report.setComponent(((AS2RestJSONDataSource)_dataSource).getRemoteObject());
//				report.setValueObject(((AS2RestJSONDataSource)_dataSource).getTransformTo());
//				report.setService("izvjestaji");
//				if(_record!=null){
//					report.setParameter(MCARD_GR_ZAHTJEV__OIB,_record.getAttribute(MCARD_GR_ZAHTJEV__OIB));
//					report.setParameter(MCARD_GR_ZAHTJEV__JMBG,_record.getAttribute(MCARD_GR_ZAHTJEV__JMBG));
//					report.setParameter(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
//				}else{
//					report.setParameter(MCARD_GR_ZAHTJEV__OIB,_form.getRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV__OIB));
//					report.setParameter(MCARD_GR_ZAHTJEV__JMBG,_form.getRecordList().get(0).getAttribute(MCARD_GR_ZAHTJEV__JMBG));
//				}
//				report.printReport();
				//TEST
				//com.google.gwt.user.client.Window.open("http://127.0.0.1:8888/karticno/report?reportFilename=podaci_o_klijentu_cn&reportFormat=pdf&reportSelected=podaci_o_klijentu&Component=hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer&Service=izvjestaji&oib="+_record.getAttributeAsStringOrBlank("oib")+"&jmbg="+_record.getAttributeAsStringOrBlank("jmbg")+"&broj_zahtjeva="+_record.getAttributeAsStringOrBlank("broj_zahtjeva"),"_blank",null);
			}
		});
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
		buttons_layout.setMembers(_button_print, _button_spremi);
		return buttons_layout;
	}

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
		String razmak = "";
		String nacin_koristenja_gotovina = _form.getValueAsString("nacin_koristenja_gotovina");
		String nacin_koristenja_kupnja = _form.getValueAsString("nacin_koristenja_kupnja");
		if(nacin_koristenja_gotovina.equals("true") && nacin_koristenja_kupnja.equals("true"))
			razmak=", ";
		if(nacin_koristenja_gotovina.equals("true")) nacin_koristenja_gotovina="gotovina";
		else nacin_koristenja_gotovina="";
		if(nacin_koristenja_kupnja.equals("true")) nacin_koristenja_kupnja="kupnja";
		else nacin_koristenja_kupnja="";
		_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_POCETNI);
		_form.getField(MCARD_GR_ZAHTJEV__NACIN_KORISTENJA).setValue(nacin_koristenja_gotovina+razmak+nacin_koristenja_kupnja);
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
		}else if(selectedRecord.getAttribute("lookup_type").equals("lookupPo")){
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_MATICNI_BROJ,selectedRecord.getAttributeAsObject("maticni_broj"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_OIB,selectedRecord.getAttributeAsObject("oib"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_NAZIV,selectedRecord.getAttributeAsObject("naziv"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_ULICA,selectedRecord.getAttributeAsObject("ulica"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_MJESTO,selectedRecord.getAttributeAsObject("mjesto"));
			setFieldValue(MCARD_GR_ZAHTJEV__POSLODAVAC_POSTANSKI_BROJ,selectedRecord.getAttributeAsObject("postanski_broj"));			
		}else if(selectedRecord.getAttribute("lookup_type").equals("lookupPartijaStednja")){
			setFieldValue(MCARD_GR_ZAHTJEV__PARTIJA_DEPOZITA,selectedRecord.getAttributeAsObject("broj_partije"));
			setFieldValue(MCARD_GR_ZAHTJEV__STANJE_DEPOZITA,selectedRecord.getAttributeAsObject("saldo"));
		}else if(selectedRecord.getAttribute("lookup_type").equals("lookupPartijaTekuci")){
			setFieldValue(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_TEKUCEG,selectedRecord.getAttributeAsObject("broj_partije_tekuceg"));
		}
	}
	@Override
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
