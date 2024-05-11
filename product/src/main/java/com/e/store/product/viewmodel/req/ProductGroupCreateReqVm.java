package com.e.store.product.viewmodel.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductGroupCreateReqVm(
    @NotBlank @NotNull String name, @NotNull String description) {}
