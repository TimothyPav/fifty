import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scatter_Plot {
   private final XYChart chart;
   private final List<Double> x_Data;
   private final List<Double> y_Data;

   public Scatter_Plot(String title, String x_title, String y_title){
       this.x_Data = new ArrayList<>();
       this.y_Data = new ArrayList<>();

       // Create Chart
       chart = new XYChartBuilder().width(800).height(600)
               .title(title).xAxisTitle(x_title).yAxisTitle(y_title).build();

       // Customize Chart
       chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
       chart.getStyler().setChartTitleVisible(true);
       chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
       chart.getStyler().setMarkerSize(16);
   }

   public void add_data(List<Double> xdata, List<Double> ydata){
       for (int i = 0; i < xdata.size() && i < ydata.size(); i++){
           x_Data.add(xdata.get(i));
           y_Data.add(ydata.get(i));
       }
   }

   public void display(String series_name){
       chart.addSeries(series_name, x_Data, y_Data);
       new SwingWrapper<>(chart).displayChart();
   }

    public List<Double> convertToDouble(List<Integer> intList) {
        return intList.stream()
                .map(Integer::doubleValue)
                .collect(Collectors.toList());
    }
}
