package com.finxis.cdm.crossproductapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Currency {

        //Load reference data
        public List<List<String>>  getCcyReferenceData() {

            List<List<String>> records = new ArrayList<>();

            InputStream is = this.getClass().getResourceAsStream("/data/currency.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            {
                String line;

                while (true) {
                    try {
                        if (!((line = br.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            }
            return records;
        }
}
