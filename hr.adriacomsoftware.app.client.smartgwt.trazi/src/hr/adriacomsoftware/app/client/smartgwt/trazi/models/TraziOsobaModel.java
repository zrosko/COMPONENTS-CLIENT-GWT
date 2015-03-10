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
public class TraziOsobaModel extends AS2RestJSONDataSource {
	
	private static TraziOsobaModel instance = new TraziOsobaModel("TraziOsobaModel");
	public static TraziOsobaModel getInstance() {
		return instance;
	}
	
	public TraziOsobaModel(String id) {
		super(id);
		AS2DataSourceField oib =  new AS2DataSourceField("oib_",AS2Field.TEXT,"OIB",11);
		AS2DataSourceField jmbg_mb_ = new AS2DataSourceField("jmbg_mb_",AS2Field.TEXT,"JMBG/MB",13);
		AS2DataSourceField naziv = new AS2DataSourceField("naziv",AS2Field.TEXT,"Naziv / Prezime ime");
		AS2DataSourceField vrsta = new AS2DataSourceField("vrsta",AS2Field.COMBO,"Vrsta");
		vrsta.setValueMap("fizicka","pravna");
		this.setFields(oib,jmbg_mb_,naziv,vrsta);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.jb.facade.BankaFacadeServer";
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
		return null;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveOsobe");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}


