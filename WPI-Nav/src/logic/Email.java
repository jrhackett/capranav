package logic;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Used to facilitate the sending of emails
 * I chose to include to have the body of the email provided in the sendEmail method (as opposed to in the constructor)
 * so that the same Email object can be used to send multiple, different messages if that is desired
 * TODO Create email address to send from
 */
public class Email {
    private String to;
    private String from;

    public Email(String address) {
        to = address;
        from = "test@gmail.com"; //TODO Replace with something
    }

    /**
     * sendEmail sends an email.. duh.
     * @param body: Email message body
     * @return boolean: true if sent successfully, false if not
     */
    public boolean sendEmail (String body) {
        String host = "localhost";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.user", "myuser");   //TODO Set username/password for email here
        props.setProperty("mail.password", "mypwd");
        Session session = Session.getDefaultInstance(props);

        try {
            // Create a message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("WPI Navigation Directions");
            message.setText(body);      // Used for normal (text) messages
            //message.setContent(body); // Used for HTML messages
            Transport.send(message);
            return true;
        }
        catch (MessagingException e) {
            return false;
        }
    }
}
