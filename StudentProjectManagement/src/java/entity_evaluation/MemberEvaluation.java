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
public class MemberEvaluation {

    private int id;
    private int evaluationId;
    private int criteriaId;
    private String criteriaName;
    private int evalWeight;
    private int totalLoc;
    private float grade;
    private String comment;

    public MemberEvaluation() {
    }

    public MemberEvaluation(int id, int evaluationId, int criteriaId, int totalLoc, float grade, String comment) {
        this.id = id;
        this.evaluationId = evaluationId;
        this.criteriaId = criteriaId;
        this.totalLoc = totalLoc;
        this.grade = grade;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(int criteriaId) {
        this.criteriaId = criteriaId;
    }

    public int getTotalLoc() {
        return totalLoc;
    }

    public void setTotalLoc(int totalLoc) {
        this.totalLoc = totalLoc;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public int getEvalWeight() {
        return evalWeight;
    }

    public void setEvalWeight(int evalWeight) {
        this.evalWeight = evalWeight;
    }

}
