package hr.adriacomsoftware.app.client.smartgwt.karticno.gr.views;

import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2SearchMWindow;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.MultipleAppearance;

public class McardGrZahtjevSearchMWindow extends AS2SearchMWindow {

	public McardGrZahtjevSearchMWindow(AS2View2 parent, AS2ListGrid listGrid) {
		  super(parent,listGrid);
		   setFocusInItem("broj_zahtjeva");
	}

	@Override
	protected AS2FormItem[] getSearchFormItems() {
		List<AS2FormItem> items = new ArrayList<AS2FormItem>();
		AS2FormItem broj_zahtjeva = new AS2FormItem("broj_zahtjeva", AS2Field.INTEGER,"Broj Zahtjeva", 1, null);
		items.add(broj_zahtjeva);
		AS2FormItem oib = new AS2FormItem("oib", AS2Field.TEXT, "OIB", 2, null, true);
		items.add(oib);
		AS2FormItem jmbg = new AS2FormItem("jmbg", AS2Field.TEXT, "JMBG", 2, null,
				true);
		items.add(jmbg);
		AS2FormItem ime_prezime = new AS2FormItem("ime_prezime", AS2Field.TEXT,
				"Prezime i Ime", 3, null, true);
		items.add(ime_prezime);
		AS2FormItem broj_partije_mcard = new AS2FormItem("broj_partije_mcard",
				AS2Field.INTEGER, "Broj kreditnog računa", 3, null, true);
		items.add(broj_partije_mcard);
		AS2FormItem datum_zaprimanja = new AS2FormItem("datum_zaprimanja",AS2FormItem.FORM_DATERANGE, "Datum zaprimanja", 3, null, true);
		items.add(datum_zaprimanja);
		AS2FormItem datum_odobravanja = new AS2FormItem("datum_odobravanja",AS2FormItem.FORM_DATERANGE, "Datum odobravanja", 3, null, true);
		items.add(datum_odobravanja);
		AS2FormItem vrsta_zahtjeva = new AS2FormItem("vrsta_zahtjeva",AS2Field.FORM_SELECT, "Vrsta zahtjeva", 4, null, true);
		vrsta_zahtjeva.setMultiple(true);
		vrsta_zahtjeva.setMultipleAppearance(MultipleAppearance.PICKLIST);
		vrsta_zahtjeva.setValueMap("Osnovni", "Dodatni");
		items.add(vrsta_zahtjeva);
		AS2FormItem status_zahtjeva = new AS2FormItem("status_zahtjeva",AS2Field.FORM_SELECT, "Status zahtjeva", 4, null, true);
		status_zahtjeva.setMultiple(true);
		status_zahtjeva.setMultipleAppearance(MultipleAppearance.PICKLIST);
		items.add(status_zahtjeva);
		AS2FormItem[] formItems = new AS2FormItem[items.size()];
		items.toArray(formItems);
		return formItems;
	}
}
