package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaGoConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaGoModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaGoMWindow extends AS2Window implements EvidencijaGoConstants{
	
	private HLayout _buttons_layout;
	private Record _record;
	private IButton _button_ispis;
	private HLayout _legend_layout;
	
	public EvidencijaGoMWindow(AS2View2 parent, Record record) {
		_parent_view = parent;
		_record = record;
		_record.setAttribute("window", true);
		_dataSource = _parent_view.getModel();
		createComponents();
	}
	@Override
	public DataSource getModel(){
		return EvidencijaGoModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
	}
	@Override
	public void createComponents() {
		_buttons_layout = getButtonsLayout();
		_form=getDynamicForm();
		_form.editRecord(_record);
		_listGrid=getListGrid();
		Criteria criteria = new Criteria();
		criteria.addCriteria(EVIDENCIJAGO__GODINA,_record.getAttribute(EVIDENCIJAGO__GODINA));
		criteria.addCriteria(EVIDENCIJAGO__RADNIK_ID,_record.getAttribute(EVIDENCIJAGO__RADNIK_ID));
		_listGrid.fetchData(criteria,null,new DSRequest(DSOperationType.CUSTOM,"procitajSveGodisnjeOdmoreRadnik"));
		_legend_layout = getLegend();
		windowLayout();
	}
	
	@Override
	public void windowLayout() {
		this.addItem(_form);
		this.addItem(_buttons_layout);
		this.addItem(_listGrid);
		this.addItem(_legend_layout);
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
		final AS2FormItem godina = new AS2FormItem(EVIDENCIJAGO__GODINA);
		godina.getField().setCanEdit(false);
		godina.setWidth(60);
		godina.getField().setAttribute("length", "4");
		AS2FormItem radnik_id = new AS2FormItem(EVIDENCIJAGO__RADNIK_ID,AS2Field.COMBO/*,"Djelatnik"*/);
		radnik_id.getField().setCanEdit(false);
		final AS2FormItem stari = new AS2FormItem(EVIDENCIJAGO__STARI_POTROSENO_OSTATAK);
		stari.getField().setCanEdit(false);
		stari.setWidth(60);
		final AS2FormItem novi = new AS2FormItem(EVIDENCIJAGO__NOVI_POTROSENO_OSTATAK);
		novi.getField().setCanEdit(false);
		novi.setWidth(60);
		
		form.setDataSource(getModel());
		form.setFields(godina, radnik_id, stari, novi);
		form.setDropDownModel(getSifrarnikModel(),radnik_id);
		return form;
	}
	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				if (getFieldName(colNum).startsWith("dan_") && record.getAttribute(getFieldName(colNum))!=null) {  
					if(record.getAttribute(getFieldName(colNum)).equals("GO")){
						return "color:#000000;";  
					}else if(record.getAttribute(getFieldName(colNum)).equals("S")){
						return "background-color: #90BECD; color:#90BECD;";  
					}else if(record.getAttribute(getFieldName(colNum)).equals("N")){
						return "background-color: #588695; color:#588695;"; 
					}else if(record.getAttribute(getFieldName(colNum)).equals("DP")){
						return "background-color: #C23A23; color:#C23A23;"; 
					}else if(record.getAttribute(getFieldName(colNum)).equals("__")){
						return "background-color: #A6A6A6; color:#A6A6A6;"; 
					}
				}
				return super.getCellCSSText(record, rowNum, colNum);  
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setAutoFetchData(false);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setShowRowNumbers(false);
		listGrid.setShowResizeBar(false);
		listGrid.setCanSelectCells(false);
		listGrid.setCanSelectAll(false);
		listGrid.setCanFocus(false);
		listGrid.setBaseStyle("myBoxedGridCell");  
		
		// inline editting
		listGrid.setCanEdit(false);
		
        AS2ListGridField godina = new AS2ListGridField(EVIDENCIJAGO__GODINA);
        AS2ListGridField radnik_id = new AS2ListGridField(EVIDENCIJAGO__RADNIK_ID,AS2Field.COMBO);
        AS2ListGridField mj = new AS2ListGridField(EVIDENCIJAGO__MJ);
        AS2ListGridField mjesec = new AS2ListGridField(EVIDENCIJAGO__MJESEC,"15%");
        AS2ListGridField dan_1 = new AS2ListGridField(EVIDENCIJAGO__DAN_1,"5%");
        AS2ListGridField dan_2 = new AS2ListGridField(EVIDENCIJAGO__DAN_2,"5%");
        AS2ListGridField dan_3 = new AS2ListGridField(EVIDENCIJAGO__DAN_3,"5%");
        AS2ListGridField dan_4 = new AS2ListGridField(EVIDENCIJAGO__DAN_4,"5%");
        AS2ListGridField dan_5 = new AS2ListGridField(EVIDENCIJAGO__DAN_5,"5%");
        AS2ListGridField dan_6 = new AS2ListGridField(EVIDENCIJAGO__DAN_6,"5%");
        AS2ListGridField dan_7 = new AS2ListGridField(EVIDENCIJAGO__DAN_7,"5%");
        AS2ListGridField dan_8 = new AS2ListGridField(EVIDENCIJAGO__DAN_8,"5%");
        AS2ListGridField dan_9 = new AS2ListGridField(EVIDENCIJAGO__DAN_9,"5%");
        AS2ListGridField dan_10 = new AS2ListGridField(EVIDENCIJAGO__DAN_10,"5%");
        AS2ListGridField dan_11 = new AS2ListGridField(EVIDENCIJAGO__DAN_11,"5%");
        AS2ListGridField dan_12 = new AS2ListGridField(EVIDENCIJAGO__DAN_12,"5%");
        AS2ListGridField dan_13 = new AS2ListGridField(EVIDENCIJAGO__DAN_13,"5%");
        AS2ListGridField dan_14 = new AS2ListGridField(EVIDENCIJAGO__DAN_14,"5%");
        AS2ListGridField dan_15 = new AS2ListGridField(EVIDENCIJAGO__DAN_15,"5%");
        AS2ListGridField dan_16 = new AS2ListGridField(EVIDENCIJAGO__DAN_16,"5%");
        AS2ListGridField dan_17 = new AS2ListGridField(EVIDENCIJAGO__DAN_17,"5%");
        AS2ListGridField dan_18 = new AS2ListGridField(EVIDENCIJAGO__DAN_18,"5%");
        AS2ListGridField dan_19 = new AS2ListGridField(EVIDENCIJAGO__DAN_19,"5%");
        AS2ListGridField dan_20 = new AS2ListGridField(EVIDENCIJAGO__DAN_20,"5%");
        AS2ListGridField dan_21 = new AS2ListGridField(EVIDENCIJAGO__DAN_21,"5%");
        AS2ListGridField dan_22 = new AS2ListGridField(EVIDENCIJAGO__DAN_22,"5%");
        AS2ListGridField dan_23 = new AS2ListGridField(EVIDENCIJAGO__DAN_23,"5%");
        AS2ListGridField dan_24 = new AS2ListGridField(EVIDENCIJAGO__DAN_24,"5%");
        AS2ListGridField dan_25 = new AS2ListGridField(EVIDENCIJAGO__DAN_25,"5%");
        AS2ListGridField dan_26 = new AS2ListGridField(EVIDENCIJAGO__DAN_26,"5%");
        AS2ListGridField dan_27 = new AS2ListGridField(EVIDENCIJAGO__DAN_27,"5%");
        AS2ListGridField dan_28 = new AS2ListGridField(EVIDENCIJAGO__DAN_28,"5%");
        AS2ListGridField dan_29 = new AS2ListGridField(EVIDENCIJAGO__DAN_29,"5%");
        AS2ListGridField dan_30 = new AS2ListGridField(EVIDENCIJAGO__DAN_30,"5%");
        AS2ListGridField dan_31 = new AS2ListGridField(EVIDENCIJAGO__DAN_31,"5%");
        
	
		listGrid.setFields(godina, radnik_id, mj, mjesec, dan_1, dan_2, dan_3,
				dan_4, dan_5, dan_6, dan_7, dan_8, dan_9, dan_10, dan_11,
				dan_12, dan_13, dan_14, dan_15, dan_16, dan_17, dan_18, dan_19,
				dan_20, dan_21, dan_22, dan_23, dan_24, dan_25, dan_26, dan_27,
				dan_28, dan_29, dan_30, dan_31);
		listGrid.setDropDownModel(getSifrarnikModel(),radnik_id);
		listGrid.setAlignFields(Alignment.CENTER, dan_1, dan_2, dan_3, dan_4,
				dan_5, dan_6, dan_7, dan_8, dan_9, dan_10, dan_11, dan_12,
				dan_13, dan_14, dan_15, dan_16, dan_17, dan_18, dan_19, dan_20,
				dan_21, dan_22, dan_23, dan_24, dan_25, dan_26, dan_27, dan_28,
				dan_29, dan_30, dan_31);
	
		listGrid.setHiddenFields(true, radnik_id, mj, godina);
		
		return listGrid;
	}
	@Override
	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout();
		buttons_layout.setAlign(Alignment.RIGHT);
		_button_ispis = new IButton("Ispis");
		_button_ispis.setIcon(AS2Resources.PRINT_PREVIEW_PATH);
		_button_ispis.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout print = new VLayout();
				print.addMember(_form);
				print.addMember(_buttons_layout);
				print.addMember(_listGrid);
				print.addMember(_legend_layout);
				Canvas.showPrintPreview(print);
				EvidencijaGoMWindow.this.addItem(print);
			}
		});

		buttons_layout.setMembers(_button_ispis);
		return buttons_layout;
	}
	@Override
	public String getWindowFormTitle() {
		String contents ="Godišnji odmor - detalji";
		return contents;
	}
	protected HLayout getLegend() {
		HLayout legend_layout = new HLayout();
		legend_layout.setAlign(Alignment.LEFT);
		legend_layout.setHeight(20);
		Canvas leg = new Canvas();
		leg.setContents("<b><p style=\"color:black; margin-left:5px;\">LEGENDA:</b>");
		Canvas s = new Canvas();
		s.setContents("<p style=\"color:#90BECD; background-color:#90BECD ;\">__</b>");
		s.setWidth(20);
		Canvas s1 = new Canvas();
		s1.setContents("<b><p style=\"color:black; margin-left:5px;\">Subota</b>");
		Canvas n = new Canvas();
		n.setContents("<p style=\"color:#588695; background-color:#588695;\">__</b>");
		n.setWidth(20);
		Canvas n1 = new Canvas();
		n1.setContents("<b><p style=\"color:black; margin-left:5px;\">Nedjelja</b>");
		Canvas dp = new Canvas();
		dp.setContents("<p style=\"color:#C23A23; background-color:#C23A23;\">__</b>");
		dp.setWidth(20);
		Canvas dp1 = new Canvas();
		dp1.setContents("<b><p style=\"color:black; margin-left:5px;\">Državni praznik</b>");
		Canvas nv = new Canvas();
		nv.setContents("<p style=\"color:#A6A6A6; background-color:#A6A6A6;\">__</b>");
		nv.setWidth(20);
		Canvas nv1 = new Canvas();
		nv1.setContents("<b><p style=\"color:black; margin-left:5px;\">Nevažeći dan</b>");

		legend_layout.setMembers(leg,s,s1,n,n1,dp,dp1,nv,nv1);
		return legend_layout;
	}
		
}

