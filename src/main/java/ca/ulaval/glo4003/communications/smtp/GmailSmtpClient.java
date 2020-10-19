package ca.ulaval.glo4003.communications.smtp;

import ca.ulaval.glo4003.communications.exceptions.EmailSendingFailedException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSmtpClient {
  private static final String GMAIL_HOST = "smtp.gmail.com";
  private static final String EMAIL_SENDER = "SPAMDUL.eq4@gmail.com"; // TODO : Get this from env
  private static final String EMAIL_PASSWORD =
      "InsquisitionsEspagnolesEquipeQuatre"; // TODO : Get this from env

  private final SmtpTransportDelegator smtpTransportDelegator;

  public GmailSmtpClient(SmtpTransportDelegator smtpTransportDelegator) {
    this.smtpTransportDelegator = smtpTransportDelegator;
  }

  public MimeMessage createMessage() {
    Session session = getSession();
    MimeMessage message = new MimeMessage(session);

    try {
      message.setFrom(new InternetAddress(EMAIL_SENDER));
    } catch (MessagingException exception) {
      throw new EmailSendingFailedException();
    }

    return message;
  }

  public void sendMessage(MimeMessage message) {
    smtpTransportDelegator.sendMessage(message, EMAIL_SENDER, EMAIL_PASSWORD);
  }

  private Session getSession() {
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", GMAIL_HOST);
    properties.setProperty("mail.smtp.port", "587");
    properties.setProperty("mail.smtp.starttls.enable", "true");

    return Session.getInstance(properties);
  }
}
