/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author admin
 * @author HIEU
 */
public class SendEmail {

    public static void send(String toAddress, String subject, String content) {
        try {
            String email = "toaintse04189@fpt.edu.vn";
            String password = "Thanhtoai1010";
            String host = "smtp.gmail.com";//or IP address

            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };

            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(email));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(content);
//            String server = "localhost";
//            int randNum = 25364;
//            String practiceName = "blueCross";
//            msg.setContent("<p>Hi there,</p><br /><a>We received a request to reset your password. <br />To reset your password and access your account, click the link below.</a><br />"
//                    + "<a href=\"<%= server%>:8080/RSIwork/ConfirmedResetPasswordPage.jsp?randNum=<%= randNum%>&practiceName=<%= practiceName%>\" Click Here </a>",
//                    "text/html");

            // sends the e-mail
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
