/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_function;

import dbContext.ConnectionContext;
import entity_team.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author trung
 */
public class FunctionDao {

    public List<Team> getAllTeam() throws SQLException, ClassNotFoundException {

        List<Team> listTeam = new ArrayList<Team>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.team;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.

            Team team = new Team();
            team.setTeam_id(rs.getInt("team_id"));
            team.setClass_id(rs.getInt("class_id"));
            team.setProjectCode(rs.getString("project_code"));
            team.setTopicCode(rs.getString("topic_code"));
            team.setTopicName(rs.getString("topic_name"));
            team.setStatus(rs.getBoolean("status"));
            team.setDescription(rs.getString("description"));

            listTeam.add(team);
        }
        connection.close();
        return listTeam;
    }

    public List<String> getAllFeature() throws SQLException, ClassNotFoundException {

        List<String> listFeature = new ArrayList<String>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT feature FROM spm391_bl5.function;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.

            String feature = rs.getString("feature");

            if (!listFeature.contains(feature)) {
                listFeature.add(feature);
            }
        }
        connection.close();
        return listFeature;
    }

    public List<Function> getFunctionsByTeamId(int teamId) throws SQLException, ClassNotFoundException {

        List<Function> listFunction = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.function WHERE team_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, teamId);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setFunctionId(rs.getInt("function_id"));
            function.setTeamId(rs.getInt("team_id"));
            function.setTitle(rs.getString("title"));
            function.setFeature(rs.getString("feature"));
            function.setPriority(rs.getInt("priority"));
            function.setIsClosed(rs.getBoolean("is_closed"));
            function.setStatusId(rs.getInt("status_id"));
            function.setSubmitStatus(rs.getInt("submit_status"));
            function.setDescription(rs.getString("description"));

            listFunction.add(function);
        }
        connection.close();
        return listFunction;
    }

    public List<ClassSetting> getAllStatus() throws SQLException, ClassNotFoundException {

        List<ClassSetting> listClassSetting = new ArrayList<ClassSetting>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.class_setting  WHERE type_id=1;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            ClassSetting classSetting = new ClassSetting();
            classSetting.setSettingId(rs.getInt("setting_id"));
            classSetting.setTypeId(rs.getInt("type_id"));
            classSetting.setSettingTitle(rs.getString("setting_title"));
            classSetting.setSettingValue(rs.getString("setting_value"));
            classSetting.setDisplayOrder(rs.getInt("display_order"));
            classSetting.setClassId(rs.getInt("class_id"));
            classSetting.setStatus(rs.getBoolean("status"));
            classSetting.setDescription(rs.getString("description"));

            listClassSetting.add(classSetting);
        }
        connection.close();
        return listClassSetting;
    }

    public List<Function> getFunctionsByStatus() throws SQLException, ClassNotFoundException {

        List<Function> listFunction = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.function WHERE status=?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, 2);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setTeamId(rs.getInt("team_id"));
            function.setTitle(rs.getString("title"));
            function.setFeature(rs.getString("feature"));
            function.setPriority(rs.getInt("priority"));
            function.setIsClosed(rs.getBoolean("is_closed"));
            function.setStatusId(rs.getInt("status_id"));
            function.setSubmitStatus(rs.getInt("submit_status"));
            function.setDescription(rs.getString("description"));

            listFunction.add(function);
        }
        connection.close();
        return listFunction;
    }

    public List<Integer> getAllSubmitStatus() throws SQLException, ClassNotFoundException {

        List<Integer> listStatus = new ArrayList<Integer>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT submit_status FROM spm391_bl5.function;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, 2);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            int submitStatus = rs.getInt(1);
            listStatus.add(submitStatus);
        }
        connection.close();
        return listStatus;
    }

    public List<Function> getFunctionsBySubmitStatus() throws SQLException, ClassNotFoundException {

        List<Function> listFunction = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.function WHERE submit_status=?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, 2);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setTeamId(rs.getInt("team_id"));
            function.setTitle(rs.getString("title"));
            function.setFeature(rs.getString("feature"));
            function.setPriority(rs.getInt("priority"));
            function.setIsClosed(rs.getBoolean("is_closed"));
            function.setStatusId(rs.getInt("status_id"));
            function.setSubmitStatus(rs.getInt("submit_status"));
            function.setDescription(rs.getString("description"));

            listFunction.add(function);
        }
        connection.close();
        return listFunction;
    }

    public Function getFunctionById(int id) throws SQLException, ClassNotFoundException {
        Function function = new Function();

        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.function WHERE function_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            function.setFunctionId(rs.getInt("function_id"));
            function.setTeamId(rs.getInt("team_id"));
            function.setTitle(rs.getString("title"));
            function.setFeature(rs.getString("feature"));
            function.setPriority(rs.getInt("priority"));
            function.setIsClosed(rs.getBoolean("is_closed"));
            function.setStatusId(rs.getInt("status_id"));
            function.setSubmitStatus(rs.getInt("submit_status"));
            function.setDescription(rs.getString("description"));
        }
        connection.close();
        return function;
    }

    public List<Function> getAllFunction() throws SQLException, ClassNotFoundException {

        List<Function> listFunction = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.function";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setFunctionId(rs.getInt("function_id"));
            function.setTeamId(rs.getInt("team_id"));
            function.setTitle(rs.getString("title"));
            function.setFeature(rs.getString("feature"));
            function.setPriority(rs.getInt("priority"));
            function.setIsClosed(rs.getBoolean("is_closed"));
            function.setStatusId(rs.getInt("status_id"));
            function.setSubmitStatus(rs.getInt("submit_status"));
            function.setDescription(rs.getString("description"));

            listFunction.add(function);
        }
        connection.close();
        return listFunction;
    }

    public List<Function> searchFunction(String teamId, String statusId, String submitStatus, String title) throws SQLException, ClassNotFoundException {
        List<Function> list = new ArrayList<Function>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.        
        String SEARCH = "SELECT * FROM spm391_bl5.function \n";
        PreparedStatement preparedStatement = null;

        if (teamId.equals("all") && statusId.equals("all") && submitStatus.equals("all") && title.equals("")) {
            preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } else {
            if (!teamId.equals("all") && !statusId.equals("all") && !submitStatus.equals("all") && !title.equals("")) {
                SEARCH += "where spm391_bl5.function.team_id = ? and spm391_bl5.function.status_id = ? and spm391_bl5.function.submit_status = ? and (title like ? or feature like ?);";
                preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setInt(1, Integer.parseInt(teamId));
                preparedStatement.setInt(2, Integer.parseInt(statusId));
                preparedStatement.setInt(3, Integer.parseInt(submitStatus));
                preparedStatement.setString(4, "%" + title + "%");
                preparedStatement.setString(5, "%" + title + "%");
            } else {
                if (!teamId.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where spm391_bl5.function.team_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(teamId));
                    }
                }
                if (!statusId.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where spm391_bl5.function.status_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(statusId));
                    } else {
                        SEARCH += "and spm391_bl5.function.status_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!teamId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(teamId));
                        }
                        preparedStatement.setInt(2, Integer.parseInt(statusId));
                    }

                }
                if (!submitStatus.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where spm391_bl5.function.submit_status = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(submitStatus));
                    } else {
                        SEARCH += "and spm391_bl5.function.submit_status = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!teamId.equals("all") && !statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(teamId));
                            preparedStatement.setInt(2, Integer.parseInt(statusId));
                            preparedStatement.setInt(3, Integer.parseInt(submitStatus));
                        } else {
                            if (!teamId.equals("all")) {
                                preparedStatement.setInt(1, Integer.parseInt(teamId));
                            }
                            if (!statusId.equals("all")) {
                                preparedStatement.setInt(1, Integer.parseInt(statusId));
                            }

                            preparedStatement.setInt(2, Integer.parseInt(submitStatus));
                        }
                    }
                }
                if (!title.equals("")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where (title like ? or feature like ?) ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setString(1, "%" + title + "%");
                        preparedStatement.setString(2, "%" + title + "%");
                    } else {
                        SEARCH += "and (title like ? or feature like ?)";
                        int number = 1;
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!teamId.equals("all") && submitStatus.equals("all") && statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(teamId));
                        preparedStatement.setString(2, "%" + title + "%");
                        preparedStatement.setString(3, "%" + title + "%");
                        }
                        else if (!statusId.equals("all") && teamId.equals("all") && submitStatus.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(statusId));
                        preparedStatement.setString(2, "%" + title + "%");
                        preparedStatement.setString(3, "%" + title + "%");
                        }
                        else if (!submitStatus.equals("all") && teamId.equals("all") && statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(submitStatus));
                        preparedStatement.setString(2, "%" + title + "%");
                        preparedStatement.setString(3, "%" + title + "%");
                        }
                        else if (!teamId.equals("all") && !submitStatus.equals("all") && statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(teamId));
                            preparedStatement.setInt(2, Integer.parseInt(submitStatus));
                        preparedStatement.setString(3, "%" + title + "%");
                        preparedStatement.setString(4, "%" + title + "%");
                        }
                        else if (!teamId.equals("all") && submitStatus.equals("all") && !statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(teamId));
                            preparedStatement.setInt(2, Integer.parseInt(statusId));
                        preparedStatement.setString(3, "%" + title + "%");
                        preparedStatement.setString(4, "%" + title + "%");
                        }
                        else if (teamId.equals("all") && !submitStatus.equals("all") && !statusId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(submitStatus));
                            preparedStatement.setInt(2, Integer.parseInt(statusId));
                        preparedStatement.setString(3, "%" + title + "%");
                        preparedStatement.setString(4, "%" + title + "%");
                        }
