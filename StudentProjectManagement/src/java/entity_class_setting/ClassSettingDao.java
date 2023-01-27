/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class_setting;

import dbContext.ConnectionContext;
import entity_class.Class;
import entity_setting.Setting;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HaiLong
 */
public class ClassSettingDao extends ConnectionContext {

    public List<Setting> getAllSetting() throws SQLException, ClassNotFoundException {

        List<Setting> list = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.setting where type_id=3;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu.       
        ResultSet rs = preparedStatement.executeQuery();
        try {
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Setting setting = new Setting();
                setting.setId(rs.getInt(1));
                setting.setTitle(rs.getString("setting_title"));
                list.add(setting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return list;
    }

    public ClassSetting getById(int id) {
        StringBuilder sql = new StringBuilder("SELECT class_setting.*\n"
                + "FROM spm391_bl5.class_setting\n"
                + "where setting_id=?");

        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            pre.setInt(1, id);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ClassSetting cs = ClassSetting.builder()
                        .displayOrder(rs.getInt("display_order"))
                        .settingId(rs.getInt("setting_id"))
                        .settingTitle(rs.getString("setting_title"))
                        .classId(rs.getInt("class_id"))
                        .typeId(rs.getInt("type_id"))
                        .status(rs.getBoolean("status"))
                        .settingValue(rs.getString("setting_value"))
                        .description(rs.getString("description"))
                        .build();
                return cs;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ArrayList<ClassSetting> search(Integer typeId, String settingValue, Boolean status, int page, String sort) {
        StringBuilder sql = new StringBuilder("SELECT class_setting.setting_id, \n"
                + "class_setting.type_id,class_setting.setting_title, setting_value, class_setting.display_order,\n"
                + "class_setting.description, setting.setting_title as type_name,\n"
                + "           class_setting.status\n"
                + "           FROM spm391_bl5.class_setting\n"
                + "           join spm391_bl5.setting on class_setting.type_id = setting.setting_id\n"
                + "           where setting.type_id = 3\n"
                + "and setting_value like ? ");
        ArrayList<ClassSetting> list = new ArrayList<>();
        if (typeId != null) {
            sql.append("and class_setting.type_id  = ?\n");
        }
        if (status != null) {
            sql.append("and class_setting.status = ?\n");
        }
        sql.append("order by " + sort + "\n");
        int start = (page - 1) * 5;
        int end = start + 5;
        sql.append(" limit " + start + ",5");
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            pre.setString(1, '%' + settingValue + '%');
            int dem = 1;
            if (typeId != null) {
                dem++;
                pre.setInt(dem, typeId);

            }
            if (status != null) {
                dem++;
                pre.setBoolean(dem, status);
            }
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ClassSetting cs = ClassSetting.builder()
                        .displayOrder(rs.getInt("display_order"))
                        .settingId(rs.getInt("setting_id"))
                        .settingTitle(rs.getString("setting_title"))
                        .typeId(rs.getInt("type_id"))
                        .typeName(rs.getString("type_name"))
                        .status(rs.getBoolean("status"))
                        .settingValue(rs.getString("setting_value"))
                        .build();
                list.add(cs);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<Class> getAllClass() {
        String sql = " select * FROM spm391_bl5.class where status = 1";
        ArrayList<Class> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Class cl = new Class();
                cl.setClassId(rs.getInt("class_id"));
                cl.setClassCode(rs.getString("class_code"));

                list.add(cl);
            }
        } catch (Exception e) {
        }
        return list;

    }
    public ArrayList<Class> getAllClass(int trainerId) {
        String sql = " select * FROM spm391_bl5.class where status = 1 and trainer_id="+trainerId;
        ArrayList<Class> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Class cl = new Class();
                cl.setClassId(rs.getInt("class_id"));
                cl.setClassCode(rs.getString("class_code"));

                list.add(cl);
            }
        } catch (Exception e) {
        }
        return list;

    }

    public int count(String settingTitle, String settingValue, Boolean status) {
        StringBuilder sql = new StringBuilder("SELECT count(*) as count "
                + "FROM spm391_bl5.class_setting\n"
                + "where 1=1\n"
                + "and setting_value like ? ");
        ArrayList<ClassSetting> list = new ArrayList<>();
        if (settingTitle != null) {
            sql.append("and setting_title = ?\n");
        }
        if (status != null) {
            sql.append("and status = ?\n");
        }

        try {
            Connection connection = getConnection();
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            pre.setString(1, '%' + settingValue + '%');
            int dem = 1;
            if (settingTitle != null) {
                dem++;
                pre.setString(dem, settingTitle);

            }
            if (status != null) {
                dem++;
                pre.setBoolean(dem, status);
            }
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    void add(ClassSetting cs) {
        String sql = "INSERT INTO `spm391_bl5`.`class_setting`\n"
                + "("
                + "`type_id`,\n"
                + "`setting_title`,\n"
                + "`setting_value`,\n"
                + "`display_order`,\n"
                + "`class_id`,\n"
                + "`status`,\n"
                + "`description`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, cs.getTypeId());
            pre.setString(2, cs.getSettingTitle());
            pre.setString(3, cs.getSettingValue());
            pre.setInt(4, cs.getDisplayOrder());
            pre.setInt(5, cs.getClassId());
            pre.setBoolean(6, cs.isStatus());
            pre.setString(7, cs.getDescription());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void update(ClassSetting cs) {
        String sql = "UPDATE `spm391_bl5`.`class_setting`\n"
                + "SET\n"
                + "`type_id` = ?,\n"
                + "`setting_title` =?,\n"
                + "`setting_value` = ?,\n"
                + "`display_order` = ?,\n"
                + "`class_id` = ?,\n"
                + "`status` =?,\n"
                + "`description` =?\n"
                + "WHERE `setting_id` =?";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, cs.getTypeId());
            pre.setString(2, cs.getSettingTitle());
            pre.setString(3, cs.getSettingValue());
            pre.setInt(4, cs.getDisplayOrder());
            pre.setInt(5, cs.getClassId());
            pre.setBoolean(6, cs.isStatus());
            pre.setString(7, cs.getDescription());
            pre.setInt(8, cs.getSettingId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
