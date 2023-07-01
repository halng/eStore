package com.e.store.email.service;

import com.e.store.email.exception.ActiveAccountException;
import com.e.store.email.viewmodel.res.AuthMessageVm;
import jakarta.mail.internet.MimeMessage;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(AuthMessageVm authMessageVm) {
        try {
            // prepare context
            Context context = new Context();
            String url = "http://localhost:9091" + authMessageVm.getUrlConfirm();
            URI activeUrl = new URI(url);
            context.setVariable("username", authMessageVm.username());
            context.setVariable("activeUrl", activeUrl.toURL());
            context.setVariable("expiredDate", Instant.ofEpochSecond(authMessageVm.expiryDate()));
            String htmlBody = templateEngine.process("email-active", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(authMessageVm.email());
            mimeMessageHelper.setSubject("[E-Store] Active account");
            mimeMessageHelper.setText(htmlBody, true);

            log.info("Start send email for active account for user: {}", authMessageVm.username());
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new ActiveAccountException("Can not send email: " + e.getMessage());
        }
    }

}
