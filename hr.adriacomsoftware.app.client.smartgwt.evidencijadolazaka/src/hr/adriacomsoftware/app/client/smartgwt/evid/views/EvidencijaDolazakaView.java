package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.EditCompletionEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

/**
 * @author msekso, astrikoman
 *
 */ 
public class EvidencijaDolazakaView extends AS2View2 implements  EvidencijaDolazakaConstants {
	private boolean _window = false;
	private String ulogirani_korisnik;
	//IZNAD JE OK
	//private ToolStrip _buttons_layout;
	private HLayout rollOverCanvas;
	private ToolStripButton _button_potvrdi;
	private boolean _refresh;
	private Record _record;
	//private ToolStripButton _button_novo;
	//private ToolStripButton toolbarPreview;
//	private AS2ListGrid _listGrid;

	private ToolStripButton _button_excel;
	private static final String HELPTEXT = "<b>* šifra 01 - </b>Neiskorišteni godišnji odmor" +  
            "<br><br><b>* šifra 10 - </b>Bolovanja, porodiljni"+
    		"<br><br><b>* šifra 01-12 - </b>Smrtni slučaj, selidba, ženidba, preseljenje<br>(najviše 7 dana godišnje)";  
	
	public EvidencijaDolazakaView(String name) {
		super(name);
	}
	public EvidencijaDolazakaView(String name, Record record) {
		super(name, record);
	}
	@Override
	public void customWindowPreferences() {
		this.setWidth100();
		this.setHeight100();
		number_of_columns = 5;
		use_form=true;
		use_listgrid=true;
		ulogirani_korisnik = AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME);
	}
	@Override
	public DataSource getModel() {
		return EvidencijaDolazakaModel.getInstance();
	}
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
	}
	@Override
	protected void getAdditionalData(Record record){
		_record = record;
		_window = record.getAttributeAsBoolean("window");
	};
	@Override
	public void windowLayout() {
		_toolStrip=getToolStrip();
		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		int addDay = 1;
		if(new Date(System.currentTimeMillis()-60*60*1000).getDay()==1)
			addDay=3; // if monday, set friday
		final AS2FormItem datum_od = new AS2FormItem(EVIDENCIJA__DATUM_OD);
		datum_od.setWidth(100);
		datum_od.getField().setDefaultValue(new Date(System.currentTimeMillis()-addDay*24*60*60*1000));
		final AS2FormItem datum_do = new AS2FormItem(EVIDENCIJA__DATUM_DO);
		datum_do.setWidth(100);
		datum_do.getField().setDefaultValue(new Date(System.currentTimeMillis()-addDay*24*60*60*1000));
		AS2FormItem button_prikazi = new AS2FormItem("button_prikazi", AS2Field.FORM_BUTTON, "Prikaži");
		button_prikazi.setStartRow(false);
		button_prikazi.setWidth(100);
		AS2FormItem id_spica_oj = new AS2FormItem(EVIDENCIJA__ID_SPICA_OJ, AS2Field.COMBO);
		if(AS2GwtDesktop.getCacheData(getSifrarnikModel(),EVIDENCIJA__ID_SPICA_OJ+"__"+ulogirani_korisnik)!=null && _window==false){
			id_spica_oj.setDropDownList(EVIDENCIJA__ID_SPICA_OJ+"__"+ulogirani_korisnik);
		}
		id_spica_oj.getField().setColSpan(5);
		id_spica_oj.setWidth(370);
		id_spica_oj.setDefaultToFirstOption(true);
		AS2FormItem radnik_id = new AS2FormItem(EVIDENCIJA__RADNIK_ID);
		radnik_id.getField().setVisible(false);
		AS2FormItem window = new AS2FormItem("window",AS2Field.TEXT);
		window.getField().setDefaultValue(_window);
		window.getField().setVisible(false);
		//handlers
		datum_od.getField().addChangedHandler(new com.smartgwt.client.widgets.form.fields.events.ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				datum_do.getField().setValue(event.getItem().getValue());
			}
		});
		button_prikazi.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = _form.getValuesAsCriteria();
				_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.FETCH));
			}
		});
		
		if(_window){
			datum_od.getField().setDefaultValue(_record.getAttributeAsDate("datum_od"));
			datum_od.getField().setCanEdit(false);
			datum_do.getField().setDefaultValue(_record.getAttributeAsDate("datum_do"));
			datum_do.getField().setCanEdit(false);
			radnik_id.getField().setDefaultValue(_record.getAttribute("radnik_id"));
			id_spica_oj.getField().setVisible(false);
			button_prikazi.getField().setVisible(false);
		}
		form.setDataSource(getModel());
		form.setFields(datum_od,datum_do,button_prikazi,id_spica_oj,radnik_id,window);
		form.setDropDownModel(getSifrarnikModel(), id_spica_oj);
		form.focusInItem(button_prikazi);
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
						prepareRollOver(this);
					}
					return rollOverCanvas;
				}else{
					HLayout layout = new HLayout();
					layout.setWidth(1);
					layout.setHeight(1);
					return layout;
				}
			}//TODO
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				if (record!=null && record.getAttributeAsObject("radni_dan")!=null && getFieldName(colNum).equals("radni_dan")) {  
					if (record.getAttribute("radni_dan").equalsIgnoreCase("0")) {  
						return "color:red;";  
					}
				}else if (record!=null && record.getAttributeAsObject("napomena_spica")!=null && getFieldName(colNum).equals("napomena_spica")) {  
					if (!record.getAttribute("napomena_spica").startsWith("0")||  record.getAttribute("napomena_spica").contains("Trajanje 1")) {  
						return "color:red;";  
					}
				}
				return super.getCellCSSText(record, rowNum, colNum);  
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setCellHeight(40);
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		listGrid.setFixedRecordHeights(false); 
		listGrid.setBaseStyle("myAltRecGridCell");
		
		// inline editting
		listGrid.setCanEdit(true);
		listGrid.setModalEditing(true);
		listGrid.setListEndEditAction(RowEndEditAction.STOP);
		listGrid.setSaveByCell(false);
		listGrid.setAutoSaveEdits(false);
		
		
		Criteria criteria = _form.getValuesAsCriteria();
		listGrid.setCriteria(criteria);
		
		AS2ListGridField id_dnevne_evidencije = new AS2ListGridField(EVIDENCIJA__ID_DNEVNE_EVIDENCIJE);
		id_dnevne_evidencije.getField().setHidden(true);
		AS2ListGridField radnik_id = new AS2ListGridField(EVIDENCIJA__RADNIK_ID,AS2Field.COMBO);
		radnik_id.getField().setCanEdit(false);
		radnik_id.setWidth("20%");
		AS2ListGridField element_obracuna_id = new AS2ListGridField(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID,AS2Field.COMBO);
		element_obracuna_id.getField().setCanEdit(true);
		FormItemIcon icon = new FormItemIcon();
		icon.setSrc(AS2Resources.HELP_PATH);
		icon.setName("hint");
		icon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				if(event.getIcon().getName().equals("hint"))
					SC.say(HELPTEXT);  

			}
		});
		element_obracuna_id.setIcons(icon);
