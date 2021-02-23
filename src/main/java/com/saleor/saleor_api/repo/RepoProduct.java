package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.ProductCatogories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RepoProduct extends JpaRepository<Product,Long> {
    List<Product> findAll();
    List<Product> findBy();
    List<Product>findAllBy(Pageable pageable);
    Optional<Product>findById(Long id);
    List<Product>findBySkuContaining(String id);
    Optional<Product> findBySku(String query);
    List<Product> findAllBySku(String Sku);
    List<Product>findByIdAndName(Long Id,String Name);
    List<Product> findAllByProductCatogories(ProductCatogories productCatogories);
   Optional<Product> findByName(String name);
    List<Product> findAllByName(String name);
    Page<Product> findAllByName(Pageable pageable,String name);
}
