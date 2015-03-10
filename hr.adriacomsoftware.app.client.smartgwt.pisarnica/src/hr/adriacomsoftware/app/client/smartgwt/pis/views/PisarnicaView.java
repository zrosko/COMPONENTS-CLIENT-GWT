package hr.adriacomsoftware.app.client.smartgwt.pis.views;

import hr.adriacomsoftware.app.client.smartgwt.pis.models.PisarnicaConstants;
import hr.adriacomsoftware.app.client.smartgwt.pis.models.PisarnicaModel;
import hr.adriacomsoftware.app.client.smartgwt.pis.models.PisarnicaSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2SpreadSheetMWindow;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.HashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * @author astrikoman
 *
 */ 
public class PisarnicaView extends AS2View2 implements  PisarnicaConstants {
	
	//private HLayout rollOverCanvas;

	public PisarnicaView(String name) {
		super(name);
	}
	@Override
	public void customWindowPreferences() {
		use_form=false;
		use_listgrid=true;
	}
	
	@Override
	public DataSource getModel() {
		return PisarnicaModel.getInstance();
	}
	
//	@Override
//	protected LinkedHashMap<String, LinkedHashMap<String, String>> getSifrarnikValueMaps() {
//		LinkedHashMap<String, LinkedHashMap<String, String>> mapa = new LinkedHashMap<>();
//		LinkedHashMap<String, String> naziv_vrste = new LinkedHashMap<>();
//		naziv_vrste.put("URA", "URA");
//		mapa.put("naziv", naziv_vrste);
//		LinkedHashMap<String, String> status_dokumenta = new LinkedHashMap<>();
//		status_dokumenta.put("Novi", "Novi");
//		status_dokumenta.put("Plaćeno", "Plaćeno");
//		status_dokumenta.put("Odobreno", "Odobreno");
//		mapa.put("status_dokumenta", status_dokumenta);
//		return mapa;
//	}

