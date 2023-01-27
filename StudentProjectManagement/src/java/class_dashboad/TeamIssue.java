/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class_dashboad;

/**
 *
 * @author HaiLong
 */
public class TeamIssue {

    private String teamCode, issue, type;
    private int count;

    public TeamIssue(String teamCode, String issue, String type, int count) {
        this.teamCode = teamCode;
        this.issue = issue;
        this.type = type;
        this.count = count;
    }

    public TeamIssue() {
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
