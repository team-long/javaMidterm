package com.teamLong.java401d.midterm.troublemaker.email;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmailSender {

    static final String FROM = "troublemakeraws@gmail.com";

    public static void sendEmail(UserAccount user, Collection<String> emails, String type, Ticket ticket) {
        Email mail = new Email();

        switch (type) {
            case("INTRO"):
                mail = new IntroEmail(user);
                break;
            case("CREATE"):
                mail = new TicketCreationEmail(user, ticket, emails);
                break;
        }

        System.out.print(mail.htmlBody);


        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(mail.recicipientEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(mail.htmlBody))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(mail.textBody)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(mail.subject)))
                    .withSource(FROM);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}

class Email {
    protected String recicipientName;

    protected Collection<String> recicipientEmail = new ArrayList<>();

    protected String subject;

    protected String htmlBody;

    protected String textBody;

    public Email() {};

    public Email(UserAccount user) {
        this.recicipientName = user.getFirstName() + " " + user.getLastName();
        this.recicipientEmail.add(user.getUsername());
    }
}

class IntroEmail extends Email {


    public IntroEmail(UserAccount user) {
        super(user);
        subject = "Welcome to Troublemaker";

        htmlBody = "<h1>Welcome to Troublemaker, " + recicipientName + "!</h1>"
                + "<p>Thanks for registering. "
                + "Visit <a href='https://aws.amazon.com/sdk-for-java/'>"
                + "Troublemaker</a> to begin reporting issues.";

        textBody = "Welcome to Troublemaker, " + recicipientName + "! Visit our site to report issues.";
    }
}

class TicketCreationEmail extends Email {


    public TicketCreationEmail(UserAccount user, Ticket ticket, Collection<String> emails) {
        super(user);
        subject = "New Ticket has been created";
        recicipientEmail = emails;

        htmlBody = "<h1>Ticket #" + ticket.getId() + " has been created by " + recicipientName + "!</h1>"
                + "<p> Visit <a href='https://aws.amazon.com/sdk-for-java/'>"
                + "Troublemaker</a> to begin resolving the issue.";

        textBody = "Ticket #" + ticket.getId() + "has been created by" + recicipientName + ". Visit Troublemaker to begin resolving the issue.";
    }
}

class TicketResolvedEmail extends Email {

}