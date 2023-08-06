package com.e.store.product.controllers;

import com.e.store.product.services.IProductAttributeService;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/attribute")
public class ProductAttributeController {
    @Autowired
    IProductAttributeService iProductAttributeService;

    @PostMapping()
    public ResponseEntity<ResVm> createNewAttribute(@RequestBody ProductAttributeCreateReqVm reqVm) {
        return this.iProductAttributeService.createNewAttribute(reqVm);
    }
}
