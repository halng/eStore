package com.e.store.product.repositories;

import com.e.store.product.entity.ProductGroup;
import com.e.store.product.entity.attribute.ProductAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
    boolean existsByName(String name);

    @Query("SELECT g FROM ProductAttribute g WHERE g.createBy=?1")
    Page<ProductAttribute> findByCreatorWithPagination(String creator, Pageable pageable);
}
