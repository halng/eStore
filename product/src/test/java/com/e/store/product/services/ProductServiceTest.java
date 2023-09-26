package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.repositories.IProductAttributeValueRepository;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.repositories.IProductImageRepository;
import com.e.store.product.repositories.IProductSEORepository;
import com.e.store.product.repositories.ProductRepository;
import com.e.store.product.services.impl.ProductServiceImpl;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.res.ResVm;
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

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        iProductGroupRepository = mock(IProductGroupRepository.class);
        iProductAttributeRepository = mock(IProductAttributeRepository.class);
        iProductImageRepository = mock(IProductImageRepository.class);
        iProductSEORepository = mock(IProductSEORepository.class);
        iProductAttributeValueRepository = mock(IProductAttributeValueRepository.class);

        productService = new ProductServiceImpl(productRepository, iProductGroupRepository, iProductAttributeRepository,
            iProductImageRepository, iProductSEORepository, iProductAttributeValueRepository);

        productReqVm = new ProductReqVm("test 1","test-1", 3.4, "url", null, 34, "xxx-yyy", "1", null, null);
    }

    @Test
    void createNewProductOption_shouldThrowException_whenDataInvalid() {
        when(this.iProductGroupRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.productService.createNewProduct(this.productReqVm);
        });

        Assertions.assertEquals("Product group with id: 1 not founded", e.getMessage());
    }

    @Test
    void createNewProductOption_shouldSuccess_whenDataValid() {
        ProductGroup productGroup = ProductGroup.builder().id("1").name("Product Name").build();
        Product expected = Product.builder().id("xxx-111").build();
        when(this.iProductGroupRepository.findById("1")).thenReturn(Optional.of(productGroup));
        when(this.productRepository.save(any())).thenReturn(expected);

        ResponseEntity<ResVm> actualResult = this.productService.createNewProduct(this.productReqVm);

        Assertions.assertEquals(201, actualResult.getStatusCode().value());
        Assertions.assertEquals("New product created. Id: xxx-111", actualResult.getBody().message());
    }
}
