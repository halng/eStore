package com.e.store.product.services.impl;

import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.ProductAttributeRepository;
import com.e.store.product.repositories.ProductAttributeValueRepository;
import com.e.store.product.repositories.ProductGroupRepository;
import com.e.store.product.repositories.ProductRepository;
import com.e.store.product.services.ProductService;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
        ProductAttributeRepository productAttributeRepository,
        ProductAttributeValueRepository productAttributeValueRepository,ProductGroupRepository productGroupRepository) {
        this.productRepository = productRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.productGroupRepository = productGroupRepository;
    }

    @Override
    public ResponseEntity<ResVm> createNewProduct(ProductReqVm productReqVm) {
        LOG.info("createNewProduct: receive request create product.");
        Product product = Product.builder().name(productReqVm.name()).price(productReqVm.price())
            .thumbnailId(productReqVm.thumbnailUrl()).quantity(productReqVm.quantity())
            .shortDescription(productReqVm.description()).blogPostId(productReqVm.blogPostId()).build();

        ProductGroup productGroup =
            productGroupRepository.findById(productReqVm.groupId()).orElseThrow(() -> new EntityNotFoundException(
                "Product group with id: " + productReqVm.groupId() + " not founded"));
        product.setProductGroup(productGroup);

        Product newProduct = productRepository.save(product);

        ResVm resVm = new ResVm(HttpStatus.CREATED, "New product created. Id: " + newProduct.getId());
        LOG.info(resVm.getLogMessage());
        return  ResponseEntity.status(HttpStatus.CREATED).body(resVm);
    }
}
