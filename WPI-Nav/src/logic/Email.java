package logic;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Used to facilitate the sending of emails
 * I chose to include to have the body of the email provided in the sendEmail method (as opposed to in the constructor)
 * so that the same Email object can be used to send multiple, different messages if that is desired
 */
public class Email {
    private String to;
    private static String host = "smtp.gmail.com";
    private static String from = "team9.cs3733@gmail.com";
    private static String pass = "neinWongs"; //shh don't tell any1
    private static String port = "465";
    private static String fork = "<a href=\"https://github.com/jrhackett/cs3733-team9/\"><img style=\""
                               + "position: absolute; top: 0; right: 0; border: 0; z-index: -1\" src=\"https://camo"
                               + ".githubusercontent.com/365986a132ccd6a44c23a9169022c0b5c890c387/68747"
                               + "470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f"
                               + "6e732f666f726b6d655f72696768745f7265645f6161303030302e706e67\" alt=\""
                               + "Fork me on GitHub\" data-canonical-src=\"https://s3.amazonaws.com/git"
                               + "hub/ribbons/forkme_right_red_aa0000.png\"></a>"; //Could this String be longer

    public Email(String address) {
        to = address;
    }

    /**
     * sendEmail sends an email.. duh.
     * @param body: Email message body
     * @param isHTML: True if body is in HTML, false if just plain text
     * @return boolean: true if sent successfully, false if not
     */
    public boolean sendEmail (String body, boolean isHTML) {
        Properties props = System.getProperties();
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false"); //I know what all of these properties are...

        Session session = Session.getDefaultInstance(props);

        try {
            // Create a message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("WPI Navigation Directions");

            if (isHTML) message.setContent(body, "text/html"); //HTML Message
            else        message.setText(body);                 //Normal (text) Message

            Transport transport = session.getTransport("smtps");
            transport.connect(host, Integer.parseInt(port), from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        }
        catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param directions List of directions
     * @param to         Starting Location
     * @param from       Ending Location
     * @return Body of email to be sent to end-user
     */
    public static String generateBody(ArrayList<String> directions, String to, String from) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>Directions from " + to + " to " + from + fork + "</h2><hr>");
        for (int i = 1; i <= directions.size(); i++) {
            builder.append("<p>" + i + ". " + directions.get(i-1) + "</p>");
        }
        builder.append("<hr><h5>Thank you for using CapraNav by 9 Wong Productions!</h5>");

        return builder.toString();
    }
}
