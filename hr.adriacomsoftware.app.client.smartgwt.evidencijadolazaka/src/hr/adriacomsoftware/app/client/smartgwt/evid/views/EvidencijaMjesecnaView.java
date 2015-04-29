package hr.adriacomsoftware.app.client.smartgwt.evid.views;
//
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaMjesecnaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaMjesecnaModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.services.AS2ReportingServices;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * @author msekso, astrikoman
 *
 */ 
public class EvidencijaMjesecnaView extends AS2View2 implements  EvidencijaMjesecnaConstants {

	private HLayout rollOverCanvas;
	//private ToolStrip _buttons_layout;
	private ToolStripButton _button_ispis;
	private ToolStripButton _button_vrste_primanja;
	private ToolStripButton _button_prebaci_podatke;
	private ToolStripButton _button_bolovanje_hzzo;

	public EvidencijaMjesecnaView(String name) {
		super(name);
	}
	
	@Override
	public void customWindowPreferences() {
		this.setWidth100();
		this.setHeight100();
		use_form=true;
		use_listgrid=true;
		number_of_columns = 5;
	}
	@Override
	public void windowLayout() {
		_toolStrip=getToolStrip();
		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
	}
	@Override
	public DataSource getModel() {
		return EvidencijaMjesecnaModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
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
		final AS2FormItem mjesec = new AS2FormItem(EVIDMJ__MJESEC,AS2Field.COMBO);
		mjesec.setWidth(60);
		mjesec.getField().setDefaultValue(new Date().getMonth()==0?12:new Date().getMonth());
		final AS2FormItem godina = new AS2FormItem(EVIDMJ__GODINA);
		godina.setWidth(60);
		godina.getField().setAttribute("length", "4");
		godina.getField().setDefaultValue(new Date().getMonth()==0?new Date().getYear()+1900-1:new Date().getYear()+1900);
		AS2FormItem button_prikazi = new AS2FormItem("button_prikazi", AS2Field.FORM_BUTTON, "Prikaži");
		button_prikazi.setStartRow(false);
		button_prikazi.setWidth(100);
		
		//handlers
		button_prikazi.getField().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = _form.getValuesAsCriteria();
				_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.FETCH));
			}
		});
		form.setDataSource(getModel());
		form.setFields(mjesec,godina,button_prikazi);
		form.setDropDownModel(getSifrarnikModel(), mjesec);
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
			}
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				if (getFieldName(colNum).equals(EVIDMJ__UKUPNI_BROJ_SATI) && record.getAttribute(EVIDMJ__UKUPNI_BROJ_SATI)!=null) {  
						return "color:#15428B;";  
				}else if (getFieldName(colNum).equals("napomena_spica") && record.getAttribute("napomena_spica")!=null) {  
					if (!record.getAttribute("napomena_spica").startsWith("0")||  record.getAttribute("napomena_spica").contains("Trajanje 1")
							||  record.getAttribute("napomena_spica").contains("Trajanje 00:00")) {  
						return "color:red;";  
					}
				}
				return super.getCellCSSText(record, rowNum, colNum);  
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setAutoFetchData(true);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
//		listGrid.setFixedRecordHeights(false); //TODO užasno sporo, pogledati link za eventualno rješenje http://forums.smartclient.com/archive/index.php/t-5193.html
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		listGrid.setBaseStyle("myBoxedGridCell");
		
		// filtering
		listGrid.setShowFilterEditor(true);
//		listGrid.setFilterOnKeypress(true);
		
		
//		AdvancedCriteria initialCriteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{  
//                new Criterion(EVIDMJ__RADNIK_ID, OperatorId.EQUALS),  
//        });  
		
		
		// inline editting
		listGrid.setCanEdit(false);
//		listGrid.setModalEditing(true);
//		listGrid.setListEndEditAction(RowEndEditAction.STOP);
//		listGrid.setSaveByCell(false);
//		listGrid.setAutoSaveEdits(false);
		
		//listGrid.setAutoFetchData(false);
		Criteria criteria = _form.getValuesAsCriteria();
        listGrid.setCriteria(criteria);
		
