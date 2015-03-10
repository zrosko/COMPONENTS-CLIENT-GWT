package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaDolazakaModel extends AS2RestJSONDataSource implements EvidencijaDolazakaConstants {
	
	public static final String REPORT_SERVER = "hr.adriacomsoftware.app.server.evidencijadolazaka.reports.PlaceReportServer";	
	private static EvidencijaDolazakaModel instance = new EvidencijaDolazakaModel("EvidencijaDolazakaModel");
	
	public static EvidencijaDolazakaModel getInstance() {
		return instance;
	}
	
	public EvidencijaDolazakaModel(String id) {
		super(id);
		AS2DataSourceField id_dnevne_evidencije = new AS2DataSourceField(EVIDENCIJA__ID_DNEVNE_EVIDENCIJE,AS2Field.PRIMARY_KEY,"Id evidencije");
		AS2DataSourceField radnik_id = new AS2DataSourceField(EVIDENCIJA__RADNIK_ID,AS2Field.TEXT,"Djelatnik");
		AS2DataSourceField element_obracuna_id = new AS2DataSourceField(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID,AS2Field.TEXT,"Kategorija prisutnosti");
		AS2DataSourceField datum = new AS2DataSourceField(EVIDENCIJA__DATUM,AS2Field.DATE,"Datum");
		AS2DataSourceField radno_vrijeme_od = new AS2DataSourceField(EVIDENCIJA__RADNO_VRIJEME_OD,AS2Field.TIME,"RV od");
		AS2DataSourceField radno_vrijeme_do = new AS2DataSourceField(EVIDENCIJA__RADNO_VRIJEME_DO,AS2Field.TIME,"RV do");
		AS2DataSourceField obracun_sati = new AS2DataSourceField(EVIDENCIJA__OBRACUN_SATI,AS2Field.INTEGER,"Sati");
		AS2DataSourceField obracun_minuta = new AS2DataSourceField(EVIDENCIJA__OBRACUN_MINUTA,AS2Field.INTEGER,"Minuta");
		AS2DataSourceField korisnik = new AS2DataSourceField(EVIDENCIJA__KORISNIK,AS2Field.TEXT,"Korisnik");
		AS2DataSourceField vrijeme_izmjene = new AS2DataSourceField(EVIDENCIJA__VRIJEME_IZMJENE,AS2Field.DATETIME,"Vrijeme izmjene");
		AS2DataSourceField organizacijska_jedinica_id = new AS2DataSourceField(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID,AS2Field.TEXT,"Organizacijska jedinica");
		AS2DataSourceField oznaka_maticne_evidencije = new AS2DataSourceField(EVIDENCIJA__OZNAKA_MATICNE_EVIDENCIJE,AS2Field.TEXT,"Ozn. mat. evid.");
		AS2DataSourceField oib = new AS2DataSourceField(EVIDENCIJA__OIB,AS2Field.TEXT,"Oib",11);
		AS2DataSourceField napomena = new AS2DataSourceField(EVIDENCIJA__NAPOMENA,AS2Field.TEXT,"Napomena");
		AS2DataSourceField dodatni_unos = new AS2DataSourceField(EVIDENCIJA__DODATNI_UNOS,AS2Field.INTEGER,"Dodatni unos");
		AS2DataSourceField id_spica_oj = new AS2DataSourceField(EVIDENCIJA__ID_SPICA_OJ,AS2Field.TEXT,"Odjel");
		
		AS2DataSourceField napomena_spica = new AS2DataSourceField(EVIDENCIJA__NAPOMENA_SPICA,AS2Field.TEXTAREA,"Napomena - Špica");
		AS2DataSourceField radni_dan = new AS2DataSourceField(EVIDENCIJA__RADNI_DAN,AS2Field.TEXT,"Radni dan");
		AS2DataSourceField potvrda = new AS2DataSourceField(EVIDENCIJA__POTVRDA,AS2Field.TEXT,"Potvrda");
		AS2DataSourceField potvrda_icon = new AS2DataSourceField(EVIDENCIJA__POTVRDA_ICON,AS2Field.TEXT,"Potvrda icon");
		AS2DataSourceField datum_mj_prvi = new AS2DataSourceField(EVIDENCIJA__DATUM_MJ_PRVI,AS2Field.DATE,"Datum početak");
		AS2DataSourceField datum_mj_zadnji = new AS2DataSourceField(EVIDENCIJA__DATUM_MJ_ZADNJI,AS2Field.DATE,"Datum kraj");
		
		AS2DataSourceField datum_od = new AS2DataSourceField(EVIDENCIJA__DATUM_OD,AS2Field.DATE,"Datum od");
		AS2DataSourceField datum_do = new AS2DataSourceField(EVIDENCIJA__DATUM_DO,AS2Field.DATE,"Datum do");
		AS2DataSourceField stara_sifra = new AS2DataSourceField(EVIDENCIJA__STARA_SIFRA,AS2Field.TEXT,"Stara kategorija");
		AS2DataSourceField nova_sifra = new AS2DataSourceField(EVIDENCIJA__NOVA_SIFRA,AS2Field.TEXT,"Nova kategorija");
		
		this.setFields(id_dnevne_evidencije, radnik_id, element_obracuna_id,
				datum, radno_vrijeme_od, radno_vrijeme_do, obracun_sati,
				obracun_minuta, korisnik, vrijeme_izmjene,
				organizacijska_jedinica_id, oznaka_maticne_evidencije, oib,
				dodatni_unos, id_spica_oj, napomena, napomena_spica, radni_dan,
				potvrda, potvrda_icon, datum_mj_prvi, datum_mj_zadnji,
				datum_od, datum_do,stara_sifra,nova_sifra);
	}
	
//	@Override
//	protected Record formatRecordFromServerJSON(Record record) {
//		if(record.getAttributeAsObject("naziv_privitka")!=null){
//			if(record.getAttribute("nacin_koristenja").contains("kupnja"))
//				record.setAttribute("nacin_koristenja_kupnja", true);
//			if(record.getAttribute("nacin_koristenja").contains("gotovina"))
//				record.setAttribute("nacin_koristenja_gotovina", true);
//		}
//		return super.formatRecordFromServerJSON(record);
//	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.evidencijadolazaka.facade.PlaceFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"dodajEvidenciju");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"dodajAzurirajEvidenciju");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"brisiEvidenciju");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveEvidencijePoOrgJed");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
