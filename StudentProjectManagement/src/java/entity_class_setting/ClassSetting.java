/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class_setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HaiLong
 */
@Builder
@Getter
@Setter
public class ClassSetting {
//    SELECT setting_id, 
//		type_id, 
//		setting_title,
//        setting_value, display_order,
//        status
//FROM spm391_bl5.class_setting;

    private int settingId, typeId, displayOrder, classId;
    private String settingTitle, settingValue, description, typeName;
    private boolean status;
}
