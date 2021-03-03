package com.saleor.saleor_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrders {

    private Long id;

    private Double cash_money;

    private Double discount_money;

    private Float discount_percent;

    private Double paid_money;

    private String codeOrder;

    private Double total_money;

    private String address;

    private Long district_id;

    private Long province_id;

    private Long ward_id;

    private Boolean order_status;

    private String createdBy;

    Date createdDate ;

    private String modified_by;

    Date modifiedDate ;

    private String customer_name;

    private String customer_phone;


    private List<DtoOrderDetail> dtoOrderDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public Double getCash_money() {
        return cash_money;
    }

    public void setCash_money(Double cash_money) {
        this.cash_money = cash_money;
    }

    public Double getDiscount_money() {
        return discount_money;
    }

    public void setDiscount_money(Double discount_money) {
        this.discount_money = discount_money;
    }

    public Float getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(Float discount_percent) {
        this.discount_percent = discount_percent;
    }

    public Double getPaid_money() {
        return paid_money;
    }

    public void setPaid_money(Double paid_money) {
        this.paid_money = paid_money;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Long district_id) {
        this.district_id = district_id;
    }

    public Long getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Long province_id) {
        this.province_id = province_id;
    }

    public Long getWard_id() {
        return ward_id;
    }

    public void setWard_id(Long ward_id) {
        this.ward_id = ward_id;
    }

    public Boolean getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Boolean order_status) {
        this.order_status = order_status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public List<DtoOrderDetail> getDtoOrderDetails() {
        return dtoOrderDetails;
    }

    public void setDtoOrderDetails(List<DtoOrderDetail> dtoOrderDetails) {
        this.dtoOrderDetails = dtoOrderDetails;
    }

}
