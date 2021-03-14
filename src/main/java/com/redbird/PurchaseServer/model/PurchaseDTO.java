package com.redbird.PurchaseServer.model;

import lombok.Data;

@Data
public class PurchaseDTO {

    private String name;
    private String description;
    private Double cost;
    private String category;
    private Long amount;
}
