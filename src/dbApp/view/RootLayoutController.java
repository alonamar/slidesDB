package dbApp.view;

import dbApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

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

}
