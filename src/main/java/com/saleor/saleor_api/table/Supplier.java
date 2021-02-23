package com.saleor.saleor_api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "supplier")
public class Supplier {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "create_by", nullable = false)
    private String createBy;

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private Set<ImportTicket> importTickets;

}
