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

    public CheckDTO registerCheck(List<BoughtGoodDTO> boughtGoodDTOList, PaymentMethod paymentMethod) {
        Check check = makeCheck(boughtGoodDTOList.get(0), paymentMethod);
        check = checkRepository.save(check);
        List<Purchase> purchaseList = registerPurchasesOnCheck(boughtGoodDTOList, check);
        return makeCheckDTO(check, convertToPurchaseDTOList(purchaseList));
    }

    // assist methods

    private Check makeCheck(BoughtGoodDTO boughtGoodDTO, PaymentMethod paymentMethod) {
        Check check = new Check();
        check.setCustomerId(boughtGoodDTO.getCustomerId());
        check.setShopName(boughtGoodDTO.getShopName());
        check.setBoughtTime(boughtGoodDTO.getBoughtTime());
        check.setPaymentMethod(paymentMethod);
        return check;
    }

    private CheckDTO makeCheckDTO(Check check, List<PurchaseDTO> purchaseDTOList) {
        CheckDTO checkDTO = new CheckDTO();
        checkDTO.setCustomerId(check.getCustomerId());
        checkDTO.setShopName(check.getShopName());
        checkDTO.setBoughtTime(check.getBoughtTime());
        checkDTO.setPaymentMethod(check.getPaymentMethod());
        checkDTO.setPurchaseDTOList(purchaseDTOList);
        return checkDTO;
    }

    private List<PurchaseDTO> convertToPurchaseDTOList(List<Purchase> purchaseList) {
        if (purchaseList == null) return null;
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        purchaseList.forEach(purchase -> {
            purchaseDTOList.add(convertToPurchaseDTO(purchase));
        });
        return purchaseDTOList;
    }

    private PurchaseDTO convertToPurchaseDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setName(purchase.getName());
        purchaseDTO.setDescription(purchase.getDescription());
        purchaseDTO.setCost(purchase.getCost());
        purchaseDTO.setCategory(purchase.getCategory());
        purchaseDTO.setAmount(purchase.getAmount());
        return purchaseDTO;
    }

    private List<Purchase> registerPurchasesOnCheck(List<BoughtGoodDTO> boughtGoodDTOList, Check check) {
        List<Purchase> purchaseList = convertToListPurchase(boughtGoodDTOList, check);
        return purchaseRepository.saveAll(purchaseList);
    }

//    public boolean checkAllFromSameShop(List<BuyGoodDTO> buyGoodDTOList) {
//        if ((buyGoodDTOList == null) || (buyGoodDTOList.size()==0)) {
//            return false;
//        }
//        boolean flag = true;
//        String shopName = buyGoodDTOList.get(0).getGoodDTO().getShopName();
//        for (BuyGoodDTO buyGoodDTO : buyGoodDTOList) {
//            if (!buyGoodDTO.getGoodDTO().getShopName().equals(shopName)) {
//                flag = false;
//                break;
//            }
//        }
//        return flag;
//    }

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
        purchase.setCustomerId(boughtGoodDTO.getCustomerId());
        purchase.setCheck(check);
        return purchase;
    }
}
