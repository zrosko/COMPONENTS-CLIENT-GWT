package hr.adriacomsoftware.app.client.smartgwt.blokade.po.views;

import hr.adriacomsoftware.app.client.smartgwt.blokade.po.models.BlokadePoConstants;
import hr.adriacomsoftware.app.client.smartgwt.blokade.po.models.BlokadePoModel;
import hr.adriacomsoftware.app.client.smartgwt.blokade.po.models.BlokadePoSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.client.smartgwt.trazi.views.TraziFormWindow;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;


/**
 * @author astrikoman
 *
 */ 
public class CopyOfBlokadePoView extends AS2View2 implements BlokadePoConstants {
	private static final String FIRST_TOOLBAR_BUTTON_DISPLAY_NAME = "Nalog za blokadu";
	private static final String SECOND_TOOLBAR_BUTTON_DISPLAY_NAME = "Blokada";
	private static final String THIRD_TOOLBAR_BUTTON_DISPLAY_NAME = "Plaćanja";
	private HLayout rollOverCanvas;
//	protected UpisnicaMWindow upisnica_modal;
	private ToolStripButton first;
	private ToolStripButton second;
	private ToolStripButton third;
	private ToolStripButton fourth;
	private ToolStripButton fifth;
	private ToolStripButton sixth;
	private ToolStripButton eight;
	
	private PickerIcon _search;
	private PickerIcon _advanced_search;
	AS2FormItem search;

	public CopyOfBlokadePoView(String name) {
		super(name);
	}
	
	@Override
	public void customWindowPreferences() {
		use_form=true;
		use_listgrid=true;
	}
	
	@Override
	public DataSource getModel() {
		return BlokadePoModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return BlokadePoSifrarnikModel.getInstance();
	}
	
	@Override
	public void windowLayout() {
		_toolStrip=getToolStrip();
		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
	}
//	
//	@Override
//	protected LinkedHashMap<String, LinkedHashMap<String, String>> getSifrarnikValueMaps() {
//		LinkedHashMap<String, LinkedHashMap<String, String>> mapa = new LinkedHashMap<>();
//		LinkedHashMap<String, String> vrsta_upisnika = new LinkedHashMap<>();
//		vrsta_upisnika.put("F", "Fizički");
//		vrsta_upisnika.put("P", "Pravna");
//		mapa.put(DION__VRSTA_UPISNIKA, vrsta_upisnika);
//		return mapa;
//	}

	
	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		AS2FormItem podkategorija = new AS2FormItem(CRM201__PODKATEGORIJA, AS2Field.COMBO/*, "Odjel"*/);
		podkategorija.getField().setColSpan(5);
		podkategorija.setWidth(370);
		podkategorija.setDefaultToFirstOption(true);
		
		search = new AS2FormItem("search",AS2Field.TEXT,"Pretraživanje");
		search.setWidth("400");
		search.getField().setIcons(_advanced_search,_search);
		search.getField().addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						TraziFormWindow bl = new TraziFormWindow(CopyOfBlokadePoView.this,_listGrid,false,true);
						bl.getButtonSearch().getField().fireEvent(new com.smartgwt.client.widgets.form.fields.events.ClickEvent(bl.getButtonSearch().getField().getJsObj()));
					}
				}
			}
		});
	
		form.setDataSource(getModel());
		form.setFields(search);
		form.setDropDownModel(getSifrarnikModel(),podkategorija);
		
		return form;
	}
	
	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				AS2RolloverButtonFactory._rollOverRecord = this.getRecord(rowNum);
				if(AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject("groupValue")==null){
					if (rollOverCanvas == null) {
						rollOverCanvas = prepareRollOver(this);
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
//		getLookUpsAndButtons();
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setShowResizeBar(false);
		//Sva polja iz baze po defaultu
		AS2ListGridField naziv = new AS2ListGridField(CRM201__NAZIV);
		AS2ListGridField maticni_broj = new AS2ListGridField(CRM201__MATICNI_BROJ);
		AS2ListGridField broj_partije = new AS2ListGridField(CRM201__BROJ_PARTIJE);
		AS2ListGridField broj_zaduznice = new AS2ListGridField(CRM201__BROJ_ZADUZNICE);
		AS2ListGridField datum_blokade = new AS2ListGridField(CRM201__DATUM_BLOKADE);
		AS2ListGridField iznos_blokade = new AS2ListGridField(CRM201__IZNOS_BLOKADE);
		AS2ListGridField datum_povlacenja_blokade = new AS2ListGridField(CRM201__DATUM_POVLACENJA_BLOKADE);
		AS2ListGridField iznos = new AS2ListGridField(CRM201__IZNOS);
		AS2ListGridField datum_naplate = new AS2ListGridField(CRM201__DATUM_NAPLATE);
		AS2ListGridField iznos_naplate = new AS2ListGridField(CRM201__IZNOS_NAPLATE);
		AS2ListGridField datum_deblokade = new AS2ListGridField(CRM201__DATUM_DEBLOKADE);
		AS2ListGridField pn = new AS2ListGridField(CRM201__PN);
		AS2ListGridField datum_pn_od = new AS2ListGridField(CRM201__DATUM_PN_OD);
		AS2ListGridField datum_pn_do = new AS2ListGridField(CRM201__DATUM_PN_DO);
		AS2ListGridField napomena = new AS2ListGridField(CRM201__NAPOMENA);
		listGrid.setFields(naziv,maticni_broj,broj_partije,broj_zaduznice,datum_blokade,
				iznos_blokade,datum_povlacenja_blokade,iznos,datum_naplate,iznos_naplate,
				datum_deblokade,pn,datum_pn_od,datum_pn_do,napomena);
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				if(_listGrid.getSelectedRecord()!=null){
//						dokument_modal = new PisarnicaDokumentMWindow(UpisnicaView.this,_listGrid.getSelectedRecord());
				}
			}
		});
		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	private HLayout prepareRollOver(final ListGrid listGrid) {
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
		first.setIcon(AS2Resources.ADD_FILES_PATH);
		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_listGrid.deselectAllRecords();
//				upisnica_modal = new UpisnicaMWindow(BlokadePoView.this,null);
			}
		});
