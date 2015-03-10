package hr.adriacomsoftware.app.client.smartgwt.obrasci.views;


import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.views.navigation.ObrasciNavigationTreeGrid;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class WestLayout extends VLayout {

    ObrasciNavigationTreeGrid obrasciNavigationTreeGrid;
    DynamicForm searchForm;

    public WestLayout() {
//    	   this.setShowResizeBar(true);
        this.setStyleName("crm-NavigationPane");
        searchForm = getDynamicForm();
        obrasciNavigationTreeGrid = new ObrasciNavigationTreeGrid();
        OContext._obrasciNavigationTreeGrid = obrasciNavigationTreeGrid;

        this.setMembers(searchForm,obrasciNavigationTreeGrid);
    }

	private DynamicForm getDynamicForm() {
		final SearchForm form = new SearchForm();
        form.setTop(50);
        form.setNumCols(3);

        PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
            public void onFormItemClick(FormItemIconClickEvent event) {
//            	Record record = OContext._obrasciTree.find(event.getItem().getValue().toString());
            	searchItemHandler(event.getItem().getValue().toString());
//               data = OContext._obrasciNavigationTreeGrid.getDataAsRecordList().findAll(form.getValuesAsAdvancedCriteria());
//              RecordList dat = new RecordList();
//               for(Record record : data){
//            	   dat.add(record);
//            	if(record!=null){
//            	   for(String attribute:record.getAttributes()){
//            		   System.out.println("RECORD : " + record.getAttribute(attribute));
//            	   }
////
//               }
//               OContext._obrasciNavigationTreeGrid.setData(dat);
            }
        });
        final AS2FormItem search = new AS2FormItem("pretrazivanje", AS2Field.TEXT, "Pretraživanje", "*", searchPicker);
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
        search.setDefaultValue("obrazac");
        form.setItems(search.getField());

        return form;
	}

	protected void searchItemHandler(String value) {
		Criteria criteria = new Criteria();
    	criteria.addCriteria("service","pretraziObrasce");
    	criteria.addCriteria("searchValue",value);
    	for(String attribute: criteria.getAttributes()){
    		 System.out.println("RECORD : " + criteria.getAttribute(attribute));
    	}

	}




//	for (FormItem field : form.getFields()) {
//		field.addKeyPressHandler(enter);
//	}
//	if(_listGrid.getCriteria()!=null ){
//		Criterion crit = new Criterion();
//		crit.addCriteria(_listGrid.getCriteria());
//		form.setValuesAsCriteria(crit);




}
