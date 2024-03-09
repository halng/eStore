package com.e.store.product.services.impl;

import com.e.store.product.constant.Action;
import com.e.store.product.constant.Constant;
import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.entity.ProductImage;
import com.e.store.product.entity.ProductSEO;
import com.e.store.product.entity.ProductVariation;
import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.entity.attribute.ProductAttributeValue;
import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.entity.option.ProductOptionValue;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.helper.UtilsHelper;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.repositories.IProductAttributeValueRepository;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.repositories.IProductImageRepository;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.repositories.IProductOptionValueRepository;
import com.e.store.product.repositories.IProductRepository;
import com.e.store.product.repositories.IProductSEORepository;
import com.e.store.product.repositories.IProductVariationRepository;
import com.e.store.product.services.IProductService;
import com.e.store.product.services.SendMessage;
import com.e.store.product.viewmodel.req.OptionValueReqVm;
import com.e.store.product.viewmodel.req.ProductAttributeReqVm;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.req.ProductVariationReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.CommonProductValueResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductDetailResVm;
import com.e.store.product.viewmodel.res.ProductResVm;
import com.e.store.product.viewmodel.res.ProductSEOResVm;
import com.e.store.product.viewmodel.res.ProductVariationsResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ProductServiceImpl implements IProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	private final IProductRepository iProductRepository;

	private final IProductGroupRepository iProductGroupRepository;

	private final IProductAttributeRepository iProductAttributeRepository;

	private final IProductImageRepository iProductImageRepository;

	private final IProductSEORepository iProductSEORepository;

	private final IProductAttributeValueRepository iProductAttributeValueRepository;

	private final IProductOptionRepository iProductOptionRepository;

	private final IProductOptionValueRepository iProductOptionValueRepository;

	private final IProductVariationRepository iProductVariationRepository;

	private final SendMessage sendMessage;

	@Autowired
	public ProductServiceImpl(IProductRepository iProductRepository, IProductGroupRepository iProductGroupRepository,
			IProductAttributeRepository iProductAttributeRepository, IProductImageRepository iProductImageRepository,
			IProductSEORepository iProductSEORepository,
			IProductAttributeValueRepository iProductAttributeValueRepository,
			IProductOptionRepository iProductOptionRepository,
			IProductOptionValueRepository iProductOptionValueRepository,
			IProductVariationRepository iProductVariationRepository, SendMessage sendMessage) {
		this.iProductRepository = iProductRepository;
		this.iProductGroupRepository = iProductGroupRepository;
		this.iProductAttributeRepository = iProductAttributeRepository;
		this.iProductImageRepository = iProductImageRepository;
		this.iProductSEORepository = iProductSEORepository;
		this.iProductAttributeValueRepository = iProductAttributeValueRepository;
		this.iProductOptionRepository = iProductOptionRepository;
		this.iProductOptionValueRepository = iProductOptionValueRepository;
		this.iProductVariationRepository = iProductVariationRepository;
		this.sendMessage = sendMessage;
	}

	private void sendProductDataIntoTopic(Action action, Product product) {
		Map<String, Object> msg = new HashMap<>();
		msg.put("id", product.getId());
		msg.put("thumbnail", product.getThumbnailUrl());
		msg.put("name", product.getName());
		msg.put("description", product.getShortDescription());
		msg.put("quantity", product.getQuantity());
		msg.put("price", product.getPrice());
		msg.put("branch", product.getProductGroup().getName());
		msg.put("slug", product.getSlug());

		Map<String, Object> finalMsg = Map.of("data", msg, "type", action.toString().toLowerCase());

		sendMessage.putMsg(finalMsg);
	}

	@Override
	public ResponseEntity<ResVm> createNewProduct(ProductReqVm productReqVm) {
		LOG.info("createNewProduct: receive request create product.");
		Product product = Product.builder()
			.name(productReqVm.name())
			.price(productReqVm.price())
			.thumbnailUrl(productReqVm.thumbnailId())
			.quantity(productReqVm.quantity())
			.shortDescription(productReqVm.description())
			.isSales(true)
			.build();

		String slug = productReqVm.slug() + UtilsHelper.getGenerateString();
		product.setSlug(slug);

		// product image
		List<ProductImage> productImageList = new ArrayList<>();
		for (String imageId : productReqVm.imagesIds()) {
			ProductImage productImage = ProductImage.builder().imageUrl(imageId).product(product).build();
			productImageList.add(iProductImageRepository.save(productImage));
		}
		product.setProductImageList(productImageList);

		// seo
		if (productReqVm.seo() != null) {
			ProductSEO productSEO = ProductSEO.builder()
				.keyword(productReqVm.seo().keyword())
				.metadata(productReqVm.seo().metadata())
				.product(product)
				.build();
			ProductSEO newProdSEO = iProductSEORepository.save(productSEO);
			product.setProductSEO(newProdSEO);
		}
		// attribute
		List<ProductAttributeValue> productAttributeValueList = new ArrayList<>();
		for (ProductAttributeReqVm att : productReqVm.attributes()) {
			ProductAttribute productAttribute = iProductAttributeRepository.findById(att.id())
				.orElseThrow(() -> new EntityNotFoundException(
						"Product Attribute with id %s not found".formatted(att.id())));
			ProductAttributeValue productAttributeValue = new ProductAttributeValue(att.id(), att.value(), product,
					productAttribute);
			productAttributeValueList.add(iProductAttributeValueRepository.save(productAttributeValue));
		}
		product.setProductAttributeValueList(productAttributeValueList);

		// product group
		ProductGroup productGroup = iProductGroupRepository.findById(productReqVm.group())
			.orElseThrow(() -> new EntityNotFoundException(
					"Product group with id: " + productReqVm.group() + " not founded"));
		product.setProductGroup(productGroup);

		Product newProduct = iProductRepository.save(product);

		for (ProductVariationReqVm variationReqVm : productReqVm.variations()) {
			ProductVariation productVariation = ProductVariation.builder()
				.price(variationReqVm.price())
				.quantity(variationReqVm.quantity())
				.product(newProduct)
				.build();
			List<String> optionValueIds = new ArrayList<>();
			for (OptionValueReqVm opV : variationReqVm.optionCombine()) {
				ProductOption option = this.iProductOptionRepository.findById(opV.optionId())
					.orElseThrow(() -> new EntityNotFoundException("Option with id: " + opV.optionId() + " not found"));

				ProductOptionValue productOptionValue = this.iProductOptionValueRepository
					.save(ProductOptionValue.builder()
						.productOption(option)
						.value(opV.optionValue())
						.product(newProduct)
						.build());
				optionValueIds.add(productOptionValue.getId());
			}
			productVariation.setOptionValueIds(optionValueIds);

			this.iProductVariationRepository.save(productVariation);
		}

		sendProductDataIntoTopic(Action.CREATE, newProduct);

		ResVm resVm = new ResVm(HttpStatus.CREATED, "New product created. Id: " + newProduct.getId() + ". And "
				+ productReqVm.variations().size() + " child product");
		LOG.info(resVm.getLogMessage());
		return ResponseEntity.status(HttpStatus.CREATED).body(resVm);
	}

	@Override
	public ResponseEntity<PagingResVm<ProductResVm>> getProductsWithPaging(int page) {
		LOG.info("Receive request to get all product with page  = " + page);
		String creator = CommonService.getUser();
		Pageable pageable = PageRequest.of(page - 1, Constant.NUM_PER_CALL, Sort.by(Direction.DESC, "lastUpdate"));
		Page<Product> products = this.iProductRepository.findAllProductWithPaging(creator, pageable);

		List<ProductResVm> productResVmList = new ArrayList<>();
		for (Product product : products.getContent()) {
			productResVmList.add(new ProductResVm(product.getSlug(), product.getName(), product.getQuantity(),
					product.getPrice(), product.getProductGroup().getName(), product.getThumbnailUrl(),
					product.getProductVariationList().size()));
		}

		PagingResVm<ProductResVm> result = new PagingResVm<>(productResVmList, products.getTotalPages(),
				products.getTotalElements());

		return ResponseEntity.ok(result);
	}

	private Product getProductById(String id) {
		return this.iProductRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s not found", id)));
	}

	private Product getProductBySlug(String slug) {
		return this.iProductRepository.findBySlug(slug)
			.orElseThrow(() -> new EntityNotFoundException(String.format("Product with slug %s not found", slug)));
	}

	@Override
	public ResponseEntity<ResVm> updateProduct(String productId, ProductReqVm productReqVm) {
		sendProductDataIntoTopic(Action.UPDATE, null);
		return null;
	}

	@Override
	public ResponseEntity<ResVm> updateStatus(String productId, String action) {
		LOG.info(String.format("Receive a request to %s product have id: %s from user: %s ", action, productId,
				CommonService.getUser()));

		Product product = this.getProductById(productId);
		product.setSales(!Action.ENABLE.toString().toLowerCase().equals(action));
		this.iProductRepository.save(product);
		ResVm res = new ResVm(HttpStatus.OK, "Product with id: " + productId + " is disable for now");
		return ResponseEntity.ok(res);
	}

	@Override
	public ResponseEntity<ProductDetailResVm> getDetailProductBySlug(String slug) {
		Product product = getProductBySlug(slug);
		CommonProductResVm group = new CommonProductResVm(product.getProductGroup().getId(),
				product.getProductGroup().getName());

		List<CommonProductValueResVm> productAttributeValues = new ArrayList<>();
		for (var attVal : product.getProductAttributeValueList()) {
			productAttributeValues.add(new CommonProductValueResVm(attVal.getProductAttribute().getId(),
					attVal.getProductAttribute().getName(), attVal.getValue()));
		}

		List<CommonProductValueResVm> productOptionValues = new ArrayList<>();
		for (var optionValue : product.getProductOptionValueList()) {
			productOptionValues.add(new CommonProductValueResVm(optionValue.getId(),
					optionValue.getProductOption().getName(), optionValue.getValue()));
		}

		List<ProductVariationsResVm> productVariationsResVms = new ArrayList<>();
		for (var variation : product.getProductVariationList()) {
			productVariationsResVms.add(ProductVariationsResVm.fromModel(variation));
		}

		List<String> imageUrls = product.getProductImageList()
			.stream()
			.map(ProductImage::getImageUrl)
			.collect(Collectors.toList());

		ProductSEOResVm seoResVm = new ProductSEOResVm(product.getProductSEO().getKeyword(),
				product.getProductSEO().getMetadata());

		ProductDetailResVm productDetailResVm = new ProductDetailResVm(product.getId(), product.getName(),
				product.getPrice(), product.getQuantity(), product.isSales(), product.getThumbnailUrl(), imageUrls,
				product.getShortDescription(), group, productAttributeValues, productOptionValues,
				productVariationsResVms, seoResVm);

		return ResponseEntity.ok(productDetailResVm);
	}

}