//		toolStrip.addButton(first);
		second = new ToolStripButton();
		second.setTitle(SECOND_TOOLBAR_BUTTON_DISPLAY_NAME);
		second.setIcon(AS2Resources.TOOLBAR_DOCUMENT_EDIT_ICON_PATH);
		second.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionPregled();
			}
		});
//		toolStrip.addButton(second);
		third = new ToolStripButton();
		third.setTitle(THIRD_TOOLBAR_BUTTON_DISPLAY_NAME);
		third.setIcon(AS2Resources.TOOLBAR_DOCUMENT_EDIT_ICON_PATH);
		third.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionPlacanje();
			}
		});
//		toolStrip.addButton(third);
		toolStrip.addSeparator();
		fourth = new ToolStripButton();
		fourth.setIcon(AS2Resources.TOOLBAR_DELETE_ICON_PATH);
		fourth.setTooltip("Briši");
		fourth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
//				String text="Da li ste sigurni da želite obrisati dokument?";
//				if(_listGrid.getSelectedRecords().length!=1)
//					text="Da li ste sigurni da želite obrisati označene dokumente?";
//				SC.confirm("Upozorenje!", text,
//						new BooleanCallback() {
//							@Override
//							public void execute(Boolean value) {
//								if (value != null && value) {
//									if(_listGrid!=null){
//										Record recordToDelete = new Record();
//										if(_listGrid.getSelectedRecords().length==1){
//											recordToDelete.setAttribute("object0", _listGrid.getSelectedRecord().getAttribute(DION__ID_UPISNICA));
//										}else{
//											ListGridRecord[] selectedRecords = _listGrid.getSelectedRecords();
//											int count=0;
//											for(ListGridRecord record:selectedRecords){
//												recordToDelete.setAttribute("object"+count++, record.getAttribute(DION__ID_UPISNICA));
//											}
//										}
//										_listGrid.removeData(recordToDelete);
//										_listGrid.invalidateCache();
//									}
//								}
//							}
//						});
			}
		});
//		toolStrip.addButton(fourth);
		fifth = new ToolStripButton();
		fifth.setIcon(AS2Resources.TOOLBAR_FILTER_ICON_PATH);
		fifth.setTooltip("Filtriraj");
		fifth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				if(!_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterRow(true,true);
				}else{
					_listGrid.setShowFilterRow(false,false);
				}
			}
		});
//		toolStrip.addButton(fifth);

		sixth = new ToolStripButton();
		sixth.setIcon(AS2Resources.TOOLBAR_REFRESH_ICON_PATH);
		sixth.setTooltip("Osvježi");
		sixth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				_listGrid.refresh();
			}
		});
