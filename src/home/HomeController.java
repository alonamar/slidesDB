package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utils.JavaFXUtils;

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
        try {
            JavaFXUtils.switchScenes(getClass(), "Search.fxml", rootPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
