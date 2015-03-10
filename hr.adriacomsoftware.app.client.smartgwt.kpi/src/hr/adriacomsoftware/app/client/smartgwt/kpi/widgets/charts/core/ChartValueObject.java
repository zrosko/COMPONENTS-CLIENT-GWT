package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

import hr.adriacomsoftware.app.client.smartgwt.kpi.KpiContext;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2LineChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2SpiderWebChart;
import hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.AS2StockLineChart;
import hr.as2.inf.common.charts.AS2ChartConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.moxieapps.gwt.highcharts.client.BaseChart;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.StockChart;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class ChartValueObject extends Record{

    public static final String TITLE="title";
    public static final String SUBTITLE="subtitle";
    public static final String POKAZATELJ_ID="pokazateljId";
//    public static final String CHART_TYPE="chartType";
    public static final String YTITLE="yTitle";
    public static final String XCATEGORIES="x";
    public static final String SERIES="series";
    public static final String OZNAKE=  "oznake";

    public ChartValueObject(){
    }

    public void setChart(Record chart){
//    	chart.getAttributes();
        if (chart.getAttributeAsObject("chartDetails") != null) {
//            HashMap<String, Object> chartDetails = new HashMap<String, String>();
            Record details = chart.getAttributeAsRecord("chartDetails");
//            for (String attribute : details.getAttributes()) {
//            	if(attribute.equals("oznake")){
//            		chartDetails.put(attribute, details.getAttributeAsMap(attribute));
//            	}else
//            		chartDetails.put(attribute, details.getAttribute(attribute));
//            }
            setChartDetails(details);
        } else if (chart.getAttributeAsObject("records") != null) {
            for (Record record : chart.getAttributeAsRecordArray("records"))
                    addSeries(record);
        }

    }
    public void setTitle(String title){
        setAttribute(TITLE,title);
    }
    public String getChartTitle(){
    	String title = getAttribute(TITLE).toString();
    	if(title.equals(""))
    		return title;
    	if(title.contains(">"))
    		return title.substring(title.lastIndexOf(">")+2,title.length());
    	else
    		return title;
    }
    public String getTitle(){
        return getAttribute(TITLE);
    }
    public void setSubtitle(String subtitle){
    	setAttribute(SUBTITLE,subtitle);
    }
    public String getSubtitle(){
        return getAttribute(SUBTITLE);
    }
    public void setPokazateljId(String pokazateljId){
    	setAttribute(POKAZATELJ_ID,pokazateljId);
    }
    public String getPokazateljId(){
        return getAttribute(POKAZATELJ_ID);
    }
    public void setChartType(String type){
//    	setAttribute(CHART_TYPE,type);
    	setAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE,type);
    }
    public String getChartType(){
//        return getAttribute(CHART_TYPE);
        return getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE);
    }
    public void setYTitle(String yTitle){
    	setAttribute(YTITLE,yTitle);
    }
    public String getYTitle(){
        return getAttribute(YTITLE);
    }
    public void setXCategories(String x){
    	setAttribute(XCATEGORIES,x.toString().split(","));
    }
    public String[] getXCategories(){
        return getAttributeAsStringArray(XCATEGORIES);
    }
    public void setSeries(List<Record> series){
    	setAttribute(SERIES,series);
    }

    public void addSeries(Record record) {
        RecordList chartRecordList;
         if (getSeries() == null) {
            chartRecordList = new RecordList();
        } else {
            chartRecordList = getSeries();
        }
        chartRecordList.add(record);
        setAttribute(SERIES, chartRecordList);
    }

    public void addPieSeries(Record record) {
    	RecordList chartRecordList;
        if (getSeries() == null) {
            chartRecordList = new RecordList();
        } else {
            chartRecordList = getSeries();
        }
        setAttribute(SERIES, chartRecordList);
    }
    public RecordList getSeries(){
        return getAttributeAsRecordList(SERIES);
    }

