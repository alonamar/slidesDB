package dbApp.view;

import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.scene.control.CheckBox;

public class TableEntry {
    private CheckBox old_form;
    private IntegerProperty id;
    private StringProperty osu;
    private StringProperty caseNo;
    private StringProperty sex;
    private SimpleObjectProperty<Double> age;
    private StringProperty site;
    private StringProperty organsystem; // ?
    private StringProperty diagnosis;
    private StringProperty diseaseprocess; // clinical information
    private StringProperty otherbiopsies;
    private IntegerProperty slides;
    private StringProperty ihc; // ?
    private IntegerProperty unstainedsections; // ?
    private BooleanProperty em; // ?
    private BooleanProperty wt; // ?
    private IntegerProperty blocks;
    private StringProperty specialfeatures;
    private StringProperty paxit;
    private BooleanProperty photography;
    private BooleanProperty seminarcase;
    private BooleanProperty reportable_newentity;
    private BooleanProperty uncertaindiagnosis;
    private StringProperty clinicalinformation;
    private StringProperty followup;
    private StringProperty references; // ?
    private StringProperty address;
    private StringProperty patientname;
    private StringProperty specialstains; // ?
    private StringProperty contactphone;
    private StringProperty contactfax;
    private ObjectProperty<LocalDate> dateaccessioned;
    private StringProperty email;
    private StringProperty materialreceived;
    private StringProperty materialkept;
    private BooleanProperty charge;
    private StringProperty contact;

    public TableEntry(){

    }

    public TableEntry(CheckBox old_form, Integer id, String osu, String caseNo, String sex,
                      Double age, String site, String diagnosis, String diseaseprocess,
                      String otherbiopsies, Integer slides, Integer blocks, String specialfeatures, String paxit, Boolean photography,
                      Boolean seminarcase, Boolean reportable_newentity, Boolean uncertaindiagnosis,
                      String clinicalinformation, String followup, String address,
                      String patientname, String contactphone, String contactfax,
                      LocalDate dateaccessioned, String email, String materialreceived, String materialkept,
                      Boolean charge, String contact) {
        this.old_form = old_form;
        this.id = new SimpleIntegerProperty(id);
        this.osu = new SimpleStringProperty(osu);
        this.caseNo = new SimpleStringProperty(caseNo);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleObjectProperty<Double>(age);
        this.site = new SimpleStringProperty(site);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.diseaseprocess = new SimpleStringProperty(diseaseprocess);
        this.otherbiopsies = new SimpleStringProperty(otherbiopsies);
        this.slides = new SimpleIntegerProperty(slides);
        this.blocks = new SimpleIntegerProperty(blocks);
        this.specialfeatures = new SimpleStringProperty(specialfeatures);
        this.paxit = new SimpleStringProperty(paxit);
        this.photography = new SimpleBooleanProperty(photography);
        this.seminarcase = new SimpleBooleanProperty(seminarcase);
        this.reportable_newentity = new SimpleBooleanProperty(reportable_newentity);
        this.uncertaindiagnosis = new SimpleBooleanProperty(uncertaindiagnosis);
        this.clinicalinformation = new SimpleStringProperty(clinicalinformation);
        this.followup = new SimpleStringProperty(followup);
        this.address = new SimpleStringProperty(address);
        this.patientname = new SimpleStringProperty(patientname);
        this.contactphone = new SimpleStringProperty(contactphone);
        this.contactfax = new SimpleStringProperty(contactfax);
        this.dateaccessioned = new SimpleObjectProperty<>(dateaccessioned);
        this.email = new SimpleStringProperty(email);
        this.materialreceived = new SimpleStringProperty(materialreceived);
        this.materialkept = new SimpleStringProperty(materialkept);
        this.charge = new SimpleBooleanProperty(charge);
        this.contact = new SimpleStringProperty(contact);
    }

    public TableEntry(String caseNo, String sex, Double age, String site, String diagnosis,
                      Integer slides, Integer blocks, LocalDate dateaccessioned) {
        this.caseNo = new SimpleStringProperty(caseNo);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleObjectProperty<Double>(age);
        this.site = new SimpleStringProperty(site);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.slides = new SimpleIntegerProperty(slides);
        this.blocks = new SimpleIntegerProperty(blocks);
        this.dateaccessioned = new SimpleObjectProperty<>(dateaccessioned);
    }

