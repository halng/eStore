package com.e.store.product.viewmodel.req;

import com.e.store.product.entity.option.ProductOption;

public record ProductOptionCreateReqVm(String name, String description, String displayType) {

  public ProductOption toModel() {
    return ProductOption.builder()
        .name(name)
        .displayType(displayType)
        .description(description)
        .build();
  }

  public ProductOption updateModel(ProductOption old) {
    old.setName(name);
    old.setDescription(description);
    old.setDisplayType(displayType);
    return old;
  }
}
