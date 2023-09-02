package com.e.store.product.repositories;

import com.e.store.product.entity.option.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductOptionRepository extends JpaRepository<ProductOption, String> {
    boolean existsByNameAndCreateBy(String name, String createBy);

    @Query("SELECT g FROM ProductOption g WHERE g.createBy=?1")
    Page<ProductOption> findByCreatorWithPagination(String creator, Pageable pageable);
}
