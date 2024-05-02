import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    System.out.println("v0.1");
    Backend back = new Backend(new DijkstraGraph());
    back.loadGraphData("campus.dot");
    Frontend.setBackend(back);

    Application.launch(Frontend.class, args);
  }
}
