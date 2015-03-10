package hr.adriacomsoftware.app.client.smartgwt.pis.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author astrikoman
 *
 */
public class PisarnicaModel extends AS2RestJSONDataSource implements PisarnicaConstants {
	
	private static PisarnicaModel instance = new PisarnicaModel("PisarnicaModel");
//	private static String ID_VRSTE = "4";
	
	public static PisarnicaModel getInstance() {
		return instance;
	}
	
	public PisarnicaModel(String id) {
		super(id);
		System.out.println("100 ="+isCreated());
		AS2DataSourceField id_dokumenta = new AS2DataSourceField(PISARNICA_ID_DOKUMENTA,AS2Field.PRIMARY_KEY, "Broj dokumenta");
		id_dokumenta.setHidden(false);
		AS2DataSourceField id_vrste = new AS2DataSourceField(PISARNICA_ID_VRSTE,AS2Field.INTEGER, "Vrsta");
		id_vrste.setHidden(true);
//		AS2DataSourceField naziv_vrste = new AS2DataSourceField(URA_NAZIV_VRSTE,AS2Field.TEXT, "Vrsta");
		AS2DataSourceField oib = new AS2DataSourceField(PISARNICA_OIB,AS2Field.TEXT,"Oib");
		AS2DataSourceField iznos = new AS2DataSourceField(PISARNICA_IZNOS, AS2Field.AMOUNT,"Iznos računa");
		AS2DataSourceField datum =  new AS2DataSourceField(PISARNICA_DATUM_DOKUMENTA,AS2Field.DATE,"Datum");
		AS2DataSourceField organizacijska_jedinica = new AS2DataSourceField(PISARNICA_ORGANIZACIJSKA_JEDINICA,AS2Field.TEXT,"Organizacijska jedinica");
		AS2DataSourceField status_dokumenta =  new AS2DataSourceField(PISARNICA_STATUS_DOKUMENTA,AS2Field.TEXT,"Status dokumenta");
//		AS2DataSourceField privitak =  new AS2DataSourceField(PISARNICA_PRIVITAK,AS2Field.BINARY,"Privitak");
//		privitak.setHidden(true);
//		privitak.getField().setCanSave(false);
		AS2DataSourceField naziv_privitka = new AS2DataSourceField(PISARNICA_NAZIV_PRIVITKA,AS2Field.TEXT,"Privitak");
		AS2DataSourceField ulaz_izlaz =  new AS2DataSourceField(PISARNICA_ULAZ_IZLAZ,AS2Field.TEXT,"Ulaz/izlaz");
		AS2DataSourceField naziv_dokumenta = new AS2DataSourceField(PISARNICA_NAZIV_DOKUMENTA,AS2Field.TEXT,"Naziv");
		AS2DataSourceField ispravno = new AS2DataSourceField(PISARNICA_ISPRAVNO,AS2Field.INTEGER,"Ispravno");
		ispravno.setHidden(true);
		AS2DataSourceField ima_privitak = new AS2DataSourceField(PISARNICA_IMA_PRIVITAK,AS2Field.TEXT,"Privitak");
		AS2DataSourceField status_icon = new AS2DataSourceField(PISARNICA_STATUS_ICON,AS2Field.TEXTAREA,"Status icon");
		this.setFields(/*naziv_vrste,*/id_dokumenta,id_vrste,oib,iznos,datum,organizacijska_jedinica,status_dokumenta,/*privitak,*/ulaz_izlaz,naziv_privitka,naziv_dokumenta,ispravno,ima_privitak,status_icon);
		System.out.println("200 ="+isCreated());
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.obrasci.facade.DokumentFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"dodajDokument");
//		params.put(URA_ID_VRSTE,ID_VRSTE);
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"azurirajDokument");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"brisiDokument");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"procitajSveDokumentePoVrsti");
//		params.put(URA_ID_VRSTE,ID_VRSTE);
		
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}