//        AdvancedCriteria ac = new AdvancedCriteria(OperatorId.AND);  
//        Criterion c = new Criterion();  
//        c.setFieldName(EVIDMJ__RADNIK_ID);  
//        ac.addCriteria(c);  
//        criteria.addCriteria(ac);

		AS2ListGridField radnik_id = new AS2ListGridField(EVIDMJ__RADNIK_ID,"180");
		AS2ListGridField id_spica_oj = new AS2ListGridField(EVIDMJ__ID_SPICA_OJ);
		AS2ListGridField naziv = new AS2ListGridField(EVIDMJ__NAZIV,"180");
		naziv.getField().setFrozen(true);  
		AS2ListGridField datum_od = new AS2ListGridField(EVIDMJ__DATUM_OD);
		AS2ListGridField datum_do = new AS2ListGridField(EVIDMJ__DATUM_DO);
		AS2ListGridField broj_dana_rada = new AS2ListGridField(EVIDMJ__BROJ_DANA_RADA,"100");
		broj_dana_rada.getField().setFrozen(true);
		AS2ListGridField ukupni_broj_sati = new AS2ListGridField(EVIDMJ__UKUPNI_BROJ_SATI,"60");
		ukupni_broj_sati.getField().setFrozen(true);
		AS2ListGridField sif_01 = new AS2ListGridField(EVIDMJ__SIF_01,"60");
