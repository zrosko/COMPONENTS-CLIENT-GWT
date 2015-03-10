package hr.adriacomsoftware.app.client.smartgwt.kpi.backup;
//package hr.adriacomsoftware.app.client.smartgwt.kpi.client.backup;
//
//import com.google.gwt.core.client.JsArray;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.ui.Panel;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.visualization.client.AbstractDataTable;
//import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
//import com.google.gwt.visualization.client.DataTable;
//import com.google.gwt.visualization.client.Selection;
//import com.google.gwt.visualization.client.VisualizationUtils;
//import com.google.gwt.visualization.client.events.SelectHandler;
//import com.google.gwt.visualization.client.visualizations.PieChart;
//import com.google.gwt.visualization.client.visualizations.PieChart.Options;
//public class GoogleCharts{
//
//	public GoogleCharts() {
//		 // Create a callback to be called when the visualization API
//	    // has been loaded.
//	    Runnable onLoadCallback = new Runnable() {
//	      public void run() {
//	        Panel panel = RootPanel.get();
//
//	        // Create a pie chart visualization.
//	        PieChart pie = new PieChart(createTable(), createOptions());
//
//	        pie.addSelectHandler(createSelectHandler(pie));
//	        panel.add(pie);
//	      }
//	    };
//
//	    // Load the visualization api, passing the onLoadCallback to be called
//	    // when loading is done.
//	    VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//	}
//
//
//    private Options createOptions() {
//        Options options = Options.create();
//        options.setWidth(400);
//        options.setHeight(240);
//        options.set3D(true);
//        options.setTitle("My Daily Activities");
//        return options;
//      }
//
//      private SelectHandler createSelectHandler(final PieChart chart) {
//        return new SelectHandler() {
//          @Override
//          public void onSelect(SelectEvent event) {
//            String message = "";
//
//            // May be multiple selections.
//            JsArray<Selection> selections = chart.getSelections();
//
//            for (int i = 0; i < selections.length(); i++) {
//              // add a new line for each selection
//              message += i == 0 ? "" : "\n";
//
//              Selection selection = selections.get(i);
//
//              if (selection.isCell()) {
//                // isCell() returns true if a cell has been selected.
//
//                // getRow() returns the row number of the selected cell.
//                int row = selection.getRow();
//                // getColumn() returns the column number of the selected cell.
//                int column = selection.getColumn();
//                message += "cell " + row + ":" + column + " selected";
//              } else if (selection.isRow()) {
//                // isRow() returns true if an entire row has been selected.
//
//                // getRow() returns the row number of the selected row.
//                int row = selection.getRow();
//                message += "row " + row + " selected";
//              } else {
//                // unreachable
//                message += "Pie chart selections should be either row selections or cell selections.";
//                message += "  Other visualizations support column selections as well.";
//              }
//            }
//
//            Window.alert(message);
//          }
//        };
//      }
//
//      private AbstractDataTable createTable() {
//        DataTable data = DataTable.create();
//        data.addColumn(ColumnType.STRING, "Task");
//        data.addColumn(ColumnType.NUMBER, "Hours per Day");
//        data.addRows(2);
//        data.setValue(0, 0, "Work");
//        data.setValue(0, 1, 14);
//        data.setValue(1, 0, "Sleep");
//        data.setValue(1, 1, 10);
//        return data;
//      }
//}
