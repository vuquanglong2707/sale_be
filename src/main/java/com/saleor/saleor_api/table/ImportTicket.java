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

@Table(name = "import_ticket")
public class ImportTicket  {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code",nullable = false, unique = false)
    private String orderCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "title",nullable = false, unique = false)
    private String title;

    @Column(name = "create_by")
    private String createBy;

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "ship_code",nullable = false, unique = false)
    private String shipCode;

    @Column(name = "total",nullable = false, unique = false)
    private Long total;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "supplier_id",nullable = false, unique = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Supplier supplier;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "importTicket")
    private Set<ImportTicketDetail> importTicketDetails = new HashSet<>();



}
