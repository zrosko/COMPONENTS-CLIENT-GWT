package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevPrivitakModel;
import hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadMWindow;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.HashMap;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;


public class McardGrZahtjevPrivitakMWindow extends AS2FileUploadMWindow implements McardGrZahtjevConstants{

	public McardGrZahtjevPrivitakMWindow(AS2View2 parent,Record record) {
		super(parent,record);
	}
	protected HashMap<String,Object> getUploadParameters(){
		HashMap<String,Object> params = new HashMap<>();
		params.put(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA, _record.getAttributeAsObject(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
		return params;
	}
	
	protected HashMap<String,Object> getDownloadParameters(){
		return null;
	}

	protected DataSource getListGridModel(){
		return McardGrZahtjevPrivitakModel.getInstance();
	}

	public String getWindowFormTitle() {
		return "<b style=\"color:black;font-size:10pt;\">Privici za zahtjev broj: "
				+ _record.getAttributeAsString("broj_zahtjeva")
				+ "</b><br>"
				+ _record.getAttributeAsString("ime_prezime");
	}
	@Override
	protected String getDownloadService() {
		return "citajPrivitak";
	}
	
	@Override
	protected String getUploadService() {
		return "dodajPrivitak";
	}

}
