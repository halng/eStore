package com.e.store.product.repositories;

import com.e.store.product.entity.ProductSEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductSEORepository extends JpaRepository<ProductSEO, String> {}
