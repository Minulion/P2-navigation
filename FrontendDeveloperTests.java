import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;

public class FrontendDeveloperTests extends ApplicationTest {

    @BeforeEach
    public void setup() throws Exception {
	    ApplicationTest.launch(Frontend.class);
    }

    /**
     * This test finds the buttons in key methods, and confirms
     * that the text in each has been properly initialized.
     */
    @Test
    public void testButtonsExist() {
        // Button button = lookup("#onlyButtonId").query();
        // Assertions.assertEquals("click me",button.getText());
    }

    /**
     * This test simulates finding the shortest path using 
     * preset return values. Tests functionality of textFields
     * and buttons.
     */
    @Test
    public void testSearchPath() {
        TextField startText = lookup("#startTextID").query();
        TextField endText = lookup("#endTextID").query();
        Label path = lookup("#pathID").query();
        Button find = lookup("#findID").query();

        clickOn("#startTextID");
        write("it's lit");
        Assertions.assertEquals("it's lit",startText.getText());

        clickOn("#endTextID");
        write("it's litty");
        Assertions.assertEquals("it's litty",endText.getText());

        clickOn("#findID");
        Assertions.assertTrue(path.getText().contains("Results List: "));
    }

    /**
     * This test simulates finding the shortest path using 
     * preset return values, including the times. Tests 
     * functionality of textFields, buttons, and checkBoxes.
     */
    @Test
    public void testSearchPathTimes() {
        TextField startText = lookup("#startTextID").query();
        TextField endText = lookup("#endTextID").query();
        Label path = lookup("#pathID").query();
        Button find = lookup("#findID").query();
        CheckBox showTimesBox = lookup("#boxID").query();

        clickOn("#startTextID");
        write("it's lit");
        Assertions.assertEquals("it's lit",startText.getText());

        clickOn("#endTextID");
        write("it's litty");
        Assertions.assertEquals("it's litty",endText.getText());

        clickOn("#boxID");

        clickOn("#findID");
        Assertions.assertTrue(path.getText().contains("Results List (with walking times): "));
    }

    /**
     * tests frontend functionality
     */
    @Test
    public void testFurthest() {
        TextField startText = lookup("#furthestTextID").query();
        Label furthestFromLabel = lookup("#furthestLabelID").query();
        Button furthestFromButton = lookup("#furthestButtonID").query();

        clickOn("#startTextID");
        write("it's lit");
        Assertions.assertEquals("it's lit",startText.getText());

        clickOn("#furthestButtonID");
        Assertions.assertTrue(furthestFromLabel.getText().contains("Most Distance Location:"));
    }

    /**
     * To demonstrate the code being tested, you can run the SampleApp above
     * as a JavaFX application through the following entry point.
     */
    public static void main(String[] args) {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
	    Application.launch(Frontend.class, args);
    }
}