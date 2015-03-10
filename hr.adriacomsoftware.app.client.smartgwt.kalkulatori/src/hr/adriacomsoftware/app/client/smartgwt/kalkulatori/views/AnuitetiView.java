package hr.adriacomsoftware.app.client.smartgwt.kalkulatori.views;

import hr.adriacomsoftware.app.client.smartgwt.kalkulatori.models.AnuitetiModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;

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
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GridSummaryCustomizer;
import com.smartgwt.client.widgets.grid.GroupSummary;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AnuitetiView extends AS2View {

	private static int FORM_WIDTH = 450;
	private AS2FormItem iznos_kredita;
	private AS2FormItem kamatna_stopa;
	private AS2FormItem manipulativni_trosak;
	private AS2FormItem period_kamatne_stope;
	private AS2FormItem rok_otplate;
	private AS2FormItem period_otplate;
	private AS2FormItem nacin_otplate;
	private AS2FormItem vrsta_obracuna;
	private AS2FormItem anuiteti_vrsta;
	private IButton _button_izracunaj;
	private IButton _button_ocisti;
	private Record _record;
	private IButton _button_ispis;
	private HLayout _buttons_layout;

	@Override
	protected void initializeView() {
		number_of_columns = 3;
		this.setOverflow(Overflow.AUTO);
	}
	public void showWidgets() {
		_buttons_layout = getFormButtons();
		_form = getDynamicForm();
		this.addMembers(_form, _buttons_layout);
	}
	@Override
	public DataSource getModel() {
		return AnuitetiModel.getInstance();
	}
	@Override
	public DataSource getSifrarnikModel() {
		return null;
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
		AS2ListGridField broj_rate = new AS2ListGridField("broj_rate",AS2Field.INTEGER);
		broj_rate.getField().setGridSummaryCustomizer(new GridSummaryCustomizer() {
			@Override
			public Object[] getGridSummary(ListGridRecord[] records,
					ListGridField field, GroupSummary[] groupSummary) {
				return new String[]{"&#8721"};//Simbol za SUMU
			}
		});
		AS2ListGridField anuitet = new AS2ListGridField("anuitet", AS2Field.AMOUNT);
		AS2ListGridField kamata = new AS2ListGridField("kamata", AS2Field.AMOUNT);
		AS2ListGridField otplata_glavnice = new AS2ListGridField("otplata_glavnice",AS2Field.AMOUNT);
		AS2ListGridField ostatak_duga = new AS2ListGridField("ostatak_duga",AS2Field.AMOUNT);
		ostatak_duga.getField().setShowGridSummary(false);
		final AS2ListGridField koeficijent = new AS2ListGridField("koeficijent",AS2Field.AMOUNT_8);
		CellFormatter koeficijentCellFormatter = new CellFormatter() {					
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
							String formattedValue = null;
							if (value != null) {
								try {
									formattedValue = AS2Field.formatAmountValue(value, koeficijent._type);
								} catch (final Exception e) {
									formattedValue = value.toString();
								}
							}
							return formattedValue;
						}
					};
		koeficijent.getField().setCellFormatter(koeficijentCellFormatter);
		koeficijent.getField().setShowGridSummary(false);
		listGrid.setFields(broj_rate, anuitet, kamata, otplata_glavnice,
				ostatak_duga, koeficijent);
		return listGrid;
	}

	@Override
	protected AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(null, number_of_columns);
		form.setDataSource(getModel());
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setStyleNameGray();
		form.setSaveOnEnter(false);
		form.setWidth(FORM_WIDTH);
		form.setColWidths("30%", "35%", "35%");
		form.setShowEdges(true);
		anuiteti_vrsta = new AS2FormItem("anuiteti_vrsta", AS2Field.FORM_RADIOGROUP, "Anuiteti vrsta","*");
		LinkedHashMap<String, Object> anuiteti_vrsta_vm = new LinkedHashMap<String, Object>();
		anuiteti_vrsta_vm.put("dek", "Dekurzivni obračun");
		anuiteti_vrsta_vm.put("ant", "Anticipativni obračun");
		anuiteti_vrsta_vm.put("jok", "Jednake otplate glavnice");
		anuiteti_vrsta.setValueMap(anuiteti_vrsta_vm);
		anuiteti_vrsta.getField().setShowTitle(false);
		anuiteti_vrsta.getField().setRowSpan(3);
		anuiteti_vrsta.setDefaultValue("dek");
		iznos_kredita = new AS2FormItem("iznos_kredita", AS2Field.AMOUNT);
		iznos_kredita.setEndRow(true);
		kamatna_stopa = new AS2FormItem("kamatna_stopa", AS2Field.AMOUNT_3);
		FloatRangeValidator floatRangeValidator = new FloatRangeValidator();  
		floatRangeValidator.setMin((float) 0.001);  
		floatRangeValidator.setMax((float) 99.999);
		floatRangeValidator.setErrorMessage("Mora biti u rasponu od 0,001-99,999");
	    kamatna_stopa.getField().setValidators(floatRangeValidator);  
		period_kamatne_stope = new AS2FormItem("period_kamatne_stope", AS2Field.FORM_RADIOGROUP, "", "*");
		LinkedHashMap<String, Object> period_vm = new LinkedHashMap<String, Object>();
		period_vm.put("g", "Godišnje");
		period_vm.put("m", "Mjesečno");
		period_kamatne_stope.setValueMap(period_vm);
		period_kamatne_stope.getField().setShowTitle(false);
		period_kamatne_stope.setVertical(false);
		period_kamatne_stope.setDefaultValue("g");
		rok_otplate = new AS2FormItem("rok_otplate",AS2Field.INTEGER);
		IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();  
		integerRangeValidator.setMin(1);  
		rok_otplate.getField().setValidateOnChange(true);
		rok_otplate.getField().setValidators(integerRangeValidator);
		period_otplate = new AS2FormItem("period_otplate", AS2Field.FORM_RADIOGROUP, "","*");
		LinkedHashMap<String, Object> period_otplate_vm = new LinkedHashMap<String, Object>();
		period_otplate_vm.put("g", "Godina");
		period_otplate_vm.put("m", "Mjeseci");
		period_otplate.getField().setShowTitle(false);
		period_otplate.setVertical(false);
		period_otplate.setDefaultValue("g");
		period_otplate.setValueMap(period_otplate_vm);
		nacin_otplate = new AS2FormItem("nacin_otplate", AS2Field.COMBO,"Dinamika otplate");
		LinkedHashMap<String, Object> nacin_otplate_vm = new LinkedHashMap<String, Object>();
		nacin_otplate_vm.put("1", "Jednomjesečno");
		nacin_otplate_vm.put("2", "Dvomjesečno");
		nacin_otplate_vm.put("3", "Tromjesečno");
		nacin_otplate_vm.put("6", "Polugodišnje");
		nacin_otplate_vm.put("12", "Godišnje");
		nacin_otplate.setValueMap(nacin_otplate_vm);
		nacin_otplate.setEndRow(true);
		nacin_otplate.getField().setTitleColSpan(1);
		CustomValidator isTrueValidator = new CustomValidator() {  
			@Override  
	        protected boolean condition(Object value) {  
				if(_form.getField("period_otplate").getValue().toString().equals("m") && _form.getField("rok_otplate").getValue()!=null && _form.getField("nacin_otplate").getValue()!=null){
	           		Integer rokOtplate =  Integer.parseInt(_form.getField("rok_otplate").getValue().toString());
	        		Integer dinamikaOtplate =  Integer.parseInt(_form.getField("nacin_otplate").getValue().toString());
	        		if(rokOtplate%dinamikaOtplate!=0) return false;
	        		else return true;
	        	}
	            return true;  
	        }  	         
	    };  
	    isTrueValidator.setErrorMessage("Broj mjeseci roka otplate mora biti djeljiv sa dinamikom otplate!");
	    nacin_otplate.getField().setValidators(isTrueValidator);
	    nacin_otplate.getField().setValidateOnChange(true);
	    manipulativni_trosak = new AS2FormItem("manipulativni_trosak", AS2Field.AMOUNT,"Manipulativni trošak (%)");
	   	manipulativni_trosak.setEndRow(true);
		vrsta_obracuna = new AS2FormItem("vrsta_obracuna", AS2Field.FORM_RADIOGROUP, "","*");
		LinkedHashMap<String, Object> vrsta_obracuna_vm = new LinkedHashMap<String, Object>();
		vrsta_obracuna_vm.put("k", "Konformni obračun");
		vrsta_obracuna_vm.put("r", "Proporcionalni obračun");
		vrsta_obracuna.setStartRow(true);
		vrsta_obracuna.getField().setShowTitle(false);
		vrsta_obracuna.getField().setRowSpan(2);
		vrsta_obracuna.setDefaultValue("k");
		vrsta_obracuna.setValueMap(vrsta_obracuna_vm);
		form.setFields(anuiteti_vrsta, iznos_kredita, kamatna_stopa,
				period_kamatne_stope, rok_otplate, period_otplate,
				nacin_otplate, manipulativni_trosak,vrsta_obracuna);
		form.setRequiredFields(iznos_kredita, kamatna_stopa, rok_otplate,
				nacin_otplate);
		form.focusInItem(iznos_kredita);
		form.setDefaultButton(_button_izracunaj);
		return form;

	}

	private DSCallback serverCallback() {
		DSCallback callback = new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
				_form.editRecord(_record);
				_listGrid = getListGrid();
				RecordList records = null;
				if(dsResponse.getStatus()>=0)
					records = ((AS2RestJSONDataSource) getModel()).getRecordsFromServerJSON(dsResponse.getDataAsRecordList());
				_listGrid.setData(records);
				AnuitetiView.this.setMembers(_form,_buttons_layout,_listGrid);
				_button_ispis.setDisabled(false);
			}
		};
		return callback;
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
				tempForm.setWrapItemTitles(false);
				AS2FormItem anuiteti_vrstaA = new AS2FormItem("anuiteti_vrstaA", AS2Field.FORM_STATIC_TEXT, "Vrsta Anuitetskog obračuna","*");
				anuiteti_vrstaA.setDefaultValue(_form.getField("anuiteti_vrsta").getDisplayValue());
				AS2FormItem vrsta_obracunaA = new AS2FormItem("vrsta_obracunaA", AS2Field.FORM_STATIC_TEXT, "Vrsta obračuna","*");
				vrsta_obracunaA.setDefaultValue(_form.getField("vrsta_obracuna").getDisplayValue());
				AS2FormItem iznos_kreditaA = new AS2FormItem("iznos_kreditaA", AS2Field.FORM_STATIC_TEXT, _form.getField("iznos_kredita").getTitle(),"*");
				iznos_kreditaA.setDefaultValue(_form.getField("iznos_kredita").getDisplayValue());
				AS2FormItem kamatna_stopaA = new AS2FormItem("kamatna_stopaA", AS2Field.FORM_STATIC_TEXT, _form.getField("kamatna_stopa").getTitle(),"*");
				kamatna_stopaA.setDefaultValue(_form.getField("kamatna_stopa").getDisplayValue() + "% " +_form.getField("period_kamatne_stope").getDisplayValue().toLowerCase());
				AS2FormItem rok_otplateA = new AS2FormItem("rok_otplateA", AS2Field.FORM_STATIC_TEXT, _form.getField("rok_otplate").getTitle(),"*");
				rok_otplateA.setDefaultValue(_form.getField("rok_otplate").getDisplayValue() + " " +_form.getField("period_otplate").getDisplayValue().toLowerCase());
				AS2FormItem nacin_otplateA = new AS2FormItem("nacin_otplateA", AS2Field.FORM_STATIC_TEXT, _form.getField("nacin_otplate").getTitle(),"*");
				nacin_otplateA.setDefaultValue(_form.getField("nacin_otplate").getDisplayValue());
				AS2FormItem manipulativni_trosakA = new AS2FormItem("manipulativni_trosakA", AS2Field.FORM_STATIC_TEXT, _form.getField("manipulativni_trosak").getTitle(),"*");
				manipulativni_trosakA.setDefaultValue(_form.getField("manipulativni_trosak").getDisplayValue());
				tempForm.setFields(anuiteti_vrstaA,vrsta_obracunaA,iznos_kreditaA,kamatna_stopaA,rok_otplateA,nacin_otplateA,manipulativni_trosakA);
				print.addMember(tempForm);
				print.addMember(_listGrid);
				Canvas.showPrintPreview(print);
				AnuitetiView.this.setMembers(_form,_buttons_layout,_listGrid);
			}
		});
		_button_ocisti = new IButton("Očisti");
		_button_ocisti.setIcon(AS2Resources.DOCUMENT_EDIT_PATH);
		_button_ocisti.setAutoFit(true);
		_button_ocisti.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_form.editNewRecord();
			}
		});
		_button_izracunaj = new IButton("Izračunaj");
		_button_izracunaj.setIcon(AS2Resources.OPTIONS_ICON_PATH);
		_button_izracunaj.setAutoFit(true);
		_button_izracunaj.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(_form.validate()){
					_record = _form.getValuesAsRecord();
					DSRequest request = new DSRequest(DSOperationType.CUSTOM,"izracunajPlanOtplate");
					_form.fetchData(_form.getValuesAsCriteria(), serverCallback(), request);
				}
			}
		});
		buttons_layout.setMembers(_button_ispis, _button_ocisti,
				_button_izracunaj);
		return buttons_layout;
	}

	class DjeljivoSValidator extends CustomValidator{
		@Override
		public void setErrorMessage(String errorMessage) {
			super.setErrorMessage("Broj mjeseci roka otplate mora biti djeljiv sa dinamikom otplate!");
		}

		@Override
		protected boolean condition(Object value) {
			if(period_otplate.getField().getValue().toString().equals("m")){
	    		Integer rokOtplate =  Integer.parseInt(rok_otplate.getField().getValue().toString());
	    		Integer dinamikaOtplate =  Integer.parseInt(nacin_otplate.getField().getValue().toString());
	    		if(rokOtplate%dinamikaOtplate!=0){
	    			return true;
	    		}
	    		else{
	    			return false;
	    		}
	    	}
			return false;
		}
		
	}

}
