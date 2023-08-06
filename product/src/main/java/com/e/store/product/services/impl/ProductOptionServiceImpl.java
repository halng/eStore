package com.e.store.product.services.impl;

import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.services.IProductOptionService;
import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionServiceImpl implements IProductOptionService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
    private final IProductOptionRepository iProductOptionRepository;

    @Autowired
    public ProductOptionServiceImpl(IProductOptionRepository iProductOptionRepository) {
        this.iProductOptionRepository = iProductOptionRepository;
    }

    @Override
    public ResponseEntity<ResVm> createNewProductOption(ProductOptionCreateReqVm productOptionCreateReqVm) {
        ProductOption productOption =productOptionCreateReqVm.toModel();

        if (this.iProductOptionRepository.existsByName(productOption.getName())) {
            throw new BadRequestException(
                "Product Option with name " + productOption.getName() + " already exists!");
        }

        ProductOption newProductOption = this.iProductOptionRepository.save(productOption);
        ResVm resVm = new ResVm(HttpStatus.CREATED,
            "Create new option successfully - id: " + newProductOption.getId());
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(201).body(resVm);
    }
}
