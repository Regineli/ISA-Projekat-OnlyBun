package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.User;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Send activation email after registration
    public void sendActivationEmail(User user) throws MailException {
        System.out.println("Sending activation email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("sergej.vlaskalic@gmail.com");  // Or load from environment/properties
        mail.setSubject("Activate Your Account");
        mail.setText("Hello " + user.getFirstName() + ",\n\n" +
                     "Thank you for registering! Please click the link below to activate your account:\n\n" +
                     "http://localhost:4200/register-activation/" + user.getId() +
                     "\n\nRegards,\nYour Team");

        javaMailSender.send(mail);
        System.out.println("Activation email sent!");
    }
    
    public void sendTestEmail() throws MailException {
        System.out.println("Sending activation email...");

        // Prepare the mail object
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("sergej@lodeware.com");  // Test recipient
        mail.setFrom("sergej.vlaskalic@com");  // Sender email address (your Gmail)
        mail.setSubject("Activate Your Account");
        mail.setText("Hello test,\n\n" +
                     "Thank you for registering! Please click the link below to activate your account:\n\n" +
                     "http://localhost:8080/api/users/activate?userId=1" +
                     "\n\nRegards,\nYour Team");

        // Send the email
        javaMailSender.send(mail);
        System.out.println("Activation email sent!");
    }
}
