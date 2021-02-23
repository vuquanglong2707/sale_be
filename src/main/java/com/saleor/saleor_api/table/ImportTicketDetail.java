package com.saleor.saleor_api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "import_ticket_detail")

public class ImportTicketDetail {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_price",nullable = false, unique = false)
    private Double importPrice;

    @Column(name = "total_price",nullable = false, unique = false)
    private Double totalPrice;

    @Column(name = "total_quantity",nullable = false, unique = false)
    private Double totalQuantity;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "import_ticket_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ImportTicket importTicket;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product  product;

}
