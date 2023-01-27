/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_issue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dbContext.ConnectionContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entity_milestone.Milestone;
import entity_setting.Setting;
import entity_team.Team;
import entity_user.User;
import entity_function.ClassSetting;
import java.io.IOException;
import java.util.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author trung
 */
public class IssueDao {  // tao xong dao cho getAllIssue va getAll cac truong filter

    public List<Issue> getAllIssue() throws SQLException, ClassNotFoundException {
        List<Issue> listIssue = new ArrayList<Issue>();

        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM issue;");
        while (rs.next()) {
//            java.util.Date newDate = rs.getDate(10);
//    java.util.Date sqlDate = new java.util.Date(newDate.getTime());

            Issue issue = new Issue();
            issue.setIssueId(rs.getInt(1));
            issue.setTitle(rs.getString(2));
            issue.setTypeId(rs.getString(3));
            issue.setFunctionIds(rs.getString(4));
            issue.setTeamId(rs.getInt(5));
            issue.setMilestoneId(rs.getInt(6));
            issue.setAssignerId(rs.getInt(7));
            issue.setAssigneeId(rs.getInt(8));
            issue.setIsClosed(rs.getBoolean(9));
//            issue.setDueDate(sqlDate);
            issue.setStatusId(rs.getString(11));
            issue.setDescription(rs.getString(12));
            issue.setUrl(rs.getString(13));
            listIssue.add(issue);
        }
        connection.close();
        return listIssue;
    }

    public List<Milestone> getAllMilestone() throws SQLException, ClassNotFoundException {
        List<Milestone> listMilestone = new ArrayList<Milestone>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM milestone;");
        while (rs.next()) {
            Milestone milestone = new Milestone();
            milestone.setMilestoneId(rs.getInt(1));
        }
        connection.close();
        return listMilestone;
    }

    public List<Setting> getAllSetting() throws SQLException, ClassNotFoundException {
        List<Setting> listSetting = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM setting WHERE type_id = 5;");
        while (rs.next()) {
            Setting setting = new Setting();
            setting.setId(rs.getInt(1));
            setting.setTypeId(rs.getInt(2));
            setting.setTitle(rs.getString(3));
            listSetting.add(setting);
        }
        connection.close();
        return listSetting;
    }

    public List<Team> getAllTeam() throws SQLException, ClassNotFoundException {
        List<Team> listTeam = new ArrayList<Team>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM team;");
        while (rs.next()) {
            Team team = new Team();
            team.setTeam_id(rs.getInt(1));
        }
        connection.close();
        return listTeam;
    }

