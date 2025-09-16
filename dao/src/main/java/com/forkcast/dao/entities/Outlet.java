package com.forkcast.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outlets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outlet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false) 
    private User owner;

    @Column(name = "outlet_name")
    private String outletName;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    private String city;

    private String state;

    @Column(name = "pin_code")
    private Integer pinCode;
}
