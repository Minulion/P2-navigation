import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;

public class BackendDeveloperTests {

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
}
