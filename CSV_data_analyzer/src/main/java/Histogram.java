import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.List;

public class Histogram {
   private final CategoryChart chart;

   public Histogram(String title, String x_title, String y_title) {
       this.chart = new CategoryChartBuilder()
               .width(800)
               .height(600)
               .title(title)
               .xAxisTitle(x_title)
               .yAxisTitle(y_title)
               .build();

       this.chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
   }

   public void add_series(String name, List<String> categories, List<Integer> values) {
       this.chart.addSeries(name, categories, values);
   }

   public void display() {
       new SwingWrapper<>(this.chart).displayChart();
   }




}