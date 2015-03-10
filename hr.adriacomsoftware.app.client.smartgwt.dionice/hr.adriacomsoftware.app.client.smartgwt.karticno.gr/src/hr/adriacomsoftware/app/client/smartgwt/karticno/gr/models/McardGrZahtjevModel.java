/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;

/**
 * @author msekso,astrikoman
 *
 */
public class McardGrZahtjevModel extends AS2RestJSONDataSource implements McardGrZahtjevConstants{
	
	public static final String REPORT_SERVER = "hr.adriacomsoftware.app.server.karticno.gr.reports.KarticnoReportServer";	
	private static McardGrZahtjevModel instance = new McardGrZahtjevModel("McardGrZahtjevModel");
	public static McardGrZahtjevModel getInstance() {
		return instance;
	}
	
	public McardGrZahtjevModel(String id) {
		super(id);
//		FormItemIcon trazi = new FormItemIcon();
//		trazi.setSrc(AS2Resources.INSTANCE.search_icon().getSafeUri().asString());
//		trazi.addFormItemClickHandler(new FormItemClickHandler() {
//			@Override
//			public void onFormItemClick(FormItemIconClickEvent event) {
//				String[] polja = {"oib","jmbg_mb","ime_prezime_naziv"};
//				new TraziWindow(TraziGrOsobaAdresaModel.getInstance(),McardGrZahtjevTabPripremaPodataka.view,polja);
//			}
//		});
		AS2DataSourceField broj_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,AS2Field.PRIMARY_KEY,"Broj zahtjeva");
		AS2DataSourceField datum_zaprimanja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA,AS2Field.DATE,"Datum zaprimanja");
		AS2DataSourceField organizacijska_jedinica = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ORGANIZACIJSKA_JEDINICA,AS2Field.INTEGER,"Organizacijska jedinica");
		AS2DataSourceField profitni_centar = new AS2DataSourceField(MCARD_GR_ZAHTJEV__PROFITNI_CENTAR,AS2Field.INTEGER,"Profitni centar");
		AS2DataSourceField zaprimatelj = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ZAPRIMATELJ,AS2Field.TEXT,"Zaprimatelj");
		AS2DataSourceField ime_prezime = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IME_PREZIME,AS2Field.TEXT,"Ime i prezime");
		AS2DataSourceField oib = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OIB,AS2Field.TEXT,"OIB",11);
		AS2DataSourceField jmbg = new AS2DataSourceField(MCARD_GR_ZAHTJEV__JMBG,AS2Field.TEXT,"JMBG",13);
		AS2DataSourceField ime_oca_majke = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IME_OCA_MAJKE,AS2Field.TEXT,"Ime oca/majke");
		AS2DataSourceField ulica = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ULICA,AS2Field.TEXT,"Ulica");
		AS2DataSourceField mjesto = new AS2DataSourceField(MCARD_GR_ZAHTJEV__MJESTO,AS2Field.TEXT,"Mjesto");
		AS2DataSourceField postanski_broj = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSTANSKI_BROJ,AS2Field.INTEGER,"Poštanski broj");
		AS2DataSourceField drzava = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DRZAVA,AS2Field.COMBO,"Država");
		AS2DataSourceField telefon = new AS2DataSourceField(MCARD_GR_ZAHTJEV__TELEFON,AS2Field.TEXT,"Telefon");
		AS2DataSourceField mobitel = new AS2DataSourceField(MCARD_GR_ZAHTJEV__MOBITEL,AS2Field.TEXT,"Mobitel");
		AS2DataSourceField email = new AS2DataSourceField(MCARD_GR_ZAHTJEV__EMAIL,AS2Field.TEXT,"Email");
		AS2DataSourceField naziv_isprave = new AS2DataSourceField(MCARD_GR_ZAHTJEV__NAZIV_ISPRAVE,AS2Field.COMBO,"Identifikacijska isprava");
		AS2DataSourceField izdavatelj_isprave = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZDAVATELJ_ISPRAVE,AS2Field.TEXT,"Naziv izdavatelja");
		AS2DataSourceField broj_isprave = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_ISPRAVE,AS2Field.TEXT,"Broj isprave");
		AS2DataSourceField drzava_isprave = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DRZAVA_ISPRAVE,AS2Field.TEXT,"Država isprave");
		AS2DataSourceField datum_rodenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DATUM_RODENJA,AS2Field.DATE,"Datum rođenja");
		AS2DataSourceField mjesto_rodenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__MJESTO_RODENJA,AS2Field.TEXT,"Mjesto rođenja");
		AS2DataSourceField drzavljanstvo = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DRZAVLJANSTVO,AS2Field.TEXT,"Drzavljanstvo");
		AS2DataSourceField bracno_stanje = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BRACNO_STANJE,AS2Field.COMBO,"Bračno stanje");
		AS2DataSourceField broj_uzdrzavanih = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_UZDRZAVANIH,AS2Field.INTEGER,"Broj uzdržavanih");
		AS2DataSourceField neto_placa = new AS2DataSourceField(MCARD_GR_ZAHTJEV__NETO_PLACA,AS2Field.AMOUNT,"Neto plaća");
		AS2DataSourceField prosjecna_neto_placa = new AS2DataSourceField(MCARD_GR_ZAHTJEV__PROSJECNA_NETO_PLACA,AS2Field.AMOUNT,"Prosječna neto plaća");
		AS2DataSourceField mjesecna_obiteljska_primanja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__MJESECNA_OBITELJSKA_PRIMANJA,AS2Field.AMOUNT,"Mjesečna obiteljska primanja");
		AS2DataSourceField mjesecni_izvanredni_prihodi = new AS2DataSourceField(MCARD_GR_ZAHTJEV__MJESECNI_IZVANREDNI_PRIHODI,AS2Field.AMOUNT,"Mjesečni izvanredni prihodi");
		AS2DataSourceField poslodavac_naziv = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_NAZIV,AS2Field.TEXT,"Naziv poduzeća/ustanove");
		AS2DataSourceField poslodavac_ulica = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_ULICA,AS2Field.TEXT,"Ulica poslodavca");
		AS2DataSourceField poslodavac_mjesto = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_MJESTO,AS2Field.TEXT,"Mjesto poslodavca");
		AS2DataSourceField poslodavac_drzava = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_DRZAVA,AS2Field.TEXT,"Država poslodavca");
		AS2DataSourceField poslodavac_postanski_broj = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_POSTANSKI_BROJ,AS2Field.INTEGER,"Poštanski broj");
		AS2DataSourceField poslodavac_oib = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_OIB,AS2Field.TEXT,"OIB poslodavca");
		AS2DataSourceField poslodavac_maticni_broj = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_MATICNI_BROJ,AS2Field.TEXT,"MB poslodavca");
		AS2DataSourceField poslodavac_telefon = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_TELEFON,AS2Field.TEXT,"Telefon poslodavca");
		AS2DataSourceField poslodavac_radni_staz_godina = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_GODINA,AS2Field.INTEGER,"Poslodavac radni staz godina",4);
		AS2DataSourceField poslodavac_radni_staz_mjesec = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POSLODAVAC_RADNI_STAZ_MJESEC,AS2Field.INTEGER,"Poslodavac radni staz mjesec",2);
		AS2DataSourceField ukupni_radni_staz_godina = new AS2DataSourceField(MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_GODINA,AS2Field.INTEGER,"Ukupni radni staz godina",4);
		AS2DataSourceField ukupni_radni_staz_mjesec = new AS2DataSourceField(MCARD_GR_ZAHTJEV__UKUPNI_RADNI_STAZ_MJESEC,AS2Field.INTEGER,"Ukupni radni staz mjesec",2);
		AS2DataSourceField radni_odnos = new AS2DataSourceField(MCARD_GR_ZAHTJEV__RADNI_ODNOS,AS2Field.COMBO,"Radni odnos na");
		AS2DataSourceField strucna_sprema = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STRUCNA_SPREMA,AS2Field.COMBO,"Stručna sprema");
		AS2DataSourceField status_zaposlenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STATUS_ZAPOSLENJA,AS2Field.COMBO,"Status zaposlenja");
		AS2DataSourceField vrsta_poslodavca = new AS2DataSourceField(MCARD_GR_ZAHTJEV__VRSTA_POSLODAVCA,AS2Field.COMBO,"Vrsta poslodavca");
		AS2DataSourceField potvrda_poslodavca = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POTVRDA_POSLODAVCA,AS2Field.COMBO,"Potvrda poslodavca");
		AS2DataSourceField vrsta_kartice = new AS2DataSourceField(MCARD_GR_ZAHTJEV__VRSTA_KARTICE,AS2Field.COMBO,"Vrsta kartice");
