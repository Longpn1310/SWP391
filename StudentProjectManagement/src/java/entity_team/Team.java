/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_team;

/**
 *
 * @author admin
 */
public class Team {
    private int team_id;
    private int class_id;
    private String projectCode;
    private String topicCode;
    private String topicName;
    private boolean status;
    private String description;

    public Team() {
    }

    public Team(int team_id, int class_id, String projectCode, String topicCode, String topicName, boolean status, String description) {
        this.team_id = team_id;
        this.class_id = class_id;
        this.projectCode = projectCode;
        this.topicCode = topicCode;
        this.topicName = topicName;
        this.status = status;
        this.description = description;
    }

    public int getTeam_id() {
        return team_id;
    }

    public int getClass_id() {
        return class_id;
    }



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }




    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }


    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
