package dbApp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController2 implements Initializable {

    @FXML
    private Button search;
    @FXML
    private TableView<TableEntry> dbTable;

    ObservableList<TableEntry> pro = FXCollections.observableArrayList();

    @FXML
    void backHome(ActionEvent event) {
        //set coloums

        TableColumn<TableEntry, String> newCol0 = new TableColumn<>("old");
        newCol0.setCellValueFactory(new PropertyValueFactory<>("old_form"));
        //newCol0.setCellFactory(TextFieldTableCell.forTableColumn());
/*        newCol0.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<TableEntry, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<TableEntry, String> t) {
                        ((TableEntry) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOld_form(Boolean.valueOf(t.getNewValue()));
                    }
                }
        );*/
        TableColumn<TableEntry, String> newCol = new TableColumn<>("Name");
        newCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        TableColumn<TableEntry, String> newCol1 = new TableColumn<>("age");
        newCol1.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<TableEntry, String> newCol2 = new TableColumn<>("site");
        newCol2.setCellValueFactory(new PropertyValueFactory<>("site"));

        dbTable.getColumns().addAll(newCol0, newCol, newCol1, newCol2);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
