package com.example.demo;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailFunction implements Serializable{

    public static void sendMail(String recipient, String subject, String subheading, String context) {
            try {
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                String myAccountEmail = "kalokyeep@gmail.com";
                String password = "cjhbqzpatbuxgehv";

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myAccountEmail, password);
                    }
                });
                Message message = prepareMessage(session, myAccountEmail, recipient, subject, subheading, context);
                Transport.send(message);
            } catch (Exception e) {
                System.out.println("\n=========================================================================================================================================\n");
                System.out.println("Failed to send email ");
                System.out.println("=========================================================================================================================================\n");
            }     
    }
    

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String subheading, String context) {
        try {
            Message message = new MimeMessage(session);
            InternetAddress senderAddress = new InternetAddress(myAccountEmail, "EZTrade");
            message.setFrom(senderAddress);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            // Set the subject of the email
            message.setSubject(subject);

            // Create the HTML message content
            String htmlContent = "<html><body>" +
                    "<h1 style=\"color: #4e74b5; font-size: 24px; font-weight: bold;\">" + "Important Message from EZTrade regarding your trading activity" + "</h1>\n" +
                    "<h2 style=\"text-decoration: underline;\">" + subheading + "</h2>\n" +
                    "<p>" + context + "</p>\n" +
                    "<br>" +
                    "<img src=\"https://img.freepik.com/free-vector/hand-drawn-stock-market-concept_23-2149163669.jpg?w=996&t=st=1685554854~exp=1685555454~hmac=ba88cfb084b849837c69f2d87f4f7322b9bc550e8f2973239da8b75089f081ae\" width=\"480\" height=\"360\">" + // Add the image tag with the image URL
                    "<footer>" +
                    "<p>If you faced any trading issue, don't hesitate to reach out to 22004810@siswa.um.edu.my for technical support!</p>" +
                    "<p>Best of luck,</p>" +
                    "<p>Yeep Ka Lok [ CEO of EZTrade ]</p>" +
                    "</footer>" +
                    "<blockquote style=\"font-style: italic; font-weight: bold;\">" + "\"Striving for the best of service\" - EZTrade" + "</blockquote>" +
                    "</body></html>";
            // Set the HTML content of the email
            message.setContent(htmlContent, "text/html");
            return message;
        } catch (Exception e) {
            System.out.println("\n=========================================================================================================================================\n");
            System.out.println("Email malfunctions!");
            System.out.println("=========================================================================================================================================\n");

        }
        return null;
    }

}