//		sif_01.getField().setPrompt("Location of seat of government");
		AS2ListGridField sif_01_10 = new AS2ListGridField(EVIDMJ__SIF_01_10,"60");
		AS2ListGridField sif_03 = new AS2ListGridField(EVIDMJ__SIF_03,"60");
		AS2ListGridField sif_10 = new AS2ListGridField(EVIDMJ__SIF_10,"60");
		AS2ListGridField sif_12 = new AS2ListGridField(EVIDMJ__SIF_12,"60");
		AS2ListGridField sif_23 = new AS2ListGridField(EVIDMJ__SIF_23,"60");
		AS2ListGridField sif_09 = new AS2ListGridField(EVIDMJ__SIF_09,"60");
		AS2ListGridField sif_01_11 = new AS2ListGridField(EVIDMJ__SIF_01_11,"60");
		AS2ListGridField sif_01_12 = new AS2ListGridField(EVIDMJ__SIF_01_12,"60");
		AS2ListGridField sif_01_13 = new AS2ListGridField(EVIDMJ__SIF_01_13,"60");
		AS2ListGridField sif_27 = new AS2ListGridField(EVIDMJ__SIF_27,"60");
		AS2ListGridField sif_01_5 = new AS2ListGridField(EVIDMJ__SIF_01_5,"60");
		AS2ListGridField sif_02_2 = new AS2ListGridField(EVIDMJ__SIF_02_2,"60");
		AS2ListGridField sif_14_2 = new AS2ListGridField(EVIDMJ__SIF_14_2,"60");
		AS2ListGridField sif_14_1 = new AS2ListGridField(EVIDMJ__SIF_14_1,"60");
		AS2ListGridField sif_01_3 = new AS2ListGridField(EVIDMJ__SIF_01_3,"60");
		AS2ListGridField sif_15_2 = new AS2ListGridField(EVIDMJ__SIF_15_2,"60");
		AS2ListGridField sif_01_32 = new AS2ListGridField(EVIDMJ__SIF_01_32,"60");
		AS2ListGridField sif_01_30 = new AS2ListGridField(EVIDMJ__SIF_01_30,"60");
		AS2ListGridField sif_01_31 = new AS2ListGridField(EVIDMJ__SIF_01_31,"60");
		AS2ListGridField sif_14 = new AS2ListGridField(EVIDMJ__SIF_14,"60");
		AS2ListGridField sif_01_6 = new AS2ListGridField(EVIDMJ__SIF_01_6,"60");
		AS2ListGridField sif_15_1 = new AS2ListGridField(EVIDMJ__SIF_15_1,"60");
		AS2ListGridField sif_01_99 = new AS2ListGridField(EVIDMJ__SIF_01_99,"60");
		AS2ListGridField sif_12_1 = new AS2ListGridField(EVIDMJ__SIF_12_1,"60");
		AS2ListGridField sif_11 = new AS2ListGridField(EVIDMJ__SIF_11,"60");
		AS2ListGridField sif_15 = new AS2ListGridField(EVIDMJ__SIF_15,"60");
		AS2ListGridField sif_17 = new AS2ListGridField(EVIDMJ__SIF_17,"60");
		AS2ListGridField sif_22 = new AS2ListGridField(EVIDMJ__SIF_22,"60");
		AS2ListGridField sif_01_1 = new AS2ListGridField(EVIDMJ__SIF_01_1,"60");
		AS2ListGridField sif_01_2 = new AS2ListGridField(EVIDMJ__SIF_01_2,"60");
		AS2ListGridField sif_27_2 = new AS2ListGridField(EVIDMJ__SIF_27_2,"60");
		AS2ListGridField sif_03_2 = new AS2ListGridField(EVIDMJ__SIF_03_2,"60");
		AS2ListGridField sif_01_71 = new AS2ListGridField(EVIDMJ__SIF_01_71,"60");
		AS2ListGridField sif_01_72 = new AS2ListGridField(EVIDMJ__SIF_01_72,"60");
		AS2ListGridField sif_12_2 = new AS2ListGridField(EVIDMJ__SIF_12_2,"60");
		AS2ListGridField sif_18 = new AS2ListGridField(EVIDMJ__SIF_18,"60");
		AS2ListGridField sif_01_4 = new AS2ListGridField(EVIDMJ__SIF_01_4,"60");
		AS2ListGridField sif_12_3 = new AS2ListGridField(EVIDMJ__SIF_12_3,"60");
		AS2ListGridField sif_01_8 = new AS2ListGridField(EVIDMJ__SIF_01_8,"60");
		AS2ListGridField sif_15_0 = new AS2ListGridField(EVIDMJ__SIF_15_0,"60");
		AS2ListGridField sif_12_0 = new AS2ListGridField(EVIDMJ__SIF_12_0,"60");
		AS2ListGridField sif_12_4 = new AS2ListGridField(EVIDMJ__SIF_12_4,"60");
		AS2ListGridField sif_23_1 = new AS2ListGridField(EVIDMJ__SIF_23_1,"60");
		AS2ListGridField sif_999 = new AS2ListGridField(EVIDMJ__SIF_999,"60");
		
		AS2ListGridField mjesec = new AS2ListGridField(EVIDMJ__MJESEC,"60");
		AS2ListGridField godina = new AS2ListGridField(EVIDMJ__GODINA,"60");
		
		listGrid.setFields(radnik_id, id_spica_oj, naziv, datum_od, datum_do,
				broj_dana_rada, ukupni_broj_sati, sif_01, sif_01_10, sif_03,
				sif_10, sif_12, sif_23, sif_09, sif_01_11, sif_01_12, sif_01_13, sif_27,
				sif_01_5, sif_02_2, sif_14_2, sif_14_1, sif_01_3, sif_15_2,
				sif_01_32, sif_01_30, sif_01_31, sif_14, sif_01_6, sif_15_1,
				sif_01_99, sif_12_1, sif_11, sif_15, sif_17, sif_22, sif_23,
				sif_01_1, sif_01_2, sif_27_2, sif_03_2, sif_01_71, sif_01_72,
				sif_12_2, sif_18, sif_01_4, sif_12_3, sif_01_8, sif_15_0,
				sif_12_0, sif_12_4, sif_23_1, sif_999, mjesec, godina);

		listGrid.setAlignFields(Alignment.CENTER, broj_dana_rada,
				ukupni_broj_sati, sif_01, sif_01_10, sif_03, sif_10, sif_12,
				sif_23, sif_09, sif_01_11, sif_01_12, sif_01_13, sif_27, sif_01_5,
				sif_02_2, sif_14_2, sif_14_1, sif_01_3, sif_15_2, sif_01_32,
				sif_01_30, sif_01_31, sif_14, sif_01_6, sif_15_1, sif_01_99,
				sif_12_1, sif_11, sif_15, sif_17, sif_22, sif_23, sif_01_1,
				sif_01_2, sif_27_2, sif_03_2, sif_01_71, sif_01_72, sif_12_2,
				sif_18, sif_01_4, sif_12_3, sif_01_8, sif_15_0, sif_12_0,
				sif_12_4, sif_23_1, sif_999);
		
		listGrid.setHiddenFields(true, radnik_id, /*organizacijska_jedinica_id,*/
				id_spica_oj, datum_od, datum_do, mjesec, godina);
		
		// summary
		listGrid.setShowGridSummary(true);
		listGrid.setShowGridSummaryFields(false,naziv,broj_dana_rada, ukupni_broj_sati);
        SummaryFunction function = new SummaryFunction() {
			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				int number=0;
				for(Record record:records){
					if(record.getAttributeAsObject(field.getName())!=null && !record.getAttribute(field.getName()).equals("") )
						number++;
				}
                return number;  
			}
		};
		listGrid.setSummaryFunctionFields(function,sif_01, sif_01_10, sif_03, sif_10, sif_12,
				sif_23, sif_09, sif_01_11, sif_01_12, sif_01_13, sif_27, sif_01_5,
				sif_02_2, sif_14_2, sif_14_1, sif_01_3, sif_15_2, sif_01_32,
				sif_01_30, sif_01_31, sif_14, sif_01_6, sif_15_1, sif_01_99,
				sif_12_1, sif_11, sif_15, sif_17, sif_22, sif_23, sif_01_1,
				sif_01_2, sif_27_2, sif_03_2, sif_01_71, sif_01_72, sif_12_2,
				sif_18, sif_01_4, sif_12_3, sif_01_8, sif_15_0, sif_12_0,
				sif_12_4, sif_23_1);
		
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				editRecord();
			}
		});

		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	private void prepareRollOver(final ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						editRecord();
					}
				}));
	}
	
	private void editRecord() {
		Record new_record = _listGrid.getRecord(_listGrid.getDataAsRecordList().indexOf(AS2RolloverButtonFactory._rollOverRecord));
		new EvidencijaMjesecnaMWindow(EvidencijaMjesecnaView.this, new_record);
	}
	protected ToolStrip getToolStrip() {
		ToolStrip buttons_layout = new ToolStrip();
	
//		buttons_layout.setAlign(Alignment.RIGHT);
		_button_ispis = new ToolStripButton("Ispis");
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_ispis.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaMjesecnaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiJasper");
				report.setParameter("@@report_selected", "mjesecni");
				report.setParameter(EVIDMJ__MJESEC, form.getAttribute(EVIDMJ__MJESEC));
				report.setParameter(EVIDMJ__GODINA, form.getAttribute(EVIDMJ__GODINA));
				report.getJasperReport("izvjestaj_mjesecni_cn");
//				VLayout print = new VLayout();
//				print.addMember(_listGrid);
//				Canvas.showPrintPreview(print);
//				EvidencijaMjesecnaView.this.setMembers(_form,_buttons_layout,_listGrid,_listGrid.getStatusBar());
			}
		});

		_button_vrste_primanja = new ToolStripButton("Vrste primanja");
		_button_vrste_primanja.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_vrste_primanja.setAutoFit(true);
		_button_vrste_primanja.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaMjesecnaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiJasper");
				report.setParameter("@@report_selected", "vrste_primanja");
				report.getJasperReport("vrste_primanja_cn");
            	/*AS2ReportingServices report = new AS2ReportingServices(EvidencijaMjesecnaView.this,  "hr.adriacomsoftware.app.server.evidencijadolazaka.reports.PlaceReportServer", "izvjestajiXls");
        		report.setParameter("@@report_selected", "vrste_primanja");
            	report.getExcelReport();*/
			}
		});
		
		_button_bolovanje_hzzo = new ToolStripButton("Bolovanje HZZO");
		_button_bolovanje_hzzo.setIcon(AS2Resources.EXCELX_ICON_PATH);
		_button_bolovanje_hzzo.setAutoFit(true);
		_button_bolovanje_hzzo.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Record form = _form.getValuesAsRecord();
				AS2ReportingServices report = new AS2ReportingServices(EvidencijaMjesecnaView.this, EvidencijaDolazakaModel.REPORT_SERVER, "izvjestajiXls");
        		report.setParameter("@@report_selected", "bolovanje_hzzo");
        		report.setParameter(EVIDMJ__MJESEC, form.getAttribute(EVIDMJ__MJESEC));
				report.setParameter(EVIDMJ__GODINA, form.getAttribute(EVIDMJ__GODINA));
            	report.getExcelReport();
			}
		});
		
		_button_prebaci_podatke = new ToolStripButton("Prebaci podatke");
		_button_prebaci_podatke.setIcon(AS2Resources.TRANSFER_ICON);
		_button_prebaci_podatke.setAutoFit(true);
		_button_prebaci_podatke.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				DataSource dataSource = getModel();
				Criteria criteria = _form.getValuesAsCriteria();
				criteria.setAttribute("@@process_selected", "mjesecna_obrada");
				dataSource.fetchData(criteria,new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						if(dsResponse.getStatus()>=0)
							SC.say(dsResponse.getDataAsRecordList().get(0).getAttribute("napomena"));
					}
				},new DSRequest(DSOperationType.CUSTOM,"pokreniObradu"));
			}
		});
		buttons_layout.setMembers(new LayoutSpacer(),_button_vrste_primanja,_button_bolovanje_hzzo,_button_ispis,_button_prebaci_podatke);
		return buttons_layout;
	}

	@Override
	protected String getRecordIdName() {
		return EVIDMJ__RADNIK_ID;
	}
}
