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
public class TraziGrOsobaAdresaModel extends AS2RestJSONDataSource {
	
	private static TraziGrOsobaAdresaModel instance = new TraziGrOsobaAdresaModel("TraziGrOsobaAdresaModel");
	public static TraziGrOsobaAdresaModel getInstance() {
		return instance;
	}
	
	public TraziGrOsobaAdresaModel(String id) {
		super(id);
		AS2DataSourceField oib =  new AS2DataSourceField("oib",AS2Field.TEXT,"OIB",11);
		AS2DataSourceField jmbg_mb = new AS2DataSourceField("jmbg_mb",AS2Field.TEXT,"JMBG/MB",13);
		AS2DataSourceField ime_prezime_naziv = new AS2DataSourceField("ime_prezime_naziv",AS2Field.TEXT,"Ime i prezime");
		AS2DataSourceField ulica = new AS2DataSourceField("ulica",AS2Field.TEXT,"Ulica");
		AS2DataSourceField mjesto = new AS2DataSourceField("mjesto",AS2Field.TEXT,"Mjesto");
		AS2DataSourceField postanski_broj = new AS2DataSourceField("postanski_broj",AS2Field.TEXT,"Poštanski broj");
		AS2DataSourceField drzava = new AS2DataSourceField("drzava",AS2Field.TEXT,"Država");
		AS2DataSourceField telefon = new AS2DataSourceField("telefon",AS2Field.TEXT,"Telefon");
		AS2DataSourceField datum_rodenja = new AS2DataSourceField("datum_rodenja",AS2Field.DATE,"Datum rođenja");
		AS2DataSourceField ime_oca_majke = new AS2DataSourceField("ime_oca_majke",AS2Field.TEXT,"Ime oca/majke");
		AS2DataSourceField broj_partije = new AS2DataSourceField("broj_partije",AS2Field.TEXT,"Broj partije tekućeg");
		this.setFields(oib,jmbg_mb	,ime_prezime_naziv,ulica,mjesto,postanski_broj,drzava,telefon,datum_rodenja,ime_oca_majke,broj_partije);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.jb.facade.BankaFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.gradani.dto.OsobaVo";
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
		params.put(REMOTE_METHOD,"pronadiOsobu");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}


