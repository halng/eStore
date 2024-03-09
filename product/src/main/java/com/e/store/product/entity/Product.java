package com.e.store.product.entity;

import com.e.store.product.entity.attribute.ProductAttributeValue;
import com.e.store.product.entity.option.ProductOptionValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  @Column(name = "slug", unique = true)
  private String slug;

  private double price;

  private int quantity;

  private boolean isSales;

  private String thumbnailUrl;

  private String blogPostId;

  @Column(length = 512)
  private String shortDescription;

  @OneToMany(mappedBy = "product")
  private List<ProductImage> productImageList;

  @OneToMany(mappedBy = "product")
  private List<ProductAttributeValue> productAttributeValueList;

  @OneToMany(mappedBy = "product")
  private List<ProductOptionValue> productOptionValueList;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = true)
  private ProductGroup productGroup;

  @OneToOne(mappedBy = "product")
  private ProductSEO productSEO;

  @OneToMany(mappedBy = "product")
  private List<ProductVariation> productVariationList;
}
