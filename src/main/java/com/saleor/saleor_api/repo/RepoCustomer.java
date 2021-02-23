package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RepoCustomer extends JpaRepository<Customer,Long> {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    List<Customer> findByNameContainingOrPhoneContaining(String name, String phone);
    Page<Customer> findByNameContainingOrPhoneContaining(Pageable pageable,String name, String phone);
    List<Customer>findAllBy(Pageable pageable);
}
