package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.ProductGroupRepository;
import com.e.store.product.services.impl.ProductGroupServiceImpl;
import com.e.store.product.viewmodel.res.ResVm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;

public class ProductGroupServiceTest {

    ProductGroupRepository productGroupRepository;
    ProductGroupServiceImpl productGroupService;

    @BeforeEach
    void setup() {
        productGroupRepository = mock(ProductGroupRepository.class);
        productGroupService = new ProductGroupServiceImpl(productGroupRepository);
    }

    @Test
    void createNewGroup_shouldThrowException_whenGroupNameExists() {
        when(productGroupRepository.existsByName(anyString())).thenReturn(true);
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            productGroupService.createNewGroup("name");
        });
        Assertions.assertEquals("name already exists", badRequestException.getMessage());
    }

    @Test
    void createNewGroup_shouldSuccess_whenDataValid() {
        ProductGroup mockProductGroup = ProductGroup.builder().id(1L).name("test").build();

        when(productGroupRepository.existsByName(anyString())).thenReturn(false);
        when(productGroupRepository.save(any())).thenReturn(mockProductGroup);

        ResponseEntity<ResVm> actualResult = this.productGroupService.createNewGroup("test");

        Assertions.assertEquals(201, actualResult.getStatusCode().value());
        Assertions.assertEquals("create new group with name: test successfully", actualResult.getBody().message());
    }


}