//                        preparedStatement.setString(2, "%" + title + "%");
//                        preparedStatement.setString(3, "%" + title + "%");
                    }
                }
            }
        };
        System.out.println("++++++++++ " + SEARCH);

        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Function function = new Function();
            function.setFunctionId(rs.getInt(1));
            function.setTeamId(rs.getInt(2));
            if (rs.getString(3).length() > 60) {
                function.setTitle(rs.getString(3).substring(0, 60) + "...");
            } else {
                function.setTitle(rs.getString(3));
            };
            if (rs.getString(4).length() > 60) {
                function.setFeature(rs.getString(4).substring(0, 60) + "...");
            } else {
                function.setFeature(rs.getString(4));
            };
            function.setPriority(rs.getInt(5));
            function.setIsClosed(rs.getBoolean(6));

            function.setStatusId(rs.getInt(7));
            function.setSubmitStatus(rs.getInt(8));

            if (rs.getString(9).length() > 60) {
                function.setDescription(rs.getString(9).substring(0, 60) + "...");
            } else {
                function.setDescription(rs.getString(9));
            };
            list.add(function);
        }
        connection.close();
        return list;
    }

    public ClassSetting getClassSettingById(int id) throws SQLException, ClassNotFoundException {
        ClassSetting classSetting = new ClassSetting();
        Connection connection = ConnectionContext.getConnection();
        String QUERY = "SELECT * FROM spm391_bl5.class_setting WHERE spm391_bl5.setting_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.            
            classSetting.setSettingId(rs.getInt(1));

            classSetting.setTypeId(rs.getInt(2));
            classSetting.setSettingTitle(rs.getString(3));
            classSetting.setSettingValue(rs.getString(4));
            classSetting.setDisplayOrder(rs.getInt(5));
            classSetting.setClassId(rs.getInt(6));
            classSetting.setStatus(rs.getBoolean(7));
            classSetting.setDescription(rs.getString(8));
        }

        connection.close();
        return classSetting;
    }

    public int updateFunction(Function function) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_FUNCTION = "UPDATE spm391_bl5.function"
                + "  SET title = ?, feature = ?, priority = ?, status_id = ?, submit_status = ?, description = ? "
                + "  WHERE function_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_FUNCTION, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
