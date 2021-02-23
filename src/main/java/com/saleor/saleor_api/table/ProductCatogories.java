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
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_catogories")
public class ProductCatogories {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "create_by", nullable = false)
    private String createBy;

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Basic(optional = false)
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedDate = new Date();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "ware_house_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WareHouse wareHouse;

    @JsonIgnore
    @OneToMany(mappedBy = "productCatogories", fetch = FetchType.LAZY)
    private Set<Product> products;


}
