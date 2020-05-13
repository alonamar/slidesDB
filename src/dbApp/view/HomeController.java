package dbApp.view;

import dbApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private AnchorPane rootPane;

    @FXML
    void openConsultDB(ActionEvent event) {
        loadPage("view/ConsultSearch.fxml", "Consult Database");
    }

    @FXML
    void openBlockDB(ActionEvent event) {
        loadPage("view/BlockSearch.fxml", "Block Database");
    }

    public void loadPage(String name, String title){
        BorderPane root = null;
        FXMLLoader toolBarLoader = null;
        try {
            toolBarLoader = new FXMLLoader();
            toolBarLoader.setLocation(Main.class.getResource("view/ToolBar.fxml"));
            root = toolBarLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToolBarController toolBarController = toolBarLoader.getController();
        toolBarController.setStage(root);
        toolBarController.loadSearch(name);
        Main.showLayout(root, title);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
