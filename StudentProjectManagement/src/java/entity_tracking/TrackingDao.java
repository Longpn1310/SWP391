/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_tracking;

import dbContext.ConnectionContext;
import entity_milestone.Milestone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class TrackingDao extends ConnectionContext {

    public void update(int functionId, int miId, String history, int status) {
        String sql = "UPDATE `spm391_bl5`.`tracking`\n"
                + "SET\n"
                + "`history` = ?, `submit_status` = ?\n"
                + "WHERE `function_id` = ?  AND `milestone_id` = ?;";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, history);
            pre.setInt(2, status);
            pre.setInt(3, functionId);
            pre.setInt(4, miId);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public TrackingEntity get(int assigneeId, int assignerId, int miId, int functionId) {
        String sql = "SELECT fu.function_id,tr.milestone_id, \n"
                + "fu.feature, mi.description, assigner.full_name as 'assigner', \n"
                + "assignee.full_name as 'assignee', tr.submit_status, tr.history\n"
                + "FROM spm391_bl5.tracking as tr\n"
                + "join milestone as mi on tr.milestone_id = mi.milestone_id\n"
                + "join spm391_bl5.function as fu on fu.function_id = tr.function_id\n"
                + "join spm391_bl5.user as assigner on tr.assigner_id = assigner.user_id\n"
                + "join spm391_bl5.user as assignee on tr.assignee_id = assignee.user_id\n"
                + "where  mi.milestone_id = ?\n"
                + "and assigner.user_id = ? and \n"
                + "assignee.user_id  = ?\n"
                + "and fu.function_id = ?";
        try {
            Connection connection = ConnectionContext.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, miId);
            pre.setInt(2, assignerId);
            pre.setInt(3, assigneeId);
            pre.setInt(4, functionId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String feature = rs.getString("feature");
                String milestone = rs.getString("description");
                String assigner = rs.getString("assigner");
                String assignee = rs.getString("assignee");
                String history = rs.getString("history");
                TrackingEntity entity = new TrackingEntity();
                entity.setAssignee(assignee);
                entity.setAssigner(assigner);
                entity.setFeature(feature);
                entity.setFunctionId(functionId);
                entity.setMilestone(milestone);
                entity.setHistory(rs.getString("history"));
                entity.setFunctionId(functionId);
                entity.setMiId(rs.getInt("milestone_id"));
                entity.setSubmitStatus(getStatusName(rs.getInt("submit_status")));
                entity.setSubmitStatusId(rs.getInt("submit_status"));
                return entity;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<TrackingEntity> getAll(int userId, int teamId, int miId, String name) {
        String sql = "SELECT fu.function_id,\n"
                + "fu.feature, mi.description, assigner.full_name as 'assigner', mi.milestone_id,tr.assigner_id,tr.assignee_id ,\n"
                + "assignee.full_name as 'assignee', tr.submit_status, tr.history\n"
                + "FROM spm391_bl5.tracking as tr\n"
                + "join milestone as mi on tr.milestone_id = mi.milestone_id\n"
                + "join spm391_bl5.function as fu on fu.function_id = tr.function_id\n"
                + "join spm391_bl5.user as assigner on tr.assigner_id = assigner.user_id\n"
                + "join spm391_bl5.user as assignee on tr.assignee_id = assignee.user_id\n"
                + "where  mi.milestone_id = ?\n"
                + "and assigner.user_id = ?  and fu.feature like ?";
        ArrayList<TrackingEntity> list = new ArrayList<>();
        try {
            Connection connection = ConnectionContext.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
//            pre.setInt(1, teamId);

            pre.setInt(1, miId);
            pre.setInt(2, userId);
            if (name == null) {
                name = "";
            }
            pre.setString(3, '%' + name + '%');
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int functionId = rs.getInt("function_id");
                String feature = rs.getString("feature");
                String milestone = rs.getString("description");
                String assigner = rs.getString("assigner");
                String assignee = rs.getString("assignee");
                TrackingEntity entity = new TrackingEntity();
                entity.setAssignee(assignee);
                entity.setAssigner(assigner);
                entity.setFeature(feature);
                entity.setFunctionId(functionId);
                entity.setMilestone(milestone);
                entity.setSubmitStatus(getStatusName(rs.getInt("submit_status")));
                entity.setMilestoneId(rs.getInt("milestone_id"));
                entity.setAssigneeId(rs.getInt("assignee_id"));
                entity.setAssignerId(rs.getInt("assigner_id"));
                list.add(entity);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrackingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    private String getStatusName(int index) {
        String[] names = {"Pending", "Committed", "Submitted", "Rejected", "Evaluated"};
        return names[index];
    }

    public List<Milestone> getAllMilestone() throws SQLException, ClassNotFoundException {

        List<Milestone> list = new ArrayList<Milestone>();
        Connection connection = ConnectionContext.getConnection();

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        String GET_All = "SELECT m.milestone_id, i.iteration_name, m.class_id , m.from_date, m.to_date, m.status, m.description FROM milestone m\n"
                + "inner join iteration i\n"
                + "on m.iteration_id = i.iteration_id\n"
                + "inner join class c\n"
                + "on m.class_id = c.class_id\n";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        ResultSet rs = preparedStatement.executeQuery();
        try {
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Milestone milestone = new Milestone();
                milestone.setMilestoneId(rs.getInt(1));
                milestone.setIterationName(rs.getString(2));
                milestone.setClassId(rs.getInt(3));
                milestone.setFromDate(rs.getDate(4));
                milestone.setToDate(rs.getDate(5));
                milestone.setStatus(rs.getBoolean(6));
                milestone.setDescription(rs.getString(7));
                list.add(milestone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return list;
    }
}
