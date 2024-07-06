package com.finxis.cdm.crossproductapp.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {


    public ArrayList<CsvField> NewTradeWorkflow(CSVReader reader) throws CsvValidationException, IOException {

        ArrayList<CsvField> csvMap = new ArrayList<CsvField>(40);

        String[] nextLine;
        int line = 0;
        List<String> headers = new ArrayList<String>(5);

        while ((nextLine = reader.readNext()) != null) {

            if (line == 0) { // Header row
                for (String col : nextLine) {
                    headers.add(col);
                }
            } else { // Data row
                //Element rowElement = newDoc.createElement("Workflow");
                //rootElement.appendChild(rowElement);

                int col = 0;
                for (String value : nextLine) {
                    String header = headers.get(col).replaceAll("[\\t\\p{Zs}\\u0020]", "_");

                    CsvField csvField = new CsvField();
                    csvField.fieldPosition = col;
                    csvField.fieldName = header;
                    csvField.fieldValue = value;

                    csvMap.add(csvField);

                    System.out.println(header + "=" + value);
                }

                col++;
            }
            line++;
        }
        return csvMap;
    }

}



