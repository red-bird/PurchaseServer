package com.redbird.PurchaseServer.controller;

import com.redbird.PurchaseServer.model.CheckDTO;
import com.redbird.PurchaseServer.model.Purchase;
import com.redbird.PurchaseServer.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checks")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @GetMapping
    public List<CheckDTO> findAll() {
        return checkService.findAll();
    }

    @GetMapping("/{id}")
    public CheckDTO findById(@PathVariable("id") Long id) {
        return checkService.findById(id);
    }

    @GetMapping("/customer/{id}")
    public List<CheckDTO> findByCustomerId(@PathVariable("id") Long id) {
        return checkService.findByCustomerId(id);
    }

    @GetMapping("/shopName/{shop}")
    public List<CheckDTO> findByShopName(@PathVariable("shop") String shopName) {
        return checkService.findByShopName(shopName);
    }

    @GetMapping("/payment/{payment}")
    public List<CheckDTO> findByCustomerId(@PathVariable("payment") String paymentMethod) {
        return checkService.findByPaymentMethod(paymentMethod);
    }


}
