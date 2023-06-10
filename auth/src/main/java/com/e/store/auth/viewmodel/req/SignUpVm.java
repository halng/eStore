package com.e.store.auth.viewmodel.req;

public record SignUpVm(String username, String password, String rePassword, Long role, String email) {
}
