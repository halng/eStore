package com.e.store.product.services.impl;

import com.e.store.product.constant.Constant;
import com.e.store.product.constant.Status;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.services.IProductGroupService;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductGroupResVm;
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
import org.springframework.stereotype.Service;

@Service
public class ProductGroupServiceImpl implements IProductGroupService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductGroupServiceImpl.class);

	private final IProductGroupRepository iProductGroupRepository;

	@Autowired
	public ProductGroupServiceImpl(IProductGroupRepository iProductGroupRepository) {
		this.iProductGroupRepository = iProductGroupRepository;
	}

	@Override
	public ResponseEntity<ResVm> createNewGroup(String groupName) {
		if (iProductGroupRepository.existsByName(groupName)) {
			throw new BadRequestException(groupName + " already exists");
		}
		LOG.info("createNewGroup: create new group with name: " + groupName);
		ProductGroup productGroup = new ProductGroup();
		productGroup.setName(groupName);
		productGroup.setStatus(Status.ENABLED);

		iProductGroupRepository.save(productGroup);

		ResVm resVm = new ResVm(HttpStatus.CREATED, "Create new group with name: " + groupName + " successfully");
		LOG.info(resVm.getLogMessage());
		return ResponseEntity.status(HttpStatus.CREATED).body(resVm);
	}

	public ProductGroup getProductGroup(String groupId) {
		ProductGroup group = iProductGroupRepository.findById(groupId)
			.orElseThrow(() -> new EntityNotFoundException(String.format("Group with id: %s does not exist", groupId)));

		if (Status.REMOVED.equals(group.getStatus())) {
			LOG.error("Cannot handle on group that removed");
			throw new BadRequestException("Product Group Removed. Cannot Handle!");
		}

		return group;
	}

	@Override
	public ResponseEntity<ResVm> updateProductGroup(String newName, String groupId) {
		LOG.info("Receive request to update name of product group");

		ProductGroup oldGroup = getProductGroup(groupId);

		oldGroup.setName(newName);
		this.iProductGroupRepository.save(oldGroup);

		ResVm res = new ResVm(HttpStatus.OK, "Update product group successfully!");
		LOG.info(res.getLogMessage());

		return ResponseEntity.status(200).body(res);
	}

	@Override
	public ResponseEntity<ResVm> disableEnableGroup(String groupId, String action) {
		LOG.info("Receive request to disable/enable product group");
		ProductGroup oldGroup = getProductGroup(groupId);
		ResVm res;

		if (Status.ENABLED.toString().toLowerCase().equals(action)) {
			oldGroup.setStatus(Status.ENABLED);
			res = new ResVm(HttpStatus.OK, "Enable product group successfully!");
		}
		else if (Status.DISABLED.toString().toLowerCase().equals(action)) {
			oldGroup.setStatus(Status.DISABLED);
			res = new ResVm(HttpStatus.OK, "Disable product group successfully!");
		}
		else {
			throw new BadRequestException(
					String.format("Cannot update group with status %s. Action not valid", action));
		}

		this.iProductGroupRepository.save(oldGroup);
		LOG.info(res.getLogMessage());

		return ResponseEntity.status(200).body(res);
	}

	@Override
	public ResponseEntity<ResVm> deleteProductGroup(String groupId) {
		LOG.info("Receive request to delete product group");
		ProductGroup oldGroup = getProductGroup(groupId);
		if (!oldGroup.getProductList().isEmpty()) {
			throw new BadRequestException(String.format(
					"Group %s contain product. Please make sure this group empty before delete", oldGroup.getName()));
		}

		oldGroup.setStatus(Status.REMOVED);
		this.iProductGroupRepository.save(oldGroup);
		ResVm res = new ResVm(HttpStatus.OK, "Delete group successfully");
		LOG.info(res.getLogMessage());
		return ResponseEntity.status(200).body(res);
	}

	@Override
	public ResponseEntity<List<CommonProductResVm>> getAllGroup() {
		LOG.info("Receive request to get all product group");
		String creator = CommonService.getUser();
		List<ProductGroup> productGroups = this.iProductGroupRepository.findByCreateByAndStatus(creator,
				Status.ENABLED);
		List<CommonProductResVm> commonProductResVms = new ArrayList<>();

		for (ProductGroup group : productGroups) {
			commonProductResVms.add(new CommonProductResVm(group.getId(), group.getName()));
		}

		return ResponseEntity.ok(commonProductResVms);
	}

	@Override
	public ResponseEntity<PagingResVm<ProductGroupResVm>> getAllGroup(int page) {
		LOG.info("Receive request to get all group");
		String creator = CommonService.getUser();
		Pageable pageable = PageRequest.of(page - 1, Constant.NUM_PER_CALL, Sort.by(Direction.DESC, "lastUpdate"));

		Page<ProductGroup> productGroupPages = this.iProductGroupRepository.findByCreatorWithPagination(creator,
				pageable);
		List<ProductGroupResVm> productGroupResVmList = productGroupPages.getContent()
			.stream()
			.map(ProductGroupResVm::fromModel)
			.collect(Collectors.toList());

		PagingResVm<ProductGroupResVm> resVm = new PagingResVm<>(productGroupResVmList,
				productGroupPages.getTotalPages(), (int) productGroupPages.getTotalElements());

		return ResponseEntity.ok(resVm);
	}

}
