package hr.adriacomsoftware.app.client.smartgwt.obrasci.models;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciTreeNode;

import java.util.List;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class ObrasciTreeModel extends Tree {

	private static ObrasciTreeModel instance = new ObrasciTreeModel(
			"ObrasciTreeModel");

	public static ObrasciTreeModel getInstance() {
		return instance;
	}

	public ObrasciTreeModel(String id) {
		this.setID(id);
		this.setModelType(TreeModelType.PARENT);
		this.setNameProperty(ObrasciTreeNode.NAME);
		this.setIdField(ObrasciTreeNode.ID);
		this.setParentIdField(ObrasciTreeNode.PARENT_ID);
		this.setShowRoot(true);
		this.setRootValue("root");
	}

	public void setData(List<ObrasciTreeNode> nodes) {
		this.setData(nodes.toArray(new ObrasciTreeNode[nodes.size()]));
	}

}
