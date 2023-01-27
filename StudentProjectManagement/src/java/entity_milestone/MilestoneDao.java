/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_milestone;

import dbContext.ConnectionContext;
import entity_class.Class;
import entity_iteration.Iteration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class MilestoneDao {

    public List<Milestone> getAllMilestoneTrainer(int trainerId) throws SQLException, ClassNotFoundException {

        List<Milestone> list = new ArrayList<Milestone>();
        Connection connection = ConnectionContext.getConnection();

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        String GET_All = "SELECT m.milestone_id, i.iteration_name, m.from_date, m.to_date, m.status, m.description, m.class_id\n"
                + "FROM milestone m\n"
                + "inner join iteration i\n"
                + "on i.iteration_id = m.iteration_id\n"
                + "inner join class c\n"
                + "on m.class_id = c.class_id\n"
                + "where c.trainer_id =?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu       

        preparedStatement.setInt(1, trainerId);
        ResultSet rs = preparedStatement.executeQuery();
        try {
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Milestone milestone = new Milestone();
                milestone.setMilestoneId(rs.getInt(1));
                milestone.setIterationName(rs.getString(2));
                milestone.setFromDate(rs.getDate(3));
                milestone.setToDate(rs.getDate(4));
                milestone.setStatus(rs.getBoolean(5));
                milestone.setDescription(rs.getString(6));
                milestone.setClassId(rs.getInt(7));
                list.add(milestone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return list;
    }

    public List<Class> getAllClass(int trainerId) throws SQLException, ClassNotFoundException {

        List<Class> list = new ArrayList<Class>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM class c\n"
                + "where trainer_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, trainerId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Class cl = new Class();
            cl.setClassId(rs.getInt(1));
            cl.setClassCode(rs.getString(2));
            cl.setSubjectId(rs.getInt(4));
            list.add(cl);
        }
        connection.close();
        return list;

    }

    public List<Iteration> getIterationsByClassId(int classId) throws SQLException, ClassNotFoundException {

        List<Iteration> list = new ArrayList<Iteration>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT i.iteration_id, i.iteration_name\n"
                + "FROM iteration i\n"
                + "where i.subject_id = (select subject_id from class where class_id = ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, classId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Iteration iter = new Iteration();
            iter.setId(rs.getInt(1));
            iter.setName(rs.getString(2));
            list.add(iter);
        }
        connection.close();
        return list;

    }

    public int changeStatusMilestone(int id, boolean milestoenStatus) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        String CHANGE_STATUS = "UPDATE `milestone` \n"
                + "SET `status` = ? \n"
                + "WHERE `milestone_id` = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                CHANGE_STATUS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            if (milestoenStatus == true) {
                preparedStatement.setBoolean(1, false);
            } else {
                if (milestoenStatus == false) {
                    preparedStatement.setBoolean(1, true);
                }
            }
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }

    public List<Milestone> searchMilestone(int trainerId, String status, String classId) throws SQLException, ClassNotFoundException {
        List<Milestone> list = new ArrayList<Milestone>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.        
        String SEARCH = "SELECT m.milestone_id, i.iteration_name, m.from_date, m.to_date, m.status, m.description\n"
                + "FROM milestone m\n"
                + "inner join iteration i\n"
                + "on i.iteration_id = m.iteration_id\n"
                + "inner join class c\n"
                + "on m.class_id = c.class_id\n";
        PreparedStatement preparedStatement = null;

        if (status.equals("all") && classId.equals("all")) {
            SEARCH += "where c.trainer_id = ?;";
            preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, trainerId);
        } else {
            if (!status.equals("all") && !classId.equals("all")) {
                SEARCH += "where m.status = ? and m.class_id = ? and c.trainer_id = ?;";
                preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                preparedStatement.setInt(2, Integer.parseInt(classId));
                preparedStatement.setInt(3, trainerId);
            } else {
                if (!status.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where m.status = ? and c.trainer_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                        preparedStatement.setInt(2, trainerId);
                    }
                }
                if (!classId.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where m.class_id = ? and c.trainer_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(classId));
                        preparedStatement.setInt(2, trainerId);
                    }
                }
            }
        }

        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Milestone milestone = new Milestone();
            milestone.setMilestoneId(rs.getInt(1));
            milestone.setIterationName(rs.getString(2));
            milestone.setFromDate(rs.getDate(3));
            milestone.setToDate(rs.getDate(4));
            milestone.setStatus(rs.getBoolean(5));
            milestone.setDescription(rs.getString(6));
            list.add(milestone);
        }
        connection.close();
        return list;
    }

    public Milestone getMilestoneById(int id) throws SQLException, ClassNotFoundException {
        Milestone milestone = new Milestone();
        Connection connection = ConnectionContext.getConnection();
        String GET_BY_ID = "SELECT * FROM milestone\n"
                + "where milestone_id = ? ;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        try {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.                
                milestone.setMilestoneId(rs.getInt(1));
                milestone.setIterationId(rs.getInt(2));
                milestone.setClassId(rs.getInt(3));
                milestone.setFromDate(rs.getDate(4));
                milestone.setToDate(rs.getDate(5));
                milestone.setStatus(rs.getBoolean(6));
                milestone.setDescription(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return milestone;
    }

    public int addMilestone(Milestone milestone) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_MILESTONE = "INSERT INTO `milestone` "
                + "(`iteration_id`, `class_id`, `from_date`, `to_date`, `status`, `description`) \n"
                + "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_MILESTONE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String fromDate = null;
        String toDate = null;
        if (milestone.getFromDate() != null) {
            fromDate = formatter.format(milestone.getFromDate());
        }
        if (milestone.getToDate() != null) {
            toDate = formatter.format(milestone.getToDate());
        }

        try {
            preparedStatement.setInt(1, milestone.getIterationId());
            preparedStatement.setInt(2, milestone.getClassId());
            preparedStatement.setString(3, fromDate);
            preparedStatement.setString(4, toDate);
            preparedStatement.setBoolean(5, milestone.isStatus());
            preparedStatement.setString(6, milestone.getDescription());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }

    public int updateMilestone(Milestone milestone) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_SUBJECT = "UPDATE milestone\n"
                + "SET iteration_id = ?, class_id = ?, from_date = ?, to_date =?, status =?,description = ?\n"
                + "WHERE milestone_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_SUBJECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String fromDate = formatter.format(milestone.getFromDate());
        String toDate = formatter.format(milestone.getToDate());

        try {
            preparedStatement.setInt(1, milestone.getIterationId());
            preparedStatement.setInt(2, milestone.getClassId());
            preparedStatement.setString(3, fromDate);
            preparedStatement.setString(4, toDate);
            preparedStatement.setBoolean(5, milestone.isStatus());
            preparedStatement.setString(6, milestone.getDescription());
            preparedStatement.setInt(7, milestone.getMilestoneId());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }    

}
