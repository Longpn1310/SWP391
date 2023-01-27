/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_subject_setting;

import dbContext.ConnectionContext;
import static dbContext.ConnectionContext.getConnection;
import entity_subject.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HaiLong
 */
public class SubjectSettingDao extends ConnectionContext {

    public ArrayList<Subject> getAll() {
        String sql = "SELECT * FROM spm391_bl5.subject where status = 1;";
        ArrayList<Subject> list = new ArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                list.add(subject);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<SubjectSetting> search(int subjectId, Integer typeId, Boolean status, String settingTitle, int page) {
//"Filters: Subject (1), Type, Status
//Column: Id, Type, Title, Value, Display Order, Status
//Row Actions: View/Edit, Activate/Deactivate
//Global Actions: Filter, Search (by Title, Value), Sort (columns), Paginate
//Roles: manager ~ full access, trainer ~ read only
//Status: Active, Inactive"
        String sql = "SELECT setting_id, subject_name,\n"
                + "`type_id`,\n"
                + "`setting_title`,\n"
                + "`setting_value`,\n"
                + "`display_order`,\n"
                + "ss.`status`,\n"
                + "ss.`description`\n"
                + " FROM spm391_bl5.subject_setting as ss\n"
                + " join spm391_bl5.subject as s\n"
                + " on ss.subject_id = s.subject_id\n"
                + " where ss.subject_id = ?\n"
                //                + " and ss.type_id = ?\n"
                //                + " and ss.status = ?\n"
                + " and (ss.setting_title like ? or ss.setting_value like ?)\n";
//                + " order by subject_name\n"
//                + " limit ?,?";
        int start = page * 5;
        int end = start + 5;
        if (typeId != null) {
            sql += " and ss.type_id = " + typeId + "\n";
        }
        if (status != null) {
            sql += " and ss.status = " + status + "\n";
        }
        sql += " order by subject_name\n"
                + " limit ?,?";
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, subjectId);
            pre.setString(2, '%' + settingTitle + '%');
            pre.setString(3, '%' + settingTitle + '%');
            pre.setInt(4, start);
            pre.setInt(5, end);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                SubjectSetting ss = SubjectSetting.builder()
                        .subjectName(rs.getString("subject_name"))
                        .description(rs.getString("description"))
                        .displayOrder(rs.getInt("display_order"))
                        .settingValue(rs.getString("setting_value"))
                        .settingTitle(rs.getString("setting_title"))
                        .typeId(rs.getInt("type_id"))
                        .status(rs.getInt("status"))
                        .settingId(rs.getInt("setting_id"))
                        .build();
                list.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }

    public SubjectSetting getById(int id) {
        String sql = "select setting_id,\n"
                + "		subject_id,\n"
                + "        type_id,\n"
                + "        setting_title,\n"
                + "        setting_value,\n"
                + "        display_order,\n"
                + "        status,\n"
                + "        description\n"
                + " from spm391_bl5.subject_setting\n"
                + " where setting_id= ?";
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                SubjectSetting ss = SubjectSetting.builder()
                        .description(rs.getString("description"))
                        .displayOrder(rs.getInt("display_order"))
                        .settingValue(rs.getString("setting_value"))
                        .settingTitle(rs.getString("setting_title"))
                        .typeId(rs.getInt("type_id"))
                        .status(rs.getInt("status"))
                        .settingId(rs.getInt("setting_id"))
                        .subjectId(rs.getInt("subject_id"))
                        .build();
                return ss;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int count(int subjectId, int typeId, int status, String settingTitle) {
        String sql = "SELECT count(*) as count "
                + " FROM spm391_bl5.subject_setting as ss\n"
                + " join spm391_bl5.subject as s\n"
                + " on ss.subject_id = s.subject_id\n"
                + " where ss.subject_id = ?\n"
                + " and ss.type_id = ?\n"
                + " and ss.status = ?\n"
                + " and (ss.setting_title like ? or ss.setting_value like ?)\n";

        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, subjectId);
            pre.setInt(2, typeId);
            pre.setInt(3, status);
            pre.setString(4, '%' + settingTitle + '%');
            pre.setString(5, '%' + settingTitle + '%');

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int add(SubjectSetting ss) {
        String sql = "INSERT INTO `spm391_bl5`.`subject_setting`\n"
                + "(`subject_id`,\n"
                + "`type_id`,\n"
                + "`setting_title`,\n"
                + "`setting_value`,\n"
                + "`display_order`,\n"
                + "`status`,\n"
                + "`description`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, ss.getSubjectId());
            pre.setInt(2, ss.getTypeId());
            pre.setString(3, ss.getSettingTitle());
            pre.setString(4, ss.getSettingValue());
            pre.setInt(5, ss.getDisplayOrder());
            pre.setInt(6, ss.getStatus());
            pre.setString(7, ss.getDescription());
            int res = pre.executeUpdate();
            return res;
        } catch (Exception ex) {

        }
        return 0;
    }

    public int update(SubjectSetting ss) {
        String sql = "UPDATE `spm391_bl5`.`subject_setting`\n"
                + "SET\n"
                + "\n"
                + "`subject_id` = ?,\n"
                + "`type_id` =?,\n"
                + "`setting_title` = ?,\n"
                + "`setting_value` = ?,\n"
                + "`display_order` = ?,\n"
                + "`status` =?,\n"
                + "`description` = ?\n"
                + "WHERE `setting_id` = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, ss.getSubjectId());
            pre.setInt(2, ss.getTypeId());
            pre.setString(3, ss.getSettingTitle());
            pre.setString(4, ss.getSettingValue());
            pre.setInt(5, ss.getDisplayOrder());
            pre.setInt(6, ss.getStatus());
            pre.setString(7, ss.getDescription());
            pre.setInt(8, ss.getSettingId());
            int res = pre.executeUpdate();
            return res;
        } catch (Exception ex) {

        }
        return 0;
    }

}
