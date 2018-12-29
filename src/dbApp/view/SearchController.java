package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    private ObservableList<TableEntry> searchQuery = FXCollections.observableArrayList();
    private DBUnit dbUnit = new DBUnit();
    private final static String TABLENAME = "Patient";
    private TableEntry selectedTE = null;

    @FXML
    private TextField minAgeText;

    @FXML
    private TextField maxAgeText;

    @FXML
    private ChoiceBox<String> sexSelect;

    @FXML
    private TextField siteText;

    @FXML
    private TextArea diagnosisText;

    @FXML
    private TextField minSlidesText;

    @FXML
    private TextField minBlocksText;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<TableEntry, String> caseCol;

    @FXML
    private TableColumn<TableEntry, Double> ageCol;

    @FXML
    private TableColumn<TableEntry, String> sexCol;

    @FXML
    private TableColumn<TableEntry, String> siteCol;

    @FXML
    private TableColumn<TableEntry, String> diagnosisCol;

    @FXML
    private TableColumn<TableEntry, Integer> slidesCol;

    @FXML
    private TableColumn<TableEntry, Integer> blocksCol;

    @FXML
    private TableView<TableEntry> dbTable;

    @FXML
    private TableColumn<TableEntry, LocalDate> dateaccessionedCol;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SearchController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        //define each column in the table
        caseCol.setCellValueFactory(cellData -> cellData.getValue().caseNoProperty());
        ageCol.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        sexCol.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        siteCol.setCellValueFactory(cellData -> cellData.getValue().siteProperty());
        diagnosisCol.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        slidesCol.setCellValueFactory(cellData -> cellData.getValue().slidesProperty().asObject());
        blocksCol.setCellValueFactory(cellData -> cellData.getValue().blocksProperty().asObject());
        dateaccessionedCol.setCellValueFactory(cellData -> cellData.getValue().dateaccessionedProperty());

        // define the limitation of each text field
        minAgeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,4})?")) {
                    minAgeText.setText(oldValue);
                }
            }
        });
        maxAgeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,4})?")) {
                    maxAgeText.setText(oldValue);
                }
            }
        });
        minSlidesText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    minSlidesText.setText(oldValue);
                }
            }
        });
        minBlocksText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    minBlocksText.setText(oldValue);
                }
            }
        });
        sexSelect.setItems(FXCollections.observableArrayList("", "F", "M"));
        sexSelect.setValue("");
        sexSelect.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });

        // Listen for selection changes and show the entry details when changed.
        dbTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectEntry(newValue));
        // Double click selection
        dbTable.setRowFactory( tv -> {
            TableRow<TableEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TableEntry rowData = row.getItem();
                    Main.showForm(rowData);
                }
            });
            return row ;
        });

        /* todo: add multiple choice
        dbTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );*/
    }

    @FXML
    private void searchQuery(ActionEvent event) { //todo: add other searchBtn criteria
        try {
            dbUnit.connect();
            List<DBCol> myList = new ArrayList<>();
            searchQuery.clear();
            dbTable.setItems(searchQuery);

            // In case there is no input, it will not be considered in the query
            if(!maxAgeText.getText().isEmpty() || !minAgeText.getText().isEmpty()) {
                Double maxVal = maxAgeText.getText().isEmpty() ? 150.0 : Double.parseDouble(maxAgeText.getText());
                Double minVal = minAgeText.getText().isEmpty() ? 0.0 : Double.parseDouble(minAgeText.getText());
                myList.add(new DBCol<Double>(maxVal, "age", minVal));
            }
            if(sexSelect.getValue() != "")
                myList.add(new DBCol<String>(sexSelect.getValue(), "sex"));
            if(!siteText.getText().isEmpty())
                myList.add(new DBCol<String>(siteText.getText(), "site"));
            if(!diagnosisText.getText().isEmpty())
                myList.add(new DBCol<String>(diagnosisText.getText(), "diagnosis"));
            if(!minBlocksText.getText().isEmpty())
                myList.add(new DBCol<Integer>(1000, "blocks",
                        Integer.parseInt(minBlocksText.getText())));
            if(!minSlidesText.getText().isEmpty())
                myList.add(new DBCol<Integer>(1000, "slides",
                        Integer.parseInt(minSlidesText.getText())));
            ResultSet mySet = dbUnit.select(myList, TABLENAME);
            while(mySet.next()){
                Double ageVal = mySet.getDouble("age");
                ageVal = mySet.wasNull() ? null : ageVal; // since resultset turn null values into 0
                searchQuery.add(
                        new TableEntry(mySet.getString("caseNo"),
                                mySet.getString("sex"),
                                ageVal,
                                mySet.getString("site"),
                                mySet.getString("diagnosis"),
                                mySet.getInt("slides"),
                                mySet.getInt("blocks"),
                                mySet.getDate("dateaccessioned") == null ? null :
                                        mySet.getDate("dateaccessioned").toLocalDate()
                        ));
            }
            dbTable.setItems(searchQuery);
            System.out.println(dbTable.getItems().size());

        } catch (SQLException e) {
            e.printStackTrace(); // todo: add alert that the connection failed.
        }

    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param tableEntry the person or null
     */
    private void selectEntry(TableEntry tableEntry) {
        if (tableEntry != null)
            this.selectedTE = tableEntry;
    }

}
