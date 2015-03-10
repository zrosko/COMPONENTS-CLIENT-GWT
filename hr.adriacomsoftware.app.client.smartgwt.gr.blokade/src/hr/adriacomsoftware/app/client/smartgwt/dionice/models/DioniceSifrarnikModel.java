/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.dionice.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso,astrikoman
 *
 */
public class DioniceSifrarnikModel extends AS2RestJSONDataSource implements DioniceSifrarnikConstants{
	
	private static DioniceSifrarnikModel instance = new DioniceSifrarnikModel("DioniceSifrarnikModel");
	public static DioniceSifrarnikModel getInstance() {
		return instance;
	}
	
	public DioniceSifrarnikModel(String id) {
		super(id);
	
		AS2DataSourceField id_sif = new AS2DataSourceField(DIO__SIFRARNIK__ID,AS2Field.PRIMARY_KEY, "Id");
		AS2DataSourceField vrsta = new AS2DataSourceField(DIO__SIFRARNIK__VRSTA,AS2Field.TEXT, "Vrsta");
		AS2DataSourceField rb = new AS2DataSourceField(DIO__SIFRARNIK__RB,AS2Field.INTEGER, "Rb");
		AS2DataSourceField id_sifre = new AS2DataSourceField(DIO__SIFRARNIK__ID_SIFRE,AS2Field.TEXT, "Id šifre");
		AS2DataSourceField naziv_sifre = new AS2DataSourceField(DIO__SIFRARNIK__NAZIV_SIFRE, AS2Field.TEXT,"Naziv šifre");
		AS2DataSourceField aplikacija = new AS2DataSourceField(DIO__SIFRARNIK__APLIKACIJA, AS2Field.TEXT,"Aplikacija");
		this.setFields(id_sif,vrsta,rb,id_sifre,naziv_sifre,aplikacija);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.dionice.facade.DioniceFacadeServer";
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
		params.put(REMOTE_METHOD,"procitajSifrarnik");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
	
}


