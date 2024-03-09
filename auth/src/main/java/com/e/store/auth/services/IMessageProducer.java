package com.e.store.auth.services;

import com.e.store.auth.viewmodel.res.AuthMessageVm;

public interface IMessageProducer {

	void sendMessage(AuthMessageVm authMessageVm);

}
