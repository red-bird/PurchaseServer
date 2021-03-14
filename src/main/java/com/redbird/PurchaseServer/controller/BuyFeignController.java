package com.redbird.PurchaseServer.controller;

import com.redbird.PurchaseServer.client.ShopsClient;
import com.redbird.PurchaseServer.model.*;
import com.redbird.PurchaseServer.service.BuyFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buy")
public class BuyFeignController {

    @Autowired
    private ShopsClient shopsClient;

    @Autowired
    private BuyFeignService buyFeignService;

    @Autowired
    private Resilience4JCircuitBreakerFactory factory;

    @GetMapping
    public List<BoughtGoodDTO> findAll() {
        var circuit = factory.create("findAll-circuit");
        var res = shopsClient.findAll();
        return circuit.run(() -> res, null);
    }

    @GetMapping("/{id}")
    public BoughtGoodDTO findById(@PathVariable("id") Long id) {
        var circuit = factory.create("findById-circuit");
        var res = shopsClient.findById(id);
        return circuit.run(() -> res, null);
    }

    @GetMapping("/name/{name}")
    public List<BoughtGoodDTO> findByGoodName(@PathVariable("name") String goodName) {
        var circuit = factory.create("shop-circuit");
        var res = shopsClient.findByGoodName(goodName);
        return circuit.run(() -> res, null);
    }

    @GetMapping("/shop/{shop}")
    public List<BoughtGoodDTO> findByShopName(@PathVariable("shop") String shopName) {
        var circuit = factory.create("shop-circuit");
        var res = shopsClient.findByShopName(shopName);
        return circuit.run(() -> res, null);
    }

    @GetMapping("/customer/{customerId}")
    public List<BoughtGoodDTO> findByCustomerId(@PathVariable("customerId") Long customerId) {
        var circuit = factory.create("customerId-circuit");
        var res = shopsClient.findByCustomerId(customerId);
        return circuit.run(() -> res, null);
    }

    @PostMapping
    private CheckDTO buyGoods(@RequestBody BuyQuery buyQuery) {
        if (!isInEnum(buyQuery.getPaymentMethod(), PaymentMethod.class)) {
            return null;
        }

        if (!buyFeignService.checkAllFromSameShop(buyQuery.getBuyGoodDTOS())) {
            return null;
        }

        var circuit = factory.create("buyQuery-circuit");
        var res = shopsClient.buyGoods(buyQuery.getBuyGoodDTOS());
        List<BoughtGoodDTO> boughtGoodDTOList = circuit.run(() -> res, null);


        if ((boughtGoodDTOList == null) || (boughtGoodDTOList.size()==0)) {
            return null;
        }

        return buyFeignService.registerCheck(boughtGoodDTOList, PaymentMethod.valueOf(buyQuery.getPaymentMethod()));
    }



    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }
}
