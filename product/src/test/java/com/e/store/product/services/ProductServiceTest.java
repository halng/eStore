package com.e.store.product.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.e.store.product.repositories.IProductAttributeRepository;
import com.e.store.product.repositories.IProductAttributeValueRepository;
import com.e.store.product.repositories.IProductGroupRepository;
import com.e.store.product.repositories.IProductImageRepository;
import com.e.store.product.repositories.IProductOptionRepository;
import com.e.store.product.repositories.IProductOptionValueRepository;
import com.e.store.product.repositories.IProductRepository;
import com.e.store.product.repositories.IProductSEORepository;
import com.e.store.product.repositories.IProductVariationRepository;
import com.e.store.product.services.impl.ProductServiceImpl;
import com.e.store.product.viewmodel.req.OptionValueReqVm;
import com.e.store.product.viewmodel.req.ProductAttributeReqVm;
import com.e.store.product.viewmodel.req.ProductReqVm;
import com.e.store.product.viewmodel.req.ProductSEOReqVm;
import com.e.store.product.viewmodel.req.ProductVariationReqVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductDetailResVm;
import com.e.store.product.viewmodel.res.ProductResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.time.Instant;
import java.util.ArrayList;
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

public class ProductServiceTest {

  IProductRepository iProductRepository;

  IProductGroupRepository iProductGroupRepository;

  IProductAttributeRepository iProductAttributeRepository;

  IProductImageRepository iProductImageRepository;

  IProductSEORepository iProductSEORepository;

  IProductAttributeValueRepository iProductAttributeValueRepository;

  IProductService productService;

  ProductReqVm productReqVm;

  IProductOptionValueRepository iProductOptionValueRepository;

  IProductOptionRepository iProductOptionRepository;

  IProductVariationRepository iProductVariationRepository;

  Product product;

  SendMessage sendMessage;

