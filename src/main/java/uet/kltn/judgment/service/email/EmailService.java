package uet.kltn.judgment.service.email;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    public String sendConfirmSignup(SignUpRequestDto signUpRequestDto)
    {
        try {
            // Creating a simple mail message
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Setting up necessary details
            helper.setFrom(sender);
            helper.setTo(signUpRequestDto.getEmail());
            helper.setText(contentConfirmSignup(signUpRequestDto), true);
            helper.setSubject("KÍCH HOẠT TÀI KHOẢN HỆ THỐNG TRA CỨU BẢN ÁN");

            // Sending the mail
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String contentConfirmSignup(SignUpRequestDto signUpRequestDto) {
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

    public String sendNotiSevenDaysTrial(User user) {
        try {
            // Creating a simple mail message
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Setting up necessary details
            helper.setFrom(sender);
            helper.setTo(user.getEmail());
            helper.setText(contentNotiSevenDaysTrial(user), true);
            helper.setSubject("THÔNG BÁO VỀ THỜI HẠN DÙNG THỬ CỦA TÀI KHOẢN TRIAL");

            // Sending the mail
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String contentNotiSevenDaysTrial(User user) {
        User userInDB = userRepository.findUserByEmailAndState(user.getEmail(), State.INACTIVE.getId());
        if (user != null) {
            String dateStart = new SimpleDateFormat("dd-MM-yyyy").format(user.getModified());
            String dateEnd = new SimpleDateFormat("dd-MM-yyyy").format(LocalDateTime.from(user.getModified()).plusDays(7));
            String content = "<div>Thân gửi <strong>" + userInDB.getName() + "</strong>, </div>" +
                    "<div>Tài khoản của bạn đã được kích hoạt thành công để sử dụng <strong>Hệ thống tra cứu bản án</strong> " +
                    "với thời hạn <b>7 ngày dùng thử</b>:</div>" +
                    "<div>Bắt đầu từ ngày: <i>" + dateStart + "</i> </div>" +
                    "<div>Kết thúc tại ngày: <i>" + dateEnd + "</i> </div>";
            return content;
        }
        return "Tài khoản của bạn đã được kích hoạt thành công trước đó.";
    }


}
