package com.saleor.saleor_api.repo;

//import com.saleor.saleor_api.table.User;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoVariation extends JpaRepository<Variation, Long> {
    List<Variation> findAll();
    List<Variation> findAllBy(Pageable pageable);
    List<Variation>findAllByProduct(Product product);
//    Optional<Variation> findById(Long userId);

}