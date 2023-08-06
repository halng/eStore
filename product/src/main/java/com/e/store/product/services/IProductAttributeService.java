package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductAttributeService {
    ResponseEntity<ResVm> createNewAttribute(ProductAttributeCreateReqVm productAttributeCreateReqVm);

}
