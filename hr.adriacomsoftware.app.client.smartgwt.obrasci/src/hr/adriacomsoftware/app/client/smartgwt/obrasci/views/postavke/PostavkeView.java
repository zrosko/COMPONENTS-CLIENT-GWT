package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.postavke;

/************* VIEW  MojiObrasciView ************/

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.postavke.PostavkeContextAreaModel;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class PostavkeView extends AS2View2 {

	private PostavkeContextAreaListGrid listGrid;

	public PostavkeView() {
		super();
	}

	@Override
	public DataSource getModel() {
		return PostavkeContextAreaModel.getInstance();
	}

/*	@Override
	public void initializeView() {
		this.listGrid = getContextAreaListGrid();
	}*/

	public void showWidgets() {
		OContext._obrasciView = this;
		this.addMembers(this.listGrid);
//		OContext._mojiObrasciStackSection.setExpanded(false);
		// fetchData();
	}

	@Override
	public AS2DynamicForm getDynamicForm() {
		return null;
	}

	@Override
	public AS2ListGrid getListGrid() {
		return null;
	}

	protected PostavkeContextAreaListGrid getContextAreaListGrid() {
		PostavkeContextAreaListGrid listGrid = new PostavkeContextAreaListGrid(
				getModel());
//		// register the ListGird handlers //TODO
		listGrid.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				Record record = event.getRecord();
				int colNum = event.getColNum();
				String columnName = "column1Name";

				// we are only interested in the columns 0 and 2 (the icon
				// columns)
				if (colNum >= 2) {
					colNum = 2;
					columnName = "column2Name";
				} else {
					colNum = 0;
				}

				String place = record.getAttribute(columnName);
				getView(place);

			}
		});