//            preparedStatement.setInt(1, function.getTeamId());
            preparedStatement.setString(1, function.getTitle());
            preparedStatement.setString(2, function.getFeature());
            preparedStatement.setInt(3, function.getPriority());
            preparedStatement.setInt(4, function.getStatusId());
            preparedStatement.setInt(5, function.getSubmitStatus());
            preparedStatement.setString(6, function.getDescription());
            preparedStatement.setInt(7, function.getFunctionId());

            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int addFunction(Function function) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_SUBJECT = "INSERT INTO spm391_bl5.function"
                + "  (team_id, title, feature, priority, is_closed, status_id, submit_status, description) VALUES "
                + "  (?,?,?,?,?,?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_SUBJECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setInt(1, function.getTeamId());
            preparedStatement.setString(2, function.getTitle());
            preparedStatement.setString(3, function.getFeature());
            preparedStatement.setInt(4, function.getPriority());
            preparedStatement.setBoolean(5, function.isIsClosed());
            preparedStatement.setInt(6, function.getStatusId());
            preparedStatement.setInt(7, function.getSubmitStatus());
            preparedStatement.setString(8, function.getDescription());
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int changeStatusFunction(int id, int status) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        String CHANGE_STATUS = "UPDATE spm391_bl5.function"
                + " SET status_id = ? "
                + "WHERE function_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                CHANGE_STATUS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }
}