//		 SortSpecifier[] sort =
//		            new SortSpecifier[] { new SortSpecifier(element_obracuna_id.getField().getName(), SortDirection.ASCENDING )/*,
//		                new SortSpecifier( "f_display_reihenfolge", SortDirection.ASCENDING ) */};
//		((ComboBoxItem)element_obracuna_id.F_ITEM_EDITOR).setPickListSort(sort);
//		element_obracuna_id.F_ITEM_EDITOR.addIconClickHandler(new IconClickHandler() {  
//            public void onIconClick(IconClickEvent event) {  
//            	if(event.getIcon().getName().equals("hint"))
//            		SC.say(HELPTEXT);  
//            }  
//        });  
		element_obracuna_id.setWidth("20%");
//		element_obracuna_id.getField().setRequired(true);
//		element_obracuna_id.setEditorProperties(element_obracuna_id.F_ITEM_EDITOR);
		if(_window==false){
			element_obracuna_id.setDropDownList(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID+"__skraceno");
		}
		AS2ListGridField datum = new AS2ListGridField(EVIDENCIJA__DATUM,"8%");
		datum.getField().setCanEdit(false);
		datum.getField().setAlign(Alignment.CENTER); 
		AS2ListGridField radno_vrijeme_od = new AS2ListGridField(EVIDENCIJA__RADNO_VRIJEME_OD,"8%");
		radno_vrijeme_od.getField().setAlign(Alignment.CENTER); 
		radno_vrijeme_od.getField().setFormat("HH:mm");
		radno_vrijeme_od.getField().setRequired(true);
		AS2ListGridField radno_vrijeme_do = new AS2ListGridField(EVIDENCIJA__RADNO_VRIJEME_DO,"8%");
		radno_vrijeme_do.getField().setAlign(Alignment.CENTER); 
		radno_vrijeme_do.getField().setFormat("HH:mm");
		radno_vrijeme_do.getField().setRequired(true);
		AS2ListGridField obracun_sati = new AS2ListGridField(EVIDENCIJA__OBRACUN_SATI,"4%");
