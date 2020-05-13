package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class Search<T extends TableEntry> {
    protected ObservableList<T> searchQuery = FXCollections.observableArrayList();
    protected DBUnit dbUnit;
    protected T selectedTE = null;
    protected static AnchorPane formLayout;

    @FXML
    protected TableView<T> dbTable;

    @FXML
    abstract protected void initialize();

    @FXML
    abstract protected void handleSearch(ActionEvent event);

    @FXML
    protected void handleNewForm(ActionEvent event) {
        showForm(null);
    }

    abstract public void showForm(T entry);

    public static AnchorPane getFormLayout() {
        return formLayout;
    }

    @FXML
    protected void handleEdit(ActionEvent event) {
        editEntry();
    }

    public void editEntry(){
        if(this.selectedTE == null)
            return;
        showForm(this.selectedTE);
    }

    @FXML
    protected void handleDelete(ActionEvent event) {
        deleteEntry();
    }

    public void deleteEntry(){
        if(selectedTE == null)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to permanently delete this record?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            List<DBCol> myList = new ArrayList<>();
            myList.add(new DBCol<Integer>(selectedTE.getId(), "id"));
            try {
                dbUnit.connect();
                dbUnit.delete(myList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbTable.getItems().remove(selectedTE);
        } else {
        }

    }


    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param tableEntry the person or null
     */
    protected void selectEntry(T tableEntry) {
        if (tableEntry != null)
            this.selectedTE = tableEntry;
    }

    public T getSelectedTE(){
        return this.selectedTE;
    }
}
