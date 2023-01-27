/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author ADMIN
 */
public enum SettingType {
    ROLE("Role"),
    SUBJECT_SETTING_TYPE("Subject setting type"),
    CLASS_SETTING_TYPE ("Class setting type");
    
    private String value;

    private SettingType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
        
}
