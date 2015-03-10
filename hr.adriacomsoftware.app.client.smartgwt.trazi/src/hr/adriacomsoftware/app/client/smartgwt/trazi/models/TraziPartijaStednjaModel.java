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
public class TraziPartijaStednjaModel extends AS2RestJSONDataSource {
	
	private static TraziPartijaStednjaModel instance = new TraziPartijaStednjaModel("TraziPartijaStednjaModel");
	public static TraziPartijaStednjaModel getInstance() {
		return instance;
	}
	
	public TraziPartijaStednjaModel(String id) {
		super(id);
		AS2DataSourceField broj_partije =  new AS2DataSourceField("broj_partije",AS2Field.BIGINT,"Broj partije"/*,10*/);
		AS2DataSourceField saldo = new AS2DataSourceField("saldo",AS2Field.AMOUNT,"Saldo");
		AS2DataSourceField preporuceni_iznos = new AS2DataSourceField("preporuceni_iznos",AS2Field.AMOUNT,"Preporučeni iznos");
		this.setFields(broj_partije,saldo,preporuceni_iznos);
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
		params.put(REMOTE_METHOD,"procitajSvePartijeStednje");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}


