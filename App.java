import javafx.application.Application;

import java.io.IOException;

public class App {
   public static void main(String[] args) {
      System.out.println("v0.1");

      Backend backend = new Backend(new DijkstraGraph<>());

      try {
         backend.loadGraphData("campus.dot");
      } catch (IOException e) {
         System.err.println("Error loading graph data: " + e.getMessage());
         return;
      }

      Frontend.setBackend(backend);
      Application.launch(Frontend.class, args);
   }
}
