package com.e.store.auth.viewmodel.req;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenReqVm(@NotBlank String refreshToken) {

}
