package hr.adriacomsoftware.app.client.smartgwt.sspn.views;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.client.smartgwt.sspn.models.PrnSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.sspn.models.SubjektivnaOcjenaConstants;
import hr.adriacomsoftware.app.client.smartgwt.sspn.models.SubjektivnaOcjenaModel;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

public class SubjektivnaOcjenaView extends AS2View2 implements SubjektivnaOcjenaConstants {

	public static SubjektivnaOcjenaView view;
	private IButton _button_novo;
	private HLayout _buttons_layout;
	private HLayout rollOverCanvas;

	public SubjektivnaOcjenaView(String name) {
		super(name);
	}
	
	@Override
	public void customWindowPreferences() {
		number_of_columns = 4;
		use_form=false;
		use_listgrid=true;
	}

	@Override
	public DataSource getModel() {
		return SubjektivnaOcjenaModel.getInstance();
	}

	@Override
	public DataSource getSifrarnikModel() {
		return PrnSifrarnikModel.getInstance();
	}
	
	@Override
	public void windowLayout() {
		_listGrid = getListGrid();
		_buttons_layout = getFormButtons();
		this.addMembers(_listGrid, _listGrid.getStatusBar(), _buttons_layout);
		SubjektivnaOcjenaView.view = this;
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}

	public HLayout getFormButtons() {
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
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true) {
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
		listGrid.setWidth100();
		listGrid.setShowResizeBar(false);
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setCellHeight(56);
		listGrid.setShowRowNumbers(true);
		listGrid.setDataSource(getModel());
		// filtering
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);

		// inline editting
		listGrid.setCanEdit(true);
		listGrid.setModalEditing(true);
		listGrid.setListEndEditAction(RowEndEditAction.STOP);
		listGrid.setSaveByCell(false);
		
//		listGrid.addRowEditorExitHandler(new RowEditorExitHandler() {
//			@Override
//			public void onRowEditorExit(RowEditorExitEvent event) {
//				SC.ask("Želite li spremiti unos?", new BooleanCallback(){
//					@Override
//					public void execute(Boolean value) {
//						if(value){
//							listGrid.saveAllEdits();
//						}
//					}
//				});
//			}
//		});
//		 Pageing
//		 listGrid.setDataPageSize(10);
//		 listGrid.setAutoSaveEdits(false);

		return listGrid;
	}

	/*********** ROLLOVER ************/
	private void prepareRollOver(ListGrid listGrid) {
		rollOverCanvas = new HLayout(3);
		rollOverCanvas.setSnapTo("TR");
		rollOverCanvas.setWidth(50);
		rollOverCanvas.setHeight(listGrid.getCellHeight());
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null));
		rollOverCanvas.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null));
	}
	
	/*********** Povratak sa Trazi window ************/
	@Override
	public void getValuesFromTrazi(Record selectedRecord) {
		setFieldValue("jmbg_mb",selectedRecord.getAttributeAsObject("jmbg_mb_"));
		setFieldValue("oib",selectedRecord.getAttributeAsObject("oib_"));
		setFieldValue("naziv_klijenta",selectedRecord.getAttributeAsObject("naziv"));
	}
	
	protected String getRecordIdName() {
		return PRN_OCJENA__ID_OCJENE;
	}
}
