package hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.AS2ContextAreaDataSource;

public class PostavkeContextAreaModel extends AS2ContextAreaDataSource {

	private static final String DATA_SOURCE = "PostavkeContextAreaModel";
	private static final String URL_PREFIX = "models/contextarea/Postavke";
	private static final String URL_SUFFIX = ".xml";

	private static PostavkeContextAreaModel instance = new PostavkeContextAreaModel(
			DATA_SOURCE);

	public static PostavkeContextAreaModel getInstance() {
		return instance;
	}

	public PostavkeContextAreaModel(String id) {
		super(id);
		setDataURL(URL_PREFIX, URL_SUFFIX);
	}
}
