//package com.saleor.saleor_api.table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "user_store")
//public class Userstore {
//    @Id
//    @Column(name = "id", nullable = false, unique = false, length = 20)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "product_id")
//    private Long productId;
//
//    @Column(name = "product_name")
//    private String productName;
//
//    @Column(name = "status")
//    private Boolean status;
//
//    @Column(name = "domain")
//    private String domain;
//
//    @JsonIgnore
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    @JsonIgnore
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//}