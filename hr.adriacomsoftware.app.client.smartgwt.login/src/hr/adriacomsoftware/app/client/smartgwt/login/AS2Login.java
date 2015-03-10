package hr.adriacomsoftware.app.client.smartgwt.login;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.rpc.LoginRequiredCallback;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.fields.BlurbItem;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AS2Login extends VLayout {
	
	private static String CREDENTIALS_URL = "/j_security_check_as2";
	private LoginWindow loginWindow;
	private LoginView loginView;
	private LoginForm _loginForm;
	private AS2GwtDesktop _desktop;
	private boolean _use_window=false;
	
	public AS2Login(String url) {
		this(url,false,null);
	}
	
	public AS2Login(boolean use_window, AS2GwtDesktop desktop) {
		this(CREDENTIALS_URL,use_window,desktop);
	}
	
	public AS2Login(String url,boolean use_window, AS2GwtDesktop desktop) {
		this._desktop = desktop;
		this._use_window = use_window;
		CREDENTIALS_URL = url;
		RPCManager.setLoginRequiredCallback(new LoginRequiredCallback() {
			@Override
			public void loginRequired(int i, RPCRequest rpcRequest,
					RPCResponse rpcResponse) {
				if(_use_window){
					if (loginWindow == null)
						loginWindow = new LoginWindow();
					if (!(loginWindow.isVisible() && loginWindow.isDrawn())) {
						_loginForm.clearValues();
						_loginForm.focusInItem("username");
					}
					loginWindow.show();
					loginWindow.bringToFront();
				}else{
					if (loginView == null)
						loginView = new LoginView();
					if (!(loginView.isVisible() && loginView.isDrawn())) {
						_loginForm.clearValues();
						_loginForm.focusInItem("username");
					}
					loginWindow.show();
				}
			}
		});
		if(_use_window){
			LoginWindow window = new LoginWindow();
			this._loginForm = ((LoginWindow)window).getLoginForm();
			addMember(new LoginWindow());
		}else{
			this.setLayoutAlign(VerticalAlignment.CENTER);
			this.setAlign(VerticalAlignment.CENTER);
			this.setStyleName("crm-ContextArea");
			this.setWidth100();
			this.setHeight100();
			LoginView view = new LoginView();
			this.addMember(view);
			this._loginForm = view.getLoginForm();
		}
	}

	private class LoginView extends VLayout {
		protected Label _window_form_title;
		private LoginForm _loginForm;

		public LoginView() {
			createComponents();
			viewLayout();
		}

		private void createComponents() {
			this._loginForm = new LoginForm(CREDENTIALS_URL,false);
			this._loginForm.addDrawHandler(new DrawHandler() {
				@Override
				public void onDraw(DrawEvent event) {
					_window_form_title.setWidth(_loginForm.getWidth());
				}
			});
			this._window_form_title = new WindowFormTitle();
		}

		protected void viewLayout(){
			this.setWidth(300);
			this.setHeight(200);
			this.setLayoutAlign(Alignment.CENTER);
			this.addMember(_window_form_title);
			this.addMember(_loginForm);
		}

		protected LoginForm getLoginForm(){
			return this._loginForm;
		}

	}
	
	private class LoginWindow extends AS2Window {
		private LoginForm _loginForm;

		public LoginWindow() {
			createComponents();
			windowLayout();
		}
		@Override
		public void createComponents() {
			this._loginForm = new LoginForm(CREDENTIALS_URL,true);
			this._window_form_title = new WindowFormTitle();
		}
		
		@Override
		public void windowLayout(){
			setShowCloseButton(false);
			setShowTitle(false);
			setAutoSize(true);
			setAutoHeight();
			setAutoWidth();
			this.addDrawHandler(new DrawHandler() {
				@Override
				public void onDraw(DrawEvent event) {
					_window_form_title.setWidth(LoginWindow.this.getWidth());
				}
			});
			this.addItem(_window_form_title);
			this.addItem(_loginForm);
			this.show();
		}

		protected LoginForm getLoginForm(){
			return this._loginForm;
		}

	}

	private class WindowFormTitle extends Label{
		public WindowFormTitle() {
			this.setContents(getWindowFormTitle());
			this.setWidth(200);
			this.setStyleName("crm-dynamicForm-titlelabel");
			this.setBorder("1px solid #7598c7");
			this.setAutoHeight();
		}
		
		public String getWindowFormTitle() {
			return "<b style=\"color:black;font-size:10pt;\">Unesite korisničko ime i lozinku:</b>";
		}

	}
	private class LoginForm extends AS2DynamicForm {
		private String credentialsURL;
		private boolean window;

		public LoginForm(String credentialsURL, boolean window) {
			this.credentialsURL = credentialsURL;
			this.window = window;
			BlurbItem blurbItem = new BlurbItem("loginFailure");
			blurbItem.setVisible(false);
			blurbItem.setColSpan(2);
			blurbItem.setDefaultValue("Invalid username or password");
			blurbItem.setCellStyle("formCellError");
			TextItem textItem = new TextItem("username","User name");
			textItem.setTitleOrientation(TitleOrientation.LEFT);
			textItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent keyPressEvent) {
					if (keyPressEvent.getKeyName().equals("Enter")) {
						focusInItem("password");
					}
				}
			});
			PasswordItem passwordItem = new PasswordItem("password", "Password");
			passwordItem.setTitleOrientation(TitleOrientation.LEFT);
			passwordItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent keyPressEvent) {
					if (keyPressEvent.getKeyName().equals("Enter")) {
						doLogin();
					}
				}
			});
			ButtonItem buttonItem = new ButtonItem("Login");
			buttonItem.setWidth(100);
			buttonItem.setColSpan(2);
			buttonItem.setAlign(Alignment.RIGHT);
			buttonItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					doLogin();
				}
			});
			setWrapItemTitles(false);
			setAutoHeightDynamicForm();
			setStyleNameGray();
			setStyleNameGray();
			setAlign(Alignment.CENTER);
			setCanSubmit(true);//Uncomment if u are using datasource
			setFields(textItem, passwordItem, buttonItem);
		}

		public void doLogin() {
			RPCRequest request = new RPCRequest();
			request.setContainsCredentials(true);
			request.setActionURL(credentialsURL);
			request.setUseSimpleHttp(true);
			request.setShowPrompt(false);
			Map<String, String> params = new HashMap<String, String>();
			params.put("j_username", getValueAsString("username"));
			params.put("j_password", getValueAsString("password"));
			request.setParams(params);
			RPCManager.sendRequest(request, new RPCCallback() {
				@Override
				public void execute(RPCResponse response, Object rawData,
						RPCRequest request) {
					clearValues();
					if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
						hideItem("loginFailure");
						RPCManager.resendTransaction();
						if(window)
							loginWindow.hide();
						else
							loginView.hide();
					} else if (response.getStatus() == RPCResponse.STATUS_LOGIN_INCORRECT) {
						showItem("loginFailure");
					} else if (response.getStatus() == RPCResponse.STATUS_MAX_LOGIN_ATTEMPTS_EXCEEDED) {
						SC.warn("Max login attempts exceeded.");
					}
					focusInItem("username");
					AS2ClientContext.setSessionValue(AS2ClientContext.USER_FULL_NAME, "Administrator");
					AS2ClientContext.setSessionValue(AS2ClientContext.AS2_USERNAME, "admin");
					AS2ClientContext.setSessionValue(AS2ClientContext.ROLE, "Administrator");
					AS2ClientContext.setSessionValue(AS2ClientContext.ROLE_ID, "1");
					AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION_ID, "portal");
					AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION,"Portal");
					AS2ClientContext.setSessionValue(AS2ClientContext.LOGIN_TIME,AS2ClientContext.formatDateTimeToString(new java.util.Date()));
					AS2ClientContext.setSessionValue(AS2ClientContext.TIMEOUT, "100000");
					_desktop.initMainLayout();
				}
			});
		}
	}
}


