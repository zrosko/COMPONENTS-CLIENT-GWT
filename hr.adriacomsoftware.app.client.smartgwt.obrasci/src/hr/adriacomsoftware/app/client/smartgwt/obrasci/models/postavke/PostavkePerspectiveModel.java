package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXmlModel;

public class PostavkePerspectiveModel extends AS2NavigationPaneSectionXmlModel {

	private static PostavkePerspectiveModel instance = new PostavkePerspectiveModel("PostavkePerspectiveModel");

	public static PostavkePerspectiveModel getInstance() {
		return instance;
	}

	public PostavkePerspectiveModel(String id) {
		super(id);
		DEFAULT_PERSPECTIVE_NAME = "administracija";
		DEFAULT_PERSPECTIVE_DISPLAY_NAME = "Administracija";
		// the name of the default ListGrid item to select
		// see NavigationPaneSectionListGrid -> onDataArrived()
		// see MainPageView -> initNavigationPane()
		setDataURL(URL_PREFIX + "PostavkePerspective", URL_SUFFIX);
	}

	public String getSectionName() {
		return "Postavke";
	}

	@Override
	public String getDefaultPerspectiveName() {
		return DEFAULT_PERSPECTIVE_NAME;
	}
}
