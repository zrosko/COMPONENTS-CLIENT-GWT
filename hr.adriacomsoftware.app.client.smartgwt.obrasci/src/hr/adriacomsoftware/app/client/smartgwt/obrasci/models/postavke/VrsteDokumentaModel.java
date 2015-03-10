package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.Record;

public class VrsteDokumentaModel extends AS2RestJSONDataSource {

	private static VrsteDokumentaModel  instance = new VrsteDokumentaModel("VrsteDokumentaModel");

    public static VrsteDokumentaModel getInstance() {
        return instance;
    }

    public VrsteDokumentaModel(String id) {
    	super(id);
		/******dok_vrsta*****/
		//Id vrste
    	AS2DataSourceField id_vrste = new AS2DataSourceField("id_vrste",AS2Field.PRIMARY_KEY, "Id vrste");
    	AS2DataSourceField naziv = new AS2DataSourceField("naziv",AS2Field.TEXT, "Naziv");
    	AS2DataSourceField verzija = new AS2DataSourceField("verzija", AS2Field.TEXT, "Verzija");
    	AS2DataSourceField opis = new AS2DataSourceField("opis",AS2Field.TEXTAREA, "Opis");
		opis.setLength(2000);
		AS2DataSourceField datum_kreiranja = new AS2DataSourceField("datum_kreiranja",AS2Field.DATE, "Datum Kreiranja");
		AS2DataSourceField tip_dokumenta = new AS2DataSourceField("tip_dokumenta",AS2Field.IMAGE,"Tip");
		tip_dokumenta.setCanFilter(false);
		tip_dokumenta.getField().setImageSize(32);
		AS2DataSourceField obrazac = new AS2DataSourceField("obrazac", AS2Field.BINARY,"Obrazac");
		obrazac.setHidden(true);
		AS2DataSourceField lokacija = new AS2DataSourceField("lokacija",AS2Field.TEXT, "Lokacija");
		this.setFields(id_vrste,naziv,verzija,opis,datum_kreiranja,tip_dokumenta,obrazac,lokacija);
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
    protected Record formatRecordFromServerJSON(Record record) {
    	record.setAttribute("tip_dokumenta", "pdf");
    	return record;
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
		params.put(REMOTE_METHOD,"procitajVrsteDokumenata");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
