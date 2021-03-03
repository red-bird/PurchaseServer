package com.redbird.PurchaseServer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customer;
    private String date;
    private String goodName;
    private Double cost;
}
