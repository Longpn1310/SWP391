/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_setting;

import entity_setting.Setting;
import dbContext.ConnectionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.SettingType;

/**
 *
 * @author ADMIN
 */
public class SettingDao {

    public List<Setting> getAllSetting() throws SQLException, ClassNotFoundException {

        List<Setting> list = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT * FROM spm391_bl5.setting;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu.       
        ResultSet rs = preparedStatement.executeQuery();
        try {
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Setting setting = new Setting();
                setting.setId(rs.getInt(1));
                setting.setTypeId(rs.getInt(2));
                if (setting.getTypeId() == 1) {
                    setting.setTypeTitle(SettingType.ROLE.getValue());
                } else if (setting.getTypeId() == 2) {
                    setting.setTypeTitle(SettingType.SUBJECT_SETTING_TYPE.getValue());
                } else {
                    setting.setTypeTitle(SettingType.CLASS_SETTING_TYPE.getValue());
                }
                setting.setTitle(rs.getString(3));
                if (rs.getString(4).length() > 50) {
                    setting.setValue(rs.getString(4).substring(0, 50) + "...");
                } else {
                    setting.setValue(rs.getString(4));
                }
                setting.setOrder(rs.getInt(5));
                setting.setStatus(rs.getBoolean(6));
                setting.setDescription(rs.getString(7));
                list.add(setting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return list;
    }

    public int changeStatusSetting(int id, boolean settingStatus) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        String CHANGE_STATUS = "UPDATE spm391_bl5.setting\n"
                + "SET status = ? \n"
                + "WHERE setting_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                CHANGE_STATUS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            if (settingStatus == true) {
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

    public List<Setting> searchSetting(String title, String status, String settingType) throws SQLException, ClassNotFoundException {
        List<Setting> list = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.        
        String SEARCH = "SELECT * FROM spm391_bl5.setting \n";
        PreparedStatement preparedStatement = null;

        if (title.equals("") && status.equals("all") && settingType.equals("all")) {
            preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } else {
            if (!title.equals("") && !status.equals("all") && !settingType.equals("all")) {
                SEARCH += "where setting.setting_title like ? and setting.status = ? and setting.type_id = ?;";
                preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setString(1, title);
                preparedStatement.setBoolean(2, Boolean.parseBoolean(status));
                preparedStatement.setInt(3, Integer.parseInt(settingType));
            } else {
                if (!title.equals("")) {
                    SEARCH += "where setting.setting_title = ? ";
                    preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    preparedStatement.setString(1, title);
                }
                if (!status.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where setting.status = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                    } else {
                        SEARCH += "and setting.status = ?";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!title.equals("")) {
                            preparedStatement.setString(1, title);
                        }
                        preparedStatement.setBoolean(2, Boolean.parseBoolean(status));
                    }

                }
                if (!settingType.equals("all")) {
                    if (!SEARCH.contains("where")) {
                        SEARCH += "where setting.type_id = ? ";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        preparedStatement.setInt(1, Integer.parseInt(settingType));
                    } else {
                        SEARCH += "and setting.type_id = ?";
                        preparedStatement = connection.prepareStatement(SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        if (!title.equals("")) {
                            preparedStatement.setString(1, title);
                        }
                        if (!status.equals("all")) {
                            preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
                        }
                        preparedStatement.setInt(2, Integer.parseInt(settingType));
                    }
                }
            }
        }

        ResultSet rs = preparedStatement.executeQuery();
        try {
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                Setting setting = new Setting();
                setting.setId(rs.getInt(1));
                setting.setTypeId(rs.getInt(2));
                if (setting.getTypeId() == 1) {
                    setting.setTypeTitle(SettingType.ROLE.getValue());
                } else if (setting.getTypeId() == 2) {
                    setting.setTypeTitle(SettingType.SUBJECT_SETTING_TYPE.getValue());
                } else {
                    setting.setTypeTitle(SettingType.CLASS_SETTING_TYPE.getValue());
                }
                setting.setTitle(rs.getString(3));
                if (rs.getString(4).length() > 50) {
                    setting.setValue(rs.getString(4).substring(0, 50) + "...");
                } else {
                    setting.setValue(rs.getString(4));
                }
                setting.setOrder(rs.getInt(5));
                setting.setStatus(rs.getBoolean(6));
                setting.setDescription(rs.getString(7));
                list.add(setting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return list;
    }

    public List<Setting> getAllSettingType() throws SQLException, ClassNotFoundException {

        List<Setting> list = new ArrayList<Setting>();
        Connection connection = ConnectionContext.getConnection();

        String GET_All = "SELECT type_id FROM setting\n"
                + "group by type_id;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_All, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Tạo đối tượng Statement chuyên để đọc dữ liệu.       
        ResultSet rs = preparedStatement.executeQuery();
        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            Setting setting = new Setting();
            setting.setTypeId(rs.getInt(1));
            if (setting.getTypeId() == 1) {
                setting.setTypeTitle(SettingType.ROLE.getValue());
            } else if (setting.getTypeId() == 2) {
                setting.setTypeTitle(SettingType.SUBJECT_SETTING_TYPE.getValue());
            } else {
                setting.setTypeTitle(SettingType.CLASS_SETTING_TYPE.getValue());
            }
            list.add(setting);
        }
        connection.close();
        return list;
    }

    public Setting getSettingById(int id) throws SQLException, ClassNotFoundException {
        Setting setting = new Setting();
        Connection connection = ConnectionContext.getConnection();
        String GET_BY_ID = "SELECT * FROM spm391_bl5.setting\n"
                + "where setting_id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        try {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.                
                setting.setId(rs.getInt(1));
                setting.setTypeId(rs.getInt(2));
                if (setting.getTypeId() == 1) {
                    setting.setTypeTitle(SettingType.ROLE.getValue());
                } else if (setting.getTypeId() == 2) {
                    setting.setTypeTitle(SettingType.SUBJECT_SETTING_TYPE.getValue());
                } else {
                    setting.setTypeTitle(SettingType.CLASS_SETTING_TYPE.getValue());
                }
                setting.setTitle(rs.getString(3));
                if (rs.getString(4).length() > 50) {
                    setting.setValue(rs.getString(4).substring(0, 50) + "...");
                } else {
                    setting.setValue(rs.getString(4));
                }
                setting.setOrder(rs.getInt(5));
                setting.setStatus(rs.getBoolean(6));
                setting.setDescription(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return setting;
    }

    public int addSetting(Setting setting) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "INSERT INTO `spm391_bl5`.`setting` (`type_id`, `setting_title`, `settin_value`, `description`, `display_order`, `status`) \n"
                + "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        try {
            preparedStatement.setInt(1, setting.getTypeId());
            preparedStatement.setString(2, setting.getTitle());
            preparedStatement.setString(3, setting.getValue());
            preparedStatement.setString(4, setting.getDescription());
            preparedStatement.setInt(5, setting.getOrder());
            preparedStatement.setBoolean(6, setting.isStatus());

            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            result = 3;
        }
        connection.close();
        return result;
    }

    public int updateSetting(Setting setting) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String UPDATE = "UPDATE `spm391_bl5`.`setting`\n"
                + "SET `type_id` = ?, \n"
                + "`setting_title` = ?, `settin_value` = ?, \n"
                + "`description` = ?, `display_order` = ?, \n"
                + "`status` = ? \n"
                + "WHERE `setting_id` = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(
                UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        try {

            preparedStatement.setInt(1, setting.getTypeId());
            preparedStatement.setString(2, setting.getTitle());
            preparedStatement.setString(3, setting.getValue());
            preparedStatement.setString(4, setting.getDescription());
            preparedStatement.setInt(5, setting.getOrder());
            preparedStatement.setBoolean(6, setting.isStatus());
            preparedStatement.setInt(7, setting.getId());

            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            result = 3;
        }
        connection.close();
        return result;
    }
}
