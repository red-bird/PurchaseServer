package com.redbird.PurchaseServer.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckDTO {

    private Long customerId;
    private String shopName;
    private ZonedDateTime boughtTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
}
