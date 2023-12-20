package com.e.store.email.service;

import com.e.store.email.viewmodel.res.AuthMessageVm;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(AuthMessageVm authMessageVm);
}
