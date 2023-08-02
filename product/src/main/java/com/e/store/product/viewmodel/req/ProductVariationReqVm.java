package com.e.store.product.viewmodel.req;

import java.util.List;

public record ProductVariationReqVm(Double price, List<String> imgUrl, List<String> attributeName) {

}
