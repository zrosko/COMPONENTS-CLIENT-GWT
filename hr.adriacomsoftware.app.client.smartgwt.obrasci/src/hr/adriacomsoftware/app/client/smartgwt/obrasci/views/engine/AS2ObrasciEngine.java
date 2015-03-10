package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.engine;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciTreeNode;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

public class AS2ObrasciEngine {


	public AS2ObrasciEngine() {
	}

	public static ObrazacValueObject getObrazacFromServer(Record current) {
		current.getAttributes();
		ObrazacValueObject obrazacVo = new ObrazacValueObject();
		if (current.getAttribute(ObrazacValueObject.FILE_BYTE_ARRAY) != null) {
			Record.copyAttributesInto(obrazacVo, current,
					current.getAttributes());
		}
		return obrazacVo;
	}

	public static void serverCallBack(RecordList dsResponseData) {
		ObrazacValueObject obrazacVo = getObrazacFromServer(dsResponseData.get(0));
		if(ObrasciTransportModel._selectedObrazacTree!=null){
			ObrasciTransportModel._selectedObrazacTree.setAttribute("obrazacVo", obrazacVo);
		}
		if(obrazacVo.getAttribute("fileByteArray")!=null)
			AS2ObrasciEngine.showObrazac(obrazacVo);
	}

	//
	public static void showObrazac(ObrazacValueObject obrazacVo) {
		// OContext.setBreadCrumbs();
		showInHtmlPanel(obrazacVo);
//		showInTileGrid(obrazacVo);
		// ObrasciTransportModel._selectedObrazacTree.getTab().getTabSet()
		// .selectTab(ObrasciTransportModel._selectedObrazacTree.getTab());
	}

	/**
	 * Creates <iframe> and shows file from record (File is byte array)
	 *
	 * @param record
	 *            (from server) holds the file
	 * @return
	 */
	protected static void showInHtmlPanel(ObrazacValueObject obrazacVo) {
		HTMLPanel panel = new HTMLPanel("<iframe seamless='seamless' width='100%' height='100%' src='data:"
						+ obrazacVo.getAttribute("contentType")
						+ ";charset=UTF-8;base64,"
						+ obrazacVo.getAttribute("fileByteArray")
						+ "' frameborder='1' style='background-color:white;'></iframe>");

		if (obrazacVo.getAttribute("contentType") != null
				&& obrazacVo.getAttribute("contentType").contains("html")) {
			VLayout whiteVlayout = new VLayout();
			panel.setHeight("100%");
			panel.setWidth("100%");
			whiteVlayout.addMember(panel);
			OContext._obrasciDesktop.setContextAreaViewVLayout(whiteVlayout,((ObrasciTreeNode) ObrasciTransportModel._selectedObrazacTree).getName());
		} else {
			panel.setHeight("100%");
			panel.setWidth("100%");
			VLayout vlayout = new VLayout();
			vlayout.addMember(panel);
			vlayout.setWidth100();
			vlayout.setHeight100();
			vlayout.setLayoutRightMargin(10);
			obrazacVo.getAttributes();
			OContext._obrasciDesktop.setContextAreaViewVLayout(vlayout, obrazacVo.getAttribute("name"));
		}

	}
}
