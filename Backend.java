import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Backend implements BackendInterface {

   /*private DijkstraGraph graph;

   public Backend(DijkstraGraph<String, Double> stringDoubleDijkstraGraph) {
      this.graph = new DijkstraGraph<String, Double>();
   }*/

   private GraphPlaceholder graph;

   public Backend() {
      this.graph = new GraphPlaceholder();
   }

   public Backend(GraphADT<String, Double> graphADT) {}
   //GraphADT



   public void loadGraphData(String filename) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null) {
         if (line.contains("->")) {
            String[] parts = line.split("\\s*->\\s*|\\s*\\[seconds=|\\];");
            String source = parts[0].replaceAll("\"", "").trim();
            String target = parts[1].replaceAll("\"", "").trim();
            Double weight = Double.parseDouble(parts[2].trim());
            graph.insertEdge(source, target, weight); //change from graph to dGraph
         }
      }
      reader.close();
   }

   public List<String> getListOfAllLocations() {
      List<String> locations = new ArrayList<>(graph.getNodeCount());
      for (int i = 0; i < graph.getNodeCount(); i++) {
         locations.add(graph.path.get(i));
      }
      return locations;
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
