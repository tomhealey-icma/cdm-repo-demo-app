package com.finxis.cdm.crossproductapp;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.finxis.cdm.crossproductapp.ui.TradeEntryPanel;
import com.finxis.cdm.crossproductapp.ui.TradeEntryPanel.*;
import com.finxis.cdm.crossproductapp.util.CItem;
import com.finxis.cdm.crossproductapp.util.CdmEnumMap;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil.*;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil.*;

import javax.swing.*;

public class BookTrade {

    public String bookTrade(TradeEntryPanel tep) throws IOException, InterruptedException, ParseException {



        //Enum Mapping
        CdmEnumMap map = new CdmEnumMap();
        map.buildEnumMap(tep.cdmMap);

        String transactionTypeStr = tep.cdmMap.get(tep.transactionTypeField.getSelectedItem().toString());

        String tdt = tep.tradeDateField.getText();
        String tradeDateStr = tdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";

        String pdt = tep.purchaseDateField.getText();
        String purchaseDateStr = pdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";

        String rdt = tep.repurchaseDateField.getText();
        String repurchaseDateStr = rdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";

        String tradeUTIStr = tep.tradeUTIField.getText();


        CItem buyer = (CItem) tep.buyerLEIField.getSelectedItem();
        String buyerNameStr = buyer.getClabel();
        String buyerLEIStr = buyer.getCValue();
        if (buyerLEIStr.equals("")) {
            JOptionPane.showMessageDialog(tep.repoTradePanel, "Buyer cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
            throw new IOException("Buyer cannot be empty");
        }



        CItem seller = (CItem) tep.sellerLEIField.getSelectedItem();
        String sellerNameStr = seller.getClabel();
        String sellerLEIStr = seller.getCValue();
        if (sellerLEIStr.equals("")) {
            JOptionPane.showMessageDialog(tep.repoTradePanel, "Seller cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
            throw new IOException("Seller cannot be empty");
        }


        String collateralDescriptionStr = tep.collateralDescriptionField.getText();
        String collateralISINStr = tep.collateralISINField.getText();

        String collateralQuantityStr = tep.collateralQuantityField.getText().replaceAll(",", "").trim();
        String collateralCleanPriceStr = tep.collateralCleanPriceField.getText();
        String collateralDirtyPriceStr = tep.collateralDirtyPriceField.getText();
        String collateralAdjustedValueStr = tep.collateralAdjustedValueField.getText().replaceAll(",", "").trim();
        String collateralCurrencyStr = tep.collateralCurrencyField.getText();
        String repoRateStr = tep.repoRateField.getText();
        String cashCurrencyStr = tep.cashCurrencyField.getText();
        String haircutStr = tep.haircutField.getText();
        String termTypeStr = tep.termTypeField.getSelectedItem().toString();
        String terminationOptionStr = tep.terminationOptionField.getSelectedItem().toString();
        String noticePeriodStr = tep.noticePeriodField.getText();
        String deliveryMethodStr = tep.deliveryMethodField.getSelectedItem().toString();
        String substitutionAllowedStr = tep.substitutionAllowedField.getSelectedItem().toString();
        String rateTypeStr = tep.rateTypeField.getSelectedItem().toString();
        String dayCountFractionStr = tep.dayCountFractionField.getText();
        String purchasePriceStr = tep.purchasePriceField.getText().replaceAll(",", "").trim();
        String cashQuantityStr = purchasePriceStr;
        String repurchasePriceStr = tep.repurchasePriceField.getText().replaceAll(",", "").trim();

        //Predefined Values
        String agreementNameStr = tep.agreementNameField.getSelectedItem().toString();
        String agreementGoverningLawStr = "England";
        String agreementVintageStr = tep.agreementVersionField.getText();
        String agreementPublisherStr = "ICMA";
        String agreementDateStr = "2011-04-20";
        String agreementIdentifierStr = "GMRA-2011_2011-04-20";
        String agreementEffectiveDate = "2011-04-20";
        String agreementUrl = "https://www.icmagroup.org/assets/documents/Legal/GMRA-2011_2011.04.20_formular_copy-incl-annexes-and-buy-sell-back-annex.pdf";
        String businessCenter = tep.businessCenterOptionField.getSelectedItem().toString();
        String execVenueCode = tep.venueCodeOptionField.getSelectedItem().toString();
        String execVenueScheme = "MIC";

        String floatingRateReferenceStr = tep.floatingRateReferenceField.getText();
        String floatingRateReferencePeriodStr = tep.floatingRateReferencePeriodField.getSelectedItem().toString();
        String floatingRateReferenceMultiplierStr = tep.floatingRateReferenceMultiplierField.getText();
        String floatingRateResetFreqStr = tep.floatingRateResetFreqField.getSelectedItem().toString();
        String floatingRateResetMultiplierStr = tep.floatingRateResetMultiplierField.getText();
        String floatingRatePaymentFreqStr = tep.floatingRatePaymentFreqField.getSelectedItem().toString();
        String floatingRatePaymentMultiplierStr = tep.floatingRatePaymentMultiplierField.getText();

        //Set settlement agent
        CItem settlementAgent = (CItem) tep.settlementAgentOptionField.getSelectedItem();
        String settlementAgentNameStr = settlementAgent.getClabel();
        String settlementAgentLEIStr = settlementAgent.getCValue();

        if (settlementAgentNameStr.equals("")) {
            JOptionPane.showMessageDialog(tep.repoTradePanel, "Settlement Agent cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
            throw new IOException("Settlement Agent cannot be empty");
        }

        //Set ccp
        CItem ccp = (CItem) tep.centralClearingCounterpartyOptionField.getSelectedItem();
        String ccpNameStr = ccp.getClabel();
        String ccpLeiStr = ccp.getCValue();

        //Set CSD Participant
        CItem csdParticipant = (CItem) tep.csdOptionField.getSelectedItem();
        String csdParticipantNameStr = csdParticipant.getClabel();
        String csdParticipantLeiStr = csdParticipant.getCValue();

        //Set Clearing Member
        CItem clearingMember = (CItem) tep.clearingMemberField.getSelectedItem();
        String clearingMemberNameStr = clearingMember.getClabel();
        String clearingMemberLeiStr = clearingMember.getCValue();

        //Set Broker
        CItem broker = (CItem) tep.brokerField.getSelectedItem();
        String brokerNameStr = broker.getClabel();
        String brokerLeiStr = broker.getCValue();

        //Set Triparty
        CItem triparty = (CItem) tep.tripartyField.getSelectedItem();
        String tripartyNameStr = triparty.getClabel();
        String tripartyLeiStr = triparty.getCValue();

        if ((deliveryMethodStr.equals("TP")) && (tripartyNameStr.equals(""))) {
            JOptionPane.showMessageDialog(tep.repoTradePanel, "Triparty Agent cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
            throw new IOException("Triparty Agent cannot be empty");
        }


        deliveryMethodStr = tep.cdmMap.get(deliveryMethodStr);
        floatingRateReferencePeriodStr = tep.cdmMap.get(floatingRateReferencePeriodStr);
        floatingRateResetFreqStr = tep.cdmMap.get(floatingRateResetFreqStr);
        floatingRatePaymentFreqStr = tep.cdmMap.get(floatingRatePaymentFreqStr);
        floatingRateReferenceStr = tep.cdmMap.get(floatingRateReferenceStr);


        String tid = tradeUTIStr;
        tep.tradeIdField.setText(tid);
        String firmTradeId = tid;
        RepoLifeCycle repoExecution = new RepoLifeCycle();

        String businessEvent = repoExecution.RepoExecution(
                tradeDateStr,                    // tradeDateStr = .getText();
                purchaseDateStr,                // purchaseDateStr
                repurchaseDateStr,                // repurchaseDateStr
                firmTradeId,                    // Firm Trade ID
                tradeUTIStr,                    // tradeUTIStr
                buyerLEIStr,                    // buyerLEIStr,
                buyerNameStr,                    // buyerNameStr
                sellerLEIStr,                    // sellerLEIStr
                sellerNameStr,
                collateralDescriptionStr,        // collateralDescriptionStr
                collateralISINStr,                // collateralISINStr
                collateralQuantityStr,            // collateralQuantitySt
                collateralCleanPriceStr,        // collateralDirtyPriceStr
                collateralDirtyPriceStr,        // collateralDirtyPriceStr
                collateralAdjustedValueStr,        // collateralAdjustedValueStr
                collateralCurrencyStr,            // collateralCurrencyStr
                repoRateStr,                    // repoRateStr
                cashCurrencyStr,                // cashCurrencyStr
                cashQuantityStr,                // cashQuantityStr
                haircutStr,                        // haircutStr
                termTypeStr,                    // termTypeStr
                terminationOptionStr,            // terminationOptionStr
                noticePeriodStr,                // noticePeriodStr
                deliveryMethodStr,                // deliveryMethodStr
                substitutionAllowedStr,            // substitutionAllowedStr
                rateTypeStr,                    // rateTypeStr
                dayCountFractionStr,            // dayCountFractionStr
                purchasePriceStr,                // purchasePriceStr
                repurchasePriceStr,                // repurchasePriceStr
                agreementNameStr,
                agreementGoverningLawStr,
                agreementVintageStr,
                agreementPublisherStr,
                agreementDateStr,
                agreementIdentifierStr,
                agreementEffectiveDate,
                agreementUrl,
                businessCenter,
                execVenueCode,
                execVenueScheme,
                settlementAgentLEIStr,
                settlementAgentNameStr,
                ccpLeiStr,
                ccpNameStr,
                csdParticipantLeiStr,
                csdParticipantNameStr,
                brokerLeiStr,
                brokerNameStr,
                tripartyLeiStr,
                tripartyNameStr,
                clearingMemberLeiStr,
                clearingMemberNameStr,
                floatingRateReferenceStr,
                floatingRateReferencePeriodStr,
                floatingRateReferenceMultiplierStr,
                floatingRateResetFreqStr,
                floatingRateResetMultiplierStr,
                floatingRatePaymentFreqStr,
                floatingRatePaymentMultiplierStr,
                transactionTypeStr
        );

        return businessEvent;

    }

    public void loadNewTrade(TradeEntryPanel tep) throws SQLException, IOException {


        //Trade Date

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(tep.defaultLocalTimeZone));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTimeString = startDateTime.format(formatter);
        tep.tradeDateField.setText(formattedDateTimeString);

        //Execution Time
        DateTimeFormatter ETformatter = DateTimeFormatter.ofPattern("hhmmss");
        tep.ETzonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(tep.defaultLocalTimeZone));
        tep.ETformattedDateTimeString = tep.ETzonedDateTime.format(ETformatter);
        tep.executionTimeField.setText(tep.ETformattedDateTimeString);

        //Purchase Date
        tep.purchaseDateField.setText(formattedDateTimeString);


        //Repurchase Date
        ZonedDateTime endDateTime = startDateTime.plusDays(1);
        formattedDateTimeString = endDateTime.format(formatter);
        tep.repurchaseDateField.setText(formattedDateTimeString);

        tep.buyerLEIField.setSelectedIndex(1);
        tep.sellerLEIField.setSelectedIndex(2);

        //UTI
        String tdt = tep.tradeDateField.getText();
        String tradeUTIStr = "ICMA"+ tdt.trim().replaceAll("-", "") + tep.executionTimeField.getText().trim().replaceAll("\\s", "");

        tep.tradeUTIField.setText(tradeUTIStr);


        //Collateral
        tep.collateralDescriptionField.setText("GILT .625 31/07/2035");


        //Collateral ISIN
        tep.collateralISINField.setText("GB00BMGR2916");


        //Collateral Quantity
        tep.collateralQuantityField.setText("1000");


        //Collateral Price
        tep.collateralCleanPriceField.setText("91.06");


        //Collateral Dirty Price
        tep.collateralDirtyPriceField.setText("91.8066");


        //Collateral Adjusted Value
        tep.collateralAdjustedValueField.setText("918066.00");

        //Collateral Currency
        tep.collateralCurrencyField.setText("GBP");


        //Repo Rate
        tep.repoRateField.setText("4.65");


        //Cash Currency
        tep.cashCurrencyField.setText("GBP");


        //Haircut
        tep.haircutField.setText("2");


        //Term Type
        tep.termTypeField.setSelectedItem("FIXED");


        //Day Count
        tep.dayCountFractionField.setText("ACT/360");

        //Term Days
        long daysBetween = Duration.between(startDateTime, endDateTime).toDays();
        DecimalFormat intFormat = new DecimalFormat("###");
        String termsDaysStr = intFormat.format(daysBetween);
        tep.termDaysField.setText(termsDaysStr);


        tep.settlementAgentOptionField.setSelectedIndex(1);

        tep.rateTypeField.setSelectedItem("FIXED");
        tep.rateTypeFieldEvent("FIXED");

        tep.collateralTypeField.setSelectedItem("Special");
        tep.collateralTypeFieldEvent("Special");

        tep.agreementNameField.setSelectedItem("GMRA");

        tep.agreementVersionField.setText("2011");

        //Reset trade states to null
        tep.afterTradeStateStr = null;
        tep.beforeTradeStateStr = null;
        tep.tradeStateStr = null;

        tep.updateTotalsXPrice();



    }
}
