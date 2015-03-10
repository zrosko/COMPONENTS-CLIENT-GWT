package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author msekso, astrikoman
 *
 */ 
public class EvidencijaPotvrdaView extends AS2View2 implements  EvidencijaDolazakaConstants {

	public EvidencijaPotvrdaView(String name) {
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
		Criteria criteria = _form.getValuesAsCriteria();
		_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.CUSTOM,"procitajSvePotvrde"));
		this.addMembers(_form,_listGrid,_listGrid.getStatusBar());
	}
	
	@Override
	public DataSource getModel() {
		return EvidencijaDolazakaModel.getInstance();
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
//		final AS2FormItem datum_od = new AS2FormItem(EVIDENCIJA__DATUM_OD);
//		datum_od.setWidth(100);
//		datum_od.getField().setDefaultValue(new Date(System.currentTimeMillis()-24*60*60*1000));
//		final AS2FormItem datum_do = new AS2FormItem(EVIDENCIJA__DATUM_DO);
//		datum_do.setWidth(100);
//		datum_do.getField().setDefaultValue(new Date(System.currentTimeMillis()-24*60*60*1000));
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
//		AS2FormItem organizacijska_jedinica_id = new AS2FormItem(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID, AS2Field.COMBO, "Org.jed");
//		organizacijska_jedinica_id.getField().setColSpan(5);
//		organizacijska_jedinica_id.setWidth(370);
//		organizacijska_jedinica_id.setDefaultToFirstOption(true);
		AS2FormItem id_spica_oj = new AS2FormItem(EVIDENCIJA__ID_SPICA_OJ, AS2Field.COMBO/*, "Odjel"*/);
		id_spica_oj.getField().setColSpan(5);
		id_spica_oj.setWidth(370);
		id_spica_oj.setDefaultToFirstOption(true);
		
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
				Criteria criteria = form.getValuesAsCriteria();
				_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.CUSTOM,"procitajSvePotvrde"));
			}
		});
		form.setDataSource(getModel());
		form.setFields(datum_od,datum_do,button_prikazi,id_spica_oj);
		form.focusInItem(button_prikazi);
		form.setDropDownModel(getSifrarnikModel(),id_spica_oj);
		return form;
	}
	
	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true);
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setShowRowNumbers(true);
		listGrid.setShowResizeBar(false);
		listGrid.setBaseStyle("myAltRecGridCell");
		
		// inline editting
		listGrid.setCanEdit(true);
		listGrid.setModalEditing(true);
		listGrid.setListEndEditAction(RowEndEditAction.STOP);
		listGrid.setSaveByCell(false);
		
		listGrid.setAutoFetchData(false);
		Criteria criteria = _form.getValuesAsCriteria();
		listGrid.setCriteria(criteria);
		
		AS2ListGridField datum = new AS2ListGridField(EVIDENCIJA__DATUM,"8%");
		datum.getField().setCanEdit(false);
		datum.getField().setAlign(Alignment.CENTER); 
//		AS2ListGridField organizacijska_jedinica_id = new AS2ListGridField(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID,AS2Field.COMBO);
//		organizacijska_jedinica_id.getField().setCanEdit(false);
//		organizacijska_jedinica_id.setWidth("20%");
		AS2ListGridField id_spica_oj = new AS2ListGridField(EVIDENCIJA__ID_SPICA_OJ,AS2Field.COMBO);
		id_spica_oj.getField().setCanEdit(false);
		id_spica_oj.setWidth("20%");
		AS2ListGridField potvrda_icon = new AS2ListGridField(EVIDENCIJA__POTVRDA_ICON,AS2Field.IMAGE);
		potvrda_icon.getField().setShowTitle(false);
		potvrda_icon.getField().setAlign(Alignment.CENTER);  
		potvrda_icon.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/s16/excel/");  
		potvrda_icon.getField().setImageURLSuffix(".png");
		potvrda_icon.setWidth(24);
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
		
		listGrid.setFields(datum,id_spica_oj,potvrda_icon);
		listGrid.setDropDownModel(getSifrarnikModel(),id_spica_oj);
		return listGrid;
	}
	@Override
	protected String getRecordIdName() {
		return EVIDENCIJA__ID_DNEVNE_EVIDENCIJE;
	}
	
}
