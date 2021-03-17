package com.redbird.PurchaseServer.repository;

import com.redbird.PurchaseServer.model.Check;
import com.redbird.PurchaseServer.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findByCustomerId(Long id);
    List<Check> findByPaymentMethod(PaymentMethod paymentMethod);
    List<Check> findByShopName(String shopName);
}
