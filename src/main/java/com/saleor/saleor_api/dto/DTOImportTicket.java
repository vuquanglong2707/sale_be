package com.saleor.saleor_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTOImportTicket {
    private  Long id;
    private String phone;
    private String title;
    private String createBy;
    Date createdDate = new Date();
    private String modifiedBy;
    private String note;
    private String shipCode;
    private Long total;
    private String orderCode;
    private  Long supplierId;
    private List<DTOImportTicketDetail> importTicketDetails = new ArrayList<>();
    private  String supplierTitle;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<DTOImportTicketDetail> getImportTicketDetails() {
        return importTicketDetails;
    }

    public void setImportTicketDetails(List<DTOImportTicketDetail> ticketDetailList) {
        this.importTicketDetails = ticketDetailList;
    }

    public String getSupplierTitle() {
        return supplierTitle;
    }

    public void setSupplierTitle(String supplierTitle) {
        this.supplierTitle = supplierTitle;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }


}
