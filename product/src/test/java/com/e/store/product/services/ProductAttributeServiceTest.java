package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.constant.Status;
import com.e.store.product.entity.attribute.ProductAttribute;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.services.impl.ProductAttributeServiceImpl;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductAttributeResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
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

public class ProductAttributeServiceTest {

  ProductAttributeServiceImpl iProductAttributeService;
  IProductAttributeRepository iProductAttributeRepository;
  ProductAttributeCreateReqVm productAttributeCreateReqVm;
  ProductAttribute productAttribute;

  @BeforeEach
  void setup() {
    iProductAttributeRepository = mock(IProductAttributeRepository.class);
    iProductAttributeService = new ProductAttributeServiceImpl(iProductAttributeRepository);
    productAttributeCreateReqVm = new ProductAttributeCreateReqVm("test 1", "None");
    productAttribute =
        ProductAttribute.builder()
            .id("111")
            .description("this is description")
            .name("test 1")
            .status(Status.ENABLED)
            .build();
  }

  @Test
  void createNewProductAttribute_shouldThrowException_whenDataInvalid() {
    when(this.iProductAttributeRepository.existsByName(anyString())).thenReturn(true);

    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              this.iProductAttributeService.createNewAttribute(this.productAttributeCreateReqVm);
            });

    Assertions.assertEquals(
        "Product Attribute with name test 1 already exists!", badRequestException.getMessage());
  }

  @Test
  void createNewProductAttribute_shouldSuccess_whenDataValid() {
    ProductAttribute expected = this.productAttributeCreateReqVm.toModel();
    expected.setId("xxx-111-222");
    when(this.iProductAttributeRepository.existsByName(anyString())).thenReturn(false);
    when(this.iProductAttributeRepository.save(any())).thenReturn(expected);

    ResponseEntity<ResVm> res =
        this.iProductAttributeService.createNewAttribute(this.productAttributeCreateReqVm);

    Assertions.assertEquals(201, res.getStatusCode().value());
    Assertions.assertEquals(
        "Create new attribute successfully - id: xxx-111-222", res.getBody().message());
  }

  @Test
  void getById_shouldThrowException_whenEntityNotExist() {
    EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> {
              this.iProductAttributeService.getById("111");
            });

    Assertions.assertEquals(
        String.format("Product Attribute with id: %s does not exist! Pls try again!", "111"),
        exception.getMessage());
  }

  @Test
  void getById_shouldThrowException_whenEntityRemoved() {
    productAttribute.setStatus(Status.REMOVED);
    when(iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));
    BadRequestException exception =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              this.iProductAttributeService.getById("111");
            });

    Assertions.assertEquals("Attribute removed. Please try again!", exception.getMessage());
  }

  @Test
  void getById_shouldReturnEntity_whenDataValid() {
    when(iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));

    ProductAttribute expected = this.iProductAttributeService.getById("111");

    Assertions.assertEquals(productAttribute.getName(), expected.getName());
    Assertions.assertEquals(productAttribute.getId(), expected.getId());
    Assertions.assertEquals(productAttribute.getDescription(), expected.getDescription());
    Assertions.assertEquals(productAttribute.getStatus(), expected.getStatus());
  }

  @Test
  void getAllProductAttributeTest() {
    SecurityContextHolder.getContext()
        .setAuthentication(
            new Authentication() {
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
                return false;
              }

              @Override
              public void setAuthenticated(boolean isAuthenticated)
                  throws IllegalArgumentException {}

              @Override
              public String getName() {
                return "admin";
              }
            });
    Page<ProductAttribute> productAttributes =
        new Page<ProductAttribute>() {
          @Override
          public int getTotalPages() {
            return 2;
          }

          @Override
          public long getTotalElements() {
            return 13;
          }

          @Override
          public <U> Page<U> map(Function<? super ProductAttribute, ? extends U> converter) {
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
          public List<ProductAttribute> getContent() {
            productAttribute.setLastUpdate(Instant.now());
            return Collections.singletonList(productAttribute);
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
          public Iterator<ProductAttribute> iterator() {
            return null;
          }
        };

    when(this.iProductAttributeRepository.findByCreatorWithPagination(any(), any()))
        .thenReturn(productAttributes);

    ResponseEntity<PagingResVm<ProductAttributeResVm>> response =
        this.iProductAttributeService.getAllProductAttribute(1);

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getBody());

    PagingResVm<ProductAttributeResVm> expected = response.getBody();
    Assertions.assertEquals(2, expected.totalPages());
    Assertions.assertEquals(13, expected.totalItems());
  }

  @Test
  void updateAttributeTest() {
    when(this.iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));

    ResponseEntity<ResVm> response =
        this.iProductAttributeService.updateAttribute("111", productAttributeCreateReqVm);
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getBody());
  }

  @Test
  void updateStatusAttribute() {
    when(this.iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));

    ResponseEntity<ResVm> response = this.iProductAttributeService.updateStatusAtt("111", "enable");
    ResponseEntity<ResVm> response2 =
        this.iProductAttributeService.updateStatusAtt("111", "disable");

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getBody());
    Assertions.assertNotNull(response2);
    Assertions.assertNotNull(response2.getBody());
    Assertions.assertEquals("Update status successfully", response.getBody().message());
  }

  @Test
  void deleteAttributeTest() {
    when(this.iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));

    ResponseEntity<ResVm> response = this.iProductAttributeService.deleteAttribute("111");

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals("Delete attribute successfully", response.getBody().message());
  }
}
