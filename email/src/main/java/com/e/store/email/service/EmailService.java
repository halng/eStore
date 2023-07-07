package com.e.store.email.service;

import org.springframework.stereotype.Service;

import com.e.store.email.viewmodel.res.AuthMessageVm;

@Service
public interface EmailService {
    void sendEmail(AuthMessageVm authMessageVm);
}
