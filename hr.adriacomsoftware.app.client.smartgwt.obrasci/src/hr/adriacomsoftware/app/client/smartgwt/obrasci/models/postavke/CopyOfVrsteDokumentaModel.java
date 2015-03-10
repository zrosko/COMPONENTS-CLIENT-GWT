//package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke;
//
//import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTransportModel;
//import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
//import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
//
//import com.smartgwt.client.types.FieldType;
//
//public class CopyOfVrsteDokumentaModel extends AS2RestJSONDataSource {
//
//	private static CopyOfVrsteDokumentaModel  instance = new CopyOfVrsteDokumentaModel("VrsteDokumentaModel");
//
//
//    public static CopyOfVrsteDokumentaModel getInstance() {
//        return instance;
//    }
//
//    public CopyOfVrsteDokumentaModel(String id) {
//    	super(id,ObrasciTransportModel._defaultServletURL);
//		/******dok_vrsta*****/
//		//Id vrste
//    	AS2DataSourceField1 id_vrste = new AS2DataSourceField1("id_vrste","pk", "Id vrste");
//		this.addField(id_vrste);
//
//		//naziv
//		AS2DataSourceField naziv = new AS2DataSourceField("naziv","text", "Naziv");
//		this.addField(naziv);
//
//		//verzija
//		AS2DataSourceField verzija = new AS2DataSourceField("verzija", "text", "Verzija");
//		this.addField(verzija);
//
//		//opis
//		AS2DataSourceField opis = new AS2DataSourceField("opis","text", "Opis");
//		opis.setLength(2000);
//		this.addField(opis);
//
//		//datum_kreiranja
//		AS2DataSourceField datum_kreiranja = new AS2DataSourceField("datum_kreiranja","date", "Datum Kreiranja");
//		this.addField(datum_kreiranja);
//
//		//tip_dokumenta
//		AS2DataSourceField tip_dokumenta = new AS2DataSourceField("tip_dokumenta","","Tip");
//		tip_dokumenta.setCanFilter(false);
//		tip_dokumenta.setType(FieldType.IMAGE);
//		tip_dokumenta.setImageSize(32);
//		this.addField(tip_dokumenta);
//
//		//obrazac
//		AS2DataSourceField obrazac = new AS2DataSourceField("obrazac", "binary","Obrazac");
//		obrazac.setHidden(true);
//		this.addField(obrazac);
//
//		//lokacija
//		AS2DataSourceField lokacija = new AS2DataSourceField("lokacija","text", "Lokacija");
//		this.addField(lokacija);
//
//	}
//
//}
