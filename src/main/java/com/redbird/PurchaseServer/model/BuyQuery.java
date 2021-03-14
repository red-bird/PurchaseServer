package com.redbird.PurchaseServer.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyQuery {
    private String paymentMethod;
    private List<BuyGoodDTO> buyGoodDTOS;
}
