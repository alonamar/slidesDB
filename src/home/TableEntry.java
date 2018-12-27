package home;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.LocalDate;
import javafx.scene.control.CheckBox;

public class TableEntry {
    private CheckBox old_form;
    private Integer id;
    private String osu;
    private String caseNo;
    private String sex;
    private Double age;
    private String site;
    private String organsystem;
    private String diagnosis;
    private String diseaseprocess;
    private String otherbiopsies;
    private Integer slides;
    private String ihc;
    private Integer unstainedsections;
    private Boolean em;
    private Boolean wt;
    private Integer blocks;
    private String specialfeatures;
    private String paxit;
    private Boolean photography;
    private Boolean seminarcase;
    private Boolean reportable_newentity;
    private Boolean uncertaindiagnosis;
    private String clinicalinformation;
    private String followup;
    private String references;
    private String address;
    private String patientname;
    private String specialstains;
    private String contactphone;
    private String contactfax;
    private LocalDate dateaccessioned;
    private String email;
    private String materialreceived;
    private String materialkept;
    private Boolean charge;
    private String contact;

    public TableEntry(CheckBox old_form, Integer id, String osu, String caseNo, String sex, Double age, String site, String organsystem, String diagnosis, String diseaseprocess, String otherbiopsies, Integer slides, String ihc, Integer unstainedsections, Boolean em, Boolean wt, Integer blocks, String specialfeatures, String paxit, Boolean photography, Boolean seminarcase, Boolean reportable_newentity, Boolean uncertaindiagnosis, String clinicalinformation, String followup, String references, String address, String patientname, String specialstains, String contactphone, String contactfax, LocalDate dateaccessioned, String email, String materialreceived, String materialkept, Boolean charge, String contact) {
        this.old_form = old_form;
        this.id = id;
        this.osu = osu;
        this.caseNo = caseNo;
        this.sex = sex;
        this.age = age;
        this.site = site;
        this.organsystem = organsystem;
        this.diagnosis = diagnosis;
        this.diseaseprocess = diseaseprocess;
        this.otherbiopsies = otherbiopsies;
        this.slides = slides;
        this.ihc = ihc;
        this.unstainedsections = unstainedsections;
        this.em = em;
        this.wt = wt;
        this.blocks = blocks;
        this.specialfeatures = specialfeatures;
        this.paxit = paxit;
        this.photography = photography;
        this.seminarcase = seminarcase;
        this.reportable_newentity = reportable_newentity;
        this.uncertaindiagnosis = uncertaindiagnosis;
        this.clinicalinformation = clinicalinformation;
        this.followup = followup;
        this.references = references;
        this.address = address;
        this.patientname = patientname;
        this.specialstains = specialstains;
        this.contactphone = contactphone;
        this.contactfax = contactfax;
        this.dateaccessioned = dateaccessioned;
        this.email = email;
        this.materialreceived = materialreceived;
        this.materialkept = materialkept;
        this.charge = charge;
        this.contact = contact;
    }

    public CheckBox getOld_form() {
        return old_form;
    }

    public void setOld_form(CheckBox old_form) {
        this.old_form = old_form;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOsu() {

        return osu;
    }

    public void setOsu(String osu) {
        this.osu = osu;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOrgansystem() {
        return organsystem;
    }

    public void setOrgansystem(String organsystem) {
        this.organsystem = organsystem;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiseaseprocess() {
        return diseaseprocess;
    }

    public void setDiseaseprocess(String diseaseprocess) {
        this.diseaseprocess = diseaseprocess;
    }

    public String getOtherbiopsies() {
        return otherbiopsies;
    }

    public void setOtherbiopsies(String otherbiopsies) {
        this.otherbiopsies = otherbiopsies;
    }

    public Integer getSlides() {
        return slides;
    }

    public void setSlides(Integer slides) {
        this.slides = slides;
    }

    public String getIhc() {
        return ihc;
    }

    public void setIhc(String ihc) {
        this.ihc = ihc;
    }

    public Integer getUnstainedsections() {
        return unstainedsections;
    }

    public void setUnstainedsections(Integer unstainedsections) {
        this.unstainedsections = unstainedsections;
    }

    public Boolean getEm() {
        return em;
    }

    public void setEm(Boolean em) {
        this.em = em;
    }

    public Boolean getWt() {
        return wt;
    }

    public void setWt(Boolean wt) {
        this.wt = wt;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public String getSpecialfeatures() {
        return specialfeatures;
    }

    public void setSpecialfeatures(String specialfeatures) {
        this.specialfeatures = specialfeatures;
    }

    public String getPaxit() {
        return paxit;
    }

    public void setPaxit(String paxit) {
        this.paxit = paxit;
    }

    public Boolean getPhotography() {
        return photography;
    }

    public void setPhotography(Boolean photography) {
        this.photography = photography;
    }

    public Boolean getSeminarcase() {
        return seminarcase;
    }

    public void setSeminarcase(Boolean seminarcase) {
        this.seminarcase = seminarcase;
    }

    public Boolean getReportable_newentity() {
        return reportable_newentity;
    }

    public void setReportable_newentity(Boolean reportable_newentity) {
        this.reportable_newentity = reportable_newentity;
    }

    public Boolean getUncertaindiagnosis() {
        return uncertaindiagnosis;
    }

    public void setUncertaindiagnosis(Boolean uncertaindiagnosis) {
        this.uncertaindiagnosis = uncertaindiagnosis;
    }

    public String getClinicalinformation() {
        return clinicalinformation;
    }

    public void setClinicalinformation(String clinicalinformation) {
        this.clinicalinformation = clinicalinformation;
    }

    public String getFollowup() {
        return followup;
    }

    public void setFollowup(String followup) {
        this.followup = followup;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getSpecialstains() {
        return specialstains;
    }

    public void setSpecialstains(String specialstains) {
        this.specialstains = specialstains;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getContactfax() {
        return contactfax;
    }

    public void setContactfax(String contactfax) {
        this.contactfax = contactfax;
    }

    public LocalDate getDateaccessioned() {
        return dateaccessioned;
    }

    public void setDateaccessioned(LocalDate dateaccessioned) {
        this.dateaccessioned = dateaccessioned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaterialreceived() {
        return materialreceived;
    }

    public void setMaterialreceived(String materialreceived) {
        this.materialreceived = materialreceived;
    }

    public String getMaterialkept() {
        return materialkept;
    }

    public void setMaterialkept(String materialkept) {
        this.materialkept = materialkept;
    }

    public Boolean getCharge() {
        return charge;
    }

    public void setCharge(Boolean charge) {
        this.charge = charge;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
