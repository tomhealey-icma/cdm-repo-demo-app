package com.finxis.cdm.crossproductapp.util;

import java.util.List;
import java.util.Map;

public class CsvFieldMap {

        private List<Map.Entry<String, String>> list;
        public Map buildNewTradeMap(Map<String, Integer> map) {

            map.put("CdmNewTradeWorkflow/Workflow/WorkflowName",0);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/WorkflowStepName",1);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/TransactionDate",2);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/PurchaseDate",3);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepurchaseDate",4);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/TradeIdentifier/UTI",5);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/TradeType",6);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Seller/LEIName",7);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Seller/LEI",8);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Buyer/LEIName",9);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Buyer/LEI",10);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/TermType",11);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RateType",12);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/DayCount",13);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Description",14);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/ISIN",15);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Currency",16);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/NominalPerUnitAmount",17);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/DayCount",18);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/InterestRate",19);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/InterestPaymentFrequency",20);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/NominalAmount",21);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/CleanPrice",22);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AccruedInterestPrice",23);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AccruedInterestAmount",24);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/DirtyPrice",25);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/UnAdjustedSettlementValue",26);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Margin",27);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/AdjustedSettlementValue",28);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/Haircut",29);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/CollateralList/Collateral/SettlementAmount",30);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Loan/LoanAmount",31);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Loan/LoanCurrency",32);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/FixedRate/Value",33);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndex",34);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexFreq",35);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexRate",36);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepoRate/Floating/FloatingRateIndexSpread",37);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/PurchasePrice",38);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/ProjectedInterest",39);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/RepurchasePrice",40);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Optionality/TerminationOnDemand",41);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Optionality/NoticePeriod",42);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/DeliveryMethod",43);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Substitution/Allowed",44);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Substitution/NumberAllowed",45);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementName",46);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementIssuer",47);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementGoverningLaw",48);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementDate",49);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementVersion",50);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementIdentifier",51);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementEffectiveDate",52);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/Agreement/AgreementUrl",53);
            map.put("CdmNewTradeWorkflow/Workflow/WorkflowStep/InitialMargin/Value",54);



            return map;
        }
}
