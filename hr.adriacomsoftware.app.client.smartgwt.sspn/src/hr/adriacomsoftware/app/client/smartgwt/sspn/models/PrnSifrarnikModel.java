/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.sspn.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso,astrikoman
 *
 */
public class PrnSifrarnikModel extends AS2RestJSONDataSource implements PrnSifrarnikConstants {
	
	private static PrnSifrarnikModel instance = new PrnSifrarnikModel("PrnSifrarnikModel");
	
	public static PrnSifrarnikModel getInstance() {
		return instance;
	}
	
	public PrnSifrarnikModel(String id) {
		super(id);
			
		AS2DataSourceField id_sif = new AS2DataSourceField(PRN_SIFRARNIK__ID,AS2Field.PRIMARY_KEY, "Id");
		AS2DataSourceField vrsta = new AS2DataSourceField(PRN_SIFRARNIK__VRSTA,AS2Field.TEXT, "Vrsta");
		AS2DataSourceField rb = new AS2DataSourceField(PRN_SIFRARNIK__RB,AS2Field.INTEGER, "Rb");
		AS2DataSourceField id_sifre = new AS2DataSourceField(PRN_SIFRARNIK__ID_SIFRE,AS2Field.TEXT, "Id šifre");
		AS2DataSourceField naziv_sifre = new AS2DataSourceField(PRN_SIFRARNIK__NAZIV_SIFRE, AS2Field.TEXT,"Naziv šifre");
		
		this.setFields(id_sif,vrsta,rb,id_sifre,naziv_sifre);
		this.setCacheAllData(true);///////BITNO
		this.setCacheMaxAge(24*60*60);///////BITNO
		
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.pranjenovca.gr.facade.PranjeNovcaFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.adriacomsoftware.app.common.jb.dto.OsnovniVo";
	}

//	@Override
//	public void fetchData(Criteria crit, DSCallback callback) {
//		Criteria criteria = new Criteria();
//		if(crit!=null)
//			criteria.addCriteria(crit);
//		criteria.addCriteria("Service","procitajPrnSifre");
//		criteria.addCriteria("Component","hr.adriacomsoftware.app.server.pranjenovca.gr.facade.PranjeNovcaFacadeServer");
//		criteria.addCriteria("transform_to","hr.adriacomsoftware.app.common.jb.dto.OsnovniVo");
//		super.fetchData(criteria, callback);
//	}

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
		HashMap<String,String> params = new HashMap<String, String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajPrnSifre");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		params.put("izvor","prn_subjektivna_ocjena");
		return params;
	}



}
