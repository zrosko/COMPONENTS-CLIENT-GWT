package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ObrasciTreeNode extends TreeNode {

	public static final String ID="id";
	public static final String PARENT_ID="parentId";
	public static final String NAME="name";
	public static final String ICON="icon";
	public static final String SERVICE="Service";
	public static final String OBRASCI_RS="obrasciRs";
	public static final String OBRAZAC_VO="obrazacVo";
	public static final String TAB="tab";
	public static final String RAZINA="razina";
	public static final String FILE_TYPE="fileType";
	public static final String FILE_EXTENSION="fileExtension";
	public static final String FILE_PATH="filePath";

	public ObrasciTreeNode(Record obrazac) {
		Record.copyAttributesInto(this, obrazac, obrazac.getAttributes());
//this.setId(obrazac.getAttribute(ID));
//		this.setParentId(obrazac.getAttribute(PARENT_ID));
//		this.setName(obrazac.getAttribute(NAME));
//		this.setFileType(obrazac.getAttribute(FILE_TYPE));
//		this.setFileExtension(obrazac.getAttribute(FILE_EXTENSION));
//		this.setFilePath(obrazac.getAttribute(FILE_PATH));
//		this.setRazina(obrazac.getAttribute(RAZINA));
//		this.setService(obrazac);
		this.setIcon(obrazac);
//		this.setTab(obrazac.getAttribute(TAB));
//
//		this.setat

//		if (pokazatelj.getAttribute(ICON) != null&& !pokazatelj.getAttribute(ICON).equalsIgnoreCase("folder")) {
//			this.setIcon("./icons/graph/16/"+ pokazatelj.getAttribute(ICON) + ".png");
//		}


	}

	public void setId(String value) {
		setAttribute(ID, value);
	}
	public String getId() {
		return getAttributeAsString(ID);
	}
	public void setParentId(String value) {
		setAttribute(PARENT_ID, value);
	}
	public String getParentId() {
		return getAttributeAsString(PARENT_ID);
	}
	public void setName(String name) {
		setAttribute(NAME, name);
	}
	public String getName() {
		return getAttributeAsString(NAME);
	}
	public void setFileType(String file_type) {
		setAttribute(FILE_TYPE, file_type);
	}
	public String getFileType() {
		return getAttributeAsString(FILE_TYPE);
	}
	public void setFileExtension(String file_extension) {
		setAttribute(FILE_EXTENSION, file_extension);
	}
	public String getFileExtension() {
		return getAttributeAsString(FILE_EXTENSION);
	}
	public void setFilePath(String file_path) {
		setAttribute(FILE_PATH, file_path);
	}
	public String getFilePath() {
		return getAttributeAsString(FILE_PATH);
	}
	public void setService(String service) {
		setAttribute(SERVICE, service);
	}
	public void setService(Record obrazac) {
		if(obrazac.getAttribute(SERVICE)!=null)
			this.setService(obrazac.getAttribute(SERVICE));
	}
	public String getService() {
		if(getAttributeAsString(SERVICE)!=null)
			return getAttributeAsString(SERVICE);
		else
			return "";
	}

	public void setIcon(Record obrazac){
		if (obrazac.getAttribute(FILE_TYPE)!= null && !obrazac.getAttribute(FILE_TYPE).equalsIgnoreCase("folder")) {
			super.setIcon(AS2Resources.IMAGE_PATH + "/icons/file_extensions/s16/"+ obrazac.getAttribute(FILE_EXTENSION) + ".png");
		}
	}

	public void setTab(String tab) {
		setAttribute(TAB,tab);
	}
	public Tab getTab() {
		return OContext._homeTab;
	}
	public void setRazina(String razine) {
		setAttribute(RAZINA, razine);
	}

	public String  getRazina() {
		return getAttributeAsString(RAZINA);
	}

	public void setObrazacVo(Record obrazacVo) {
		setAttribute(OBRAZAC_VO, obrazacVo);
	}


}
