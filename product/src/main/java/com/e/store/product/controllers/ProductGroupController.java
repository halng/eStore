package com.e.store.product.controllers;

import com.e.store.product.services.ProductGroupService;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/group")
public class ProductGroupController {
    @Autowired
    ProductGroupService productGroupService;

    @PostMapping()
    public ResponseEntity<ResVm> createNewProduct(String groupName) {
        return this.productGroupService.createNewGroup(groupName);
    }

}
