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
public class TeamGrade {

    private String teamCode;
    private int teamBase, individualBase;

    public TeamGrade(String teamCode, int teamBase, int individualBase) {
        this.teamCode = teamCode;
        this.teamBase = teamBase;
        this.individualBase = individualBase;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public int getTeamBase() {
        return teamBase;
    }

    public void setTeamBase(int teamBase) {
        this.teamBase = teamBase;
    }

    public int getIndividualBase() {
        return individualBase;
    }

    public void setIndividualBase(int individualBase) {
        this.individualBase = individualBase;
    }

}
