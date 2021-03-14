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

    public List<Purchase> findByCustomerId(Long id) {
        return purchaseRepository.findByCustomerId(id);
    }

    private Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    private void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }

    public Purchase updateById(Long id, Map<String, Object> fields) {
        Purchase purchase = findById(id);
        if (purchase == null) {
            return null;
        }

        fields.forEach((name, value) -> {
            try {
                if (name.equals("category")) {
                    purchase.setCategory((String) value);
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return savePurchase(purchase);
    }

}
