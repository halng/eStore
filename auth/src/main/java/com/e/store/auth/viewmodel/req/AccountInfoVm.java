package com.e.store.auth.viewmodel.req;

import org.springframework.web.multipart.MultipartFile;

public record AccountInfoVm(String address, String phoneNumber, String name) {

}
