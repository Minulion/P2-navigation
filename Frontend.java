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

    private static Backend back;

    public static void setBackend(Back back) {
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
        startText.setId("startTextID");
        src.setLayoutX(32);
        src.setLayoutY(16);
        startText.setLayoutX(176);
        startText.setLayoutY(16);
        parent.getChildren().add(src);
        parent.getChildren().add(startText);

        Label dst = new Label("Path End Selector: ");
        TextField endText = new TextField();
        endText.setId("endTextID");
        dst.setLayoutX(32);
        dst.setLayoutY(48);
        endText.setLayoutX(176);
        endText.setLayoutY(48);
        parent.getChildren().add(dst);
        parent.getChildren().add(endText);

        Label path = new Label();
        path.setId("pathID");
        path.setLayoutX(32);
        path.setLayoutY(112);
        parent.getChildren().add(path);

        Button find = new Button("Submit/Find Button");
        find.setId("findID");
        find.setLayoutX(32);
        find.setLayoutY(80);
        find.addEventHandler(ActionEvent.ACTION, (event) -> {
            shortestPath = back.findShortestPath(startText.getText(), endText.getText());
            travelTimes = back.getTravelTimesOnPath(startText.getText(), endText.getText());
            totalTime = 0.0;
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
                pathString = "Results List (with walking times): ";
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
            path.setText(pathString);
        });
        parent.getChildren().add(find);
    }

    /**
     * Creates the controls for displaying the shortest path returned by the search.
     * @param the parent pane that contains all controls
     */
    public void createPathListDisplay(Pane parent) {
        Label path = new Label();
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
        showTimesBox.setId("boxID");
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
        Label locationSelector = new Label("Location Selector:  ");
        TextField startText = new TextField();
        startText.setId("furthestTextID");
        startText.setLayoutX(608);
        startText.setLayoutY(16);
        locationSelector.setLayoutX(480);
        locationSelector.setLayoutY(16);
        parent.getChildren().add(locationSelector);
        parent.getChildren().add(startText);

        Label furthestFromLabel = new Label();
        furthestFromLabel.setId("furthestLabelID");
        furthestFromLabel.setLayoutX(480);
        furthestFromLabel.setLayoutY(80);
        parent.getChildren().add(furthestFromLabel);
        
        Button furthestFromButton = new Button("Find Most Distant Location");
        furthestFromButton.setId("furthestButtonID");
        furthestFromButton.addEventHandler(ActionEvent.ACTION, (event) -> {
            furthestFromLabel.setText("Most Distance Location:  \n" + back.getMostDistantLocation(startText.getText()));
        });
        furthestFromButton.setLayoutX(480);
        furthestFromButton.setLayoutY(48);
        parent.getChildren().add(furthestFromButton);
    }

    private boolean aboutOpen = false;
    /**
     * Creates an about and quit button.
     * @param parent parent pane that contains all controls
     */
    public void createAboutAndQuitControls(Pane parent) {
        Label aboutText = new Label();
        aboutText.setLayoutX(32);
        aboutText.setLayoutY(496);
        parent.getChildren().add(aboutText);

        Button about = new Button("About");
        about.setId("aboutID");
        about.addEventHandler(ActionEvent.ACTION, (event) -> {
            if (!aboutOpen) {
                aboutText.setText("Type locations into the search bar \nto get started. Press this button\nagain to close.");
                aboutOpen = true;
            } else {
                aboutText.setText("");
                aboutOpen = false;
            }
        });
        about.setLayoutX(32);
        about.setLayoutY(560);
        parent.getChildren().add(about);

        Button quit = new Button("Quit");
        quit.setId("quitID");
        quit.addEventHandler(ActionEvent.ACTION, (event) -> {
            Stage stage = (Stage) quit.getScene().getWindow();
            stage.close();
        });
        quit.setLayoutX(96);
        quit.setLayoutY(560);
        parent.getChildren().add(quit);
    }

    
}