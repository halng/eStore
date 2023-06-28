package com.e.store.email.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;

import com.e.store.email.viewmodel.res.AuthMessageVm;

public class EmailServiceTest {

    @Autowired
    EmailService emailService;
    @Autowired
    JavaMailSender javaMailSender;

    TemplateEngine templateEngine;

    AuthMessageVm authMessageVm;

    @BeforeEach
    void setUp(){
//        javaMailSender = mock(JavaMailSender.class);
        templateEngine = mock(TemplateEngine.class);
        emailService = new EmailServiceImpl();
        ReflectionTestUtils.setField(emailService, "sender", "me");
        authMessageVm = new AuthMessageVm("email", "username", "123-344", 10000L);
    }

    @Test
    public void sendEmail_shouldThrowException() {

        when(templateEngine.process(anyString(), any())).thenReturn("<p></p>");

        Exception exception = Assert.assertThrows(Exception.class, () -> {
            emailService.sendEmail(authMessageVm);
        });

        Assertions.assertNotNull(exception);

    }

}
