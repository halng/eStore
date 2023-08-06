package com.e.store.product.services.impl;

import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.ProductGroupRepository;
import com.e.store.product.services.IProductGroupService;
import com.e.store.product.viewmodel.res.ResVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupServiceImpl implements IProductGroupService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductGroupServiceImpl.class);
    private final ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupServiceImpl(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }


    @Override
    public ResponseEntity<ResVm> createNewGroup(String groupName) {
        if (productGroupRepository.existsByName(groupName)) {
            throw new BadRequestException(groupName + " already exists");
        }
        LOG.info("createNewGroup: create new group with name: " + groupName);
        ProductGroup productGroup = new ProductGroup();
        productGroup.setName(groupName);

        productGroupRepository.save(productGroup);

        ResVm resVm = new ResVm(HttpStatus.CREATED, "create new group with name: " + groupName + " successfully");
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(resVm);
    }
}
