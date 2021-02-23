package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.OrderDetail;
import com.saleor.saleor_api.table.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepoDetailOrder extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAll();
    Optional<OrderDetail> findById(Long id);
    List<OrderDetail> findAllByOrders(Orders orders);
}
