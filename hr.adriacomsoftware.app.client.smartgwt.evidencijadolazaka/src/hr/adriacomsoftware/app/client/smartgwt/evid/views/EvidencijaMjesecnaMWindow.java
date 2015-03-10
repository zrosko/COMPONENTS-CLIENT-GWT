package hr.adriacomsoftware.app.client.smartgwt.evid.views;

import hr.adriacomsoftware.app.client.smartgwt.evid.models.EvidencijaDolazakaConstants;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * @author msekso, astrikoman
 *
 */
public class EvidencijaMjesecnaMWindow extends AS2Window implements EvidencijaDolazakaConstants{
	
//	private static int WINDOW_WIDTH = 600;
	private HLayout _buttons_layout;
//	private IButton _button_novo;
	private Record _record;
//	private boolean _novi_racun;
	private EvidencijaDolazakaView _view;
	
	public EvidencijaMjesecnaMWindow(AS2View2 parent, Record record) {
		_parent_view = parent;
		_record = record;
		_record.setAttribute("window", true);
		_dataSource = _parent_view.getModel();
		
		createComponents();
	}
	
	public void createComponents() {
		_view = new EvidencijaDolazakaView(_parent_view.getID(),_record);//TODO
		_buttons_layout = getButtonsLayout();
//		if (_racun_record == null){
//			_novi_racun=true;
//			_racun_record=new Record();
//			_form = getDynamicForm();
//		}else{
//			_novi_racun=false;
//			_form = getDynamicForm();
//			_form.fetchData(new Criteria(PISARNICA_ID_DOKUMENTA,_racun_record.getAttribute(PISARNICA_ID_DOKUMENTA)),new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//					_racun_record = dsResponse.getDataAsRecordList().get(0);
//					_form.editRecord(_racun_record);
//				}
//			},new DSRequest(DSOperationType.CUSTOM,"procitajDokument"));
//		}
//		_window_form_title.setContents(getWindowFormTitle());
		windowLayout();
	}
	
	@Override
	public void windowLayout() {
//		this.setWidth(WINDOW_WIDTH);
//		this.setHeight(WINDOW_WIDTH-200);
//		this.addItem(_window_form_title);
		this.addItem(_view);
		this.addItem(_buttons_layout);
		this.show();
	}

	public AS2DynamicForm getDynamicForm() {
		return null;
	}
	
	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(2);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setWidth100();
		buttons_layout.setAutoHeight();
		buttons_layout.setShowEdges(true);
		// define Buttons
		_button_izlaz = new IButton("Izlaz");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeWindow(_refresh);
			}
		});
		buttons_layout.setMembers(_button_izlaz);
		return buttons_layout;
	}
	
	public String getWindowFormTitle() {
		String contents ="";
//		if(_novi_racun)
//			contents = "<b style=\"color:black;font-size:10pt;\">NOVI DOKUMENT</b>";
//		else{	
//			contents = "<b style=\"color:black;font-size:10pt;\">Podaci za dokument broj: "
//					+ _racun_record.getAttribute(PISARNICA_ID_DOKUMENTA)
//					+ "</b></br>Vrsta dokumenta: "
//					+ _racun_record.getAttributeAsString(PISARNICA_NAZIV_VRSTE);
//		}
		return contents;
	}
}

