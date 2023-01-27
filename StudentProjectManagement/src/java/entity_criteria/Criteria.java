/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_criteria;

/**
 *
 * @author admin
 */
public class Criteria {

    private int criteriaId;
    private int iterationId;
    private String criteriaName;
    private int evalWeight;
    private int maxLoc;
    private boolean status;
    private boolean isTeamEval;
    private String description;

    public Criteria() {
    }

    public Criteria(int criteriaId, int iterationId, String criteriaName, int evalWeight, int maxLoc, boolean status, boolean isTeamEval, String description) {
        this.criteriaId = criteriaId;
        this.iterationId = iterationId;
        this.criteriaName = criteriaName;
        this.evalWeight = evalWeight;
        this.maxLoc = maxLoc;
        this.status = status;
        this.isTeamEval = isTeamEval;
        this.description = description;
    }

    
    public int getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(int criteriaId) {
        this.criteriaId = criteriaId;
    }

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(int iterationId) {
        this.iterationId = iterationId;
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

    public int getMaxLoc() {
        return maxLoc;
    }

    public void setMaxLoc(int maxLoc) {
        this.maxLoc = maxLoc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIsTeamEval() {
        return isTeamEval;
    }

    public void setIsTeamEval(boolean isTeamEval) {
        this.isTeamEval = isTeamEval;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
