package com.e.store.product.repositories;

import com.e.store.product.entity.attribute.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeValueRepository
    extends JpaRepository<ProductAttributeValue, String> {}
