package com.e.store.product.viewmodel.req;

import java.util.List;

public record ProductVariationReqVm(
    String name, Double price, int quantity, List<OptionValueReqVm> optionCombine) {

}
