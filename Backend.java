import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Backend implements BackendInterface {

   private GraphADT graph; //changed to GraphADT

   public Backend(GraphADT graph) {
      this.graph = graph; //constructor was empty, updated to take GraphADT
   }

   ArrayList<String> allLocations = new ArrayList<>();  //new list to keep track of locations

   public void loadGraphData(String filename) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null) {
         if (line.contains("->")) {
            String[] parts = line.split("\\s*->\\s*|\\s*\\[seconds=|\\];");
            String source = parts[0].replaceAll("\"", "").trim();
            String target = parts[1].replaceAll("\"", "").trim();
            Double weight = Double.parseDouble(parts[2].trim());
            graph.insertNode(source); //add nodes to graph before edge
            graph.insertNode(target);
            graph.insertEdge(source, target, weight);
            if (!allLocations.contains(source)) { //add to list of locations if not already added
               allLocations.add(source);
            }
            if (!allLocations.contains(target)) {
               allLocations.add(target);
            }
         }
      }
      reader.close();
   }

   public List<String> getListOfAllLocations() {
      return allLocations; //changed method to use new list
   }

   public List<String> findShortestPath(String startLocation, String endLocation) {
      List<String> path = new ArrayList<>();
      try {
         path = graph.shortestPathData(startLocation, endLocation);
      } catch (NoSuchElementException e) {
         path.add
      }

      return 
   }

   public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
      List<Double> travelTimes = new ArrayList<>();
      List<String> path = findShortestPath(startLocation, endLocation);
      for (int i = 0; i < path.size() - 1; i++) {
         String currentLocation = path.get(i);
         String nextLocation = path.get(i + 1);
	try{
            double travelTime = graph.getEdge(currentLocation, nextLocation).doubleValue();
           System.out.println("Travel time between " + currentLocation + " and " + nextLocation + ": " + travelTime);
	    travelTimes.add(travelTime);
	}catch (NoSuchElementException e) {
            System.out.println(currentLocation + " ----" + nextLocation);
         }
      }
      return travelTimes;
   }

   public String getMostDistantLocation(String startLocation) throws NoSuchElementException {
      List<String> allLocations = getListOfAllLocations();
      double maxDistance = Double.MIN_VALUE;
      String mostDistant = null;
      for (String location : allLocations) {
         double distance = 0.0;
         try { //skip locations that can't be accessed
            distance = graph.shortestPathCost(startLocation, location);
         } catch (NoSuchElementException e) {
            continue;
         }
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
