package Leet.Solutions;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

// 1396 Design Underground System
public class UndergroundSystem {

    private HashMap<String, HashMap<String, double[]>> averageTime;
    private HashMap<Integer, Map.Entry<String, Integer>> checkIn;


    public UndergroundSystem() {
        averageTime = new HashMap<>();
        checkIn = new HashMap<>();
    }

    public void checkIn(int id, String sourceStation, int tripStartTime) {
        checkIn.put(id, new AbstractMap.SimpleEntry<>(sourceStation, tripStartTime));
    }

    public void checkOut(int id, String destinationStation, int tripEndTime) {
        Map.Entry<String, Integer> checkInInfo = checkIn.remove(id);
        String sourceStation = checkInInfo.getKey();
        int tripStartTime = checkInInfo.getValue();

        HashMap<String, double[]> destination = averageTime.getOrDefault(sourceStation, new HashMap<>());
        double[] destinationAverageTime = destination.getOrDefault(destinationStation, new double[2]);
        destinationAverageTime[0] += tripEndTime - tripStartTime;
        destinationAverageTime[1]++;

        destination.put(destinationStation, destinationAverageTime);
        averageTime.put(sourceStation, destination);
    }

    public double getAverageTime(String startStation, String endStation) {
        double[] avgTime = averageTime.get(startStation).get(endStation);
        return avgTime[0]/avgTime[1];
    }
}
