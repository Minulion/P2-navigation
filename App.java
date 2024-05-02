import javafx.application.Application;
import java.io.IOException;

public class App {
  public static void main(String[] args) {
    System.out.println("v0.1");
    Backend back = new Backend(new DijkstraGraph());
    try {
      back.loadGraphData("campus.dot");
      Frontend.setBackend(back);

      Application.launch(Frontend.class, args);
    } catch (IOException e) {
      System.out.println("failed to load file");
    }
    // Frontend.setBackend(back);

    // Application.launch(Frontend.class, args);
  }
}
