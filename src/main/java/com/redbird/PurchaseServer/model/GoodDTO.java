package com.redbird.PurchaseServer.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class GoodDTO {
    private String name;
    private String description;
    private Double cost;
    private Long amount;
    private String shopName;
    private String category;
}
