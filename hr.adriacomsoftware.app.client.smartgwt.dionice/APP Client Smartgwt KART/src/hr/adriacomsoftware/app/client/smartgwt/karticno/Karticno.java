package hr.adriacomsoftware.app.client.smartgwt.karticno;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views.McardGrZahtjevView;
import hr.adriacomsoftware.app.client.smartgwt.karticno.models.KarticnoGrPerspectiveModel;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ContextArea;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2NavigationPane;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Karticno extends AS2GwtDesktop {


	@Override
	protected void getNavigationPaneSections(AS2NavigationPane navigationPane) {
//		navigationPane.addSection(KarticnoGrPerspectiveXmlModel.getInstance().getSectionName(),KarticnoGrPerspectiveXmlModel.getInstance());
		navigationPane.addSection(KarticnoGrPerspectiveModel.getInstance().getSectionName(),KarticnoGrPerspectiveModel.getInstance());
	}
	
	@Override
	protected List<DataSource> getDropDownCacheModels() {
		List<DataSource> list = new ArrayList<DataSource>();
		list.add(McardGrZahtjevSifrarnikModel.getInstance());
		return list;
	}

	@Override
    public String getNavigationPaneHeader(){
    	return "Jadranska Banka d.d.";
    }

	@Override
	protected Canvas getView(String name){
		if(name.equals("zahtjevi")){
			return new McardGrZahtjevView(name);
		}else
			return new AS2ContextArea();

	}
	
	@Override
	protected String getApplicationName() {
		return "Kartično poslovanje";
		
	}

	@Override
	protected String getApplicationId() {
		return "kart";
	}
//	protected void getSectionHeaderClickHandler(SectionHeaderClickEvent event) {
//		
//	}
	
//	@Override
//	protected String getDefaultViewName() {
//		return KarticnoGrPerspectiveXmlModel.DEFAULT_PERSPECTIVE_NAME;
//	}
//	
//	protected String getDefaultViewDisplayName(){
//		return KarticnoGrPerspectiveXmlModel.DEFAULT_PERSPECTIVE_DISPLAY_NAME;
//
//	}

	




}