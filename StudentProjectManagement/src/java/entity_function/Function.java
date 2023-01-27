/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_function;

/**
 *
 * @author trung
 */
public class Function {
    private int functionId;
    private int teamId;
    private String title;
    private String feature;
    private int priority;
    private boolean isClosed;
    private int statusId;
    private int submitStatus;
    private String description;

    public Function() {
    }

    public Function(int functionId, int teamId, String title, String feature, int priority, boolean isClosed, int statusId, int submitStatus, String description) {
        this.functionId = functionId;
        this.teamId = teamId;
        this.title = title;
        this.feature = feature;
        this.priority = priority;
        this.isClosed = isClosed;
        this.statusId = statusId;
        this.submitStatus = submitStatus;
        this.description = description;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(int submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
