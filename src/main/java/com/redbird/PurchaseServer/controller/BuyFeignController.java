package com.redbird.PurchaseServer.controller;

import com.redbird.PurchaseServer.client.ShopsClient;
import com.redbird.PurchaseServer.model.*;
import com.redbird.PurchaseServer.service.BuyFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.openfeign.FeignCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buy")
public class BuyFeignController {

    @Autowired
    private ShopsClient shopsClient;

    @Autowired
    private BuyFeignService buyFeignService;

    @GetMapping
    public List<BoughtGoodDTO> findAll() {
        return shopsClient.findAll();
    }

    @GetMapping("/{id}")
    public BoughtGoodDTO findById(@PathVariable("id") Long id) {
        return shopsClient.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<BoughtGoodDTO> findByGoodName(@PathVariable("name") String goodName) {
        return shopsClient.findByGoodName(goodName);
    }

    @GetMapping("/shop/{shop}")
    public List<BoughtGoodDTO> findByShopName(@PathVariable("shop") String shopName) {
        return shopsClient.findByShopName(shopName);
    }

    @GetMapping("/customer/{customerId}")
    public List<BoughtGoodDTO> findByCustomerId(@PathVariable("customerId") Long customerId) {
        return shopsClient.findByCustomerId(customerId);
    }

    @PostMapping
    private CheckDTO buyGoods(@RequestBody BuyQuery buyQuery) {
        if (!isInEnum(buyQuery.getPaymentMethod(), PaymentMethod.class)) {
            return null;
        }

        List<BoughtGoodDTO> boughtGoodDTOList = shopsClient.buyGoods(buyQuery);

        if ((boughtGoodDTOList == null) || (boughtGoodDTOList.size()==0)) {
            return null;
        }

        return buyFeignService.registerCheck(boughtGoodDTOList, PaymentMethod.valueOf(buyQuery.getPaymentMethod()));
    }



    public List<BoughtGoodDTO> fallBack() {
        return null;
    }

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }
}
