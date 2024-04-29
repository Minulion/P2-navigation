import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    System.out.println("v0.1");

    //FrontendPlaceholder.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    //Application.launch(FrontendPlaceholder.class, args);

    Frontend.setBackend(new Backend(new GraphPlaceholder()));
    Application.launch(Frontend.class, args);

  }
}
