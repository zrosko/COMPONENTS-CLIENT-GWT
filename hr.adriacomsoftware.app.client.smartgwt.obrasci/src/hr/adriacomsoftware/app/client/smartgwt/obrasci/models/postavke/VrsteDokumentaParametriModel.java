package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke;

import java.util.HashMap;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

public class VrsteDokumentaParametriModel extends AS2RestJSONDataSource {

	private static VrsteDokumentaParametriModel instance = new VrsteDokumentaParametriModel("VrsteDokumentaParametriModel");


    public static VrsteDokumentaParametriModel getInstance() {
        return instance;
    }

    public VrsteDokumentaParametriModel(String id) {
    	super(id);
    	this.setClientOnly(true);

		/*****dok_vrsta_parametar****/
    	//Id parametra
		AS2DataSourceField id_parametra = new AS2DataSourceField("id_parametra",AS2Field.PRIMARY_KEY, "Id parametra");
		AS2DataSourceField id_vrste = new AS2DataSourceField("id_vrste",AS2Field.INTEGER, "Id vrste");
		AS2DataSourceField redni_broj = new AS2DataSourceField("redni_broj",AS2Field.TEXT, "Redni broj");
		AS2DataSourceField parametar = new AS2DataSourceField("parametar",AS2Field.TEXT, "Naziv parametra");
		AS2DataSourceField naslov = new AS2DataSourceField("naslov",AS2Field.TEXT, "Naslov");
		AS2DataSourceField tip = new AS2DataSourceField("tip",AS2Field.COMBO, "Tip");
		AS2DataSourceField ui_tip = new AS2DataSourceField("ui_tip",AS2Field.COMBO, "UI tip");
		AS2DataSourceField obavezno = new AS2DataSourceField("obavezno",AS2Field.TEXT, "Obavezno");
		AS2DataSourceField izvor_podataka = new AS2DataSourceField("izvor_podataka",AS2Field.TEXT, "Izvor podataka");
		
		this.setFields(id_parametra,id_vrste,redni_broj,parametar,naslov,tip,ui_tip,obavezno,izvor_podataka);

	}

	@Override
	public String getRemoteObject() {
		return null;
	}

	@Override
	public String getTransformTo() {
		return null;
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
