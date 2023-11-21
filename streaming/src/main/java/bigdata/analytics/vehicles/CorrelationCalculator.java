package bigdata.analytics.vehicles;

import bigdata.pojo.VehicleInfo;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
/**
 * Calculates the correlation coefficient between vehicle speed and CO2 emissions
 * for a given time window.
 */
public class CorrelationCalculator extends ProcessAllWindowFunction<VehicleInfo, Double, TimeWindow> {
    @Override
    public void process(ProcessAllWindowFunction<VehicleInfo, Double, TimeWindow>.Context context,
                        Iterable<VehicleInfo> iterable,
                        Collector<Double> collector) throws Exception {
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumXSquare = 0.0;
        double sumYSquare = 0.0;
        int count = 0;

        for (VehicleInfo veh : iterable) {
            double speed = veh.VehicleSpeed;
            double co2 = veh.VehicleCO2;

            sumX += speed;
            sumY += co2;
            sumXY += speed * co2;
            sumXSquare += speed * speed;
            sumYSquare += co2 * co2;

            count++;
        }

        if (count > 0) {
            double correlation = calculateCorrelationCoefficient(sumX, sumY, sumXY, sumXSquare, sumYSquare, count);
            collector.collect(correlation);
        }
    }

    private double calculateCorrelationCoefficient(double sumX, double sumY, double sumXY, double sumXSquare, double sumYSquare, int count) {
        double numerator = count * sumXY - sumX * sumY;
        double denominator = Math.sqrt((count * sumXSquare - sumX * sumX) * (count * sumYSquare - sumY * sumY));
        return numerator / denominator;
    }
}
