package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevConstants;
import hr.adriacomsoftware.app.client.smartgwt.karticno.gr.models.McardGrZahtjevModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.util.Date;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class McardGrZahtjevZavrsiMWindow extends AS2Window implements McardGrZahtjevConstants {
	private IButton _button_zavrsi;
	private McardGrZahtjevMWindow _mcard_m_window;
	private McardGrZahtjevDodatniMWindow _mcard_m_window_dodatni;
	private Record _record;
	private AS2FormItem osoba_odobravanja;
	
	public McardGrZahtjevZavrsiMWindow(McardGrZahtjevDodatniMWindow mcard_m_window_dodatni, Record record) {
		NUMBER_OF_COLUMNS=2;
		_mcard_m_window_dodatni = mcard_m_window_dodatni;
		_record=record;
		createComponents();
	}
	
	public McardGrZahtjevZavrsiMWindow(McardGrZahtjevMWindow mcard_m_window, Record record) {
		NUMBER_OF_COLUMNS=2;
		_mcard_m_window = mcard_m_window;
		_record=record;
		createComponents();
	}

	public DataSource getModel() {
		return McardGrZahtjevModel.getInstance();
	}
	@Override
	public void createComponents() {
		_dataSource = getModel();
		_buttons_layout = getButtonsLayout();
		_form = getDynamicForm();
		_form.editRecord(_record);
		windowLayout();
	}
	@Override
	public void windowLayout(){
		this.addItem(_form);
		this.addItem(_buttons_layout);
//		this.addCloseClickHandler(new CloseClickHandler() {
//			public void onCloseClick(CloseClickEvent event) {
//				closeWindow(false);
//			}
//		});
		this.setWidth(550);
		this.setHeight(350);
		this.setTitle("Završetak zahtjeva");
		this.show();

	}
	@Override
	public AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(NUMBER_OF_COLUMNS);
		form.setColWidths("20%", "80%");
		form.setWrapItemTitles(true);
		form.setStyleNameGray();
		form.setPadding(10);
		form.setSaveOnEnter(false);
		AS2FormItem broj_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA);
		broj_zahtjeva.getField().setVisible(false);
        AS2FormItem status_odobrenja = new  AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ODOBRENJA,AS2Field.FORM_RADIOGROUP,"Odluka");
        
        //TODO testirati zamjenu
//        status_odobrenja.setValueMap(AS2Field.getComboData("status_odobrenja_zavrsi"));
        status_odobrenja.setDropDownList("status_odobrenja_zavrsi");
       
        
//        status_odobrenja.setDropDownList("status_odobrenja_zavrsi");
        status_odobrenja.getField().addChangedHandler(new ChangedHandler() {
     			@Override
     			public void onChanged(ChangedEvent event) {
     				statusOdobrenjaChanged(event.getItem().getValue().toString());
     			}
     		});
        osoba_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA);
        osoba_odobravanja.setDropDownList("svi_korisnici");
        osoba_odobravanja.setWidth(400);
        AS2FormItem datum_odobravanja = new AS2FormItem(MCARD_GR_ZAHTJEV__DATUM_ODOBRAVANJA);
        datum_odobravanja.setWidth(150);
        datum_odobravanja.getField().setDefaultValue(new Date());
        AS2FormItem komentar =  new AS2FormItem(MCARD_GR_ZAHTJEV__KOMENTAR);
        komentar.getField().setRowSpan(3);
        komentar.getField().setWidth(400);
    	AS2FormItem status_zahtjeva = new AS2FormItem(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA);
		status_zahtjeva.setDefaultValue("Zatvoreno");
		status_zahtjeva.getField().setVisible(false);
        form.setDataSource(_dataSource);
        form.setFields(broj_zahtjeva,status_odobrenja,
        		osoba_odobravanja,datum_odobravanja,komentar,status_zahtjeva);
        form.focusInItem(status_odobrenja);
        form.setRequiredFields(status_odobrenja,osoba_odobravanja,datum_odobravanja);
        return form;
	}

    protected void statusOdobrenjaChanged(String odabir) {
    		if(odabir.equalsIgnoreCase("odustanak_klijenta")){
    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(false);
    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(false);
    		}else{
    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setVisible(true);
    			_form.getField(MCARD_GR_ZAHTJEV__OSOBA_ODOBRAVANJA).setRequired(true);
    		}
    		_form.redraw();
	}

	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(5);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
		_button_zavrsi = new IButton("Završi");
		_button_zavrsi.setIcon(AS2Resources.SAVE_PATH);
		_button_zavrsi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_form.getField(MCARD_GR_ZAHTJEV__STATUS_ZAHTJEVA).setValue(McardGrZahtjevMWindow.STATUS_ZAHTJEVA_ZATVORENO);
				_form.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						if(_mcard_m_window_dodatni==null)
							_mcard_m_window.refreshParentView();
						else{
							_mcard_m_window_dodatni.refreshParentView();
						}
						closeWindow(false);
					}
				});
			}
		});
		_button_izlaz = new IButton("Odustani");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeWindow(false);
			}
		});
		buttons_layout.setMembers(_button_zavrsi, _button_izlaz);
		return buttons_layout;
	}
}
