/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_student;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Student {

    private int userId;
    private String userName;
    private int teamId;
    private String rollNumber;
    private String email;
    private String classCode;
    private boolean leader;
    private Date dropoutDate;
    private String note;
    private float ongoingEval;
    private float finalPresEval;
    private float finalTopicEval;
    private boolean status;

    public Student() {
    }

    public Student(int userId, String userName, int teamId, String rollNumber, String email, String classCode, boolean leader, Date dropoutDate, String note, float ongoingEval, float finalPresEval, float finalTopicEval, boolean status) {
        this.userId = userId;
        this.userName = userName;
        this.teamId = teamId;
        this.rollNumber = rollNumber;
        this.email = email;
        this.classCode = classCode;
        this.leader = leader;
        this.dropoutDate = dropoutDate;
        this.note = note;
        this.ongoingEval = ongoingEval;
        this.finalPresEval = finalPresEval;
        this.finalTopicEval = finalTopicEval;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public Date getDropoutDate() {
        return dropoutDate;
    }

    public void setDropoutDate(Date dropoutDate) {
        this.dropoutDate = dropoutDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getOngoingEval() {
        return ongoingEval;
    }

    public void setOngoingEval(float ongoingEval) {
        this.ongoingEval = ongoingEval;
    }

    public float getFinalPresEval() {
        return finalPresEval;
    }

    public void setFinalPresEval(float finalPresEval) {
        this.finalPresEval = finalPresEval;
    }

    public float getFinalTopicEval() {
        return finalTopicEval;
    }

    public void setFinalTopicEval(float finalTopicEval) {
        this.finalTopicEval = finalTopicEval;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
