/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaDolazakaSifrarnikModel extends AS2RestJSONDataSource implements EvidencijaDolazakaSifrarnikConstants{
	
	private static EvidencijaDolazakaSifrarnikModel instance = new EvidencijaDolazakaSifrarnikModel("EvidencijaDolazakaSifrarnikModel");
	public static EvidencijaDolazakaSifrarnikModel getInstance() {
		return instance;
	}
	
	public EvidencijaDolazakaSifrarnikModel(String id) {
		super(id);
		AS2DataSourceField id_sif = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__ID,AS2Field.PRIMARY_KEY, "Id");
		AS2DataSourceField vrsta = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__VRSTA,AS2Field.TEXT, "Vrsta");
		AS2DataSourceField rb = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__RB,AS2Field.INTEGER, "Rb");
		AS2DataSourceField id_sifre = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__ID_SIFRE,AS2Field.TEXT, "Id šifre");
		AS2DataSourceField naziv_sifre = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__NAZIV_SIFRE, AS2Field.TEXT,"Naziv šifre");
		AS2DataSourceField aplikacija = new AS2DataSourceField(EVIDENCIJA_SIFRARNIK__APLIKACIJA, AS2Field.TEXT,"Aplikacija");
		this.setFields(id_sif,vrsta,rb,id_sifre,naziv_sifre,aplikacija);
		this.setCacheAllData(true);///////BITNO
		this.setCacheMaxAge(24*60*60);///////BITNO
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
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"dodajPlaceSifrarnik");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"azurirajPlaceSifrarnik");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"brisiPlaceSifrarnik");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSifrarnik");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		params.put(APLIKACIJA,AS2ClientContext.getSessionValue(AS2ClientContext.APPLICATION_ID));
		return params;
	}
	
}


