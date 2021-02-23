package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoShop  extends JpaRepository<Shop, Long> {
    Optional<Shop> findById(Long id);

    Optional<Shop> findByTitleContainingOrPhoneContainingOrManagerContaining(String title, String phone, String manager);

    Optional<Shop> findByTitleContaining(String title);

    Optional<Shop> findByPhoneContaining(String phone);

    Optional<Shop> findByManagerContaining(String manager);

}
