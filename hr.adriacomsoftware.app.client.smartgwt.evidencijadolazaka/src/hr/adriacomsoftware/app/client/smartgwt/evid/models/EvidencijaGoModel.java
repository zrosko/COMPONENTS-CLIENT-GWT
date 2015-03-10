package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaGoModel extends AS2RestJSONDataSource implements EvidencijaGoConstants {
	
	private static EvidencijaGoModel instance = new EvidencijaGoModel("EvidencijaGoModel");
	
	public static EvidencijaGoModel getInstance() {
		return instance;
	}
	
	public EvidencijaGoModel(String id) {
		super(id);
		
		AS2DataSourceField id_godisnji = new AS2DataSourceField(EVIDENCIJAGO__ID_GODISNJI,AS2Field.PRIMARY_KEY,"id_godisnji");
		AS2DataSourceField radnik_id = new AS2DataSourceField(EVIDENCIJAGO__RADNIK_ID,AS2Field.INTEGER,"Djelatnik");
		AS2DataSourceField prezime_ime = new AS2DataSourceField(EVIDENCIJAGO__PREZIME_IME,AS2Field.TEXT,"Djelatnik");
		AS2DataSourceField ostatak_prethodna_godina = new AS2DataSourceField(EVIDENCIJAGO__OSTATAK_PRETHODNA_GODINA,AS2Field.INTEGER,"Ostatak prethodna godina");
		AS2DataSourceField pravo_tekuca_godina = new AS2DataSourceField(EVIDENCIJAGO__PRAVO_TEKUCA_GODINA,AS2Field.INTEGER,"Pravo tekuća godina");
		AS2DataSourceField ukupno_koristeno_u_godini = new AS2DataSourceField(EVIDENCIJAGO__UKUPNO_KORISTENO_U_GODINI,AS2Field.INTEGER,"Ukupno korišteno u godini");
		AS2DataSourceField potroseno_starog = new AS2DataSourceField(EVIDENCIJAGO__POTROSENO_STAROG,AS2Field.INTEGER,"Potršeno starog");
		AS2DataSourceField potroseno_novog = new AS2DataSourceField(EVIDENCIJAGO__POTROSENO_NOVOG,AS2Field.INTEGER,"Potrošeno novog");
		AS2DataSourceField ostatak_stari = new AS2DataSourceField(EVIDENCIJAGO__OSTATAK_STARI,AS2Field.INTEGER,"Ostatak stari");
		AS2DataSourceField ostatak_novi = new AS2DataSourceField(EVIDENCIJAGO__OSTATAK_NOVI,AS2Field.INTEGER,"Ostatak novi");
		AS2DataSourceField stari_potroseno_ostatak = new AS2DataSourceField(EVIDENCIJAGO__STARI_POTROSENO_OSTATAK,AS2Field.TEXT,"Stari (potrošeno / ostatak)");
		AS2DataSourceField novi_potroseno_ostatak = new AS2DataSourceField(EVIDENCIJAGO__NOVI_POTROSENO_OSTATAK,AS2Field.TEXT,"Novi (potrošeno / ostatak)");
		
		//ostalo
		AS2DataSourceField godina = new AS2DataSourceField(EVIDENCIJAGO__GODINA,AS2Field.INTEGER,"Godina");
		AS2DataSourceField id_spica_oj = new AS2DataSourceField(EVIDENCIJAGO__ID_SPICA_OJ,AS2Field.TEXT,"Odjel");
		
		//detalji po radniku
		AS2DataSourceField mj = new AS2DataSourceField(EVIDENCIJAGO__MJ,AS2Field.INTEGER,"Mj");
		AS2DataSourceField mjesec = new AS2DataSourceField(EVIDENCIJAGO__MJESEC,AS2Field.TEXT,"Mjesec");
		AS2DataSourceField dan_1 = new AS2DataSourceField(EVIDENCIJAGO__DAN_1,AS2Field.TEXT,"1");
		AS2DataSourceField dan_2 = new AS2DataSourceField(EVIDENCIJAGO__DAN_2,AS2Field.TEXT,"2");
		AS2DataSourceField dan_3 = new AS2DataSourceField(EVIDENCIJAGO__DAN_3,AS2Field.TEXT,"3");
		AS2DataSourceField dan_4 = new AS2DataSourceField(EVIDENCIJAGO__DAN_4,AS2Field.TEXT,"4");
		AS2DataSourceField dan_5 = new AS2DataSourceField(EVIDENCIJAGO__DAN_5,AS2Field.TEXT,"5");
		AS2DataSourceField dan_6 = new AS2DataSourceField(EVIDENCIJAGO__DAN_6,AS2Field.TEXT,"6");
		AS2DataSourceField dan_7 = new AS2DataSourceField(EVIDENCIJAGO__DAN_7,AS2Field.TEXT,"7");
		AS2DataSourceField dan_8 = new AS2DataSourceField(EVIDENCIJAGO__DAN_8,AS2Field.TEXT,"8");
		AS2DataSourceField dan_9 = new AS2DataSourceField(EVIDENCIJAGO__DAN_9,AS2Field.TEXT,"9");
		AS2DataSourceField dan_10 = new AS2DataSourceField(EVIDENCIJAGO__DAN_10,AS2Field.TEXT,"10");
		AS2DataSourceField dan_11 = new AS2DataSourceField(EVIDENCIJAGO__DAN_11,AS2Field.TEXT,"11");
		AS2DataSourceField dan_12 = new AS2DataSourceField(EVIDENCIJAGO__DAN_12,AS2Field.TEXT,"12");
		AS2DataSourceField dan_13 = new AS2DataSourceField(EVIDENCIJAGO__DAN_13,AS2Field.TEXT,"13");
		AS2DataSourceField dan_14 = new AS2DataSourceField(EVIDENCIJAGO__DAN_14,AS2Field.TEXT,"14");
		AS2DataSourceField dan_15 = new AS2DataSourceField(EVIDENCIJAGO__DAN_15,AS2Field.TEXT,"15");
		AS2DataSourceField dan_16 = new AS2DataSourceField(EVIDENCIJAGO__DAN_16,AS2Field.TEXT,"16");
		AS2DataSourceField dan_17 = new AS2DataSourceField(EVIDENCIJAGO__DAN_17,AS2Field.TEXT,"17");
		AS2DataSourceField dan_18 = new AS2DataSourceField(EVIDENCIJAGO__DAN_18,AS2Field.TEXT,"18");
		AS2DataSourceField dan_19 = new AS2DataSourceField(EVIDENCIJAGO__DAN_19,AS2Field.TEXT,"19");
		AS2DataSourceField dan_20 = new AS2DataSourceField(EVIDENCIJAGO__DAN_20,AS2Field.TEXT,"20");
		AS2DataSourceField dan_21 = new AS2DataSourceField(EVIDENCIJAGO__DAN_21,AS2Field.TEXT,"21");
		AS2DataSourceField dan_22 = new AS2DataSourceField(EVIDENCIJAGO__DAN_22,AS2Field.TEXT,"22");
		AS2DataSourceField dan_23 = new AS2DataSourceField(EVIDENCIJAGO__DAN_23,AS2Field.TEXT,"23");
		AS2DataSourceField dan_24 = new AS2DataSourceField(EVIDENCIJAGO__DAN_24,AS2Field.TEXT,"24");
		AS2DataSourceField dan_25 = new AS2DataSourceField(EVIDENCIJAGO__DAN_25,AS2Field.TEXT,"25");
		AS2DataSourceField dan_26 = new AS2DataSourceField(EVIDENCIJAGO__DAN_26,AS2Field.TEXT,"26");
		AS2DataSourceField dan_27 = new AS2DataSourceField(EVIDENCIJAGO__DAN_27,AS2Field.TEXT,"27");
		AS2DataSourceField dan_28 = new AS2DataSourceField(EVIDENCIJAGO__DAN_28,AS2Field.TEXT,"28");
		AS2DataSourceField dan_29 = new AS2DataSourceField(EVIDENCIJAGO__DAN_29,AS2Field.TEXT,"29");
		AS2DataSourceField dan_30 = new AS2DataSourceField(EVIDENCIJAGO__DAN_30,AS2Field.TEXT,"30");
		AS2DataSourceField dan_31 = new AS2DataSourceField(EVIDENCIJAGO__DAN_31,AS2Field.TEXT,"31");
		
		
		this.setFields(id_godisnji, radnik_id, prezime_ime,
				ostatak_prethodna_godina, pravo_tekuca_godina,
				ukupno_koristeno_u_godini, potroseno_starog, potroseno_novog,
				ostatak_stari, ostatak_novi, stari_potroseno_ostatak,
				novi_potroseno_ostatak, godina, id_spica_oj, mj, mjesec, dan_1,
				dan_2, dan_3, dan_4, dan_5, dan_6, dan_7, dan_8, dan_9, dan_10,
				dan_11, dan_12, dan_13, dan_14, dan_15, dan_16, dan_17, dan_18,
				dan_19, dan_20, dan_21, dan_22, dan_23, dan_24, dan_25, dan_26,
				dan_27, dan_28, dan_29, dan_30, dan_31);
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
		return null;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"dodajAzurirajGodisnjiOdmor");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveGodisnjeOdmore");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
