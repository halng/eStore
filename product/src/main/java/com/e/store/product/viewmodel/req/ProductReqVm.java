package com.e.store.product.viewmodel.req;

import java.util.List;

public record ProductReqVm(String name, Double price, List<String> imgUrl, ) {

}
