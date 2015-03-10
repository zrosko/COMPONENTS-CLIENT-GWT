package hr.adriacomsoftware.app.client.smartgwt.obrasci;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciNavigationTreeGrid;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.stacksections.MojiObrasciStackSection;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class OContext {

//	public static TabSet _tabSet;
	public static VLayout _obrasciView;
	public static AS2GwtDesktop _obrasciDesktop;
	public static Tab _homeTab;
	public static ObrasciNavigationTreeGrid _obrasciNavigationTreeGrid;
//	public static Tree _obrasciTree;
//	public static HLayout _breadCrumbLabel;
	public static String URL="//192.168.202.63/public/Razmjena/Maja/obrasci";
	public static MojiObrasciStackSection _mojiObrasciStackSection;

	public static LinkedHashMap<String, Object>  _tipPodatkaValueMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("d", "d-date");
		map.put("t", "t-time");
		map.put("i", "i-intege");
		map.put("c", "c-character");
		map.put("f", "f-float");
		map.put("b", "b-boolean");
		return map;
	}
	public static String _tipPodatkaTooltip() {
			return "Tip podatka u bazi podataka: <br/>d-date<br/>t-time<br/>i-integer<br/>c-character<br/>f-float<br/>b-boolean";
	}
	public static LinkedHashMap<String, Object>  _uitipPodatkaValueMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("c", "c-checkbox");
		map.put("l", "l-listbox");
		map.put("n", "n-number");
		map.put("d", "d-date");
		map.put("t", "t-time");
		map.put("r", "r-radiobutton");
		map.put("p", "p-password");
		map.put("b", "s-string");
		return map;
	}
	public static String _uitipPodatkaTooltip() {
			return "Izgled polja na korisničkom sučelju: <br/>c-checkbox<br/>l-listbox<br/>n-number<br/>d-date<br/>t-time<br/>r-radiobutton<br/>p-password<br/>s-string";
	}
	public static LinkedHashMap<String, Object>  _obaveznoPoljeValueMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("d", "d-Da");
		map.put("n", "n-Ne");
		return map;
	}
	public static String _obaveznoPoljeTooltip() {
			return "Polje je obavezno: <br/>d-Da<br/>n-Ne";
	}
	public static String _izvorPodatakaTooltip() {
		return "Uglavnom za \"drop down\" vrste parametara navodimo izvor odakle se dohvaćaju podaci.<br/>" +
				"Primjer:<br/>" +
				"SQL=select oznaka_valute from bi_valuta<br/>" +
				"URL=http://portaltest:8080/korisnici";
}

//	public static void closeMojiObrasciIcon() {
//		OContext._mojiObrasciStackSection.setAttribute("expanded", true);
//	}

//	public static void setContextAreaViewVLayout(VLayout layout,String navigationPaneHeaderDisplayLabel) {
////		if(navigationPaneHeader!=null)
////			navigationPaneHeader.setContextAreaHeaderLabelContents(navigationPaneHeaderDisplayLabel);
//		AS2NavigationPaneHeader navigationPaneHeader = new AS2NavigationPaneHeader();
//		navigationPaneHeader.setContextAreaHeaderLabelContents(navigationPaneHeaderDisplayLabel);
//		Canvas.getById("AS2NavigationPaneHeader").addChild(navigationPaneHeader);
//		VLayout context = layout;
//		Canvas.getById("AS2ContextArea").addChild(context);
//
//	}

//	public static void setBreadCrumbs() {
//		if(ObrasciTransportModel._criteriaForServer!=null){
//			HLayout breadCrumbsLayout = new HLayout();
//			_breadCrumbLabel.setMembers(breadCrumbsLayout);
//		}
//
//	}

}
