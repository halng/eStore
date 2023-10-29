package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.Product;
import java.util.List;

public record ProductDetailResVm(
    String id,
    String name,
    double price,
    int quantity,
    boolean isSale,
    String thumbnailUrl,
    List<String> imageUrls,
    String shortDescription,
    CommonProductResVm group,
    List<CommonProductValueResVm> productAttributeValues,
    List<CommonProductValueResVm> productOptionValues,
    List<ProductVariationsResVm> productVariations) {}
