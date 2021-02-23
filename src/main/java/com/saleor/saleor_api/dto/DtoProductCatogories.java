package com.saleor.saleor_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductCatogories {

    private Long id;

    private String title;

    private String code;

    private Boolean isActive;

    private String createBy;

    Date createdDate = new Date();

    private String modifiedBy;

    Date modifiedDate = new Date();

    private Long wareHouse_id;

    private String wareHouseTitle;

    public String getWareHouseTitle() {
        return wareHouseTitle;
    }

    public void setWareHouseTitle(String wareHouseTitle) {
        this.wareHouseTitle = wareHouseTitle;
    }

    private List<DtoProduct> dtoProducts= new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getWareHouse_id() {
        return wareHouse_id;
    }

    public void setWareHouse_id(Long wareHouse_id) {
        this.wareHouse_id = wareHouse_id;
    }

    public List<DtoProduct> getDtoProducts() {
        return dtoProducts;
    }

    public void setDtoProducts(List<DtoProduct> dtoProducts) {
        this.dtoProducts = dtoProducts;
    }


}
