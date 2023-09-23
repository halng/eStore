package com.e.store.product.services.impl;

import com.e.store.product.constant.Constant;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.services.IProductOptionService;
import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.ListProductOptionResVm;
import com.e.store.product.viewmodel.res.ProductOptionResVm;
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
public class ProductOptionServiceImpl implements IProductOptionService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
    private final IProductOptionRepository iProductOptionRepository;

    @Autowired
    public ProductOptionServiceImpl(IProductOptionRepository iProductOptionRepository) {
        this.iProductOptionRepository = iProductOptionRepository;
    }

    @Override
    public ResponseEntity<ResVm> createNewProductOption(ProductOptionCreateReqVm productOptionCreateReqVm) {
        ProductOption productOption = productOptionCreateReqVm.toModel();
        String createBy = SecurityContextHolder.getContext().getAuthentication().getName();

        if (this.iProductOptionRepository.existsByNameAndCreateBy(productOption.getName(), createBy)) {
            throw new BadRequestException("Product Option with name " + productOption.getName() + " already exists!");
        }

        ProductOption newProductOption = this.iProductOptionRepository.save(productOption);
        ResVm resVm = new ResVm(HttpStatus.CREATED, "Create new option successfully - id: " + newProductOption.getId());
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(201).body(resVm);
    }

    @Override
    public ResponseEntity<ListProductOptionResVm> getAllOption(int page) {
        LOG.info("Receive request to get all option");
        String creator = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page - 1, Constant.NUM_PER_CALL, Sort.by(Direction.DESC, "lastUpdate"));
        Page<ProductOption> productOptionPage = this.iProductOptionRepository.findByCreatorWithPagination(creator,
            pageable);

        List<ProductOptionResVm> productOptionResVmList = productOptionPage.getContent().stream()
            .map(ProductOptionResVm::fromModel).collect(
                Collectors.toList());

        ListProductOptionResVm listProductOptionResVm = new ListProductOptionResVm(productOptionResVmList,
            (int) productOptionPage.getTotalElements(),
            productOptionPage.getTotalPages());

        return ResponseEntity.ok(listProductOptionResVm);
    }

    public ProductOption getOptionById(String id) {
        return this.iProductOptionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Option with id %s not found".formatted(id)));
    }

    @Override
    public ResponseEntity<ResVm> updateOption(String optionId, ProductOptionCreateReqVm req) {
        LOG.info(String.format("Receive request to update option with id: %s", optionId));

        ProductOption old = getOptionById(optionId);
        ProductOption newOption = req.updateModel(old);
        this.iProductOptionRepository.save(newOption);

        ResVm resVm = new ResVm(HttpStatus.OK, "Update option %s successfully".formatted(newOption.getName()));
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.ok(resVm);
    }

    @Override
    public ResponseEntity<ResVm> deleteOption(String optionId) {
        LOG.info(String.format("Receive request to delete option with id: %s", optionId));
        ProductOption productOption = getOptionById(optionId);
        if (!productOption.getProductOptionValueList().isEmpty()) {
            throw new BadRequestException("This option still in use. Please try again");
        }

        this.iProductOptionRepository.delete(productOption);
        ResVm resVm = new ResVm(HttpStatus.OK, "Delete option %s successfully".formatted(productOption.getName()));
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.ok(resVm);
    }

    @Override
    public ResponseEntity<List<CommonProductResVm>> getAllOption() {
        LOG.info("Receive request to get all option");
        String creator = CommonService.getUser();

        List<ProductOption> productOptions = this.iProductOptionRepository.findByCreateBy(creator);
        List<CommonProductResVm> commonProductResVms = new ArrayList<>();
        for (ProductOption option: productOptions) {
            commonProductResVms.add(new CommonProductResVm(option.getId(), option.getName()));
        }
        return ResponseEntity.ok(commonProductResVms);
    }
}
