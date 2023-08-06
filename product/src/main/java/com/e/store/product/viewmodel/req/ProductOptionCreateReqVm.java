package com.e.store.product.viewmodel.req;

import com.e.store.product.entity.option.ProductOption;

public record ProductOptionCreateReqVm(String name, String description) {

    public ProductOption toModel() {
        return ProductOption.builder().name(name).description(description).build();
    }

}
