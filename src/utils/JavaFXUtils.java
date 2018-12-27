package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXUtils {

    // c - getClass() method of the controller
    // layoutName - the name of the fxml file
    // root - the root object in the hierarchy tree of hte new layout
    public static void switchScenes(Class c, String layoutName, Parent root) throws IOException {
        Parent layout = FXMLLoader.load(c.getResource(layoutName));
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(layout, root.getScene().getWidth(), root.getScene().getHeight()));
    }
}
