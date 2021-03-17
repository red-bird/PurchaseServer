package com.redbird.PurchaseServer.client;

import com.redbird.PurchaseServer.model.BoughtGoodDTO;
import com.redbird.PurchaseServer.model.BuyQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ShopsClientFallback implements FallbackFactory<ShopsClient> {
    @Override
    public ShopsClient create(Throwable cause) {
        return new ShopsClient() {
            @Override
            public List<BoughtGoodDTO> findAll() {
                return null;
            }

            @Override
            public BoughtGoodDTO findById(Long id) {
                return null;
            }

            @Override
            public List<BoughtGoodDTO> findByGoodName(String goodName) {
                return null;
            }

            @Override
            public List<BoughtGoodDTO> findByShopName(String shopName) {
                return null;
            }

            @Override
            public List<BoughtGoodDTO> findByCustomerId(Long customerId) {
                return null;
            }

            @Override
            public List<BoughtGoodDTO> buyGoods(BuyQuery buyQuery) {
                return null;
            }
        };
    }
}
