package com.e.store.product.repositories;

import com.e.store.product.entity.Product;
import com.e.store.product.entity.option.ProductOptionValue;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductOptionValueRepository extends JpaRepository<ProductOptionValue, String> {
  Optional<ProductOptionValue> findByProduct(Product product);
}
