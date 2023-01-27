/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_subject;

import dbContext.ConnectionContext;
import entity_user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SubjectDao {

    public List<Subject> getAllSubject() throws SQLException, ClassNotFoundException {
        List<Subject> list = new ArrayList<Subject>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery("SELECT * FROM subject;");

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

    public List<User> getAllAuthor() throws SQLException, ClassNotFoundException {

        List<User> list = new ArrayList<User>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM user\n"
                + "where role_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu
        preparedStatement.setInt(1, 2);
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

    public int changeStatusSubject(int id, boolean subjectStatus) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        String CHANGE_STATUS = "UPDATE subject\n"
                + "SET status = ? \n"
                + "WHERE subject_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                CHANGE_STATUS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            if (subjectStatus == true) {
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

    public List<Subject> searchSubject(String status, String authorId, String title) throws SQLException, ClassNotFoundException {
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

    public Subject getSubjectById(int id) throws SQLException, ClassNotFoundException {
        Subject subject = new Subject();
        Connection connection = ConnectionContext.getConnection();
        String INSERT_SUBJECT = "SELECT * FROM subject WHERE subject_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_SUBJECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.            
            subject.setSubjectId(rs.getInt(1));
            subject.setSubjectCode(rs.getString(2));
            if (rs.getString(3).length() > 60) {
                subject.setSubjectName(rs.getString(3).substring(0, 60) + "...");
            } else {
                subject.setSubjectName(rs.getString(3));
            }
            subject.setManagerId(rs.getInt(4));
            subject.setStatus(rs.getBoolean(5));
        }

        connection.close();
        return subject;
    }

    public int addSubject(Subject subject) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_SUBJECT = "INSERT INTO subject"
                + "  (subject_code, subject_name, manager_id, status, description) VALUES "
                + "  (?,?,?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_SUBJECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setString(1, subject.getSubjectCode());
            preparedStatement.setString(2, subject.getSubjectName());
            preparedStatement.setInt(3, subject.getManagerId());
            preparedStatement.setBoolean(4, subject.isStatus());
            preparedStatement.setString(5, subject.getDescription());
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int updateSubject(Subject subject) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT_SUBJECT = "UPDATE subject"
                + "  SET subject_code = ?, subject_name = ?, manager_id = ?, status = ?, description = ? "
                + "  WHERE subject_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_SUBJECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            preparedStatement.setString(1, subject.getSubjectCode());
            preparedStatement.setString(2, subject.getSubjectName());
            preparedStatement.setInt(3, subject.getManagerId());
            preparedStatement.setBoolean(4, subject.isStatus());
            preparedStatement.setString(5, subject.getDescription());
            preparedStatement.setInt(6, subject.getSubjectId());            
            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = 3;
        }
        connection.close();
        return result;
    }
}
