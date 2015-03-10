package hr.adriacomsoftware.app.client.smartgwt.portal.models;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.Record;

/**
 * @author msekso,astrikoman
 *
 */
public class PortalModel extends AS2RestJSONDataSource implements PortalConstants {
	
	private static PortalModel instance = new PortalModel("PortalModel");
	
	public static PortalModel getInstance() {
		return instance;
	}
	
	public PortalModel(String id) {
		super(id);
		AS2DataSourceField aplikacija = new AS2DataSourceField(PORTAL__APPLICATION,AS2Field.TEXT,"Aplikacija");
		aplikacija.getField().setPrimaryKey(true);
		AS2DataSourceField naziv = new AS2DataSourceField(PORTAL__NAZIV,AS2Field.TEXT,"Naziv");
		AS2DataSourceField url_test = new AS2DataSourceField(PORTAL__URL_TEST,AS2Field.TEXT,"Url test");
		AS2DataSourceField url_prod = new AS2DataSourceField(PORTAL__URL_PROD,AS2Field.TEXT,"Url prod");
		
		this.setFields(aplikacija,naziv,url_test,url_prod);
	}

	@Override
	protected Record formatRecordFromServerJSON(Record record) {
		record.setAttribute(PORTAL__APPLICATION, record.getAttribute(PORTAL__APPLICATION)+".png");
		return record;
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.portal.facade.PortalFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.security.user.AS2User";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajAplikacije");
		params.put(AS2ClientContext.AS2_USERNAME,AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME));
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
