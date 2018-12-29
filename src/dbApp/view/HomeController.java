package dbApp.view;

import dbApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button searchBtn;

    private DirectoryChooser sourceDirectoryChooser = new DirectoryChooser();

    @FXML
    void searchDB(ActionEvent event) {
        Main.showLayout("view/Search.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
