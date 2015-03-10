package hr.adriacomsoftware.app.client.smartgwt.obrtnici.models;

import hr.adriacomsoftware.app.common.obrtnici.ObrtniciConstants;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author astrikoman
 *
 */
public class ObrtniciModel extends AS2RestJSONDataSource implements ObrtniciConstants {
	
	private static ObrtniciModel instance = new ObrtniciModel("ObrtniciModel");
	
	public static ObrtniciModel getInstance() {
		return instance;
	}
	
	public ObrtniciModel(String id) {
		super(id);
		AS2DataSourceField maticni_broj = new AS2DataSourceField(OBRT__MATICNI_BROJ,AS2Field.INTEGER,"Matični broj");
		AS2DataSourceField naziv = new AS2DataSourceField(OBRT__NAZIV,AS2Field.TEXT,"Naziv");
		AS2DataSourceField mjesto = new AS2DataSourceField(OBRT__MJESTO,AS2Field.TEXT,"Mjesto");
		AS2DataSourceField telefon = new AS2DataSourceField(OBRT__TELEFON,AS2Field.TEXT,"Telefon");
		AS2DataSourceField godina_pocetka_poslovanja = new AS2DataSourceField(OBRT__GODINA_POCETKA_POSLOVANJA,AS2Field.INTEGER,"Godina početka poslovanja");
		AS2DataSourceField ocjena_ukupna = new AS2DataSourceField(OBRT__OCJENA_UKUPNA,AS2Field.TEXT,"Ocjena ukupna");
		AS2DataSourceField oib = new AS2DataSourceField(OBRT__OIB,AS2Field.OIB,"Oib");
		this.setFields(maticni_broj,naziv,mjesto,telefon,godina_pocetka_poslovanja,ocjena_ukupna,oib);
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.obrtnici.facade.ObrtnikFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.pravneosobe.dto.PravnaOsobaVo";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"dodajObrtnika");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"azurirajObrtnika");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"brisiObrtnika");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"citajSveObrtnike");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}
