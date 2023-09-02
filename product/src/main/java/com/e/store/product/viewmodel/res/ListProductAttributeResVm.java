package com.e.store.product.viewmodel.res;

import java.util.List;

public record ListProductAttributeResVm(List<ProductAttributeResVm> attributes, int totalPages, int totalAttributes) {

}
