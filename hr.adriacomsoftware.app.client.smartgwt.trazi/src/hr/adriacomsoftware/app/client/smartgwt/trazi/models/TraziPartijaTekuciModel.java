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
public class TraziPartijaTekuciModel extends AS2RestJSONDataSource {
	
	private static TraziPartijaTekuciModel instance = new TraziPartijaTekuciModel("TraziPartijaTekuciModel");
	public static TraziPartijaTekuciModel getInstance() {
		return instance;
	}
	
	public TraziPartijaTekuciModel(String id) {
		super(id);
		AS2DataSourceField broj_partije_tekuceg =  new AS2DataSourceField("broj_partije_tekuceg",AS2Field.TEXT,"Broj partije tekućeg");
		broj_partije_tekuceg.setWidth("*");
		this.setFields(broj_partije_tekuceg);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.jb.dto.OsnovniVo";
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
		params.put(REMOTE_METHOD,"procitajPartijeTekuci");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}


