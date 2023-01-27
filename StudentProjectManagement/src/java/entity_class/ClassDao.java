/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class;

import dbContext.ConnectionContext;
import entity_setting.Setting;
import entity_subject.Subject;
import entity_user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ClassDao {

    public List<Class> getAllClassOfManager(int managerId) throws SQLException, ClassNotFoundException {
        List<Class> list = new ArrayList<Class>();
        Connection connection = ConnectionContext.getConnection();

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        String GET_All = "SELECT c.class_id, c.class_code, c.trainer_id, u.full_name, "
                + "c.subject_id, s.subject_code, c.term_id, st.setting_title, c.is_block5, c.status \n"
                + "FROM class c\n"
                + "inner join subject s\n"
                + "on c.subject_id = s.subject_id\n"
                + "inner join user u\n"
                + "on s.manager_id = u.user_id\n"
                + "inner join setting st\n"
                + "on c.term_id = st.setting_id\n"
                + "where manager_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu       

        preparedStatement.setInt(1, managerId);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Class classs = new Class();
            classs.setClassId(rs.getInt(1));
            classs.setClassCode(rs.getString(2));
            classs.setTrainerId(rs.getInt(3));
            classs.setTrainerName(rs.getString(4));
            classs.setSubjectId(rs.getInt(5));
            classs.setSubjectName(rs.getString(6));
            classs.setTermId(rs.getInt(7));
            classs.setTermtitle(rs.getString(8));
            classs.setBlock5(rs.getBoolean(9));
            classs.setStatus(rs.getBoolean(10));
            list.add(classs);
        }
        connection.close();
        return list;
    }

    public List<User> getAllTrainer() throws SQLException, ClassNotFoundException {

        List<User> list = new ArrayList<User>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM user\n"
                + "where role_id = 2";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
//        preparedStatement.setInt(1, roleId);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            User user = new User();
            user.setUserId(rs.getInt(1));
            user.setRollNumber(rs.getString(2));
            user.setFullName(rs.getString(3));
            list.add(user);
        }
        connection.close();
        return list;
    }

    public List<Subject> getAllSubject(int managerId) throws SQLException, ClassNotFoundException {

        List<Subject> list = new ArrayList<Subject>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT s.subject_id, s.subject_code FROM subject s\n"
                + "where manager_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu        
        preparedStatement.setInt(1, managerId);
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Subject subject = new Subject();
            subject.setSubjectId(rs.getInt(1));
            subject.setSubjectCode(rs.getString(2));
            list.add(subject);
        }
        connection.close();
        return list;
    }

    public List<Setting> getAllTerm() throws SQLException, ClassNotFoundException {

        List<Setting> list = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT s.setting_id, s.setting_title FROM setting s\n"
                + "where type_id = 4;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu        
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Setting setting = new Setting();
            setting.setId(rs.getInt(1));
            setting.setTitle(rs.getString(2));
            list.add(setting);
        }
        connection.close();
        return list;
    }

    public int changeStatusClass(int id, boolean classStatus) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        String CHANGE_STATUS = "UPDATE class\n"
                + "SET status = ? \n"
                + "WHERE class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                CHANGE_STATUS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            if (classStatus == true) {
                preparedStatement.setBoolean(1, false);
            } else {
                preparedStatement.setBoolean(1, true);
            }
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }

    public List<Subject> searchClass(String status, String authorId, String title) throws SQLException, ClassNotFoundException {
        List<Subject> list = new ArrayList<Subject>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.        
        String SEARCH = "SELECT * FROM subject \n";
        PreparedStatement preparedStatement = null;

        if (status.equals("all") && authorId.equals("all") && title.equals("")) {
            preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } else {
            if (!status.equals("all") && !authorId.equals("all") && !title.equals("")) {
                SEARCH += "where subject.status = ? and subject.manager_id = ? and (subject_code like ? or subject_name like ?);";
                preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                preparedStatement.setInt(2, Integer.parseInt(authorId));
                preparedStatement.setString(3, "%" + title + "%");
                preparedStatement.setString(4, "%" + title + "%");
            } else {
                if (!status.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where subject.status = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                    }
                }
                if (!authorId.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where subject.manager_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(authorId));
                    } else {
                        SEARCH += "and subject.manager_id = ?";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!status.equals("all")) {
                            preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                        }
                        preparedStatement.setInt(2, Integer.parseInt(authorId));
                    }
                }
                if (!title.equals("")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where (subject_code like ? or subject_name like ?) ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setString(1, "%" + title + "%");
                        preparedStatement.setString(2, "%" + title + "%");
                    } else {
                        SEARCH += "and (subject_code like ? or subject_name like ?)";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!authorId.equals("all")) {
                            preparedStatement.setInt(1, Integer.parseInt(authorId));
                        }
                        if (!status.equals("all")) {
                            preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                        }
                        preparedStatement.setString(2, "%" + title + "%");
                        preparedStatement.setString(3, "%" + title + "%");
                    }
                }
            }
        }

        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Subject subject = new Subject();
            subject.setSubjectId(rs.getInt(1));
            subject.setSubjectCode(rs.getString(2));
            if (rs.getString(3).length() > 60) {
                subject.setSubjectName(rs.getString(3).substring(0, 60) + "...");
            } else {
                subject.setSubjectName(rs.getString(3));
            }
            subject.setManagerId(rs.getInt(4));
            subject.setStatus(rs.getBoolean(5));
            list.add(subject);
        }
        connection.close();
        return list;
    }

    public Class getClassById(int id) throws SQLException, ClassNotFoundException {
        Class classs = new Class();
        Connection connection = ConnectionContext.getConnection();
        String GET_BY_ID = "SELECT * \n"
                + "FROM class c\n"
                + "where c.class_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.            
            classs.setClassId(rs.getInt(1));
            classs.setClassCode(rs.getString(2));
            classs.setTrainerId(rs.getInt(3));
            classs.setSubjectId(rs.getInt(4));
            classs.setTermId(rs.getInt(5));
            classs.setBlock5(rs.getBoolean(6));
            classs.setStatus(rs.getBoolean(7));
        }

        connection.close();
        return classs;
    }

    public int addClass(Class classs) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "INSERT INTO `class` (`class_code`, `trainer_id`, `subject_id`, `term_id`, `is_block5`, `status`) \n"
                + "VALUES ( ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setString(1, classs.getClassCode());
            preparedStatement.setInt(2, classs.getTrainerId());
            preparedStatement.setInt(3, classs.getSubjectId());
            preparedStatement.setInt(4, classs.getTermId());
            preparedStatement.setBoolean(5, classs.isBlock5());
            preparedStatement.setBoolean(6, classs.isStatus());
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int updateClass(Class classs) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String UPDATE = "UPDATE `class`\n"
                + "SET `class_code` = ?, `trainer_id` = ?, `subject_id` = ?, `term_id` = ?, `is_block5` = ?, `status` = ?\n"
                + "WHERE (`class_id` = ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setString(1, classs.getClassCode());
            preparedStatement.setInt(2, classs.getTrainerId());
            preparedStatement.setInt(3, classs.getSubjectId());
            preparedStatement.setInt(4, classs.getTermId());
            preparedStatement.setBoolean(5, classs.isBlock5());
            preparedStatement.setBoolean(6, classs.isStatus());
            preparedStatement.setInt(7, classs.getClassId());
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }
}
