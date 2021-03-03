package com.redbird.PurchaseServer.repository;

import com.redbird.PurchaseServer.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
