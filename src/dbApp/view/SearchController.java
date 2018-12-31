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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    private ObservableList<TableEntry> searchQuery = FXCollections.observableArrayList();
    private DBUnit dbUnit = new DBUnit();
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
    private DatePicker startDatePick;

    @FXML
    private DatePicker endDatePick;

    @FXML
    private TextField patientNameText;

    @FXML
    private TextField contactText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField materialText;

    @FXML
    private TableColumn<TableEntry, String> caseCol;

    @FXML
    private TableColumn<TableEntry, String> osuCol;

    @FXML
    private TableColumn<TableEntry, Double> ageCol;

    @FXML
    private TableColumn<TableEntry, String> sexCol;

    @FXML
    private TableColumn<TableEntry, String> siteCol;

    @FXML
    private TableColumn<TableEntry, String> diagnosisCol;

    @FXML
    private TableColumn<TableEntry, LocalDate> dateaccessionedCol;

    @FXML
    private TableColumn<TableEntry, String> patientCol;

    @FXML
    private TableColumn<TableEntry, String> contactCol;

    @FXML
    private TableColumn<TableEntry, String> addressCol;

    @FXML
    private TableColumn<TableEntry, String> materialCol;

    @FXML
    private TableView<TableEntry> dbTable;


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
        osuCol.setCellValueFactory(cellData -> cellData.getValue().osuProperty());
        ageCol.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        sexCol.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        siteCol.setCellValueFactory(cellData -> cellData.getValue().siteProperty());
        diagnosisCol.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        dateaccessionedCol.setCellValueFactory(cellData -> cellData.getValue().dateaccessionedProperty());
        patientCol.setCellValueFactory(cellData -> cellData.getValue().patientnameProperty());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        contactCol.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialkeptProperty());

        // Custom rendering of the table cell.
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateaccessionedCol.setCellFactory(column -> {
            return new TableCell<TableEntry, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                    }
                }
            };
        });
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
        sexSelect.setItems(FXCollections.observableArrayList("All", "F", "M"));
        sexSelect.setValue("All");
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
    private void handleSearch(ActionEvent event) {
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
            if(sexSelect.getValue() != "All")
                myList.add(new DBCol<String>(sexSelect.getValue(), "sex"));
            if(!siteText.getText().isEmpty())
                myList.add(new DBCol<String>(siteText.getText(), "site"));
            if(!diagnosisText.getText().isEmpty())
                myList.add(new DBCol<String>(diagnosisText.getText(), "diagnosis"));
            if(startDatePick.getValue() != null || endDatePick.getValue() != null) {
                LocalDate endVal = endDatePick.getValue() == null ?
                        LocalDate.now() : endDatePick.getValue();
                LocalDate startVal = startDatePick.getValue() == null ?
                        LocalDate.MIN  : startDatePick.getValue();
                myList.add(new DBCol<LocalDate>(endVal, "dateaccessioned", startVal));
            }
            if(!patientNameText.getText().isEmpty())
                myList.add(new DBCol<String>(patientNameText.getText(), "patientname"));
            if(!contactText.getText().isEmpty())
                myList.add(new DBCol<String>(contactText.getText(), "contact"));
            if(!addressText.getText().isEmpty())
                myList.add(new DBCol<String>(addressText.getText(), "address"));
            if(!materialText.getText().isEmpty())
                myList.add(new DBCol<String>(materialText.getText(), "materialkept"));
            ResultSet mySet = dbUnit.select(myList, DBUnit.getTablename());
            while(mySet.next()){
                Double ageVal = mySet.getDouble("age");
                ageVal = mySet.wasNull() ? null : ageVal; // since resultset turn null values into 0
                searchQuery.add(
                        new TableEntry(mySet.getBoolean("old_form"),
                                mySet.getInt("id"),
                                mySet.getString("osu"),
                                mySet.getString("caseNo"),
                                mySet.getString("sex"),
                                ageVal,
                                mySet.getString("site"),
                                mySet.getString("diagnosis"),
                                mySet.getString("diseaseprocess"),
                                mySet.getString("otherbiopsies"),
                                mySet.getString("specialfeatures"),
                                mySet.getString("paxit"),
                                mySet.getBoolean("photography"),
                                mySet.getBoolean("seminarcase"),
                                mySet.getBoolean("reportable_newentity"),
                                mySet.getBoolean("uncertaindiagnosis"),
                                mySet.getString("clinicalinformation"),
                                mySet.getString("followup"),
                                mySet.getString("address"),
                                mySet.getString("patientname"),
                                mySet.getString("contactphone"),
                                mySet.getString("contactfax"),
                                mySet.getDate("dateaccessioned") == null ? null :
                                        mySet.getDate("dateaccessioned").toLocalDate(),
                                mySet.getString("email"),
                                mySet.getString("materialreceived"),
                                mySet.getString("materialkept"),
                                mySet.getBoolean("charge"),
                                mySet.getString("contact")
                        ));
            }
            dbTable.setItems(searchQuery);
            System.out.println(dbTable.getItems().size());

        } catch (SQLException e) {
            e.printStackTrace(); // todo: add alert that the connection failed.
        }

    }

    @FXML
    private void handleNewForm(ActionEvent event) {
        Main.showForm(null);
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        Main.showForm(this.selectedTE);
    }

    @FXML
    private void handleDelete(ActionEvent event) { // todo: add alert
        if(selectedTE == null)
            return;
        List<DBCol> myList = new ArrayList<>();
        myList.add(new DBCol<Integer>(selectedTE.getId(), "id"));
        try {
            dbUnit.connect();
            dbUnit.delete(myList, DBUnit.getTablename());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbTable.getItems().remove(selectedTE);
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
