package com.e.store.product.viewmodel.req;

import java.util.List;
import java.util.Map;

public record ProductVariationReqVm(String name, Double price, int quantity, List<OptionValueReqVm> optionCombine) {

}
