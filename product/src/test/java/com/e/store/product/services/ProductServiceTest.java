package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.repositories.IProductAttributeValueRepository;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.repositories.IProductImageRepository;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.repositories.IProductOptionValueRepository;
import com.e.store.product.repositories.IProductSEORepository;
import com.e.store.product.repositories.ProductRepository;
import com.e.store.product.services.impl.ProductServiceImpl;
import com.e.store.product.viewmodel.req.ProductAttributeReqVm;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.req.ProductSEOReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ProductServiceTest {

    ProductRepository productRepository;
    IProductGroupRepository iProductGroupRepository;
    IProductAttributeRepository iProductAttributeRepository;
    IProductImageRepository iProductImageRepository;
    IProductSEORepository iProductSEORepository;
    IProductAttributeValueRepository iProductAttributeValueRepository;
    IProductService productService;
    ProductReqVm productReqVm;
    IProductOptionValueRepository iProductOptionValueRepository;
    IProductOptionRepository iProductOptionRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        iProductGroupRepository = mock(IProductGroupRepository.class);
        iProductAttributeRepository = mock(IProductAttributeRepository.class);
        iProductImageRepository = mock(IProductImageRepository.class);
        iProductSEORepository = mock(IProductSEORepository.class);
        iProductAttributeValueRepository = mock(IProductAttributeValueRepository.class);
        iProductOptionValueRepository = mock(IProductOptionValueRepository.class);
        iProductOptionRepository = mock(IProductOptionRepository.class);

        productService = new ProductServiceImpl(productRepository, iProductGroupRepository, iProductAttributeRepository,
            iProductImageRepository, iProductSEORepository, iProductAttributeValueRepository, iProductOptionRepository,
            iProductOptionValueRepository);

        List<ProductAttributeReqVm> attributes = Arrays.asList(new ProductAttributeReqVm("AAA-1", "", "value 1"));
        ProductSEOReqVm seo = new ProductSEOReqVm("key word", "meta data");
        productReqVm = new ProductReqVm("test 1", "test-1", 3.4, "thumbnail-id", Arrays.asList("XXX-11", "XXX-22"), 34,
            "xxx-yyy", "1", attributes,null, seo);
    }

    void testEntityNotFoundException(ProductReqVm productReqVm, String assertMsg) {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            productService.createNewProduct(productReqVm);
        });

        Assertions.assertEquals(assertMsg, exception.getMessage());
    }
    @Test
    void createNewProduct_shouldThrowException_whenInvalidProductGroup() {
        ProductAttribute productAttribute = ProductAttribute.builder().id("attId").build();

        when(iProductAttributeRepository.findById(anyString())).thenReturn(Optional.of(productAttribute));
        when(iProductGroupRepository.findById("xxx-yyy")).thenReturn(Optional.empty());

        testEntityNotFoundException(productReqVm, "Product group with id: " + productReqVm.group() + " not founded" );
    }

    @Test
    void createNewProduct_shouldThrowException_whenInvalidAttribute() {
        when(iProductAttributeRepository.findById(anyString())).thenReturn(Optional.empty());

        testEntityNotFoundException(productReqVm, "Product Attribute with id AAA-1 not found" );
    }

    @Test
    void createNewProductOption_shouldSuccess_whenDataValid() {
        ProductAttribute productAttribute = ProductAttribute.builder().id("attId").build();
        ProductGroup productGroup = ProductGroup.builder().id("1").build();
        Product expected = Product.builder().id("xxx-111").build();

        when(iProductAttributeRepository.findById(anyString())).thenReturn(Optional.of(productAttribute));
        when(this.iProductGroupRepository.findById(anyString())).thenReturn(Optional.of(productGroup));


        when(this.productRepository.save(any())).thenReturn(expected);

        ResponseEntity<ResVm> actualResult = this.productService.createNewProduct(this.productReqVm);

        Assertions.assertEquals(201, actualResult.getStatusCode().value());
        Assertions.assertEquals("New product created. Id: xxx-111", actualResult.getBody().message());
    }
}
