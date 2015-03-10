package hr.adriacomsoftware.app.client.smartgwt.portal.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso,astrikoman
 *
 */
public class PortalObavijestiModel extends AS2RestJSONDataSource implements PortalObavijestiConstants {
	
	private static PortalObavijestiModel instance = new PortalObavijestiModel("PortalObavijestiModel");
	
	public static PortalObavijestiModel getInstance() {
		return instance;
	}
	
	public PortalObavijestiModel(String id) {
		super(id);
		AS2DataSourceField id_obavijesti = new AS2DataSourceField(PORTAL__ID_OBAVIJESTI,AS2Field.PRIMARY_KEY,"Id obavijesti");
		AS2DataSourceField obavijest = new AS2DataSourceField(PORTAL__OBAVIJEST,AS2Field.TEXTAREA,"Obavijest");
		AS2DataSourceField obavijest_html = new AS2DataSourceField(PORTAL__OBAVIJEST_HTML,AS2Field.TEXT,"Obavijest html");
		AS2DataSourceField vrijeme_izmjene = new AS2DataSourceField(PORTAL__VRIJEME_IZMJENE,AS2Field.DATETIME,"Vrijeme izmjene");
		AS2DataSourceField korisnik = new AS2DataSourceField(PORTAL__KORISNIK,AS2Field.TEXT,"Korisnik");
		AS2DataSourceField ispravno = new AS2DataSourceField(PORTAL__ISPRAVNO,AS2Field.INTEGER,"Ispravno");
		AS2DataSourceField korisnik_vrijeme = new AS2DataSourceField(PORTAL__KORISNIK_VRIJEME,AS2Field.TEXT,"korisnik_vrijeme");
		
		this.setFields(id_obavijesti,obavijest,obavijest_html,vrijeme_izmjene,korisnik,ispravno,korisnik_vrijeme);
	}

	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.portal.facade.PortalFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"dodajObavijest");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"azurirajObavijest");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"brisiObavijest");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveObavijesti");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
