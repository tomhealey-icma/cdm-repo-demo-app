package com.finxis.cdm.crossproductapp.util;


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.opencsv.exceptions.CsvValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLCreators {
    // Protected Properties

    protected static DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    protected static DocumentBuilder domBuilder = null;

    static {
        try {
            domBuilder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    Document newDoc = domBuilder.newDocument();

    public XMLCreators() throws ParserConfigurationException {

        domFactory = DocumentBuilderFactory.newInstance();
        domBuilder = domFactory.newDocumentBuilder();

        Document newDoc = domBuilder.newDocument();

    }

    public Document convertFile(File csvFile, String xmlFileName,
                                char delimiter) {

        int rowsCount = -1;
        BufferedReader csvReader;
        try {

            //String rootStr = "CdmNewTradeWorkflow xmlns=\"urn:icma:xsd:ICMARepoNewTrade\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:icma:xsd:ICMARepoNewTrade icma-cdm-repo-trade.xsd\"";
            String rootStr = "CdmNewTradeWorkflow";

            Element rootElement = newDoc.createElement(rootStr);
            newDoc.appendChild(rootElement);
            rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:icma:xsd:ICMARepoNewTrade");
            rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "urn:icma:xsd:ICMARepoNewTrade icma-cdm-repo-trade.xsd");


            String xmlName = "CdmNewTradeWorkflow";
            // Read csv file
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
                    //Element rowElement = newDoc.createElement("Workflow");
                    //rootElement.appendChild(rowElement);

                    int col = 0;
                    for (String value : nextLine) {
                        String header = headers.get(col).replaceAll("[\\t\\p{Zs}\\u0020]", "_");
                        //header = headers.get(col).replaceAll("[\\/]", "><");

                        if (header.contains("/")) {
                            String[] tokens = header.split("\\/");
                            System.out.println("Header:" + header);

                            addNode(rootElement, header, value);

                        }

                        col++;

                    }
                }
                line++;
            }
            //** End of CSV parsing**//

            FileWriter writer = null;

            try {

                writer = new FileWriter(new File(xmlFileName));


                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
                aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                Source src = new DOMSource(newDoc);

                Result result = new StreamResult(writer);
                aTransformer.transform(src, result);
                System.out.println(src);

                writer.flush();

            } catch (Exception exp) {
                exp.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }

            // Output to console for testing
            // Resultt result = new StreamResult(System.out);

        } catch (IOException exp) {
            System.err.println(exp.toString());
        } catch (Exception exp) {
            System.err.println(exp.toString());
        }
        return newDoc;
        // "XLM Document has been created" + rowsCount;
    }


    public boolean addNode(Element parent, String nodeStr, String value) {

        NodeList nodeList = parent.getChildNodes();

        int nl1 = nodeList.getLength();
        Element newEl = null;

        String[] tokens = nodeStr.split("/");
        if (tokens.length <= 1) {
            parent.appendChild(newDoc.createTextNode(value.trim()));
            return true;
        }


        String fchild = tokens[1];


        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals(fchild)) {
                String newNodeStr = nodeStr.substring(nodeStr.indexOf("/") + 1, nodeStr.length());
                if (newNodeStr != null && newNodeStr != "")
                    return addNode((Element) nodeList.item(i), newNodeStr, value);
                else
                    nodeList.item(i).appendChild(newDoc.createTextNode(value.trim()));
            }

        }

        String newNodeStr = nodeStr.substring(nodeStr.indexOf("/") + 1, nodeStr.length());
        if (newNodeStr != null && newNodeStr != "") {
            newEl = newDoc.createElement(fchild);
            parent.appendChild(newEl);
            System.out.println("Added Node:" + fchild);
            return addNode(newEl, newNodeStr, value);
        } else {
            parent.appendChild(newDoc.createTextNode(value.trim()));
        }
        return false;
    }


    public CdmNewTradeWorkflow convertFileWithCsvMap(File csvFile, String xmlFileName,
                                         char delimiter) {

        int rowsCount = -1;
        BufferedReader csvReader;

        IcmaRepoUtil iruilt = new IcmaRepoUtil();



        try {


            // Read csv file
            csvReader = new BufferedReader(new FileReader(csvFile));

            //** Now using the OpenCSV **//
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(delimiter)
                    .build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(csvFile))
                    .withCSVParser(parser)
                    .build();

            Map<String, Integer> cdmNewTradeMap = new HashMap<>();
            CsvFieldMap csvFieldMap = new CsvFieldMap();
            csvFieldMap.buildNewTradeMap(cdmNewTradeMap);
            CsvMapper csvMapper = new CsvMapper();

            ArrayList<CsvField> csvMap = csvMapper.NewTradeWorkflow(reader);

            CdmNewTradeWorkflow cdmt = new CdmNewTradeWorkflow();

           String t = csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowName")).fieldValue;

           CdmNewTradeWorkflow.Workflow cdmWf = new CdmNewTradeWorkflow.Workflow();

           String test = csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/WorkflowStepName")).fieldValue;



            cdmWf.setWorkflowName(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowName")).fieldValue);
            CdmNewTradeWorkflow.Workflow.WorkflowStep wfs = new CdmNewTradeWorkflow.Workflow.WorkflowStep();
            wfs.setWorkflowStepName(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/WorkflowStepName")).fieldValue);

            wfs.setTransactionDate(iruilt.convertStringtoGrgorianCalendar(
                    csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/TransactionDate")).fieldValue));

            wfs.setPurchaseDate(iruilt.convertStringtoGrgorianCalendar(
                    csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/PurchaseDate")).fieldValue));

            wfs.setRepurchaseDate(iruilt.convertStringtoGrgorianCalendar(
                    csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepurchaseDate")).fieldValue));


            wfs.setTradeType(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/TradeType")).fieldValue);
            wfs.setTermType(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/TermType")).fieldValue);
            wfs.setRateType(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RateType")).fieldValue);
            wfs.setDayCount(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/DayCount")).fieldValue);


            CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer buyer = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer();
            buyer.setLEI(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Buyer/LEI")).fieldValue);
            buyer.setLEIName(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Buyer/LEIName")).fieldValue);

            CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller seller = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller();
            seller.setLEI(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Seller/LEI")).fieldValue);
            seller.setLEIName(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Seller/LEIName")).fieldValue);

            CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier tradeId = new CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier();
            tradeId.setUTI(Integer.parseInt(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/TradeIdentifier/UTI")).fieldValue));
            wfs.setTradeIdentifier(tradeId);


            CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList collList = new CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList();

            CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral coll = new CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral();
            coll.setDescription(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Description")).fieldValue);
            coll.setISIN(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/ISIN")).fieldValue);
            coll.setCurrency(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Currency")).fieldValue);
            coll.setNominalAmount(Integer.parseInt(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/NominalPerUnitAmount")).fieldValue));
            coll.setDayCount(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/DayCount")).fieldValue);
            coll.setInterestRate(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/InterestRate")).fieldValue)));
            coll.setInterestPaymentFrequency(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/InterestPaymentFrequency")).fieldValue);
            coll.setNominalAmount(Integer.parseInt(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/NominalAmount")).fieldValue));
            coll.setCleanPrice(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/CleanPrice")).fieldValue)));
            coll.setAccruedInterestPrice(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AccruedInterestPrice")).fieldValue)));
            coll.setAccruedInterestAmount(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AccruedInterestAmount")).fieldValue)));
            coll.setDirtyPrice(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/DirtyPrice")).fieldValue)));
            coll.setUnAdjustedSettlementValue(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/UnAdjustedSettlementValue")).fieldValue)));
            coll.setMargin(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Margin")).fieldValue)));
            coll.setAdjustedSettlementValue(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AdjustedSettlementValue")).fieldValue)));
            coll.setHaircut(Short.valueOf(String.valueOf(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Haircut")).fieldValue)));
            coll.setSettlementAmount(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/SettlementAmount")).fieldValue)));

            CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan loan = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan();
            loan.setLoanAmount(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Loan/LoanAmount")).fieldValue)));
            loan.setLoanCurrency(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Loan/LoanCurrency")).fieldValue);

            CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate repoRate= new CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate();

            CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate fixedRepoRate = new CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate();
            fixedRepoRate.setValue(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/FixedRate/Value")).fieldValue)));


            CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating floatingRepoRate = new CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating();
            floatingRepoRate.setFloatingRateIndex(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndex")).fieldValue);
            floatingRepoRate.setFloatingRateIndexFreq(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexFreq")).fieldValue)));
            floatingRepoRate.setFloatingRateIndexRate(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexRate")).fieldValue)));
            floatingRepoRate.setFloatingRateIndexSpread(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexSpread")).fieldValue)));

            wfs.setPurchasePrice(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/PurchasePrice")).fieldValue)));
            wfs.setProjectedInterest(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/ProjectedInterest")).fieldValue)));
            wfs.setRepurchasePrice(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepurchasePrice")).fieldValue)));
            wfs.setDeliveryMethod(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/DeliveryMethod")).fieldValue);


            CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality optionality = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality();
            optionality.setTerminationOnDemand(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Optionality/TerminationOnDemand")).fieldValue);
            optionality.setNoticePeriod(Short.valueOf(String.valueOf(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Optionality/NoticePeriod")).fieldValue)));

            CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution substitution = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution();
            substitution.setAllowed(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Substitution/Allowed")).fieldValue);
            substitution.setNumberAllowed(Short.valueOf(String.valueOf(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Substitution/NumberAllowed")).fieldValue)));

            CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement agreement = new CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement();
            agreement.setAgreementName(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementName")).fieldValue);
            agreement.setAgreementIssuer(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementIssuer")).fieldValue);
            agreement.setAgreementGoverningLaw(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementGoverningLaw")).fieldValue);
            agreement.setAgreementDate(Integer.parseInt(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementDate")).fieldValue));

            agreement.setAgreementVersion(Integer.parseInt(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementVersion")).fieldValue));

            agreement.setAgreementIdentifier(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementIdentifier")).fieldValue);
            agreement.setAgreementEffectiveDate(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementEffectiveDate")).fieldValue);
            agreement.setAgreementUrl(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementUrl")).fieldValue);

            CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin initialMargin = new CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin();
            initialMargin.setValue(BigDecimal.valueOf(iruilt.doubleParseStr(csvMap.get(cdmNewTradeMap.get("CdmNewTradeWorkflow/Workflow/WorkflowStep/InitialMargin/Value")).fieldValue)));


            cdmt.setWorkflow(cdmWf);
            cdmWf.setWorkflowStep(wfs);
            wfs.setBuyer(buyer);
            wfs.setSeller(seller);
            collList.setCollateral(coll);
            wfs.setCollateralList(collList);
            wfs.setLoan(loan);
            repoRate.setFixedRate(fixedRepoRate);
            repoRate.setFloating(floatingRepoRate);
            wfs.setRepoRate(repoRate);
            wfs.setOptionality(optionality);
            wfs.setSubstitution(substitution);
            wfs.setAgreement(agreement);


            //** End of CSV parsing**//
            return cdmt;

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }


    }
}