//		obracun_sati.getField().setCanEdit(false);
		obracun_sati.getField().setAlign(Alignment.CENTER);
		obracun_sati.getField().setRequired(true);
		AS2ListGridField obracun_minuta = new AS2ListGridField(EVIDENCIJA__OBRACUN_MINUTA,"4%");
//		obracun_minuta.getField().setCanEdit(false);
		obracun_minuta.getField().setAlign(Alignment.CENTER);
		obracun_minuta.getField().setRequired(true);
		AS2ListGridField korisnik = new AS2ListGridField(EVIDENCIJA__KORISNIK);
		korisnik.getField().setHidden(true);
		AS2ListGridField vrijeme_izmjene = new AS2ListGridField(EVIDENCIJA__VRIJEME_IZMJENE);
		vrijeme_izmjene.getField().setHidden(true);
		AS2ListGridField  organizacijska_jedinica_id = new AS2ListGridField(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID,AS2Field.COMBO);
		organizacijska_jedinica_id.getField().setCanEdit(false);
		organizacijska_jedinica_id.getField().setHidden(true);
		organizacijska_jedinica_id.setWidth("20%");
		AS2ListGridField id_spica_oj = new AS2ListGridField(EVIDENCIJA__ID_SPICA_OJ,AS2Field.COMBO);
		id_spica_oj.getField().setCanEdit(false);
		id_spica_oj.getField().setHidden(true);
		id_spica_oj.setWidth("20%");
		AS2ListGridField oznaka_maticne_evidencije = new AS2ListGridField(EVIDENCIJA__OZNAKA_MATICNE_EVIDENCIJE,"8%");
		oznaka_maticne_evidencije.getField().setCanEdit(false);
		oznaka_maticne_evidencije.getField().setHidden(true);
		AS2ListGridField radni_dan = new AS2ListGridField(EVIDENCIJA__RADNI_DAN,AS2Field.COMBO);
		radni_dan.setWidth("6%");
		radni_dan.getField().setCanEdit(false);
		radni_dan.getField().setAlign(Alignment.CENTER);
		AS2ListGridField napomena_spica = new AS2ListGridField(EVIDENCIJA__NAPOMENA_SPICA,"20%");
		napomena_spica.getField().setCanEdit(false);
		AS2ListGridField napomena = new AS2ListGridField(EVIDENCIJA__NAPOMENA,"20%");
		AS2ListGridField dodatni_unos = new AS2ListGridField(EVIDENCIJA__DODATNI_UNOS);
		dodatni_unos.getField().setHidden(true);
		AS2ListGridField potvrda_icon = new AS2ListGridField(EVIDENCIJA__POTVRDA_ICON,AS2Field.IMAGE);
		potvrda_icon.getField().setShowTitle(false);
		potvrda_icon.getField().setAlign(Alignment.CENTER);  
		potvrda_icon.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/s16/excel/");  
		potvrda_icon.getField().setImageURLSuffix(".png");
		potvrda_icon.setWidth(24);
		potvrda_icon.getField().setCanSort(false);
		potvrda_icon.getField().setCanEdit(false);
		potvrda_icon.getField().setShowHover(true);
		potvrda_icon.getField().setHoverCustomizer(new HoverCustomizer() {
			@Override 
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) {
				String potvrda = record.getAttributeAsString(EVIDENCIJA__POTVRDA);
				if(potvrda != null && potvrda.length()>0)
					return potvrda;
				return "Nije potvrđeno!";
			}
		});
		
		AS2ListGridField datum_mj_prvi = new AS2ListGridField(EVIDENCIJA__DATUM_MJ_PRVI);
		datum_mj_prvi.getField().setHidden(true);
		AS2ListGridField datum_mj_zadnji = new AS2ListGridField(EVIDENCIJA__DATUM_MJ_ZADNJI);
		datum_mj_zadnji.getField().setHidden(true);
		listGrid.setDropDownModel(getSifrarnikModel(),radnik_id,radni_dan,element_obracuna_id,organizacijska_jedinica_id,id_spica_oj);		
