package com.redbird.PurchaseServer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "checks")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String shopName;
    private ZonedDateTime boughtTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "check", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Purchase> purchases;
}
