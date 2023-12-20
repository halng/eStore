package com.e.store.product.viewmodel.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductReqVm(
    @NotBlank @NotEmpty String name,
    @NotBlank @NotEmpty String slug,
    @Min(0) Double price,
    @NotEmpty @NotBlank String thumbnailId,
    List<String> imagesIds,
    @Min(0) int quantity,
    @NotNull String group,
    @NotNull String description,
    List<ProductAttributeReqVm> attributes,
    List<ProductVariationReqVm> variations,
    ProductSEOReqVm seo) {}
