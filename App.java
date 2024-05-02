import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    System.out.println("v0.1");
    Backend back = new Backend(new DijkstraGraph());
    try {
      back.loadGraphData("campus.dot");
    } catch (IOException e) {
      Assertions.fail("invalid file :)");
    }
    Frontend.setBackend(back);

    Application.launch(Frontend.class, args);
  }
}