//	public Double[] getCurrentSeriesPoints(Record currentSeries) {
////		currentSeries.getAttributes();
//		Double[] points = new Double[currentSeries
//				.getAttributeAsDoubleArray("y").length];
//		int j = 0;
//		for (Double point : currentSeries.getAttributeAsDoubleArray("y")) {
//			points[j] = point;
//			j++;
//		}
//		return points;
//    }

    public static Double[] getCurrentSeriesPoints(double[] currentSeriesPoints){
//  		currentSeries.getAttributes();
    	Double[] points = new Double[currentSeriesPoints.length];
//    	System.arraycopy(currentSeriesPoints, 0, points, 0, currentSeriesPoints.length);
  		int j = 0;
  		for (Double point : currentSeriesPoints) {
  			points[j] = point;
  			j++;
  		}
  		return points;

      }

    public Double[] getCurrentSeriesPoints(Record currentSeries, String name){
//		currentSeries.getAttributes();
		Double[] points;
		String value="y";
		if(currentSeries.getAttributeAsDoubleArray("y")!=null)
			points = new Double[currentSeries.getAttributeAsDoubleArray("y").length];
		else{
			value=name;
			points = new Double[currentSeries.getAttributeAsDoubleArray(name).length];
		}
		points=getCurrentSeriesPoints(currentSeries.getAttributeAsDoubleArray(value));
		return points;

    }

    public Point getCurrentBasicPieSeriesPoints(Record currentSeries,String currentSeriesName){
    	if(currentSeries.getAttributeAsDouble("y")!=null){
			return new Point(currentSeriesName,currentSeries.getAttributeAsDouble("y"));
		}else{
			return new Point(currentSeriesName,currentSeries.getAttributeAsDouble(currentSeriesName));
		}
    	
    	
//        currentSeries.getAttributes();
//        Point[] points = new  Point[currentSeries.getAttributeAsDoubleArray("y").length];
////        System.arraycopy(currentSeries.getAttributeAsDoubleArray("y"), 0, points, 0, currentSeries.getAttributeAsDoubleArray("y").length);
//        int j = 0;
//        for (Double point : currentSeries.getAttributeAsDoubleArray("y")) {
//        	points[j]= new Point(currentSeries.getAttribute(TITLE), point);
//            j++;
//        }
//        return points;
    }
    

	private Number[][] getCurrentStockChartPoints(Record currentSeries) {
		currentSeries.getAttributes();
		Number[][] points;
		Number[] x = null;
		Double[] y = null;
		if (currentSeries.getAttributeAsObject("x") != null) {
			String[] array = currentSeries.getAttributeAsStringArray("x");
			x = new Number[array.length];
			for (int i = 0; i < array.length; i++) {
				Date date = KpiContext.formatStringToDate(array[i]);
				x[i] = date.getTime()+(1000*60*60*24);//Error in getTime(). Fixed by adding +1day to Date
			}
		}
		if (currentSeries.getAttributeAsObject("y") != null) {
			y = new Double[currentSeries.getAttributeAsDoubleArray("y").length];
			y = getCurrentSeriesPoints(currentSeries
					.getAttributeAsDoubleArray("y"));
		}
		points = new Number[x.length][2];
		for (int j = 0; j < x.length; j++) {
			points[j][0] = x[j];
			points[j][1] = y[j];
		}
		return points;
	}

    public String getCurrentSeriesName(Record currentSeries){
        return currentSeries.getAttribute(TITLE);
    }

