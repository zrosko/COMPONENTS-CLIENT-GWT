package hr.adriacomsoftware.app.client.smartgwt.dionice.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author astrikoman
 *
 */
public class UpisnicaModel extends AS2RestJSONDataSource implements UpisnicaConstants {
	
	private static UpisnicaModel instance = new UpisnicaModel("UpisnicaModel");
	
	public static UpisnicaModel getInstance() {
		return instance;
	}
	
	public UpisnicaModel(String id) {
		super(id);
		AS2DataSourceField id_upisnica = new AS2DataSourceField(DION__ID_UPISNICA,AS2Field.PRIMARY_KEY,"id_upisnica");
		AS2DataSourceField id_ponude = new AS2DataSourceField(DION__ID_PONUDE,AS2Field.TEXT,"Krug");
		AS2DataSourceField broj_zaprimanja = new AS2DataSourceField(DION__BROJ_ZAPRIMANJA,AS2Field.INTEGER,"Broj zaprimanja");
		AS2DataSourceField racun_vlasnika = new AS2DataSourceField(DION__RACUN_VLASNIKA,AS2Field.INTEGER,"Račun vlasnika");
		AS2DataSourceField upisnik = new AS2DataSourceField(DION__UPISNIK,AS2Field.TEXT,"Upisnik");
		AS2DataSourceField vrsta_upisnika = new AS2DataSourceField(DION__VRSTA_UPISNIKA,AS2Field.TEXT,"Vrsta upisnika");
		AS2DataSourceField mbg_mb = new AS2DataSourceField(DION__MBG_MB,AS2Field.JMBG,"Mbg/Mb");
		AS2DataSourceField oib = new AS2DataSourceField(DION__OIB,AS2Field.OIB,"Oib");
		AS2DataSourceField adresa = new AS2DataSourceField(DION__ADRESA,AS2Field.TEXT,"Adresa");
		AS2DataSourceField kontak_osoba = new AS2DataSourceField(DION__KONTAKT_OSOBA,AS2Field.TEXT,"Kontakt osoba");
		AS2DataSourceField kontat_adresa = new AS2DataSourceField(DION__KONTAKT_ADRESA,AS2Field.TEXT,"Kontakt adresa");
		AS2DataSourceField mjesto = new AS2DataSourceField(DION__MJESTO,AS2Field.TEXT,"Mjesto");
		AS2DataSourceField postanski_broj = new AS2DataSourceField(DION__POSTANSKI_BROJ,AS2Field.TEXT,"Poštanski broj");
		AS2DataSourceField drzava = new AS2DataSourceField(DION__DRZAVA,AS2Field.TEXT,"Država");
		AS2DataSourceField telefon_fax = new AS2DataSourceField(DION__TELEFON_FAX,AS2Field.TEXT,"Telefon/fax");
		AS2DataSourceField mobitel = new AS2DataSourceField(DION__MOBITEL,AS2Field.TEXT,"Mobitel");
		AS2DataSourceField email = new AS2DataSourceField(DION__EMAIL,AS2Field.TEXT,"Email");
		AS2DataSourceField iban_upisnika = new AS2DataSourceField(DION__IBAN_UPISNIKA,AS2Field.IBAN,"IBAN");
		AS2DataSourceField banka_skrbnik_upisnika = new AS2DataSourceField(DION__BANKA_SKRBNIK_UPISNIKA,AS2Field.TEXT,"Banka skrbnik upisnika");
		AS2DataSourceField broj_novih_dionica = new AS2DataSourceField(DION__BROJ_NOVIH_DIONICA,AS2Field.AMOUNT_0,"Broj novih dionica");
		AS2DataSourceField datum_upisnice = new AS2DataSourceField(DION__DATUM_UPISNICE,AS2Field.DATE,"Datum upisnice");
		AS2DataSourceField datum_zaprimanja = new AS2DataSourceField(DION__DATUM_ZAPRIMANJA,AS2Field.DATE,"Datum zaprimanja");
		AS2DataSourceField vrijeme_uplate = new AS2DataSourceField(DION__VRIJEME_UPLATE,AS2Field.DATE,"Datum uplate");
		AS2DataSourceField iznos_uplate = new AS2DataSourceField(DION__IZNOS_UPLATE,AS2Field.AMOUNT,"Iznos uplate");
		AS2DataSourceField nominalna_vrijednost = new AS2DataSourceField(DION__NOMINALNA_VRIJEDNOST,AS2Field.AMOUNT,"Cijena nove dionice");
		AS2DataSourceField status_upisnice = new AS2DataSourceField(DION__STATUS_UPISNICE,AS2Field.TEXT,"Status upisnice");
		AS2DataSourceField broj_dionica_prije = new AS2DataSourceField(DION__BROJ_DIONICA_PRIJE,AS2Field.AMOUNT_0,"Dionice 11.8.2014.");
		AS2DataSourceField postotak_udjela = new AS2DataSourceField(DION__POSTOTAK_UDJELA,AS2Field.AMOUNT_4,"Post. udjela 11.8.2014.");
		AS2DataSourceField pravo_upisa = new AS2DataSourceField(DION__PRAVO_UPISA,AS2Field.AMOUNT_0,"Pravo upisa");
		AS2DataSourceField napomena = new AS2DataSourceField(DION__NAPOMENA,AS2Field.TEXT,"Napomena");
		AS2DataSourceField ispravno = new AS2DataSourceField(DION__ISPRAVNO,AS2Field.INTEGER,"Ispravno");
		//samo za grid
		AS2DataSourceField krug = new AS2DataSourceField(DION__KRUG,AS2Field.INTEGER,"Krug");//isti naziv ako i id_ponude-veza
		/* Javna ponuda */
		this.setFields(id_upisnica,id_ponude,krug,broj_zaprimanja,racun_vlasnika,upisnik,
				vrsta_upisnika,mbg_mb,oib,adresa,kontak_osoba,kontat_adresa,mjesto,postanski_broj,drzava,telefon_fax,
				mobitel,email,iban_upisnika,banka_skrbnik_upisnika,broj_novih_dionica,
				datum_upisnice,datum_zaprimanja,vrijeme_uplate,iznos_uplate,nominalna_vrijednost,
				status_upisnice,broj_dionica_prije,postotak_udjela,pravo_upisa,napomena,ispravno);
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
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajUpisnicu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"azurirajUpisnicu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"brisiUpisnicu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajSveUpisniceKruga");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}
