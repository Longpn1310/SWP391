/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_student;

import dbContext.ConnectionContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class StudentDao {

    public List<Student> getAllStudent() throws SQLException, ClassNotFoundException {
        List<Student> list = new ArrayList<Student>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery("select \n"
                + "class_user.user_id,\n"
                + "team.team_id,\n"
                + "user.full_name,\n"
                + "user.roll_number,\n"
                + "user.email,\n"
                + "class_user.is_team_leader,\n"
                + "class_user.ongoing_eval,\n"
                + "class_user.final_eval,\n"
                + "class_user.topic_eval,\n"
                + "class_user.status,\n"
                + "class_user.dropout_date\n"
                + "from class_user\n"
                + "inner join team on team.team_id = class_user.team_id\n"
                + "inner join class on class_user.class_id=class.class_id\n"
                + "inner join user on user.user_id=class_user.user_id;");

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Student clu = new Student();
            clu.setUserId(rs.getInt(1));
            clu.setTeamId(rs.getInt(2));
            clu.setUserName(rs.getString(3));
            clu.setRollNumber(rs.getString(4));
            clu.setEmail(rs.getString(5));
            clu.setLeader(rs.getBoolean(6));
            clu.setOngoingEval(rs.getFloat(7));
            clu.setFinalPresEval(rs.getFloat(8));
            clu.setFinalTopicEval(rs.getFloat(9));
            clu.setStatus(rs.getBoolean(10));
            clu.setDropoutDate(rs.getDate(11));
            list.add(clu);
        }
        connection.close();
        return list;
    }
}
