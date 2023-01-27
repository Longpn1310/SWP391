/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_user;

import java.util.Date;

/**
 *
 * @author admin
 */
public class User {
    private int userId;
    private String rollNumber;
    private String fullName;
    private String email;
    private String password;
    private String mobile;
    private String avatarLink;
    private int roleId;
    private boolean status;
    private String note;

    public User() {
    }

    public User(int userId, String rollNumber, String fullName, String email, String password, String mobile, String avatarLink, int roleId, boolean status, String note) {
        this.userId = userId;
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.avatarLink = avatarLink;
        this.roleId = roleId;
        this.status = status;
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
