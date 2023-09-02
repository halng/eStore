package com.e.store.product.viewmodel.req;

import java.util.List;

public record ProductReqVm(String name, Double price, String thumbnailUrl, List<String> imgUrl, int quantity,
                           String blogPostId, String groupId, String description,
                           List<ProductVariationReqVm> productVariations, List<ProductAttributeReqVm> attributeReqVms) {

}
