package by.it_academy.jd2.service;

import by.it_academy.jd2.entity.MailEntity;
import by.it_academy.jd2.service.api.IMailService;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService implements IMailService {
    // private final String addressSMTP;
    private final String login;
    private final String password;

    private final Properties prop;

    public MailService(String addressSMTP,  String login, String password) {
     //   this.addressSMTP = addressSMTP
        this.login = login;
        this.password = password;

        prop = new Properties();
        prop.put("mail.smtp.host", addressSMTP);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
    }


    public boolean send(MailEntity mail) {                 //может void
        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(login));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail.getRecipient())  // может без парсинга
            );
            // message.addRecipient();   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(mail.getTitle());
            message.setText(mail.getBody());

            Transport.send(message);

            return true;
        } catch (AddressException e) {
            throw new RuntimeException("Ошибка валидации получателя",e);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка отправки письма",e);
        }
    }

}
