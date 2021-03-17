package com.redbird.PurchaseServer.model;

import lombok.Data;

@Data
public class PurchaseOutput {
    private Long id;

    private String name;
    private String description;
    private Double cost;
    private String category;
    private Long amount;
    private Long customerId;
    private String shopName;
}
