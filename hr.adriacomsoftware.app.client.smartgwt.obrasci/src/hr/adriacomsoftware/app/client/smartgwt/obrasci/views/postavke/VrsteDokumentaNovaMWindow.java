package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.postavke;

/************* MWINDOW  VrsteDokumentaMWindow ************/

import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke.VrsteDokumentaModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke.VrsteDokumentaParametriModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class VrsteDokumentaNovaMWindow extends AS2Window{

	public static final String TARGET = "uploadTarget";
	public static RecordList _recordListFromServer;
//	private AS2Window _parent;
	private TabSet _theTabs;
	private Tab _tabVrsta;
	private VrsteDokumentaParametriTab _tabParametri;
	private VLayout _layoutVrsta;
//	private IButton _btn_potvrdi;
	private boolean change;

	public VrsteDokumentaNovaMWindow(AS2Window parent) {
		super();
		this.setHeight("60%");
		this.setWidth("70%");
		this.setShowResizeBar(false);
		createComponents();
	}

	public void createComponents() {
		_window_form_title.setContents(getWindowFormTitle());
		_dataSource = getModel();
		_vm = new ValuesManager();
		_vm.setDataSource(getModel());
		_theTabs = new TabSet();
		_theTabs.setWidth100();
		_theTabs.setHeight100();

		_tabVrsta = new Tab();
		_tabVrsta.setTitle("Vrsta");
		_layoutVrsta = new VLayout();
		_form = getDynamicFormVrsta();
		_form.setID("vrstaForm0");
		_layoutVrsta.addMembers(_form);
		_layoutVrsta.setWidth100();
		_layoutVrsta.setHeight100();
		_tabVrsta.setPane(_layoutVrsta);

		_tabParametri = new VrsteDokumentaParametriTab(getParametriValuesManager());


		_theTabs.setTabs(_tabVrsta,_tabParametri);
		windowLayout();
	}

	public void windowLayout() {
		this.addItem(_window_form_title);
		this.addItem(_theTabs);
		this.addItem(getButtonsLayout());
//		this.addCloseClickHandler(new CloseClickHandler() {
//			public void onCloseClick(CloseClickEvent event) {
//				// if(upload!=null) upload.removeFromParent();
//				closeWindow(true);
//			}
//		});
		this.show();
	}

	public DataSource getModel() {
		return VrsteDokumentaModel.getInstance();
	}
	private ValuesManager getParametriValuesManager() {
		ValuesManager vm=new ValuesManager();
		vm.setDataSource(VrsteDokumentaParametriModel.getInstance());
		return vm;
	}

	private AS2DynamicForm getDynamicFormVrsta() {
		AS2DynamicForm form = new AS2DynamicForm(4);
		form.setValuesManager(_vm);
		form.setAutoFetchData(false);
		form.setWrapItemTitles(true);
		form.setWidth100();
		form.setHeight100();
//		form.setEncoding(Encoding.MULTIPART);
//		form.setTarget(TARGET);
		AS2FormItem naziv = new AS2FormItem("naziv",AS2Field.TEXT);
		naziv.setWidth("*");
		naziv.getField().setColSpan(4);
		AS2FormItem verzija = new AS2FormItem("verzija", AS2Field.TEXT);
		verzija.setWidth(50);
		verzija.getField().setEndRow(true);
		AS2FormItem opis = new AS2FormItem("opis",AS2Field.TEXTAREA);
		opis.getField().setEndRow(true);
		AS2FormItem datum_kreiranja = new AS2FormItem("datum_kreiranja",AS2Field.DATE);
		verzija.setWidth(50);
		AS2FormItem lokacija = new AS2FormItem("lokacija",AS2Field.TEXT);
		AS2FormItem _uploadFileItem = new AS2FormItem("obrazac",AS2Field.FORM_UPLOAD,"Obrazac","*");
		_uploadFileItem.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				change=true;
			}
		});
//		(new ChangeHandler() {
//			@Override
//			public void onChange(ChangeEvent event) {
//				change=true;
//			}
//		});
//		fileItem.setWidth(DEFAULT_WIDTH);
		form.setFields(naziv,verzija,opis,datum_kreiranja,lokacija,_uploadFileItem);
		return form;
	}

	public HLayout getButtonsLayout() {
    	// define Buttons
//		_buttons_layout.setHeight(100);
		_buttons_layout.setDefaultLayoutAlign(Alignment.CENTER);

		IButton _button_upload = new IButton("Dodaj vrstu dokumenta");
		_button_upload.setLayoutAlign(Alignment.CENTER);
		_button_upload.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent e) {
				if (change) {
					change=false;
					DSRequest request= new DSRequest();
					request.setAttribute("test", "test");
					for(Record record: _tabParametri._listGridParametri.getRecords())
						request.setParams(record.toMap());
//					parameters.putAll(_parent._form.getValues());
					//TODO
//					if(formOrLgrid!=null){
//						if(formOrLgrid instanceof DynamicForm)
//							parameters.putAll(((DynamicForm)formOrLgrid).getValues());
//						else{
//							Record record = ((ListGrid)formOrLgrid).getSelectedRecord();
//							parameters.putAll(record.toMap());
//						}
//					}

//					request.setParams(parameters);
					_vm.saveData(serverCallback(),request);
				} else
					SC.say("Odaberite datoteku.");
//				Object obj = fileItem.getDisplayValue();
//				if (obj != null) {
//					_uploadForm.submitForm();
//				} else
//					SC.say("Odaberite datoteku.");
			}

		});


//		_btn_potvrdi = new IButton();
//		_btn_potvrdi.setTitle("Potvrdi");
//		_btn_potvrdi.addClickHandler(new  com.smartgwt.client.widgets.events.ClickHandler() {
//            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//                _vm.validate();
//                if (_form.hasErrors()) {
//                    _theTabs.selectTab(1);
//                } else {
//                    _theTabs.selectTab(0);
//                }
//            }
//        });

        _button_izlaz = new IButton("Odustani");
        _button_izlaz.setIcon(AS2Resources.INSTANCE.cancel_icon().getSafeUri().asString());
        _button_izlaz.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//            	upload.removeFromParent();
               closeWindow(false);
            }
        });
//		if(upload!=null)
//			_buttons_layout.setMembers(upload.getUploadButtons());
        _buttons_layout.setMembers(_button_upload,_button_izlaz);
        return _buttons_layout;
    }

	private DSCallback serverCallback() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getWindowFormTitle() {
		return "<b style=\"color:black;font-size:10pt;\">Dodaj novu vrstu dokumenta";
	}

}
