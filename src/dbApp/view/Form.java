package dbApp.view;

import dbApp.Main;
import dbApp.database.DBCol;
import dbApp.database.DBUnit;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class Form<T extends TableEntry> {
    protected DBUnit dbUnit;
    protected Stage dialogStage;
    T entry;

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
    abstract public void setEntry(T entry);
    abstract public void updateEntry();
    @FXML
    abstract public void handleSave();

    public void saveToDB(List<DBCol> myList){
        try {
            dbUnit.connect();
            if(entry == null){
                dbUnit.insert(myList);
            }else{
                List<DBCol> pkList = new ArrayList<>();
                pkList.add(new DBCol<Integer>(entry.getId(), "id"));
                dbUnit.update(pkList, myList);
                updateEntry();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void addFieldtoList(List<DBCol> myList, TextField text, String colName){
        if(!text.getText().isEmpty())
            myList.add(new DBCol<String>(text.getText(), colName));
    }
    protected void addFieldtoList(List<DBCol> myList, TextArea text, String colName){
        if(!text.getText().isEmpty())
            myList.add(new DBCol<String>(text.getText(), colName));
    }
    protected void addFieldtoList(List<DBCol> myList, CheckBox box, String colName){
        if(box.isSelected())
            myList.add(new DBCol<Boolean>(true, colName));
    }
    protected void addFieldtoList(List<DBCol> myList, DatePicker date, String colName){
        if(date.getValue() != null)
            myList.add(new DBCol<LocalDate>(date.getValue(), colName));
    }
    protected void addFieldtoList(List<DBCol> myList, Hyperlink link, String colName){
        if(link.getText() != null)
            myList.add(new DBCol<String>(link.getText(), colName));
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
        AnchorPane layout = Search.getFormLayout();
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
