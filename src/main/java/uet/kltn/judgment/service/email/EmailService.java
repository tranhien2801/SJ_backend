package uet.kltn.judgment.service.email;

import uet.kltn.judgment.dto.request.user.SignUpRequestDto;
import uet.kltn.judgment.model.email.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(SignUpRequestDto signUpRequestDto);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
