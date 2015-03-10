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

public class McardGrZahtjevDodatniMWindow extends AS2Window implements McardGrZahtjevConstants {
	boolean _novi_zahtjev;
//	public boolean _refresh = false;
//	AS2SimpleCacheManager _cache = AS2SimpleCacheManager.getInstance();
	private IButton _button_prethodno;
	private IButton _button_nastavak;
	private IButton _button_zavrsi;
	private TabSet _tabSet;
	private Record _zahtjev_record;
	private Tab pripremaPodataka;
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

	
	public McardGrZahtjevDodatniMWindow(AS2View2 parent, Record record) {
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
		}
		else{
			if(_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).equals(""))
				_zahtjev_record.setAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA, FAZA_ZAHTJEVA_POCETNA);
			_novi_zahtjev=false;
		}
		_window_form_title_layout = getWindowFormTitleLayout();
		_tabSet = getTabSet();
		 disableTabs(_faza_zahtjeva);
		windowLayout();
	}
	@Override
	public void windowLayout(){
		this.addItem(_window_form_title_layout);
		this.addItem(_tabSet);
		this.addItem(getButtonsLayout());
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
			contents = "<b style=\"color:black;font-size:10pt;\">Podaci za zahtjev dodatnog korisnika broj: "
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
						//AS2Field.getComboData(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).get( //TODO
						_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA)
						//)
						.toString().toUpperCase();
		}
		return contents;
	}



	protected HLayout getButtonsLayout() {
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
		_buttons_layout.setMembers(_button_prethodno,_button_nastavak, _button_zavrsi, _button_izlaz);
		return _buttons_layout;
	}
	
	protected void pitanjePrevious(final String faza_zahtjeva_previous) {
		SC.ask("Prethodni korak", "Potvrdite povratak u prethodni korak", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value!=null && value){
					String faza_zahtjeva = "";
					if(faza_zahtjeva_previous.equals(odluka.getID())){
						faza_zahtjeva=McardGrZahtjevTabOdlukaDodatni.FAZA_ZAHTJEVA_PREVIOUS;
					}else if(faza_zahtjeva_previous.equals(finalizacija.getID())){
						faza_zahtjeva=McardGrZahtjevTabFinalizacijaDodatni.FAZA_ZAHTJEVA_PREVIOUS;
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
						McardGrZahtjevTabPripremaPodatakaDodatni pripremaTab = (McardGrZahtjevTabPripremaPodatakaDodatni) pripremaPodataka.getPane();
						if(pripremaTab.getForm().validate()){
							pripremaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabPripremaPodatakaDodatni.FAZA_ZAHTJEVA_NEXT);
							pripremaTab.saveData("next");
						}
					}else if(faza_zahtjeva_next.equals(odluka.getID())){
						McardGrZahtjevTabOdlukaDodatni odlukaTab = (McardGrZahtjevTabOdlukaDodatni)odluka.getPane();
						if(odlukaTab.getForm().validate()){
							odlukaTab.getForm().getField(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).setValue(McardGrZahtjevTabOdlukaDodatni.FAZA_ZAHTJEVA_NEXT);
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
						McardGrZahtjevTabOdlukaDodatni odlukaTab = (McardGrZahtjevTabOdlukaDodatni)odluka.getPane();
						if(odlukaTab.getForm().validate()){
							odlukaTab.getForm().getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevDodatniMWindow.STATUS_ZAHTJEVA_ZATVORENO);
							odlukaTab.saveData("end");
						}
					}else if(faza_zahtjeva_end.equals(finalizacija.getID())){ 
						McardGrZahtjevTabFinalizacijaDodatni finalizacijaTab = (McardGrZahtjevTabFinalizacijaDodatni)finalizacija.getPane();
						if(finalizacijaTab.getForm().validate()){
							finalizacijaTab.getForm().getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevDodatniMWindow.STATUS_ZAHTJEVA_ZATVORENO);
							finalizacijaTab.saveData("end");
						}
					}else{
						new McardGrZahtjevZavrsiMWindow(McardGrZahtjevDodatniMWindow.this,_zahtjev_record);
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
	    odluka = new Tab("Odluka");  
	    odluka.setID("odluka");
	    finalizacija = new Tab("Finalizacija");  
	    finalizacija.setID("finalizacija");
		tabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(McardGrZahtjevDodatniMWindow._faza_zahtjeva.equals(event.getTab().getID()) && (!_novi_zahtjev && 
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
	    tabSet.addTab(odluka);
	    tabSet.addTab(finalizacija);
	    if(_zahtjev_record.getAttributeAsObject(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA)!=null)
	    	_faza_zahtjeva = _zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA);
		tabSet.selectTab(_faza_zahtjeva);
        return tabSet; 
	}
	
	private void disableTabs(String faza) {
		if (faza.equals(pripremaPodataka.getID())) {
			_tabSet.disableTab(odluka.getID());
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(pripremaPodataka.getID());
		}else if (faza.equals(odluka.getID())) {
			_tabSet.disableTab(finalizacija.getID());
			_tabSet.enableTab(odluka.getID());
		}else if (faza.equals(finalizacija.getID())) {
			_tabSet.enableTab(finalizacija.getID());
		}
	}

	private void openWindow(String faza, Record record) {
		if (faza.equals(pripremaPodataka.getID())) {
			pripremaPodataka.setPane(new McardGrZahtjevTabPripremaPodatakaDodatni(McardGrZahtjevDodatniMWindow.this,record));
//			_button_prethodno.setDisabled(true);
//			if (_novi_zahtjev) {
//				_button_zavrsi.setDisabled(true);
//				_button_nastavak.setDisabled(false);
//			}
		} else if (faza.equals(odluka.getID())) {
			odluka.setPane(new McardGrZahtjevTabOdlukaDodatni(McardGrZahtjevDodatniMWindow.this,record));
		} else if (faza.equals(finalizacija.getID())) {
			finalizacija.setPane(new McardGrZahtjevTabFinalizacijaDodatni(McardGrZahtjevDodatniMWindow.this,record));
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
