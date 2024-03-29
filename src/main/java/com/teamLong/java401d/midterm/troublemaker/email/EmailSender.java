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

    public static boolean sendEmail(UserAccount user, Collection<String> emails, String type, Ticket ticket) {
        Email mail = new Email();

        switch (type) {
            case("INTRO"):
                mail = new IntroEmail(user);
                break;
            case("CREATE"):
                mail = new TicketCreationEmail(user, ticket, emails);
                break;
            case("RESOLVED"):
                mail = new TicketResolutionEmail(user, ticket);
                break;
        }

        System.out.print(mail.htmlBody);


        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
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
            return true;
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
            return false;
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
        this.recicipientName = user.getUsername();
        this.recicipientEmail.add(user.getUsername());
    }
}

class IntroEmail extends Email {


    public IntroEmail(UserAccount user) {
        super(user);
        subject = "Welcome to Troublemaker";

        htmlBody = "<h1>Welcome to Troublemaker, " + recicipientName + "!</h1>"
                + "<p>Thanks for registering. "
                + "Visit <a href='troublemaker.us-west-2.elasticbeanstalk.com/'>"
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
                + "<p> Visit <a href='troublemaker.us-west-2.elasticbeanstalk.com/'>"
                + "Troublemaker</a> to begin resolving the issue.";

        textBody = "Ticket #" + ticket.getId() + "has been created by" + recicipientName + ". Visit Troublemaker to begin resolving the issue.";
    }
}

class TicketResolutionEmail extends Email {

    public TicketResolutionEmail(UserAccount user, Ticket ticket) {
        super(user);
        subject = "Your Ticket has been resolved";

        htmlBody = "<h1>Ticket #" + ticket.getId() + " has been resolved!</h1>"
                + "<p> Visit <a href='troublemaker.us-west-2.elasticbeanstalk.com/'>"
                + "Troublemaker</a> for any more issues.";

        textBody = "Ticket #" + ticket.getId() + "has been resolved! Visit Troublemaker for any further issues.";
    }

}