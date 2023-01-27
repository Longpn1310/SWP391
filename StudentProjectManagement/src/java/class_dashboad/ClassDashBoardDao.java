package class_dashboad;

import dbContext.ConnectionContext;
import entity_team.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HaiLong
 */
public class ClassDashBoardDao extends ConnectionContext {

    public ArrayList<TeamGrade> getAll() {
        ArrayList<TeamGrade> list = new ArrayList<>();
        String sql = "SELECT team.project_code as team_code, cu.topic_eval, cu.ongoing_eval \n"
                + "FROM spm391_bl5.class_user as cu\n"
                + "join spm391_bl5.team on cu.team_id = team.team_id";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                String teamCode = rs.getString("team_code");
                int teamBase = rs.getInt("topic_eval");
                int individualBase = rs.getInt("ongoing_eval");
                TeamGrade tg = new TeamGrade(teamCode, teamBase, individualBase);
                list.add(tg);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClassDashBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassDashBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    ArrayList<TeamIssue> getAllTeamIssues() {
        ArrayList<TeamIssue> list = new ArrayList<>();
        String sql = "select t.project_code, cs.setting_title, count(*) as 'count' from spm391_bl5.team as t\n"
                + "join spm391_bl5.issue as i\n"
                + "on t.team_id = i.team_id\n"
                + "join spm391_bl5.class_setting as cs\n"
                + "on i .status_id = cs.setting_id\n"
                + "group by t.project_code, cs.setting_title";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                String teamCode = rs.getString("project_code");
                String issue = rs.getString("setting_title");
                int count = rs.getInt("count");
                TeamIssue teamIssue = new TeamIssue();
                teamIssue.setCount(count);
                teamIssue.setIssue(issue);
                teamIssue.setTeamCode(teamCode);
                list.add(teamIssue);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClassDashBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassDashBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    HashMap<String, TeamSubmitStatus> getAllTeamSubmiStatus() {
        String sql = "SELECT t.team_id, t.project_code, f.submit_status,count(*) as count\n"
                + " FROM spm391_bl5.function as f\n"
                + "join team as t on f.team_id = t.team_id\n"
                + "group by t.team_id,t.project_code, f.submit_status";
        HashMap<String, TeamSubmitStatus> map = new HashMap<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String teamCode = rs.getString("project_code");
                int submitStatus = rs.getInt("submit_status");
                TeamSubmitStatus teamSubmitStatus = map.get(teamCode);
                if (teamSubmitStatus == null) {
                    teamSubmitStatus = new TeamSubmitStatus();
                }
                int value = rs.getInt("count");
                if (submitStatus == 1) {
                    teamSubmitStatus.setPending(value);

                } else if (submitStatus == 2) {
                    teamSubmitStatus.setPlanded(value);
                } else if (submitStatus == 3) {
                    teamSubmitStatus.setSubmitted(value);
                } else if (submitStatus == 4) {
                    teamSubmitStatus.setRejected(value);
                } else if (submitStatus == 5) {
                    teamSubmitStatus.setEvaluation(value);
                }
                map.put(teamCode, teamSubmitStatus);
            }

        } catch (Exception ex) {

        }
        return map;
    }
}
