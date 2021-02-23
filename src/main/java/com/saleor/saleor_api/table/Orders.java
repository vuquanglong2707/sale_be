package com.saleor.saleor_api.table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_order")// tênSP
    private String codeOrder;

    @Column(name = "cash_money")// tênSP
    private Double cash_money;

    @Column(name = "discount_money")
    private Double discount_money;

    @Column(name = "discount_percent")
    private Float discount_percent;

    @Column(name = "paid_money")
    private Double paid_money;

    @Column(name = "total_money")
    private Double total_money;

    @Column(name = "address")
    private String address;

    @Column(name = "district_id")
    private Long district_id;

    @Column(name = "province_id")
    private Long province_id;

    @Column(name = "ward_id")
    private Long ward_id;

    @Column(name = "order_status")
    private Boolean order_status;

    @Column(name = "createdBy")
    private String createdBy;

    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate ;

    @Column(name = "modified_by")
    private String modified_by;

    @Basic(optional = false)
    @Column(name = "modifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedDate ;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    private Set<OrderDetail> listOrderDetail = new HashSet<>();

}