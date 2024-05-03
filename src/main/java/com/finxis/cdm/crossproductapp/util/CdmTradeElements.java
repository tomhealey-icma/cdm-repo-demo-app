package com.finxis.cdm.crossproductapp.util;

import cdm.base.math.NonNegativeQuantity;
import cdm.base.math.metafields.FieldWithMetaNonNegativeQuantitySchedule;
import cdm.event.common.BusinessEvent;
import cdm.observable.asset.Price;
import cdm.observable.asset.PriceSchedule;
import cdm.product.common.settlement.PriceQuantity;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CdmTradeElements {


    public String getPurchasePrice(BusinessEvent businessEvent){

        String purchasePrice = null;
        List<PriceQuantity> priceQuantity = (List<PriceQuantity>) businessEvent.getAfter().get(0).getTrade().getTradableProduct().getTradeLot().get(0).getPriceQuantity();

        FieldWithMetaNonNegativeQuantitySchedule quantity = null;

       int iCount= priceQuantity.size();
       int nCount = 0;
       String metValue = null;

        for(int i=0;i<iCount;i++) {
            nCount= priceQuantity.get(i).getQuantity().size();
            for(int n=0;n<nCount;n++){
                quantity = priceQuantity.get(i).getQuantity().get(n);
                if (quantity.getMeta() != null) {
                metValue = quantity.getMeta().getGlobalKey().trim().toString();
                if (quantity.getMeta().getGlobalKey().trim().toString().equals("PURCHASE_PRICE"))
                    purchasePrice = quantity.getValue().getValue().setScale(2, RoundingMode.UP).toString();
            }}
        }
        return purchasePrice;

    }

    public String getRepurchasePrice(BusinessEvent businessEvent){

        String repurchasePrice = null;
        List<PriceQuantity> priceQuantity = (List<PriceQuantity>) businessEvent.getAfter().get(0).getTrade().getTradableProduct().getTradeLot().get(0).getPriceQuantity();

        FieldWithMetaNonNegativeQuantitySchedule quantity = null;

        int iCount= priceQuantity.size();
        int nCount = 0;
        String metValue = null;

        for(int i=0;i<iCount;i++) {
            nCount= priceQuantity.get(i).getQuantity().size();
            for(int n=0;n<nCount;n++){
                quantity = priceQuantity.get(i).getQuantity().get(n);
                if (quantity.getMeta() != null) {
                metValue = quantity.getMeta().getGlobalKey().trim().toString();
                if (quantity.getMeta().getGlobalKey().trim().toString().equals("REPURCHASE_PRICE"))
                    repurchasePrice = quantity.getValue().getValue().setScale(2, RoundingMode.UP).toString();
            }}
        }
        return repurchasePrice;

    }

    public String getRepoInterest(BusinessEvent businessEvent){

        String repoInterest = null;
        List<PriceQuantity> priceQuantity = (List<PriceQuantity>) businessEvent.getAfter().get(0).getTrade().getTradableProduct().getTradeLot().get(0).getPriceQuantity();

        FieldWithMetaNonNegativeQuantitySchedule quantity = null;

        int iCount= priceQuantity.size();
        int nCount = 0;
        String metValue = null;

        for(int i=0;i<iCount;i++) {
            nCount= priceQuantity.get(i).getQuantity().size();
            for(int n=0;n<nCount;n++){
                quantity = priceQuantity.get(i).getQuantity().get(n);
                if (quantity.getMeta() != null) {
                metValue = quantity.getMeta().getGlobalKey().trim().toString();
                if (quantity.getMeta().getGlobalKey().trim().toString().equals("REPURCHASE_PRICE"))
                    repoInterest = quantity.getValue().getValue().setScale(2, RoundingMode.UP).toString();
            }}
        }
        return repoInterest;

    }

    public String getRepoRate(BusinessEvent businessEvent){

        String repoRate= null;
        List<PriceQuantity> priceQuantity = (List<PriceQuantity>) businessEvent.getAfter().get(0).getTrade().getTradableProduct().getTradeLot().get(0).getPriceQuantity();

        PriceSchedule price = null;

        int iCount= priceQuantity.size();
        int nCount = 0;
        String metValue = null;

        for(int i=0;i<iCount;i++) {
            nCount= priceQuantity.get(i).getPrice().size();
            for(int n=0;n<nCount;n++){
                price = priceQuantity.get(i).getPrice().get(n).getValue();
                if (priceQuantity.get(i).getPrice().get(n).getMeta() != null) {
                    metValue = priceQuantity.get(i).getPrice().get(n).getMeta().getGlobalKey().toString();
                    if (metValue.toString().equals("REPO_RATE"))
                        repoRate = priceQuantity.get(i).getPrice().get(n).getValue().getValue().toString();
                }}
        }
        return repoRate;

    }
}
