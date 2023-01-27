/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.iteration_eval;

import entity_class.Class;
import dbContext.ConnectionContext;
import entity_evaluation.IterationEvaluation;
import entity_milestone.Milestone;
import entity_team.Team;
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
public class IterationEvalDao {

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

    public List<Team> getAllTeam(int classId) throws SQLException, ClassNotFoundException {

        List<Team> list = new ArrayList<Team>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT team_id, project_code from team\n"
                + "where class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, classId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Team team = new Team();
            team.setTeam_id(rs.getInt(1));
            team.setProjectCode(rs.getString(2));
            list.add(team);
        }
        connection.close();
        return list;

    }

    public List<Milestone> getAllMilestone(int classId) throws SQLException, ClassNotFoundException {

        List<Milestone> list = new ArrayList<Milestone>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT milestone_id, milestone_name from milestone\n"
                + "where class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        preparedStatement.setInt(1, classId);
        ResultSet rs = preparedStatement.executeQuery();

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Milestone milestone = new Milestone();
            milestone.setMilestoneId(rs.getInt(1));
            milestone.setMilestoneName(rs.getString(2));
            list.add(milestone);
        }
        connection.close();
        return list;

    }

    public List<IterationEvaluation> getAllIterationEvaluation(int classId, int team_id, int milestoneId) throws SQLException, ClassNotFoundException {

        List<IterationEvaluation> list = new ArrayList<IterationEvaluation>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT ie.evaluation_id, ie.milestone_id, ie.team_id,\n"
                + "ie.class_id, ie.user_id, ie.bonus, ie.total_grade, ie.comment,\n"
                + "u.roll_number, u.full_name, t.project_code, i.iteration_id\n"
                + "FROM iteration_evaluation ie\n"
                + "inner join user u\n"
                + "on u.user_id = ie.user_id\n"
                + "inner join team t\n"
                + "on t.team_id = ie.team_id\n"
                + "inner join milestone m\n"
                + "on m.milestone_id = ie.milestone_id\n"
                + "inner join iteration i\n"
                + "on i.iteration_id = m.iteration_id\n"
                + "where ie.class_id = ? and ie.milestone_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        if (team_id != 0) {
            GET_All += " and ie.team_id = ?;";
            preparedStatement = connection.prepareStatement(
                    GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(3, team_id);
            preparedStatement.setInt(2, milestoneId);
        } else {
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, milestoneId);
        }

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
            iter.setRollNumber(rs.getString(9));
            iter.setFullName(rs.getString(10));
            iter.setTeamName(rs.getString(11));
            iter.setIterationId(rs.getInt(12));
            list.add(iter);
        }
        connection.close();
        return list;

    }

    public List<IterationEvaluation> getAllTeamEvaluation(List<IterationEvaluation> list) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionContext.getConnection();
        for (IterationEvaluation iterationEvaluation : list) {            
            float total = 0;
            String GET_All = "SELECT * FROM team_evaluation te\n"
                    + "inner join evaluation_criteria ec\n"
                    + "on te.criteria_id = ec.criteria_id\n"
                    + "inner join iteration_evaluation ie\n"
                    + "on te.team_id = ie.team_id and te.milestone_id = ie.milestone_id\n"
                    + "where te.team_id = ? and te.milestone_id = ? and ie.user_id = ? and ec.is_team_eval = true;";

            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Tạo đối tượng Statement chuyên để đọc dữ liệu
            preparedStatement.setInt(1, iterationEvaluation.getTeamId());
            preparedStatement.setInt(2, iterationEvaluation.getMilestoneId());
            preparedStatement.setInt(3, iterationEvaluation.getUserId());
            ResultSet rs = preparedStatement.executeQuery();

            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                float grade = rs.getFloat(1);
                int evalWeight = rs.getInt(2);
                total += grade * evalWeight / 100;
            }
            iterationEvaluation.setTeamEvaluation(total);
        }
        connection.close();
        return list;
    }

    public List<IterationEvaluation> getAllMemberEvaluation(List<IterationEvaluation> list) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionContext.getConnection();
        for (IterationEvaluation iterationEvaluation : list) {
            float total = 0;
            String GET_All = "SELECT me.grade, me.total_loc, ec.eval_weight FROM member_evaluation me\n"
                    + "inner join evaluation_criteria ec\n"
                    + "on me.criteria_id = ec.criteria_id\n"
                    + "where evaluation_id = ? and ec.is_team_eval = false;";

            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Tạo đối tượng Statement chuyên để đọc dữ liệu            
            preparedStatement.setInt(1, iterationEvaluation.getEvaluationId());
            ResultSet rs = preparedStatement.executeQuery();

            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                float grade = rs.getFloat(1);
                int totalLoc = rs.getInt(2);
                int evalWeight = rs.getInt(3);
                total += grade * evalWeight / 100;
                iterationEvaluation.setLocEvaluation(totalLoc);
            }
            iterationEvaluation.setMemberEvaluation(total);
        }
        connection.close();
        return list;
    }

}
