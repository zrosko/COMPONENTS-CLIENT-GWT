package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.postavke;

/************* MWINDOW  VrsteDokumentaMWindow ************/

import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke.VrsteDokumentaModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;

public class VrsteDokumentaMWindow extends AS2Window {

	private IButton _button_uredi;
	private AS2ListGridField _nazivGrid;
	private AS2ListGridField _tipDokumentaGrid;
	public static RecordList _recordListFromServer;

	public VrsteDokumentaMWindow(AS2View2 parent) {
		super();
		_parent_view = parent;
		SC.warn("Ova komponenta je u testnoj fazi.", new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				if(true){
				
					_dataSource = VrsteDokumentaModel.getInstance();
					createComponents();
				}
				
			}
		});
	
	}

	public void createComponents() {
		_window_form_title.setContents(getWindowFormTitle());
		_form = getDynamicForm();
		_listGrid = getListGrid();
		windowLayout();
	}

	public void windowLayout(){
		this.addItem(_window_form_title);
		this.addItem(_listGrid);
		this.addItem(_listGrid.getStatusBar());
		this.addItem(getButtonsLayout());
		// this.addCloseClickHandler(new CloseClickHandler() {
		// public void onCloseClick(CloseClickEvent event) {
		// closeWindow(false);
		// }
		// });
		this.show();

	}

	public AS2DynamicForm getDynamicForm() {
		return null;
	}

	public AS2ListGrid getListGrid() {
		_tipDokumentaGrid = new AS2ListGridField("tip_dokumenta", AS2Field.IMAGE);
		_tipDokumentaGrid.setWidth(50);;
		_tipDokumentaGrid.getField().setAlign(Alignment.CENTER);
		_tipDokumentaGrid.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"fileupload/extensions/s32/");
		_tipDokumentaGrid.getField().setImageURLSuffix(".png");
		_nazivGrid = new AS2ListGridField("naziv");
		final AS2ListGrid listGrid = new AS2ListGrid(true);
		listGrid.setDataSource(_dataSource);
		listGrid.setCellHeight(35);
		listGrid.setWrapCells(true);
		listGrid.setDataSource(_dataSource,_tipDokumentaGrid,_nazivGrid);
		listGrid.setAlternateRecordStyles(true);
		listGrid.setCanExpandRecords(true);
	    listGrid.setExpansionMode(ExpansionMode.DETAILS);
	    listGrid.setAutoFetchData(true);
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if(_listGrid.getSelectedRecord()==null)
					_button_uredi.setDisabled(true);
				else
					_button_uredi.setDisabled(false);
			}
		});
		return listGrid;
	}

	public HLayout getButtonsLayout() {
    	// define Buttons
		IButton _button_dodaj_vrstu = new IButton("Dodaj vrstu");
		_button_dodaj_vrstu.setIcon(AS2Resources.INSTANCE.add_icon().getSafeUri().asString());
		_button_dodaj_vrstu.setAutoFit(true);
		_button_dodaj_vrstu.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new VrsteDokumentaNovaMWindow(VrsteDokumentaMWindow.this);
			}
		});
		
		_button_uredi = new IButton("Uredi");
		_button_uredi.setDisabled(true);
		_button_uredi.setShowDisabledIcon(false);
		_button_uredi.setIcon(AS2Resources.OPEN_DOCUMENT_ICON_PATH);
		_button_uredi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				downloadFile();
			}
		});

		_button_brisi = new IButton("Briši");
		_button_brisi.setIcon(AS2Resources.INSTANCE.delete_icon().getSafeUri().asString());
		_button_brisi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("Upozorenje!", "Da li ste sigurni da želite obrisati privitak?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if(_listGrid!=null){
										_listGrid.removeSelectedData();
										_listGrid.refresh();
									}
									_button_uredi.setDisabled(true);
								}
							}
						});
			}
		});

        _button_izlaz = new IButton("Odustani");
        _button_izlaz.setIcon(AS2Resources.INSTANCE.cancel_icon().getSafeUri().asString());
        _button_izlaz.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               closeWindow(false);
            }
        });
        _buttons_layout.setMembers(_button_dodaj_vrstu, _button_uredi,_button_brisi,
                _button_izlaz);
        return _buttons_layout;
    }

	public String getWindowFormTitle() {
		return "<b style=\"color:black;font-size:10pt;\">Vrste dokumenta";
	}

}
