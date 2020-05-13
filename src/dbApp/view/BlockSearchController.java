package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.ir.Block;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlockSearchController extends Search<TableEntryBlock> {

    @FXML
    private TextField organText;

    @FXML
    private TextField boxNumText;

    @FXML
    private TextField caseNumText;

    @FXML
    private TextField origNumText;

    @FXML
    private TextField otherNumText;

    @FXML
    private TextArea diagnosisText;

    @FXML
    private TextArea specialFeaturesText;

    @FXML
    private TextField numOfBlocksText;

    @FXML
    private TextField numOfSlidesText;

    @FXML
    private TableColumn<TableEntryBlock, String> organCol;

    @FXML
    private TableColumn<TableEntryBlock, String> boxNumCol;

    @FXML
    private TableColumn<TableEntryBlock, String> caseNumCol;

    @FXML
    private TableColumn<TableEntryBlock, String> origNumCol;

    @FXML
    private TableColumn<TableEntryBlock, String> otherNumCol;

    @FXML
    private TableColumn<TableEntryBlock, String> diagnosisCol;

    @FXML
    private TableColumn<TableEntryBlock, String> specialFeaturesCol;

    @FXML
    private TableColumn<TableEntryBlock, Double> numOfBlocksCol;

    @FXML
    private TableColumn<TableEntryBlock, Double> numOfSlidesCol;

    @FXML
    private TableColumn<TableEntryBlock, String> paperworkCol;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BlockSearchController() {
         this.dbUnit = new DBUnit("blocks");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    protected void initialize() {
        //define each column in the table
        organCol.setCellValueFactory(cellData -> cellData.getValue().organProperty());
        boxNumCol.setCellValueFactory(cellData -> cellData.getValue().boxNumProperty());
        caseNumCol.setCellValueFactory(cellData -> cellData.getValue().caseNumProperty());
        origNumCol.setCellValueFactory(cellData -> cellData.getValue().origNumProperty());
        otherNumCol.setCellValueFactory(cellData -> cellData.getValue().otherNumProperty());
        diagnosisCol.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        specialFeaturesCol.setCellValueFactory(cellData -> cellData.getValue().specialFeaturesProperty());
        numOfBlocksCol.setCellValueFactory(cellData -> cellData.getValue().numOfBlocksProperty());
        numOfSlidesCol.setCellValueFactory(cellData -> cellData.getValue().numOfSlidesProperty());
        paperworkCol.setCellValueFactory(cellData -> cellData.getValue().paperworkProperty());

        // define the limitation of each text field
        numOfBlocksText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d+")) {
                    numOfBlocksText.setText(oldValue);
                }
            }
        });
        numOfSlidesText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d+")) {
                    numOfSlidesText.setText(oldValue);
                }
            }
        });

        // Listen for selection changes and show the entry details when changed.
        dbTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectEntry(newValue));
        // Double click selection
        dbTable.setRowFactory( tv -> {
            TableRow<TableEntryBlock> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TableEntryBlock rowData = row.getItem();
                    editEntry();
                }
            });
            return row ;
        });
    }

    public void showForm(TableEntryBlock entry) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BlockForm.fxml"));
            this.formLayout = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage()); // not sure if needed
            Scene scene = new Scene(formLayout);
            dialogStage.setScene(scene);

            BlockFormController controller = loader.getController();
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

    @FXML
    protected void handleSearch(ActionEvent event) {
        try {
            dbUnit.connect();
            List<DBCol> myList = new ArrayList<>();
            searchQuery.clear();
            dbTable.setItems(searchQuery);

            // In case there is no input, it will not be considered in the query
            if(!numOfBlocksText.getText().isEmpty()) {
                Double maxVal = 100000.0;
                Double minVal = numOfBlocksText.getText().isEmpty() ? 0.0 : Double.parseDouble(numOfBlocksText.getText());
                myList.add(new DBCol<Double>(maxVal, "numOfBlocks", minVal));
            }
            if(!numOfSlidesText.getText().isEmpty()) {
                Double maxVal = 100000.0;
                Double minVal = numOfSlidesText.getText().isEmpty() ? 0.0 : Double.parseDouble(numOfSlidesText.getText());
                myList.add(new DBCol<Double>(maxVal, "numOfSlides", minVal));
            }
            if(!organText.getText().isEmpty())
                myList.add(new DBCol<String>(organText.getText(), "organ"));
            if(!boxNumText.getText().isEmpty())
                myList.add(new DBCol<String>(boxNumText.getText(), "boxNum"));
            if(!caseNumText.getText().isEmpty())
                myList.add(new DBCol<String>(caseNumText.getText(), "caseNum"));
            if(!origNumText.getText().isEmpty())
                myList.add(new DBCol<String>(origNumText.getText(), "origNum"));
            if(!otherNumText.getText().isEmpty())
                myList.add(new DBCol<String>(otherNumText.getText(), "otherNum"));
            if(!diagnosisText.getText().isEmpty())
                myList.add(new DBCol<String>(diagnosisText.getText(), "diagnosis"));
            if(!specialFeaturesText.getText().isEmpty())
                myList.add(new DBCol<String>(specialFeaturesText.getText(), "specialFeatures"));

            ResultSet mySet = dbUnit.select(myList);
            while(mySet.next()){
                Double numOfBlocksVal = mySet.getDouble("numOfBlocks");
                numOfBlocksVal = mySet.wasNull() ? null : numOfBlocksVal; // since resultset turn null values into 0
                Double numOfSlidesVal = mySet.getDouble("numOfSlides");
                numOfSlidesVal = mySet.wasNull() ? null : numOfSlidesVal; // since resultset turn null values into 0
                searchQuery.add(
                        new TableEntryBlock(mySet.getInt("id"),
                                mySet.getString("organ"),
                                mySet.getString("boxNum"),
                                mySet.getString("caseNum"),
                                mySet.getString("origNum"),
                                mySet.getString("otherNum"),
                                mySet.getString("diagnosis"),
                                mySet.getString("specialFeatures"),
                                numOfBlocksVal,
                                numOfSlidesVal,
                                mySet.getString("paperwork")
                        ));
            }
            dbTable.setItems(searchQuery);
            System.out.println(dbTable.getItems().size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
