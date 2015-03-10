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
public class ObracunKamateModel extends AS2RestJSONDataSource {
	
	private static ObracunKamateModel instance = new ObracunKamateModel("ObracunKamateModel");
	public static ObracunKamateModel getInstance() {
		return instance;
	}
	
	public ObracunKamateModel(String id) {
		super(id);
		//Forma
		AS2DataSourceField vrsta_obracuna = new AS2DataSourceField("vrsta_obracuna",AS2Field.TEXT, "Vrsta obračuna");
		AS2DataSourceField rok_otplate_jedinica = new AS2DataSourceField("rok_otplate_jedinica", AS2Field.TEXT,"Rok otplate jedinica");
		AS2DataSourceField iznos_za_obracun_prethodni = new AS2DataSourceField("iznos_za_obracun_prethodni", AS2Field.AMOUNT,"Prethodni iznos");
		AS2DataSourceField kamata_prethodna = new AS2DataSourceField("kamata_prethodna",AS2Field.AMOUNT_3,"Prethodna kamata");
		//Tablica
		AS2DataSourceField br_dokumenta = new AS2DataSourceField("br_dokumenta",AS2Field.TEXT,"Broj dokumenta");
		AS2DataSourceField glavnica = new AS2DataSourceField("glavnica",AS2Field.AMOUNT,"Glavnica");
		AS2DataSourceField iznos_za_obracun =  new AS2DataSourceField("iznos_za_obracun",AS2Field.AMOUNT,"Iznos za obračun");
		AS2DataSourceField datum_pocetak = new AS2DataSourceField("datum_pocetak",AS2Field.DATE,"Datum od");
		AS2DataSourceField datum_kraj =  new AS2DataSourceField("datum_kraj",AS2Field.DATE,"Datum do");
		AS2DataSourceField broj_dana =  new AS2DataSourceField("broj_dana",AS2Field.INTEGER,"Broj dana");
		AS2DataSourceField kamatna_stopa =  new AS2DataSourceField("kamatna_stopa",AS2Field.AMOUNT_3,"Kamatna stopa");
		AS2DataSourceField koeficijent = new AS2DataSourceField("koeficijent",AS2Field.AMOUNT_6,"Koeficijent");
		AS2DataSourceField kamata = new AS2DataSourceField("kamata", AS2Field.AMOUNT,"Kamata");
		this.setFields(vrsta_obracuna,rok_otplate_jedinica,iznos_za_obracun_prethodni,kamata_prethodna,br_dokumenta,glavnica,
				iznos_za_obracun,datum_pocetak,datum_kraj,broj_dana,kamatna_stopa,
				koeficijent,kamata);
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