    public List<User> getAllUser() throws SQLException, ClassNotFoundException {
        List<User> listUser = new ArrayList<User>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM user;");
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt(1));
            user.setFullName(rs.getString(3));
            listUser.add(user);
        }
        connection.close();
        return listUser;
    }

    public List<ClassSetting> getAllClassSetting() throws SQLException, ClassNotFoundException {
        List<ClassSetting> listClassSetting = new ArrayList<ClassSetting>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = statement.executeQuery("SELECT * FROM class_setting WHERE type_id = 2;");
        while (rs.next()) {
            ClassSetting classSetting = new ClassSetting();
            classSetting.setSettingId(rs.getInt(1));
            classSetting.setTypeId(rs.getInt(2));
            classSetting.setSettingTitle(rs.getString(3));
        }
        connection.close();
        return listClassSetting;
    }           

    public void syncIssue() throws IOException, SQLException, ClassNotFoundException, ParseException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("PRIVATE-TOKEN", "glpat-Anax6goXt12zg84v4aKu")
                .url("https://gitlab.com/api/v4/projects/38406507/issues?per_page=100")
                .build();

        Gson g = new Gson();
        JsonParser parser = new JsonParser();
        Response response = client.newCall(request).execute();
        JsonArray objects = (JsonArray) parser.parse(response.body().string());
        System.out.println("============ " + objects);
        Connection connection = ConnectionContext.getConnection();

        String DELETE_ISSUE = " DELETE FROM issue";

        PreparedStatement preparedStatement = connection.prepareStatement(
                DELETE_ISSUE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.executeUpdate();

        String RESET_ID = "ALTER TABLE issue AUTO_INCREMENT=0";
        preparedStatement = connection.prepareStatement(
                RESET_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.executeUpdate();

        String INSERT_ISSUE = "INSERT INTO issue"
                + "  (title, type_id, function_ids, team_id, milestone_id, assigner_id, assignee_id, is_closed, due_date, status_id, description, url) VALUES "
                + "  (?,?,?,?,?,?,?,?,?,?,?,?);";

        preparedStatement = connection.prepareStatement(
                INSERT_ISSUE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        for (int i = objects.size() - 1; i >= 0; i--) {


             if(objects.get(i).getAsJsonObject().get("due_date") != null) {
                 preparedStatement.setString(9, objects.get(i).getAsJsonObject().get("due_date").toString().replaceAll("\"", ""));
             }else{
                 preparedStatement.setString(9, "");
             }
                
//                java.sql.Date sqlDate = java.sql.Date.valueOf("2022-03-18");
            JsonArray labels = objects.get(i).getAsJsonObject().get("labels").getAsJsonArray();
            
            if (labels != null) {
                if (labels.size() == 1) {
                    if (labels.get(0).toString().contains("_")) {
                        String[] parts = labels.get(0).getAsString().split("_");
//                         for(String part: parts){
//                             System.out.println("============" + part);
//                         }

                        preparedStatement.setString(10, parts[1]);
                    preparedStatement.setString(2, " ");
                    } else {
                        preparedStatement.setString(2, labels.get(0).getAsString());
                    preparedStatement.setString(10, " ");
                    }
                } else {
                    String[] parts = labels.get(0).getAsString().split("_");
                    preparedStatement.setString(10, parts[1]);
                    preparedStatement.setString(2, labels.get(1).getAsString());
                }
            } else {
                
                    preparedStatement.setString(10, " ");
                    preparedStatement.setString(2, " ");
            }

            preparedStatement.setString(1, objects.get(i).getAsJsonObject().get("title").toString().substring(1, objects.get(i).getAsJsonObject().get("title").toString().length() - 1));
//            preparedStatement.setString(2, "7");
            preparedStatement.setString(3, "1");
            preparedStatement.setInt(4, 3);
            preparedStatement.setInt(5, Integer.parseInt(objects.get(i).getAsJsonObject().get("milestone").getAsJsonObject().get("iid").toString()));
            preparedStatement.setInt(6, Integer.parseInt(objects.get(i).getAsJsonObject().get("assignee").getAsJsonObject().get("id").toString()));
            preparedStatement.setInt(7, Integer.parseInt(objects.get(i).getAsJsonObject().get("author").getAsJsonObject().get("id").toString()));
            preparedStatement.setBoolean(8, objects.get(i).getAsJsonObject().get("is_closed") != null);
//            preparedStatement.setDate(9, sqlDate);
//            preparedStatement.setString(10, "3"); 
           preparedStatement.setString(11, objects.get(i).getAsJsonObject().get("description").toString().replaceAll("\"", ""));
            preparedStatement.setString(12, objects.get(i).getAsJsonObject().get("web_url").getAsString());
            preparedStatement.executeUpdate();
        }
        connection.close();

    }
    
    
    public List<Issue> searchIssue(String title) throws SQLException, ClassNotFoundException{
        List<Issue> list = new ArrayList<Issue>();
        Connection connection = ConnectionContext.getConnection();
        
        String SEARCH = "SELECT * FROM spm391_bl5.issue where title like ?  \n";
        PreparedStatement preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
        
        preparedStatement.setString(1, "%" + title + "%");
       
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Issue issue = new Issue();
            issue.setIssueId(rs.getInt(1));
            issue.setTitle(rs.getString(2));
            issue.setTypeId(rs.getString(3));
            issue.setFunctionIds(rs.getString(4));
            issue.setTeamId(rs.getInt(5));
            issue.setMilestoneId(rs.getInt(6));
            issue.setAssignerId(rs.getInt(7));
            issue.setAssigneeId(rs.getInt(8));
            issue.setIsClosed(rs.getBoolean(9));
//            issue.setDueDate(sqlDate);
            issue.setStatusId(rs.getString(11));
            issue.setDescription(rs.getString(12));
            issue.setUrl(rs.getString(13));
            list.add(issue);
        }
        
    return list;
    
    }
}