//		listGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
//			@Override
//			public void onCellDoubleClick(CellDoubleClickEvent event) {
//				Record record = event.getRecord();
//				int colNum = event.getColNum();
//				String columnName = "column1Name";
//
//				// we are only interested in the columns 0 and 2 (the icon
//				// columns)
//				if (colNum >= 2) {
//					colNum = 2;
//					columnName = "column2Name";
//				} else {
//					colNum = 0;
//				}
//
//				String place = record.getAttribute(columnName);
//
//				// if (getUiHandlers() != null) {
//				// getUiHandlers().onCellDoubleClicked(place);
//				// }
//			}
//		});
		return listGrid;
	}

	protected void getView(String viewName) {
		if(viewName.equalsIgnoreCase("Vrste Dokumenta")){
			new VrsteDokumentaMWindow(this);
		}

	}

	// private void fetchData() {
	// _listGrid.fetchData(getFetchCriteria(), serverCallBack(),null);
	// }

	// private Criteria getFetchCriteria() {
	// // Criteria for server
	// Criteria criteria = new Criteria();
	// criteria.addCriteria("service", "listajObrascePoKorisniku");
	// criteria.addCriteria("korisnik", OContext._obrasciDesktop._userId);
	// return criteria;
	// }

	// protected DSCallback serverCallBack() {
	// return new DSCallback() {
	// @Override
	// public void execute(DSResponse dsResponse, Object data, DSRequest
	// dsRequest) {
	// boolean error = ObrasciTransportModel.checkErrorsFormServer(dsResponse);
	// if (!error) {
	// if(dsResponse.getDataAsRecordList().get(0).getAttributeAsObject("updated")!=null){
	// SC.say("Izbrisano");
	// refreshView();
	// }else{
	// RecordList tempRecordList =
	// dsResponse.getDataAsRecordList().get(0).getAttributeAsRecordList("records");
	// for(int i=0;i<tempRecordList.getLength();i++){
	// Record record = tempRecordList.get(i).getAttributeAsRecord("record");
	//
	// // _opisGrid = new ListGridField("opis", "Opis", 150);
	// // _opisGrid.setType(ListGridFieldType.TEXT);
	// // if(i==0){
	// // lgridFields = new ListGridField[record.getAttributes().length+1];
	// // ListGridField tipDokumentaGrid = new ListGridField("tip_dokumenta",
	// "Tip", 50);
	// // tipDokumentaGrid.setType(ListGridFieldType.IMAGE);
	// // tipDokumentaGrid.setAlign(Alignment.CENTER);
	// // tipDokumentaGrid.setImageSize(32);
	// // tipDokumentaGrid.setImageURLPrefix("icons/file_extensions/32/");
	// // tipDokumentaGrid.setImageURLSuffix(".png");
	// // lgridFields[columnCount++] = tipDokumentaGrid;
	// // for (String attribute : record.getAttributes()) {
	// // if(!attribute.contains("tip_dokumenta") ||
	// !attribute.contains("naziv")|| !attribute.contains("datum_kreiranja")||
	// !attribute.contains("opis")){
	// // DetailViewerField detailViewerField = new DetailViewerField(attribute,
	// attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length()));
	// // _listGrid.setDetailField(attribute);
	// //
	// //// ListGridField field = new ListGridField();
	// //// field.setType(ListGridFieldType.TEXT);
	// //// field.setName(attribute);
	// ////
	// field.setTitle(attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length()));
	// //// if(attribute.equals("datum_kreiranja")){
	// //// field.setTitle("Datum Kreiranja");
	// //// field.setType(ListGridFieldType.DATE);
	// //// }
	// //// lgridFields[columnCount++] = field;
	// // }
	// // }
	// // }
	// // record.setAttribute("tip_dokumenta", "pdf");
	// // record.setAttribute("profitni_centar",
	// record.getAttributeAsInt("profitni_centar"));
	// // record.setAttribute("organizacijska_jedinica",
	// record.getAttributeAsInt("organizacijska_jedinica"));
	// // record.setAttribute("datum_kreiranja",
	// AS2ClientContext._defaultDateFormatter.format(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").parse(record.getAttribute("datum_kreiranja"))));
	// record.setAttribute("datum_kreiranja",
	// AS2ClientContext.formatDBStringDateStringToDate(record.getAttribute("datum_kreiranja")));
	// tempRecordList.set(i, record);
	// }
	// _recordListFromServer = tempRecordList;
	// _listGrid.setData(_recordListFromServer);
	// _listGrid.updateStatusBar();
	// PostavkeView.this.setMembers(PostavkeView.this.listGrid);
	// OContext._mojiObrasciStackSection.setExpanded(false);
	// }
	// }
	// }
	// };
	// }

	protected ToolStrip getToolStrip() {
		return null;
		// ToolStrip toolStrip = new ToolStrip();
		// // initialise the buttons
		// first = new ToolStripButton();
		// first.setTitle(FIRST_TOOLBAR_MENU_BUTTON_DISPLAY_NAME);
		// first.setIcon(DOCUMENT_NEW_TOOLBAR_BUTTON);
		// first.setDisabled(true);
		// first.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// if (_listGrid.getSelectedRecord() == null) {
		// SC.warn("Odaberite obrazac");
		// } else{
		// // if
		// (!_listGrid.getSelectedRecord().getAttribute("vrsta_zahtjeva").equalsIgnoreCase("dodatni"))
		// {
		// // zahtjev_modal = new
		// VrsteDokumentaMWindow(current_view,_listGrid.getSelectedRecord());
		// // } else {
		// // zahtjev_dodatni_modal = new
		// McardGrZahtjevDodatniMWindow(current_view,
		// _listGrid.getSelectedRecord());
		// //
		// // }
		// }
		// }
		// });
		// toolStrip.addButton(first);
		// //
		// // second = new ToolStripButton();
		// // second.setTitle(SECOND_TOOLBAR_BUTTON_DISPLAY_NAME);
		// // second.setIcon(CREDIT_CARD_TOOLBAR_BUTTON);
		// // second.addClickHandler(new ClickHandler() {
		// // public void onClick(ClickEvent event) {
		// // if(_listGrid.getSelectedRecord()==null)
		// // SC.warn("Odaberite Zahtjev");
		// //// else
		// //// uvjeti_modal = new McardGrZahtjevUvjetiMWindow(current_view,
		// _listGrid.getSelectedRecord());
		// //
		// //
		// // }
		// // });
		// // toolStrip.addButton(second);
		// //
		// // third = new ToolStripButton();
		// // third.setTitle(THIRD_TOOLBAR_BUTTON_DISPLAY_NAME);
		// // third.setIcon(SCORE_TOOLBAR_BUTTON);
		// // third.addClickHandler(new ClickHandler() {
		// // public void onClick(ClickEvent event) {
		// // if(_listGrid.getSelectedRecord()==null)
		// // SC.warn("Odaberite Zahtjev");
		// // else{
		// ////
		// if(!_listGrid.getSelectedRecord().getAttributeAsString("vrsta_kartice").equalsIgnoreCase("Revolving"))
		// ////
		// SC.warn("Izračun ocjena (skoring) se radi samo za revolving kartice!");
		// // }
		// // }
		// // });
		// // toolStrip.addButton(third);
		// //
		// // fourth = new ToolStripButton();
		// // fourth.setTitle(FOURTH_TOOLBAR_BUTTON_DISPLAY_NAME);
		// // fourth.setIcon(STATUS_TOOLBAR_BUTTON);
		// // fourth.addClickHandler(new ClickHandler() {
		// // public void onClick(ClickEvent event) {
		// // if(_listGrid.getSelectedRecord()==null)
		// // SC.warn("Odaberite Zahtjev");
		// // }
		// // });
		// // toolStrip.addButton(fourth);
		// //
		// // fifth = new ToolStripButton();
		// // fifth.setTitle(FIFTH_TOOLBAR_BUTTON_DISPLAY_NAME);
		// // fifth.setIcon(ATTACHEMENT_BUTTON);
		// // fifth.addClickHandler(new ClickHandler() {
		// // public void onClick(ClickEvent event) {
		// // if(_listGrid.getSelectedRecord()==null)
		// // SC.warn("Odaberite Zahtjev");
		// //// else
		// //// privitak_modal = new VrsteDokumentaMWindow(current_view,
		// _listGrid.getSelectedRecord());
		// // }
		// // });
		// // toolStrip.addButton(fifth);
		// toolStrip.addSeparator();
		// //
		// sixth = new ToolStripButton();
		// sixth.setIcon(DELETE_TOOLBAR_BUTTON);
		// sixth.setTooltip(DELETE_TOOLBAR_BUTTON_HINT);
		// sixth.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event){
		// String text="Da li ste sigurni da želite obrisati dokument?";
		// if(_listGrid.getSelectedRecords().length!=1)
		// text="Da li ste sigurni da želite obrisati označene dokumente?";
		// SC.confirm("Upozorenje!", text,
		// new BooleanCallback() {
		// @Override
		// public void execute(Boolean value) {
		// if (value != null && value) {
		// if(_listGrid!=null){
		// Record recordToDelete = new Record();
		// recordToDelete.setAttribute("service","brišiMojiObrasci");
		// recordToDelete.setAttribute("id_dokumenta","999"); //dummy id
		// if(_listGrid.getSelectedRecords().length==1){
		// recordToDelete.setAttribute("object0",
		// _listGrid.getSelectedRecord().getAttribute("id_dokumenta"));
		// // Record.copyAttributesInto(recordToDelete,
		// _listGrid.getSelectedRecord(),_listGrid.getSelectedRecord().getAttributes());
		// }else{
		// ListGridRecord[] selectedRecords = _listGrid.getSelectedRecords();
		// int count=0;
		// for(ListGridRecord record:selectedRecords){
		// recordToDelete.setAttribute("object"+count++,
		// record.getAttribute("id_dokumenta"));
		// }
		// }
		// _listGrid.removeData(recordToDelete,serverCallBack());
		// }
		// }
		// }
		// });
		// }
		// });
		// toolStrip.addButton(sixth);
		//
		// seventh = new ToolStripButton();
		// seventh.setIcon(FILTER_TOOLBAR_BUTTON);
		// seventh.setTooltip(FILTER_TOOLBAR_BUTTON_HINT);
		// seventh.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event){
		// if(!_listGrid.getShowFilterEditor()){
		// _listGrid.setShowFilterRow(true,true);
		// }else{
		// _listGrid.setShowFilterRow(false,false);
		// }
		// }
		// });
		// toolStrip.addButton(seventh);
		//
		// eight = new ToolStripButton();
		// eight.setIcon(SEARCH_TOOLBAR_BUTTON);
		// eight.setTooltip(SEARCH_TOOLBAR_BUTTON_HINT);
		// eight.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event){
		// // @SuppressWarnings("unused")
		// // McardGrZahtjevSearchMWindow searchWindow = new
		// McardGrZahtjevSearchMWindow(current_view,_listGrid);
		// }
		// });
		// toolStrip.addButton(eight);
		//
		// ninth = new ToolStripButton();
		// ninth.setIcon(REFRESH_TOOLBAR_BUTTON);
		// ninth.setTooltip(REFRESH_TOOLBAR_BUTTON_HINT);
		// ninth.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event){
		// refreshView();
		// }
		// });
		// toolStrip.addButton(ninth);
		// return toolStrip;
	}

	// There is no sifrarnik
	@Override
	public DataSource getSifrarnikModel() {
		return null;
	}

	protected void fillCacheFromSifrarnik() {
	}

	@Override
	protected String getRecordIdName() {
		// TODO Auto-generated method stub
		return null;
	}

}
