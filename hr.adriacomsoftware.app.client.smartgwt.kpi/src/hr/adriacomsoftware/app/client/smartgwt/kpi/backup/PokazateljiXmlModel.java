package hr.adriacomsoftware.app.client.smartgwt.kpi.backup;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PokazateljiXmlModel extends DataSource{

	   private static PokazateljiXmlModel instance = null;

	    public static PokazateljiXmlModel getInstance() {
	        if (instance == null) {
	            instance = new PokazateljiXmlModel("PokazateljiModel");
	        }
	        return instance;
	    }

	    public PokazateljiXmlModel(String id) {
			setID(id);
			setTitleField("Name");
			setRecordXPath("/List/pokazatelj");

			DataSourceTextField nameField = new DataSourceTextField("Name");

			DataSourceTextField idField = new DataSourceTextField("Id");
			idField.setPrimaryKey(true);
			idField.setRequired(true);

			DataSourceTextField parentIdField = new DataSourceTextField("ParentId");
			parentIdField.setRequired(true);
			parentIdField.setForeignKey(id + ".Id");
			parentIdField.setRootValue("root");

			DataSourceIntegerField levelField = new DataSourceIntegerField("Level");
			DataSourceTextField chartTypeField = new DataSourceTextField("ChartType");

	        setFields(idField, parentIdField, nameField, levelField, chartTypeField);

	        setDataURL("ds/pokazatelji/pokazatelji.data.xml");
	        setClientOnly(true);
	    }


}
