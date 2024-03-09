package com.e.store.product.viewmodel.res;

import com.e.store.product.entity.attribute.ProductAttribute;

public record ProductAttributeResVm(String id, String name, String description, String lastUpdateDate, String status) {

	public static ProductAttributeResVm fromModel(ProductAttribute attribute) {
		return new ProductAttributeResVm(attribute.getId(), attribute.getName(), attribute.getDescription(),
				attribute.getLastUpdate().toString(), attribute.getStatus().toString());
	}
}
