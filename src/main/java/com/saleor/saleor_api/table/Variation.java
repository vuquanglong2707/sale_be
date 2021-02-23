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

//public class Variation {
//
//}
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "variation")

public class Variation {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false, unique = false)
    private String title;

//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive;

    @Column(name = "create_by", nullable = false)
    private String createBy;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ref_product_properties")
    private List<String> properties;

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product  product;


}
