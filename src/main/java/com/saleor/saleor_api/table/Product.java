package com.saleor.saleor_api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id",  nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "createdBy")
    private String createdBy;

    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate ;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Basic(optional = false)
    @Column(name = "modifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedDate ;

    @Column(name = "barcode")
    private String barCode;

    @Column(name = "qrcode")
    private String qrCode;

    @Column(name = "content",columnDefinition = "text")
    private String content;

    @Column(name = "content_detail",columnDefinition = "text")
    private String descsDetail;

    @Column(name = "price")
    private Double price;

    @Column(name = "salePrice")
    private Double salePrice;

    @Column(name = "descs")
    private String descs;

    @Column(name = "quantity_sold") //Số lượng đã bán
    private Double quantitySold;

    @Column(name = "quantity_current") // Số lượng hiện tại
    private Double quantityCurrent;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ref_product_images")
    private List<String> images;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<Variation> variations = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ImportTicketDetail> importTicketDetails = new HashSet<>();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "product_catogories_id",nullable = false)
    private ProductCatogories productCatogories;

    // Unit 1-n Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Unit_id")
    private Units units;
}
