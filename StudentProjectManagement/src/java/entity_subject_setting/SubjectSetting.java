/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_subject_setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.bcel.Const;

/**
 *
 * @author HaiLong
 */
@Getter
@Builder
@Setter
public class SubjectSetting {
    private  int settingId;
    private  int subjectId;
    private  int typeId;
    private  String settingTitle;
    private  String settingValue;
    private  int displayOrder;
    private  int status;
    private  String description, subjectName;
}
