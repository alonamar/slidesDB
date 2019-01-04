package dbApp.view;

import dbApp.Main;
import dbApp.database.DBUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RootLayoutController {
    private Label jobStatus = new Label();
    private Main main;

    @FXML
    private MenuItem newFormBtn;

    @FXML
    private MenuItem backUpBtn;

    @FXML
    private MenuItem printBtn;

    @FXML
    private MenuItem closeBtn;

    @FXML
    private MenuItem editBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private MenuItem aboutBtn;

    @FXML
    private void newForm(ActionEvent event) {
        Main.showForm(null);
    }

    @FXML
    private void editEntry(ActionEvent event) {
        Main.getSearchController().editEntry();
    }

    @FXML
    private void deleteEntry(ActionEvent event) {
        Main.getSearchController().deleteEntry();
    }

    @FXML
    private void initDB(ActionEvent event) {
        DBUnit dbUnit = new DBUnit();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Init DB");
        alert.setHeaderText(null);
        try {
            dbUnit.connect();
            dbUnit.init();
            alert.setContentText("Database was successfully initialized");
        } catch (SQLException e) {
            e.printStackTrace();
            alert.setContentText("Database already exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        alert.showAndWait();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
