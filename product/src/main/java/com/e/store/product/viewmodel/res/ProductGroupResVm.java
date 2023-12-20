package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.ProductGroup;

public record ProductGroupResVm(
    String id, String name, String status, String createdDate, String updatedDate) {

  public static ProductGroupResVm fromModel(ProductGroup productGroup) {
    return new ProductGroupResVm(
        productGroup.getId(),
        productGroup.getName(),
        productGroup.getStatus().toString(),
        productGroup.getCreateDate().toString(),
        productGroup.getLastUpdate().toString());
  }
}
