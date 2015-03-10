package hr.adriacomsoftware.app.client.smartgwt.kalkulatori.views;

import hr.adriacomsoftware.app.client.smartgwt.kalkulatori.models.ObracunKamateModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;

import java.util.Date;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GridSummaryCustomizer;
import com.smartgwt.client.widgets.grid.GroupSummary;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ObracunKamateView extends AS2View {

	private static int FORM_WIDTH = 450;
	private static Record _record;
	private AS2FormItem vrsta_obracuna;
	private AS2FormItem br_dokumenta;
	private AS2FormItem glavnica;
	private AS2FormItem datum_pocetak;
	private AS2FormItem datum_kraj;
	private AS2FormItem kamatna_stopa;
	private AS2FormItem rok_otplate_jedinica;
	private AS2FormItem iznos_za_obracun_prethodni;
	private AS2FormItem kamata_prethodna; 
	private AS2ListGridField datum_krajGrid;
	private IButton _button_izracunaj;
	private IButton _button_ocisti;
	private IButton _button_ispis;
	private HLayout _buttons_layout;
	private RecordList _recordList;
	private DateRangeValidator dateRangeValidator;
	private Record _formOldValues;

	@Override
	protected void initializeView() {
		number_of_columns = 3;
		this.setOverflow(Overflow.AUTO);
		_recordList = new RecordList();//Resultset tablice
	}
	public void showWidgets() {
		_listGrid=getListGrid();
		_buttons_layout = getFormButtons();
		_form = getDynamicForm();
		this.addMembers(_form,_buttons_layout,_listGrid);
	}
	@Override
	public DataSource getModel() {
		return ObracunKamateModel.getInstance();
	}
	@Override
	public DataSource getSifrarnikModel() {
		return null;
	}
	@Override
	protected AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(null, number_of_columns);
		form.setDataSource(getModel());
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setSaveOnEnter(false);
		form.setStyleNameGray();
		form.setWrapItemTitles(false);
		form.setWidth(FORM_WIDTH);
		form.setColWidths("30%", "35%", "35%");
		form.setShowEdges(true);
		vrsta_obracuna = new AS2FormItem("vrsta_obracuna", AS2Field.FORM_RADIOGROUP, "","*");
		LinkedHashMap<String, Object> vrsta_obracuna_vm = new LinkedHashMap<String, Object>();
		vrsta_obracuna_vm.put("s", "Složeni obračun");
		vrsta_obracuna_vm.put("p", "Prosti obračun");
		vrsta_obracuna.getField().setShowTitle(false);
		vrsta_obracuna.getField().setRowSpan(2);
		vrsta_obracuna.setDefaultValue("s");
		vrsta_obracuna.setValueMap(vrsta_obracuna_vm);
		br_dokumenta = new AS2FormItem("br_dokumenta", AS2Field.TEXT);		
		glavnica = new AS2FormItem("glavnica", AS2Field.AMOUNT);
		FloatRangeValidator glavnicaValidator = new FloatRangeValidator();  
		glavnicaValidator.setMin((float)0.01);  
		glavnicaValidator.setErrorMessage("Mora biti veće od 0");
		glavnica.getField().setValidators(glavnicaValidator);
		datum_pocetak = new AS2FormItem("datum_pocetak", AS2Field.DATE);
		datum_kraj = new AS2FormItem("datum_kraj", AS2Field.DATE);
		dateRangeValidator = new DateRangeValidator();
		datum_kraj.getField().setValidators(dateRangeValidator);
		datum_kraj.getField().addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				dateRangeValidator.setMin((Date)datum_pocetak.getField().getValue());
				datum_kraj.getField().validate();		
			}
		});
		kamatna_stopa = new AS2FormItem("kamatna_stopa", AS2Field.AMOUNT_3);
		FloatRangeValidator floatRangeValidator = new FloatRangeValidator();  
		floatRangeValidator.setMin((float) 0.001);  
		floatRangeValidator.setMax((float) 99.999);
		floatRangeValidator.setErrorMessage("Mora biti u rasponu od 0,001-99,999");
		kamatna_stopa.getField().setValidators(floatRangeValidator);
		rok_otplate_jedinica = new AS2FormItem("rok_otplate_jedinica",AS2Field.FORM_RADIOGROUP, "", "*");
		LinkedHashMap<String, Object> period_vm = new LinkedHashMap<String, Object>();
		period_vm.put("g", "Godišnje");
		period_vm.put("m", "Mjesečno");
		rok_otplate_jedinica.getField().setShowTitle(false);
		rok_otplate_jedinica.setVertical(false);
		rok_otplate_jedinica.setDefaultValue("g");
		rok_otplate_jedinica.setValueMap(period_vm);
		iznos_za_obracun_prethodni = new AS2FormItem("iznos_za_obracun_prethodni", AS2Field.AMOUNT);
		iznos_za_obracun_prethodni.getField().setVisible(false);
		kamata_prethodna = new AS2FormItem("kamata_prethodna", AS2Field.AMOUNT);
		kamata_prethodna.getField().setVisible(false);
		form.setFields(vrsta_obracuna, br_dokumenta,glavnica,datum_pocetak,datum_kraj,
				kamatna_stopa, rok_otplate_jedinica, iznos_za_obracun_prethodni,kamata_prethodna);
		form.setRequiredFields(br_dokumenta,glavnica, datum_pocetak,datum_kraj,	kamatna_stopa);
		form.focusInItem(br_dokumenta);
		form.setDefaultButton(_button_izracunaj);
		return form;
	}
	@Override
	protected AS2ListGrid getListGrid() {
		AS2ListGrid listGrid = new AS2ListGrid(false);
		listGrid.setDataSource(getModel());
		listGrid.setAutoFetchData(false);
		listGrid.setAutoHeightListGrid();
		listGrid.setAutoFitHeaderHeights(true);
		listGrid.setShowEdges(true);
		listGrid.setLeaveScrollbarGap(true); 
		listGrid.setShowGridSummary(true);  
		listGrid.setShowResizeBar(false);
		listGrid.setAutoFitData(Autofit.HORIZONTAL);
		AS2ListGridField br_dokumenta = new AS2ListGridField("br_dokumenta",AS2Field.TEXT);
		br_dokumenta.getField().setGridSummaryCustomizer(
				new GridSummaryCustomizer() {
					@Override
					public Object[] getGridSummary(ListGridRecord[] records,
							ListGridField field, GroupSummary[] groupSummary) {
						return new String[] { "&#8721" };//Simbol za SUMU
					}
				});
		AS2ListGridField glavnica = new AS2ListGridField("glavnica",AS2Field.AMOUNT);
		AS2ListGridField iznos_za_obracun = new AS2ListGridField("iznos_za_obracun",AS2Field.AMOUNT);
		iznos_za_obracun.getField().setShowGridSummary(false);
		AS2ListGridField datum_pocetak = new AS2ListGridField("datum_pocetak",AS2Field.DATE);
		datum_pocetak.setWidth(75);
		datum_krajGrid = new AS2ListGridField("datum_kraj",AS2Field.DATE);
		datum_krajGrid.setWidth(75);
		AS2ListGridField broj_dana = new AS2ListGridField("broj_dana",AS2Field.INTEGER);
		broj_dana.getField().setShowGridSummary(false);
		final AS2ListGridField kamatna_stopa = new AS2ListGridField("kamatna_stopa",AS2Field.AMOUNT_3);
		CellFormatter kamataCellFormatter = new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,int colNum) {
					String formattedValue = null;
					if (value != null) {
						try {
							formattedValue = AS2Field.formatAmountValue(value, kamatna_stopa._type)+"% "+record.getAttribute("rok_otplate_jedinica");;
						} catch (final Exception e) {
							formattedValue = value.toString();
						}
					}
					return formattedValue;
				}
			};
		kamatna_stopa.getField().setCellFormatter(kamataCellFormatter);
		kamatna_stopa.getField().setShowGridSummary(false);
		final AS2ListGridField koeficijent = new AS2ListGridField("koeficijent",AS2Field.AMOUNT_6);
		CellFormatter koeficijentCellFormatter = new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				String formattedValue = null;
				if (value != null) {
					try {
						formattedValue = AS2Field.formatAmountValue(value, koeficijent._type)+"%";
					} catch (final Exception e) {
						formattedValue = value.toString();
					}
				}
				return formattedValue;
			}
		};
		koeficijent.getField().setCellFormatter(koeficijentCellFormatter);
		koeficijent.getField().setShowGridSummary(false);
		AS2ListGridField kamata = new AS2ListGridField("kamata",AS2Field.AMOUNT,"Iznos kamate");
		listGrid.setFields(br_dokumenta,glavnica,iznos_za_obracun,
				datum_pocetak,datum_krajGrid,broj_dana,kamatna_stopa,koeficijent,kamata);
		return listGrid;
	}
	
	private void callServer() {
		if(_form.validate()){
			_formOldValues = _form.getValuesAsRecord();
			_form.fetchData(_form.getValuesAsCriteria(),serverCallback(), new DSRequest(DSOperationType.CUSTOM,"izracunajKamatu"));
		}
	}
	private Record pripremiFormu(){
		Record novi = new Record();
		novi.setAttribute("rok_otplate_jedinica",_formOldValues.getAttribute("rok_otplate_jedinica"));
		novi.setAttribute("vrsta_obracuna",_formOldValues.getAttribute("vrsta_obracuna"));
		novi.setAttribute("iznos_za_obracun_prethodni",_record.getAttribute("iznos_za_obracun"));
		novi.setAttribute("kamata_prethodna",_record.getAttribute("kamata"));
		if(_recordList!=null &&_recordList.getLength()>0){
			Date datum  = _record.getAttributeAsDate("datum_kraj");
			novi.setAttribute("datum_pocetak", new Date(datum.getTime()+(1000*60*60*24)));//+1 dan
//			datum_pocetak.getField().setDisabled(true);
			dateRangeValidator.setMin(datum);
		}
		return novi;
	}
	private DSCallback serverCallback() {
		return new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
				RecordList records = ((AS2RestJSONDataSource)getModel()).getRecordsFromServerJSON(dsResponse.getDataAsRecordList());
				if(records!=null && records.getLength()!=0){
					for(int i=0;i<records.getLength();i++){
						_recordList.add(records.get(i));
					}
					_record = records.get(records.getLength()-1);
					_form.editRecord(pripremiFormu());
				}
				_listGrid.setData(_recordList);
				_button_ispis.setDisabled(false);

			}
		};
	}
	
	protected HLayout getFormButtons() {
		HLayout buttons_layout = new HLayout(5);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setWidth(FORM_WIDTH);
		buttons_layout.setAutoHeight();
		buttons_layout.setShowEdges(true);
		// define Buttons
		_button_ispis = new IButton("Ispis");
		_button_ispis.setDisabled(true);
		_button_ispis.setAutoFit(true);
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_ispis.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VLayout print = new VLayout();
				AS2DynamicForm tempForm = new AS2DynamicForm(null,number_of_columns);
				String razmak = "";
				String slozeni_obracun = "";
				String prosti_obracun="";
				boolean slozeni=false;
				boolean prosti=false;
				for(Record record :_listGrid.getRecords()){
					if(record.getAttribute("vrsta_obracuna").equals("s")){
						slozeni=true;
					}else{
						prosti=true;
					}
				}
				if(slozeni==true && prosti==true){
					razmak=" i ";
				}
				if(slozeni) slozeni_obracun = "Složeni obračun";
				if(prosti)prosti_obracun="Prosti obračun";
				AS2FormItem vrsta_obracunaA = new AS2FormItem("vrsta_obracuna", AS2Field.FORM_STATIC_TEXT, "Vrsta obračuna","*");
				vrsta_obracunaA.setDefaultValue(slozeni_obracun+razmak+prosti_obracun);
				tempForm.setFields(vrsta_obracunaA);		
				print.addMember(tempForm);
				print.addMember(_listGrid);
				Canvas.showPrintPreview(print);
				ObracunKamateView.this.setMembers(_form,_buttons_layout,_listGrid);
			}
		});
		_button_ocisti = new IButton("Očisti");
		_button_ocisti.setIcon(AS2Resources.DOCUMENT_EDIT_PATH);
		_button_ocisti.setAutoFit(true);
		_button_ocisti.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_form.editRecord(pripremiFormu());

			}
		});
		_button_izracunaj = new IButton("Izračunaj");
		_button_izracunaj.setIcon(AS2Resources.OPTIONS_ICON_PATH);
		_button_izracunaj.setAutoFit(true);
		_button_izracunaj.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				callServer();
			}
		});
		buttons_layout.setMembers(_button_ispis, _button_ocisti, _button_izracunaj);

		return buttons_layout;
	}
	
}
