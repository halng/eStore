package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.entity.option.ProductOption;
import com.e.store.product.entity.option.ProductOptionValue;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.services.impl.ProductOptionServiceImpl;
import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ListProductOptionResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProductOptionServiceTest {

    IProductOptionRepository iProductOptionRepository;
    ProductOptionServiceImpl productOptionService;
    ProductOptionCreateReqVm optionCreateReqVm;

    @BeforeEach
    void setup() {
        iProductOptionRepository = mock(IProductOptionRepository.class);
        productOptionService = new ProductOptionServiceImpl(iProductOptionRepository);
        optionCreateReqVm = new ProductOptionCreateReqVm("Test option name", "test description", "text");
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "username";
            }
        });
    }

    @Test
    void createNewProductOption_shouldThrowException_whenDataInvalid() {
        when(this.iProductOptionRepository.existsByNameAndCreateBy(anyString(), anyString())).thenReturn(true);

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
        when(this.iProductOptionRepository.existsByNameAndCreateBy(anyString(), anyString())).thenReturn(false);
        when(this.iProductOptionRepository.save(any())).thenReturn(expected);

        ResponseEntity<ResVm> res = this.productOptionService.createNewProductOption(this.optionCreateReqVm);

        Assertions.assertEquals(201, res.getStatusCode().value());
        Assertions.assertEquals("Create new option successfully - id: xxx-111-222", res.getBody().message());

    }

    @Test
    void getAllOptionTest() {

        Page<ProductOption> productOptionPage = new Page<ProductOption>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 2;
            }

            @Override
            public <U> Page<U> map(Function<? super ProductOption, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<ProductOption> getContent() {
                ProductOption productOption = ProductOption.builder().displayType("text").name("test").description("hh").build();
                ProductOption productOption2 = ProductOption.builder().displayType("number").name("name").description("hh").build();
                productOption2.setCreateDate(Instant.now());
                productOption2.setLastUpdate(Instant.now());
                productOption.setCreateDate(Instant.now());
                productOption.setLastUpdate(Instant.now());
                return Arrays.asList(productOption2, productOption);
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<ProductOption> iterator() {
                return null;
            }
        };

        when(this.iProductOptionRepository.findByCreatorWithPagination(any(), any())).thenReturn(productOptionPage);

        ResponseEntity<ListProductOptionResVm> res = this.productOptionService.getAllOption(1);

        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(1, res.getBody().totalPages());
        Assertions.assertEquals(2, res.getBody().totalOptions());
    }

    @Test
    void getOptionById_shouldThrowException_whenIdInvalid() {
        when(this.iProductOptionRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.productOptionService.getOptionById("xxx");
        });

        Assertions.assertEquals("Option with id %s not found".formatted("xxx"), exception.getMessage());
    }

    @Test
    void getOptionById_shouldReturnOption_whenDataValid() {
        ProductOption productOption = ProductOption.builder().id("iii").build();
        when(this.iProductOptionRepository.findById(any())).thenReturn(Optional.of(productOption));
        ProductOption result = this.productOptionService.getOptionById("iii");

        Assertions.assertEquals("iii", result.getId());
    }

    @Test
    void updateOptionTest(){
        ProductOption productOption = ProductOption.builder().id("iii").build();
        when(this.iProductOptionRepository.findById(any())).thenReturn(Optional.of(productOption));

        ResponseEntity<ResVm> response = this.productOptionService.updateOption("iii", optionCreateReqVm);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Update option Test option name successfully", response.getBody().message());
    }

    @Test
    void deleteOption_shouldThrowException_whenDataInvalid() {
        ProductOptionValue productOptionValue = ProductOptionValue.builder().id("hdfgs").build();
        ProductOption productOption = ProductOption.builder().id("iii").productOptionValueList(Arrays.asList(productOptionValue)).build();

        when(this.iProductOptionRepository.findById(any())).thenReturn(Optional.of(productOption));

        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            this.productOptionService.deleteOption("iii");
        });

        Assertions.assertEquals("This option still in use. Please try again", exception.getMessage());

    }

    @Test
    void deleteOption_shouldSuccess_whenDataValid() {
        ProductOption productOption = ProductOption.builder().id("iii").name("test-name").productOptionValueList(Arrays.asList()).build();
        when(this.iProductOptionRepository.findById(any())).thenReturn(Optional.of(productOption));

        ResponseEntity<ResVm> response = this.productOptionService.deleteOption("iii");

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Delete option test-name successfully", response.getBody().message());

    }

}
