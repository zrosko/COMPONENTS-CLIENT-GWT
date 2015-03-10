package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaIspravakKategorijeMWindow extends AS2Window implements EvidencijaDolazakaConstants{
	
	private static int WINDOW_WIDTH = 500;
	private HLayout _buttons_layout;
	private Record _record;
	private IButton _button_izmjeni;
	
	public EvidencijaIspravakKategorijeMWindow(AS2View2 parent, Record record) {
		_parent_view = parent;
		_record = record;
		_dataSource = _parent_view.getModel();
		
		createComponents();
	}
	
	public DataSource getModel(){
		return EvidencijaDolazakaModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
	}
	
	public void createComponents() {
		_buttons_layout = getButtonsLayout();
		_form=getDynamicForm();
		windowLayout();
	}
	
	@Override
	public void windowLayout() {
		this.setWidth(WINDOW_WIDTH);
		this.setHeight(WINDOW_WIDTH-300);
		this.addItem(_form);
		this.addItem(_buttons_layout);
		this.show();
	}
	@Override
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(4);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setSaveOnEnter(false);
		form.setWidth(100);
		form.setPadding(10);
		AS2FormItem radnik_id = new AS2FormItem(EVIDENCIJA__RADNIK_ID,AS2Field.COMBO/*,"Djelatnik"*/);
		radnik_id.getField().setCanEdit(false);
		radnik_id.getField().setColSpan(4);
		radnik_id.setWidth(370);
		radnik_id.setDefaultValue(_record.getAttribute(EVIDENCIJA__RADNIK_ID));
		AS2FormItem datum_od = new AS2FormItem(EVIDENCIJA__DATUM_OD,AS2Field.DATE,"Razdoblje od");
		datum_od.setWidth(130);
		datum_od.getField().setDefaultValue(_record.getAttributeAsDate(EVIDENCIJA__DATUM_OD));
		AS2FormItem datum_do = new AS2FormItem(EVIDENCIJA__DATUM_DO,AS2Field.DATE,"Razdoblje do");
		datum_do.setWidth(130);
		datum_do.getField().setDefaultValue(_record.getAttributeAsDate(EVIDENCIJA__DATUM_DO));
		AS2FormItem stara_sifra = new AS2FormItem(EVIDENCIJA__STARA_SIFRA,AS2Field.COMBO/*,"Stara kategorija"*/);
		stara_sifra.getField().setColSpan("*");
		stara_sifra.setDropDownList("element_obracuna_id");
		stara_sifra.setWidth(370);
		AS2FormItem nova_sifra = new AS2FormItem(EVIDENCIJA__NOVA_SIFRA,AS2Field.COMBO/*,"Nova kategorija"*/);
		nova_sifra.getField().setColSpan(4);
		nova_sifra.setDropDownList("element_obracuna_id");
		nova_sifra.setWidth(370);
		
		form.setDataSource(getModel());
		form.setFields(radnik_id, datum_od, datum_do, stara_sifra, nova_sifra);
		form.setRequiredFields(datum_od, datum_do, stara_sifra, nova_sifra);
		form.setDropDownModel(getSifrarnikModel(),radnik_id,stara_sifra,nova_sifra);
		return form;
	}
	@Override
	public AS2ListGrid getListGrid() {
		return null;
	}
	@Override
	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout();
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setPadding(10);
		_button_izmjeni = new IButton("Izmjeni");
		_button_izmjeni.setIcon(AS2Resources.SAVE_PATH);
		_button_izmjeni.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(_form.validate()){
					Record form = _form.getValuesAsRecord();
					ListGridRecord[] records = _parent_view._listGrid.getRecords();
					int rowNum = 0;
				    for(ListGridRecord record:records){
				    	if(record.getAttributeAsObject(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID) != null &&
				    	   record.getAttributeAsObject(EVIDENCIJA__DATUM) != null &&
				    	   form.getAttributeAsObject(EVIDENCIJA__DATUM_OD) != null &&
				    	   form.getAttributeAsObject(EVIDENCIJA__DATUM_DO) != null &&
				    	   record.getAttributeAsString(EVIDENCIJA__CAT_ELEMENT_OBRACUNA_ID).equals(form.getAttributeAsString(EVIDENCIJA__STARA_SIFRA)) &&
				    	   checkIsDateInRange(record.getAttributeAsDate(EVIDENCIJA__DATUM),form.getAttributeAsDate(EVIDENCIJA__DATUM_OD),form.getAttributeAsDate(EVIDENCIJA__DATUM_DO))==true
				    	   ){
				    		_parent_view._listGrid.setEditValue(rowNum, 2, form.getAttributeAsString(EVIDENCIJA__NOVA_SIFRA));
				    	}
				    	rowNum++;
					
				    }
				}
			}
		});

		buttons_layout.setMembers(_button_izmjeni);
		return buttons_layout;
	}
	@Override
	public String getWindowFormTitle() {
		String contents ="Izmjena kategorije za razdoblje";
		return contents;
	}
	
	
	//TODO - prebaciti u infrastrukturu
	public Boolean checkIsDateInRange(Date date, Date date_from, Date date_to) {
		if (date.equals(date_from) || date.equals(date_to)) {
			return true;
		} else if (date.after(date_from) && date.before(date_to)) {
			return true;
		} else
			return false;
	}
	
}