//		toolStrip.addButton(sixth);
		
		
		eight = new ToolStripButton();
		eight.setIcon(AS2Resources.TOOLBAR_SEARCH_ICON_PATH);
		eight.setTooltip("Traži");
		eight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				TraziFormWindow trazi = new TraziFormWindow(CopyOfBlokadePoView.this,_listGrid,true,false);
				trazi.setHeight(300);
			}
		});
		
		
		toolStrip.addMembers(first,second,third,fourth,fifth,sixth,eight,new LayoutSpacer());
		return toolStrip;
	}
	
	private void actionPregled() {
		if (_listGrid.getSelectedRecord() == null) {
			SC.warn("Odaberite upisnicu");
		} else{
//			upisnica_modal = new UpisnicaMWindow(BlokadePoView.this,_listGrid.getSelectedRecord());
		}
	}
	
	private void actionPlacanje() {
		if (_listGrid.getSelectedRecord() == null) {
			SC.warn("Odaberite upisnicu");
		} else{
//			upisnica_modal = new UpisnicaMWindow(BlokadePoView.this,_listGrid.getSelectedRecord());
		}
		
	}
	@Override
	public String getSearchCriteria(){
		if(_form.getField("search").getValue() == null)
			return "";
		else 
			return _form.getField("search").getValue().toString();
	};
	@Override
	public void getLookUpsAndButtons() {
		_advanced_search = new PickerIcon(PickerIcon.COMBO_BOX,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						Rectangle itemRect = search.getField().getPageRect();
						TraziFormWindow trazi = new TraziFormWindow(CopyOfBlokadePoView.this,_listGrid,true,true);
						trazi.moveWindowTo(itemRect.getLeft()-3,itemRect.getTop()+itemRect.getHeight());
						trazi.resizeTo(itemRect.getWidth()-12, 280);
					}
				});
		_search = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			public void onFormItemClick(FormItemIconClickEvent event) {
				TraziFormWindow bl = new TraziFormWindow(CopyOfBlokadePoView.this,_listGrid,false,true);
				bl.getButtonSearch().getField().fireEvent(new com.smartgwt.client.widgets.form.fields.events.ClickEvent(bl.getButtonSearch().getField().getJsObj()));
					}
				});
	}
	
	@Override
	public AS2FormItem[] getSearchFormItems() {
		List<AS2FormItem> items = new ArrayList<AS2FormItem>();
		AS2FormItem oib = new AS2FormItem("oib",AS2Field.TEXT,"OIB","*");
		oib.getField().setTitleOrientation(TitleOrientation.TOP);
		items.add(oib);
		AS2FormItem jmbg = new AS2FormItem("jmbg",AS2Field.TEXT,"JMBG","*");
		jmbg.getField().setTitleOrientation(TitleOrientation.TOP);
		items.add(jmbg);
		AS2FormItem maticni_broj = new AS2FormItem("maticni_broj",AS2Field.TEXT,"Matični broj","*");
		maticni_broj.getField().setTitleOrientation(TitleOrientation.TOP);
		maticni_broj.getField().setSaveOnEnter(false);
		items.add(maticni_broj);
		AS2FormItem naziv = new AS2FormItem("naziv",AS2Field.TEXT,"Naziv","*");
		naziv.getField().setTitleOrientation(TitleOrientation.TOP);
		items.add(naziv);
		AS2FormItem trazi_sve = new AS2FormItem("trazi_sve",AS2Field.TEXT,"Sadrži","*");
		trazi_sve.getField().setTitleOrientation(TitleOrientation.TOP);
		items.add(trazi_sve);
//		AS2FormItem vrsta_zahtjeva = new AS2FormItem("vrsta_zahtjeva",AS2Field.FORM_SELECT, "Vrsta zahtjeva", 4, null, true);
//		vrsta_zahtjeva.setMultiple(true);
//		vrsta_zahtjeva.setMultipleAppearance(MultipleAppearance.PICKLIST);
//		vrsta_zahtjeva.setValueMap("Osnovni", "Dodatni");
//		items.add(vrsta_zahtjeva);
//		AS2FormItem status_zahtjeva = new AS2FormItem("status_zahtjeva",AS2Field.FORM_SELECT, "Status zahtjeva", 4, null, true);
//		status_zahtjeva.setMultiple(true);
//		status_zahtjeva.setMultipleAppearance(MultipleAppearance.PICKLIST);
//		items.add(status_zahtjeva);
		AS2FormItem[] formItems = new AS2FormItem[items.size()];
		items.toArray(formItems);
		return formItems;
	}
	
	
	@Override
	protected String getRecordIdName() {
		return "id_crm";
	}
}
