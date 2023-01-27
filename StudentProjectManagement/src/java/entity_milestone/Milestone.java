/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_milestone;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Milestone {

    private int milestoneId;    
    private String milestoneName;
    private int iterationId;    
    private String iterationName;
    private int classId;
    private Date fromDate;
    private Date toDate;
    private boolean status;
    private String description;

    public Milestone() {
    }

    public Milestone(int milestoneId, String milestoneName, int iterationId, String iterationName, int classId, Date fromDate, Date toDate, boolean status, String description) {
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
        this.iterationId = iterationId;
        this.iterationName = iterationName;
        this.classId = classId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.description = description;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