//		listGrid.setOptionDataSource(getSifrarnikModel(), radnik_id,radni_dan,element_obracuna_id,organizacijska_jedinica_id,id_spica_oj);
		listGrid.setFields(id_dnevne_evidencije, oznaka_maticne_evidencije,
				radnik_id, element_obracuna_id, datum, radno_vrijeme_od,
				radno_vrijeme_do, obracun_sati, obracun_minuta, korisnik,
				vrijeme_izmjene, organizacijska_jedinica_id, id_spica_oj,
				radni_dan, napomena_spica, dodatni_unos, napomena,
				potvrda_icon, datum_mj_prvi, datum_mj_zadnji);
		//handlers
		radno_vrijeme_od.getField().addChangedHandler(new com.smartgwt.client.widgets.grid.events.ChangedHandler() {
			@Override
			public void onChanged(com.smartgwt.client.widgets.grid.events.ChangedEvent event) {
//				Record record = _listGrid.getEditedRecord(event.getRowNum());
//				record.setAttribute(EVIDENCIJA__RADNO_VRIJEME_OD,event.getValue());
				izracunajSateMinute(event.getRowNum());
			}
		});
		radno_vrijeme_do.getField().addChangedHandler(new com.smartgwt.client.widgets.grid.events.ChangedHandler() {
			@Override
			public void onChanged(com.smartgwt.client.widgets.grid.events.ChangedEvent event) {
//				Record record = _listGrid.getEditedRecord(event.getRowNum());
//				record.setAttribute(EVIDENCIJA__RADNO_VRIJEME_DO,event.getValue());
				izracunajSateMinute(event.getRowNum());
			}
		});
		element_obracuna_id.getField().addChangedHandler(new com.smartgwt.client.widgets.grid.events.ChangedHandler() {
			@Override
			public void onChanged(com.smartgwt.client.widgets.grid.events.ChangedEvent event) {
				Record record = listGrid.getEditedRecord(event.getRowNum());
				if(event.getItem().getValue() != null && record.getAttributeAsObject(EVIDENCIJA__RADNI_DAN)!=null
						&& record.getAttribute(EVIDENCIJA__RADNI_DAN).equals("0")
						&& record.getAttribute(EVIDENCIJA__RADNO_VRIJEME_OD)==null && record.getAttribute(EVIDENCIJA__RADNO_VRIJEME_DO)==null
						&& record.getAttribute(EVIDENCIJA__OBRACUN_SATI)==null && record.getAttribute(EVIDENCIJA__OBRACUN_MINUTA)==null){
					_listGrid.setFieldValue(EVIDENCIJA__RADNO_VRIJEME_OD,"07:00:00");
					_listGrid.setFieldValue(EVIDENCIJA__RADNO_VRIJEME_DO,"15:00:00");
					_listGrid.setFieldValue(EVIDENCIJA__OBRACUN_SATI,8);
					_listGrid.setFieldValue(EVIDENCIJA__OBRACUN_MINUTA,0);
				}
			}
		});
		
		listGrid.addEditCompleteHandler(new EditCompleteHandler() {
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				if(_refresh){
					dodajPotvrdu();
				}
			}
		});
		
		return listGrid;
	}
	
	private void izracunajSateMinute(int rowNum){
		int sati = 8;
		int minute = 0;
		Record record = _listGrid.getEditedRecord(rowNum);
			if(record.getAttributeAsObject(EVIDENCIJA__RADNO_VRIJEME_OD)!=null && 
					record.getAttributeAsObject(EVIDENCIJA__RADNO_VRIJEME_DO)!=null){
				
				int rv_od_h = Integer.parseInt(DateTimeFormat.getFormat("HH").format(record.getAttributeAsDate(EVIDENCIJA__RADNO_VRIJEME_OD)));
				int rv_od_m = Integer.parseInt(DateTimeFormat.getFormat("mm").format(record.getAttributeAsDate(EVIDENCIJA__RADNO_VRIJEME_OD)));
				int rv_do_h = Integer.parseInt(DateTimeFormat.getFormat("HH").format(record.getAttributeAsDate(EVIDENCIJA__RADNO_VRIJEME_DO)));
				int rv_do_m = Integer.parseInt(DateTimeFormat.getFormat("mm").format(record.getAttributeAsDate(EVIDENCIJA__RADNO_VRIJEME_DO)));
				
				sati = rv_do_h - rv_od_h;
				minute = 0; 
				if(rv_do_m<rv_od_m){
					sati -= 1;
					minute = 60;
				}
				minute += rv_do_m - rv_od_m;
				if(sati<0){
					SC.warn("Krivo vrijeme","Unijeli ste krivo vrijeme!");
					return;
				}
				_listGrid.setFieldValue(EVIDENCIJA__OBRACUN_SATI,sati);
				_listGrid.setFieldValue(EVIDENCIJA__OBRACUN_MINUTA,minute);
		}
	}
	
	private void dodajPotvrdu() {
		if(_window){
			Criteria criteria = _form.getValuesAsCriteria();//new Criteria();
			_listGrid.setCriteria(criteria);
			_listGrid.invalidateCache();
		}else{
			Criteria criteria = _form.getValuesAsCriteria();
			_listGrid.fetchData(criteria,new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					_listGrid.setData(dsResponse.getData());
				}
			},new DSRequest(DSOperationType.CUSTOM,"dodajPotvrdu"));
		}
	}
	
	/*********** ROLLOVER ************/
	private void prepareRollOver(final ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
	}
	@Override
	protected ToolStrip getToolStrip() {
		ToolStrip buttons_layout = new ToolStrip();
	
		Menu menu = new Menu();
		menu.setCanSelectParentItems(true);
		
		MenuItem mj_pregled_djelatnik = new MenuItem("Mjesečni pregled djelatnika");
		mj_pregled_djelatnik.setIcon(AS2Resources.ADD_FILES_PATH);
		mj_pregled_djelatnik.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite djelatnika");
				} else{
					Record new_record = _listGrid.getRecord(_listGrid.getDataAsRecordList().indexOf(_listGrid.getSelectedRecord()));
					new_record.setAttribute(EVIDENCIJA__DATUM_OD, new_record.getAttributeAsDate(EVIDENCIJA__DATUM_MJ_PRVI));
					new_record.setAttribute(EVIDENCIJA__DATUM_DO, new_record.getAttributeAsDate(EVIDENCIJA__DATUM_MJ_ZADNJI));
					new_record.setAttribute("window", "true");
					new EvidencijaMjesecnaMWindow(EvidencijaDolazakaView.this, new_record);
				}
			}
		});
		
		MenuItem mj_pregled_oj = new MenuItem("Mjesečni pregled po odjelu");
		mj_pregled_oj.setIcon(AS2Resources.PDF_ICON_PATH);
		mj_pregled_oj.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaDolazakaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiJasper");
        		report.setParameter("@@report_selected", "mjesecni_oj_po_danima");
        		report.setParameter(EVIDENCIJA__DATUM_OD, DateTimeFormat.getFormat("yyyy-MM-dd").format(form.getAttributeAsDate(EVIDENCIJA__DATUM_OD)));
        		report.setParameter(EVIDENCIJA__DATUM_DO, DateTimeFormat.getFormat("yyyy-MM-dd").format(form.getAttributeAsDate(EVIDENCIJA__DATUM_DO)));
        		report.setParameter(EVIDENCIJA__ID_SPICA_OJ, form.getAttribute(EVIDENCIJA__ID_SPICA_OJ));
        		report.getJasperReport("mjesecni_oj_po_danima_cn");
			}
		});
		
		MenuItem stanje_sati = new MenuItem("Stanje sati (prema odabranom odjelu)");
		stanje_sati.setIcon(AS2Resources.EXCELX_ICON_PATH);
		stanje_sati.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaDolazakaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiXls");
        		report.setParameter("@@report_selected", "stanje_sati");
        		report.setParameter(EVIDENCIJA__DATUM_OD, DateTimeFormat.getFormat("yyyy-MM-dd").format(form.getAttributeAsDate(EVIDENCIJA__DATUM_OD)));
        		report.setParameter(EVIDENCIJA__DATUM_DO, DateTimeFormat.getFormat("yyyy-MM-dd").format(form.getAttributeAsDate(EVIDENCIJA__DATUM_DO)));
