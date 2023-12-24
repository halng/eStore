package com.e.store.product.viewmodel.res;

import java.util.List;

public record ProductResVm(
    String id,
    String name,
    int quantity,
    double price,
    String lastUpdateDate,
    List<ProductOptionListResVm> optionList,
    List<ProductVariationsResVm> variations, List<CommonProductValueResVm> attributes) {}
