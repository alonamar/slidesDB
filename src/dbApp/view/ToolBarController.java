package dbApp.view;

import dbApp.Main;
import dbApp.database.DBUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import dbApp.view.HomeController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;

public class ToolBarController {
    private static ConsultSearchController searchController;
    private Label jobStatus = new Label();
    private Search search;
    private BorderPane root;

    @FXML
    private MenuItem newFormBtn;

    @FXML
    private MenuItem backUpBtn;

    @FXML
    private MenuItem printBtn;

    @FXML
    private MenuItem switchBtn;

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
        search.showForm(null);
    }

    @FXML
    private void editEntry(ActionEvent event) {
        search.editEntry();
    }

    @FXML
    private void deleteEntry(ActionEvent event) {
        search.deleteEntry();
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

    @FXML
    private void switchDB(ActionEvent event) {
        if(this.search instanceof BlockSearchController) {
            loadSearch("view/ConsultSearch.fxml");
            Main.getPrimaryStage().setTitle("Consult Database");
        } else {
            loadSearch("view/BlockSearch.fxml");
            Main.getPrimaryStage().setTitle("Block Database");
        }
    }

    @FXML
    private void backUpDB(ActionEvent event) {
        DBUnit dbUnit = new DBUnit();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Back Up DB");
        alert.setHeaderText(null);
        try {
            dbUnit.connect();
            dbUnit.backUpDB();
            alert.setContentText("Database was successfully backed up");
        } catch (SQLException e) {
            e.printStackTrace();
            alert.setContentText("Database could not back up");
        }
        alert.showAndWait();
    }

    public void loadSearch(String searchName){
        AnchorPane layout = null;
        FXMLLoader searchLoader = null;
        try {
            searchLoader = new FXMLLoader();
            searchLoader.setLocation(Main.class.getResource(searchName));
            layout = searchLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Search searchController = searchLoader.getController();
        this.root.setCenter(layout);
        setSearch(searchController);
    }

    public void setStage(BorderPane root){
        this.root = root;
    }

    @FXML
    public void initialize() { }

    public void setSearch(Search search) {
        this.search = search;
    }
}
