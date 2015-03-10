package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.postavke;

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;

import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class VrsteDokumentaParametriTab extends Tab {

	public AS2DynamicForm _formParametri;

	public AS2ListGrid _listGridParametri;
	private ValuesManager _vm;

	public VrsteDokumentaParametriTab(ValuesManager vm) {
		this.setTitle("Parametri");
		this._vm = vm;
		this._formParametri = getDynamicFormParametri();
		this._formParametri.setID("vrstaParametriForm");
		this._listGridParametri = getListGridParametri();
		this._listGridParametri.setID("vrstaParametriListGrid");
		HLayout _parametriLayout = new HLayout();
		_parametriLayout.setWidth100();
		_parametriLayout.setHeight100();
		_parametriLayout.setMembers(_formParametri,_listGridParametri);
		this.setPane(_parametriLayout);
	}

	private AS2DynamicForm getDynamicFormParametri() {
		final AS2DynamicForm form = new AS2DynamicForm(2);
		form.setIsGroup(true);
		form.setGroupTitle("Parametri");
		form.setValuesManager(_vm);
		AS2FormItem redni_broj = new AS2FormItem("redni_broj",AS2Field.INTEGER,"Redni broj",null,null,true);
		redni_broj.setWidth(50);
		AS2FormItem parametar = new AS2FormItem("parametar",AS2Field.TEXT,"Naziv","*",null,true);
		parametar.setWidth(150);
		parametar.getField().setTooltip("Naziv polja u bazi podataka");
		AS2FormItem naslov = new AS2FormItem("naslov",AS2Field.TEXT,"Naslov","*",null,true);
		naslov.setWidth(150);
		AS2FormItem tip = new AS2FormItem("tip",AS2Field.COMBO,"Tip",null,null,true);
		tip.setWidth(100);
		tip.getField().setTooltip(OContext._tipPodatkaTooltip());
		tip.setValueMap(OContext._tipPodatkaValueMap());
		AS2FormItem ui_tip = new AS2FormItem("ui_tip",AS2Field.COMBO,"UI tip",null,null,true);
		ui_tip.setWidth(100);
		ui_tip.getField().setTooltip(OContext._uitipPodatkaTooltip());
		ui_tip.setValueMap(OContext._uitipPodatkaValueMap());
		AS2FormItem obavezno = new AS2FormItem("obavezno",AS2Field.COMBO,"Obavezno",null,null,true);
		obavezno.setWidth(100);
		obavezno.getField().setTooltip(OContext._obaveznoPoljeTooltip());
		obavezno.setValueMap(OContext._obaveznoPoljeValueMap());
		obavezno.setDefaultValue("n");
		AS2FormItem izvor_podataka = new AS2FormItem("izvor_podataka",AS2Field.TEXT,"Izvor podataka","*",null,true);
		izvor_podataka.setWidth(200);
		izvor_podataka.getField().setTooltip(OContext._izvorPodatakaTooltip());
		AS2FormItem button_spremi = new AS2FormItem("btn_spremi",AS2Field.FORM_BUTTON,
				"Dodaj parametar");
		button_spremi.getField().setEndRow(false);
		button_spremi.getField().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				form.saveData();
				form.editNewRecord();
			}
		});
		form.setAutoFetchData(true);
		form.setFields(redni_broj, parametar, naslov, izvor_podataka, obavezno,
				tip, ui_tip,button_spremi);
		form.setWrapItemTitles(true);
		form.setHeight100();
		form.setWidth100();
		return form;
	}

	private AS2ListGrid getListGridParametri() {
		final AS2ListGrid listGrid = new AS2ListGrid(true);
		listGrid.setValuesManager(_vm);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setAutoFetchData(true);
		AS2ListGridField redni_broj = new AS2ListGridField("redni_broj",AS2Field.INTEGER);
		redni_broj.setWidth(50);
		AS2ListGridField parametar = new AS2ListGridField("parametar",AS2Field.TEXT);
		listGrid.setFields(redni_broj,parametar);
		listGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				_formParametri.reset();
				_formParametri.editSelectedData(listGrid);
			}
		});
		return listGrid;
	}

	//Getter for _formParametri
	public AS2DynamicForm getformParametri() {
		return _formParametri;
	}

	//Getter for _formParametri
	public AS2ListGrid getlistGridParametri() {
		return _listGridParametri;
	}
}
