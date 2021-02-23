package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DtoOrderDetail;
import com.saleor.saleor_api.mapper.MapperOrderDetails;
import com.saleor.saleor_api.repo.RepoDetailOrder;
import com.saleor.saleor_api.table.OrderDetail;
import com.saleor.saleor_api.table.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SerOrderDetail {
    @Autowired
    RepoDetailOrder repoDetailOrder;
    @Autowired
    MapperOrderDetails mapperOrderDetails;

    public List<DtoOrderDetail> getAllOrderDetailByOrderId(Orders orders) {
        List<OrderDetail> orderDetails = repoDetailOrder.findAllByOrders(orders);
        List<DtoOrderDetail> dtoOrderDetails = new ArrayList<DtoOrderDetail>();
        for (OrderDetail item : orderDetails) {
            DtoOrderDetail dtoOrderDetail = mapperOrderDetails.toDto(item);
//            dtoOrderDetail.setOrderId(item.getOrders().getId());
            dtoOrderDetail.setProductId(item.getProduct().getId());
            dtoOrderDetail.setProductTitle(item.getProduct().getName());
            dtoOrderDetails.add(dtoOrderDetail);
        }
        return dtoOrderDetails;
    }
    public List<OrderDetail> GetAll()
    {
        return  repoDetailOrder.findAll();
    }
    public Optional<OrderDetail> GetByID(Long id)
    {
        return  repoDetailOrder.findById(id);
    }
    public OrderDetail InsertData(OrderDetail orderDetail) { return repoDetailOrder.save(orderDetail); }

    public OrderDetail UpdateData(OrderDetail orderDetail) { return repoDetailOrder.save(orderDetail); }

    public void Delete(Long id) { repoDetailOrder.deleteById(id); }
}
