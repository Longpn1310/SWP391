/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.student_eval;

import dbContext.ConnectionContext;
import entity_evaluation.EvaluationCriteria;
import entity_evaluation.MemberEvaluation;
import entity_evaluation.TeamEvaluation;
import entity_function.Function;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StudentEvaluationDao {

    public List<TeamEvaluation> getAllTeamEvaluation(int teamId, int milestoneId) throws SQLException, ClassNotFoundException {
        List<TeamEvaluation> evaluations = new ArrayList<>();
        Connection connection = ConnectionContext.getConnection();
        String GET_All = "SELECT te.id, te.criteria_id, te.grade, te.comment, te.team_id, te.milestone_id, ec.criteria_name, ec.eval_weight\n"
                + "FROM team_evaluation te\n"
                + "inner join evaluation_criteria ec\n"
                + "on te.criteria_id = ec.criteria_id\n"
                + "where team_id = ? and milestone_id = ? and ec.is_team_eval = true;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, teamId);
        preparedStatement.setInt(2, milestoneId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            TeamEvaluation evaluation = new TeamEvaluation();
            evaluation.setId(rs.getInt(1));
            evaluation.setCriteriaId(rs.getInt(2));
            evaluation.setGrade(rs.getFloat(3));
            evaluation.setComment(rs.getString(4));
            evaluation.setTeamId(rs.getInt(5));
            evaluation.setMilestoneId(rs.getInt(6));
            evaluation.setCriteriaName(rs.getString(7));
            evaluation.setEvalWeight(rs.getInt(8));
            evaluations.add(evaluation);
        }
        connection.close();
        return evaluations;
    }

    public List<MemberEvaluation> getAllMemberEvaluation(int evaluationId) throws SQLException, ClassNotFoundException {
        List<MemberEvaluation> evaluations = new ArrayList<>();
        Connection connection = ConnectionContext.getConnection();
        String GET_All = "SELECT me.id, me.evaluation_id, me.criteria_id, me.total_loc, me.grade, me.comment, ec.criteria_name, ec.eval_weight\n"
                + "FROM member_evaluation me\n"
                + "inner join evaluation_criteria ec\n"
                + "on me.criteria_id = ec.criteria_id\n"
                + "where me.evaluation_id = ? and ec.is_team_eval = false;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu            
        preparedStatement.setInt(1, evaluationId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            MemberEvaluation evaluation = new MemberEvaluation();
            evaluation.setId(rs.getInt(1));
            evaluation.setEvaluationId(rs.getInt(2));
            evaluation.setCriteriaId(rs.getInt(3));
            evaluation.setTotalLoc(rs.getInt(4));
            evaluation.setGrade(rs.getFloat(5));
            evaluation.setComment(rs.getString(6));
            evaluation.setCriteriaName(rs.getString(7));
            evaluation.setEvalWeight(rs.getInt(8));
            evaluations.add(evaluation);
        }
        connection.close();
        return evaluations;
    }

    public int addTeamEvaluation(float grade, String comment, int id) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "UPDATE `team_evaluation` SET `grade` = ?, `comment` = ? WHERE (`id` = ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setInt(3, id);
            preparedStatement.setFloat(1, grade);
            preparedStatement.setString(2, comment);
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int addMemberEvaluation(float grade, String comment, int id) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "UPDATE `member_evaluation` SET `grade` = ?, `comment` = ? WHERE (`id` = ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setInt(3, id);
            preparedStatement.setFloat(1, grade);
            preparedStatement.setString(2, comment);
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int addBonus(int milestoneId, int teamId, float bonus) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "UPDATE `iteration_evaluation` \n"
                + "SET  `bonus` = ? \n"
                + "WHERE (`milestone_id` = ? and `team_id` = ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setFloat(1, bonus);
        preparedStatement.setInt(2, milestoneId);
        preparedStatement.setInt(3, teamId);
        try {
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public List<Function> getTeamFunction(int teamId) throws SQLException, ClassNotFoundException {
        List<Function> list = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        String GET_All = "SELECT * FROM `function`\n"
                + "where team_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu       

        preparedStatement.setInt(1, teamId);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setFunctionId(rs.getInt(1));
            function.setTeamId(rs.getInt(2));
            function.setTitle(rs.getString(3));
            function.setFeature(rs.getString(4));
            function.setPriority(rs.getInt(5));
            function.setIsClosed(rs.getBoolean(6));
            function.setStatusId(rs.getInt(7));
            function.setSubmitStatus(rs.getInt(8));
            function.setDescription(rs.getString(9));
            list.add(function);
        }
        connection.close();
        return list;
    }
}
