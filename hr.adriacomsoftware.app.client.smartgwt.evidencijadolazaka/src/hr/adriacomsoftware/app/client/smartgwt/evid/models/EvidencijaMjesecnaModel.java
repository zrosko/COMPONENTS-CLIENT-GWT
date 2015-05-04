package hr.adriacomsoftware.app.client.smartgwt.evid.models;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaMjesecnaModel extends AS2RestJSONDataSource implements EvidencijaMjesecnaConstants {
	
	private static EvidencijaMjesecnaModel instance = new EvidencijaMjesecnaModel("EvidencijaMjesecnaModel");
	
	public static EvidencijaMjesecnaModel getInstance() {
		return instance;
	}
	
	public EvidencijaMjesecnaModel(String id) {
		super(id);
		AS2DataSourceField radnik_id = new AS2DataSourceField(EVIDMJ__RADNIK_ID,AS2Field.TEXT,"Djelatnik");
		radnik_id.getField().setPrimaryKey(true);
		AS2DataSourceField id_spica_oj = new AS2DataSourceField(EVIDMJ__ID_SPICA_OJ,AS2Field.TEXT,"Odjel");
		AS2DataSourceField naziv = new AS2DataSourceField(EVIDMJ__NAZIV,AS2Field.TEXT,"Djelatnik");
		AS2DataSourceField datum_od = new AS2DataSourceField(EVIDMJ__DATUM_OD,AS2Field.DATE,"Datum od");
		AS2DataSourceField datum_do = new AS2DataSourceField(EVIDMJ__DATUM_DO,AS2Field.DATE,"Datum do");
		AS2DataSourceField broj_dana_rada = new AS2DataSourceField(EVIDMJ__BROJ_DANA_RADA,AS2Field.INTEGER,"Uk.rad. dana");
		AS2DataSourceField ukupni_broj_sati = new AS2DataSourceField(EVIDMJ__UKUPNI_BROJ_SATI,AS2Field.INTEGER,"Uk.sati");
		AS2DataSourceField sif_01 = new AS2DataSourceField(EVIDMJ__SIF_01,AS2Field.INTEGER,"01");
		AS2DataSourceField sif_01_10 = new AS2DataSourceField(EVIDMJ__SIF_01_10,AS2Field.INTEGER,"01_10");
		AS2DataSourceField sif_03 = new AS2DataSourceField(EVIDMJ__SIF_03,AS2Field.INTEGER,"03");
		AS2DataSourceField sif_10 = new AS2DataSourceField(EVIDMJ__SIF_10,AS2Field.INTEGER,"10");
		AS2DataSourceField sif_12 = new AS2DataSourceField(EVIDMJ__SIF_12,AS2Field.INTEGER,"12");
		AS2DataSourceField sif_23 = new AS2DataSourceField(EVIDMJ__SIF_23,AS2Field.INTEGER,"23");
		AS2DataSourceField sif_09 = new AS2DataSourceField(EVIDMJ__SIF_09,AS2Field.INTEGER,"09");
		AS2DataSourceField sif_01_11 = new AS2DataSourceField(EVIDMJ__SIF_01_11,AS2Field.INTEGER,"01_11");
		AS2DataSourceField sif_01_12 = new AS2DataSourceField(EVIDMJ__SIF_01_12,AS2Field.INTEGER,"01_12");
		AS2DataSourceField sif_01_13 = new AS2DataSourceField(EVIDMJ__SIF_01_13,AS2Field.INTEGER,"01_13");
		AS2DataSourceField sif_27 = new AS2DataSourceField(EVIDMJ__SIF_27,AS2Field.INTEGER,"27");
		AS2DataSourceField sif_01_5 = new AS2DataSourceField(EVIDMJ__SIF_01_5,AS2Field.INTEGER,"01_5");
		AS2DataSourceField sif_02_2 = new AS2DataSourceField(EVIDMJ__SIF_02_2,AS2Field.INTEGER,"02_2");
		AS2DataSourceField sif_14_2 = new AS2DataSourceField(EVIDMJ__SIF_14_2,AS2Field.INTEGER,"14_2");
		AS2DataSourceField sif_14_1 = new AS2DataSourceField(EVIDMJ__SIF_14_1,AS2Field.INTEGER,"14_1");
		AS2DataSourceField sif_01_3 = new AS2DataSourceField(EVIDMJ__SIF_01_3,AS2Field.INTEGER,"01_3");
		AS2DataSourceField sif_15_2 = new AS2DataSourceField(EVIDMJ__SIF_15_2,AS2Field.INTEGER,"15_2");
		AS2DataSourceField sif_01_32 = new AS2DataSourceField(EVIDMJ__SIF_01_32,AS2Field.INTEGER,"01_32");
		AS2DataSourceField sif_01_30 = new AS2DataSourceField(EVIDMJ__SIF_01_30,AS2Field.INTEGER,"01_30");
		AS2DataSourceField sif_01_31 = new AS2DataSourceField(EVIDMJ__SIF_01_31,AS2Field.INTEGER,"01_31");
		AS2DataSourceField sif_14 = new AS2DataSourceField(EVIDMJ__SIF_14,AS2Field.INTEGER,"14");
		AS2DataSourceField sif_01_6 = new AS2DataSourceField(EVIDMJ__SIF_01_6,AS2Field.INTEGER,"01_6");
		AS2DataSourceField sif_15_1 = new AS2DataSourceField(EVIDMJ__SIF_15_1,AS2Field.INTEGER,"15_1");
		AS2DataSourceField sif_01_99 = new AS2DataSourceField(EVIDMJ__SIF_01_99,AS2Field.INTEGER,"01_99");
		AS2DataSourceField sif_12_1 = new AS2DataSourceField(EVIDMJ__SIF_12_1,AS2Field.INTEGER,"12_1");
		AS2DataSourceField sif_11 = new AS2DataSourceField(EVIDMJ__SIF_11,AS2Field.INTEGER,"11");
		AS2DataSourceField sif_15 = new AS2DataSourceField(EVIDMJ__SIF_15,AS2Field.INTEGER,"15");
		AS2DataSourceField sif_17 = new AS2DataSourceField(EVIDMJ__SIF_17,AS2Field.INTEGER,"17");
		AS2DataSourceField sif_22 = new AS2DataSourceField(EVIDMJ__SIF_22,AS2Field.INTEGER,"22");
		AS2DataSourceField sif_01_1 = new AS2DataSourceField(EVIDMJ__SIF_01_1,AS2Field.INTEGER,"01_1");
		AS2DataSourceField sif_01_2 = new AS2DataSourceField(EVIDMJ__SIF_01_2,AS2Field.INTEGER,"01_2");
		AS2DataSourceField sif_27_2 = new AS2DataSourceField(EVIDMJ__SIF_27_2,AS2Field.INTEGER,"27_2");
		AS2DataSourceField sif_03_2 = new AS2DataSourceField(EVIDMJ__SIF_03_2,AS2Field.INTEGER,"03_2");
		AS2DataSourceField sif_01_71 = new AS2DataSourceField(EVIDMJ__SIF_01_71,AS2Field.INTEGER,"01_71");
		AS2DataSourceField sif_01_72 = new AS2DataSourceField(EVIDMJ__SIF_01_72,AS2Field.INTEGER,"01_72");
		AS2DataSourceField sif_12_2 = new AS2DataSourceField(EVIDMJ__SIF_12_2,AS2Field.INTEGER,"12_2");
		AS2DataSourceField sif_18 = new AS2DataSourceField(EVIDMJ__SIF_18,AS2Field.INTEGER,"18");
		AS2DataSourceField sif_01_4 = new AS2DataSourceField(EVIDMJ__SIF_01_4,AS2Field.INTEGER,"01_4");
		AS2DataSourceField sif_12_3 = new AS2DataSourceField(EVIDMJ__SIF_12_3,AS2Field.INTEGER,"12_3");
		AS2DataSourceField sif_01_8 = new AS2DataSourceField(EVIDMJ__SIF_01_8,AS2Field.INTEGER,"01_8");
		AS2DataSourceField sif_15_0 = new AS2DataSourceField(EVIDMJ__SIF_15_0,AS2Field.INTEGER,"15_0");
		AS2DataSourceField sif_12_0 = new AS2DataSourceField(EVIDMJ__SIF_12_0,AS2Field.INTEGER,"12_0");
		AS2DataSourceField sif_12_4 = new AS2DataSourceField(EVIDMJ__SIF_12_4,AS2Field.INTEGER,"12_4");
		AS2DataSourceField sif_23_1 = new AS2DataSourceField(EVIDMJ__SIF_23_1,AS2Field.INTEGER,"23_1");
		AS2DataSourceField sif_23_0 = new AS2DataSourceField(EVIDMJ__SIF_23_0,AS2Field.INTEGER,"23_0");
		AS2DataSourceField sif_999 = new AS2DataSourceField(EVIDMJ__SIF_999,AS2Field.INTEGER,"999");
		AS2DataSourceField sati_redovni_i_praznici = new AS2DataSourceField(EVIDMJ__SATI_REDOVNI_I_PRAZNICI,AS2Field.TEXT,"Sati redovni / praznici");
		
		AS2DataSourceField mjesec = new AS2DataSourceField(EVIDMJ__MJESEC,AS2Field.TEXT,"Mjesec");
//		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//		for(int i = 1; i<=12; i++ ){
//			String s = "0"+Integer.toString(i);
//			String s2 = s.substring(s.length() - 2, s.length());
//			map.put(Integer.toString(i), s2);
//		}
//		mjesec.setValueMap(map);
		AS2DataSourceField godina = new AS2DataSourceField(EVIDMJ__GODINA,AS2Field.INTEGER,"Godina");
		
		this.setFields(radnik_id, id_spica_oj, naziv, datum_od, datum_do,
				broj_dana_rada, ukupni_broj_sati, sif_01, sif_01_10, sif_03,
				sif_10, sif_12, sif_23, sif_09, sif_01_11, sif_01_12,
				sif_01_13, sif_27, sif_01_5, sif_02_2, sif_14_2, sif_14_1,
				sif_01_3, sif_15_2, sif_01_32, sif_01_30, sif_01_31, sif_14,
				sif_01_6, sif_15_1, sif_01_99, sif_12_1, sif_11, sif_15,
				sif_17, sif_22, sif_01_1, sif_01_2, sif_27_2, sif_03_2,
				sif_01_71, sif_01_72, sif_12_2, sif_18, sif_01_4, sif_12_3,
				sif_01_8, sif_15_0, sif_12_0, sif_12_4, sif_23_1, sif_23_0,
				sif_999, sati_redovni_i_praznici, mjesec, godina);
	}
	
//	@Override
//	protected Record formatRecordFromServerJSON(Record record) {
//		if(record.getAttributeAsObject("naziv_privitka")!=null){
//			if(record.getAttribute("nacin_koristenja").contains("kupnja"))
//				record.setAttribute("nacin_koristenja_kupnja", true);
//			if(record.getAttribute("nacin_koristenja").contains("gotovina"))
//				record.setAttribute("nacin_koristenja_gotovina", true);
//		}
//		return super.formatRecordFromServerJSON(record);
//	}
	
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.evidencijadolazaka.facade.PlaceFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
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
		params.put(REMOTE_METHOD,"procitajSveEvidencijeMjesecno");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
