package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.stacksections;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTreeModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciNavigationTreeGrid;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ObrasciStackSection extends SectionStackSection {
	ObrasciNavigationTreeGrid obrasciNavigationTreeGrid;
	DynamicForm search;

	public ObrasciStackSection() {
		super("Obrasci");
		this.setID("obrasci");
		search = getSearchForm();
		obrasciNavigationTreeGrid = new ObrasciNavigationTreeGrid();
		OContext._obrasciNavigationTreeGrid = obrasciNavigationTreeGrid;
		this.addItem(search);
		this.addItem(obrasciNavigationTreeGrid);
		this.setExpanded(false);
	}

	private DynamicForm getSearchForm() {
		final SearchForm form = new SearchForm();
		form.setTop(50);
		form.setNumCols(3);
		PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						searchItemHandler(event.getItem().getValue().toString());

					}
				});
		final AS2FormItem search = new AS2FormItem("pretrazivanje",AS2Field.TEXT,
				"Pretraživanje", "*", searchPicker);
		search.getField().setTitleOrientation(TitleOrientation.TOP);
		search.getField().addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						searchItemHandler(event.getItem().getValue().toString());
					}
				}
			}
		});
		search.getField().setTooltip("Unesite naziv obrasca");
		form.setItems(search.getField());
		return form;
	}

	public static void searchItemHandler(String value) {
		TreeNode[] nodes = ObrasciTreeModel.getInstance().getAllNodes();
		List<TreeNode> foundNodes = new ArrayList<TreeNode>();
		for(TreeNode node: nodes){
			String name = node.getAttribute("name");
			if(name.toLowerCase().contains(value.toLowerCase())){
				foundNodes.add(node);
			}
		}
		if(foundNodes.size()==1){
			OContext._obrasciNavigationTreeGrid.deselectAllRecords();
			ObrasciTreeModel.getInstance().closeAll();
			TreeNode foundNode = foundNodes.get(0);
			expandNodes(foundNode);
			OContext._obrasciNavigationTreeGrid.selectRecord(foundNode);
		}else{
			OContext._obrasciNavigationTreeGrid.deselectAllRecords();
			ObrasciTreeModel.getInstance().closeAll();
			for(int i=0;i<foundNodes.size();i++){
				TreeNode foundNode = foundNodes.get(i);
				expandNodes(foundNode);
		}
		}

	}

	public static void expandNodes(TreeNode foundNode) {
//		for(TreeNode parent : OContext._obrasciTree.getParents(foundNode)){
//			OContext._obrasciTree.openFolder(parent);
//
//		}
		for(TreeNode parent : ObrasciTreeModel.getInstance().getParents(foundNode)){
			ObrasciTreeModel.getInstance().openFolder(parent);

		}

	}

//	private void expandNodes(TreeNode node) {
//		if(currentPath.contains("/")){
//			int index = currentPath.indexOf("/");
//			String newPath = (String) currentPath.subSequence(0,  index);
//			if ((ObrasciTreeNode) OContext._obrasciTree.find("name", newPath)!= null) {
//				TreeNode rec = (ObrasciTreeNode) OContext._obrasciTree.find("name", newPath);
//				OContext._obrasciTree.openFolder(rec);
//				openNodes((String) currentPath.subSequence(index+1,  originalPathLength),originalPathLength);
//			}
//		}else{
//			if ((ObrasciTreeNode) OContext._obrasciTree.find("name", currentPath)!= null) {
//				OContext._obrasciTree.openFolder((ObrasciTreeNode) OContext._obrasciTree.find("name", currentPath));
//			}
//		}
//
//
//
//	}

}
