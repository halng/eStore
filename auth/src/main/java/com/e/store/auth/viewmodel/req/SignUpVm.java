package com.e.store.auth.viewmodel.req;

import com.e.store.auth.annotation.RePassword;
import com.e.store.auth.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpVm(@NotBlank String username, @ValidPassword String password, @RePassword String rePassword,
					   Long role, @Email String email) {

}
