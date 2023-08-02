package com.e.store.product.controllers;

import com.e.store.product.viewmodel.req.ProductReqVm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @PostMapping()
    public ResponseEntity<HttpStatus> createNewProduct(@RequestBody ProductReqVm productReqVm) {
        return null;
    }
}
