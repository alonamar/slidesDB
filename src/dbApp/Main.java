package dbApp;

import dbApp.database.DBUnit;
import dbApp.view.FormController;
import dbApp.view.TableEntry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private Stage primaryStage;
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SusterDB");

        initRootLayout();
        showLayout("view/Search.fxml");
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the selected view inside the root layout.
     * @param layoutName the layout to load
     */
    public static void showLayout(String layoutName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(layoutName));
            AnchorPane layout = (AnchorPane) loader.load();
            rootLayout.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showForm(TableEntry entry) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Form.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            // dialogStage.initOwner(primaryStage); // not sure if needed
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            FormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEntry(entry);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
