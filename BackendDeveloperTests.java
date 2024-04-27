import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;

//Frontend partner's imports
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class BackendDeveloperTests extends ApplicationTest {

   // Placeholder backend object for testing
   Backend backend = new Backend();

   @Test
   public void testGetListOfAllLocations() {
      List<String> locations = backend.getListOfAllLocations();

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



   @BeforeEach
   public void setup() throws Exception {
      FrontendPlaceholder.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
      ApplicationTest.launch(FrontendPlaceholder.class);
   }

   @Test
   public void testAboutButtonIntegration() {
      Button aboutButton = lookup("#aboutID").query();
      Label aboutText = lookup("#aboutText").query();

      clickOn("#aboutID");
      Assertions.assertTrue(aboutText.getText().contains("Type locations into the search bar"));

      clickOn("#aboutID");
      Assertions.assertTrue(aboutText.getText().isEmpty());
   }

   @Test
   public void testFurthestIntegration() {
      TextField furthestText = lookup("#furthestTextID").query();
      Label furthestLabel = lookup("#furthestLabelID").query();
      Button findFurthestButton = lookup("#furthestButtonID").query();

      clickOn("#furthestTextID");
      write("Start Location");

      clickOn("#furthestButtonID");
      Assertions.assertTrue(furthestLabel.getText().contains("Most Distance Location:"));
   }

}
