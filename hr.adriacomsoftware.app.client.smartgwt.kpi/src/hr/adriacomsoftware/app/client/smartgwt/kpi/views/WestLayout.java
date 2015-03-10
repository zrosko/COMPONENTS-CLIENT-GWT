package hr.adriacomsoftware.app.client.smartgwt.kpi.views;


import hr.adriacomsoftware.app.client.smartgwt.kpi.views.navigation.PokazateljiNavigationTreeGrid;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class WestLayout extends VLayout {

    TreeGrid pokazateljiTreeGrid;

    public WestLayout() {
        this.setShowResizeBar(true);
        this.setStyleName("crm-NavigationPane");
        pokazateljiTreeGrid = new PokazateljiNavigationTreeGrid();
        this.addMember(pokazateljiTreeGrid);
    }






}
