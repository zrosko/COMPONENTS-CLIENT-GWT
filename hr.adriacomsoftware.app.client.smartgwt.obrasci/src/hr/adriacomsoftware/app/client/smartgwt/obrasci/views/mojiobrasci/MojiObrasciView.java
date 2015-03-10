package hr.adriacomsoftware.app.client.smartgwt.obrasci.views.mojiobrasci;

/************* VIEW  MojiObrasciView ************/

import hr.adriacomsoftware.app.client.smartgwt.obrasci.OContext;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.ObrasciTransportModel;
import hr.adriacomsoftware.app.client.smartgwt.obrasci.models.mojiobrasci.MojiObrasciModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class MojiObrasciView extends AS2View {

	private static final String FIRST_TOOLBAR_BUTTON_DISPLAY_NAME = "Novi dokument";
	private static final String SECOND_TOOLBAR_BUTTON_DISPLAY_NAME = "Pregled";
	private AS2ListGridField _idDokumentaGrid;
	private AS2ListGridField _tipDokumentaGrid;
	private AS2ListGridField _nazivDokumentaGrid;
	private AS2ListGridField _datumKreiranjaGrid;
	private AS2ListGridField _korisnikGrid;
	private AS2ListGridField _profitniCentarGrid;
	private AS2ListGridField _orgJedinicaGrid;
	public static RecordList _recordListFromServer;
	public SectionStackSection _sectionStackSection;

	@Override
	public DataSource getModel() {
		return MojiObrasciModel.getInstance();
	}

	@Override
	protected void initializeView() {
		
	}
	@Override
	public DataSource getSifrarnikModel(){
		return null;
	}

	public void showWidgets() {
		_listGrid = getListGrid();
//		OContext._obrasciDesktop.setContextAreaViewVLayout(this, "Moji obrasci");
		OContext._obrasciView = this;
		MojiObrasciView.this.addMembers(_toolBar,_listGrid,_listGrid.getStatusBar());
		OContext._mojiObrasciStackSection.setExpanded(false);
//		fetchData();
	}

	@Override
	protected AS2DynamicForm getDynamicForm() {
		return null;
	}

	@Override
	protected AS2ListGrid getListGrid() {
		AS2ListGrid listGrid = new AS2ListGrid(true, null);
//		{
////			@Override
////			public DataSource getRelatedDataSource(ListGridRecord record) {
////				return _listGrid.getDataSource();
////			}
//            @Override
//            protected Canvas getExpansionComponent(final ListGridRecord record) {
////                final AS2ListGrid innerGrid = new AS2ListGrid(true,null);
////                innerGrid.setWidth100();
////                innerGrid.setHeight(224);
//////                innerGrid.setCellHeight(22);
////                innerGrid.setCellHeight(40);
////
//////                innerGrid.setDataSource(getRelatedDataSource(record));
//////                innerGrid.fetchRelatedData(record, ObrasciTransportModel.getInstance());
////
////                //Id dokumenta
////                ListGridField id_dokumenta = new ListGridField("id_dokumenta","Id dokumenta",5);
////                id_dokumenta.setType(ListGridFieldType.INTEGER);
////
////        		//Id vrste
////                ListGridField id_vrste = new ListGridField("id_vrste","Id vrste",5);
////                id_vrste.setType(ListGridFieldType.INTEGER);
////
////        		//oib
////                ListGridField oib = new ListGridField("oib","Oib",75);
////                oib.setType(ListGridFieldType.INTEGER);
////
////        		//broj_partije
////                ListGridField broj_partije = new ListGridField("broj_partije","Broj partije",75);
////                broj_partije.setType(ListGridFieldType.INTEGER);
////
////        		//opis
////                ListGridField opis = new ListGridField("opis","Opis");
////                opis.setWidth("*");
////                opis.setType(ListGridFieldType.TEXT);
////
////        		//tip_klijenta
////                ListGridField tip_klijenta = new ListGridField("tip_klijenta","Tip klijenta",5);
////                tip_klijenta.setType(ListGridFieldType.TEXT);
////
////                innerGrid.setFields(id_dokumenta,id_vrste,oib,broj_partije,opis,tip_klijenta);
////                innerGrid.setData(listGrid.getDataAsRecordList());
////                innerGrid.setCanEdit(true);
////                innerGrid.setModalEditing(true);
////                innerGrid.setEditEvent(ListGridEditEvent.CLICK);
////                innerGrid.setListEndEditAction(RowEndEditAction.NEXT);
////                innerGrid.setAutoSaveEdits(false);
////                return innerGrid;
//
//				DetailViewer innerGridDetailViewer = new DetailViewer();
//				// printViewer.setDataSource(countryDS);
//				innerGridDetailViewer.setWidth100();
//				innerGridDetailViewer.setHeight(150);
//				innerGridDetailViewer.setMargin(15);
//				// Id dokumenta
//				// ListGridField id_dokumenta = new
//				// ListGridField("id_dokumenta","Id dokumenta",5);
//				// id_dokumenta.setType(ListGridFieldType.INTEGER);
//				// DetailViewerField id_dokumenta = new
//				// DetailViewerField("id_dokumenta","Id dokumenta");
//
//				// Id vrste
//				// ListGridField id_vrste = new
//				// ListGridField("id_vrste","Id vrste",5);
//				// id_vrste.setType(ListGridFieldType.INTEGER);
//				// DetailViewerField id_vrste = new
//				// DetailViewerField("id_vrste","Id vrste");
//
//				// oib
//				// ListGridField oib = new ListGridField("oib","Oib",75);
//				// oib.setType(ListGridFieldType.INTEGER);
//				DetailViewerField oib = new DetailViewerField("oib", "Oib");
//
//				// broj_partije
//				// ListGridField broj_partije = new
//				// ListGridField("broj_partije","Broj partije",75);
//				// broj_partije.setType(ListGridFieldType.INTEGER);
//				DetailViewerField broj_partije = new DetailViewerField(
//						"broj_partije", "Broj partije");
//
//				// opis
//				// ListGridField opis = new ListGridField("opis","Opis");
//				// opis.setWidth("*");
//				// opis.setType(ListGridFieldType.TEXT);
//				DetailViewerField opis = new DetailViewerField("opis", "Opis");
//				opis.setHeight(50);
//
//				// lokacija
//				DetailViewerField lokacija = new DetailViewerField("lokacija",
//						"Lokacija");
//
//				// verzija
//				DetailViewerField verzija = new DetailViewerField("verzija","Verzija");
//
//				// tip_klijenta
//				// ListGridField tip_klijenta = new
//				// ListGridField("tip_klijenta","Tip klijenta",5);
//				// tip_klijenta.setType(ListGridFieldType.TEXT);
//				// DetailViewerField tip_klijenta = new
//				// DetailViewerField("tip_klijenta","Tip klijenta");
//				innerGridDetailViewer.setFields(
//						/* id_dokumenta,id_vrste, */oib, broj_partije, opis,
//						verzija, lokacija/* ,tip_klijenta */);
//				RecordList recordList = new RecordList();
//				recordList.add(record);
//				innerGridDetailViewer.setData(recordList);
//				return innerGridDetailViewer;
//            }
//        };
		listGrid.setDataSource(getModel());
//		listGrid.setAutoFetchData(false);
		listGrid.setCanExpandRecords(true);
	    listGrid.setExpansionMode(ExpansionMode.DETAILS);
//	    listGrid.setCriteria(new Criteria("korisnik", OContext._obrasciDesktop._userId));
	
		_idDokumentaGrid = new AS2ListGridField("id_dokumenta");
		_idDokumentaGrid.setTitle("ID");
		_idDokumentaGrid.setWidth(50);
		_tipDokumentaGrid = new AS2ListGridField("tip_dokumenta");
		_tipDokumentaGrid.getField().setImageURLPrefix(AS2Resources.IMAGE_PATH+"icons/file_extensions/s32/");
		_tipDokumentaGrid.getField().setImageURLSuffix(".png");
		_tipDokumentaGrid.getField().setAlign(Alignment.CENTER);
		_tipDokumentaGrid.setWidth(50);
		_nazivDokumentaGrid = new AS2ListGridField("naziv");
		_datumKreiranjaGrid = new AS2ListGridField("datum_kreiranja");
		_datumKreiranjaGrid.setWidth(75);
		_korisnikGrid = new AS2ListGridField("korisnik");
		_korisnikGrid.setWidth(100);
		_profitniCentarGrid = new AS2ListGridField("profitni_centar");
		_profitniCentarGrid.setWidth(75);
		_orgJedinicaGrid = new AS2ListGridField("organizacijska_jedinica");
		_orgJedinicaGrid.setWidth(75);
		listGrid.setFields(_tipDokumentaGrid,_idDokumentaGrid,_nazivDokumentaGrid,_datumKreiranjaGrid,_korisnikGrid,_profitniCentarGrid,_orgJedinicaGrid);
//		listGrid.addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {
//			@Override
//			public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
//				_listGrid.setFilterLocalData(true);
//				_listGrid.filterData(event.getCriteria());
//			}
//		});
		listGrid.addRecordDoubleClickHandler(ObrasciTransportModel.mojiObrasciViewRecordClickHandler());
		return listGrid;
	}

//	private RecordDoubleClickHandler getMojiObrasciViewRecordClickHandler(AS2ListGrid listGrid) {
//		listGrid.fetchData()new DSRequest(DSOperationType.CUSTOM,"izracunajPlanOtplate")
//		return null;
//	}

//	private void fetchData() {
//		_listGrid.fetchData(new Criteria("korisnik", OContext._obrasciDesktop._userId), serverCallBack(),null);
//	}
	
//
//	private Criteria getFetchCriteria() {
//		// Criteria for server
//		Criteria criteria = new Criteria();
//		criteria.addCriteria("service", "procitajSveDokumenteKorisnika");
//		criteria.addCriteria("korisnik", OContext._obrasciDesktop._userId);
//		return criteria;
//	}

//	protected DSCallback serverCallBack() {
//		return new DSCallback() {
//			@Override
//			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				boolean error = ObrasciTransportModel.checkErrorsFormServer(dsResponse);
//				if (!error) {
//					if(dsResponse.getDataAsRecordList().get(0).getAttributeAsObject("updated")!=null){
//						SC.say("Uspješno brisanje");
//						refreshView();
//					}else{
//						RecordList tempRecordList = dsResponse.getDataAsRecordList().get(0).getAttributeAsRecordList("records");
//						for(int i=0;i<tempRecordList.getLength();i++){
//							Record record = tempRecordList.get(i).getAttributeAsRecord("record");
//
//	//						_opisGrid = new ListGridField("opis", "Opis", 150);
//	//						_opisGrid.setType(ListGridFieldType.TEXT);
//	//						if(i==0){
//	//							lgridFields = new ListGridField[record.getAttributes().length+1];
//	//							ListGridField tipDokumentaGrid = new ListGridField("tip_dokumenta", "Tip", 50);
//	//							tipDokumentaGrid.setType(ListGridFieldType.IMAGE);
//	//							tipDokumentaGrid.setAlign(Alignment.CENTER);
//	//							tipDokumentaGrid.setImageSize(32);
//	//							tipDokumentaGrid.setImageURLPrefix("icons/file_extensions/32/");
//	//							tipDokumentaGrid.setImageURLSuffix(".png");
//	//							lgridFields[columnCount++] = tipDokumentaGrid;
//	//							for (String attribute : record.getAttributes()) {
//	//								if(!attribute.contains("tip_dokumenta") || !attribute.contains("naziv")|| !attribute.contains("datum_kreiranja")|| !attribute.contains("opis")){
//	//									DetailViewerField detailViewerField = new DetailViewerField(attribute, attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length()));
//	//									_listGrid.setDetailField(attribute);
//				//
//	////									ListGridField field = new ListGridField();
//	////									field.setType(ListGridFieldType.TEXT);
//	////									field.setName(attribute);
//	////									field.setTitle(attribute.substring(0,1).toUpperCase()+attribute.substring(1,attribute.length()));
//	////									if(attribute.equals("datum_kreiranja")){
//	////										field.setTitle("Datum Kreiranja");
//	////										field.setType(ListGridFieldType.DATE);
//	////									}
//	////									lgridFields[columnCount++] = field;
//	//								}
//	//							}
//	//						}
//	//						record.setAttribute("tip_dokumenta", "pdf");
//	//						record.setAttribute("profitni_centar", record.getAttributeAsInt("profitni_centar"));
//	//						record.setAttribute("organizacijska_jedinica", record.getAttributeAsInt("organizacijska_jedinica"));
//	//						record.setAttribute("datum_kreiranja", AS2ClientContext._defaultDateFormatter.format(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").parse(record.getAttribute("datum_kreiranja"))));
//							record.setAttribute("datum_kreiranja", AS2ClientContext.formatStringDateTimeToDate(record.getAttribute("datum_kreiranja")));
//							tempRecordList.set(i, record);
//						}
//						_recordListFromServer = tempRecordList;
						
						
						
//						_recordListFromServer = ((AS2RestJSONDataSource) getModel()).getRecordsFromServerJSON(dsResponse.getDataAsRecordList());
//						_listGrid.setData(_recordListFromServer);
//						_listGrid.updateStatusBar();
						
						
//						MojiObrasciView.this.addMembers(_toolBar,_listGrid,_listGrid.getStatusBar());
//						OContext._mojiObrasciStackSection.setExpanded(false);
//					}
//				}
//			}
//		};
//	}
	
	
//	this.addNodeClickHandler(new NodeClickHandler() {
//		@Override
//		public void onNodeClick(NodeClickEvent event) {
//			if (event.getNode() != null) {
//				TreeNode selectedRecord = event.getNode();
//				if (!selectedRecord.getAttributeAsBoolean("isFolder")) {
//					if (selectedRecord.getIcon() != null) {
//						Criteria criteria = new Criteria("service","showPdfForm");
//						criteria.addCriteria("rootUrl",OContext.URL);
//						ObrasciTransportModel.callServer(selectedRecord,criteria);
//					}
//				} else {
//					ObrasciNavigationTreeGrid.this.toggleFolder(selectedRecord);
//				}
//			}
//		}
//	});

	protected ToolStrip getToolStrip() {
		ToolStrip toolStrip = new ToolStrip();
		// initialise the buttons
		first = new ToolStripButton();
		first.setTitle(FIRST_TOOLBAR_BUTTON_DISPLAY_NAME);
		first.setIcon(DOCUMENT_NEW_TOOLBAR_BUTTON);
		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite obrazac");
				} else{
					Criteria criteria = new Criteria("Service","prikaziPdfFormu");
					criteria.addCriteria("rootUrl",OContext.URL);
					Record record= _listGrid.getSelectedRecord();
					String filePath = record.getAttribute("lokacija");
					filePath =filePath.substring(filePath.lastIndexOf("\\") + 1,filePath.length());
					filePath +="/"+ record.getAttribute("naziv");
					if(record.getAttributeAsObject("verzija")==null || record.getAttribute("verzija").equals("")){
						filePath +=".pdf";
					}else{
						filePath +="_"+ record.getAttribute("verzija") + ".pdf";
					}		
					criteria.addCriteria("filePath",filePath);
					ObrasciTransportModel.callServer(_listGrid.getSelectedRecord(),criteria);				
				}
			}
		});
		toolStrip.addButton(first);
		second = new ToolStripButton();
		second.setTitle(SECOND_TOOLBAR_BUTTON_DISPLAY_NAME);
		second.setIcon(DOCUMENT_EDIT_TOOLBAR_BUTTON);
		second.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite Zahtjev");
				} else{
					ObrasciTransportModel.callServer(_listGrid.getSelectedRecord(), new Criteria("Service","prikaziMojiObrasciFormu"));
				}
			}
		});
		toolStrip.addButton(second);
		toolStrip.addSeparator();
		third = new ToolStripButton();
		third.setIcon(DELETE_TOOLBAR_BUTTON);
		third.setTooltip(DELETE_TOOLBAR_BUTTON_HINT);
		third.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				String text="Da li ste sigurni da želite obrisati dokument?";
				if(_listGrid.getSelectedRecords().length!=1)
					text="Da li ste sigurni da želite obrisati označene dokumente?";
				SC.confirm("Upozorenje!", text,
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if(_listGrid!=null){
										Record recordToDelete = new Record();
										recordToDelete.setAttribute("id_dokumenta","999"); //dummy id
										if(_listGrid.getSelectedRecords().length==1){
											recordToDelete.setAttribute("object0", _listGrid.getSelectedRecord().getAttribute("id_dokumenta"));
//											Record.copyAttributesInto(recordToDelete, _listGrid.getSelectedRecord(),_listGrid.getSelectedRecord().getAttributes());
										}else{
											ListGridRecord[] selectedRecords = _listGrid.getSelectedRecords();
											int count=0;
											for(ListGridRecord record:selectedRecords){
												recordToDelete.setAttribute("object"+count++, record.getAttribute("id_dokumenta"));
											}
										}
										_listGrid.removeData(recordToDelete);
										_listGrid.invalidateCache();
									}
								}
							}
						});
			}
		});
		toolStrip.addButton(third);

		fourth = new ToolStripButton();
		fourth.setIcon(FILTER_TOOLBAR_BUTTON);
		fourth.setTooltip(FILTER_TOOLBAR_BUTTON_HINT);
		fourth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				if(!_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterRow(true,true);
				}else{
					_listGrid.setShowFilterRow(false,false);
				}
			}
		});
		toolStrip.addButton(fourth);
		
		fifth = new ToolStripButton();
		fifth.setIcon(REFRESH_TOOLBAR_BUTTON);
		fifth.setTooltip(REFRESH_TOOLBAR_BUTTON_HINT);
		fifth.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				refreshView();
			}
		});
		toolStrip.addButton(fifth);
		return toolStrip;
	}


	private void refreshView() {
		_listGrid.setShowFilterRow(false,false);
		_listGrid.invalidateCache();
		_listGrid.refresh();
	}


}
