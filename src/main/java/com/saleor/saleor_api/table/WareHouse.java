package com.saleor.saleor_api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "ware_house")
public class WareHouse {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "manager", nullable = false)
    private String manager;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "district_id",nullable = false)
    private String district_id;

    @Column(name = "province_id",nullable = false)
    private String province_id;

    @Column(name = "ward_id",nullable = false)
    private String ward_id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "shop_id")
    private  Shop shop;

//    @JsonIgnore
//    @OneToMany(mappedBy = "wareHouse", fetch = FetchType.LAZY)
//    private Set<ImportTicket> importTickets;

//    @JsonIgnore
//    @OneToMany(mappedBy = "wareHouse", fetch = FetchType.LAZY)
//    private Set<ProductCatogories> productCatogories;
    
}
