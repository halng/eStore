package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.product.constant.Status;
import com.e.store.product.entity.Product;
import com.e.store.product.entity.ProductGroup;
import com.e.store.product.exceptions.BadRequestException;
import com.e.store.product.exceptions.EntityNotFoundException;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.services.impl.ProductGroupServiceImpl;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductGroupResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.time.Instant;
import java.util.Arrays;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProductGroupServiceTest {

  IProductGroupRepository iProductGroupRepository;

  ProductGroupServiceImpl productGroupService;

  ProductGroup mockProductGroup;

  @BeforeEach
  void setup() {
    iProductGroupRepository = mock(IProductGroupRepository.class);
    productGroupService = new ProductGroupServiceImpl(iProductGroupRepository);
    mockProductGroup = ProductGroup.builder().id("1").name("test").status(Status.ENABLED).build();
  }

  @Test
  void createNewGroup_shouldThrowException_whenGroupNameExists() {
    when(iProductGroupRepository.existsByName(anyString())).thenReturn(true);
    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              productGroupService.createNewGroup("name");
            });
    Assertions.assertEquals("name already exists", badRequestException.getMessage());
  }

  @Test
  void createNewGroup_shouldSuccess_whenDataValid() {

    when(iProductGroupRepository.existsByName(anyString())).thenReturn(false);
    when(iProductGroupRepository.save(any())).thenReturn(mockProductGroup);

    ResponseEntity<ResVm> actualResult = this.productGroupService.createNewGroup("test");

    Assertions.assertEquals(201, actualResult.getStatusCode().value());
    Assertions.assertEquals(
        "Create new group with name: test successfully", actualResult.getBody().message());
  }

  @Test
  void getProductGroup_shouldThrowException_whenGroupNotExist() {
    EntityNotFoundException entityNotFoundException =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> {
              this.productGroupService.getProductGroup("1232");
            });
    Assertions.assertEquals(
        "Group with id: 1232 does not exist", entityNotFoundException.getMessage());
  }

  @Test
  void getProductGroup_shouldThrowBadRequestException_whenStatusRemoved() {
    mockProductGroup.setStatus(Status.REMOVED);
    when(this.iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              this.productGroupService.getProductGroup("111");
            });

    Assertions.assertEquals(
        "Product Group Removed. Cannot Handle!", badRequestException.getMessage());
  }

  @Test
  void getProductGroup_shouldReturnGroup_whenDataValid() {
    when(this.iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    ProductGroup group = this.productGroupService.getProductGroup("1");

    Assertions.assertEquals("1", group.getId());
    Assertions.assertEquals("test", group.getName());
    Assertions.assertEquals(Status.ENABLED, group.getStatus());
  }

  @Test
  void updateProductGroupTest() {
    ProductGroup newGroup = ProductGroup.builder().name("new test").build();

    when(iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    when(iProductGroupRepository.save(any())).thenReturn(newGroup);

    ResponseEntity<ResVm> response = this.productGroupService.updateProductGroup("new", "1");

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals("Update product group successfully!", response.getBody().message());
  }

  @Test
  void disableEnableGroupTest() {
    ProductGroup newGroup = ProductGroup.builder().name("new test").build();

    when(iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    when(iProductGroupRepository.save(any())).thenReturn(newGroup);

    ResponseEntity<ResVm> response = this.productGroupService.disableEnableGroup("1", "disabled");
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals("Disable product group successfully!", response.getBody().message());

    ResponseEntity<ResVm> response2 = this.productGroupService.disableEnableGroup("1", "enabled");
    Assertions.assertNotNull(response2);
    Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());
    Assertions.assertNotNull(response2.getBody());
    Assertions.assertEquals("Enable product group successfully!", response2.getBody().message());
  }

  @Test
  void disableEnableGroup_shouldThrowException_whenActionInvalid() {
    ProductGroup newGroup = ProductGroup.builder().name("new test").build();

    when(iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    when(iProductGroupRepository.save(any())).thenReturn(newGroup);

    BadRequestException exception =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              this.productGroupService.disableEnableGroup("1", "disbled");
            });

    Assertions.assertEquals(
        "Cannot update group with status disbled. Action not valid", exception.getMessage());
  }

  @Test
  void deleteGroup_shouldThrowException_whenDataInvalid() {
    Product product = Product.builder().build();
    mockProductGroup.setProductList(Collections.singletonList(product));

    when(iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    BadRequestException exception =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              this.productGroupService.deleteProductGroup("1");
            });

    Assertions.assertEquals(
        "Group test contain product. Please make sure this group empty before delete",
        exception.getMessage());
  }

  @Test
  void deleteGroup_shouldSuccess_whenDataValid() {
    mockProductGroup.setProductList(List.of());
    ProductGroup newGroup = ProductGroup.builder().name("new test").build();

    when(iProductGroupRepository.findById(any())).thenReturn(Optional.of(mockProductGroup));
    when(iProductGroupRepository.save(any())).thenReturn(newGroup);

    ResponseEntity<ResVm> response = this.productGroupService.deleteProductGroup("1");
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals("Delete group successfully", response.getBody().message());
  }

  @Test
  void getAllGroupTest() {
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
                return "test-admin";
              }
            });
    Page<ProductGroup> productGroups =
        new Page<ProductGroup>() {
          @Override
          public int getTotalPages() {
            return 3;
          }

          @Override
          public long getTotalElements() {
            return 25;
          }

          @Override
          public <U> Page<U> map(Function<? super ProductGroup, ? extends U> converter) {
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
          public List<ProductGroup> getContent() {
            ProductGroup productGroup =
                ProductGroup.builder().id("1").status(Status.ENABLED).build();
            ProductGroup productGroup1 =
                ProductGroup.builder().id("2").status(Status.ENABLED).build();
            productGroup1.setCreateDate(Instant.now());
            productGroup1.setLastUpdate(Instant.now());
            productGroup.setCreateDate(Instant.now());
            productGroup.setLastUpdate(Instant.now());
            return Arrays.asList(productGroup1, productGroup);
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
          public Iterator<ProductGroup> iterator() {
            return null;
          }
        };

    when(iProductGroupRepository.findByCreatorWithPagination(any(), any()))
        .thenReturn(productGroups);
    ResponseEntity<PagingResVm<ProductGroupResVm>> res = this.productGroupService.getAllGroup(1);
    Assertions.assertNotNull(res);
    Assertions.assertNotNull(res.getBody());

    PagingResVm<ProductGroupResVm> listProductGroupResVm = res.getBody();
    Assertions.assertEquals(3, listProductGroupResVm.totalPages());
    Assertions.assertEquals(25, listProductGroupResVm.totalItems());
  }
}
