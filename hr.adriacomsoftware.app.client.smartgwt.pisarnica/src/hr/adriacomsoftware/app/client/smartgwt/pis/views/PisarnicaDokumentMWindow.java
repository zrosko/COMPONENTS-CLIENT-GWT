package hr.adriacomsoftware.app.client.smartgwt.pis.views;

import hr.adriacomsoftware.app.client.smartgwt.pis.models.PisarnicaConstants;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziPoOsobaAdresaJrrModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.views.TraziWindow;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadDialog;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

/**
 * @author astrikoman
 *
 */
public class PisarnicaDokumentMWindow extends AS2Window implements PisarnicaConstants {	
	private AS2FormItem ulaz_izlaz;
	
	public PisarnicaDokumentMWindow(AS2View2 parent, Record record) {
		super(parent,record);
	}
	@Override
	public DataSource getModel() {
		return _parent_view.getModel();
	}
	@Override
	public void customWindowPreferences(){
		this.setWidth(600);
		this.setHeight(400);
	}
	
	@Override
	public DataSource getSifrarnikModel() {
		return _parent_view.getSifrarnikModel();
	}
	@Override
	protected Criteria initCriteria(){
		return new Criteria(PISARNICA_ID_DOKUMENTA,_record.getAttribute(PISARNICA_ID_DOKUMENTA));
	}
	@Override
	protected String getInitRemoteMethod(){
		return "findReference";
	}
	@Override
	public AS2DynamicForm getDynamicForm() {		
		AS2DynamicForm form = new AS2DynamicForm(2);
		form.setColWidths("30%", "80%");
		form.setAutoFetchData(false);
		form.setSaveOnEnter(false);
		form.setWrapItemTitles(false);
		form.setWidth100();
		form.setPadding(5);
		form.setStyleNameGray();
		form.setCanSubmit(true);
		AS2FormItem id_dokumenta = new AS2FormItem(PISARNICA_ID_DOKUMENTA);
		id_dokumenta.getField().setVisible(false);
		AS2FormItem id_vrste = new AS2FormItem(PISARNICA_ID_VRSTE, AS2Field.COMBO);
		id_vrste.getField().setWidth(150);
		id_vrste.setDefaultToFirstOption(true);
		ulaz_izlaz = new  AS2FormItem(PISARNICA_ULAZ_IZLAZ,AS2Field.FORM_RADIOGROUP, "Ulaz/izlaz");
//		ulaz_izlaz.setValueMap(AS2Field.getCacheData(PISARNICA_ULAZ_IZLAZ));
		ulaz_izlaz.setDefaultValue("U");
		ulaz_izlaz.setVertical(false);
		AS2FormItem naziv_dokumenta = new AS2FormItem(PISARNICA_NAZIV_DOKUMENTA,AS2Field.TEXT,"Naziv");
		naziv_dokumenta.getField().setWidth(300);
		naziv_dokumenta.getField().setHint("Broj računa, ponude...");
		AS2FormItem oib = new AS2FormItem(PISARNICA_OIB, AS2Field.COMBO);
		oib.getField().setWidth(300);
		oib.getField().setIcons(_lookupOib);
		oib.setAddUnknownValues(true);
		AS2FormItem iznos = new AS2FormItem(PISARNICA_IZNOS);
		iznos.getField().setWidth(150);
		AS2FormItem datum = new AS2FormItem(PISARNICA_DATUM_DOKUMENTA);
		datum.getField().setWidth(150);
		datum.getField().setDefaultValue(new Date());
		AS2FormItem organizacijska_jedinica = new AS2FormItem(PISARNICA_ORGANIZACIJSKA_JEDINICA, AS2Field.COMBO);
		organizacijska_jedinica.getField().setWidth(300);
		AS2FormItem status_dokumenta = new AS2FormItem(PISARNICA_STATUS_DOKUMENTA, AS2Field.COMBO);
		status_dokumenta.getField().setWidth(200);
		status_dokumenta.setDefaultToFirstOption(true);
//		AS2FormItem privitak = new AS2FormItem(PISARNICA_PRIVITAK, AS2Field.IMAGE_FILE, "Pirivita");
		AS2FormItem naziv_privitka = new AS2FormItem(PISARNICA_NAZIV_PRIVITKA);
		naziv_privitka.setWidth(363);
		naziv_privitka.setIcons(_privitakDodaj,_privitakOtvori,_privitakBrisi);
		naziv_privitka.getField().setCanEdit(false);
		AS2FormItem ispravno = new AS2FormItem(PISARNICA_ISPRAVNO);
		ispravno.getField().setVisible(false);
		form.setDataSource(_dataSource);
		form.setFields(/*privitak,*/id_dokumenta,id_vrste,ulaz_izlaz,oib,datum,naziv_dokumenta,iznos,organizacijska_jedinica,
					   naziv_privitka,status_dokumenta,ispravno);
		form.setRequiredFields(id_vrste,oib,iznos,datum,status_dokumenta,ulaz_izlaz,organizacijska_jedinica,naziv_dokumenta);
		form.focusInItem(id_vrste);
		form.setDropDownModel(getSifrarnikModel(), id_vrste,status_dokumenta,ulaz_izlaz,oib,organizacijska_jedinica);
		form.setDefaultButton(_button_spremi);
		
		//handlers
		id_vrste.getField().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String value = event.getItem().getValue().toString();
				if(value.equalsIgnoreCase("5"))//IRA
					ulaz_izlaz.getField().setValue("I");
				else
					ulaz_izlaz.getField().setValue("U");
			}
		});
		
		return form;
	}

	@Override
	public void fileUploadCompleteCallbackCustom(AS2FileUploadDialog fileUploadDialog, String fileName, String pk) {
		_form.getField(PISARNICA_NAZIV_PRIVITKA).setValue(fileName);
		_form.getField(PISARNICA_ID_DOKUMENTA).setValue(pk);
		_record.setAttribute(PISARNICA_NAZIV_PRIVITKA, fileName);
		_record.setAttribute(PISARNICA_ID_DOKUMENTA, pk);
		_refresh=true;
	}
	@Override
	protected HashMap<String, Object> getUploadParameters() {
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("servlet_service", "upload");
		if(_form.getField(PISARNICA_ID_DOKUMENTA).getValue()==null){
//			parameters.put(URA_NAZIV_VRSTE,_form.getValue(URA_NAZIV_VRSTE).toString());
			parameters.put(AS2RestJSONDataSource.REMOTE_METHOD,  "dodajPrivitak");
			parameters.put("ispravno","0");
		}else{
			parameters.put(PISARNICA_ID_DOKUMENTA,_form.getField(PISARNICA_ID_DOKUMENTA).getValue().toString());
			parameters.put(AS2RestJSONDataSource.REMOTE_METHOD,  "azurirajDokument");
		}
		parameters.put(AS2RestJSONDataSource.REMOTE_OBJECT,((AS2RestJSONDataSource)_dataSource).getRemoteObject());
		parameters.put(AS2RestJSONDataSource.TRANSFORM_TO, ((AS2RestJSONDataSource)_dataSource).getTransformTo());
		parameters.put("korisnik",AS2ClientContext.getSessionValue(AS2ClientContext.AS2_USERNAME));
		return parameters;
	}
	@Override
	public String getWindowFormTitleNew() {
		return "<b style=\"color:black;font-size:10pt;\">NOVI DOKUMENT</b>";
	}
	@Override
	public String getWindowFormTitleUpdate() {
		return  "<b style=\"color:black;font-size:10pt;\">Podaci za dokument broj: "
				+ _record.getAttribute(PISARNICA_ID_DOKUMENTA)
				+ "</b></br>Vrsta dokumenta: "
				+ _record.getAttributeAsString(PISARNICA_NAZIV_VRSTE);
	}
	@Override
	protected void actionDownloadFile(FormItemIconClickEvent event){
		if(_record.getAttributeAsObject(PISARNICA_IMA_PRIVITAK)!=null
				&& !_record.getAttribute(PISARNICA_IMA_PRIVITAK).equalsIgnoreCase("NE")){
			_record.getAttribute(PISARNICA_ID_DOKUMENTA);
			((PisarnicaView)_parent_view).downloadFile(_record);
		}
	}
	@Override
	protected void actionDeleteFile(FormItemIconClickEvent event){
		if(_record.getAttributeAsObject(PISARNICA_IMA_PRIVITAK)!=null
				&& !_record.getAttribute(PISARNICA_IMA_PRIVITAK).equalsIgnoreCase("NE")){
			_form.getField(PISARNICA_NAZIV_PRIVITKA).setValue("");
			_form.saveData();
		}
	}
	@Override
	protected void actionLookupOib(FormItemIconClickEvent event){
		LinkedHashMap<String,String> remap = new LinkedHashMap<String,String>();
		remap.put(PISARNICA_OIB, "oib");
		String[] polja = {"oib","naziv"};
		Criteria criteria = new Criteria();
//		criteria.addCriteria("lookup_type","lookupPo");
		if(event.getItem().getValue()!=null){
			criteria.addCriteria(remap.get(event.getItem().getName()), event.getItem().getValue().toString());
			criteria.addCriteria("value",event.getItem().getValue().toString());
		}
		new TraziWindow(TraziPoOsobaAdresaJrrModel.getInstance(),PisarnicaDokumentMWindow.this,criteria,polja);
		
	}
	/*********** Povratak sa Trazi window ************/
	@Override
	public void getValuesFromTrazi(Record selectedRecord) {
		setFieldValue(PISARNICA_OIB,selectedRecord.getAttributeAsObject("oib"));
	}
}
