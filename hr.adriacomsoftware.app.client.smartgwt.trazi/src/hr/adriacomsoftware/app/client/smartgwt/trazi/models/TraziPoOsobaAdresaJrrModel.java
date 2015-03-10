/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.trazi.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class TraziPoOsobaAdresaJrrModel extends AS2RestJSONDataSource {
	
	private static TraziPoOsobaAdresaJrrModel instance = new TraziPoOsobaAdresaJrrModel("TraziPoOsobaAdresaJrrModel");
	public static TraziPoOsobaAdresaJrrModel getInstance() {
		return instance;
	}
	
	public TraziPoOsobaAdresaJrrModel(String id) {
		super(id);
		AS2DataSourceField oib =  new AS2DataSourceField("oib",AS2Field.TEXT,"OIB",11);
		AS2DataSourceField maticni_broj = new AS2DataSourceField("maticni_broj",AS2Field.TEXT,"Matični broj",8);
		AS2DataSourceField naziv = new AS2DataSourceField("naziv",AS2Field.TEXT,"Naziv");
		AS2DataSourceField ulica = new AS2DataSourceField("ulica",AS2Field.TEXT,"Ulica");
		AS2DataSourceField mjesto = new AS2DataSourceField("mjesto",AS2Field.TEXT,"Mjesto");
		AS2DataSourceField postanski_broj = new AS2DataSourceField("postanski_broj",AS2Field.TEXT,"Poštanski broj");
		this.setFields(oib,maticni_broj,naziv,ulica,mjesto,postanski_broj);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.pravneosobe.facade.PravnaOsobaFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.pravneosobe.dto.PravnaOsobaVo";
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
		params.put(REMOTE_METHOD,"pronadiPravnuOsobuJrrSimple");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		params.put("as2_smartgwt","true");
		return params;
	}
}


