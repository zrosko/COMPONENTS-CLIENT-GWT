package hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXml;

public class PokazateljiNavigationSection extends AS2NavigationPaneSectionXml{

	private PokazateljiNavigationTreeGrid treeGrid;

	public PokazateljiNavigationSection() {
		super("Pokazatelji");
		
		treeGrid = new PokazateljiNavigationTreeGrid();
		
		this.addItem(treeGrid);
	}

}
