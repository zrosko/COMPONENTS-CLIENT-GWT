package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaOvlastiModel extends AS2RestJSONDataSource implements EvidencijaDolazakaConstants {
	
	private static EvidencijaOvlastiModel instance = new EvidencijaOvlastiModel("EvidencijaOvlastiModel");
//	private static String ID_VRSTE = "4";
	
	public static EvidencijaOvlastiModel getInstance() {
		return instance;
	}
	
	public EvidencijaOvlastiModel(String id) {
		super(id);
		AS2DataSourceField id_ovlasti = new AS2DataSourceField(EVIDENCIJA__ID_OVLASTI,AS2Field.PRIMARY_KEY,"Id ovlasti");
		AS2DataSourceField id_spica_oj = new AS2DataSourceField(EVIDENCIJA__ID_SPICA_OJ,AS2Field.TEXT,"Odjel");
		AS2DataSourceField radnik_id = new AS2DataSourceField(EVIDENCIJA__RADNIK_ID,AS2Field.TEXT,"Djelatnik");
		AS2DataSourceField korisnik = new AS2DataSourceField(EVIDENCIJA__KORISNIK,AS2Field.TEXT,"Korisnik");
		AS2DataSourceField vrijeme_izmjene = new AS2DataSourceField(EVIDENCIJA__VRIJEME_IZMJENE,AS2Field.DATETIME,"Vrijeme izmjene");
			
		this.setFields(id_ovlasti,id_spica_oj,radnik_id,korisnik,vrijeme_izmjene);
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
		params.put(REMOTE_METHOD,"dodajOvlasti");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"azurirajOvlasti");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"brisiOvlasti");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveOvlasti");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
