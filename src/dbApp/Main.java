package dbApp;

import dbApp.database.DBUnit;
import dbApp.view.FormController;
import dbApp.view.RootLayoutController;
import dbApp.view.SearchController;
import dbApp.view.TableEntry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static SearchController searchController;
    private static AnchorPane formLayout;

    public static SearchController getSearchController() {
        return searchController;
    }

    public static AnchorPane getFormLayout() {
        return formLayout;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SusterDB");

        loginScreen();
        //initRootLayout();
        //showLayout("view/Search.fxml");
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
            RootLayoutController controller = loader.getController();
            controller.setMain(this);
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
            searchController = loader.getController();
            rootLayout.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginScreen(){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your username and password");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            if(usernamePassword.getKey().equals("suster") &&  usernamePassword.getValue().equals("trump")) {
                initRootLayout();
                showLayout("view/Search.fxml");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Entrance");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username or password!");
                alert.showAndWait();
            }
        });
    }

    public static void showForm(TableEntry entry) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Form.fxml"));
            formLayout = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage); // not sure if needed
            Scene scene = new Scene(formLayout);
            dialogStage.setScene(scene);

            FormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEntry(entry);

            // Show the dialog and wait until the user closes it
            dialogStage.show();
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    if(!controller.handleCancel())
                        we.consume();
                }
            });
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

    public BorderPane getRootLayout(){
        return rootLayout;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
