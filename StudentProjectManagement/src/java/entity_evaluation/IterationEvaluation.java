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
public class IterationEvaluation {

    private int evaluationId;
    private int milestoneId;
    private int teamId;
    private String teamName;
    private int classId;
    private int userId;
    private String fullName;
    private String rollNumber;
    private float bonus;
    private float totalGrade;
    private String comment;
    private int iterationId;
    private String iterationName;
    private float teamEvaluation;
    private float memberEvaluation;
    private int locEvaluation;

    public IterationEvaluation() {
    }    

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(float totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(int iterationId) {
        this.iterationId = iterationId;
    }

    public String getIterationName() {
        return iterationName;
    }

    public void setIterationName(String iterationName) {
        this.iterationName = iterationName;
    }

    public float getTeamEvaluation() {
        return teamEvaluation;
    }

    public void setTeamEvaluation(float teamEvaluation) {
        this.teamEvaluation = teamEvaluation;
    }

    public float getMemberEvaluation() {
        return memberEvaluation;
    }

    public void setMemberEvaluation(float memberEvaluation) {
        this.memberEvaluation = memberEvaluation;
    }    

    public int getLocEvaluation() {
        return locEvaluation;
    }

    public void setLocEvaluation(int locEvaluation) {
        this.locEvaluation = locEvaluation;
    }
        
}
