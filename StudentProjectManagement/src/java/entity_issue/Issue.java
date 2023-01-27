/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_issue;

import java.util.Date;

/**
 *
 * @author trung
 */
public class Issue {
    private int issueId;
    private String title;
    private String typeId;
    private String functionIds;
    private int teamId;
    private int milestoneId;
    private int assignerId;
    private int assigneeId;
    private boolean isClosed;
    private String dueDate;
    private String statusId;
    private String description;
    private String url;

    public Issue() {
    }

    public Issue(int issueId, String title, String typeId, String functionIds, int teamId, int milestoneId, int assignerId, int assigneeId, boolean isClosed, String dueDate, String statusId, String description, String url) {
        this.issueId = issueId;
        this.title = title;
        this.typeId = typeId;
        this.functionIds = functionIds;
        this.teamId = teamId;
        this.milestoneId = milestoneId;
        this.assignerId = assignerId;
        this.assigneeId = assigneeId;
        this.isClosed = isClosed;
        this.dueDate = dueDate;
        this.statusId = statusId;
        this.description = description;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(int assignerId) {
        this.assignerId = assignerId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
