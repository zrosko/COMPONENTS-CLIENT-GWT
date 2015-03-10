package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevSifrarnikModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class McardGrZahtjevMWindow extends AS2Window implements McardGrZahtjevConstants {
	boolean _novi_zahtjev;
//	public boolean _refresh = false;
//	AS2SimpleCacheManager _cache = AS2SimpleCacheManager.getInstance();
	private IButton _button_prethodno;
	private IButton _button_nastavak;
	private IButton _button_zavrsi;
	private TabSet _tabSet;
	private Record _zahtjev_record;
	private Tab pripremaPodataka;
	private Tab obradaPodataka;
	private Tab kreditnaSposobnost;
	private Tab preporuka;
	private Tab odluka;
	private Tab finalizacija;
	private Label _window_form_title_left;
	private HLayout _window_form_title_layout;
	private Label _window_form_title_right;
	public static final String FAZA_ZAHTJEVA_POCETNA = "priprema";
	public static final String STATUS_ZAHTJEVA_POCETNI = "U tijeku";
	public static final String STATUS_ZAHTJEVA_ZATVORENO = "Zatvoreno";
	public static String _faza_zahtjeva = FAZA_ZAHTJEVA_POCETNA;
	public static String _status_zahtjeva = STATUS_ZAHTJEVA_POCETNI;
	private HLayout _buttons_layout;

	//TODO viditi zašto se javlja pogreška:
	//Nakon izmjene podataka na formi u nekom tabu, spremimo te podatke i želimo ugasiti Mwindow. Onda bi se trebao
	//napraviti refresh listgrid-a na viewu. Međutim ovo javlja grešku zbog grupiranja podataka na listgridu
	public McardGrZahtjevMWindow(AS2View2 parent, Record record) {
		_parent_view = parent;
		_dataSource = _parent_view.getModel();
		_zahtjev_record = record;
		createComponents();
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return McardGrZahtjevSifrarnikModel.getInstance();
	}
	
	@Override
	public void createComponents() {
		if (_zahtjev_record == null){
			_novi_zahtjev=true;
			_zahtjev_record = new Record();
			_faza_zahtjeva = FAZA_ZAHTJEVA_POCETNA;
		}else{
			if(_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).equals(""))
				_zahtjev_record.setAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA, FAZA_ZAHTJEVA_POCETNA);
			_novi_zahtjev=false;
		}
		_window_form_title_layout = getWindowFormTitleLayout();
		_tabSet = getTabSet();
		_buttons_layout = getButtonsLayout();
		 disableTabs(_faza_zahtjeva);
		windowLayout();
	}
	@Override
	public void windowLayout(){
		this.addItem(_window_form_title_layout);
		this.addItem(_tabSet);
		this.addItem(_buttons_layout);
//		this.addCloseClickHandler(new CloseClickHandler() {
//			public void onCloseClick(CloseClickEvent event) {
//				closeWindow(_refresh);
//			}
//		});
		this.show();
	}
	
	private HLayout getWindowFormTitleLayout() {
		HLayout title = new HLayout();
		title.setBorder("1px solid #7598c7");
		title.setHeight(10);
		title.setStyleName("crm-dynamicForm-titlelabel");
		_window_form_title_left = new Label();
		_window_form_title_left.setWidth("50%");
		_window_form_title_left.setContents(getWindowFormTitleLeft());
		_window_form_title_right = new Label();
		_window_form_title_right.setContents(getWindowFormTitleRight());
		_window_form_title_right.setWidth("50%");
		_window_form_title_right.setAlign(Alignment.RIGHT);
		title.setMembers(_window_form_title_left,_window_form_title_right);
		return title;
	}

	private String getWindowFormTitleLeft(){
		String contents ="";
		if(_novi_zahtjev)
			contents = "NOVI ZAHTJEV";
		else{	
			contents = "<b style=\"color:black;font-size:10pt;\">Podaci za zahtjev broj: "
			+ _zahtjev_record.getAttributeAsString(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)
			+ "</b></br>"
			+ _zahtjev_record.getAttributeAsString(MCARD_GR_ZAHTJEV__IME_PREZIME);
		}
		return contents;
	}
	
	private String getWindowFormTitleRight() {
		String contents = "";
		if(!_novi_zahtjev){
				contents = "<b style=\"color:black;font-size:10pt;\">Faza zahtjeva:</b></t></br>"
						+ 
						//AS2Field.getComboData(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).get(			//TODO
						_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA)
						//)
						.toString().toUpperCase();
		}
		return contents;
	}



	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(4);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setWidth100();
		buttons_layout.setAutoHeight();
		buttons_layout.setShowEdges(true);
		// define Buttons
		_button_prethodno = new IButton("Prethodni korak");
		_button_prethodno.setIcon(AS2Resources.RESULT_SET_PREVIOUS_PATH);
		_button_prethodno.setIconOrientation("left");
		_button_prethodno.setDisabled(true);
		_button_prethodno.setAutoFit(true);
		_button_prethodno.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pitanjePrevious(_faza_zahtjeva);
			}
		});
		_button_nastavak = new IButton("Sljedeći korak");
		_button_nastavak.setIcon(AS2Resources.RESULT_SET_NEXT_PATH);
		_button_nastavak.setIconOrientation("right");
		_button_nastavak.setDisabled(true);
		_button_nastavak.setAutoFit(true);
		_button_nastavak.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pitanjeNext(_faza_zahtjeva);
			}
		});
		_button_zavrsi = new IButton("Završi");
		_button_zavrsi.setIcon(AS2Resources.SAVE_PATH);
		_button_zavrsi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!_novi_zahtjev){
					pitanjeEnd(_faza_zahtjeva);
				}
			}
		});
		_button_izlaz = new IButton("Izlaz");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeWindow(_refresh);
			}
		});
		buttons_layout.setMembers(_button_prethodno,_button_nastavak, _button_zavrsi, _button_izlaz);
		return buttons_layout;
	}
	
	protected void pitanjePrevious(final String faza_zahtjeva_previous) {
		SC.ask("Prethodni korak", "Potvrdite povratak u prethodni korak", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value!=null && value){
					String faza_zahtjeva = "";
					if(faza_zahtjeva_previous.equals(obradaPodataka.getID())){
						faza_zahtjeva=McardGrZahtjevTabObrada.FAZA_ZAHTJEVA_PREVIOUS;
					}else if(faza_zahtjeva_previous.equals(kreditnaSposobnost.getID())){
						faza_zahtjeva=McardGrZahtjevTabKreditnaSposobnost.FAZA_ZAHTJEVA_PREVIOUS;
					}else if(faza_zahtjeva_previous.equals(preporuka.getID())){
						faza_zahtjeva=McardGrZahtjevTabPreporuka.FAZA_ZAHTJEVA_PREVIOUS;
					}else if(faza_zahtjeva_previous.equals(odluka.getID())){
						faza_zahtjeva=McardGrZahtjevTabOdluka.FAZA_ZAHTJEVA_PREVIOUS;
					}else if(faza_zahtjeva_previous.equals(finalizacija.getID())){
						faza_zahtjeva=McardGrZahtjevTabFinalizacija.FAZA_ZAHTJEVA_PREVIOUS;
					}
					saveData(faza_zahtjeva);
				}
			}
		});
	}
	
	protected void pitanjeNext(final String faza_zahtjeva_next) {
		SC.ask("Sljedeći korak", "Potvrdite prelazak u sljedeći korak", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value!=null && value){
					if(faza_zahtjeva_next.equals(pripremaPodataka.getID())){
						McardGrZahtjevTabPripremaPodataka pripremaTab = (McardGrZahtjevTabPripremaPodataka) pripremaPodataka.getPane();
						if(pripremaTab.getForm().validate()){
							pripremaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabPripremaPodataka.FAZA_ZAHTJEVA_NEXT);
							pripremaTab.saveData("next");
						}
					}else if(faza_zahtjeva_next.equals(obradaPodataka.getID())){
						McardGrZahtjevTabObrada obradaTab = (McardGrZahtjevTabObrada)obradaPodataka.getPane();
						if(obradaTab.getForm().validate()){
							obradaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabObrada.FAZA_ZAHTJEVA_NEXT);
							obradaTab.saveData("next");
						}
					}else if(faza_zahtjeva_next.equals(kreditnaSposobnost.getID())){
						saveData(McardGrZahtjevTabKreditnaSposobnost.FAZA_ZAHTJEVA_NEXT);
					}else if(faza_zahtjeva_next.equals(preporuka.getID())){
						McardGrZahtjevTabPreporuka preporukaTab = (McardGrZahtjevTabPreporuka)preporuka.getPane();
						if(preporukaTab.getForm().validate()){
							preporukaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabPreporuka.FAZA_ZAHTJEVA_NEXT);
							preporukaTab.saveData("next");
						}
					}else if(faza_zahtjeva_next.equals(odluka.getID())){
						McardGrZahtjevTabOdluka odlukaTab = (McardGrZahtjevTabOdluka)odluka.getPane();
						if(odlukaTab.getForm().validate()){
							odlukaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabOdluka.FAZA_ZAHTJEVA_NEXT);
							odlukaTab.saveData("next");
						}
					}
				}
			}
		});
	}
	
	protected void pitanjeEnd(final String faza_zahtjeva_end) {
		SC.ask("Je ste sigurni da želite zatvoriti zahtjev?", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value!=null && value){
					if(faza_zahtjeva_end.equals(odluka.getID())){
						McardGrZahtjevTabOdluka odlukaTab = (McardGrZahtjevTabOdluka)odluka.getPane();
						if(odlukaTab.getForm().validate()){
							odlukaTab.getForm().getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO);
							odlukaTab.saveData("end");
						}
					}else if(faza_zahtjeva_end.equals(finalizacija.getID())){ 
						McardGrZahtjevTabFinalizacija finalizacijaTab = (McardGrZahtjevTabFinalizacija)finalizacija.getPane();
						if(finalizacijaTab.getForm().validate()){
							finalizacijaTab.getForm().getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO);
							finalizacijaTab.saveData("end");
						}
					}else{
						new McardGrZahtjevZavrsiMWindow(McardGrZahtjevMWindow.this,_zahtjev_record);
					}
				}
			}
		});
		
	}
	
	public void tabSaveCallback(){
		_faza_zahtjeva = _zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		disableTabs(_faza_zahtjeva);
		_tabSet.selectTab(_faza_zahtjeva);
	}
	
	public void endCallback() {
		closeWindow(true);
	}
	
	private void saveData(String faza) {
		AS2DynamicForm tempForm = new AS2DynamicForm();
		tempForm.setDataSource(_parent_view.getModel());
		tempForm.setFields(new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA),new AS2FormItem(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA));
		Record data = new Record();
		data.setAttribute(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA, _zahtjev_record.getAttributeAsObject(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA));
		data.setAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA,  faza);
		tempForm.editRecord(data);
		tempForm.saveData(new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				Record server = dsResponse.getDataAsRecordList().get(0);
				_refresh=true;
				refreshWindow(server);
				tabSaveCallback();
			}
		});
	}
	
	protected TabSet getTabSet() {
		TabSet tabSet = new TabSet();  
		tabSet.setTabBarPosition(Side.LEFT);  
		tabSet.setTabBarAlign(Side.LEFT);  
		tabSet.setTabBarThickness(150);
		tabSet.setWidth100();  
		tabSet.setHeight100();
	    pripremaPodataka = new Tab("Prikupljanje podataka");
	    pripremaPodataka.setID("priprema");
	    obradaPodataka = new Tab("Obrada Podataka");  
	    obradaPodataka.setID("obrada");
	    kreditnaSposobnost = new Tab("Kreditna sposobnost");  
	    kreditnaSposobnost.setID("kreditna_sposobnost");
	    preporuka = new Tab("Preporuka");  
	    preporuka.setID("preporuka");
	    odluka = new Tab("Odluka");  
	    odluka.setID("odluka");
	    finalizacija = new Tab("Finalizacija");  
	    finalizacija.setID("finalizacija");
		tabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(_faza_zahtjeva.equals(event.getTab().getID()) && (!_novi_zahtjev && 
						!_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).equalsIgnoreCase(STATUS_ZAHTJEVA_ZATVORENO))){
					_button_nastavak.setDisabled(false);
					_button_prethodno.setDisabled(false);
					_button_zavrsi.setDisabled(false);
				}else{
					_button_nastavak.setDisabled(true);
					_button_prethodno.setDisabled(true);
					_button_zavrsi.setDisabled(true);
				}
				openWindow(event.getTab().getID(),_zahtjev_record);
			}
		});
	    tabSet.addTab(pripremaPodataka);
	    tabSet.addTab(obradaPodataka);
	    tabSet.addTab(kreditnaSposobnost);
	    tabSet.addTab(preporuka);
	    tabSet.addTab(odluka);
	    tabSet.addTab(finalizacija);
	    if(_zahtjev_record.getAttributeAsObject(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA)!=null)
	    	_faza_zahtjeva = _zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		tabSet.selectTab(_faza_zahtjeva);
        return tabSet; 
	}
	
	private void disableTabs(String faza) {
		if (faza.equals(pripremaPodataka.getID())) {
			_tabSet.disableTab(obradaPodataka.getID());
			_tabSet.disableTab(kreditnaSposobnost.getID());
			_tabSet.disableTab(preporuka.getID());
			_tabSet.disableTab(odluka.getID());
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(pripremaPodataka.getID());
		}else if (faza.equals(obradaPodataka.getID())) {
			_tabSet.disableTab(kreditnaSposobnost.getID());
			_tabSet.disableTab(preporuka.getID());
			_tabSet.disableTab(odluka.getID());
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(obradaPodataka.getID());
		}else if (faza.equals(kreditnaSposobnost.getID())) {
			_tabSet.disableTab(preporuka.getID());
			_tabSet.disableTab(odluka.getID());
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(kreditnaSposobnost.getID());
		} else if (faza.equals(preporuka.getID())) {
			_tabSet.disableTab(odluka.getID());
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(preporuka.getID());
		}else if (faza.equals(odluka.getID())) {
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(odluka.getID());
		}else if (faza.equals(finalizacija.getID())) {
			_tabSet.enableTab(finalizacija.getID());
		}
	}

	private void openWindow(String faza, Record record) {
		if (faza.equals(pripremaPodataka.getID())) {
			pripremaPodataka.setPane(new McardGrZahtjevTabPripremaPodataka(McardGrZahtjevMWindow.this,record));
//			_button_prethodno.setDisabled(true);
//			if (_novi_zahtjev) {
//				_button_zavrsi.setDisabled(true);
//				_button_nastavak.setDisabled(false);
//			}
		} else if (faza.equals(obradaPodataka.getID())) {
			obradaPodataka.setPane(new McardGrZahtjevTabObrada(McardGrZahtjevMWindow.this, record));
		} else if (faza.equals(kreditnaSposobnost.getID())) {
			kreditnaSposobnost.setPane(new McardGrZahtjevTabKreditnaSposobnost(McardGrZahtjevMWindow.this,record));
		} else if (faza.equals(preporuka.getID())) {
			preporuka.setPane(new McardGrZahtjevTabPreporuka(McardGrZahtjevMWindow.this,record));
		} else if (faza.equals(odluka.getID())) {
			odluka.setPane(new McardGrZahtjevTabOdluka(McardGrZahtjevMWindow.this,record));
		} else if (faza.equals(finalizacija.getID())) {
			finalizacija.setPane(new McardGrZahtjevTabFinalizacija(McardGrZahtjevMWindow.this,record));
		}
	}
	
	//main record for tabs
	public Record getZahtjevRecord() {
		return _zahtjev_record;
	}
	public void setZahtjevRecord(Record _zahtjev_record) {
		this._zahtjev_record = _zahtjev_record;
	}
	public IButton getButtonPrethodno() {
		return _button_prethodno;
	}
	public IButton getButtonNastavak() {
		return _button_nastavak;
	}
	public IButton getButtonZavrsi() {
		return _button_zavrsi;
	}
	public String getSelectedTabID(){
		return _tabSet.getSelectedTab().getID();
	}
	public void refreshParentView(){
		closeWindow(true);
	}
	public void refreshWindowTitle(){
		_window_form_title_left.setContents(getWindowFormTitleLeft());
		_window_form_title_right.setContents(getWindowFormTitleRight());
	}
	public void refreshWindow(Record record){
		setZahtjevRecord(record);
		refreshWindowTitle();
	}
}
