/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.class_eval;

import dbContext.ConnectionContext;
import entity_evaluation.ClassEval;
import entity_class.Class;
import entity_evaluation.IterationEvaluation;
import entity_iteration.Iteration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ClassEvalDao {

    public List<ClassEval> getClassEvals(int classId) throws SQLException, ClassNotFoundException {
        List<ClassEval> list = new ArrayList<ClassEval>();
        Connection connection = ConnectionContext.getConnection();

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        String GET_All = "SELECT "
                + "cu.user_id, cu.class_id, u.roll_number, u.full_name,\n"
                + "cu.team_id, t.project_code, cu.final_eval, cu.ongoing_eval\n"
                + "FROM class_user cu\n"
                + "inner join class c\n"
                + "on cu.class_id = c.class_id\n"
                + "inner join user u\n"
                + "on u.user_id = cu.user_id\n"
                + "inner join team t\n"
                + "on t.team_id = cu.team_id\n"
                + "where cu.class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu       

        preparedStatement.setInt(1, classId);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            ClassEval classEval = new ClassEval();
            classEval.setUserId(rs.getInt(1));
            classEval.setClassId(rs.getInt(2));
            classEval.setRollNumber(rs.getString(3));
            classEval.setFullName(rs.getString(4));
            classEval.setTeamId(rs.getInt(5));
            classEval.setTeamName(rs.getString(6));
            classEval.setTotal(rs.getFloat(7));
            classEval.setOnGoing(rs.getFloat(8));
            list.add(classEval);
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

    public List<Iteration> getAllIteration(int classId) throws SQLException, ClassNotFoundException {

        List<Iteration> list = new ArrayList<Iteration>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT i.iteration_id, i.iteration_name FROM iteration i\n"
                + "inner join `subject` s\n"
                + "on i.subject_id = s.subject_id\n"
                + "inner join class c\n"
                + "on s.subject_id = c.subject_id\n"
                + "where c.class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, classId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Iteration iteration = new Iteration();
            iteration.setId(rs.getInt(1));
            iteration.setName(rs.getString(2));
            list.add(iteration);
        }
        connection.close();
        return list;

    }

    public List<IterationEvaluation> getIterationEvaluation(int classId, int userId) throws SQLException, ClassNotFoundException {

        List<IterationEvaluation> list = new ArrayList<IterationEvaluation>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT ie.evaluation_id, ie.milestone_id, ie.team_id, ie.class_id, ie.user_id, ie.bonus, ie.total_grade, ie.comment, i.iteration_id\n"
                + "FROM iteration_evaluation ie\n"
                + "inner join submit s\n"
                + "on ie.milestone_id = s.milestone_id and ie.team_id = s.team_id\n"
                + "inner join milestone m\n"
                + "on m.milestone_id = s.milestone_id\n"
                + "inner join iteration i\n"
                + "on i.iteration_id = m.iteration_id\n"
                + "where ie.class_id = ? and ie.user_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, classId);
        preparedStatement.setInt(2, userId);        
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            IterationEvaluation iter = new IterationEvaluation();
            iter.setEvaluationId(rs.getInt(1));
            iter.setMilestoneId(rs.getInt(2));
            iter.setTeamId(rs.getInt(3));
            iter.setClassId(rs.getInt(4));
            iter.setUserId(rs.getInt(5));
            iter.setBonus(rs.getFloat(6));
            iter.setTotalGrade(rs.getFloat(7));
            iter.setComment(rs.getString(8));
            iter.setIterationId(rs.getInt(9));
            list.add(iter);
        }
        connection.close();
        return list;
    }

}
