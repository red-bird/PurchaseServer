package com.redbird.PurchaseServer.controller;

import com.redbird.PurchaseServer.model.PurchaseOutput;
import com.redbird.PurchaseServer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<PurchaseOutput> findAll() {
        return purchaseService.findAll();
    }

    @GetMapping("/{id}")
    public PurchaseOutput findById(@PathVariable("id") Long id) {
        return purchaseService.findById(id);
    }

    @GetMapping("/customer/{id}")
    public List<PurchaseOutput> findByCustomerId(@PathVariable("id") Long id) {
        return purchaseService.findByCustomerId(id);
    }

//    @PostMapping
//    public Purchase savePurchase(@RequestBody Purchase purchase) {
//        return purchaseService.savePurchase(purchase);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable("id") Long id) {
//        purchaseService.deleteById(id);
//    }

    @PatchMapping("/{id}")
    public PurchaseOutput updatePurchase(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return purchaseService.updateById(id, fields);
    }
}
