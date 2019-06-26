package com.teamLong.java401d.midterm.troublemaker.sendgrid;

import com.sendgrid.*;

public class EmailSender {
    public static void SendEmail(String user) {

        Email from = new Email("nguyenv2@outlook.com");
        String subject = "Welcome to Troublemaker";
        Email to = new Email(user);
        Content content = new Content("text/plain", "Thanks for signing up with Troublemaker. No go make some trouble!");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
