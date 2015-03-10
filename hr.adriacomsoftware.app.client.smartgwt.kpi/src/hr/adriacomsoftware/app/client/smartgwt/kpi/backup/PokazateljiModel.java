package hr.adriacomsoftware.app.client.smartgwt.kpi.backup;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;

public class PokazateljiModel extends RestDataSource {

	private static PokazateljiModel instance = null;

	public static PokazateljiModel getInstance() {
		if (instance == null) {
			instance = new PokazateljiModel("PokazateljiModel");
		}
		return instance;
	}

	public PokazateljiModel(String id) {
		this.setDataFormat(DSDataFormat.JSON);
		this.setID(id);
		this.setDataURL(GWT.getModuleBaseURL() + "servletjson");
	}

}
