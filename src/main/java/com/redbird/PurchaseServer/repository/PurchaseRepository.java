package com.redbird.PurchaseServer.repository;

import com.redbird.PurchaseServer.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerId(Long id);
}
