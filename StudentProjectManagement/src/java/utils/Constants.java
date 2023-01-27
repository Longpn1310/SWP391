package utils;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
//dùng thư viện org.apache.httpcomponents (httpclient, httpcore, fluent) để gửi
//request từ bên trong class servlet, thư viện gson để convert dữ liệu từ dạng json và ngược lại
//Dùng để lưu client_id, client_secret, redirect_uri của ứng dung mình tạo qua Google+
public class Constants {

    public static String GOOGLE_CLIENT_ID = "571220986659-t6e8dm1j1jsf5h1o48s04qhnfca7sjle.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/StudentProjectManagement/login-google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
    static Random random = new Random();

    public static String generatePassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

        char[] password = new char[9];

        for (int i = 0; i < 9; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password.toString();

    }
}
