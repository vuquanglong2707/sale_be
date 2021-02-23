package com.saleor.saleor_api.repo;

import java.util.List;
import java.util.Optional;

import com.saleor.saleor_api.table.AddressProvince;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saleor.saleor_api.table.AddressDistrict;

public interface DistrictRepository extends JpaRepository<AddressDistrict, String> {
    Optional<AddressDistrict> findByNameIgnoreCase(String district);

}
