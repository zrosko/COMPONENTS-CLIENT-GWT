package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

public class McardGrZahtjevPrivitakModel  extends AS2RestJSONDataSource implements McardGrZahtjevPrivitakConstants{

	private static McardGrZahtjevPrivitakModel instance =  new McardGrZahtjevPrivitakModel("McardGrZahtjevPrivitakModel");
	public static McardGrZahtjevPrivitakModel getInstance() {
		return instance;
	}
	
	public McardGrZahtjevPrivitakModel(String id) {
		super(id);
	
		AS2DataSourceField id_privitka = new AS2DataSourceField(MCARD_GR_ZAHTJEV_PRIVITAK__ID_PRIVITKA,AS2Field.PRIMARY_KEY, "Id Privitka");
		AS2DataSourceField broj_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV_PRIVITAK__BROJ_ZAHTJEVA,AS2Field.INTEGER, "Broj Zahtjeva");
		broj_zahtjeva.setHidden(true);
		AS2DataSourceField dokument = new AS2DataSourceField(MCARD_GR_ZAHTJEV_PRIVITAK__DOKUMENT, AS2Field.BINARY,"Dokument");
		AS2DataSourceField naziv_dokumenta = new AS2DataSourceField(MCARD_GR_ZAHTJEV_PRIVITAK__NAZIV_DOKUMENTA,AS2Field.TEXT,"Naziv Dokumenta");
		naziv_dokumenta.setHidden(true);
		AS2DataSourceField tip_dokumenta = new AS2DataSourceField(MCARD_GR_ZAHTJEV_PRIVITAK__TIP_DOKUMENTA, AS2Field.TEXT, "Tip");
		tip_dokumenta.setHidden(true);
		this.setFields(id_privitka,broj_zahtjeva,dokument,naziv_dokumenta,tip_dokumenta);
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.karticno.gr.dto.McardGrZahtjevPrivitakVo";
	}
	
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajPrivitak");
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
		params.put(AS2RestJSONDataSource.SERVICE,"brisiPrivitak");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajSvePrivitke");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}