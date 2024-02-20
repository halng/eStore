package com.e.store.product.repositories;

import com.e.store.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {

  @Query("SELECT p FROM Product p WHERE p.createBy=?1")
  Page<Product> findAllProductWithPaging(String creator, Pageable pageable);

  Optional<Product> findBySlug(String slug);
}
