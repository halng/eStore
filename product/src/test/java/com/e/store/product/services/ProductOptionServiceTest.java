package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.services.impl.ProductOptionServiceImpl;
import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ProductOptionServiceTest {

    IProductOptionRepository iProductOptionRepository;
    IProductOptionService productOptionService;
    ProductOptionCreateReqVm optionCreateReqVm;

    @BeforeEach
    void setup() {
        iProductOptionRepository = mock(IProductOptionRepository.class);
        productOptionService = new ProductOptionServiceImpl(iProductOptionRepository);
        optionCreateReqVm = new ProductOptionCreateReqVm("Test option name", "test description");
    }

    @Test
    void createNewProductOption_shouldThrowException_whenDataInvalid() {
        when(this.iProductOptionRepository.existsByName(anyString())).thenReturn(true);

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            this.productOptionService.createNewProductOption(this.optionCreateReqVm);
        });

        Assertions.assertEquals("Product Option with name Test option name already exists!",
            badRequestException.getMessage());
    }

    @Test
    void createNewProductOption_shouldSuccess_whenDataValid() {
        ProductOption expected = this.optionCreateReqVm.toModel();
        expected.setId("xxx-111-222");
        when(this.iProductOptionRepository.existsByName(anyString())).thenReturn(false);
        when(this.iProductOptionRepository.save(any())).thenReturn(expected);

        ResponseEntity<ResVm> res = this.productOptionService.createNewProductOption(this.optionCreateReqVm);

        Assertions.assertEquals(201, res.getStatusCode().value());
        Assertions.assertEquals("Create new option successfully - id: xxx-111-222", res.getBody().message());

    }
}
