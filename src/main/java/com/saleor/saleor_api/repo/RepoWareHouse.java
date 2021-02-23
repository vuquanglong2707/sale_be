package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RepoWareHouse extends JpaRepository<WareHouse, Long> {
    Optional<WareHouse> findByPhoneContainingOrTitleContaining(String phone, String title);
    Optional<WareHouse> findByPhoneContaining(String phone);
    Optional<WareHouse> findByTitleContaining(String title);
    List<WareHouse> findBy();
    List<WareHouse> findByTitle(String title);

}
