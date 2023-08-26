package com.e.store.product.viewmodel.res;

import java.util.List;

public record ListProductOptionResVm(List<ProductOptionResVm> options,  int totalOptions, int totalPages) {

}
