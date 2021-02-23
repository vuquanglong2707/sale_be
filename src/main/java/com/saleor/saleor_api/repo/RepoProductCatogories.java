package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.ProductCatogories;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RepoProductCatogories extends JpaRepository<ProductCatogories, Long> {

    List<ProductCatogories> findBy();

    List<ProductCatogories> findAllBy(Pageable pageable);

    Optional<ProductCatogories> findById(Long id);

    List<ProductCatogories> findByTitle(String title);

    Page<ProductCatogories> findByTitle(Pageable pageable,String title);

    Optional<ProductCatogories> findByTitleContainingOrCodeContaining(String title, String code);

    List<ProductCatogories> findByTitleContainingAndCodeContaining(String title, String code);

//    Page<ProductCatogories> findByTitleContainingAnAndCodeContaining(String title, String code);
}
