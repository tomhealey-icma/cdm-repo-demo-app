package com.finxis.cdm.crossproductapp.util;

public class CItem {

        String label;
        String value;
        String code;
        String scheme;

        public CItem(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public CItem(String label, String value, String code, String scheme){
            this.label = label;
            this.value = value;
            this.code = code;
            this.scheme = scheme;
        }

        public String getClabel() {
            return label;
        }

        public String getCValue(){

            return value;

        }

        @Override
        public String toString() {
            return label;
        }

}