    public String getCaseNo() {
        if(caseNo == null){
            return "";
        }
        return caseNo.get();
    }

    public StringProperty caseNoProperty() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo.set(caseNo);
    }

    public String getSex() {
        if(sex == null){
            return "";
        }
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getAgeString() {
        if(age.getValue() == null){
            return "";
        }
        return Double.toString(age.get());
    }

    public double getAge() {
        return age.get();
    }

    public SimpleObjectProperty<Double> ageProperty() {
        return age;
    }

    public void setAge(double age) {
        this.age.set(age);
    }

    public String getSite() {
        if(site == null){
            return "";
        }
        return site.get();
    }

    public StringProperty siteProperty() {
        return site;
    }

    public void setSite(String site) {
        this.site.set(site);
    }

    public String getDiagnosis() {
        if(diagnosis == null){
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

    public String getSlidesString() {
        if(slides.getValue() == null){
            return "";
        }
        return Integer.toString(slides.get());
    }

    public int getSlides() {
        return slides.get();
    }

    public IntegerProperty slidesProperty() {
        return slides;
    }

    public void setSlides(int slides) {
        this.slides.set(slides);
    }

    public String getBlocksString() {
        if(blocks.getValue() == null){
            return "";
        }
        return Integer.toString(blocks.get());
    }

    public int getBlocks() {
        return blocks.get();
    }

    public IntegerProperty blocksProperty() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks.set(blocks);
    }

    public String getDateaccessionedString() {
        if(dateaccessioned == null){
            return "";
        }
        return dateaccessioned.get().toString();
    }

    public LocalDate getDateaccessioned() {
        return dateaccessioned.get();
    }

    public ObjectProperty<LocalDate> dateaccessionedProperty() {
        return dateaccessioned;
    }

    public void setDateaccessioned(LocalDate dateaccessioned) {
        this.dateaccessioned.set(dateaccessioned);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getOsu() {
        if(osu == null){
            return "";
        }
        return osu.get();
    }

    public StringProperty osuProperty() {
        return osu;
    }

    public void setOsu(String osu) {
        this.osu.set(osu);
    }

    public String getOrgansystem() {
        return organsystem.get();
    }

    public StringProperty organsystemProperty() {
        return organsystem;
    }

    public void setOrgansystem(String organsystem) {
        this.organsystem.set(organsystem);
    }

    public String getDiseaseprocess() {
        if(diseaseprocess == null){
            return "";
        }
        return diseaseprocess.get();
    }

    public StringProperty diseaseprocessProperty() {
        return diseaseprocess;
    }

    public void setDiseaseprocess(String diseaseprocess) {
        this.diseaseprocess.set(diseaseprocess);
    }

    public String getOtherbiopsies() {
        if(otherbiopsies == null){
            return "";
        }
        return otherbiopsies.get();
    }

    public StringProperty otherbiopsiesProperty() {
        return otherbiopsies;
    }

    public void setOtherbiopsies(String otherbiopsies) {
        this.otherbiopsies.set(otherbiopsies);
    }

    public String getIhc() {
        return ihc.get();
    }

    public StringProperty ihcProperty() {
        return ihc;
    }

    public void setIhc(String ihc) {
        this.ihc.set(ihc);
    }

    public int getUnstainedsections() {
        return unstainedsections.get();
    }

    public IntegerProperty unstainedsectionsProperty() {
        return unstainedsections;
    }

    public void setUnstainedsections(int unstainedsections) {
        this.unstainedsections.set(unstainedsections);
    }

    public boolean isEm() {
        return em.get();
    }

    public BooleanProperty emProperty() {
        return em;
    }

    public void setEm(boolean em) {
        this.em.set(em);
    }

    public boolean isWt() {
        return wt.get();
    }

    public BooleanProperty wtProperty() {
        return wt;
    }

    public void setWt(boolean wt) {
        this.wt.set(wt);
    }

    public String getSpecialfeatures() {
        if(specialfeatures == null){
            return "";
        }
        return specialfeatures.get();
    }

    public StringProperty specialfeaturesProperty() {
        return specialfeatures;
    }

    public void setSpecialfeatures(String specialfeatures) {
        this.specialfeatures.set(specialfeatures);
    }

    public String getPaxit() {
        if(paxit == null){
            return "";
        }
        return paxit.get();
    }

    public StringProperty paxitProperty() {
        return paxit;
    }

    public void setPaxit(String paxit) {
        this.paxit.set(paxit);
    }

    public boolean isPhotography() {
        if(photography == null){
            return false;
        }
        return photography.get();
    }

    public BooleanProperty photographyProperty() {
        return photography;
    }

    public void setPhotography(boolean photography) {
        this.photography.set(photography);
    }

    public boolean isSeminarcase() {
        if(seminarcase == null){
            return false;
        }
        return seminarcase.get();
    }

    public BooleanProperty seminarcaseProperty() {
        return seminarcase;
    }

    public void setSeminarcase(boolean seminarcase) {
        this.seminarcase.set(seminarcase);
    }

    public boolean isReportable_newentity() {
        if(reportable_newentity == null){
            return false;
        }
        return reportable_newentity.get();
    }

    public BooleanProperty reportable_newentityProperty() {
        return reportable_newentity;
    }

    public void setReportable_newentity(boolean reportable_newentity) {
        this.reportable_newentity.set(reportable_newentity);
    }

    public boolean isUncertaindiagnosis() {
        if(uncertaindiagnosis == null){
            return false;
        }
        return uncertaindiagnosis.get();
    }

    public BooleanProperty uncertaindiagnosisProperty() {
        return uncertaindiagnosis;
    }

    public void setUncertaindiagnosis(boolean uncertaindiagnosis) {
        this.uncertaindiagnosis.set(uncertaindiagnosis);
    }

    public String getClinicalinformation() {
        if(clinicalinformation == null){
            return "";
        }
        return clinicalinformation.get();
    }

    public StringProperty clinicalinformationProperty() {
        return clinicalinformation;
    }

    public void setClinicalinformation(String clinicalinformation) {
        this.clinicalinformation.set(clinicalinformation);
    }

    public String getFollowup() {
        if(followup == null){
            return "";
        }
        return followup.get();
    }

    public StringProperty followupProperty() {
        return followup;
    }

    public void setFollowup(String followup) {
        this.followup.set(followup);
    }

    public String getReferences() {
        return references.get();
    }

    public StringProperty referencesProperty() {
        return references;
    }

    public void setReferences(String references) {
        this.references.set(references);
    }

    public String getPatientname() {
        if(patientname == null){
            return "";
        }
        return patientname.get();
    }

    public StringProperty patientnameProperty() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname.set(patientname);
    }

    public String getAddress() {
        if(address == null){
            return "";
        }
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getContact() {
        if(contact == null){
            return "";
        }
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getContactphone() {
        if(contactphone == null){
            return "";
        }
        return contactphone.get();
    }

    public StringProperty contactphoneProperty() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone.set(contactphone);
    }

    public String getContactfax() {
        if(contactfax == null){
            return "";
        }
        return contactfax.get();
    }

    public StringProperty contactfaxProperty() {
        return contactfax;
    }

    public void setContactfax(String contactfax) {
        this.contactfax.set(contactfax);
    }

    public String getEmail() {
        if(email == null){
            return "";
        }
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getMaterialreceived() {
        if(materialreceived == null){
            return "";
        }
        return materialreceived.get();
    }

    public StringProperty materialreceivedProperty() {
        return materialreceived;
    }

    public void setMaterialreceived(String materialreceived) {
        this.materialreceived.set(materialreceived);
    }

    public String getMaterialkept() {
        if(materialkept == null){
            return "";
        }
        return materialkept.get();
    }

    public StringProperty materialkeptProperty() {
        return materialkept;
    }

    public void setMaterialkept(String materialkept) {
        this.materialkept.set(materialkept);
    }

    public boolean isCharge() {
        if(charge == null){
            return false;
        }
        return charge.get();
    }

    public BooleanProperty chargeProperty() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge.set(charge);
    }
}
