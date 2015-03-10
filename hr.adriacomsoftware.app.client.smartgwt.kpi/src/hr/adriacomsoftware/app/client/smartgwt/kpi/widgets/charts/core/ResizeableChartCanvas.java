package hr.adriacomsoftware.app.client.smartgwt.kpi.widgets.charts.core;

import org.moxieapps.gwt.highcharts.client.BaseChart;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;

/**
 * A SmartGWT container widget that will contain a GWT Highchart and automatically
 * handle growing/shrinking the chart as the SmartGWT container changes in size.
 *
 * @author squinn@moxiegroup.com (Shawn Quinn)
 * @author ante.strikoman@gmail.com (Ante Strikoman)
 * @since 1.1
 */
public class ResizeableChartCanvas extends WidgetCanvas {

	BaseChart<?> _chart;

    public ResizeableChartCanvas(final BaseChart<?> chart) {
        super(chart);
        this._chart=chart;
        chart.setReflow(false);
        final WidgetCanvas wc = this;
        this.addResizedHandler(new ResizedHandler() {
            public void onResized(ResizedEvent event) {
                chart.setSize(wc.getWidth(), wc.getHeight(), false);
            }
        });
        this.addDrawHandler(new DrawHandler() {
            public void onDraw(DrawEvent event) {
                chart.setSize(wc.getWidth(), wc.getHeight(), false);
            }
        });

        this.addVisibilityChangedHandler(new VisibilityChangedHandler() {

			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				chart.setVisible(event.getIsVisible());
			}
		});
        wc.setOverflow(Overflow.HIDDEN);
    }

    public BaseChart<?> getChart(){
    	return _chart;
    }
}