//    public void addSeriesToChart(Chart chart){
//		if (this.getSeries() != null) {
//			RecordList series = this.getSeries();
//			for(int i=0;i<series.getLength();i++){
//				Record record = series.get(i);
//				Record currentSeries = record.getAttributeAsRecord("record");
//				Double[] currentSeriesPoints;
//				String currentSeriesName= this.getCurrentSeriesName(currentSeries);
//				currentSeriesPoints = this.getCurrentSeriesPoints(currentSeries, currentSeriesName);
//				if(this.getChartType().equals("SpiderWeb")){
//					chart.addSeries(chart
//							.createSeries()
//							.setName(currentSeriesName)
////									this.getCurrentSeriesName(currentSeries))
//							.setPoints(currentSeriesPoints)
////									this.getCurrentSeriesPoints(currentSeries))
//							.setOption("pointPlacement", "on"));
//				}else if(this.getChartType().equals("AreaLine")){
//					if(currentSeriesName.contains("troškovi"))
//						chart.addSeries(chart.createSeries()
//							.setName(currentSeriesName)
//							.setPoints(currentSeriesPoints)
//							.setType(Series.Type.AREA));
//					else{
//						chart.addSeries(chart.createSeries()
//								.setName(currentSeriesName)
//								.setPoints(currentSeriesPoints)
//								.setType(Series.Type.SPLINE));
//					}
//				}
//				else{				
//					chart.addSeries(chart
//							.createSeries()
//							.setName(currentSeriesName)
//							.setPoints(currentSeriesPoints));
//				}
//			}
//		}
//	}
    
    public void addSeriesToChart(BaseChart<?> chaart){
    	Chart chart;
    	StockChart stockChart;
    	if (this.getSeries() != null) {
    		RecordList series = this.getSeries();
    		for(int i=0;i<series.getLength();i++){
    			Record record = series.get(i);
    			Record currentSeries = record.getAttributeAsRecord("record");
//    			for(String attribute:currentSeries.getAttributes()){
//    				currentSeries.getAttribute(attribute);
//    			}
    			String currentSeriesName= this.getCurrentSeriesName(currentSeries);
    			//Converting first letter to uppercase
    			String currentSeriesTitle = currentSeriesName.substring(0,1).toUpperCase()+currentSeriesName.substring(1,currentSeriesName.length());
    			if(chaart instanceof Chart){
    		    	chart = (Chart)chaart;
    		    	Double[] currentSeriesPoints = this.getCurrentSeriesPoints(currentSeries, currentSeriesName);
	    			if(this.getChartType().equals("SpiderWeb")){
	    				chart.addSeries(AS2SpiderWebChart.createCurrentSeriesForChart(chart,currentSeriesTitle,currentSeriesPoints));
	    			}else if(this.getChartType().equals("BasicPie")){
	    				//See AS2BasicPieChart // TODO
	    			}else if(this.getChartType().equals("AreaLine")){
	    				chart.addSeries(AS2LineChart.createCurrentSeriesForChart(chart,currentSeriesTitle,currentSeriesPoints));
    				}else{				
    					//Default
    					chart.addSeries(chart
    							.createSeries()
    							.setName(currentSeriesTitle)
    							.setPoints(currentSeriesPoints));
    				}
    			}else if (chaart instanceof StockChart){
    	    		stockChart = (StockChart)chaart;
    	    		Number[][] currentSeriesPoints = this.getCurrentStockChartPoints(currentSeries);
    	    		stockChart.addSeries(AS2StockLineChart.createCurrentSeriesForChart(stockChart,currentSeriesTitle,currentSeriesPoints));
    	    	}
    		}
    	}
	}
    
	@SuppressWarnings("unchecked")
	public void setChartDetails(Record details) {
		details.getAttributes();
	   setTitle(details.getAttribute(TITLE));
       setSubtitle(details.getAttribute(SUBTITLE));
       setPokazateljId(details.getAttribute(POKAZATELJ_ID));
       setChartType(details.getAttribute(AS2ChartConstants.AS2_CHART_TREE__CHART_TYPE));/*CHART_TYPE));*/
       setYTitle(details.getAttribute(YTITLE));
       setXCategories(details.getAttribute(XCATEGORIES));
       details.getAttributes();
       if(details.getAttributeAsMap(OZNAKE)!=null){
       		setAttribute(OZNAKE,(HashMap<String,String>)details.getAttributeAsMap(OZNAKE));
       }
	}
	

//    public void setChartDetails(HashMap<String, String> chartDetails){
//        setTitle(chartDetails.get(TITLE));
//        setSubtitle(chartDetails.get(SUBTITLE));
//        setPokazateljId(chartDetails.get(POKAZATELJ_ID));
//        setChartType(chartDetails.get(CHART_TYPE));
//        setYTitle(chartDetails.get(YTITLE));
//        setXCategories(chartDetails.get(XCATEGORIES));
//        if(chartDetails.get(OZNAKE)!=null)
//        	setAttribute(OZNAKE,chartDetails.get(OZNAKE));
////    }
	
}
