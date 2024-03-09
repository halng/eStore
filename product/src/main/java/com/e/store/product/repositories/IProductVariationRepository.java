package com.e.store.product.repositories;

import com.e.store.product.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductVariationRepository extends JpaRepository<ProductVariation, String> {}