//        		report.setParameter(EVIDENCIJA__DATUM_DO, new java.sql.Date(form.getAttributeAsDate(EVIDENCIJA__DATUM_DO).getTime()).toString());
        		report.setParameter(EVIDENCIJA__ID_SPICA_OJ, form.getAttribute(EVIDENCIJA__ID_SPICA_OJ));
            	report.getExcelReport();
			}
		});
		
		MenuItem buduce_razdoblje = new MenuItem("Unos kat. prisutnosti budućeg razdoblja");
		buduce_razdoblje.setIcon(AS2Resources.ADD_FILES_PATH);
		buduce_razdoblje.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite djelatnika");
				} else{
					Record form = _listGrid.getSelectedRecord();
					Record new_record = new Record();
					new_record.setAttribute(EVIDENCIJA__RADNIK_ID, form.getAttribute(EVIDENCIJA__RADNIK_ID));
					new_record.setAttribute(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID, form.getAttribute(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID));
//					new_record.setAttribute(EVIDENCIJA__KORISNIK, AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME));
					new EvidencijaBuduceRazdobljeMWindow(EvidencijaDolazakaView.this, new_record);
				}
			}
		});
		
		
		MenuItem ispravak = new MenuItem("Ispravak šifre kategorije");
		ispravak.setIcon(AS2Resources.ADD_FILES_PATH);
		ispravak.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Record form = _form.getValuesAsRecord();
				Record new_record = new Record();
				new_record.setAttribute(EVIDENCIJA__RADNIK_ID, form.getAttribute(EVIDENCIJA__RADNIK_ID));
				new_record.setAttribute(EVIDENCIJA__DATUM_OD, form.getAttributeAsDate(EVIDENCIJA__DATUM_OD));
				new_record.setAttribute(EVIDENCIJA__DATUM_DO, form.getAttributeAsDate(EVIDENCIJA__DATUM_DO));
				new EvidencijaIspravakKategorijeMWindow(EvidencijaDolazakaView.this, new_record);
			}
		});
		if(_window)
			menu.setData(ispravak);
		else
			menu.setData(mj_pregled_djelatnik,mj_pregled_oj,stanje_sati,buduce_razdoblje);
		
		ToolStripMenuButton mainMenuButton = new ToolStripMenuButton("Dodatne opcije", menu);
		buttons_layout.addMenuButton(mainMenuButton);
		
		toolbarAdd = new ToolStripButton("Novo");
		toolbarAdd.setIcon(AS2Resources.INSTANCE.add_icon().getSafeUri().asString());
