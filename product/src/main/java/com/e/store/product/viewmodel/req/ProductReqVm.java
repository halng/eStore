package com.e.store.product.viewmodel.req;

import java.util.List;

public record ProductReqVm(String name,String slug, Double price, String thumbnailId, List<String> imagesIds, int quantity,
                           String group, String description , List<ProductAttributeReqVm> attributes, List<ProductVariationReqVm> variations ,ProductSEOReqVm seo) {

}
