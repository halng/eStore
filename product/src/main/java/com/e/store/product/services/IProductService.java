package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    ResponseEntity<ResVm> createNewProduct(ProductReqVm productReqVm);
}
