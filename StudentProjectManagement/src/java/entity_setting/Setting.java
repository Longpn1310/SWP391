/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_setting;

/**
 *
 * @author ADMIN
 */
public class Setting {

    private int id;
    private int typeId;
    private String typeTitle;
    private String title;
    private String value;
    private String description;
    private int order;
    private boolean status;

    public Setting() {
    }

    public Setting(int id, int typeId, String typeTitle, String title, String value, String description, int order, boolean status) {
        this.id = id;
        this.typeId = typeId;
        this.typeTitle = typeTitle;
        this.title = title;
        this.value = value;
        this.description = description;
        this.order = order;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
