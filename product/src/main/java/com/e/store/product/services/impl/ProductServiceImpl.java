package com.e.store.product.services.impl;

import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.entity.ProductImage;
import com.e.store.product.entity.ProductSEO;
import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.entity.attribute.ProductAttributeValue;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.repositories.IProductAttributeValueRepository;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.repositories.IProductImageRepository;
import com.e.store.product.repositories.IProductSEORepository;
import com.e.store.product.repositories.ProductRepository;
import com.e.store.product.services.IProductService;
import com.e.store.product.viewmodel.req.ProductAttributeReqVm;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final IProductGroupRepository iProductGroupRepository;
    private final IProductAttributeRepository iProductAttributeRepository;
    private final IProductImageRepository iProductImageRepository;
    private final IProductSEORepository iProductSEORepository;
    private final IProductAttributeValueRepository iProductAttributeValueRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, IProductGroupRepository iProductGroupRepository,
        IProductAttributeRepository iProductAttributeRepository, IProductImageRepository iProductImageRepository,
        IProductSEORepository iProductSEORepository, IProductAttributeValueRepository iProductAttributeValueRepository) {
        this.productRepository = productRepository;
        this.iProductGroupRepository = iProductGroupRepository;
        this.iProductAttributeRepository = iProductAttributeRepository;
        this.iProductImageRepository = iProductImageRepository;
        this.iProductSEORepository = iProductSEORepository;
        this.iProductAttributeValueRepository = iProductAttributeValueRepository;
    }

    @Override
    public ResponseEntity<ResVm> createNewProduct(ProductReqVm productReqVm) {
        LOG.info("createNewProduct: receive request create product.");
        Product product = Product.builder().name(productReqVm.name()).price(productReqVm.price())
            .thumbnailId(productReqVm.thumbnailId()).quantity(productReqVm.quantity())
            .shortDescription(productReqVm.description()).slug(productReqVm.slug()).build();

        // product image
        List<ProductImage> productImageList = new ArrayList<>();
        for(String imageId:productReqVm.imagesIds()){
            ProductImage productImage = ProductImage.builder().imageId(imageId).product(product).build();
            productImageList.add(iProductImageRepository.save(productImage));
        }
        product.setProductImageList(productImageList);

        // seo
        ProductSEO productSEO = ProductSEO.builder().keyword(productReqVm.seo().keyword())
            .metadata(productReqVm.seo().metadata()).product(product).build();
        ProductSEO newProdSEO = iProductSEORepository.save(productSEO);
        product.setProductSEO(newProdSEO);

        // attribute
        List<ProductAttributeValue> productAttributeValueList = new ArrayList<>();
        for (ProductAttributeReqVm att : productReqVm.attributes()) {
            ProductAttribute productAttribute = iProductAttributeRepository.findById(att.id()).orElseThrow(
                () -> new EntityNotFoundException("Product Attribute with id %s not found".formatted(att.id())));
            ProductAttributeValue productAttributeValue = new ProductAttributeValue(att.id(), att.value(), product, productAttribute);
            productAttributeValueList.add(iProductAttributeValueRepository.save(productAttributeValue));

        }
        product.setProductAttributeValueList(productAttributeValueList);

        // product group
        ProductGroup productGroup = iProductGroupRepository.findById(productReqVm.group()).orElseThrow(
            () -> new EntityNotFoundException("Product group with id: " + productReqVm.group() + " not founded"));
        product.setProductGroup(productGroup);

        product.setParent(true);
        Product newProduct = productRepository.save(product);


        ResVm resVm = new ResVm(HttpStatus.CREATED, "New product created. Id: " + newProduct.getId());
        LOG.info(resVm.getLogMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(resVm);
    }
}
