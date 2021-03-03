package com.redbird.PurchaseServer.controller;

import com.redbird.PurchaseServer.model.Purchase;
import com.redbird.PurchaseServer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> findAll() {
        return purchaseService.findAll();
    }

    @GetMapping("/{id}")
    public Purchase findById(@PathVariable("id") Long id) {
        return purchaseService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Purchase savePurchase(@RequestBody Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        purchaseService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Purchase updatePurchase(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return purchaseService.updateById(id, fields);
    }
}
