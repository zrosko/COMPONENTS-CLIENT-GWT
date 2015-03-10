package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

public interface AS2ChartProperties {
	
	public static final boolean ENABLE_CREDITS = false;
	public static final boolean SHOW_SHADOW = true;
	public static final int PLOT_BORDER_WIDTH = 1;
	public static final String BASIC_PIE_HIGHCHART ="BasicPie";
	public static final String BAR_NEG_STACK_HIGHCHART ="BarNegStack";
	public static final String SPIDER_WEB_HIGHCHART ="SpiderWeb";
	public static final String LINE_HIGHCHART ="Line";
	public static final String AREA_LINE_HIGHCHART ="AreaLine";
	
	public abstract ResizeableChartCanvas getChart(
			final ChartValueObject chartVo);

}
