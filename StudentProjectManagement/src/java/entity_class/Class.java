/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class;

/**
 *
 * @author Administrator
 */
public class Class {

    private int classId;
    private String classCode;    
    private int trainerId;
    private String trainerName;
    private int subjectId;
    private String subjectName;
    private int termId;
    private String termtitle;
    private boolean block5;
    private boolean status;    

    public Class() {
    }

    public Class(int classId, String classCode, String subjectName, int trainerId, int subjectId, String classTerm, boolean block5, boolean status, String trainerName) {
        this.classId = classId;
        this.classCode = classCode;
        this.subjectName = subjectName;
        this.trainerId = trainerId;
        this.subjectId = subjectId;
        this.termtitle = classTerm;
        this.block5 = block5;
        this.status = status;
        this.trainerName = trainerName;
    }

    public Class(int classId, String classCode, String subjectName, String classTerm, boolean block5, boolean status, String trainerName) {
        this.classId = classId;
        this.classCode = classCode;
        this.subjectName = subjectName;
        this.termtitle = classTerm;
        this.block5 = block5;
        this.status = status;
        this.trainerName = trainerName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTermtitle() {
        return termtitle;
    }

    public void setTermtitle(String termtitle) {
        this.termtitle = termtitle;
    }    

    public boolean isBlock5() {
        return block5;
    }

    public void setBlock5(boolean block5) {
        this.block5 = block5;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }    

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

}
