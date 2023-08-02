package com.e.store.product.viewmodel.req;

import java.util.List;
import java.util.Map;

public record ProductVariationReqVm(Double price, List<String> imgUrl, Map<String, String> optionValues) {

}
