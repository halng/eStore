package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.option.ProductOption;

public record ProductOptionResVm(
    String id,
    String name,
    String displayType,
    String description,
    String createdDate,
    String updatedDate) {
  public static ProductOptionResVm fromModel(ProductOption productOption) {
    return new ProductOptionResVm(
        productOption.getId(),
        productOption.getName(),
        productOption.getDisplayType(),
        productOption.getDescription(),
        productOption.getCreateDate().toString(),
        productOption.getLastUpdate().toString());
  }
}