  @BeforeEach
  void setup() {
    iProductRepository = mock(IProductRepository.class);
    iProductGroupRepository = mock(IProductGroupRepository.class);
    iProductAttributeRepository = mock(IProductAttributeRepository.class);
    iProductImageRepository = mock(IProductImageRepository.class);
    iProductSEORepository = mock(IProductSEORepository.class);
    iProductAttributeValueRepository = mock(IProductAttributeValueRepository.class);
    iProductOptionValueRepository = mock(IProductOptionValueRepository.class);
    iProductOptionRepository = mock(IProductOptionRepository.class);
    iProductVariationRepository = mock(IProductVariationRepository.class);
    sendMessage = mock(SendMessage.class);

    productService =
        new ProductServiceImpl(
            iProductRepository,
            iProductGroupRepository,
            iProductAttributeRepository,
            iProductImageRepository,
            iProductSEORepository,
            iProductAttributeValueRepository,
            iProductOptionRepository,
            iProductOptionValueRepository,
            iProductVariationRepository,
            sendMessage);

    List<ProductAttributeReqVm> attributes =
        List.of(new ProductAttributeReqVm("AAA-1", "", "value 1"));
    List<OptionValueReqVm> optionValueReqVms = List.of(new OptionValueReqVm("xxx", "nnn", "vvv"));
    List<ProductVariationReqVm> variationReqVms = new ArrayList<>();
    variationReqVms.add(new ProductVariationReqVm("test", 12.0, 2, optionValueReqVms));

    ProductSEOReqVm seo = new ProductSEOReqVm("key word", "meta data");
    productReqVm =
        new ProductReqVm(
            "test 1",
            "test-1",
            3.4,
            "thumbnail-id",
            Arrays.asList("XXX-11", "XXX-22"),
            34,
            "xxx-yyy",
            "1",
            attributes,
            variationReqVms,
            seo);

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
                return "SYSTEM";
              }
            });

    ProductVariation productVariation =
        ProductVariation.builder()
            .price(1)
            .quantity(1)
            .id("xxx")
            .optionValueIds(Arrays.asList("XX1", "XX2"))
            .build();
    ProductOption productOption = ProductOption.builder().name("option name").id("id").build();
    ProductOptionValue productOptionValue =
        ProductOptionValue.builder().id("ii").productOption(productOption).value("val").build();
    ProductAttribute productAttribute = ProductAttribute.builder().name("type").id("id").build();
    ProductAttributeValue productAttributeValue =
        ProductAttributeValue.builder()
            .productAttribute(productAttribute)
            .value("XXM")
            .id("ipav")
            .productAttribute(productAttribute)
            .build();
    ProductGroup productGroup = ProductGroup.builder().name("this is name").build();
    ProductImage productImage = ProductImage.builder().imageUrl("xxx").build();
    ProductSEO productSEO = ProductSEO.builder().keyword("key_word").metadata("meta_data").build();
    product =
        Product.builder()
            .id("piid")
            .name("this is name")
            .price(3.4)
            .slug("this-is-name")
            .productVariationList(Collections.singletonList(productVariation))
            .productOptionValueList(Collections.singletonList(productOptionValue))
            .productAttributeValueList(Collections.singletonList(productAttributeValue))
            .productGroup(productGroup)
            .productImageList(Collections.singletonList(productImage))
            .productSEO(productSEO)
            .build();
    product.setLastUpdate(Instant.now());
  }

  void testEntityNotFoundException(ProductReqVm productReqVm, String assertMsg) {
    EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> {
              productService.createNewProduct(productReqVm);
            });

    Assertions.assertEquals(assertMsg, exception.getMessage());
  }

  @Test
  void createNewProduct_shouldThrowException_whenInvalidProductGroup() {
    ProductAttribute productAttribute = ProductAttribute.builder().id("attId").build();

    when(iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));
    when(iProductGroupRepository.findById("xxx-yyy")).thenReturn(Optional.empty());

    testEntityNotFoundException(
        productReqVm, "Product group with id: " + productReqVm.group() + " not founded");
  }

  @Test
  void createNewProduct_shouldThrowException_whenInvalidAttribute() {
    when(iProductAttributeRepository.findById(anyString())).thenReturn(Optional.empty());

    testEntityNotFoundException(productReqVm, "Product Attribute with id AAA-1 not found");
  }

  @Test
  void createNewProductOption_shouldSuccess_whenDataValid() {
    ProductAttribute productAttribute = ProductAttribute.builder().id("attId").build();
    ProductGroup productGroup = ProductGroup.builder().id("1").name("group-name").build();
    Product expected = Product.builder().id("xxx-111").productGroup(productGroup).build();
    ProductOptionValue productOptionValue = ProductOptionValue.builder().id("111").build();
    ProductOption option = ProductOption.builder().build();

    when(iProductAttributeRepository.findById(anyString()))
        .thenReturn(Optional.of(productAttribute));
    when(this.iProductGroupRepository.findById(anyString())).thenReturn(Optional.of(productGroup));
    when(this.iProductOptionRepository.findById(anyString())).thenReturn(Optional.of(option));
    when(iProductOptionValueRepository.save(any())).thenReturn(productOptionValue);
    when(this.iProductRepository.save(any())).thenReturn(expected);

    ResponseEntity<ResVm> actualResult = this.productService.createNewProduct(this.productReqVm);

    Assertions.assertEquals(201, actualResult.getStatusCode().value());
    Assertions.assertEquals(
        "New product created. Id: xxx-111. And 1 child product", actualResult.getBody().message());
  }

  @Test
  void getProductWithPaging() {
    Page<Product> expected =
        new Page<Product>() {
          @Override
          public int getTotalPages() {
            return 1;
          }

          @Override
          public long getTotalElements() {
            return 1;
          }

          @Override
          public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
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
          public List<Product> getContent() {

            return List.of(product);
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
          public Iterator<Product> iterator() {
            return null;
          }
        };

    when(iProductRepository.findAllProductWithPaging(anyString(), any())).thenReturn(expected);
    ResponseEntity<PagingResVm<ProductResVm>> actualResult =
        this.productService.getProductsWithPaging(1);

    Assertions.assertNotNull(actualResult);
    Assertions.assertNotNull(actualResult.getBody());

    PagingResVm<ProductResVm> resultBody = actualResult.getBody();
    Assertions.assertEquals(1, resultBody.totalItems());
    Assertions.assertEquals(1, resultBody.totalPages());
    Assertions.assertEquals(1, resultBody.items().size());
  }

  @Test
  void updateStatus_shouldSuccess_whenDataValid() {
    Product product = Product.builder().id("XXXX").build();
    when(iProductRepository.findById(anyString())).thenReturn(Optional.of(product));

    ResponseEntity<ResVm> res = this.productService.updateStatus("XXXX", "diable");
    Assertions.assertNotNull(res);
    Assertions.assertEquals(200, res.getStatusCode().value());
    Assertions.assertEquals("Product with id: XXXX is disable for now", res.getBody().message());
  }

  @Test
  void getDetailProductBySlug_shouldThrowException_whenSlugNotExist() {
    when(iProductRepository.findBySlug(anyString())).thenReturn(Optional.empty());
    EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> {
              productService.getDetailProductBySlug("nnn");
            });
    Assertions.assertEquals("Product with slug nnn not found", exception.getMessage());
  }

  @Test
  void getDetailProductBySlug_shouldSuccess_whenDataValid() {
    when(iProductRepository.findBySlug(anyString())).thenReturn(Optional.of(product));
    ResponseEntity<ProductDetailResVm> resVmResponseEntity =
        this.productService.getDetailProductBySlug("nnn");

    Assertions.assertEquals(HttpStatus.OK, resVmResponseEntity.getStatusCode());
    ProductDetailResVm result = resVmResponseEntity.getBody();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.imageUrls().size());
    Assertions.assertEquals("this is name", result.name());
    Assertions.assertEquals("key_word", result.seo().keyword());
    Assertions.assertEquals("meta_data", result.seo().metadata());
    Assertions.assertEquals("piid", result.id());
    Assertions.assertEquals(1, result.productVariations().size());
    Assertions.assertEquals(1, result.productAttributeValues().size());
    Assertions.assertEquals(1, result.productOptionValues().size());
    Assertions.assertNull(result.shortDescription());
  }
}
