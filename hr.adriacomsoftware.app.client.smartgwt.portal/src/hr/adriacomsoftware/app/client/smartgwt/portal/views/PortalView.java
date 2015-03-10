package hr.adriacomsoftware.app.client.smartgwt.portal.views;

import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalConstants;
import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalModel;
import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalObavijestiConstants;
import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalObavijestiModel;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PortalView extends AS2View2 implements PortalObavijestiConstants, PortalConstants {
	
	private TileGrid _tileGrid;
	private VLayout _newsLayout;
	private Label _obavijestiTitle;
	public PortalObavijestMWindow portal_obavijest_modal;
	private ToolStripButton _editButton;
	private ToolStripButton _removeButton;

	public PortalView(String name) {
		super(name);
	}
	
	@Override
	public void customWindowPreferences() {
		this.setWidth100();
		this.setHeight100();
		this.setStyleName("");
		number_of_columns = 5;
		use_form=false;
		use_listgrid=true;
	}
	
	@Override
	public void windowLayout() {
		_tileGrid = getTileGrid();
		_newsLayout = getNewsLayout();
		HLayout layout = new HLayout();
		layout.setMembers(_tileGrid,_newsLayout);
		this.setMembers(layout);
//		_toolStrip=getToolStrip();
//		this.addMembers(_form,_toolStrip,_listGrid,_listGrid.getStatusBar());
	}


//	private void getApplications(TileGrid tileGrid) {
//		Criteria criteria = new Criteria();
//		for(String name: Cookies.getCookieNames()){
//			criteria.addCriteria(name,Cookies.getCookie(name));
//		}
//		criteria.addCriteria("remoteobject","hr.as2.inf.server.security.authorization.facade.RBACSecurityAuthorizationFacadeServer");
//		criteria.addCriteria("remotemethod","procitajAplikacije");
//		criteria.addCriteria("transform_to","hr.as2.inf.common.security.user.J2EEUser");
//		tileGrid.fetchData(criteria, new DSCallback() {
//			
//			@Override
//			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		
//	}
	
	@Override
	public DataSource getModel() {
		return PortalModel.getInstance();
	}
	
	public DataSource getObavijestiModel() {
		return PortalObavijestiModel.getInstance();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return null;
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}
	
	private VLayout getNewsLayout() {
		VLayout news = new VLayout();
		news.setWidth("30%");
		_obavijestiTitle = new Label("<b style=\"font-size:10pt;\">Obavijesti</b></br>");
		_obavijestiTitle.setHeight(40);
		_listGrid = getListGrid();
		_toolStrip = getObavijestiToolStrip();
		news.setMembers(_obavijestiTitle,_listGrid);
		news.setShowEdges(true);
		news.setPadding(20);
		return news;
	}
	
	private TileGrid getTileGrid() {
		final TileGrid tileGrid = new TileGrid();
		tileGrid.setDataSource(getModel());
		tileGrid.setAutoFetchData(true);
		tileGrid.setTileWidth(194);  
		tileGrid.setTileHeight(165);  
		tileGrid.setShowEdges(true);
		tileGrid.setHeight100();  
		tileGrid.setWidth100();  
		tileGrid.setCanReorderTiles(true);  
		tileGrid.setShowAllRecords(true);  
		tileGrid.setShowResizeBar(true);

		DetailViewerField pictureField = new DetailViewerField(PORTAL__APPLICATION);  
		pictureField.setType("image");  
		pictureField.setImageURLPrefix("../module/images/applications/"); 
		pictureField.setImageWidth(120);  
		pictureField.setImageHeight(120);  

		DetailViewerField nameField = new DetailViewerField(PORTAL__NAZIV);  

		tileGrid.setFields(pictureField, nameField);  
		
		tileGrid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(tileGrid.getSelectedRecord()!=null){
					String url = tileGrid.getSelectedRecord().getAttribute(PORTAL__URL_PROD);
					if(AS2ClientContext.ENVIROMENT.equalsIgnoreCase("test"))
						url = tileGrid.getSelectedRecord().getAttribute(PORTAL__URL_TEST);
					com.google.gwt.user.client.Window.open(url,"_blank","");
					tileGrid.deselectAllRecords();
				}
			}
		});
		return tileGrid;
	}

	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid();
		listGrid.setDataSource(getObavijestiModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false); 
		listGrid.setShowResizeBar(false);
		listGrid.setShowHeader(false);
		listGrid.setBorder("0px");
		listGrid.setSelectionType(SelectionStyle.SINGLE);
		listGrid.setEditEvent(ListGridEditEvent.CLICK);
		//grouping
		listGrid.setGroupStartOpen(GroupStartOpen.ALL); 
		listGrid.setGroupByField(PORTAL__KORISNIK_VRIJEME); 
		listGrid.setCanCollapseGroup(false);
		
		AS2ListGridField id_obavijesti = new AS2ListGridField(PORTAL__ID_OBAVIJESTI);
		id_obavijesti.getField().setHidden(true);
		AS2ListGridField obavijest = new AS2ListGridField(PORTAL__OBAVIJEST);
		obavijest.getField().setHidden(true);
		AS2ListGridField obavijest_html = new AS2ListGridField(PORTAL__OBAVIJEST_HTML, AS2Field.RICHTEXT);
		AS2ListGridField vrijeme_izmjene = new AS2ListGridField(PORTAL__VRIJEME_IZMJENE);
		vrijeme_izmjene.getField().setHidden(true);
		AS2ListGridField korisnik = new AS2ListGridField(PORTAL__KORISNIK);
		korisnik.getField().setHidden(true);
		AS2ListGridField ispravno = new AS2ListGridField(PORTAL__ISPRAVNO);
		ispravno.getField().setHidden(true);
		AS2ListGridField korisnik_vrijeme = new AS2ListGridField(PORTAL__KORISNIK_VRIJEME);
		korisnik_vrijeme.getField().setHidden(true);
				
		listGrid.setFields(id_obavijesti, obavijest, obavijest_html, vrijeme_izmjene, korisnik, ispravno,korisnik_vrijeme);
		
		//handler
		
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				Record obavijest = event.getRecord();
				String username = AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME);
				String role = AS2ClientContext.getSessionValue(AS2ClientContext.ROLE);
				if(username.equals(obavijest.getAttribute(PORTAL__KORISNIK))
						|| role.equalsIgnoreCase("Administrator")){
					_editButton.setDisabled(false);
					_removeButton.setDisabled(false);
				}else{
					_editButton.setDisabled(true);
					_removeButton.setDisabled(true);
				}
			}
		});
		
		return listGrid;
	}
	
	
	private ToolStrip getObavijestiToolStrip(){
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setHeight(24);

		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setWidth("*");

		ToolStripButton refreshButton = new ToolStripButton();
		refreshButton.setIcon("[SKIN]/actions/refresh.png");
		refreshButton.setPrompt("Osvježi");
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_listGrid.refresh();
			}
		});
		
		ToolStripButton addButton = new ToolStripButton();
		addButton.setIcon("[SKIN]/actions/add.png");
		addButton.setPrompt("Dodaj obavijest");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				portal_obavijest_modal = new PortalObavijestMWindow(PortalView.this, null);
				if(_listGrid.getSelectedRecords().length!=0)
					_listGrid.deselectAllRecords();
			}
		});

		_editButton = new ToolStripButton();
		_editButton.setDisabled(true);
		_editButton.setIcon("[SKIN]/actions/edit.png");
		_editButton.setPrompt("Uredi obavijest");
		_editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null){
					SC.warn("Odaberite obavijest");
				}else{
					portal_obavijest_modal = new PortalObavijestMWindow(PortalView.this, _listGrid.getSelectedRecord());
				}
			}
		});

		_removeButton = new ToolStripButton();
		_removeButton.setDisabled(true);
		_removeButton.setIcon("[SKIN]/actions/remove.png");
		_removeButton.setPrompt("Obriši obavijest");
		_removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_listGrid.removeSelectedData();

			}
		});

		toolStrip.setMembers(refreshButton, spacer, addButton, _editButton, _removeButton);

		//Raspored komponenti tablice
		_listGrid.setGridComponents(new Object[] {ListGridComponent.FILTER_EDITOR, ListGridComponent.HEADER, ListGridComponent.BODY, toolStrip});
		return toolStrip;
	}

	@Override
	protected String getRecordIdName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

