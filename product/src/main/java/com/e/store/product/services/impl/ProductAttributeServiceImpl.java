package com.e.store.product.services.impl;

import com.e.store.product.constant.Action;
import com.e.store.product.constant.Constant;
import com.e.store.product.constant.Status;
import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.services.IProductAttributeService;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.ListProductAttributeResVm;
import com.e.store.product.viewmodel.res.ProductAttributeResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        ProductAttribute productAttribute = productAttributeCreateReqVm.toModel();
        if (this.iProductAttributeRepository.existsByName(productAttribute.getName())) {
            throw new BadRequestException(
                "Product Attribute with name " + productAttribute.getName() + " already exists!");
        }
        productAttribute.setStatus(Status.ENABLED);
        ProductAttribute newProductAttribute = this.iProductAttributeRepository.save(productAttribute);

        ResVm resVm = new ResVm(HttpStatus.CREATED,
            "Create new attribute successfully - id: " + newProductAttribute.getId());
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(201).body(resVm);
    }

    public ProductAttribute getById(String id) {
        LOG.info("getById: get product attribute with id: " + id);
        ProductAttribute productAttribute = this.iProductAttributeRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Product Attribute with id: %s does not exist! Pls try again!", id)));

        if (Status.REMOVED.equals(productAttribute.getStatus())) {
            LOG.debug("getById: bad request. Attribute have status is REMOVED.");
            throw new BadRequestException("Attribute removed. Please try again!");
        }

        return productAttribute;
    }

    @Override
    public ResponseEntity<ListProductAttributeResVm> getAllProductAttribute(int page) {
        LOG.info("Receive request to get all product attribute");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page - 1, Constant.NUM_PER_CALL, Sort.by(Direction.DESC, "lastUpdate"));

        Page<ProductAttribute> productAttributes = this.iProductAttributeRepository.findByCreatorWithPagination(
            username, pageable);

        List<ProductAttributeResVm> productAttributeResVmList = productAttributes.getContent().stream()
            .map(ProductAttributeResVm::fromModel).collect(Collectors.toList());

        ListProductAttributeResVm productAttributeResVm = new ListProductAttributeResVm(productAttributeResVmList,
            productAttributes.getTotalPages(), (int) productAttributes.getTotalElements());

        return ResponseEntity.ok(productAttributeResVm);
    }

    @Override
    public ResponseEntity<ResVm> updateAttribute(String attId, ProductAttributeCreateReqVm updateModel) {
        LOG.info(String.format("Receive request to update properties for attribute have id: %s", attId));
        ProductAttribute productAttribute = getById(attId);

        productAttribute.setName(updateModel.name());
        productAttribute.setDescription(updateModel.description());

        this.iProductAttributeRepository.save(productAttribute);

        ResVm resVm = new ResVm(HttpStatus.OK, "Update attribute successfully");
        LOG.info(resVm.getLogMessage());

        return ResponseEntity.ok(resVm);
    }

    @Override
    public ResponseEntity<ResVm> updateStatusAtt(String attId, String action) {
        LOG.info(
            String.format("Receive request to change status for attribute have id: %s and action: %s ", attId, action));
        ProductAttribute productAttribute = getById(attId);
        if (Action.ENABLE.toString().toLowerCase().equals(action)) {
            productAttribute.setStatus(Status.ENABLED);
        } else {
            productAttribute.setStatus(Status.DISABLED);
        }
        this.iProductAttributeRepository.save(productAttribute);

        ResVm resVm = new ResVm(HttpStatus.OK, "Update status successfully");
        LOG.info(resVm.getLogMessage());

        return ResponseEntity.ok(resVm);
    }

    @Override
    public ResponseEntity<ResVm> deleteAttribute(String attId) {
        LOG.info(String.format("Receive request to delete status for attribute have id: %s", attId));
        ProductAttribute productAttribute = getById(attId);

        productAttribute.setStatus(Status.REMOVED);
        this.iProductAttributeRepository.save(productAttribute);

        LOG.info("Delete attribute done");

        ResVm resVm = new ResVm(HttpStatus.OK, "Delete attribute successfully");
        LOG.info(resVm.getLogMessage());

        return ResponseEntity.ok(resVm);
    }

    @Override
    public ResponseEntity<List<CommonProductResVm>> getAllAttribute() {
        String user = CommonService.getUser();
        List<ProductAttribute> productAttributes = this.iProductAttributeRepository.findByCreateByAndStatus(user,
            Status.ENABLED);
        List<CommonProductResVm> productResVms = new ArrayList<>();

        for (ProductAttribute attribute : productAttributes) {
            productResVms.add(new CommonProductResVm(attribute.getId(), attribute.getName()));
        }

        return ResponseEntity.ok(productResVms);
    }
}
