package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaBuduceRazdobljeModel extends EvidencijaDolazakaModel implements EvidencijaDolazakaConstants {
	
	public static final String REPORT_SERVER = "hr.adriacomsoftware.app.server.evidencijadolazaka.reports.PlaceReportServer";	
	private static EvidencijaBuduceRazdobljeModel instance = new EvidencijaBuduceRazdobljeModel("EvidencijaBuduceRazdobljeModel");
	
	public static EvidencijaBuduceRazdobljeModel getInstance() {
		return instance;
	}
	
	public EvidencijaBuduceRazdobljeModel(String id) {
		super(id);
	}
	
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
		params.put(REMOTE_METHOD,"azurirajBuduceRazdoblje");
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
		params.put(REMOTE_METHOD,"procitajSveBuduceRazdoblje");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
