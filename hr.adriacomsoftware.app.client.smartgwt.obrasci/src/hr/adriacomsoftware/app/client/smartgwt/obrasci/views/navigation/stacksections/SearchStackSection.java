package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.stacksections;
//package hr.adriacomsoftware.app.client.smartgwt.obrasci.client.views.navigation.stacksections;
//
//import hr.adriacomsoftware.app.client.smartgwt.obrasci.client.OContext;
//import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.smartgwt.client.widgets.form.DynamicForm;
//import com.smartgwt.client.widgets.form.SearchForm;
//import com.smartgwt.client.widgets.form.fields.PickerIcon;
//import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
//import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
//import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
//import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
//import com.smartgwt.client.widgets.layout.SectionStackSection;
//import com.smartgwt.client.widgets.tree.TreeNode;
//
//public class SearchStackSection extends SectionStackSection {
//
//	DynamicForm searchForm;
//
//	public SearchStackSection() {
//		super("Pretraživanje");
//		this.setID("pretrazivanje");
//		searchForm = getDynamicForm();
//		this.addItem(searchForm);
//		this.setExpanded(true);
//	}
//
//	private DynamicForm getDynamicForm() {
//		final SearchForm form = new SearchForm();
//		form.setTop(50);
//		form.setNumCols(3);
//
//		PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH,
//				new FormItemClickHandler() {
//					public void onFormItemClick(FormItemIconClickEvent event) {
//						searchItemHandler(event.getItem().getValue().toString());
//
//					}
//				});
//		final AS2FormItem search = new AS2FormItem("pretrazivanje", "text",
//				"Pretraživanje", "*", searchPicker);
//		search.setShowTitle(false);
//		search.addKeyPressHandler(new KeyPressHandler() {
//
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if (event.getKeyName() != null) {
//					if (event.getKeyName().equals("Enter")) {
//						searchItemHandler(event.getItem().getValue().toString());
//					}
//				}
//			}
//		});
//		search.setTooltip("Unesite naziv obrasca");
//		form.setItems(search);
//
//		return form;
//	}
//
//	protected void searchItemHandler(String value) {
//		TreeNode[] nodes = OContext._obrasciTree.getAllNodes();
//		List<TreeNode> foundNodes = new ArrayList<TreeNode>();
//		for(TreeNode node: nodes){
//			String name = node.getAttribute("name");
//			if(name.toLowerCase().contains(value.toLowerCase())){
//				foundNodes.add(node);
//			}
//		}
//		if(foundNodes.size()==1){
//			OContext._obrasciNavigationTreeGrid.deselectAllRecords();
//			OContext._obrasciTree.closeAll();
//			TreeNode foundNode = foundNodes.get(0);
//			expandNodes(foundNode);
//			OContext._obrasciNavigationTreeGrid.selectRecord(foundNode);
//		}else{
//			OContext._obrasciNavigationTreeGrid.deselectAllRecords();
//			OContext._obrasciTree.closeAll();
//			for(int i=0;i<foundNodes.size();i++){
//
//				TreeNode foundNode = foundNodes.get(i);
//				expandNodes(foundNode);
//		}
//		}
//
//	}
//
//	private void expandNodes(TreeNode foundNode) {
//		for(TreeNode parent : OContext._obrasciTree.getParents(foundNode)){
//			OContext._obrasciTree.openFolder(parent);
//		}
//
//	}
//
////	private void expandNodes(TreeNode node) {
////		if(currentPath.contains("/")){
////			int index = currentPath.indexOf("/");
////			String newPath = (String) currentPath.subSequence(0,  index);
////			if ((ObrasciTreeNode) OContext._obrasciTree.find("name", newPath)!= null) {
////				TreeNode rec = (ObrasciTreeNode) OContext._obrasciTree.find("name", newPath);
////				OContext._obrasciTree.openFolder(rec);
////				openNodes((String) currentPath.subSequence(index+1,  originalPathLength),originalPathLength);
////			}
////		}else{
////			if ((ObrasciTreeNode) OContext._obrasciTree.find("name", currentPath)!= null) {
////				OContext._obrasciTree.openFolder((ObrasciTreeNode) OContext._obrasciTree.find("name", currentPath));
////			}
////		}
////
////
////
////	}
//
//}
