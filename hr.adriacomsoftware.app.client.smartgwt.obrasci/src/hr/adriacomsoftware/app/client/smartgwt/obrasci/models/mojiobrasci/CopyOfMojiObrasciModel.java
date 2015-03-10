//package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.mojiobrasci;
//
//import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTransportModel;
//import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
//import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
//import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
//import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField1;
//import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ItemFactory;
//
//import java.util.HashMap;
//
//import com.smartgwt.client.types.FieldType;
//
//public class CopyOfMojiObrasciModel extends AS2RestJSONDataSource {
//
//	private static CopyOfMojiObrasciModel instance = new CopyOfMojiObrasciModel("MojiObrasciModel");
//	
//    public static CopyOfMojiObrasciModel getInstance() {
//        return instance;
//    }
//
//    public CopyOfMojiObrasciModel(String id) {
//    	super(id,ObrasciTransportModel._defaultServletURL);
//		//Id dokumenta
//    	AS2DataSourceField1 id_dokumenta = new AS2DataSourceField1("id_dokumenta",AS2ItemFactory.PRIMARY_KEY, "Id dokumenta");
//    	AS2DataSourceField1 id_vrste = new AS2DataSourceField1("id_vrste",AS2ItemFactory.INTEGER, "Id vrste");
//		id_vrste.setHidden(true);
//		AS2DataSourceField1 oib =  new AS2DataSourceField1("oib",AS2ItemFactory.TEXT,"OIB",11,true);
//		//broj_partije
//		AS2DataSourceField broj_partije = new AS2DataSourceField("broj_partije","integer", "Broj partije");
//		this.addField(broj_partije);
//
//		//datum_kreiranja
//		AS2DataSourceField datum_kreiranja = new AS2DataSourceField("datum_kreiranja","date", "Datum Kreiranja");
////		datum_kreiranja.setLength(75);
//		this.addField(datum_kreiranja);
//
//		//korisnik
//		AS2DataSourceField korisnik = new AS2DataSourceField("korisnik","text", "Korisnik");
////		korisnik.setLength(100);
//		this.addField(korisnik);
//
//		//naziv
//		AS2DataSourceField naziv = new AS2DataSourceField("naziv","text", "Naziv");
//		this.addField(naziv);
//
//		//opis
//		AS2DataSourceField opis = new AS2DataSourceField("opis","text", "Opis");
//		opis.setLength(2000);
//		this.addField(opis);
//
//		//tip_klijenta
//		AS2DataSourceField tip_klijenta = new AS2DataSourceField("tip_klijenta","combo", "Tip klijenta");
//		tip_klijenta.setHidden(true);
//		this.addField(tip_klijenta);
//
//		// profitni_centar
//		AS2DataSourceField profitni_centar = new AS2DataSourceField("profitni_centar", "combo", "Profitni centar");
////		profitni_centar.setLength(75);
//		profitni_centar.setValueMap(AS2ClientContext.getProfitniCentriValueMap());
//		this.addField(profitni_centar);
//
//		// organizacijska_jedinica
//		AS2DataSourceField organizacijska_jedinica = new AS2DataSourceField("organizacijska_jedinica", "text", "Organizacijska jedinica");
////		profitni_centar.setLength(75);
//		this.addField(organizacijska_jedinica);
//
//		// verzija
//		AS2DataSourceField verzija = new AS2DataSourceField("verzija", "text", "Verzija");
//		this.addField(verzija);
//
//		//tip_dokumenta
//		AS2DataSourceField tip_dokumenta = new AS2DataSourceField("tip_dokumenta","","Tip");
//		tip_dokumenta.setCanFilter(false);
//		tip_dokumenta.setType(FieldType.IMAGE);
////		tip_dokumenta.setLength(50);
//		tip_dokumenta.setImageSize(16);
//		this.addField(tip_dokumenta);
//
////		this.setCacheAllData(true);
////		this.setClientOnly(true);
//
//	}
//
//	@Override
//	protected HashMap<String, String> getCustomOperationProperties(String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected HashMap<String, String> getAddOperationProperties() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected HashMap<String, String> getUpdateOperationProperties() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected HashMap<String, String> getDeleteOperationProperties() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected HashMap<String, String> getFetchOperationProperties() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
