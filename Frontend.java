import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.List;

public class Frontend extends Application implements FrontendInterface {

    private static BackendPlaceholder back;

    public static void setBackend(BackendPlaceholder back) {
        Frontend.back = back;
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

    private List<String> shortestPath = null;
    private List<Double> travelTimes = null;
    private Double totalTime = 0.0;
    private String pathString = "";
    private boolean showTimes = false;

    /**
     * Creates the controls for the shortest path search.
     * @param parent the parent pane that contains all controls
     */
    public void createShortestPathControls(Pane parent) {
        Label src = new Label("Path Start Selector: ");
        TextField startText = new TextField();
        src.setLayoutX(32);
        src.setLayoutY(16);
        startText.setLayoutX(160);
        startText.setLayoutY(16);
        parent.getChildren().add(src);
        parent.getChildren().add(startText);

        Label dst = new Label("Path End Selector: ");
        TextField endText = new TextField();
        dst.setLayoutX(32);
        dst.setLayoutY(48);
        endText.setLayoutX(160);
        endText.setLayoutY(48);
        parent.getChildren().add(dst);
        parent.getChildren().add(endText);

        Button find = new Button("Submit/Find Button");
        find.setLayoutX(32);
        find.setLayoutY(80);
        find.addEventHandler(ActionEvent.ACTION, (event) -> {
            shortestPath = back.findShortestPath(startText.getText(), endText.getText());
            travelTimes = back.getTravelTimesOnPath(startText.getText(), endText.getText());
            for (Double d : travelTimes) { //iterate thru travelTimes to sum them up into totalTime
                totalTime += d;
            }
            totalTime = totalTime/60; //conversion to minutes

            if (!showTimes) {
                pathString = "Results List: ";
                for (int i = 0; i < shortestPath.size(); i++) {
                    pathString += "\n\t";
                    pathString += shortestPath.get(i);
                }
            } else {
                pathString += "Results List (with walking times): ";
                pathString += "\n\t";
                pathString += shortestPath.get(0);
                for (int i = 1; i < shortestPath.size(); i++) {
                    pathString += "\n\t";
                    pathString += "-(";
                    pathString += String.valueOf(travelTimes.get(i - 1));
                    pathString += "sec)->";
                    pathString += shortestPath.get(i);
                }
                pathString += "\n\t";
                pathString += "Total time: ";
                pathString += String.valueOf(totalTime);
                pathString += "min";
            }
            createPathListDisplay(parent);
        });
        parent.getChildren().add(find);
    }

    /**
     * Creates the controls for displaying the shortest path returned by the search.
     * @param the parent pane that contains all controls
     */
    public void createPathListDisplay(Pane parent) {
        Label path =
        new Label(pathString);
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
        showTimesBox.addEventHandler(ActionEvent.ACTION, (event) -> {
            showTimes = showTimesBox.isSelected();
        });
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