	@Override
	public DataSource getSifrarnikModel() {
		return PisarnicaSifrarnikModel.getInstance();
	}
	
	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}
	
	@Override
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				return getRolloverCanvas(rowNum,colNum,this);
			}
		};
		listGrid.setDataSource(getModel());
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setShowResizeBar(false);
						
		Criteria criteria = new Criteria(PISARNICA_APLIKACIJA,AS2ClientContext.getSessionValue(AS2ClientContext.APPLICATION_ID));
		listGrid.setCriteria(criteria);
		
		AS2ListGridField id_dokumenta = new AS2ListGridField(PISARNICA_ID_DOKUMENTA,"8%");
		AS2ListGridField id_vrste = new AS2ListGridField(PISARNICA_ID_VRSTE,AS2Field.COMBO);
		id_vrste.setWidth("8%");
		AS2ListGridField ulaz_izlaz =  new AS2ListGridField(PISARNICA_ULAZ_IZLAZ,AS2Field.COMBO);
		ulaz_izlaz.setWidth("8%");
		AS2ListGridField oib = new AS2ListGridField(PISARNICA_OIB, AS2Field.COMBO);
		ulaz_izlaz.setWidth("12%");
		AS2ListGridField datum =  new AS2ListGridField(PISARNICA_DATUM_DOKUMENTA,"8%");
		datum.getField().setAlign(Alignment.CENTER); 
		AS2ListGridField naziv_dokumenta = new AS2ListGridField(PISARNICA_NAZIV_DOKUMENTA,"20%");
		AS2ListGridField iznos = new AS2ListGridField(PISARNICA_IZNOS,"8%");
		iznos.getField().setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
		iznos.getField().setSummaryFunction(SummaryFunctionType.SUM);  
		AS2ListGridField ima_privitak = new AS2ListGridField(PISARNICA_IMA_PRIVITAK,"3%");
		AS2ListGridField organizacijska_jedinica =  new AS2ListGridField(PISARNICA_ORGANIZACIJSKA_JEDINICA,AS2Field.COMBO);
		organizacijska_jedinica.setWidth("12%");
		AS2ListGridField status_icon = new AS2ListGridField(PISARNICA_STATUS_ICON,AS2Field.IMAGE);
		status_icon.getField().setShowTitle(false);
		status_icon.getField().setAlign(Alignment.CENTER);  
		status_icon.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/s16/excel/");  
		status_icon.getField().setImageURLSuffix(".png");
		status_icon.setWidth(24);
		status_icon.getField().setCanSort(false);
		AS2ListGridField status_dokumenta =  new AS2ListGridField(PISARNICA_STATUS_DOKUMENTA,AS2Field.COMBO);
		status_dokumenta.setWidth("12%");
		listGrid.setFields(id_dokumenta,id_vrste,ulaz_izlaz,oib,datum,
						   naziv_dokumenta,iznos,ima_privitak,organizacijska_jedinica,
						   status_icon,status_dokumenta);
		listGrid.setDropDownModel(getSifrarnikModel(),id_vrste,ulaz_izlaz,status_dokumenta,organizacijska_jedinica);
		// summary
		listGrid.setShowGridSummary(true);
		listGrid.setShowGroupSummary(true);  
		listGrid.setShowGroupSummaryInHeader(true);  
		listGrid.setShowGridSummaryFields(false,id_dokumenta,id_vrste,ulaz_izlaz,oib,datum,
								    naziv_dokumenta,ima_privitak,organizacijska_jedinica,
								    status_icon,status_dokumenta);
		listGrid.setShowGroupSummaryFields(false,id_dokumenta,id_vrste,ulaz_izlaz,oib,datum,
									 naziv_dokumenta,ima_privitak,organizacijska_jedinica,
									 status_icon,status_dokumenta);
		//handlers
		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				if(_listGrid.getSelectedRecord()!=null){
						new PisarnicaDokumentMWindow(PisarnicaView.this,_listGrid.getSelectedRecord());
				}
			}
		});
		return listGrid;
	}
	
	@Override
	protected void getRollOverButtons(HLayout rollover, ListGrid listGrid) {
		ImgButton downloadButton = new ImgButton();
		downloadButton.setHeight(16);
		downloadButton.setWidth(16);
		downloadButton.setShowDown(false);
		downloadButton.setShowRollOver(false);
		downloadButton.setLayoutAlign(Alignment.CENTER);
		downloadButton.setSrc(AS2Resources.INSTANCE.open_document_icon().getSafeUri().asString());
		downloadButton.setPrompt("Otvori privitak");
		downloadButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if(AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject(PISARNICA_IMA_PRIVITAK)!=null
							&& AS2RolloverButtonFactory._rollOverRecord.getAttribute(PISARNICA_IMA_PRIVITAK).equalsIgnoreCase("NE")){
						SC.warn("Dokument nema privitak");
					}
					else
						downloadFile(AS2RolloverButtonFactory._rollOverRecord);
				}
			});
		rollover.addMember(AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						actionToolbarPreview(event);
					}
				}));
		rollover.addMember(downloadButton);
		rollover.addMember(rolloverDeleteButton);
	}
	
	protected ToolStrip getToolStrip(){
		ToolStrip toolStrip = super.getToolStrip();
		toolStrip.setMembers(toolbarAdd, toolbarPreview, toolbarDelete, toolbarDelete, toolbarFilter, toolbarRefresh);
		return toolStrip;
	}
	@Override
	protected void actionToolbarAdd(ClickEvent event) {
		new PisarnicaDokumentMWindow(PisarnicaView.this,null);
	}
	@Override
	protected void actionToolbarPreview(ClickEvent event) {
		new PisarnicaDokumentMWindow(PisarnicaView.this,_listGrid.getSelectedRecord());
	}
	@Override
	protected void actionToolbarAttachement(ClickEvent event) {
		if(_listGrid.getSelectedRecord().getAttributeAsObject(PISARNICA_IMA_PRIVITAK)!=null
				&& _listGrid.getSelectedRecord().getAttribute(PISARNICA_IMA_PRIVITAK).equalsIgnoreCase("NE")){
			SC.warn("Dokument nema privitak");
		}else{
			downloadFile(_listGrid.getSelectedRecord());
		}
	}
	
	@Override
	public HashMap<String, Object> getDownloadParameters(Record record) {
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("servlet_service", "download");
		parameters.put(AS2RestJSONDataSource.REMOTE_METHOD,  "citajPrivitak");
		parameters.put(AS2RestJSONDataSource.REMOTE_OBJECT,((AS2RestJSONDataSource)PisarnicaModel.getInstance()).getRemoteObject());
		parameters.put(AS2RestJSONDataSource.TRANSFORM_TO, ((AS2RestJSONDataSource)PisarnicaModel.getInstance()).getTransformTo());
		parameters.put("korisnik",AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME));
		for(String attribute: record.getAttributes()){
			parameters.put(attribute, record.getAttributeAsObject(attribute));
		}
		return parameters;
	}
	@Override
	protected String getRecordIdName() {
		return PISARNICA_ID_DOKUMENTA;
	}

}
