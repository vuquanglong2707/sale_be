package com.saleor.saleor_api.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saleor.saleor_api.table.AddressProvince;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<AddressProvince, String>{

    Optional<AddressProvince> findByNameIgnoreCase(String province);

}
