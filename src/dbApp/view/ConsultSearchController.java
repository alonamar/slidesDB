package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultSearchController extends Search<TableEntryConsult> {

    @FXML
    private TextField caseNumText;

    @FXML
    private TextField mcwNumText;

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
    private TableColumn<TableEntryConsult, String> caseCol;

    @FXML
    private TableColumn<TableEntryConsult, String> osuCol;

    @FXML
    private TableColumn<TableEntryConsult, Double> ageCol;

    @FXML
    private TableColumn<TableEntryConsult, String> sexCol;

    @FXML
    private TableColumn<TableEntryConsult, String> siteCol;

    @FXML
    private TableColumn<TableEntryConsult, String> diagnosisCol;

    @FXML
    private TableColumn<TableEntryConsult, LocalDate> dateaccessionedCol;

    @FXML
    private TableColumn<TableEntryConsult, String> patientCol;

    @FXML
    private TableColumn<TableEntryConsult, String> contactCol;

    @FXML
    private TableColumn<TableEntryConsult, String> addressCol;

    @FXML
    private TableColumn<TableEntryConsult, String> materialCol;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ConsultSearchController() {
        this.dbUnit = new DBUnit("patient");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    protected void initialize() {
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
            return new TableCell<TableEntryConsult, LocalDate>() {
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
            TableRow<TableEntryConsult> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TableEntryConsult rowData = row.getItem();
                    editEntry();
                }
            });
            return row ;
        });

        /* todo: add multiple choice
        dbTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );*/
    }

    public void showForm(TableEntryConsult entry) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ConsultForm.fxml"));
            this.formLayout = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage()); // not sure if needed
            Scene scene = new Scene(formLayout);
            dialogStage.setScene(scene);

            ConsultFormController controller = loader.getController();
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
            if(!caseNumText.getText().isEmpty())
                myList.add(new DBCol<String>(caseNumText.getText(), "caseno"));
            if(!mcwNumText.getText().isEmpty())
                myList.add(new DBCol<String>(mcwNumText.getText(), "osu"));
            if(!materialText.getText().isEmpty())
                myList.add(new DBCol<String>(materialText.getText(), "materialkept"));
            ResultSet mySet = dbUnit.select(myList);
            while(mySet.next()){
                Double ageVal = mySet.getDouble("age");
                ageVal = mySet.wasNull() ? null : ageVal; // since resultset turn null values into 0
                searchQuery.add(
                        new TableEntryConsult(mySet.getBoolean("old_form"),
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
            e.printStackTrace();
        }

    }
}
