package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.mojiobrasci;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

public class MojiObrasciModel extends AS2RestJSONDataSource {

	private static MojiObrasciModel instance = new MojiObrasciModel("MojiObrasciModel");
	
    public static MojiObrasciModel getInstance() {
        return instance;
    }
    
    public MojiObrasciModel(String id) {
    	super(id);
    	AS2DataSourceField id_dokumenta = new AS2DataSourceField("id_dokumenta",AS2Field.PRIMARY_KEY, "Id dokumenta");
    	AS2DataSourceField id_vrste = new AS2DataSourceField("id_vrste",AS2Field.INTEGER, "Id vrste");
		id_vrste.setHidden(true);
		AS2DataSourceField oib =  new AS2DataSourceField("oib",AS2Field.TEXT,"OIB",11);
		AS2DataSourceField broj_partije = new AS2DataSourceField("broj_partije",AS2Field.TEXT, "Broj partije");
		AS2DataSourceField datum_kreiranja = new AS2DataSourceField("datum_kreiranja",AS2Field.DATE, "Datum Kreiranja");
		AS2DataSourceField korisnik = new AS2DataSourceField("korisnik",AS2Field.TEXT, "Korisnik");
		AS2DataSourceField naziv = new AS2DataSourceField("naziv",AS2Field.TEXT, "Naziv");
		AS2DataSourceField opis = new AS2DataSourceField("opis",AS2Field.TEXTAREA, "Opis");
		opis.setLength(2000);
		AS2DataSourceField tip_klijenta = new AS2DataSourceField("tip_klijenta",AS2Field.COMBO, "Tip klijenta");
		tip_klijenta.setHidden(true);
		AS2DataSourceField profitni_centar = new AS2DataSourceField("profitni_centar", AS2Field.COMBO, "Profitni centar");
		profitni_centar.setValueMap(AS2ClientContext.getProfitniCentriValueMap());
		AS2DataSourceField organizacijska_jedinica = new AS2DataSourceField("organizacijska_jedinica", AS2Field.TEXT, "Organizacijska jedinica");
		AS2DataSourceField verzija = new AS2DataSourceField("verzija", AS2Field.TEXT, "Verzija");
		AS2DataSourceField tip_dokumenta = new AS2DataSourceField("tip_dokumenta", AS2Field.IMAGE,"Tip");
		tip_dokumenta.setCanFilter(false);
		tip_dokumenta.getField().setImageSize(16);
		this.setFields(id_dokumenta,id_vrste,oib,broj_partije,datum_kreiranja,korisnik,naziv,opis,tip_klijenta,
				profitni_centar,organizacijska_jedinica,verzija,tip_dokumenta);
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
	public HashMap<String, String> getDefaultProperties(){
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(APLIKACIJA,"obr");
		return params;
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
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"brisiFormu");
		params.put("service","brisiMojiObrasci");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"procitajSveDokumenteKorisnika");
		params.put("service","procitajSveDokumenteKorisnika");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
