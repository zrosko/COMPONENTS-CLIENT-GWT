/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.kalkulatori.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso,astrikoman
 *
 */
public class AnuitetiModel extends AS2RestJSONDataSource {
	
	private static AnuitetiModel instance = new AnuitetiModel("AnuitetiModel");
	public static AnuitetiModel getInstance() {
		return instance;
	}
	
	public AnuitetiModel(String id) {
		super(id);
		//Forma
		AS2DataSourceField anuiteti_vrsta = new AS2DataSourceField("anuiteti_vrsta",AS2Field.TEXT,"");
		AS2DataSourceField iznos_kredita = new AS2DataSourceField("iznos_kredita",AS2Field.AMOUNT,"Iznos kredita");
		AS2DataSourceField kamatna_stopa =  new AS2DataSourceField("kamatna_stopa",AS2Field.AMOUNT_3,"Kamatna stopa");
		AS2DataSourceField period_kamatne_stope = new AS2DataSourceField("period_kamatne_stope",AS2Field.TEXT,"");
		AS2DataSourceField rok_otplate =  new AS2DataSourceField("rok_otplate",AS2Field.INTEGER,"Rok otplate");
		AS2DataSourceField period_otplate =  new AS2DataSourceField("period_otplate",AS2Field.TEXT,"Period otplate");
		AS2DataSourceField nacin_otplate =  new AS2DataSourceField("nacin_otplate",AS2Field.COMBO,"Način otplate");
		AS2DataSourceField vrsta_obracuna = new AS2DataSourceField("vrsta_obracuna",AS2Field.TEXT,"Vrsta obračuna");
		//Tablica
		AS2DataSourceField broj_rate = new AS2DataSourceField("broj_rate",AS2Field.INTEGER, "Broj rate");
		AS2DataSourceField anuitet = new AS2DataSourceField("anuitet", AS2Field.AMOUNT,"Anuitet");
		AS2DataSourceField kamata = new AS2DataSourceField("kamata", AS2Field.AMOUNT,"Kamata");
		AS2DataSourceField otplata_glavnice = new AS2DataSourceField("otplata_glavnice",AS2Field.AMOUNT,"Otplata glavnice");
		AS2DataSourceField ostatak_duga = new AS2DataSourceField("ostatak_duga",AS2Field.AMOUNT,"Ostatak duga");
		AS2DataSourceField koeficijent = new AS2DataSourceField("koeficijent",AS2Field.AMOUNT_8,"Koeficijent");
		this.setFields(anuiteti_vrsta,iznos_kredita,kamatna_stopa,period_kamatne_stope,rok_otplate,period_otplate,nacin_otplate,vrsta_obracuna,broj_rate,
				anuitet,kamata,otplata_glavnice,ostatak_duga,koeficijent);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.kalkulatori.facade.KalkulatoriFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.kalkulatori.dto.FinancijskiKalkulatorVo";
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
		return null;
	}
}