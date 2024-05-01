import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

//Frontend partner's imports

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;

public class BackendDeveloperTests {

   // Placeholder backend object for testing
   //Backend backend = new Backend(new GraphPlaceholder()); //initializationError


   private Backend backend = new Backend();

   @Test
   public void testGetListOfAllLocations() {
      List<String> locations = backend.getListOfAllLocations();
      DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

      assertFalse(locations.isEmpty());
      assertEquals(3, locations.size());
      assertTrue(locations.contains("Union South"));
      assertTrue(locations.contains("Computer Sciences and Statistics"));
      assertTrue(locations.contains("Atmospheric, Oceanic and Space Sciences"));
   }

   @Test
   public void testFindShortestPath() {
      List<String> path = backend.findShortestPath("Union South", "Atmospheric, Oceanic and Space Sciences");

      assertFalse(path.isEmpty());
      assertEquals(3, path.size());
      assertEquals("Union South", path.get(0));
      assertEquals("Computer Sciences and Statistics", path.get(1));
      assertEquals("Atmospheric, Oceanic and Space Sciences", path.get(2));
   }

   @Test
   public void testGetTravelTimesOnPath() {
      List<Double> travelTimes = backend.getTravelTimesOnPath("Union South", "Atmospheric, Oceanic and Space Sciences");

      assertFalse(travelTimes.isEmpty());
      assertEquals(2, travelTimes.size());
      assertEquals(176.0, travelTimes.get(0), 0.01);
      assertEquals(127.2, travelTimes.get(1), 0.01);
   }

   @Test
   public void testGetMostDistantLocation() {
      String mostDistant = backend.getMostDistantLocation("Union South");

      assertEquals("Atmospheric, Oceanic and Space Sciences", mostDistant);
   }

   @Test
   public void testCreateAllControls() {
      FrontendInterface frontend = new Frontend();
      Pane parent = new Pane();
      frontend.createAllControls(parent);
      System.out.println(parent.getChildren().size()+ "!!!!!!!!!!");
      assertEquals(5, parent.getChildren().size()); // Assuming four controls are added
   }

   @Test
   public void testCreateShortestPathControls() {
      FrontendInterface frontend = new Frontend();
      Pane parent = new Pane();
      frontend.createShortestPathControls(parent);
      assertEquals(3, parent.getChildren().size());
   }


}
