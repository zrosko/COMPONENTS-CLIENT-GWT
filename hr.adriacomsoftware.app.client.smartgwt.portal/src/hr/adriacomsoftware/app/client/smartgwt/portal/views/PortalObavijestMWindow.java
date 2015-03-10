package hr.adriacomsoftware.app.client.smartgwt.portal.views;

import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalObavijestiConstants;
import hr.adriacomsoftware.app.client.smartgwt.portal.models.PortalObavijestiModel;
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class PortalObavijestMWindow extends AS2Window implements PortalObavijestiConstants {
	
	private Record _record;
	private boolean _nova_obavijest;
	private RichTextEditor _richTextEditor;
	
	public PortalObavijestMWindow(PortalView parent, Record record) {
		NUMBER_OF_COLUMNS=2;
		_record=record;
		_parent_view=parent;
		createComponents();
	}
	
	@Override
	public DataSource getModel() {
		return PortalObavijestiModel.getInstance();
	}
	
	@Override
	public void createComponents() {
		_dataSource = getModel();
		_buttons_layout = getButtonsLayout();
		_form = getDynamicForm();
		if (_record == null){
			_record = new Record();
			_nova_obavijest=true;
			_form.editNewRecord();
		}else{
			_nova_obavijest=false;
			_form.editRecord(_record);
		}
		_richTextEditor = getRichTextEditor();
		windowLayout();
	}
	
	private RichTextEditor getRichTextEditor() {
		RichTextEditor richTextEditor = new RichTextEditor();  
		richTextEditor.setHeight100();  
		richTextEditor.setOverflow(Overflow.HIDDEN);  
		richTextEditor.setCanDragResize(true);  
		richTextEditor.setShowEdges(false);  
		
		LinkedHashMap<String, String> font_size = new LinkedHashMap<>();
		font_size.put("1", "8pt");
		font_size.put("2", "10pt");
		font_size.put("3", "12pt");
		richTextEditor.setFontSizes(font_size);
		// Standard control group options include  
		// "fontControls", "formatControls", "styleControls" and "colorControls"  
		//richTextEditor.setControlGroups(new String[]{"fontControls", "styleControls"});  
		if(!_nova_obavijest)
			richTextEditor.setValue(_record.getAttribute(PORTAL__OBAVIJEST_HTML));
		return richTextEditor;
	}

	@Override
	public void windowLayout(){
        this.addItem(_richTextEditor);
		this.addItem(_form);
		this.addItem(_buttons_layout);
//		this.addCloseClickHandler(new CloseClickHandler() {
//			public void onCloseClick(CloseClickEvent event) {
//				closeWindow(false);
//			}
//		});
		this.setWidth(550);
		this.setHeight(350);
		this.setTitle("Obavijest");
		this.show();

	}
	
	@Override
	public AS2DynamicForm getDynamicForm() {
		AS2DynamicForm form = new AS2DynamicForm(NUMBER_OF_COLUMNS);
		form.setColWidths("20%", "80%");
		form.setWrapItemTitles(true);
//		form.setStyleNameGray();
		form.setPadding(10);
		form.setSaveOnEnter(false);
		form.setVisible(false);
		
		AS2FormItem id_obavijesti = new AS2FormItem(PORTAL__ID_OBAVIJESTI);
		id_obavijesti.getField().setVisible(false);
        AS2FormItem obavijest_html = new  AS2FormItem(PORTAL__OBAVIJEST_HTML);
        obavijest_html.getField().setVisible(false);
        
        form.setDataSource(_dataSource);
        form.setFields(id_obavijesti,obavijest_html);
        form.focusInItem(obavijest_html);
        form.setRequiredFields(obavijest_html);
        return form;
	}
	
	@Override
	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(5);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setBorder("1px solid #7598c7");
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setAutoHeight();
		// define Buttons
		_button_spremi = new IButton("Prihvati");
		_button_spremi.setIcon(AS2Resources.SAVE_PATH);
		_button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_richTextEditor.getFontNames();
				_richTextEditor.getFontSizes();
				_form.getField(PORTAL__OBAVIJEST_HTML).setValue(_richTextEditor.getValue());
				_form.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						_refresh=true;
						closeWindow(_refresh);
						
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
		buttons_layout.setMembers(_button_spremi, _button_izlaz);
		return buttons_layout;
	}
}
