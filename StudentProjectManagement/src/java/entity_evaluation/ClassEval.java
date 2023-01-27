/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ClassEval {

    private int userId;
    private int classId;
    private int teamId;
    private String teamName;
    private String rollNumber;
    private String fullName;
    private float onGoing;
    private float total;
    private List<IterationEvaluation> iterationEvaluations;

    public ClassEval() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getOnGoing() {
        return onGoing;
    }

    public void setOnGoing(float onGoing) {
        this.onGoing = onGoing;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<IterationEvaluation> getIterationEvaluations() {
        return iterationEvaluations;
    }

    public void setIterationEvaluations(List<IterationEvaluation> iterationEvaluations) {
        this.iterationEvaluations = iterationEvaluations;
    }

}
