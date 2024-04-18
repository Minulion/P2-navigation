import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Frontend implements FrontendInterface {

    private static BackendPlaceholder back;

    public static void setBackend(BackendPlaceholder back) {
        FrontendPlaceholder.back = back;
    }
    
    public void start(Stage stage) {
        Pane root = new Pane();

        createAllControls(root);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("P2: Prototype");
        stage.show();
    }

    /**
     * Creates all controls in the GUI.
     * @param parent the parent pane that contains all controls
     */
    public void createAllControls(Pane parent) {
        createShortestPathControls(parent);
        createPathListDisplay(parent);
        createAdditionalFeatureControls(parent);
        createAboutAndQuitControls(parent);
    }

    /**
     * Creates the controls for the shortest path search.
     * @param parent the parent pane that contains all controls
     */
    public void createShortestPathControls(Pane parent) {
        Label src = new Label("Path Start Selector: ");
        TextField startText = new TextField();
        src.setLayoutX(32);
        src.setLayoutY(16);
        startText.setLayoutX(64);
        startText.setLayoutY(16);
        parent.getChildren().add(src);
        parent.getChildren().add(startText);

        Label dst = new Label("Path End Selector: Computer Science");
        dst.setLayoutX(32);
        dst.setLayoutY(48);
        parent.getChildren().add(dst);

        Button find = new Button("Submit/Find Button");
        find.setLayoutX(32);
        find.setLayoutY(80);
        parent.getChildren().add(find);
    }

    /**
     * Creates the controls for displaying the shortest path returned by the search.
     * @param the parent pane that contains all controls
     */
    public void createPathListDisplay(Pane parent) {
        Label path =
        new Label(
            "Results List: \n\tMemorial Union\n\tSciene Hall\n\tPyschology\n\tComputer Science" +
            "\n\n" +
            "Results List (with walking times):\n\tMemorial Union\n\t-(30sec)->Science Hall\n\t-(170sec)->Psychology\n\t-(45sec)->Computer Science\n\tTotal time: 4.08min"
            );
        path.setLayoutX(32);
        path.setLayoutY(112);
        parent.getChildren().add(path);
    }

    /**
     * Creates controls for the two features in addition to the shortest path search.
     * @param parent parent pane that contains all controls
     */
    public void createAdditionalFeatureControls(Pane parent) {
        this.createTravelTimesBox(parent);
        this.createFurthestDestinationControls(parent);
    }

    /**
     * Creates the check box to add travel times in the result display.
     * @param parent parent pane that contains all controls
     */
    public void createTravelTimesBox(Pane parent) {
        CheckBox showTimesBox = new CheckBox("Show Walking Times");
        showTimesBox.setLayoutX(200);
        showTimesBox.setLayoutY(80);
        parent.getChildren().add(showTimesBox);
    }

    /**
     * Creates controls to search for a maximally distant location.
     * @param parent parent pane that contains all controls
     */
    public void createFurthestDestinationControls(Pane parent) {
        Label locationSelector = new Label("Location Selector:  Memorial Union");
        locationSelector.setLayoutX(500);
        locationSelector.setLayoutY(16);
        parent.getChildren().add(locationSelector);
        Button furthestFromButton = new Button("Find Most Distant Location");
        furthestFromButton.setLayoutX(500);
        furthestFromButton.setLayoutY(48);
        parent.getChildren().add(furthestFromButton);
        Label furthestFromLabel = new Label("Most Distance Location:  Union South");
        furthestFromLabel.setLayoutX(500);
        furthestFromLabel.setLayoutY(80);
        parent.getChildren().add(furthestFromLabel);
    }

    /**
     * Creates an about and quit button.
     * @param parent parent pane that contains all controls
     */
    public void createAboutAndQuitControls(Pane parent) {
        Button about = new Button("About");
        about.setLayoutX(32);
        about.setLayoutY(560);
        parent.getChildren().add(about);

        Button quit = new Button("Quit");
        quit.setLayoutX(96);
        quit.setLayoutY(560);
        parent.getChildren().add(quit);
    }

    
}