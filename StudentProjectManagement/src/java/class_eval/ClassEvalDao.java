/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class_eval;

import dbContext.ConnectionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author HaiLong
 */
public class ClassEvalDao extends ConnectionContext {

    public ArrayList<ClassEval> getAll() {
        String sql = "	select user.roll_number, user.full_name, class_user.ongoing_eval, class_user.final_eval, ie.total_grade, milestone_id\n"
                + "	 from spm391_bl5.user \n"
                + "	join spm391_bl5.class_user on user.user_id = class_user.user_id \n"
                + "	join spm391_bl5.iteration_evaluation as ie on ie.team_id = class_user.team_id \n"
                + "    order by milestone_id\n"
                + "	LIMIT 0, 500";
        ArrayList<ClassEval> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                ClassEval ce = new ClassEval();
                ce.setFullname(rs.getString("full_name"));
                ce.setOnGoing(rs.getInt("ongoing_eval"));
                ce.setRole(rs.getString("roll_number"));
                ce.setTotal(rs.getInt("final_eval"));
                ce.setMilestoneId(rs.getInt("milestone_id"));
                ce.setTotalGrad(rs.getInt("total_grade"));
                list.add(ce);
            }

        } catch (SQLException ex) {

        } catch (ClassNotFoundException ex) {

        }
        return list;
    }
}
