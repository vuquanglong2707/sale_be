

package com.saleor.saleor_api.table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name="quantity")
    private Double quantity;

    @Column(name = "weight")
    private Float weight;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orders_id",nullable = false)
    private Orders orders;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;


}