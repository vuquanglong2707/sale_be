package com.saleor.saleor_api.repo;


import com.saleor.saleor_api.table.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
@Repository
public interface RepoSupplier extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllBy(Pageable pageable);

    Optional<Supplier> findByTitleContaining(String title);

    Optional<Supplier> findByPhoneContaining(String phone);

    List<Supplier> findByPhoneContainingOrTitleContaining(String phone, String title);
    Page<Supplier> findByPhoneContainingOrTitleContaining(Pageable pageable,String phone, String title);
//
//    List<Supplier> findAllBy(org.springframework.data.domain.Pageable pageable);
}
