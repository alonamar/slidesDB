package dbApp.view;

import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.DirectoryChooser;

public class BlockFormController extends Form<TableEntryBlock> {

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
    private Hyperlink paperworkText = new Hyperlink();


    public BlockFormController(){
        dbUnit = new DBUnit("blocks");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
//        // define the limitation of each text field
//        numOfBlocksText.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (!newValue.matches("\\d+")) {
//                    numOfBlocksText.setText(oldValue);
//                }
//            }
//        });
//        numOfSlidesText.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (!newValue.matches("\\d+")) {
//                    numOfSlidesText.setText(oldValue);
//                }
//            }
//        });
        setEntry(this.entry);
    }

    /**
     * Sets the entry to be edited in the dialog.
     *
     * @param entry
     */
    public void setEntry(TableEntryBlock entry) {
        if(entry != null) {
            this.entry = entry;
            organText.setText(entry.getOrgan());
            boxNumText.setText(entry.getBoxNum());
            caseNumText.setText(entry.getCaseNum());
            origNumText.setText(entry.getOrigNum());
            otherNumText.setText(entry.getOtherNum());
            diagnosisText.setText(entry.getDiagnosis());
            specialFeaturesText.setText(entry.getSpecialFeatures());
            numOfBlocksText.setText(entry.getNumOfBlocksString());
            numOfSlidesText.setText(entry.getNumOfSlidesString());
            paperworkText.setText(entry.getPaperwork());
        }
    }

    public void updateEntry() {
        entry.setOrgan(organText.getText());
        entry.setBoxNum(boxNumText.getText());
        entry.setcaseNum(caseNumText.getText());
        entry.setOrigNum(origNumText.getText());
        entry.setOtherNum(otherNumText.getText());
        entry.setDiagnosis(diagnosisText.getText());
        entry.setSpecialFeatures(specialFeaturesText.getText());
        Double number = numOfBlocksText.getText().isEmpty() ? null : Double.parseDouble(numOfBlocksText.getText());
        entry.setNumOfBlocks(number);
        number = numOfSlidesText.getText().isEmpty() ? null : Double.parseDouble(numOfSlidesText.getText());
        entry.setNumOfSlides(number);
        entry.setPaperwork(paperworkText.getText());
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    public void handleSave() {
        List<DBCol> myList = new ArrayList<>();
        addFieldtoList(myList, organText, "organ");
        addFieldtoList(myList, boxNumText, "boxNum");
        addFieldtoList(myList, caseNumText, "caseNum");
        addFieldtoList(myList, origNumText, "origNum");
        addFieldtoList(myList, otherNumText, "otherNum");
        addFieldtoList(myList, diagnosisText, "diagnosis");
        addFieldtoList(myList, specialFeaturesText, "specialFeatures");
        addFieldtoList(myList, numOfBlocksText, "numOfBlocks");
        addFieldtoList(myList, numOfSlidesText, "numOfSlides");
        addFieldtoList(myList, paperworkText, "paperwork");
        saveToDB(myList);
        dialogStage.close();
    }

    @FXML
    public void handleLink() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File dir = dirChooser.showDialog(dialogStage);
        System.out.println(dir.getAbsolutePath());
        paperworkText.setText(dir.getAbsolutePath());
    }

    @FXML
    public void handlePaperwork() {
        try {
            Desktop.getDesktop().open(new File(paperworkText.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
