/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author
 */
public class Constant {

    public static final String MSG_EMPTY = "Khong duoc de trong";
    public static final String TYPE_SUBJECT_QUANTITY = "Quality";
    public static final String TYPE_SUBJECT_COMPLEXITY = "Complexity";
    public static final String ADMIN_URL[] = {"/setting", "/userlist", "/userdetail", "/subject"};
    public static final String MANAGER_URL[] = {"/iteration", "/subject-setting", "/classlist", "/criteria"};
    public static final String TRAINER_URL[] = {"/team", "/milestone", "/class-setting"};
    public static final String STUDENT_URL[] = {"/team-dashboard","/tracking"};
    public static final String COMMON_URL[] = {"netdna", "image","css", 
        "vendor", "/login", "/home", "/logout", "/register", 
        "/get-new-password", "css", "js", "image", "common", "/team", 
        "/class-setting", "ajax", "/team-dashboard", "maxcdn", "/criteria",
        "class-eval", "webfont", "tracking", "add-tracking","boo",
        "jquery"
    };

    public static String[] getURLByRole(int roleId) {
        switch (roleId) {
            case 1:
                return ADMIN_URL;
            case 2:
                return MANAGER_URL;
            case 3:
                return TRAINER_URL;
            case 4:
                return STUDENT_URL;
        }
        return COMMON_URL;

    }
}
