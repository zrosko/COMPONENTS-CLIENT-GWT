/**
 * 
 */
package hr.adriacomsoftware.app.client.smartgwt.sspn.models;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.app.client.smartgwt.sspn.views.SubjektivnaOcjenaView;
import hr.adriacomsoftware.app.client.smartgwt.trazi.models.TraziOsobaModel;
import hr.adriacomsoftware.app.client.smartgwt.trazi.views.TraziWindow;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

/**
 * @author msekso,astrikoman
 *
 */
public class SubjektivnaOcjenaModel extends AS2RestJSONDataSource implements SubjektivnaOcjenaConstants {
	
	private static SubjektivnaOcjenaModel instance = new SubjektivnaOcjenaModel("SubjetkivnaOcjenaModel");
	public static SubjektivnaOcjenaModel getInstance() {
		return instance;
	}
	
	public SubjektivnaOcjenaModel(String id) {
		super(id);
		FormItemIcon trazi = new FormItemIcon();
		trazi.setSrc(AS2Resources.INSTANCE.search_icon().getSafeUri().asString());
		trazi.addFormItemClickHandler(new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				LinkedHashMap<String,String> remap = new LinkedHashMap<String,String>();
				remap.put("oib", "oib_");
				remap.put("jmbg_mb", "jmbg_mb_");
				remap.put("naziv_klijenta", "naziv");
				remap.put("vrsta", "vrsta");
				Criteria criteria = null;
				if(event.getItem().getValue()!=null)
					criteria = new Criteria(remap.get(event.getItem().getName()), event.getItem().getValue().toString());
				new TraziWindow(TraziOsobaModel.getInstance(),SubjektivnaOcjenaView.view,criteria);
			}
		});
		AS2DataSourceField id_ocjene = new AS2DataSourceField(PRN_OCJENA__ID_OCJENE,AS2Field.PRIMARY_KEY);
		AS2DataSourceField jmbg_mb = new AS2DataSourceField(PRN_OCJENA__JMBG_MB,AS2Field.TEXT,"JMBG/MB",13,true);
		jmbg_mb.setIcons(trazi);
		AS2DataSourceField oib =  new AS2DataSourceField(PRN_OCJENA__OIB,AS2Field.TEXT,"OIB",11,true);
		oib.setIcons(trazi);
		AS2DataSourceField naziv_klijenta = new AS2DataSourceField(PRN_OCJENA__NAZIV_KLIJENTA,AS2Field.TEXT,"Naziv klijenta");
		naziv_klijenta.getField().setCanEdit(false);
		AS2DataSourceField ocjena =  new AS2DataSourceField(PRN_OCJENA__OCJENA,AS2Field.INTEGER,"Ocjena",0,true);
		AS2DataSourceField napomena =  new AS2DataSourceField(PRN_OCJENA__NAPOMENA,AS2Field.TEXT);
		napomena.getField().setHidden(true);
		AS2DataSourceField grupa =  new AS2DataSourceField(PRN_OCJENA__GRUPA,AS2Field.COMBO,"Grupa",0,true);
		AS2DataSourceField obrazlozenje = new AS2DataSourceField(PRN_OCJENA__OBRAZLOZENJE,AS2Field.TEXTAREA,"Obrazloženje");
//		obrazlozenje.setFormItemEditorHeight(56);//TODO
		AS2DataSourceField vrijedi_od = new AS2DataSourceField(PRN_OCJENA__VRIJEDI_OD,AS2Field.DATE,"Vrijedi od");
		AS2DataSourceField vrijedi_do = new AS2DataSourceField(PRN_OCJENA__VRIJEDI_DO,AS2Field.DATE,"Vrijedi do");
		this.setFields(id_ocjene,jmbg_mb,oib,naziv_klijenta,ocjena,napomena,grupa,obrazlozenje,vrijedi_od,vrijedi_do);
	}
	@Override
	public String getRemoteObject() {
		return "hr.adriacomsoftware.app.server.pranjenovca.subjektivnaocjena.facade.SubjektivnaOcjenaFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
	}
	
	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"dodajSubjektivnuOcjenu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"azurirajSubjektivnuOcjenu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"brisiSubjektivnuOcjenu");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(AS2RestJSONDataSource.SERVICE,"procitajSveOcjene");
		params.put(AS2RestJSONDataSource.COMPONENT,getRemoteObject());
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,getTransformTo());
		return params;
	}
}


