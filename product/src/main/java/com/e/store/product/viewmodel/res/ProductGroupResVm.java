package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.ProductGroup;

public record ProductGroupResVm(
    String id, String name, String description,String status, String updatedDate) {

  public static ProductGroupResVm fromModel(ProductGroup productGroup) {
    return new ProductGroupResVm(
        productGroup.getId(),
        productGroup.getName(),
        productGroup.getDescription(),
        productGroup.getStatus().toString(),
        productGroup.getLastUpdate().toString());
  }
}
