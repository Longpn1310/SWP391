/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_criteria;

import dbContext.ConnectionContext;
import static dbContext.ConnectionContext.getConnection;
import entity_iteration.Iteration;
import entity_subject.Subject;
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
public class CriteriaDao {

    public int updateCriteria(Criteria criteria) {
        String query = "UPDATE `spm391_bl5`.`evaluation_criteria`\n"
                + "SET\n"
                + "`iteration_id` = ?,\n"
                + "`criteria_name` = ?,\n"
                + "`is_team_eval` = ?,\n"
                + "`eval_weight` = ?,\n"
                + "`max_loc` =?,\n"
                + "`status` =?,\n"
                + "`description` =?\n"
                + "WHERE `criteria_id` =?;";
        try {
            Connection cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(query);
            ps.setInt(1, criteria.getIterationId());
            ps.setString(2, criteria.getCriteriaName());
            ps.setBoolean(3, criteria.isIsTeamEval());

            ps.setInt(4, criteria.getEvalWeight());
            ps.setInt(5, criteria.getMaxLoc());
            ps.setBoolean(6, criteria.isStatus());
            ps.setString(7, criteria.getDescription());
            ps.setInt(8, criteria.getCriteriaId());

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int addCriteria(Criteria criteria) {
        String sql = "INSERT INTO `spm391_bl5`.`evaluation_criteria`\n"
                + "("
                + "`criteria_name`,\n"
                + "`is_team_eval`,\n"
                + "`eval_weight`,\n"
                + "`max_loc`,\n"
                + "`status`,\n"
                + "`description`, `iteration_id`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";
        Connection cnn;
        int res = 0;
        try {
            cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(sql);

            ps.setString(1, criteria.getCriteriaName());
            ps.setBoolean(2, criteria.isIsTeamEval());
            ps.setInt(3, criteria.getEvalWeight());
            ps.setInt(4, criteria.getMaxLoc());

            ps.setBoolean(5, criteria.isStatus());
            ps.setString(6, criteria.getDescription());
            ps.setInt(7, criteria.getIterationId());
            res = ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Criteria getById(int id) {
        try {
            String query = "SELECT * FROM spm391_bl5.evaluation_criteria where criteria_id=" + id;
            Connection connection = ConnectionContext.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(query);
            rs.next();
//            int teamId = rs.getInt("team_id");
            String criteriaName = rs.getString("criteria_name");
            boolean isTeamEval = rs.getBoolean("is_team_eval");
            int evalWeight = rs.getInt("eval_weight");
            int maxLoc = rs.getInt("max_loc");
            boolean status = rs.getBoolean("status");
            int iterationId = rs.getInt("iteration_id");
            String description = rs.getString("description");

            Criteria criteria = new Criteria(id, iterationId, criteriaName, evalWeight, maxLoc, status, isTeamEval, description);

            return criteria;
        } catch (SQLException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Criteria> search(Integer iterationId, Integer subjectId, Boolean status, int start, int end) {
        String sql = "SELECT cri.criteria_id, cri.criteria_name, cri.eval_weight, cri.is_team_eval,cri.max_loc, cri.status, cri.description, cri.iteration_id, su.subject_name,  iter.iteration_name\n"
                + "           FROM spm391_bl5.evaluation_criteria as cri\n"
                + "                join spm391_bl5.iteration as iter\n"
                + "               on cri.iteration_id = iter.iteration_id\n"
                + "               join spm391_bl5.subject as su\n"
                + "               on iter.subject_id = su.subject_id\n"
                + "                where  su.status = 1 and iter.status = 1 ";
        ArrayList<Criteria> list = new ArrayList<>();
        if (iterationId != null) {
            sql += "\nand iter.iteration_id = " + iterationId;
        }
        if (subjectId != null) {
            sql += "\nand su.subject_id = " + subjectId;
        }
        if (status != null) {
            sql += "\nand cri.status=" + status;
        }
        sql += "\nlimit " + start + "," + end;
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int criteriaId = rs.getInt("criteria_id");
                String criteriaName = rs.getString("criteria_name");
                int evalWeight = rs.getInt("eval_weight");
                boolean isTeamEval = rs.getBoolean("is_team_eval");
                int maxLoc = rs.getInt("max_loc");
                boolean sta = rs.getString("status").equals("1") ? true : false;
                String description = rs.getString("description");
                String subjectName = rs.getString("subject_name");
                String iterationName = rs.getString("iteration_name");
                Criteria cri = new Criteria(criteriaId, 0, criteriaName, evalWeight, maxLoc, isTeamEval, sta, description);
                list.add(cri);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int count(Integer iterationId, Integer subjectId, Boolean status) {
        String sql = "SELECT count(*)\n"
                + "           FROM spm391_bl5.evaluation_criteria as cri\n"
                + "                join spm391_bl5.iteration as iter\n"
                + "               on cri.iteration_id = iter.iteration_id\n"
                + "               join spm391_bl5.subject as su\n"
                + "               on iter.subject_id = su.subject_id\n"
                + "               where  iter.status = 1 and su.status = 1 ";
        ArrayList<Criteria> list = new ArrayList<>();
        if (iterationId != null) {
            sql += "\nand iter.iteration_id = " + iterationId;
        }
        if (iterationId != null) {
            sql += "\nand su.subject_id = " + subjectId;
        }
        if (status != null) {
            sql += "\nand cri.status=" + status;
        }

        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ArrayList<Iteration> searchIteration() {
        String sql = "SELECT * FROM spm391_bl5.iteration as ti\n"
                + "join spm391_bl5.subject as su on ti.subject_id = su.subject_id\n"
                + "where su.status = 1 and ti.status = 1\n"
                + "order by su.subject_id;";
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Iteration iter = new Iteration();
                iter.setId(rs.getInt("iteration_id"));
                iter.setSubjectName(rs.getString("subject_name"));
                iter.setName(rs.getString("iteration_name"));
                list.add(iter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Subject> searchSubject() {
        String sql = "SELECT * FROM spm391_bl5.subject where status = 1;";
        ArrayList<Subject> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject su = new Subject();
                su.setSubjectId(rs.getInt("subject_id"));
                su.setSubjectName(rs.getString("subject_name"));
                list.add(su);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
