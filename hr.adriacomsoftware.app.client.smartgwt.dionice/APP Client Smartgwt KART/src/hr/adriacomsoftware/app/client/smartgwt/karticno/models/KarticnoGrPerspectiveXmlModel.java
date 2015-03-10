package hr.adriacomsoftware.app.client.smartgwt.karticno.models;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionDaoModel;

public class KarticnoGrPerspectiveXmlModel extends
		AS2NavigationPaneSectionDaoModel {
	
	public static final String DEFAULT_PERSPECTIVE_NAME = "zahtjevi";
	public static final String DEFAULT_PERSPECTIVE_DISPLAY_NAME = "Zahtjevi";
	public static final String DEFAULT_PERSPECTIVE_VIEW_DISPLAY_NAME = "Zahtjevi za MasterCard kreditnom karticom";
	
	private static KarticnoGrPerspectiveXmlModel instance = new KarticnoGrPerspectiveXmlModel("KarticnoGrPerspectiveXmlModel");
	public static KarticnoGrPerspectiveXmlModel getInstance() {
		return instance;
	}

	public KarticnoGrPerspectiveXmlModel(String id) {
		super(id);
	}

	public String getSectionName() {
		return "MasterCard GR";
	}

	@Override
	public String getDefaultPerspectiveName() {
		return DEFAULT_PERSPECTIVE_NAME;
	}
}
