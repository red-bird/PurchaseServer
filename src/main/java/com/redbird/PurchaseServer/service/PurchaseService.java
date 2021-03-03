package com.redbird.PurchaseServer.service;

import com.redbird.PurchaseServer.model.Purchase;
import com.redbird.PurchaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }

    public Purchase updateById(Long id, Map<String, Object> fields) {
        Purchase purchase = findById(id);
        if (purchase == null) {
            return null;
        }

        fields.forEach((name, value) -> {
            if (name.equals("customer")) {
                try {
                    purchase.setCustomer(Long.parseLong((String)value));
                }
                catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (name.equals("date")) {
                purchase.setGoodName((String) value);
            }
            if (name.equals("goodName")) {
                purchase.setGoodName((String) value);
            }
            if (name.equals("cost")) {
                try {
                    purchase.setCost(Double.parseDouble((String)value));
                }
                catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        savePurchase(purchase);
        return purchase;
    }
}
