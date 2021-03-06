package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FormController {

    @FXML
    private TextField caseText;

    @FXML
    private TextField osuText;

    @FXML
    private TextField pNameText;

    @FXML
    private TextField contactText;

    @FXML
    private TextArea addressText;

    @FXML
    private DatePicker dateText;

    @FXML
    private TextField contactPhoneText;

    @FXML
    private TextField contactFaxPhone;

    @FXML
    private TextField emailText;

    @FXML
    private TextArea siteText;

    @FXML
    private TextArea diagnosisText;

    @FXML
    private TextField ageText;

    @FXML
    private ChoiceBox<String> sexSelect;

    @FXML
    private TextArea clinicalText;

    @FXML
    private TextArea featuresText;

    @FXML
    private CheckBox picturesBox;

    @FXML
    private CheckBox reportableBox;

    @FXML
    private CheckBox dxBox;

    @FXML
    private CheckBox seminarBox;

    @FXML
    private TextArea receivedText;

    @FXML
    private TextArea keptText;

    @FXML
    private TextField paxitText;

    @FXML
    private TextField otherText;

    @FXML
    private TextArea diseaseText;

    @FXML
    private CheckBox chargeBox;

    @FXML
    private TextArea followText;

    private Stage dialogStage;
    private TableEntry entry;
    private DBUnit dbUnit = new DBUnit();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // define the limitation of each text field
        ageText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,4})?")) {
                    ageText.setText(oldValue);
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

        setEntry(this.entry);
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the entry to be edited in the dialog.
     *
     * @param entry
     */
    public void setEntry(TableEntry entry) {
        if(entry != null) {
            this.entry = entry;
            caseText.setText(entry.getCaseNo());
            osuText.setText(entry.getOsu());
            pNameText.setText(entry.getPatientname());
            contactText.setText(entry.getContact());
            addressText.setText(entry.getAddress());
            dateText.setValue(entry.getDateaccessioned());
            contactPhoneText.setText(entry.getContactphone());
            contactFaxPhone.setText(entry.getContactfax());
            emailText.setText(entry.getEmail());
            siteText.setText(entry.getSite());
            clinicalText.setText(entry.getClinicalinformation());
            featuresText.setText(entry.getSpecialfeatures());
            picturesBox.setSelected(entry.isPhotography());
            reportableBox.setSelected(entry.isReportable_newentity());
            dxBox.setSelected(entry.isUncertaindiagnosis());
            seminarBox.setSelected(entry.isSeminarcase());
            receivedText.setText(entry.getMaterialreceived());
            keptText.setText(entry.getMaterialkept());
            paxitText.setText(entry.getPaxit());
            otherText.setText(entry.getOtherbiopsies());
            diseaseText.setText(entry.getDiseaseprocess());
            chargeBox.setSelected(entry.isCharge());
            followText.setText(entry.getFollowup());
            diagnosisText.setText(entry.getDiagnosis());
            ageText.setText(entry.getAgeString());
            sexSelect.setValue(entry.getSex());
        }
    }

    public void updateEntry() { // todo: add listener instead of general update
        entry.setCaseNo(caseText.getText());
        entry.setOsu(osuText.getText());
        entry.setPatientname(pNameText.getText());
        entry.setContact(contactText.getText());
        entry.setAddress(addressText.getText());
        entry.setDateaccessioned(dateText.getValue());
        entry.setContactphone(contactPhoneText.getText());
        entry.setContactfax(contactFaxPhone.getText());
        entry.setEmail(emailText.getText());
        entry.setSite(siteText.getText());
        entry.setClinicalinformation(clinicalText.getText());
        entry.setSpecialfeatures(featuresText.getText());
        entry.setPhotography(picturesBox.isSelected());
        entry.setReportable_newentity(picturesBox.isSelected());
        entry.setUncertaindiagnosis(dxBox.isSelected());
        entry.setSeminarcase(seminarBox.isSelected());
        entry.setMaterialreceived(receivedText.getText());
        entry.setMaterialkept(keptText.getText());
        entry.setPaxit(paxitText.getText());
        entry.setOtherbiopsies(otherText.getText());
        entry.setDiseaseprocess(diseaseText.getText());
        entry.setCharge(chargeBox.isSelected());
        entry.setFollowup(followText.getText());
        entry.setDiagnosis(diagnosisText.getText());
        Double age = ageText.getText().isEmpty() ? null : Double.parseDouble(ageText.getText());
        entry.setAge(age);
        entry.setSex(sexSelect.getValue());
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {// todo : add alert

        // creating the entry for the database
        List<DBCol> myList = new ArrayList<>();
        myList.add(new DBCol<Boolean>(false, "old_form"));
        addFieldtoList(myList, osuText, "osu");
        addFieldtoList(myList, caseText, "caseNo");
        addFieldtoList(myList, dateText, "dateaccessioned");
        addFieldtoList(myList, pNameText, "patientname");
        addFieldtoList(myList, contactText, "contact");
        addFieldtoList(myList, contactPhoneText, "contactphone");
        addFieldtoList(myList, contactFaxPhone, "contactfax");
        addFieldtoList(myList, addressText, "address");
        addFieldtoList(myList, emailText, "email");
        addFieldtoList(myList, siteText, "site");
        addFieldtoList(myList, diagnosisText, "diagnosis");
        addFieldtoList(myList, diseaseText, "diseaseprocess");
        addFieldtoList(myList, featuresText, "specialfeatures");
        addFieldtoList(myList, keptText, "materialkept");
        addFieldtoList(myList, receivedText, "materialreceived");
        addFieldtoList(myList, picturesBox, "photography");
        addFieldtoList(myList, reportableBox, "reportable_newentity");
        addFieldtoList(myList, dxBox, "uncertaindiagnosis");
        addFieldtoList(myList, seminarBox, "seminarcase");
        addFieldtoList(myList, paxitText, "paxit");
        addFieldtoList(myList, otherText, "otherbiopsies");
        addFieldtoList(myList, clinicalText, "clinicalinformation");
        addFieldtoList(myList, followText, "followup");
        addFieldtoList(myList, chargeBox, "charge");
        if(sexSelect.getValue() != "All")
            myList.add(new DBCol<String>(sexSelect.getValue(), "sex"));
        if(!ageText.getText().isEmpty())
            myList.add(new DBCol<Double>(Double.parseDouble(ageText.getText()), "age"));
        try {
            dbUnit.connect();
            if(this.entry == null){
                dbUnit.insert(myList, DBUnit.getTablename());
            }else{
                List<DBCol> pkList = new ArrayList<>();
                pkList.add(new DBCol<Integer>(this.entry.getId(), "id"));
                dbUnit.update(pkList, myList, DBUnit.getTablename());
                updateEntry();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dialogStage.close();
    }

    private void addFieldtoList(List<DBCol> myList, TextField text, String colName){
        if(!text.getText().isEmpty())
            myList.add(new DBCol<String>(text.getText(), colName));
    }
    private void addFieldtoList(List<DBCol> myList, TextArea text, String colName){
        if(!text.getText().isEmpty())
            myList.add(new DBCol<String>(text.getText(), colName));
    }
    private void addFieldtoList(List<DBCol> myList,CheckBox box, String colName){
        if(box.isSelected())
            myList.add(new DBCol<Boolean>(true, colName));
    }
    private void addFieldtoList(List<DBCol> myList,DatePicker date, String colName){
        if(date.getValue() != null)
            myList.add(new DBCol<LocalDate>(date.getValue(), colName));
    }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public boolean handleCancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText(null);
        alert.setContentText("Your changes will not be saved. Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dialogStage.close();
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void handlePrint() { //todo: make it reshape without showing it
        AnchorPane layout = Main.getFormLayout();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        double scaleX =  pageLayout.getPrintableWidth()/layout.getBoundsInParent().getWidth();
        double scaleY =  pageLayout.getPrintableHeight()/layout.getBoundsInParent().getHeight();
        layout.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(dialogStage.getOwner());
        if (job != null) {
            boolean success = job.printPage(pageLayout, layout);
            if (success) {
                job.endJob();
            }
        }
        layout.getTransforms().remove(layout.getTransforms().size() -1);

    }


}
