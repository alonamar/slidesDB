package dbApp.view;

import javafx.beans.property.*;

public class TableEntryBlock extends TableEntry {
    private StringProperty organ;
    private StringProperty boxNum;
    private StringProperty caseNum;
    private StringProperty origNum;
    private StringProperty otherNum;
    private StringProperty diagnosis;
    private StringProperty specialFeatures;
    private SimpleObjectProperty<Double> numOfBlocks;
    private SimpleObjectProperty<Double> numOfSlides;
    private StringProperty paperwork;

    public TableEntryBlock(){

    }

    public TableEntryBlock(Integer id, String organ, String boxNum, String caseNum, String origNum,
                           String otherNum, String diagnosis, String specialFeatures, Double numOfBlocks,
                           Double numOfSlides, String paperwork) {
        this.id = new SimpleIntegerProperty(id);
        this.organ = new SimpleStringProperty(organ);
        this.boxNum = new SimpleStringProperty(boxNum);
        this.caseNum = new SimpleStringProperty(caseNum);
        this.origNum = new SimpleStringProperty(origNum);
        this.otherNum = new SimpleStringProperty(otherNum);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.specialFeatures = new SimpleStringProperty(specialFeatures);
        this.numOfBlocks = new SimpleObjectProperty<Double>(numOfBlocks);
        this.numOfSlides = new SimpleObjectProperty<Double>(numOfSlides);
        this.paperwork = new SimpleStringProperty(paperwork);
    }

    public String getOrgan() {
        if(organ == null  || organ.get() == null){
            return "";
        }
        return organ.get();
    }
    public StringProperty organProperty() {
        return organ;
    }
    public void setOrgan(String organ) {
        this.organ.set(organ);
    }


    public String getBoxNum() {
        if(boxNum == null  || boxNum.get() == null){
            return "";
        }
        return boxNum.get();
    }
    public StringProperty boxNumProperty() {
        return boxNum;
    }
    public void setBoxNum(String boxNum) {
        this.boxNum.set(boxNum);
    }

    public String getCaseNum() {
        if(caseNum == null  || caseNum.get() == null){
            return "";
        }
        return caseNum.get();
    }
    public StringProperty caseNumProperty() {
        return caseNum;
    }
    public void setcaseNum(String caseNum) {
        this.caseNum.set(caseNum);
    }

    public String getOrigNum() {
        if(origNum == null  || origNum.get() == null){
            return "";
        }
        return origNum.get();
    }
    public StringProperty origNumProperty() {
        return origNum;
    }
    public void setOrigNum(String origNum) {
        this.origNum.set(origNum);
    }

    public String getOtherNum() {
        if(otherNum == null  || otherNum.get() == null){
            return "";
        }
        return otherNum.get();
    }
    public StringProperty otherNumProperty() {
        return otherNum;
    }
    public void setOtherNum(String otherNum) {
        this.otherNum.set(otherNum);
    }

    public String getDiagnosis() {
        if(diagnosis == null  || diagnosis.get() == null){
            return "";
        }
        return diagnosis.get();
    }
    public StringProperty diagnosisProperty() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public String getSpecialFeatures() {
        if(specialFeatures == null  || specialFeatures.get() == null){
            return "";
        }
        return specialFeatures.get();
    }
    public StringProperty specialFeaturesProperty() {
        return specialFeatures;
    }
    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures.set(specialFeatures);
    }

    public String getNumOfBlocksString() {
        if(numOfBlocks.getValue() == null  || numOfBlocks.get() == null){
            return "";
        }
        return Double.toString(numOfBlocks.get());
    }
    public Double getNumOfBlocks() {
        return numOfBlocks.get();
    }
    public SimpleObjectProperty<Double> numOfBlocksProperty() {
        return numOfBlocks;
    }
    public void setNumOfBlocks(Double numOfBlocks) {
        this.numOfBlocks.set(numOfBlocks);
    }

    public String getNumOfSlidesString() {
        if(numOfSlides.getValue() == null  || numOfSlides.get() == null){
            return "";
        }
        return Double.toString(numOfSlides.get());
    }
    public Double getNumOfSlides() {
        return numOfSlides.get();
    }
    public SimpleObjectProperty<Double> numOfSlidesProperty() {
        return numOfSlides;
    }
    public void setNumOfSlides(Double age) {
        this.numOfSlides.set(age);
    }

    public String getPaperwork() {
        if(paperwork == null  || paperwork.get() == null){
            return "";
        }
        return paperwork.get();
    }
    public StringProperty paperworkProperty() {
        return paperwork;
    }
    public void setPaperwork(String paperwork) {
        this.paperwork.set(paperwork);
    }
}
