/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_iteration;

import dbContext.ConnectionContext;
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
public class IterationDao extends ConnectionContext {

    /**
     * "Filters: Subject (1), Status Column: Id, Name, Eval Weight, On-Going,
     * Status, Description Row Actions: View/Edit, Activate/Deactivate Global
     * Actions: Filter, Search (by Name), Sort (columns), Paginate Roles:
     * manager ~ full access, trainer ~ read only Status: Active, Inactive"
     *
     */
    public int updateIteration(Iteration iter) {
        String query = "UPDATE `spm391_bl5`.`iteration`\n"
                + "SET\n"
                + "`subject_id` = ?,\n"
                + "`iteration_name` = ?,\n"
                + "`eval_weight` = ?,\n"
                + "`is_ongoing` = ?,\n"
                + "`description` = ?,\n"
                + "`status` =?\n"
                + "WHERE `iteration_id` =?;";
        try {
            Connection cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(query);
            ps.setInt(1, iter.getSubjectId());
            ps.setString(2, iter.getName());
            ps.setInt(3, iter.getEvalWeight());
            ps.setBoolean(4, iter.isOnGoing());
            ps.setString(5, iter.getDescription());
            ps.setBoolean(6, iter.isStatus());
            ps.setInt(7, iter.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void add(Iteration itera) {
        String sql = "INSERT INTO `spm391_bl5`.`iteration`\n"
                + "(\n"
                + "`subject_id`,\n"
                + "`iteration_name`,\n"
                + "`eval_weight`,\n"
                + "`is_ongoing`,\n"
                + "`description`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "("
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?);";
        Connection cnn;
        try {
            cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(sql);

            ps.setInt(1, itera.getSubjectId());
            ps.setString(2, itera.getName());
            ps.setInt(3, itera.getEvalWeight());
            ps.setBoolean(4, itera.isOnGoing());
            ps.setString(5, itera.getDescription());
            ps.setBoolean(6, itera.isStatus());

            boolean res = ps.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Iteration getById(int id) {
        try {
            String query = "SELECT * FROM spm391_bl5.iteration where iteration_id=" + id;
            Connection connection = ConnectionContext.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String name = rs.getString("iteration_name");
            int evalWeight = rs.getInt("eval_weight");
            boolean ongoing = rs.getBoolean("is_ongoing");
            String description = rs.getString("description");
            boolean sta = rs.getBoolean("status");
//            String subjectName = rs.getString("subject_name");
            int subjectId = rs.getInt("subject_id");

            Iteration it = new Iteration(id, subjectId, name, evalWeight, ongoing, description, sta);

            return it;
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Iteration> search(String iteationName, int subjectId, int status, int start, int end, String sort) {
        String sql = "SELECT it.iteration_name, it.iteration_id, it.eval_weight, it.is_ongoing, it.description, it.status, su.subject_name\n"
                + "FROM spm391_bl5.iteration as it\n"
                + "join spm391_bl5.subject as su\n"
                + "on it.subject_id = su.subject_id\n"
                + "where it.iteration_name like ?\n"
                + "and su.subject_id = ?\n"
                + "and it.status = ?\n"
                + " order by " + sort + " limit ?, ?";
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, '%' + iteationName + '%');
            pre.setInt(2, subjectId);
            pre.setInt(3, status);
            pre.setInt(4, start);
            pre.setInt(5, end);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("iteration_id");
                String name = rs.getString("iteration_name");
                int evalWeight = rs.getInt("eval_weight");
                boolean ongoing = rs.getBoolean("is_ongoing");
                String description = rs.getString("description");
                boolean sta = rs.getBoolean("status");
                String subjectName = rs.getString("subject_name");
                Iteration it = new Iteration(id, name, subjectName, evalWeight, ongoing, description, sta);
                list.add(it);

            }
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int count(String iteationName, int subjectId, int status) {
        String sql = "SELECT count(*) as count\n"
                + "FROM spm391_bl5.iteration as it\n"
                + "join spm391_bl5.subject as su\n"
                + "on it.subject_id = su.subject_id\n"
                + "where it.iteration_name like ?\n"
                + "and su.subject_id = ?\n"
                + "and it.status = ?";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, '%' + iteationName + '%');
            pre.setInt(2, subjectId);
            pre.setInt(3, status);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public ArrayList<Subject> search() {
        String sql = "SELECT * FROM spm391_bl5.subject;";
        ArrayList<Subject> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));                                
                list.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IterationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
