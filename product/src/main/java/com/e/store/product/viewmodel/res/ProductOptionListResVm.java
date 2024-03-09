package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.option.ProductOptionValue;

public record ProductOptionListResVm(String optionValueId, String optionName, String optionValue) {

	public static ProductOptionListResVm fromModel(ProductOptionValue productOptionValue) {
		return new ProductOptionListResVm(productOptionValue.getId(), productOptionValue.getProductOption().getName(),
				productOptionValue.getValue());
	}
}
