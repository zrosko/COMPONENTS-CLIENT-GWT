package hr.adriacomsoftware.app.client.smartgwt.obrtnici.views;

import hr.adriacomsoftware.app.client.smartgwt.obrtnici.models.ObrtniciModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.common.obrtnici.ObrtniciConstants;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * @author astrikoman
 *
 */ 
public class ObrtniciView extends AS2View implements ObrtniciConstants {
	private static final String FIRST_TOOLBAR_BUTTON_DISPLAY_NAME = "Pregled";
	private static final String SECOND_TOOLBAR_BUTTON_DISPLAY_NAME = "Plasmani";
	private static final String THIRD_TOOLBAR_BUTTON_DISPLAY_NAME = "Depoziti";
	private static final String FOURTH_TOOLBAR_BUTTON_DISPLAY_NAME = "Ugovori";
	private static final String FIFTH_TOOLBAR_BUTTON_DISPLAY_NAME = "Bonitet";
	private HLayout rollOverCanvas;

	@Override
	protected void initializeView() {
		use_form=false;
		use_listgrid=true;
	}
	
	@Override
	public DataSource getModel() {
		return ObrtniciModel.getInstance();
	}
	
//	@Override
//	protected LinkedHashMap<String, LinkedHashMap<String, String>> getSifrarnikValueMaps() {
//		LinkedHashMap<String, LinkedHashMap<String, String>> mapa = new LinkedHashMap<>();
//		LinkedHashMap<String, String> vrsta_upisnika = new LinkedHashMap<>();
//		vrsta_upisnika.put("F", "Fizička");
//		vrsta_upisnika.put("P", "Pravna");
//		mapa.put(DION__VRSTA_UPISNIKA, vrsta_upisnika);
//		return mapa;
//	}

	@Override
	public DataSource getSifrarnikModel() {
		return ObrtniciModel.getInstance();
	}
	
	@Override
	protected AS2DynamicForm getDynamicForm() {
		return null;
	}
	
	@Override
	protected AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				AS2RolloverButtonFactory._rollOverRecord = this.getRecord(rowNum);
				if(AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject("groupValue")==null){
					if (rollOverCanvas == null) {
						rollOverCanvas = getRollOver(this);
					}
					return rollOverCanvas;
				}else{
					HLayout layout = new HLayout();
					layout.setWidth(1);
					layout.setHeight(1);
					return layout;
				}
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setShowResizeBar(false);
		//Sva polja iz baze po defaultu
		AS2ListGridField maticni_broj = new AS2ListGridField(OBRT__MATICNI_BROJ);
		AS2ListGridField naziv = new AS2ListGridField(OBRT__NAZIV);
		AS2ListGridField mjesto = new AS2ListGridField(OBRT__MJESTO);
		AS2ListGridField telefon = new AS2ListGridField(OBRT__TELEFON);
		AS2ListGridField godina_pocetka_poslovanja = new AS2ListGridField(OBRT__GODINA_POCETKA_POSLOVANJA);
		AS2ListGridField ocjena_ukupna = new AS2ListGridField(OBRT__OCJENA_UKUPNA);
		AS2ListGridField oib = new AS2ListGridField(OBRT__OIB);
		  
		listGrid.setFields(maticni_broj,naziv,mjesto,telefon,godina_pocetka_poslovanja,ocjena_ukupna,oib);
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				actionPregled();
			}
		});
		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	private HLayout getRollOver(final ListGrid listGrid) {
		HLayout rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						actionPregled();
					}
				}));
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null));
		return rollOverCanvas;
	}
	
	protected ToolStrip getToolStrip() {
		ToolStrip toolStrip = new ToolStrip();
		// initialise the buttons
		first = new ToolStripButton();
		first.setTitle(FIRST_TOOLBAR_BUTTON_DISPLAY_NAME);
		first.setIcon(DOCUMENT_EDIT_TOOLBAR_BUTTON);
		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionPregled();
			}
		});
		toolStrip.addButton(first);
		second = new ToolStripButton();
		second.setTitle(SECOND_TOOLBAR_BUTTON_DISPLAY_NAME);
		second.setIcon(AS2Resources.ADD_FILES_PATH);
		second.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_listGrid.deselectAllRecords();
			}
		});
		toolStrip.addButton(second);
		third = new ToolStripButton();
		third.setTitle(THIRD_TOOLBAR_BUTTON_DISPLAY_NAME);
		third.setIcon(DOCUMENT_NEW_TOOLBAR_BUTTON);
		third.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_listGrid.deselectAllRecords();
			}
		});
		toolStrip.addButton(third);
		fourth = new ToolStripButton();
		fourth.setTitle(FOURTH_TOOLBAR_BUTTON_DISPLAY_NAME);
		fourth.setIcon(AS2Resources.EXCELX_ICON_PATH);
		fourth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionIzvjestaj();
			}
		});
		toolStrip.addButton(fourth);
		fifth = new ToolStripButton();
		fifth.setTitle(FIFTH_TOOLBAR_BUTTON_DISPLAY_NAME);
		fifth.setIcon(AS2Resources.EXCELX_ICON_PATH);
		fifth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionIzvjestaj();
			}
		});
		toolStrip.addButton(fifth);
		toolStrip.addSeparator();
		seventh = new ToolStripButton();
		seventh.setIcon(DELETE_TOOLBAR_BUTTON);
		seventh.setTooltip(DELETE_TOOLBAR_BUTTON_HINT);
		seventh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				String text="Da li ste sigurni da želite obrisati obrtnika?";
				if(_listGrid.getSelectedRecords().length!=1)
					text="Da li ste sigurni da želite obrisati označene obrtnike?";
				SC.confirm(AS2GwtDesktop._messages.dialog_WarnTitle(), text,
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if(_listGrid!=null){
										Record recordToDelete = new Record();
										if(_listGrid.getSelectedRecords().length==1){
											recordToDelete.setAttribute("object0", " ");////TODO ID record.getAttribute(DION__ID_UPISNICA));
										}else{
											ListGridRecord[] selectedRecords = _listGrid.getSelectedRecords();
											int count=0;
											for(ListGridRecord record:selectedRecords){
												recordToDelete.setAttribute("object"+count++, " ");////TODO ID record.getAttribute(DION__ID_UPISNICA));
											}
										}
										_listGrid.removeData(recordToDelete);
										_listGrid.invalidateCache();
									}
								}
							}
						});

			}
		});
		toolStrip.addButton(seventh);
		eight = new ToolStripButton();
		eight.setIcon(FILTER_TOOLBAR_BUTTON);
		eight.setTooltip(FILTER_TOOLBAR_BUTTON_HINT);
		eight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				if(!_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterRow(true,true);
				}else{
					_listGrid.setShowFilterRow(false,false);
				}
			}
		});
		toolStrip.addButton(eight);

		ninth = new ToolStripButton();
		ninth.setIcon(REFRESH_TOOLBAR_BUTTON);
		ninth.setTooltip(REFRESH_TOOLBAR_BUTTON_HINT);
		ninth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				_listGrid.refresh();
			}
		});
		toolStrip.addButton(ninth);
		return toolStrip;
	}
	
	private void actionPregled() {
		if (_listGrid.getSelectedRecord() == null) {
			SC.warn("Odaberite upisnicu");
		} else{
		}
	}
	
	private void actionIzvjestaj() {
		_listGrid.deselectAllRecords();
	}

}
