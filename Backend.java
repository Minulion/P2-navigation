import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend implements BackendInterface {

   public ArrayList<String> seenNodes;
   public ArrayList<String> allLocations;

   private GraphADT<String, Double> graph;

   public Backend(GraphADT<String, Double> graph) {
      this.graph = graph;
      seenNodes = new ArrayList<>();
      allLocations = new ArrayList<>();
   }


   public void loadGraphData(String filename) throws IOException {
      try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
         String line;
         Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*->\\s*\"([^\"]+)\"\\s*\\[seconds=([\\d.]+)\\]");
         while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
               String source = matcher.group(1).trim();
               String target = matcher.group(2).trim();
               Double weight = Double.parseDouble(matcher.group(3).trim());

               //System.out.println("Found source: " + source + ", target: " + target + ", weight: " + weight); // Add this line for logging

               if (!seenNodes.contains(source)) {
                  seenNodes.add(source);
                  allLocations.add(source);
                  graph.insertNode(source);
                  //System.out.println("Added location: " + source);
               }
               if (!seenNodes.contains(target)) {
                  seenNodes.add(target);
                  allLocations.add(target);
                  graph.insertNode(target);
                  //System.out.println("Added location: " + target);
               }

               graph.insertEdge(source, target, weight);
            } else {
               System.out.println("Invalid line format: " + line);
            }
         }
	 //System.out.println(allLocations);
         //System.out.println(allLocations.size());
      }
   }
   public List<String> getListOfAllLocations() {
     System.out.println(allLocations);
     System.out.println(allLocations.size());
     return allLocations;
   }

   public List<String> findShortestPath(String startLocation, String endLocation) {
      return graph.shortestPathData(startLocation, endLocation);
   }

   public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
      List<Double> travelTimes = new ArrayList<>();
      List<String> path = findShortestPath(startLocation, endLocation);
      for (int i = 0; i < path.size() - 1; i++) {
         String currentLocation = path.get(i);
         String nextLocation = path.get(i + 1);
         try {
            double travelTime = graph.getEdge(currentLocation, nextLocation).doubleValue();
            System.out.println(
                "Travel time between " + currentLocation + " and " + nextLocation + ": " + travelTime);
            travelTimes.add(travelTime);
         } catch (NoSuchElementException e) {
            System.out.println(currentLocation + " ----" + nextLocation);
         }
      }
      return travelTimes;
   }

   public String getMostDistantLocation(String startLocation) throws NoSuchElementException {
      List<String> allLocations = getListOfAllLocations();

      if (allLocations.isEmpty()) {
         throw new NoSuchElementException("No locations found.");
      }

      double maxDistance = Double.MIN_VALUE;
      String mostDistant = null;
      for (String location : allLocations) {
         double distance = graph.shortestPathCost(startLocation, location);
         if (distance > maxDistance) {
            maxDistance = distance;
            mostDistant = location;
         }
      }
      if (mostDistant == null) {
         throw new NoSuchElementException("No locations found.");
      }
      return mostDistant;
   }
}
