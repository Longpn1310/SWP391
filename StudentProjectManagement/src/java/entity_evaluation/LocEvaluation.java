/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation;

/**
 *
 * @author ADMIN
 */
public class LocEvaluation {
    private int id;
    private int functionId;
    private int milestoneId;
    private int complexityId;
    private int qualityId;
    private int convertedLoc;
    private boolean isLateSubmit;
    private String comment;
    private int newMilestoneId;
    private int newComplexityId;
    private int newQualityId;
    private int newConvertedLoc;

    public LocEvaluation() {
    }

    public LocEvaluation(int id, int functionId, int milestoneId, int complexityId, int qualityId, int convertedLoc, boolean isLateSubmit, String comment, int newMilestoneId, int newComplexityId, int newQualityId, int newConvertedLoc) {
        this.id = id;
        this.functionId = functionId;
        this.milestoneId = milestoneId;
        this.complexityId = complexityId;
        this.qualityId = qualityId;
        this.convertedLoc = convertedLoc;
        this.isLateSubmit = isLateSubmit;
        this.comment = comment;
        this.newMilestoneId = newMilestoneId;
        this.newComplexityId = newComplexityId;
        this.newQualityId = newQualityId;
        this.newConvertedLoc = newConvertedLoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getComplexityId() {
        return complexityId;
    }

    public void setComplexityId(int complexityId) {
        this.complexityId = complexityId;
    }

    public int getQualityId() {
        return qualityId;
    }

    public void setQualityId(int qualityId) {
        this.qualityId = qualityId;
    }

    public int getConvertedLoc() {
        return convertedLoc;
    }

    public void setConvertedLoc(int convertedLoc) {
        this.convertedLoc = convertedLoc;
    }

    public boolean isIsLateSubmit() {
        return isLateSubmit;
    }

    public void setIsLateSubmit(boolean isLateSubmit) {
        this.isLateSubmit = isLateSubmit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNewMilestoneId() {
        return newMilestoneId;
    }

    public void setNewMilestoneId(int newMilestoneId) {
        this.newMilestoneId = newMilestoneId;
    }

    public int getNewComplexityId() {
        return newComplexityId;
    }

    public void setNewComplexityId(int newComplexityId) {
        this.newComplexityId = newComplexityId;
    }

    public int getNewQualityId() {
        return newQualityId;
    }

    public void setNewQualityId(int newQualityId) {
        this.newQualityId = newQualityId;
    }

    public int getNewConvertedLoc() {
        return newConvertedLoc;
    }

    public void setNewConvertedLoc(int newConvertedLoc) {
        this.newConvertedLoc = newConvertedLoc;
    }
    
}
