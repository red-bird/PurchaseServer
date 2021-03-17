package com.redbird.PurchaseServer.service;

import com.redbird.PurchaseServer.model.Purchase;
import com.redbird.PurchaseServer.model.PurchaseOutput;
import com.redbird.PurchaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<PurchaseOutput> findAll() {
        return convertToPurchaseOutputs(purchaseRepository.findAll());
    }

    public PurchaseOutput findById(Long id) {
        return convertToPurchaseOutput(purchaseRepository.findById(id).orElse(null));
    }

    public List<PurchaseOutput> findByCustomerId(Long id) {
        return convertToPurchaseOutputs(purchaseRepository.findByCustomerId(id));
    }

    private Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    private void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }

    public PurchaseOutput updateById(Long id, Map<String, Object> fields) {
        Purchase purchase = purchaseRepository.findById(id).orElse(null);
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
        return convertToPurchaseOutput(savePurchase(purchase));
    }

    private List<PurchaseOutput> convertToPurchaseOutputs(List<Purchase> purchaseList) {
        if (purchaseList == null) return null;
        List<PurchaseOutput> purchaseOutputs = new ArrayList<>();
        purchaseList.forEach(purchase -> {
            purchaseOutputs.add(convertToPurchaseOutput(purchase));
        });
        return purchaseOutputs;
    }
    private PurchaseOutput convertToPurchaseOutput(Purchase purchase) {
        if (purchase == null) return null;
        PurchaseOutput output = new PurchaseOutput();
        output.setId(purchase.getId());
        output.setName(purchase.getName());
        output.setDescription(purchase.getDescription());
        output.setCost(purchase.getCost());
        output.setCategory(purchase.getCategory());
        output.setAmount(purchase.getAmount());
        output.setShopName(purchase.getCheck().getShopName());
        output.setCustomerId(purchase.getCustomerId());
        return output;
    }

}
