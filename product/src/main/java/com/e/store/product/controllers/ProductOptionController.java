package com.e.store.product.controllers;

import com.e.store.product.services.IProductOptionService;
import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/option")
public class ProductOptionController {
    @Autowired
    IProductOptionService iProductOptionService;

    @PostMapping()
    public ResponseEntity<ResVm> createNewOption(ProductOptionCreateReqVm req) {
        return this.iProductOptionService.createNewProductOption(req);
    }
}
