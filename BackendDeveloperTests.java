import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.layout.Pane;
/**
 * Tests for Backend
 */
public class BackendDeveloperTests {

   private Backend backend;

   /**
    * Set up method for Backend
    */
   @BeforeEach
   public void setUp() {
      GraphADT<String, Double> graph = new DijkstraGraph<>();


      backend = new Backend(graph);
      // Add nodes and edges to the graph
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertEdge("A", "B", 4.0);
      graph.insertEdge("A", "C", 2.0);
      graph.insertEdge("B", "D", 1.0);
      graph.insertEdge("C", "D", 5.0);
   }

   /**
    * Test load graph method
    */
   @Test
   public void testLoadGraphData() {
      assertDoesNotThrow(() -> backend.loadGraphData("dgraph.dot"));
   }

   /**
    * Test List all location
    */
   @Test
   public void testGetListOfAllLocations() {
      List<String> locations = backend.getListOfAllLocations();
      assertEquals(4, locations.size());
      assertTrue(locations.contains("A"));
      assertTrue(locations.contains("B"));
      assertTrue(locations.contains("C"));
      assertTrue(locations.contains("D"));
   }

   /**
    * Test find shortest path
    */
   @Test
   public void testFindShortestPath() {
      List<String> shortestPath = backend.findShortestPath("A", "D");
      assertNotNull(shortestPath);
      assertEquals(3, shortestPath.size());
      assertEquals("A", shortestPath.get(0));
      assertEquals("B", shortestPath.get(1));
      assertEquals("D", shortestPath.get(2));
   }

   /**
    * test find travel time
    */
   @Test
   public void testGetTravelTimesOnPath() {
      List<Double> travelTimes = backend.getTravelTimesOnPath("A", "D");
      assertNotNull(travelTimes);
      assertEquals(2, travelTimes.size());
      assertEquals(4.0, travelTimes.get(0));
      assertEquals(1.0, travelTimes.get(1));
   }

   /**
    * test most distant location
    */
   @Test
   public void testGetMostDistantLocation() {
      String mostDistantLocation = backend.getMostDistantLocation("A");
      assertNotNull(mostDistantLocation);
      assertEquals("D", mostDistantLocation);
   }

   /**
    * test most distant location exceotions
    */
   @Test
   public void testGetMostDistantLocationThrowsException() {
      assertThrows(NoSuchElementException.class, () -> backend.getMostDistantLocation("X"));
   }

   /**
    * test for partner code
    */
   @Test
   public void testCreateShortestPathControlsIntegration() {
      Frontend frontend = new Frontend();
      Pane parent = new Pane();
      frontend.createShortestPathControls(parent);
      // Check if controls for shortest path search are created
      assertEquals(6, parent.getChildren().size()); // Assuming 6 controls are added
   }

   /**
    * test for partner code
    */
   @Test
   public void testCreateFurthestDestinationControlsIntegration() {
      Frontend frontend = new Frontend();
      Pane parent = new Pane();
      frontend.createFurthestDestinationControls(parent);
      // Check if controls for finding the most distant location are created
      assertEquals(5, parent.getChildren().size()); // Assuming 5 controls are added
   }
}
