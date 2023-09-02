package com.e.store.product.repositories;

import com.e.store.product.entity.ProductGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductGroupRepository extends JpaRepository<ProductGroup, String> {
    boolean existsByName(String name);

    @Query("SELECT g FROM ProductGroup g WHERE g.createBy=?1")
    Page<ProductGroup> findByCreatorWithPagination(String creator, Pageable pageable);

}
