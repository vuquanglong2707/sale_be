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
public class DtoProduct {

    private Long id;

    private String name;

    private String sku;

    private Boolean isActive;

    private String createdBy;

    private Date createdDate ;

    private String modifiedBy;

    private  Date modifiedDate;

    private String barCode;

    private String qrCode;

    private String content;

    private Double price;

    private Double salePrice;

    private String descs;

    private Double quantitySold;

    private Double quantityCurrent;

    private List<String> images;
//
    private List<DtoVariation> dtoVariationList= new ArrayList<>();

    public List<DtoVariation> getDtoVariationList() {
        return dtoVariationList;
    }

    public void setDtoVariationList(List<DtoVariation> dtoVariationList) {
        this.dtoVariationList = dtoVariationList;
    }
//    private Long productCategoriesId;

    private  String productCategoriesName;

    private String unitsTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Double getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Double quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Double getQuantityCurrent() {
        return quantityCurrent;
    }

    public void setQuantityCurrent(Double quantityCurrent) {
        this.quantityCurrent = quantityCurrent;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
//
//    public List<DtoProductProperties> getDtoProductProperties() {
//        return dtoProductProperties;
//    }
//
//    public void setDtoProductProperties(List<DtoProductProperties> dtoProductProperties) {
//        this.dtoProductProperties = dtoProductProperties;
//    }

//    public Long getProductCategoriesId() {
//        return productCategoriesId;
//    }
//
//    public void setProductCategoriesId(Long productCategoriesId) {
//        this.productCategoriesId = productCategoriesId;
//    }

    public String getProductCategoriesName() {
        return productCategoriesName;
    }

    public void setProductCategoriesName(String productCategoriesName) {
        this.productCategoriesName = productCategoriesName;
    }

    public String getUnitsTitle() {
        return unitsTitle;
    }

    public void setUnitsTitle(String unitsTitle) {
        this.unitsTitle = unitsTitle;
    }
}

