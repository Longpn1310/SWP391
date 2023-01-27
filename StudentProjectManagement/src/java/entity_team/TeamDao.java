/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_team;

import dbContext.ConnectionContext;
import static dbContext.ConnectionContext.getConnection;
import entity_iteration.IterationDao;
import entity_class.Class;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class TeamDao {

    public ArrayList<Class> search(int userId) {
        String sql = "SELECT cl.*\n"
                + "FROM spm391_bl5.class_user as cu\n"
                + "join class as cl on cu.class_id = cl.class_id\n"
                + "where trainer_id = ?\n"
                + "and cl.status = 1;";
        ArrayList<Class> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, userId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Class cl = new Class();
                cl.setClassId(rs.getInt("class_id"));
                cl.setClassCode(rs.getString("class_code"));
                list.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int updateTeam(Team team) {
        String query = "UPDATE `spm391_bl5`.`team`\n"
                + "SET\n"
                + "`class_id` = ?,\n"
                + "`project_code` = ?,\n"
                + "`topic_code` = ?,\n"
                + "`topic_name` = ?,\n"
                + "`status` =?,\n"
                + "`description` =?\n"
                + "WHERE `team_id` =?;";
        try {
            Connection cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(query);
            ps.setInt(1, team.getClass_id());
            ps.setString(2, team.getProjectCode());
            ps.setString(3, team.getTopicCode());
            ps.setString(4, team.getTopicName());
            ps.setBoolean(5, team.isStatus());
            ps.setString(6, team.getDescription());
            ps.setInt(7, team.getTeam_id());

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int addTeam(Team team) {
        String sql = "INSERT INTO `spm391_bl5`.`team`\n"
                + "(`class_id`,\n"
                + "`project_code`,\n"
                + "`topic_code`,\n"
                + "`topic_name`,\n"
                + "`status`,\n"
                + "`description`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?);";
        Connection cnn;
        int res = 0;
        try {
            cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(sql);

            ps.setInt(1, team.getClass_id());
            ps.setString(2, team.getProjectCode());
            ps.setString(3, team.getTopicCode());
            ps.setString(4, team.getTopicName());
            ps.setBoolean(5, team.isStatus());
            ps.setString(6, team.getDescription());

            res = ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Team getById(int id) {
        try {
            String query = "SELECT * FROM spm391_bl5.team where team_id=" + id;
            Connection connection = ConnectionContext.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(query);
            rs.next();
//            int teamId = rs.getInt("team_id");
            String teamCode = rs.getString("project_code");
            String topicCode = rs.getString("topic_code");
            String topicName = rs.getString("topic_name");
            String description = rs.getString("description");
            boolean status = rs.getBoolean("status");
            int classId = rs.getInt("class_id");
            Team team = new Team(id, classId, teamCode, topicCode, topicName, status, description);

            return team;
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Team getByTeamCode(String code) {
        try {
            String query = "SELECT * FROM spm391_bl5.team where project_code=?";
            Connection connection = ConnectionContext.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery();
            rs.next();
            int teamId = rs.getInt("team_id");
            String teamCode = rs.getString("project_code");
            String topicCode = rs.getString("topic_code");
            String topicName = rs.getString("topic_name");
            String description = rs.getString("description");
            boolean status = rs.getBoolean("status");
            int classId = rs.getInt("class_id");
            Team team = new Team(teamId, classId, teamCode, topicCode, topicName, status, description);

            return team;
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Team> search(Integer classId, Boolean status, String teamCode, int start, int end) {
        String sql = "SELECT tea.team_id, tea.project_code, tea.topic_code, tea.topic_name, tea.status, tea.description, tea.class_id, cl.class_code\n"
                + "           FROM spm391_bl5.team as tea\n"
                + "                join spm391_bl5.class as cl\n"
                + "               on tea.class_id = cl.class_id\n"
                + "               where  cl.status = 1 ";
        ArrayList<Team> list = new ArrayList<>();
        if (classId != null) {
            sql += "\nand cl.class_id = " + classId;
        }
        if (status != null) {
            sql += "\nand tea.status=" + status;
        }
        if (teamCode != null && !"".equals(teamCode)) {
            sql += "\nand tea.project_code like  \'" + ('%' + teamCode + '%') + "\' ";
        }
        sql += "\norder by tea.project_code \nlimit " + start + "," + end;

        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int teamId = rs.getInt("team_id");
                String code = rs.getString("project_code");
                String topicCode = rs.getString("topic_code");
                String topicName = rs.getString("topic_name");
                String description = rs.getString("description");
                boolean sta = rs.getString("status").equals("1") ? true : false;
                String classCode = rs.getString("class_code");
                String projectCode = rs.getString("project_code");
                Team tea = new Team(teamId, 0, projectCode, topicCode, topicName, sta, description);
                list.add(tea);

            }
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int count(Integer classId, Boolean status, String teamCode) {
        String sql = "SELECT count(*)\n"
                + "           FROM spm391_bl5.team as tea\n"
                + "                join spm391_bl5.class as cl\n"
                + "               on tea.class_id = cl.class_id\n"
                + "               where  cl.status = 1 ";
        ArrayList<Team> list = new ArrayList<>();
        if (classId != null) {
            sql += "\nand cl.class_id = " + classId;
        }
        if (status != null) {
            sql += "\nand tea.status=" + status;
        }
        if (teamCode != null && !"".equals(teamCode)) {
            sql += "\nand tea.project_code like  \'" + ('%' + teamCode + '%') + "\' ";
        }
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
