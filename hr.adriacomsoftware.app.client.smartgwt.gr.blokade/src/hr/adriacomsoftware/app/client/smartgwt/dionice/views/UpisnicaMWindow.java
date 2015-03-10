package hr.adriacomsoftware.app.client.smartgwt.dionice.views;

import hr.adriacomsoftware.app.client.smartgwt.dionice.models.UpisnicaConstants;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

/**
 * @author astrikoman
 *
 */
public class UpisnicaMWindow extends AS2Window implements UpisnicaConstants{
	
	private boolean dupliciraj_upisnicu=false;
	private AS2FormItem broj_dionica_prije;
	private AS2FormItem pravo_upisa;
	
	public UpisnicaMWindow(AS2View2 parent, Record record) {
		this(parent,record,false);
	}
	
	public UpisnicaMWindow(AS2View2 parent, Record record, boolean dupliciraj) {
		super(parent,record);
		dupliciraj_upisnicu=dupliciraj;
	}
	@Override
	public DataSource getSifrarnikModel(){
		return _parent_view.getSifrarnikModel();
	}
	@Override
	public DataSource getModel() {
		return _parent_view.getModel();
	}
	@Override
	protected Criteria initCriteria() {
		return new Criteria(DION__ID_UPISNICA,_record.getAttribute(DION__ID_UPISNICA));
	}
	@Override
	protected String getInitRemoteMethod(){
		return "citajUpisnicu";
	}
	@Override
	protected void initCallback(DSResponse dsResponse, Object data, DSRequest dsRequest) {
		_record = dsResponse.getDataAsRecordList().get(0);
		boolean visable = true;
		if(_record.getAttribute(DION__KRUG).equals("2")){
			visable=false;
		}
		drugiKrug(visable);
		if(dupliciraj_upisnicu)
			_form.editNewRecord(_record);
		else
			_form.editRecord(_record);
	}
	@Override
	public void customWindowPreferences(){
		this.setWidth(600);
		this.setHeight(500);
	}
	@Override
	public AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(2);
		form.setColWidths("30%", "80%");
		form.setSaveOnEnter(false);
		form.setWrapItemTitles(false);
		form.setWidth100();
		form.setPadding(5);
		form.setStyleNameGray();
		form.setCanSubmit(true);
		AS2FormItem id_upisnica = new AS2FormItem(DION__ID_UPISNICA);
		id_upisnica.getField().setVisible(false);
		AS2FormItem id_ponude = new AS2FormItem(DION__ID_PONUDE, AS2Field.FORM_SELECT);
		id_ponude.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				boolean hide = true;
				if(event.getValue().toString().equals("2")){
					hide = false;
				}
				drugiKrug(hide);
			}
		});
		id_ponude.getField().setWidth(100);
		id_ponude.setDefaultToFirstOption(true);		
		AS2FormItem upisnik = new AS2FormItem(DION__UPISNIK);
		upisnik.getField().setWidth(300);
		AS2FormItem vrsta_upisnika = new AS2FormItem(DION__VRSTA_UPISNIKA, AS2Field.FORM_SELECT);
		vrsta_upisnika.getField().setWidth(100);
		AS2FormItem mbg_mb = new AS2FormItem(DION__MBG_MB);
		AS2FormItem oib = new AS2FormItem(DION__OIB);
		oib.setIcons(_lookupOib);
		AS2FormItem adresa = new AS2FormItem(DION__ADRESA);
		adresa.getField().setWidth(300);
		AS2FormItem kontakt_osoba = new AS2FormItem(DION__KONTAKT_OSOBA);
		kontakt_osoba.getField().setWidth(300);
		AS2FormItem kontakt_adresa = new AS2FormItem(DION__KONTAKT_ADRESA);
		AS2FormItem mjesto = new AS2FormItem(DION__MJESTO);
		AS2FormItem postanski_broj = new AS2FormItem(DION__POSTANSKI_BROJ);
		AS2FormItem drzava = new AS2FormItem(DION__DRZAVA);
		AS2FormItem telefon_fax = new AS2FormItem(DION__TELEFON_FAX);
		telefon_fax.getField().setWidth(100);
		AS2FormItem mobitel = new AS2FormItem(DION__MOBITEL);
		mobitel.getField().setWidth(100);
		AS2FormItem email = new AS2FormItem(DION__EMAIL);
		email.getField().setWidth(300);
		AS2FormItem iban_upisnika = new AS2FormItem(DION__IBAN_UPISNIKA);
		iban_upisnika.setDefaultValue("HR");
		AS2FormItem banka_skrbnik_upisnika = new AS2FormItem(DION__BANKA_SKRBNIK_UPISNIKA);
		banka_skrbnik_upisnika.getField().setWidth(300);
		AS2FormItem racun_vlasnika = new AS2FormItem(DION__RACUN_VLASNIKA);
		racun_vlasnika.getField().setWidth(100);
		broj_dionica_prije = new AS2FormItem(DION__BROJ_DIONICA_PRIJE);
		broj_dionica_prije.getField().setDisabled(true);
		broj_dionica_prije.getField().setWidth(100);
		pravo_upisa = new AS2FormItem(DION__PRAVO_UPISA);
		pravo_upisa.getField().setWidth(100);
		final AS2FormItem broj_novih_dionica = new AS2FormItem(DION__BROJ_NOVIH_DIONICA);
		broj_novih_dionica.getField().setWidth(100);
		final AS2FormItem nominalna_vrijednost = new AS2FormItem(DION__NOMINALNA_VRIJEDNOST);
		nominalna_vrijednost.getField().setDisabled(true);
		nominalna_vrijednost.getField().setWidth(100);
		if(_new_record){
			nominalna_vrijednost.setDefaultValue("1000");
		}
		final AS2FormItem iznos_uplate = new AS2FormItem(DION__IZNOS_UPLATE);
		iznos_uplate.getField().setWidth(200);
		AS2FormItem datum_upisnice = new AS2FormItem(DION__DATUM_UPISNICE);
		datum_upisnice.getField().setWidth(100);
		AS2FormItem datum_zaprimanja = new AS2FormItem(DION__DATUM_ZAPRIMANJA);
		datum_zaprimanja.getField().setWidth(100);
		AS2FormItem vrijeme_uplate = new AS2FormItem(DION__VRIJEME_UPLATE);
		vrijeme_uplate.getField().setWidth(100);
		AS2FormItem broj_zaprimanja = new AS2FormItem(DION__BROJ_ZAPRIMANJA);
		broj_zaprimanja.getField().setWidth(100);
		AS2FormItem napomena = new AS2FormItem(DION__NAPOMENA);
		AS2FormItem ispravno = new AS2FormItem(DION__ISPRAVNO);
		ispravno.getField().setVisible(false);
		form.setDataSource(_dataSource);
		form.setFields(id_upisnica,id_ponude,vrsta_upisnika,upisnik,mbg_mb,oib,adresa,mjesto,postanski_broj,drzava,kontakt_osoba,
				kontakt_adresa,telefon_fax,mobitel,email,iban_upisnika,banka_skrbnik_upisnika,racun_vlasnika,
				broj_dionica_prije,pravo_upisa,broj_novih_dionica,nominalna_vrijednost,iznos_uplate,datum_upisnice,datum_zaprimanja,
				vrijeme_uplate,broj_zaprimanja,napomena,ispravno);
		form.setRequiredFields(upisnik,vrsta_upisnika,oib,broj_novih_dionica);
		form.setDropDownModel(getSifrarnikModel(), id_ponude,vrsta_upisnika);
		form.focusInItem(id_ponude);
		form.setDefaultButton(_button_spremi);		
		//handler
		broj_novih_dionica.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				float vrijednost = Float.parseFloat(nominalna_vrijednost.getField().getValue().toString());
				float iznos = Float.parseFloat(broj_novih_dionica.getField().getValue().toString())*vrijednost;
				iznos_uplate.getField().setValue(iznos);
			}
		});
		
		return form;
	}
	@Override
	protected void actionLookupOib(FormItemIconClickEvent event) {
		if(event.getItem().getValue()!=null){
			Criteria criteria = new Criteria(DION__OIB, event.getItem().getValue().toString());
			_form.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if(dsResponse.getDataAsRecordList()!=null && dsResponse.getDataAsRecordList().get(0)!=null)
						_form.editNewRecord(dsResponse.getDataAsRecordList().get(0));
				}
			},new DSRequest(DSOperationType.CUSTOM,"citajPodatkeDionicara") );
		}
	}
	protected void drugiKrug(boolean visable) {
		if(visable){
			broj_dionica_prije.getField().show();
			pravo_upisa.getField().show();
		}else{
			broj_dionica_prije.getField().hide();
			pravo_upisa.getField().hide();
		}
	}

	
	@Override
	public String getWindowFormTitleNew() {
		return "<b style=\"color:black;font-size:10pt;\">NOVA UPISNICA</b>";
	}
	@Override
	public String getWindowFormTitleUpdate() {
		return  "<b style=\"color:black;font-size:10pt;\">Identifikator upisnice: "
				+ _record.getAttribute(DION__ID_UPISNICA)
				+ "</b></br> Upisnik: "
				+ _record.getAttribute(DION__UPISNIK);
	}
}
