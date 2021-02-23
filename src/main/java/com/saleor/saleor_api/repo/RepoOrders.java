package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Orders;
import com.saleor.saleor_api.table.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepoOrders extends JpaRepository<Orders,Long> {
    List<Orders> findAll();
    List<Orders> findAllBy(Pageable pageable);
    Optional<Orders> findById(Long id);
    List<Orders>findByCodeOrderContaining(String id);
    List<Orders> findByCodeOrderAndId(String code,Long id );
    List<Orders> findByCodeOrder(String codeOrder);

    Page<Orders> findByCodeOrder(Pageable pageable, String codeOrder);
}
