package com.e.store.product.services.impl;

import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.services.IProductAttributeService;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeServiceImpl implements IProductAttributeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);
    private final IProductAttributeRepository iProductAttributeRepository;

    @Autowired
    public ProductAttributeServiceImpl(IProductAttributeRepository iProductAttributeRepository) {
        this.iProductAttributeRepository = iProductAttributeRepository;
    }

    @Override
    public ResponseEntity<ResVm> createNewAttribute(ProductAttributeCreateReqVm productAttributeCreateReqVm) {
        ProductAttribute productAttribute  = productAttributeCreateReqVm.toModel();
        if (this.iProductAttributeRepository.existsByName(productAttribute.getName())) {
            throw new BadRequestException("Product Attribute with name " + productAttribute.getName() + " already exists!");
        }

        ProductAttribute newProductAttribute = this.iProductAttributeRepository.save(productAttribute);

        ResVm resVm = new ResVm(HttpStatus.CREATED,  "Create new attribute successfully - id: "  + newProductAttribute.getId());
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(201).body(resVm);
    }
}
