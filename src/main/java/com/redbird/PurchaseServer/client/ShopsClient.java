package com.redbird.PurchaseServer.client;

import com.redbird.PurchaseServer.model.BoughtGoodDTO;
import com.redbird.PurchaseServer.model.BuyQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "shops-service", path = "/api/v1/buy")
public interface ShopsClient {

    @GetMapping
    public List<BoughtGoodDTO> findAll();

    @GetMapping("/{id}")
    public BoughtGoodDTO findById(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")
    public List<BoughtGoodDTO> findByGoodName(@PathVariable("name") String goodName);

    @GetMapping("/shop/{shop}")
    public List<BoughtGoodDTO> findByShopName(@PathVariable("shop") String shopName);

    @GetMapping("/customer/{customerId}")
    public List<BoughtGoodDTO> findByCustomerId(@PathVariable("customerId") Long customerId);

    @PostMapping
    public List<BoughtGoodDTO> buyGoods(@RequestBody BuyQuery buyQuery);

}
