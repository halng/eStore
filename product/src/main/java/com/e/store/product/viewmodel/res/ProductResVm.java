package com.e.store.product.viewmodel.res;

public record ProductResVm(String slug, String name, int quantity, double price, String groupName, String thumbnail,
		int variations) {
}
