package com.redbird.PurchaseServer.service;

import com.redbird.PurchaseServer.model.*;
import com.redbird.PurchaseServer.repository.CheckRepository;
import com.redbird.PurchaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyFeignService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CheckRepository checkRepository;

    public Check registerCheck(List<BoughtGoodDTO> boughtGoodDTOList, PaymentMethod paymentMethod) {
        BoughtGoodDTO boughtGoodDTO = boughtGoodDTOList.get(0);
        Check check = new Check();
        check.setCustomerId(boughtGoodDTO.getCustomerId());
        check.setShopName(boughtGoodDTO.getShopName());
        check.setBoughtTime(boughtGoodDTO.getBoughtTime());
        check.setPaymentMethod(paymentMethod);
        registerPurchasesOnCheck(boughtGoodDTOList, check);
        return checkRepository.save(check);
    }

    // assist methods

    private void registerPurchasesOnCheck(List<BoughtGoodDTO> boughtGoodDTOList, Check check) {
        List<Purchase> purchaseList = convertToListPurchase(boughtGoodDTOList, check);
        purchaseRepository.saveAll(purchaseList);
    }

    public boolean checkAllFromSameShop(List<BuyGoodDTO> buyGoodDTOList) {
        if ((buyGoodDTOList == null) || (buyGoodDTOList.size()==0)) {
            return false;
        }
        boolean flag = true;
        String shopName = buyGoodDTOList.get(0).getGoodDTO().getShopName();
        for (BuyGoodDTO buyGoodDTO : buyGoodDTOList) {
            if (!buyGoodDTO.getGoodDTO().getShopName().equals(shopName)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public List<Purchase> convertToListPurchase(List<BoughtGoodDTO> boughtGoodDTOList, Check check) {
        if (boughtGoodDTOList == null) return null;
        List<Purchase> purchaseList = new ArrayList<>();
        boughtGoodDTOList.forEach(boughtGoodDTO -> {
            purchaseList.add(convertToPurchase(boughtGoodDTO, check));
        });
        return purchaseList;
    }

    public Purchase convertToPurchase(BoughtGoodDTO boughtGoodDTO, Check check) {
        if (boughtGoodDTO == null) return null;
        Purchase purchase = new Purchase();
        purchase.setName(boughtGoodDTO.getName());
        purchase.setDescription(boughtGoodDTO.getDescription());
        purchase.setCost(boughtGoodDTO.getCost());
        purchase.setCategory(boughtGoodDTO.getCategory());
        purchase.setAmount(boughtGoodDTO.getAmount());
        purchase.setCheck(check);
        return purchase;
    }
}
