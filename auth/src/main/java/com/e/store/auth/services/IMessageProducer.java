package com.e.store.auth.services;

import com.e.store.auth.viewmodel.res.AuthMessageVm;
import org.springframework.stereotype.Service;

@Service
public interface IMessageProducer {

    void sendMessage(AuthMessageVm authMessageVm);

}
