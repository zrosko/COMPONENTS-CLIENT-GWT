package hr.adriacomsoftware.app.client.smartgwt.trazi.views;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class TraziWindow extends AS2Window{
	private static int DEFAULT_WIDTH = 650;
	private static int DEFAULT_HEIGHT = 400;
	private Criteria _criteria = null;
	private AS2Window _parent_window = null;
	private HLayout _buttons_layout;
	
	public TraziWindow(DataSource dataSource, AS2View2 parent,Criteria criteria, String[] polja) {
		super();
		this._parent_view = parent;
		this._dataSource = dataSource;
		this._criteria = criteria;
		for(DataSourceField dsField: _dataSource.getFields()){
			dsField.setHidden(true);
			dsField.setCanFilter(false);
			for(String polje:polja){
				if(dsField.getName().equals(polje)){
					dsField.setHidden(false);
					dsField.setCanFilter(true);
					break;
				}
			}
		}
		createComponents();
		windowLayout();
	}
	
	//TODO
	public TraziWindow(DataSource dataSource, AS2Window parent,Criteria criteria, String[] polja) {
		super();
		this._parent_window = parent;
		this._dataSource = dataSource;
		this._criteria = criteria;
		for(DataSourceField dsField: _dataSource.getFields()){
			dsField.setHidden(true);
			dsField.setCanFilter(false);
			for(String polje:polja){
				if(dsField.getName().equals(polje)){
					dsField.setHidden(false);
					dsField.setCanFilter(true);
					break;
				}
			}
		}
		createComponents();
		windowLayout();
	}

	public TraziWindow(DataSource dataSource, AS2View2 parent, Criteria criteria) {
		super();
		this._parent_view = parent;
		this._dataSource = dataSource;
		this._criteria = criteria;
		createComponents();
		windowLayout();
	}

	@Override
	public void createComponents() {
		_listGrid = getListGrid();
		_buttons_layout = getButtonsLayout();
	}

	@Override
	public void windowLayout() {
		this.setTitle("Pretraživanje");
		this.setWidth(DEFAULT_WIDTH);
		this.setHeight(DEFAULT_HEIGHT);
		this.setCanDragResize(true);
		this.setShowMaximizeButton(true);
		this.addItem(_listGrid);
		this.addItem(_listGrid.getStatusBar());
		this.addItem(_buttons_layout);
		this.addCloseClickHandler(new CloseClickHandler() {
            public void onCloseClick(CloseClickEvent event) {
                closeWindow(false);
            }
        });
		this.draw();
	}

	public AS2ListGrid getListGrid(){
		final AS2ListGrid listGrid= new AS2ListGrid(true);
		listGrid.setDataSource(_dataSource);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setShowResizeBar(false);
		listGrid.setWrapCells(true);
//		listGrid.setAutoFitFieldWidths(true);
//		listGrid.setAutoFitFieldsFillViewport(true);
		listGrid.setSelectionType(SelectionStyle.SINGLE);
		//filtering
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(false);
		listGrid.addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {
			@Override
			public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
				listGrid.setCriteria(event.getCriteria());
				listGrid.refresh();
			}
		});
		listGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				setValuesFromTrazi();
			}
		});
		if(_criteria!=null){
			listGrid.setCriteria(_criteria);
		}
		return listGrid;
	}
	
	
	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(2);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setWidth100();
		buttons_layout.setAutoHeight();
		buttons_layout.setShowEdges(true);
		_button_spremi = new IButton("Odaberi");
		_button_spremi.setIcon(AS2Resources.INSTANCE.save_icon().getSafeUri().asString());
		_button_spremi.setAutoFit(true);
		_button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setValuesFromTrazi();
			}
		});
		buttons_layout.addMember(_button_spremi);
		return buttons_layout;
	}
	
	private void setValuesFromTrazi() {
		if(_listGrid.getSelectedRecord()!=null){
			if(_criteria!=null)
				_listGrid.getSelectedRecord().setAttribute("lookup_type",_criteria.getAttribute("lookup_type"));
			if(_parent_view!=null)
				_parent_view.getValuesFromTrazi(_listGrid.getSelectedRecord());
			else if(_parent_window!=null)
				_parent_window.getValuesFromTrazi(_listGrid.getSelectedRecord());
			closeWindow(false);
		}else{
			SC.warn("Niste odabrali liniju!");
		}
	}

}

