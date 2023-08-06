package com.e.store.product.repositories;

import com.e.store.product.entity.option.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductOptionRepository extends JpaRepository<ProductOption, String> {
    boolean existsByName(String name);
}
