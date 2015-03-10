package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaOvlastiModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * @author msekso, astrikoman
 *
 */ 
public class EvidencijaOvlastiView extends AS2View2 implements  EvidencijaDolazakaConstants {
	
	private IButton _button_novo;
	private HLayout _buttons_layout;
	private HLayout rollOverCanvas;

	public EvidencijaOvlastiView(String name) {
		super(name);
	}
	
	@Override
	public void customWindowPreferences() {
		this.setWidth100();
		this.setHeight100();
		number_of_columns = 5;
		use_form=false;
		use_listgrid=true;
	}

	@Override
	public void windowLayout() {
		_buttons_layout = getFormButtons();
		this.addMembers(_listGrid, _listGrid.getStatusBar(), _buttons_layout);
	}
	
	@Override
	public DataSource getModel() {
		return EvidencijaOvlastiModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return EvidencijaDolazakaSifrarnikModel.getInstance();
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
		};
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
		
		AS2ListGridField id_ovlasti = new AS2ListGridField(EVIDENCIJA__ID_OVLASTI);
		id_ovlasti.getField().setHidden(true);
//		AS2ListGridField organizacijska_jedinica_id = new AS2ListGridField(EVIDENCIJA__ORGANIZACIJSKA_JEDINICA_ID,AS2Field.COMBO);
//		organizacijska_jedinica_id.setWidth("50%");
		AS2ListGridField id_spica_oj = new AS2ListGridField(EVIDENCIJA__ID_SPICA_OJ,AS2Field.COMBO);
		id_spica_oj.setWidth("50%");
		AS2ListGridField radnik_id = new AS2ListGridField(EVIDENCIJA__RADNIK_ID,AS2Field.COMBO);
		radnik_id.setWidth("50%");
		listGrid.setDropDownModel(getSifrarnikModel(),radnik_id,id_spica_oj);
		listGrid.setFields(id_ovlasti, /*organizacijska_jedinica_id,*/id_spica_oj,radnik_id);
		return listGrid;
	}
	
	/*********** ROLLOVER ************/
	private void prepareRollOver(final ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null));
	}
	protected HLayout getFormButtons() {
		_buttons_layout = new HLayout(5);
		_buttons_layout.setAlign(Alignment.RIGHT);
		_buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		_buttons_layout.setAutoHeight();
		_buttons_layout.setShowEdges(true);
		// define Buttons
		_button_novo = new IButton("Novo");
		_button_novo.setIcon(AS2Resources.INSTANCE.add_icon().getSafeUri()
				.asString());
		_button_novo.setAutoFit(true);
		_button_novo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_listGrid.startEditingNew();
			}
		});
		_buttons_layout.setMembers(_button_novo);
		return _buttons_layout;
	}

	@Override
	protected String getRecordIdName() {
		return EVIDENCIJA__ID_OVLASTI;
	}
	
}
