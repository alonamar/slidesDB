package dbApp.view;

import dbApp.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private TextField blocksText;

    @FXML
    private TextField slidesText;

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

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    private Stage dialogStage;
    private TableEntry entry;
    private boolean saveClicked = false;


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
        slidesText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    slidesText.setText(oldValue);
                }
            }
        });
        blocksText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    blocksText.setText(oldValue);
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
            blocksText.setText(entry.getBlocksString());
            slidesText.setText(entry.getSlidesString());
        }else {
            caseText.clear();
            osuText.clear();
            pNameText.clear();
            contactText.clear();
            addressText.clear();
            dateText.getEditor().clear();
            contactPhoneText.clear();
            contactFaxPhone.clear();
            emailText.clear();
            siteText.clear();
            clinicalText.clear();
            featuresText.clear();
            picturesBox.setSelected(false);
            reportableBox.setSelected(false);
            dxBox.setSelected(false);
            seminarBox.setSelected(false);
            receivedText.clear();
            keptText.clear();
            paxitText.clear();
            otherText.clear();
            diseaseText.clear();
            chargeBox.setSelected(false);
            followText.clear();
            diagnosisText.clear();
            ageText.clear();
            sexSelect.setValue("");
            blocksText.clear();
            slidesText.clear();
        }
    }

    /**
     * Returns true if the user clicked Save, false otherwise.
     *
     * @return
     */
    public boolean isSavedClicked() {
        return this.saveClicked;
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {// todo : add alert
        System.out.println("Asdasd1111");
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() { // todo : add alert
        System.out.println("Asdasd2222");
        dialogStage.close();
    }
}
