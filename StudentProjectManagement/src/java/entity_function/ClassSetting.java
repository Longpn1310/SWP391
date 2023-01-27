/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_function;

/**
 *
 * @author trung
 */
public class ClassSetting {
    private int settingId;
    private int typeId;
    private String settingTitle;
    private String settingValue;
    private int displayOrder;
    private int classId;
    private boolean status;
    private String description;

    public ClassSetting() {
    }

    public ClassSetting(int settingId, int typeId, String settingTitle, String settingValue, int displayOrder, int classId, boolean status, String description) {
        this.settingId = settingId;
        this.typeId = typeId;
        this.settingTitle = settingTitle;
        this.settingValue = settingValue;
        this.displayOrder = displayOrder;
        this.classId = classId;
        this.status = status;
        this.description = description;
    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getSettingTitle() {
        return settingTitle;
    }

    public void setSettingTitle(String settingTitle) {
        this.settingTitle = settingTitle;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}


