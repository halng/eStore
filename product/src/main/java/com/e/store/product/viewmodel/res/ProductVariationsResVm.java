package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.ProductVariation;
import java.util.List;

public record ProductVariationsResVm(
    List<String> optionValueIds, int quantity, double price, String id) {

  public static ProductVariationsResVm fromModel(ProductVariation productVariation) {
    return new ProductVariationsResVm(
        productVariation.getOptionValueIds(),
        productVariation.getQuantity(),
        productVariation.getPrice(),
        productVariation.getId());
  }
}
