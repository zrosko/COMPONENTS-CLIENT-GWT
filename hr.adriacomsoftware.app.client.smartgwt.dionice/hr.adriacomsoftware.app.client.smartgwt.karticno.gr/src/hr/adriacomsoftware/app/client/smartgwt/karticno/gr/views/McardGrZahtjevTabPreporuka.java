package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevTabPreporuka extends AS2View2 implements McardGrZahtjevConstants {
	
	public static McardGrZahtjevTabPreporuka view;
	private McardGrZahtjevMWindow _parent;
	public static final String FAZA_ZAHTJEVA = "preporuka";
	public static final String FAZA_ZAHTJEVA_NEXT = "odluka";
	public static final String FAZA_ZAHTJEVA_PREVIOUS = "kreditna_sposobnost";
	Record _record;
    boolean refresh=false;
    
    AS2FormItem broj_zahtjeva;
    AS2FormItem odluka;
    AS2FormItem section_odluka;

    public DataSource _dataSource;
	private IButton _button_spremi;
	private HLayout _buttons_layout;
	private Label odluka_label;

	public McardGrZahtjevTabPreporuka(McardGrZahtjevMWindow parent,Record record) {
		_record = record;
		_parent = parent;
		createComponents();
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 2;
		McardGrZahtjevTabPreporuka.view=this;
	}
	
	public void showWidgets() {
	}
	
	@Override
	public DataSource getModel() {
		return McardGrZahtjevModel.getInstance();
	}

	@Override
	public DataSource getSifrarnikModel() {
		return McardGrZahtjevSifrarnikModel.getInstance();
	}

	public void createComponents() {
		_dataSource = getModel();
		_buttons_layout = getFormButtons();
		_form = getDynamicForm();
		_form.fetchData(new Criteria(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA,_record.getAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)),new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				_record = dsResponse.getDataAsRecordList().get(0);
				_form.editRecord(_record);
				if(_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).getValue().toString().equalsIgnoreCase(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO))
					_form.setCanEdit(false);
			}
		},new DSRequest(DSOperationType.CUSTOM,"procitajMcardGrZahtjev"));
		this.setWidth100();
		this.setHeight100();
		this.setStyleName("");
		this.addMembers(_form, _buttons_layout);
	}

	@Override
	public AS2ListGrid getListGrid() {
		return null;
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setColWidths("20%", "80%");
		form.setWrapItemTitles(true);
		form.setStyleNameGray();
		form.setSaveOnEnter(false);
		form.addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				if(_button_spremi.getDisabled())
					_button_spremi.setDisabled(false);				
			}
		});
		broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
		odluka = new AS2FormItem(MCARD_GR_ZAHTJEV__ODLUKA,AS2Field.FORM_CANVAS,"");
        odluka_label = new Label();  
        odluka_label.setHeight(30);  
        odluka_label.setPadding(10);  
        odluka_label.setAlign(Alignment.CENTER);  
        odluka_label.setValign(VerticalAlignment.CENTER);  
        odluka_label.setWrap(false);  
        odluka_label.setShowEdges(true);  
        odluka_label.setContents(_record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));  
        odluka.getField().setShowTitle(false);
        odluka.setCanvas(odluka_label);
        odluka.getField().setColSpan("*");
        AS2FormItem preporuka =  new AS2FormItem(MCARD_GR_ZAHTJEV__PREPORUKA);
        preporuka.getField().setRowSpan(3);
        section_odluka = new AS2FormItem("section_odluka",AS2Field.FORM_SECTION);
        section_odluka.setDefaultValue("Preporuka");
        section_odluka.setSectionItems(MCARD_GR_ZAHTJEV__ODLUKA,MCARD_GR_ZAHTJEV__PREPORUKA);
        AS2FormItem faza_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		faza_zahtjeva.getField().setVisible(false);
		faza_zahtjeva.setDefaultValue(FAZA_ZAHTJEVA);
		AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.getField().setVisible(false);
        form.setDataSource(_dataSource);
        form.setFields(broj_zahtjeva,section_odluka,odluka,preporuka,faza_zahtjeva,status_zahtjeva);
        form.focusInItem(preporuka);
        if(condition(MCARD_GR_ZAHTJEV__ODLUKA,_record))
			odlukaSlika(_record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));
        return form;
	}
	protected AS2DynamicForm getForm(){
		return _form;
	}
    public HLayout getFormButtons() {
    	HLayout buttons_layout = new HLayout(5);
    	buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
        _button_spremi = new IButton("Spremi");
        _button_spremi.setIcon(AS2Resources.SAVE_PATH);
    	_button_spremi.setDisabled(true);				
        _button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveData("save");
			}
        });
        buttons_layout.setMembers(_button_spremi);
        return buttons_layout;
    }
    private boolean condition(String attribute,Record record){
 		if(record.getAttributeAsObject(attribute)==null || record.getAttributeAsString(attribute).equals(""))
 			return false;
 		else
 			return true;
 	}
    
	private void odlukaSlika(String tekst){
		if(tekst.contains("Odbija")){
			odluka_label.setIcon(AS2Resources.CANCEL_PATH);
		}else if(tekst.contains("Odobrava")){
			odluka_label.setIcon(AS2Resources.SAVE_PATH);
		}else{
			odluka_label.setIcon(AS2Resources.WARNING_PATH);
		}
		odluka_label.setContents("<b style=\"font-size:12px;\">"+tekst+"</b>");
	}
	
//    protected boolean saveDataNoviKorak(String akcija) {
//    	if(akcija.equals("previous")){
//    		_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_PREVIOUS);
//    		saveData(false);
//    		return true;
//    	}else if(akcija.equals("next") && _form.validate()){
//    		_form.getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(FAZA_ZAHTJEVA_NEXT);
//    		saveData(false);
//    		return true;
//    	}else 
//    		return false;
//    }
    
	protected void saveData(final String akcija) {
		_form.saveData(new DSCallback() {
			public void execute(DSResponse dsResponse, Object rawData,DSRequest request) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				for(String attribute: server.getAttributes()){
					_record.setAttribute(attribute, server.getAttributeAsObject(attribute));
				}
				odlukaSlika(_record.getAttribute(MCARD_GR_ZAHTJEV__ODLUKA));
				_parent._refresh=true;
				_parent.refreshWindow(_record);
				if(akcija.equals("next") || akcija.equals("previous"))
					_parent.tabSaveCallback();
				_button_spremi.setDisabled(true);
			}
		});
	}

//	    public String getWindowFormTitle(){
//	        return "<b style=\"color:black;font-size:10pt;\">Obrada / uvjeti za zahtjev broj: "
//	                + _record.getAttributeAsString("broj_zahtjeva")
//	                + "</b></br>"
//	                + _record.getAttributeAsString("ime_prezime");
//	    }
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}
}
