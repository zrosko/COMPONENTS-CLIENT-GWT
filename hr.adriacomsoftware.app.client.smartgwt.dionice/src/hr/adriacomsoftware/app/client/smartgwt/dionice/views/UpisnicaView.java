package hr.adriacomsoftware.app.client.smartgwt.dionice.views;

import hr.adriacomsoftware.app.client.smartgwt.dionice.models.DioniceSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.dionice.models.UpisnicaConstants;
import hr.adriacomsoftware.app.client.smartgwt.dionice.models.UpisnicaModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * @author astrikoman
 *
 */ 
public class UpisnicaView extends AS2View2 implements  UpisnicaConstants {
	private static final String DUPLICIRAJ_TOOLBAR_BUTTON_DISPLAY_NAME = "Dupliciraj";
	private static final String IZVJESTAJ_TOOLBAR_BUTTON_DISPLAY_NAME = "Izvještaj";
	public ToolStripButton toolbarDupliciraj;
	public ToolStripButton toolbarIzvjestaj;
	private HLayout rollOverCanvas;
	protected UpisnicaMWindow upisnica_modal;

	public UpisnicaView(String name) {
		super(name);
	}
	@Override
	public void customWindowPreferences() {
		use_form=false;
		use_listgrid=true;
	}
	
	@Override
	public DataSource getModel() {
		return UpisnicaModel.getInstance();
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
		return DioniceSifrarnikModel.getInstance();
	}
	
	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}
	
	@Override
	public AS2ListGrid getListGrid() {
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
		AS2ListGridField id_upisnica = new AS2ListGridField(DION__ID_UPISNICA);
		AS2ListGridField id_ponude = new AS2ListGridField(DION__ID_PONUDE,AS2Field.SELECT);
		AS2ListGridField broj_zaprimanja = new AS2ListGridField(DION__BROJ_ZAPRIMANJA);
		AS2ListGridField racun_vlasnika = new AS2ListGridField(DION__RACUN_VLASNIKA);
		AS2ListGridField upisnik = new AS2ListGridField(DION__UPISNIK);
		AS2ListGridField vrsta_upisnika = new AS2ListGridField(DION__VRSTA_UPISNIKA,AS2Field.SELECT);
		AS2ListGridField mbg_mb = new AS2ListGridField(DION__MBG_MB);
		AS2ListGridField oib = new AS2ListGridField(DION__OIB);
		AS2ListGridField adresa = new AS2ListGridField(DION__ADRESA);
		AS2ListGridField kontakt_osoba = new AS2ListGridField(DION__KONTAKT_OSOBA);
		AS2ListGridField kontakt_adresa = new AS2ListGridField(DION__KONTAKT_ADRESA);
		AS2ListGridField telefon_fax = new AS2ListGridField(DION__TELEFON_FAX);
		AS2ListGridField mobitel = new AS2ListGridField(DION__MOBITEL);
		AS2ListGridField email = new AS2ListGridField(DION__EMAIL);
		AS2ListGridField iban_upisnika = new AS2ListGridField(DION__IBAN_UPISNIKA);
		AS2ListGridField banka_skrbnik_upisnika = new AS2ListGridField(DION__BANKA_SKRBNIK_UPISNIKA);
		AS2ListGridField broj_novih_dionica = new AS2ListGridField(DION__BROJ_NOVIH_DIONICA);
		AS2ListGridField datum_upisnice = new AS2ListGridField(DION__DATUM_UPISNICE);
		AS2ListGridField datum_zaprimanja = new AS2ListGridField(DION__DATUM_ZAPRIMANJA);
		AS2ListGridField vrijeme_uplate = new AS2ListGridField(DION__VRIJEME_UPLATE);
		AS2ListGridField iznos_uplate = new AS2ListGridField(DION__IZNOS_UPLATE);
		AS2ListGridField status_upisnice = new AS2ListGridField(DION__STATUS_UPISNICE);
		AS2ListGridField broj_dionica_prije = new AS2ListGridField(DION__BROJ_DIONICA_PRIJE);
		AS2ListGridField postotak_udjela = new AS2ListGridField(DION__POSTOTAK_UDJELA);
		AS2ListGridField pravo_upisa = new AS2ListGridField(DION__PRAVO_UPISA);
		AS2ListGridField napomena = new AS2ListGridField(DION__NAPOMENA);
		AS2ListGridField ispravno = new AS2ListGridField(DION__ISPRAVNO);
		//javana ponuda
		AS2ListGridField krug = new AS2ListGridField(DION__KRUG);
		listGrid.setFields(id_upisnica,id_ponude,krug,broj_zaprimanja,racun_vlasnika,upisnik,
				vrsta_upisnika,mbg_mb,oib,adresa,kontakt_osoba,kontakt_adresa,telefon_fax,
				mobitel,email,iban_upisnika,banka_skrbnik_upisnika,broj_novih_dionica,
				datum_upisnice,datum_zaprimanja,vrijeme_uplate,iznos_uplate,status_upisnice,
				broj_dionica_prije,postotak_udjela,pravo_upisa,napomena,ispravno);
		listGrid.setHiddenFields(true,id_upisnica,id_ponude,adresa,kontakt_osoba,kontakt_adresa,
				telefon_fax,mobitel,email,iban_upisnika,banka_skrbnik_upisnika,datum_upisnice,
				datum_zaprimanja,vrijeme_uplate,status_upisnice,napomena,ispravno);
		listGrid.setDropDownModel(getSifrarnikModel(), id_ponude,vrsta_upisnika);
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				actionToolbarPreview(null);
			}
		});
		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	public HLayout getRollOver(final ListGrid listGrid) {
		HLayout rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						actionToolbarPreview(event);
					}
				}));
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null));
		return rollOverCanvas;
	}
	@Override
	protected void prepareToolbarButtons() {
		super.prepareToolbarButtons();
		toolbarAdd.setTitle("Nova upisnica");
		toolbarDupliciraj = new ToolStripButton();
		//toolStrip.addButton(toolbarDupliciraj);
		toolbarDupliciraj.setTitle(DUPLICIRAJ_TOOLBAR_BUTTON_DISPLAY_NAME);
		toolbarDupliciraj.setIcon(AS2Resources.TOOLBAR_DOCUMENT_NEW_ICON_PATH);
		toolbarDupliciraj.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionDupliciraj();
			}
		});
		toolbarIzvjestaj = new ToolStripButton();
		//toolStrip.addButton(toolbarIzvjestaj);
		toolbarIzvjestaj.setTitle(IZVJESTAJ_TOOLBAR_BUTTON_DISPLAY_NAME);
		toolbarIzvjestaj.setIcon(AS2Resources.EXCELX_ICON_PATH);
		toolbarIzvjestaj.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionIzvjestaj();
			}
		});
		//TODO viditi multiple delete u staroj verziji
		
	}
	@Override
	protected ToolStrip getToolStrip() {
		ToolStrip toolStrip = super.getToolStrip();
		toolStrip.setMembers(toolbarPreview, toolbarAdd, toolbarDupliciraj, toolbarIzvjestaj, toolbarDelete, toolbarFilter, toolbarRefresh);
		return toolStrip;
	}
	
	@Override
	protected void actionToolbarAdd(ClickEvent event) {
		_listGrid.deselectAllRecords();
		upisnica_modal = new UpisnicaMWindow(UpisnicaView.this,null);
	}

	@Override
	protected void actionToolbarPreview(ClickEvent event) {
		if (_listGrid.getSelectedRecord() == null) {
			SC.warn("Odaberite upisnicu");
		} else{
			upisnica_modal = new UpisnicaMWindow(UpisnicaView.this,_listGrid.getSelectedRecord());
		}
	}
	
	private void actionDupliciraj() {
		if (_listGrid.getSelectedRecord() == null) {
			SC.warn("Odaberite upisnicu");
		} else{
			upisnica_modal = new UpisnicaMWindow(UpisnicaView.this,_listGrid.getSelectedRecord(),true);
		}

	}
	
	private void actionIzvjestaj() {
		_listGrid.deselectAllRecords();
		AS2ReportingServices report = new AS2ReportingServices(this, "hr.adriacomsoftware.app.server.dionice.reports.DioniceReportServer",  "citajIzvjestajExcel");
		report.getExcelReport();
		//TODO testirati da li radi? ako ne radi, download formu triba dodati na layout
	}
	@Override
	protected String getRecordIdName() {
		return DION__ID_UPISNICA;
	}
}
