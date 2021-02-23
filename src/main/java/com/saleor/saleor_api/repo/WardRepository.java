package com.saleor.saleor_api.repo;

import java.util.Optional;

import com.saleor.saleor_api.table.AddressWard;
import com.saleor.saleor_api.table.AddressProvince;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saleor.saleor_api.table.AddressDistrict;
import com.saleor.saleor_api.table.AddressWard;

public interface WardRepository extends JpaRepository<AddressWard, String>{
    Optional<AddressWard> findByNameIgnoreCase(String ward);

}
