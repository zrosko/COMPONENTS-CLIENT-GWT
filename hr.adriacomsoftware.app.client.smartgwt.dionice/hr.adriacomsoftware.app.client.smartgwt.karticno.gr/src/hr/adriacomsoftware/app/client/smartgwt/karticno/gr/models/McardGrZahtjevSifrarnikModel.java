/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso,astrikoman
 *
 */
public class McardGrZahtjevSifrarnikModel extends AS2RestJSONDataSource implements McardGrZahtjevSifrarnikConstants{
	
	private static McardGrZahtjevSifrarnikModel instance = new McardGrZahtjevSifrarnikModel("McardGrZahtjevSifrarnikModel");
	public static McardGrZahtjevSifrarnikModel getInstance() {
		return instance;
	}
	
	public McardGrZahtjevSifrarnikModel(String id) {
		super(id);
		AS2DataSourceField id_sif = new AS2DataSourceField(MCARD_GR_SIFRARNIK__ID,AS2Field.PRIMARY_KEY, "Id");
		AS2DataSourceField vrsta = new AS2DataSourceField(MCARD_GR_SIFRARNIK__VRSTA,AS2Field.TEXT, "Vrsta");
		AS2DataSourceField rb = new AS2DataSourceField(MCARD_GR_SIFRARNIK__RB,AS2Field.INTEGER, "Rb");
		AS2DataSourceField id_sifre = new AS2DataSourceField(MCARD_GR_SIFRARNIK__ID_SIFRE,AS2Field.TEXT, "Id šifre");
		AS2DataSourceField naziv_sifre = new AS2DataSourceField(MCARD_GR_SIFRARNIK__NAZIV_SIFRE, AS2Field.TEXT,"Naziv šifre");
		
		this.setFields(id_sif,vrsta,rb,id_sifre,naziv_sifre);
		this.setCacheAllData(true);///////BITNO
		this.setCacheMaxAge(24*60*60);///////BITNO
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.karticno.dto.McardSifrarnikVo";
	}
	
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajMcardGrSifrarnik");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"azurirajMcardGrSifrarnik");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"brisiMcardGrSifrarnik");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajMcardGrSifrarnik");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
	
}