//		vrsta_kartice.getField().setValueMap("REVOLVING","CHARGE");/ima u sifrarniku
		AS2DataSourceField nacin_podmirenja_obveza = new AS2DataSourceField(MCARD_GR_ZAHTJEV__NACIN_PODMIRENJA_OBVEZA,AS2Field.COMBO,"Nacin podmirivanja obveza");
		AS2DataSourceField broj_partije_tekuceg = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_TEKUCEG,AS2Field.TEXT,"Broj tekućeg računa");
		AS2DataSourceField visina_tereta = new AS2DataSourceField(MCARD_GR_ZAHTJEV__VISINA_TERETA,AS2Field.COMBO,"Visina tereta");
		AS2DataSourceField osigurano_depozitom = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OSIGURANO_DEPOZITOM,AS2Field.COMBO,"Depozit kao instrument osiguranja");
		AS2DataSourceField nacin_koristenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__NACIN_KORISTENJA,AS2Field.TEXT,"Plan korištenja kartice");
		AS2DataSourceField izvor_sredstava = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVOR_SREDSTAVA,AS2Field.COMBO,"Izvor sredstava");
		AS2DataSourceField ocekivana_potrosnja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OCEKIVANA_POTROSNJA,AS2Field.COMBO,"Ocekivana potrosnja");
		AS2DataSourceField nacin_primanja_izvatka = new AS2DataSourceField(MCARD_GR_ZAHTJEV__NACIN_PRIMANJA_IZVATKA,AS2Field.TEXT,"Nacin primanja izvatka");
		AS2DataSourceField izvadak_ulica = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVADAK_ULICA,AS2Field.TEXT,"Izvadak ulica");
		AS2DataSourceField izvadak_mjesto = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVADAK_MJESTO,AS2Field.TEXT,"Izvadak mjesto");
		AS2DataSourceField izvadak_postanski_broj = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVADAK_POSTANSKI_BROJ,AS2Field.INTEGER,"Izvadak postanski broj");
		AS2DataSourceField izvadak_drzava = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVADAK_DRZAVA,AS2Field.COMBO,"Izvadak drzava");
		AS2DataSourceField izvadak_email = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IZVADAK_EMAIL,AS2Field.TEXT,"Izvadak email");
		AS2DataSourceField preporuceni_odobren_iznos = new AS2DataSourceField(MCARD_GR_ZAHTJEV__PREPORUCENI_ODOBREN_IZNOS,AS2Field.AMOUNT,"Preporuceni odobren iznos");
		AS2DataSourceField zahtjev_obradio = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ZAHTJEV_OBRADIO,AS2Field.COMBO,"Zahtjev obradio");
		AS2DataSourceField odobreni_iznos = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ODOBRENI_IZNOS,AS2Field.AMOUNT,"Odobreni iznos");
		AS2DataSourceField hrok_rate_kredita = new AS2DataSourceField(MCARD_GR_ZAHTJEV__HROK_RATE_KREDITA,AS2Field.AMOUNT,"Rate kredita (HROK)");
		AS2DataSourceField datum_odobravanja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA,AS2Field.DATE,"Datum odlluke");
		AS2DataSourceField osoba_odobravanja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA,AS2Field.COMBO,"Osoba odluke");
		AS2DataSourceField status_odobrenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,AS2Field.TEXT,"Status odobrenja");
		AS2DataSourceField komentar = new AS2DataSourceField(MCARD_GR_ZAHTJEV__KOMENTAR,AS2Field.TEXTAREA,"Odluka / zaključak");
		AS2DataSourceField ukupna_ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV__UKUPNA_OCJENA,AS2Field.TEXT,"Ukupna ocjena");
		AS2DataSourceField status_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA,AS2Field.TEXT,"Status zahtjeva");
		AS2DataSourceField operater_izmjene = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OPERATER_IZMJENE,AS2Field.TEXT,"Operater izmjene");
		AS2DataSourceField vrijeme_izmjene = new AS2DataSourceField(MCARD_GR_ZAHTJEV__VRIJEME_IZMJENE,AS2Field.DATE,"Vrijeme izmjene");
		AS2DataSourceField godine_zivota = new AS2DataSourceField(MCARD_GR_ZAHTJEV__GODINE_ZIVOTA,AS2Field.INTEGER,"Broj godina života");
		AS2DataSourceField dospjeli_dug = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DOSPJELI_DUG,AS2Field.AMOUNT,"Dospjeli dug");
		AS2DataSourceField broj_dana_kasnjenja = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_DANA_KASNJENJA,AS2Field.INTEGER,"Dani kašnjenja (HROK)");
		AS2DataSourceField stanje_tekuci_racun = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STANJE_TEKUCI_RACUN,AS2Field.AMOUNT,"Stanje tekuci racun");
		AS2DataSourceField broj_mjeseci_tekuci_racun = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_MJESECI_TEKUCI_RACUN,AS2Field.INTEGER,"Broj mjeseci tekuci racun");
		AS2DataSourceField broj_partije_mcard = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_PARTIJE_MCARD,AS2Field.TEXT,"Broj kreditnog računa");
		AS2DataSourceField partija_depozita = new AS2DataSourceField(MCARD_GR_ZAHTJEV__PARTIJA_DEPOZITA,AS2Field.BIGINT,"Partija depozita");
		AS2DataSourceField stanje_depozita = new AS2DataSourceField(MCARD_GR_ZAHTJEV__STANJE_DEPOZITA,AS2Field.AMOUNT,"Stanje depozita");
		AS2DataSourceField datum_obrade = new AS2DataSourceField(MCARD_GR_ZAHTJEV__DATUM_OBRADE,AS2Field.DATE,"Datum obrade");
		AS2DataSourceField ssp = new AS2DataSourceField(MCARD_GR_ZAHTJEV__SSP,AS2Field.TEXT,"Ssp");
		AS2DataSourceField vrsta_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA,AS2Field.TEXT,"Vrsta zahtjeva");
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("O", "Osnovni");
		map.put("D", "Dodatni");
		vrsta_zahtjeva.getField().setValueMap(map);//TODO - msekso - mislim da neće raditi
		AS2DataSourceField broj_kartice = new AS2DataSourceField(MCARD_GR_ZAHTJEV__BROJ_KARTICE,AS2Field.TEXT,"Broj kartice");
		AS2DataSourceField oib_veza = new AS2DataSourceField(MCARD_GR_ZAHTJEV__OIB_VEZA,AS2Field.TEXT,"Oib veza");
		AS2DataSourceField jmbg_veza = new AS2DataSourceField(MCARD_GR_ZAHTJEV__JMBG_VEZA,AS2Field.TEXT,"Jmbg veza");
		AS2DataSourceField ime_prezime_veza = new AS2DataSourceField(MCARD_GR_ZAHTJEV__IME_PREZIME_VEZA,AS2Field.TEXT,"Ime prezime veza");
		AS2DataSourceField pokrenuta_ovrha = new AS2DataSourceField(MCARD_GR_ZAHTJEV__POKRENUTA_OVRHA,AS2Field.COMBO,"Pokrenuta ovrha");
		AS2DataSourceField faza_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA,AS2Field.TEXT,"Faza zahtjeva");
		AS2DataSourceField preporuka = new AS2DataSourceField(MCARD_GR_ZAHTJEV__PREPORUKA,AS2Field.TEXTAREA,"Napomena pred odluku");
		AS2DataSourceField faza_icon = new AS2DataSourceField(MCARD_GR_ZAHTJEV__FAZA_ICON,AS2Field.TEXTAREA,"Faza icon");
		AS2DataSourceField odobrenje_icon = new AS2DataSourceField(MCARD_GR_ZAHTJEV__ODOBRENJE_ICON,AS2Field.TEXTAREA,"Odobrenje icon");
		
		AS2DataSourceField odluka = new AS2DataSourceField("odluka",AS2Field.TEXT,"Odluka");
		this.setFields(broj_zahtjeva, datum_zaprimanja,
				organizacijska_jedinica, profitni_centar, zaprimatelj,
				ime_prezime, oib, jmbg, ime_oca_majke, ulica, mjesto,
				postanski_broj, drzava, telefon, mobitel, email, naziv_isprave,
				izdavatelj_isprave, broj_isprave, drzava_isprave,
				datum_rodenja, mjesto_rodenja, drzavljanstvo, bracno_stanje,
				broj_uzdrzavanih, neto_placa, prosjecna_neto_placa,
				mjesecna_obiteljska_primanja, mjesecni_izvanredni_prihodi,
				poslodavac_naziv, poslodavac_ulica, poslodavac_mjesto,
				poslodavac_drzava, poslodavac_postanski_broj, poslodavac_oib,
				poslodavac_maticni_broj, poslodavac_telefon,
				poslodavac_radni_staz_godina, poslodavac_radni_staz_mjesec,
				ukupni_radni_staz_godina, ukupni_radni_staz_mjesec,
				radni_odnos, strucna_sprema, status_zaposlenja,
				vrsta_poslodavca, potvrda_poslodavca, vrsta_kartice,
				nacin_podmirenja_obveza, broj_partije_tekuceg, visina_tereta,
				osigurano_depozitom, nacin_koristenja, izvor_sredstava,
				ocekivana_potrosnja, nacin_primanja_izvatka, izvadak_ulica,
				izvadak_mjesto, izvadak_postanski_broj, izvadak_drzava,
				izvadak_email, preporuceni_odobren_iznos, zahtjev_obradio,
				odobreni_iznos, hrok_rate_kredita, datum_odobravanja,
				osoba_odobravanja, status_odobrenja, komentar, ukupna_ocjena,
				status_zahtjeva, operater_izmjene, vrijeme_izmjene,
				godine_zivota, dospjeli_dug, broj_dana_kasnjenja,
				stanje_tekuci_racun, broj_mjeseci_tekuci_racun,
				broj_partije_mcard, partija_depozita, stanje_depozita,
				datum_obrade, ssp, vrsta_zahtjeva, broj_kartice, oib_veza,
				jmbg_veza, ime_prezime_veza, pokrenuta_ovrha, faza_zahtjeva, 
				odluka, preporuka, faza_icon,odobrenje_icon);
	}
	
	@Override
	protected Record formatRecordFromServerJSON(Record record) {
		if(record.getAttributeAsObject("nacin_koristenja")!=null){
			if(record.getAttribute("nacin_koristenja").contains("kupnja"))
				record.setAttribute("nacin_koristenja_kupnja", true);
			if(record.getAttribute("nacin_koristenja").contains("gotovina"))
				record.setAttribute("nacin_koristenja_gotovina", true);
		}
		return super.formatRecordFromServerJSON(record);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.karticno.gr.dto.McardGrZahtjevVo";
	}

	
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajMcardGrZahtjev");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"azurirajMcardGrZahtjev");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"brisiMcardGrZahtjev");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajSveMcardGrZahtjev");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
	

	//TODO
	/*
	public String getExecuteFetchServiceName(String operation_id){
		if(operation_id != null && operation_id.equals("single"))
			return "procitajMcardGrZahtjev";
		if(operation_id != null && operation_id.equals("ucitajPodatkeZaMcardGrZahtjev"))
			return "ucitajPodatkeZaMcardGrZahtjev";
		return "procitajSveMcardGrZahtjev";
	}

	public String getExecuteAddServiceName(){
		return "dodajMcardGrZahtjev";
	}
	public String getExecuteUpdateServiceName(){
		return "azurirajMcardGrZahtjev";
	}
	public String getExecuteRemoveServiceName(){
		return "brisiMcardGrZahtjev";
	}
	public String getExecuteCustomServiceName(){
		return "ucitajPodatkeZaMcardGrZahtjev";
	}

		*/
	
	
}


