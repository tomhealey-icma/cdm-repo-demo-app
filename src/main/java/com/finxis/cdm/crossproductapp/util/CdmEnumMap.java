package com.finxis.cdm.crossproductapp.util;

import java.util.List;
import java.util.Map;

public class CdmEnumMap {


        private List<Map.Entry<String, String>> list;
        public Map buildEnumMap(Map<String, String> map) {

            map.put("DVP", "DELIVERY_VERSUS_PAYMENT");
            map.put("TP", "DELIVERY_VERSUS_PAYMENT");
            map.put("FOP", "FREE_OF_PAYMENT");

            map.put("DAYS", "D");
            map.put("WEEKS", "W");
            map.put("MONTHS", "M");
            map.put("YEARS", "Y");

            map.put("DAY", "D");
            map.put("WEEK", "W");
            map.put("MONTH", "M");
            map.put("YEAR", "Y");

            map.put("SONIA", "GBP_SONIA");
            map.put("ESTR", "EUR_EUROSTR");

            map.put("Repurchase Agreement", "REPURCHASE_AGREEMENT");
            map.put("Buy/Sell-Back Agreement", "BUY/SELL_BACK_AGREEMENT");

            return map;
        }
}
