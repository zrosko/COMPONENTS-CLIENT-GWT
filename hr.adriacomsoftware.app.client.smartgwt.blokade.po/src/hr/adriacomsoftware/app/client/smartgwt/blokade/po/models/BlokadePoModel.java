package hr.adriacomsoftware.app.client.smartgwt.blokade.po.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

public class BlokadePoModel extends AS2RestJSONDataSource implements BlokadePoConstants {
	
	private static BlokadePoModel instance = new BlokadePoModel("BlokadePoModel");
	
	public static BlokadePoModel getInstance() {
		return instance;
	}
	
	public BlokadePoModel(String id) {
		super(id);
		AS2DataSourceField id_crm = new AS2DataSourceField("id_crm",AS2Field.PRIMARY_KEY,"Id");
		AS2DataSourceField naziv = new AS2DataSourceField(CRM201__NAZIV,AS2Field.TEXT,"Naziv");
		AS2DataSourceField maticni_broj = new AS2DataSourceField(CRM201__MATICNI_BROJ,AS2Field.TEXT,"Matični broj");
		AS2DataSourceField broj_partije = new AS2DataSourceField(CRM201__BROJ_PARTIJE,AS2Field.TEXT,"Broj partije");
		AS2DataSourceField broj_zaduznice = new AS2DataSourceField(CRM201__BROJ_ZADUZNICE,AS2Field.TEXT,"Broj zadužnice");
		AS2DataSourceField datum_blokade = new AS2DataSourceField(CRM201__DATUM_BLOKADE,AS2Field.TEXT,"Datum blokade");
		AS2DataSourceField iznos_blokade = new AS2DataSourceField(CRM201__IZNOS_BLOKADE,AS2Field.TEXT,"Iznos blokade");
		AS2DataSourceField datum_povlacenja_blokade = new AS2DataSourceField(CRM201__DATUM_POVLACENJA_BLOKADE,AS2Field.TEXT,"Datum povlačenja blokade");
		AS2DataSourceField iznos = new AS2DataSourceField(CRM201__IZNOS,AS2Field.TEXT,"Iznos");
		AS2DataSourceField datum_naplate = new AS2DataSourceField(CRM201__DATUM_NAPLATE,AS2Field.TEXT,"Datum naplate");
		AS2DataSourceField iznos_naplate = new AS2DataSourceField(CRM201__IZNOS_NAPLATE,AS2Field.TEXT,"Iznos naplate");
		AS2DataSourceField datum_deblokade = new AS2DataSourceField(CRM201__DATUM_DEBLOKADE,AS2Field.TEXT,"Datum deblokade");
		AS2DataSourceField pn = new AS2DataSourceField(CRM201__PN,AS2Field.TEXT,"Predstečajna");
		AS2DataSourceField datum_pn_od = new AS2DataSourceField(CRM201__DATUM_PN_OD,AS2Field.TEXT,"Datum PN od");
		AS2DataSourceField datum_pn_do = new AS2DataSourceField(CRM201__DATUM_PN_DO,AS2Field.TEXT,"Datum PN da");
		AS2DataSourceField napomena = new AS2DataSourceField(CRM201__NAPOMENA,AS2Field.TEXT,"Napomena");
		
		AS2DataSourceField podkategorija = new AS2DataSourceField(CRM201__PODKATEGORIJA,AS2Field.TEXT,"Podkategorija");
		
		this.setFields(id_crm,naziv, maticni_broj, broj_partije, broj_zaduznice,
				datum_blokade, iznos_blokade, datum_povlacenja_blokade, iznos,
				datum_naplate, iznos_naplate, datum_deblokade, pn, datum_pn_od,
				datum_pn_do, napomena, podkategorija);
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.crm.kontakt.facade.CrmKontaktFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.crm.kontakt.dto.KontaktPredmetVo";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
//		HashMap<String,String> params = new HashMap<String,String>();
//		params.put(AS2RestJSONDataSource.SERVICE,"dodajUpisnicu");
//		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
//		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
//		return params;
		return null;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
//		HashMap<String,String> params = new HashMap<String,String>();
//		params.put(AS2RestJSONDataSource.SERVICE,"azurirajUpisnicu");
//		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
//		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
//		return params;
		return null;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
//		HashMap<String,String> params = new HashMap<String,String>();
//		params.put(AS2RestJSONDataSource.SERVICE,"brisiUpisnicu");
//		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
//		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
//		return params;
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.REMOTE_METHOD,"pretraziKontaktPredmet");
		params.put(AS2RestJSONDataSource.REMOTE_OBJECT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		params.put("kategorija", "blokada_po_as2");
		return params;
	}
}
