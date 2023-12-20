package com.e.store.product.viewmodel.req;

import com.e.store.product.entity.attribute.ProductAttribute;

public record ProductAttributeCreateReqVm(String name, String description) {

    public ProductAttribute toModel() {
        return ProductAttribute.builder().name(name).description(description).build();
    }
}
