package com.e.store.product.repositories;

import com.e.store.product.entity.attribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
    boolean existsByName(String name);
}
