package com.redbird.PurchaseServer.service;

import com.redbird.PurchaseServer.model.*;
import com.redbird.PurchaseServer.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CheckService {

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private PurchaseService purchaseService;

    public CheckDTO findById(Long id) {
        Check check = checkRepository.findById(id).orElse(null);
        return makeCheckDTO(check, convertToPurchaseDTOList(check.getPurchases()));
    }

    public List<CheckDTO> findAll() {
        return convertCheckListToDTO(checkRepository.findAll());
    }

    public List<CheckDTO> findByCustomerId(Long id) {
        return convertCheckListToDTO(checkRepository.findByCustomerId(id));
    }

    public List<CheckDTO> findByPaymentMethod(String paymentMethod) {
        if (!isInEnum(paymentMethod, PaymentMethod.class)) return null;
        return convertCheckListToDTO(checkRepository.findByPaymentMethod(PaymentMethod.valueOf(paymentMethod)));
    }

    public List<CheckDTO> findByShopName(String shopName) {
        return convertCheckListToDTO(checkRepository.findByShopName(shopName));
    }

    // assist methods

    private List<CheckDTO> convertCheckListToDTO(List<Check> checkList) {
        if (checkList == null) return null;
        List<CheckDTO> checkDTOList = new ArrayList<>();
        checkList.forEach(check -> {
            checkDTOList.add(makeCheckDTO(check, convertToPurchaseDTOList(check.getPurchases())));
        });
        return checkDTOList;
    }

    private CheckDTO makeCheckDTO(Check check, List<PurchaseDTO> purchaseDTOList) {
        if (check == null) return null;
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
        if (purchase == null) return null;
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setName(purchase.getName());
        purchaseDTO.setDescription(purchase.getDescription());
        purchaseDTO.setCost(purchase.getCost());
        purchaseDTO.setCategory(purchase.getCategory());
        purchaseDTO.setAmount(purchase.getAmount());
        return purchaseDTO;
    }

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }
}
