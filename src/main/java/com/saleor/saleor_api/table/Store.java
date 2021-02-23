//package com.saleor.saleor_api.table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.Set;
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//
//@Table(name = "store")
//public class Store {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "images")
//    private String images;
//
//    @Column(name = "url")
//    private String url;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "userStore", fetch = FetchType.LAZY)
//    private Set<Userstore> refLandingPageUsers ;
//}
