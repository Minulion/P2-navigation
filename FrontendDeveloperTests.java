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
import java.io.IOException;
import java.util.NoSuchElementException;

public class FrontendDeveloperTests extends ApplicationTest {

    /**
     * This test finds the about and quit buttons, and confirms
     * that the text in each has been properly initialized.
     */
    @Test
    public void testAboutQuitExist() {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

        Button about = lookup("#aboutID").query();
        Assertions.assertEquals("About",about.getText());

        Button quit = lookup("#quitID").query();
        Assertions.assertEquals("Quit",quit.getText());
    }

    /**
     * This test simulates finding the shortest path using 
     * preset return values. Tests functionality of textFields
     * and buttons.
     */
    @Test
    public void testSearchPath() {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

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
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

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
     * This test simulates finding the furthest location using 
     * preset return values. Tests functionality of textFields 
     * and buttons.
     */
    @Test
    public void testFurthest() {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

        TextField startText = lookup("#furthestTextID").query();
        Label furthestFromLabel = lookup("#furthestLabelID").query();
        Button furthestFromButton = lookup("#furthestButtonID").query();

        clickOn("#furthestTextID");
        write("it's lit");
        Assertions.assertEquals("it's lit",startText.getText());

        clickOn("#furthestButtonID");
        Assertions.assertTrue(furthestFromLabel.getText().contains("Most Distance Location:"));
    }

    /**
     * Tests finding the shortest path using
     * frontend and backend together
     */
    @Test
    public void integrationTest1() {
        Backend back = new Backend(new DijkstraGraph());
        try {
            back.loadGraphData("campus.dot");
        } catch (IOException e) {
            Assertions.fail("invalid file :)");
        }
        Frontend.setBackend(back);
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

        TextField startText = lookup("#startTextID").query();
        TextField endText = lookup("#endTextID").query();
        Label path = lookup("#pathID").query();
        Button find = lookup("#findID").query();

        clickOn("#startTextID");
        write("Memorial Union");
        Assertions.assertEquals("Memorial Union",startText.getText());

        clickOn("#endTextID");
        write("Science Hall");
        Assertions.assertEquals("Science Hall",endText.getText());

        clickOn("#findID");
        Assertions.assertTrue(path.getText().contains("Results List: "));
    }

    /**
     * Tests finding the furthest location
     * using frontend and backend together
     */
    @Test
    public void integrationTest2() {
        Backend back = new Backend(new DijkstraGraph());
        try {
            back.loadGraphData("campus.dot");
        } catch (IOException e) {
            Assertions.fail("invalid file :)");
        }
        Frontend.setBackend(back);
        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
            Assertions.fail("app failed :)");
        }

        TextField startText = lookup("#furthestTextID").query();
        Label furthestFromLabel = lookup("#furthestLabelID").query();
        Button furthestFromButton = lookup("#furthestButtonID").query();

        clickOn("#furthestTextID");
        write("Memorial Union");
        Assertions.assertEquals("Memorial Union",startText.getText());

        clickOn("#furthestButtonID");
        Assertions.assertTrue(furthestFromLabel.getText().contains("Most Distance Location:"));
    }

    /**
     * Tests that getMostDistantLocation() 
     * throws a NoSuchElementException when
     * necessary.
     */
    @Test
    public void partnerTest1() {
        Assertions.assertThrows(NoSuchElementException.class, () -> backend.getMostDistantLocation("it's litty"));
    }

    /**
     * Tests that findShortestPath() returns
     * an empty list when no path exists.
     */
    @Test
    public void partnerTest2() {
        List<String> path = backend.findShortestPath("Union South", "it's litty");
        assertTrue(path.isEmpty());
    }

    /**
     * To demonstrate the code being tested, you can run Frontend 
     * as a JavaFX application through the following entry point.
     */
    public static void main(String[] args) {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
	    Application.launch(Frontend.class, args);
    }
}