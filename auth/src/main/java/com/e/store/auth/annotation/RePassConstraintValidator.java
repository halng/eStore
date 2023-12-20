package com.e.store.auth.annotation;

import com.e.store.auth.viewmodel.req.SignUpVm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RePassConstraintValidator implements ConstraintValidator<RePassword, SignUpVm> {

    @Override
    public boolean isValid(SignUpVm signUpVm,
        ConstraintValidatorContext constraintValidatorContext) {
        return signUpVm.password().equals(signUpVm.rePassword());
    }
}
