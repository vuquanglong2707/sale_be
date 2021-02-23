package com.saleor.saleor_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saleor.saleor_api.table.Orders;
import com.saleor.saleor_api.table.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrderDetail {

    private Long id;

    private Double totalMoney;

    private Double quantity;

    private Float weight;

//    private Long orderId;

//    private String orderCode;

    private Long productId;

    private String productTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getOrderCode() {
//        return orderCode;
//    }
//
//    public void setOrderCode(String orderCode) {
//        this.orderCode = orderCode;
//    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}
