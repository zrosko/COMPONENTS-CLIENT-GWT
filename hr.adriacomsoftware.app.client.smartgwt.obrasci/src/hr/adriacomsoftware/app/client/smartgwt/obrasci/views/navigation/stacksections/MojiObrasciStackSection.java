package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.stacksections;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.mojiobrasci.MojiObrasciView;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;

import com.smartgwt.client.widgets.layout.SectionStackSection;

public class MojiObrasciStackSection extends SectionStackSection {

	public MojiObrasciStackSection() {
		super("Moji obrasci");
		this.setID("moji_obrasci_stacksection");
		this.setExpanded(false);
		this.setIcon(AS2Resources.IMAGE_PATH + "/treegrid/folder_blue.png");
		OContext._mojiObrasciStackSection = this;
	}
	public AS2View getView(){
		return new MojiObrasciView();
	}
	public String getViewHeaderLabel() {
		return "Moji obrasci";
	}
}
