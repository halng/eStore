package com.e.store.product.controllers;

import com.e.store.product.services.ProductService;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @PostMapping()
    public ResponseEntity<ResVm> createNewProduct(@RequestBody ProductReqVm productReqVm) {
        return productService.createNewProduct(productReqVm);
    }

    @GetMapping("get/{name}")
    public ResponseEntity<String> testLogAndSecurity(@PathVariable String name){
        return ResponseEntity.ok(name);
    }
 }
