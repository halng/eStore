package com.e.store.product.viewmodel.res;

import java.util.List;

public record PagingResVm<T>(List<T> items, int totalPages, int totalItems) {

}
