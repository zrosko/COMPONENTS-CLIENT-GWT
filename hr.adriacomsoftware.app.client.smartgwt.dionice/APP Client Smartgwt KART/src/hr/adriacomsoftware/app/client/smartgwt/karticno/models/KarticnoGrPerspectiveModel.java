package hr.adriacomsoftware.app.client.smartgwt.karticno.models;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionDaoModel;

public class KarticnoGrPerspectiveModel extends
		AS2NavigationPaneSectionDaoModel {
	
	public static final String DEFAULT_PERSPECTIVE_NAME = "zahtjevi";
	public static final String DEFAULT_PERSPECTIVE_DISPLAY_NAME = "MasterCard GR zahtjevi";
	public static final String DEFAULT_PERSPECTIVE_VIEW_DISPLAY_NAME = "Zahtjevi za MasterCard kreditnom karticom";
	
	private static KarticnoGrPerspectiveModel instance = new KarticnoGrPerspectiveModel("KarticnoGrPerspectiveModel");
	public static KarticnoGrPerspectiveModel getInstance() {
		return instance;
	}

	public KarticnoGrPerspectiveModel(String id) {
		super(id);
	}

	public String getSectionName() {
		return DEFAULT_PERSPECTIVE_DISPLAY_NAME;
	}

	@Override
	public String getDefaultPerspectiveName() {
		return DEFAULT_PERSPECTIVE_NAME;
	}
}
