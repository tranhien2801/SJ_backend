package uet.kltn.judgment.service.email;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.config.AppProperties;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.request.user.SignUpRequestDto;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.model.email.EmailDetails;
import uet.kltn.judgment.respository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Method 1
    // To send a simple email
    public String sendSimpleMail(SignUpRequestDto signUpRequestDto)
    {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Setting up necessary details
            helper.setFrom(sender);
            helper.setTo(signUpRequestDto.getEmail());
            helper.setText(contentEmail(signUpRequestDto), true);
            helper.setSubject("KÍCH HOẠT TÀI KHOẢN HỆ THỐNG TRA CỨU BẢN ÁN");

            // Sending the mail
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String contentEmail(SignUpRequestDto signUpRequestDto) {
        AppProperties.Notification notification = appProperties.getNotification();
        User user = userRepository.findUserByEmailAndState(signUpRequestDto.getEmail(), State.INACTIVE.getId());
        if (user != null) {
            String content = "<div>Thân gửi <strong>" + user.getName() + "</strong>, </div>" +
                    "<div>Tài khoản của bạn đã được đăng ký để sử dụng <strong>Hệ thống tra cứu bản án</strong> với mật khẩu <i>" +
                    signUpRequestDto.getPassword() + "</i>, " +
                    "để kích hoạt tài khoản vui lòng nhấn vào link bên dưới: </div>" +
                    "<a href=\"" + notification.getConfirmSignup() + user.getUid() + "\">Link kích hoạt tài khoản</a>";
            return content;
        }
        return "Tài khoản của bạn đã được kích hoạt thành công trước đó.";
    }

    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}
