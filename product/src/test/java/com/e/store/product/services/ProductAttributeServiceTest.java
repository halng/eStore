package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.services.impl.ProductAttributeServiceImpl;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ProductAttributeServiceTest {

    IProductAttributeService iProductAttributeService;
    IProductAttributeRepository iProductAttributeRepository;
    ProductAttributeCreateReqVm productAttributeCreateReqVm;

    @BeforeEach
    void setup() {
        iProductAttributeRepository = mock(IProductAttributeRepository.class);
        iProductAttributeService = new ProductAttributeServiceImpl(iProductAttributeRepository);
        productAttributeCreateReqVm = new ProductAttributeCreateReqVm("test 1", "None");
    }

    @Test
    void createNewProductOption_shouldThrowException_whenDataInvalid() {
        when(this.iProductAttributeRepository.existsByName(anyString())).thenReturn(true);

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            this.iProductAttributeService.createNewAttribute(this.productAttributeCreateReqVm);
        });

        Assertions.assertEquals("Product Attribute with name test 1 already exists!", badRequestException.getMessage());
    }

    @Test
    void createNewProductOption_shouldSuccess_whenDataValid() {
        ProductAttribute expected = this.productAttributeCreateReqVm.toModel();
        expected.setId("xxx-111-222");
        when(this.iProductAttributeRepository.existsByName(anyString())).thenReturn(false);
        when(this.iProductAttributeRepository.save(any())).thenReturn(expected);

        ResponseEntity<ResVm> res = this.iProductAttributeService.createNewAttribute(this.productAttributeCreateReqVm);

        Assertions.assertEquals(201, res.getStatusCode().value());
        Assertions.assertEquals("Create new attribute successfully - id: xxx-111-222", res.getBody().message());

    }
}
