package com.finxis.cdm.crossproductapp.util;


import cdm.event.workflow.WorkflowStep;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Csv2Cdm {



    public Csv2Cdm() throws ParserConfigurationException {

    }

    public WorkflowStep convertFileX(File csvFile, String xmlFileName,
                                    char delimiter) {


        WorkflowStep workflowStep = WorkflowStep.builder().build();

        int rowsCount = -1;
        BufferedReader csvReader;
        try {

            csvReader = new BufferedReader(new FileReader(csvFile));

            //** Now using the OpenCSV **//
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(delimiter)
                    .build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(csvFile))
                    .withCSVParser(parser)
                    .build();


            //CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            int line = 0;
            List<String> headers = new ArrayList<String>(5);
            while ((nextLine = reader.readNext()) != null) {

                if (line == 0) { // Header row
                    for (String col : nextLine) {
                        headers.add(col);
                    }
                } else { // Data row

                    int col = 0;
                    for (String value : nextLine) {
                        String header = headers.get(col).replaceAll("[\\t\\p{Zs}\\u0020]", "_");

                        if (header.contains("/")) {
                            String[] tokens = header.split("\\/");
                            System.out.println("Header:" + header);

                            for (int l = 0; l < tokens.length - 1; l++) {


                            }
                        }

                        col++;

                    }
                }
                line++;
            }
            //** End of CSV parsing**//

            return workflowStep;

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }