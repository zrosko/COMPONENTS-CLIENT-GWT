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
public class McardGrZahtjevOcjenaModel extends AS2RestJSONDataSource implements McardGrZahtjevOcjenaConstants{
	
	public static final String REPORT_SERVER = McardGrZahtjevModel.REPORT_SERVER;
	private static McardGrZahtjevOcjenaModel instance = new McardGrZahtjevOcjenaModel("McardGrZahtjevOcjenaModel");
	public static McardGrZahtjevOcjenaModel getInstance() {
		return instance;
	}
	
	public McardGrZahtjevOcjenaModel(String id) {
		super(id);
		//polja iz tabele
		AS2DataSourceField id_ocjene = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__ID_OCJENE,AS2Field.PRIMARY_KEY, "Id ocjene");
		AS2DataSourceField broj_zahtjeva = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__BROJ_ZAHTJEVA,AS2Field.INTEGER,"Broj zahtjeva");
		AS2DataSourceField datum_ocjene = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__DATUM_OCJENE,AS2Field.DATE,"Datum ocjene");
		AS2DataSourceField pokazatelj = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__POKAZATELJ,AS2Field.INTEGER,"Pokazatelj");
		AS2DataSourceField naziv = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__NAZIV,AS2Field.TEXT,"Pokazatelj");
		AS2DataSourceField ponder = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__PONDER,AS2Field.AMOUNT,"Ponder");
		AS2DataSourceField vrsta = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__VRSTA,AS2Field.INTEGER,"Vrsta");
		AS2DataSourceField naziv_vrste = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__NAZIV_VRSTE,AS2Field.TEXT,"Naziv vrste");
		AS2DataSourceField ponder_vrste = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__PONDER_VRSTE,AS2Field.FLOAT,"Pond. ocjena");
		AS2DataSourceField ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__OCJENA,AS2Field.INTEGER,"Ocjena");
		AS2DataSourceField vrijednost_pokazatelja = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__VRIJEDNOST_POKAZATELJA,AS2Field.AMOUNT,"Vrijednost");
		
		//dodatna polja		
		AS2DataSourceField jmbg = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__JMBG,AS2Field.TEXT,"Jmbg",13);
		AS2DataSourceField oib = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__OIB,AS2Field.TEXT,"Oib",11);
		AS2DataSourceField naziv_klijenta = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__NAZIV_KLIJENTA,AS2Field.TEXT,"Naziv klijenta");
		AS2DataSourceField ponderirana_ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__PONDERIRANA_OCJENA,AS2Field.AMOUNT,"Ponderirana ocjena");
		AS2DataSourceField ukupno_ponder = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDER,AS2Field.AMOUNT,"Ukupno ponder");
		AS2DataSourceField ukupno_ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_OCJENA,AS2Field.AMOUNT,"Ukupno ocjena");
		AS2DataSourceField ukupno_ponderirana_ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__UKUPNO_PONDERIRANA_OCJENA,AS2Field.AMOUNT,"Ukupno ponderirana ocjena");
		AS2DataSourceField sveukupna_ocjena = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__SVEUKUPNA_OCJENA,AS2Field.AMOUNT,"Sveukupna ocjena");
		AS2DataSourceField rejting = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__REJTING,AS2Field.TEXT,"Rejting");
		AS2DataSourceField oznaka = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__OZNAKA,AS2Field.INTEGER,"Oznaka");
		AS2DataSourceField odluka = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__ODLUKA,AS2Field.TEXT,"Odluka");
		AS2DataSourceField isms_povjerljivost = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__ISMS_POVJERLJIVOST,AS2Field.TEXT,"Isms povjerljivost");
		AS2DataSourceField hrok_rate_kredita = new AS2DataSourceField(MCARD_GR_ZAHTJEV_OCJENA__HROK_RATE_KREDITA,AS2Field.AMOUNT,"Hrok rate kredita");
		
		
		this.setFields(id_ocjene,broj_zahtjeva,datum_ocjene,pokazatelj,naziv,ponder,vrsta,naziv_vrste,ponder_vrste,ocjena,vrijednost_pokazatelja,
				jmbg,oib,naziv_klijenta,ponderirana_ocjena,ukupno_ponder,ukupno_ocjena,ukupno_ponderirana_ocjena,sveukupna_ocjena,rejting,oznaka,odluka,isms_povjerljivost,hrok_rate_kredita);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.karticno.gr.facade.KarticnoFacadeServer";
	}


	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.karticno.gr.dto.McardGrZahtjevOcjenaVo";
	}

	
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajMcardGrZahtjevOcjena");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"azurirajMcardGrZahtjevOcjena");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"brisiMcardGrZahtjevOcjena");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajSveMcardGrZahtjevOcjena");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}