//		_button_novo.setAutoFit(true);
		toolbarAdd.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				Record rec = _listGrid.getDataAsRecordList().get(0);
//				LinkedHashMap<String, String> defaultValues = new LinkedHashMap<>();
//				for (String value : rec.getAttributes()){
//					defaultValues.put(value, rec.getAttribute(value));
//				}
//				defaultValues.put(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID, _form.getField(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID).getValue().toString());
				_listGrid.deselectAllRecords();
				_listGrid.getField(EVIDENCIJA__RADNIK_ID).setCanEdit(true);
				_listGrid.getField(EVIDENCIJA__DATUM).setCanEdit(true);
				_listGrid.getField(EVIDENCIJA__RADNIK_ID).setRequired(true);
				_listGrid.getField(EVIDENCIJA__DATUM).setRequired(true);
								
				_listGrid.startEditingNew(/*defaultValues*/);
			}
		});
		_button_potvrdi = new ToolStripButton("Potvrdi");
		if(_window)
			_button_potvrdi.setTitle("Spremi izmjene");
		_button_potvrdi.setIcon(AS2Resources.SAVE_PATH);
		_button_potvrdi.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(_form.getField(EVIDENCIJA__DATUM_OD).getValue().toString().equals(_form.getField(EVIDENCIJA__DATUM_DO).getValue().toString()) || 
						_form.getField("window").getValue().toString().equals("true")){
					if(_listGrid.getAllEditRows().length!=0){
						int lastRow = _listGrid.getAllEditRows()[_listGrid.getAllEditRows().length-1];
						for(int rowNum:_listGrid.getAllEditRows()){
							_listGrid.saveEdits(EditCompletionEvent.PROGRAMMATIC,null, rowNum);
							if(rowNum==lastRow){
								_refresh=true; //TODO ne osvježava dobro kada je dodaj i ažuriraj u istom potvrđivanju
							}else{
								_refresh=false;
							}
						}
					}else{
						dodajPotvrdu();
					}
						
				}else {
					SC.say("Upozorenje!","Nije moguće potvrditi raspon datuma veći od jedan dan.");
				}
			}
		});
		
		_button_excel = new ToolStripButton("");
		_button_excel.setIcon(AS2Resources.EXCELX_ICON_PATH);
		_button_excel.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaDolazakaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiXls");
				Record form = _form.getValuesAsRecord();
				for(String attribute: form.getAttributes()){
					report.setParameter(attribute,form.getAttribute(attribute));
				}
        		report.setParameter("@@report_selected", "stanje_sati");
        		report.setParameter(EVIDENCIJA__DATUM_OD, new java.sql.Date(form.getAttributeAsDate(EVIDENCIJA__DATUM_OD).getTime()).toString());
        		report.setParameter(EVIDENCIJA__DATUM_DO, new java.sql.Date(form.getAttributeAsDate(EVIDENCIJA__DATUM_DO).getTime()).toString());
        		report.setParameter(EVIDENCIJA__ID_SPICA_OJ, form.getAttribute(EVIDENCIJA__ID_SPICA_OJ));
            	report.getExcelReport();
			}
		});
		
		toolbarPreview = new ToolStripButton("Ispis");
		toolbarPreview.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		toolbarPreview.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout print = new VLayout();
				print.addMember(_listGrid);
				Canvas.showPrintPreview(print);
				EvidencijaDolazakaView.this.setMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
			}
		});
		if(_window)
			buttons_layout.setMembers(new LayoutSpacer(),mainMenuButton,/*_button_excel,*/toolbarPreview,toolbarAdd,_button_potvrdi);
		else
			buttons_layout.setMembers(new LayoutSpacer(),mainMenuButton,/*_button_excel,*/toolbarPreview,_button_potvrdi);
		return buttons_layout;
	}
	@Override
	protected String getRecordIdName() {
		return EVIDENCIJA__ID_DNEVNE_EVIDENCIJE;
	}
	
}
