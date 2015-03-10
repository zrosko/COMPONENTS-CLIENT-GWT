package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class McardGrZahtjevView extends AS2View2 implements McardGrZahtjevConstants {

	
	private static final String FIRST_TOOLBAR_MENU_BUTTON_DISPLAY_NAME = "Zahtjev za MASTERCARD";
	private static final String SECOND_TOOLBAR_MENU_BUTTON_DISPLAY_NAME = "Zahtjev za dodatnog korisnika";
	private static final String FIRST_TOOLBAR_BUTTON_DISPLAY_NAME = "Pregled";
	private static final String SECOND_TOOLBAR_BUTTON_DISPLAY_NAME = "Privitak";
	private McardGrZahtjevView current_view;
	public McardGrZahtjevMWindow zahtjev_modal;
	public McardGrZahtjevDodatniMWindow zahtjev_dodatni_modal;
	public McardGrZahtjevPrivitakMWindow privitak_modal;

	public McardGrZahtjevView(String name) {
		super(name);
		this.current_view = this;
	}
	
	@Override
	public void customWindowPreferences() {
		use_form=false;
		use_listgrid=true;
	}

	@Override
	public DataSource getModel() {
		return McardGrZahtjevModel.getInstance();
	}

	@Override
	public DataSource getSifrarnikModel() {
		return McardGrZahtjevSifrarnikModel.getInstance();
	}
//	@Override
//	public void windowLayout() {
//		_toolStrip=getToolStrip();
//		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
//	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}

	@Override
	public AS2ListGrid getListGrid() {
		AS2ListGrid listGrid = new AS2ListGrid(true) {
			@Override  
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				if (getFieldName(colNum).equals("status_zahtjeva") && record.getAttribute("status_zahtjeva")!=null) {  
					if (record.getAttribute("status_zahtjeva").equalsIgnoreCase("U tijeku")) {  
						return "background-color: #B8CCE4; color:#000000;";  
					} else {  
						return super.getCellCSSText(record, rowNum, colNum);  
					}  
				} else if (getFieldName(colNum).equals("vrsta_zahtjeva") && record.getAttribute("vrsta_zahtjeva")!=null) { 
					if (record.getAttribute("vrsta_zahtjeva").equalsIgnoreCase("D")) {  
						return "color:#0033FF;";
						//                    } else if (record.getAttribute("vrsta_zahtjeva").equalsIgnoreCase("O")) {  
						//                        return "color:#0033FF;";
					} else {  
						return super.getCellCSSText(record, rowNum, colNum);  
					}
				} else {  
					return super.getCellCSSText(record, rowNum, colNum);  
				}
			}}; 
			listGrid.setDataSource(getModel());
			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setShowResizeBar(false);
			//listGrid.setSelectionType(SelectionStyle.SINGLE);
			AS2ListGridField broj_zahtjeva = new AS2ListGridField(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA, AS2Field.INTEGER);
			broj_zahtjeva.setWidth("8%");//90);
			AS2ListGridField vrsta_zahtjeva = new AS2ListGridField(MCARD_GR_ZAHTJEV__VRSTA_ZAHTJEVA);
			vrsta_zahtjeva.setWidth("8%");//90);
			AS2ListGridField vrsta_kartice = new AS2ListGridField(MCARD_GR_ZAHTJEV__VRSTA_KARTICE);
			vrsta_kartice.setWidth("8%");//90);
			AS2ListGridField oib = new AS2ListGridField(MCARD_GR_ZAHTJEV__OIB);
			oib.setWidth("8%");//100);
			AS2ListGridField ime_prezime = new AS2ListGridField(MCARD_GR_ZAHTJEV__IME_PREZIME);
			ime_prezime.setWidth("30%");
			AS2ListGridField datum_zaprimanja = new AS2ListGridField(MCARD_GR_ZAHTJEV__DATUM_ZAPRIMANJA, AS2Field.DATE);
			datum_zaprimanja.setWidth("8%");//120);
			datum_zaprimanja.getField().setAlign(Alignment.CENTER); 
			AS2ListGridField status_zahtjeva = new AS2ListGridField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
			status_zahtjeva.setWidth("8%");//120);
			AS2ListGridField faza_icon = new AS2ListGridField(MCARD_GR_ZAHTJEV__FAZA_ICON,AS2Field.IMAGE);
			faza_icon.getField().setShowTitle(false);
			faza_icon.getField().setAlign(Alignment.CENTER);  
			faza_icon.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/s16/excel/");  
			faza_icon.getField().setImageURLSuffix(".png");
			faza_icon.setWidth(24);
			//		faza_icon.getField().setAutoFitWidth(true);
			faza_icon.getField().setCanSort(false);
			AS2ListGridField faza_zahtjeva = new AS2ListGridField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA,AS2Field.COMBO);
			faza_zahtjeva.setWidth("12%");//120);
			AS2ListGridField status_odobrenja = new AS2ListGridField(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,AS2Field.COMBO);
			status_odobrenja.setWidth("10%");//100);	
			AS2ListGridField odobrenje_icon = new AS2ListGridField(MCARD_GR_ZAHTJEV__ODOBRENJE_ICON,AS2Field.IMAGE);
			odobrenje_icon.getField().setShowTitle(false);
			odobrenje_icon.getField().setAlign(Alignment.CENTER);  
			odobrenje_icon.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/s16/excel/");  
			odobrenje_icon.getField().setImageURLSuffix(".png");
			odobrenje_icon.setWidth(24);
			//		odobrenje_icon.getField().setAutoFitWidth(true);
			odobrenje_icon.getField().setCanSort(false);
			listGrid.setFields(broj_zahtjeva,vrsta_zahtjeva,vrsta_kartice,oib,ime_prezime,datum_zaprimanja,status_zahtjeva,
					faza_icon,faza_zahtjeva,odobrenje_icon,status_odobrenja);
			listGrid.setGroupByField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
			listGrid.setGroupStartOpen(GroupStartOpen.ALL);
			//TODO
			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					if(_listGrid.getSelectedRecord()!=null){
						//					DSRequest ds_request = _dataSource.getRequestProperties();
						//					ds_request.setOperationType(DSOperationType.FETCH);
						//					ds_request.setOperationId("single");
						//					_dataSource.setRequestProperties(ds_request);
						//					_dataSource.fetchRecord(_listGrid.getSelectedRecord().getAttributeAsInt("broj_zahtjeva"));
						//					_listGrid.updateStatusBar();
						if (event.getRecord().getAttribute("vrsta_zahtjeva").equalsIgnoreCase("O"))
							zahtjev_modal = new McardGrZahtjevMWindow(current_view, _listGrid.getSelectedRecord());
						else
							zahtjev_dodatni_modal = new McardGrZahtjevDodatniMWindow(current_view, _listGrid.getSelectedRecord());
					}

				}
			});


			return listGrid;
	}


	protected ToolStrip getToolStrip() {
		ToolStrip toolStrip = new ToolStrip();

		// initialise the buttons
		Menu menu = new Menu();
		menu.setCanSelectParentItems(true);
		MenuItem novi = new MenuItem(FIRST_TOOLBAR_MENU_BUTTON_DISPLAY_NAME);
		novi.setIcon(AS2Resources.TOOLBAR_DOCUMENT_NEW_ICON_PATH);
		novi.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				zahtjev_modal = new McardGrZahtjevMWindow(current_view, null);
				_listGrid.deselectAllRecords();

			}
		});
		MenuItem dodatni = new MenuItem(SECOND_TOOLBAR_MENU_BUTTON_DISPLAY_NAME);
		dodatni.setIcon(AS2Resources.TOOLBAR_DOCUMENT_NEW_ICON_PATH);
		dodatni.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				zahtjev_dodatni_modal = new McardGrZahtjevDodatniMWindow(current_view,null);
				_listGrid.deselectAllRecords();
			}
		});
		menu.setData(novi,dodatni);
		ToolStripMenuButton novi_zahtjev = new ToolStripMenuButton("Novi Zahtjev", menu);
		novi_zahtjev.setIcon(AS2Resources.TOOLBAR_DOCUMENT_NEW_ICON_PATH);
		toolStrip.addMenuButton(novi_zahtjev);

		ToolStripButton _novi_zahtjev = new ToolStripButton();
		_novi_zahtjev.setTitle(FIRST_TOOLBAR_BUTTON_DISPLAY_NAME);
		_novi_zahtjev.setIcon(AS2Resources.TOOLBAR_DOCUMENT_EDIT_ICON_PATH);
		_novi_zahtjev.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite Zahtjev");
				} else{
					if (_listGrid.getSelectedRecord().getAttribute("vrsta_zahtjeva").equalsIgnoreCase("O")) {
						zahtjev_modal = new McardGrZahtjevMWindow(current_view,_listGrid.getSelectedRecord());
					} else {
							zahtjev_dodatni_modal = new McardGrZahtjevDodatniMWindow(current_view, _listGrid.getSelectedRecord());
//
					}
				}
			}
		});
		toolStrip.addButton(_novi_zahtjev);

		ToolStripButton _novi_zahtjev_dodatni = new ToolStripButton();
		_novi_zahtjev_dodatni.setTitle(SECOND_TOOLBAR_BUTTON_DISPLAY_NAME);
		_novi_zahtjev_dodatni.setIcon(AS2Resources.TOOLBAR_ATTACHEMENT_ICON);
		_novi_zahtjev_dodatni.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(_listGrid.getSelectedRecord()==null)
					SC.warn("Odaberite Zahtjev");
				else
					privitak_modal = new McardGrZahtjevPrivitakMWindow(current_view, _listGrid.getSelectedRecord());
			}
		});
		toolStrip.addButton(_novi_zahtjev_dodatni);
		toolStrip.addSeparator();

		ToolStripButton _brisi = new ToolStripButton();
		_brisi.setIcon(AS2Resources.TOOLBAR_DELETE_ICON_PATH);
		_brisi.setTooltip("Briši");
		_brisi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				String text="Da li ste sigurni da želite obrisati zahtjev?";
				if(_listGrid.getSelectedRecords().length!=1)
					text="Da li ste sigurni da želite obrisati označene zahtjeve?";
				SC.confirm("Upozorenje!", text,
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if(_listGrid!=null){
//										_listGrid.removeSelectedData();
										for(Record record: _listGrid.getSelectedRecords()){
											_listGrid.removeData(record,new DSCallback() {
												@Override
												public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
													_listGrid.invalidateCache();
													
												}
											});
											
										}
									
									}
								}
							}
						});

			}
		});
		toolStrip.addButton(_brisi);

		ToolStripButton _filter = new ToolStripButton();
		_filter.setIcon(AS2Resources.TOOLBAR_FILTER_ICON_PATH);
		_filter.setTooltip("Filtriraj");
		_filter.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				if(!_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterRow(true,true);
				}else{
					_listGrid.setShowFilterRow(false,false);
				}
			}
		});
		toolStrip.addButton(_filter);

		ToolStripButton _pronadi = new ToolStripButton();
		_pronadi.setIcon(AS2Resources.TOOLBAR_SEARCH_ICON_PATH);
		_pronadi.setTooltip("Pronađi");
		_pronadi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				new McardGrZahtjevSearchMWindow(current_view,_listGrid);
			}
		});
		toolStrip.addButton(_pronadi);


		ToolStripButton _osvjezi = new ToolStripButton();
		_osvjezi.setIcon(AS2Resources.TOOLBAR_REFRESH_ICON_PATH);
		_osvjezi.setTooltip("Osvježi");
		_osvjezi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				_listGrid.refresh();
			}
		});
		toolStrip.addButton(_osvjezi);
		
		return toolStrip;
	}
	@Override
	protected String getRecordIdName() {
		return MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA;
	}

}
