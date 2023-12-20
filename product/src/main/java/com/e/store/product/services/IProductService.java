package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductDetailResVm;
import com.e.store.product.viewmodel.res.ProductResVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;

public interface IProductService {

  ResponseEntity<ResVm> createNewProduct(ProductReqVm productReqVm);

  ResponseEntity<PagingResVm<ProductResVm>> getProducts(int page);

  ResponseEntity<ResVm> updateProduct(String productId, ProductReqVm productReqVm);

  ResponseEntity<ResVm> updateStatus(String productId, String action);

  ResponseEntity<ProductDetailResVm> getDetailProductById(String productId);
}
