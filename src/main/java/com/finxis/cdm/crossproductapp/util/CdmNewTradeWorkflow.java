
package com.finxis.cdm.crossproductapp.util;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Workflow">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkflowName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="WorkflowStep">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="WorkflowStepName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="PurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="RepurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="TradeIdentifier">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="UTI" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="TradeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Seller">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Buyer">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="TermType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RateType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CollateralList">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Collateral">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="ISIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="NominalPerUnitAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                                                 &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="InterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="InterestPaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="NominalAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                                                 &lt;element name="CleanPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="AccruedInterestPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="AccruedInterestAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="DirtyPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="UnAdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="Margin" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="AdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="Haircut" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                                                 &lt;element name="SettlementAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Loan">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="LoanAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="LoanCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="RepoRate">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="FixedRate">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="Floating">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="FloatingRateIndex" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                                 &lt;element name="FloatingRateIndexFreq" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                                 &lt;element name="FloatingRateIndexRate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                                 &lt;element name="FloatingRateIndexSpread" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="PurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="ProjectedInterest" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="RepurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="Optionality">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="TerminationOnDemand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="NoticePeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="DeliveryMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Substitution">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Allowed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="NumberAllowed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Agreement">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AgreementName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AgreementIssuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AgreementGoverningLaw" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AgreementDate" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                                       &lt;element name="AgreementVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                                       &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="AgreementEffectiveDate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="AgreementUrl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="InitialMargin">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "workflow"
})
@XmlRootElement(name = "CdmNewTradeWorkflow", namespace = "urn:icma:xsd:ICMARepoNewTrade")
public class CdmNewTradeWorkflow {

    @XmlElement(name = "Workflow", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
    protected CdmNewTradeWorkflow.Workflow workflow;

    /**
     * Gets the value of the workflow property.
     * 
     * @return
     *     possible object is
     *     {@link CdmNewTradeWorkflow.Workflow }
     *     
     */
    public CdmNewTradeWorkflow.Workflow getWorkflow() {
        return workflow;
    }

    /**
     * Sets the value of the workflow property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdmNewTradeWorkflow.Workflow }
     *     
     */
    public void setWorkflow(CdmNewTradeWorkflow.Workflow value) {
        this.workflow = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="WorkflowName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="WorkflowStep">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="WorkflowStepName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="PurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="RepurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="TradeIdentifier">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="UTI" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="TradeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Seller">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Buyer">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="TermType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RateType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CollateralList">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Collateral">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="ISIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="NominalPerUnitAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *                                       &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="InterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="InterestPaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="NominalAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *                                       &lt;element name="CleanPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="AccruedInterestPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="AccruedInterestAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="DirtyPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="UnAdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="Margin" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="AdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="Haircut" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *                                       &lt;element name="SettlementAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Loan">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="LoanAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="LoanCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="RepoRate">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="FixedRate">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="Floating">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="FloatingRateIndex" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                                       &lt;element name="FloatingRateIndexFreq" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                                       &lt;element name="FloatingRateIndexRate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                                       &lt;element name="FloatingRateIndexSpread" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="PurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="ProjectedInterest" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="RepurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="Optionality">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="TerminationOnDemand" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="NoticePeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="DeliveryMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Substitution">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Allowed" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="NumberAllowed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Agreement">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="AgreementName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="AgreementIssuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="AgreementGoverningLaw" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="AgreementDate" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *                             &lt;element name="AgreementVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *                             &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="AgreementEffectiveDate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="AgreementUrl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="InitialMargin">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "workflowName",
        "workflowStep"
    })
    public static class Workflow {

        @XmlElement(name = "WorkflowName", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
        protected String workflowName;
        @XmlElement(name = "WorkflowStep", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
        protected CdmNewTradeWorkflow.Workflow.WorkflowStep workflowStep;

        /**
         * Gets the value of the workflowName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWorkflowName() {
            return workflowName;
        }

        /**
         * Sets the value of the workflowName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWorkflowName(String value) {
            this.workflowName = value;
        }

        /**
         * Gets the value of the workflowStep property.
         * 
         * @return
         *     possible object is
         *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep }
         *     
         */
        public CdmNewTradeWorkflow.Workflow.WorkflowStep getWorkflowStep() {
            return workflowStep;
        }

        /**
         * Sets the value of the workflowStep property.
         * 
         * @param value
         *     allowed object is
         *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep }
         *     
         */
        public void setWorkflowStep(CdmNewTradeWorkflow.Workflow.WorkflowStep value) {
            this.workflowStep = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="WorkflowStepName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="PurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="RepurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="TradeIdentifier">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="UTI" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="TradeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Seller">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Buyer">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="TermType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RateType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CollateralList">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Collateral">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="ISIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="NominalPerUnitAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
         *                             &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="InterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="InterestPaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="NominalAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
         *                             &lt;element name="CleanPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="AccruedInterestPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="AccruedInterestAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="DirtyPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="UnAdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="Margin" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="AdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="Haircut" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
         *                             &lt;element name="SettlementAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Loan">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="LoanAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="LoanCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="RepoRate">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="FixedRate">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="Floating">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="FloatingRateIndex" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                             &lt;element name="FloatingRateIndexFreq" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                             &lt;element name="FloatingRateIndexRate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                             &lt;element name="FloatingRateIndexSpread" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="PurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="ProjectedInterest" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="RepurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="Optionality">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="TerminationOnDemand" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="NoticePeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="DeliveryMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Substitution">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Allowed" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="NumberAllowed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Agreement">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AgreementName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="AgreementIssuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="AgreementGoverningLaw" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="AgreementDate" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
         *                   &lt;element name="AgreementVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
         *                   &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="AgreementEffectiveDate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="AgreementUrl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="InitialMargin">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "workflowStepName",
            "transactionDate",
            "purchaseDate",
            "repurchaseDate",
            "tradeIdentifier",
            "tradeType",
            "seller",
            "buyer",
            "termType",
            "rateType",
            "dayCount",
            "collateralList",
            "loan",
            "repoRate",
            "purchasePrice",
            "projectedInterest",
            "repurchasePrice",
            "optionality",
            "deliveryMethod",
            "substitution",
            "agreement",
            "initialMargin"
        })
        public static class WorkflowStep {

            @XmlElement(name = "WorkflowStepName", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String workflowStepName;
            @XmlElement(name = "TransactionDate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar transactionDate;
            @XmlElement(name = "PurchaseDate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar purchaseDate;
            @XmlElement(name = "RepurchaseDate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar repurchaseDate;
            @XmlElement(name = "TradeIdentifier", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier tradeIdentifier;
            @XmlElement(name = "TradeType", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String tradeType;
            @XmlElement(name = "Seller", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller seller;
            @XmlElement(name = "Buyer", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer buyer;
            @XmlElement(name = "TermType", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String termType;
            @XmlElement(name = "RateType", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String rateType;
            @XmlElement(name = "DayCount", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String dayCount;
            @XmlElement(name = "CollateralList", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList collateralList;
            @XmlElement(name = "Loan", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan loan;
            @XmlElement(name = "RepoRate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate repoRate;
            @XmlElement(name = "PurchasePrice", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected BigDecimal purchasePrice;
            @XmlElement(name = "ProjectedInterest", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected BigDecimal projectedInterest;
            @XmlElement(name = "RepurchasePrice", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected BigDecimal repurchasePrice;
            @XmlElement(name = "Optionality", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality optionality;
            @XmlElement(name = "DeliveryMethod", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected String deliveryMethod;
            @XmlElement(name = "Substitution", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution substitution;
            @XmlElement(name = "Agreement", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement agreement;
            @XmlElement(name = "InitialMargin", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
            protected CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin initialMargin;

            /**
             * Gets the value of the workflowStepName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getWorkflowStepName() {
                return workflowStepName;
            }

            /**
             * Sets the value of the workflowStepName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setWorkflowStepName(String value) {
                this.workflowStepName = value;
            }

            /**
             * Gets the value of the transactionDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTransactionDate() {
                return transactionDate;
            }

            /**
             * Sets the value of the transactionDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTransactionDate(XMLGregorianCalendar value) {
                this.transactionDate = value;
            }

            /**
             * Gets the value of the purchaseDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getPurchaseDate() {
                return purchaseDate;
            }

            /**
             * Sets the value of the purchaseDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setPurchaseDate(XMLGregorianCalendar value) {
                this.purchaseDate = value;
            }

            /**
             * Gets the value of the repurchaseDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getRepurchaseDate() {
                return repurchaseDate;
            }

            /**
             * Sets the value of the repurchaseDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setRepurchaseDate(XMLGregorianCalendar value) {
                this.repurchaseDate = value;
            }

            /**
             * Gets the value of the tradeIdentifier property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier getTradeIdentifier() {
                return tradeIdentifier;
            }

            /**
             * Sets the value of the tradeIdentifier property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier }
             *     
             */
            public void setTradeIdentifier(CdmNewTradeWorkflow.Workflow.WorkflowStep.TradeIdentifier value) {
                this.tradeIdentifier = value;
            }

            /**
             * Gets the value of the tradeType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTradeType() {
                return tradeType;
            }

            /**
             * Sets the value of the tradeType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTradeType(String value) {
                this.tradeType = value;
            }

            /**
             * Gets the value of the seller property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller getSeller() {
                return seller;
            }

            /**
             * Sets the value of the seller property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller }
             *     
             */
            public void setSeller(CdmNewTradeWorkflow.Workflow.WorkflowStep.Seller value) {
                this.seller = value;
            }

            /**
             * Gets the value of the buyer property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer getBuyer() {
                return buyer;
            }

            /**
             * Sets the value of the buyer property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer }
             *     
             */
            public void setBuyer(CdmNewTradeWorkflow.Workflow.WorkflowStep.Buyer value) {
                this.buyer = value;
            }

            /**
             * Gets the value of the termType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTermType() {
                return termType;
            }

            /**
             * Sets the value of the termType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTermType(String value) {
                this.termType = value;
            }

            /**
             * Gets the value of the rateType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRateType() {
                return rateType;
            }

            /**
             * Sets the value of the rateType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRateType(String value) {
                this.rateType = value;
            }

            /**
             * Gets the value of the dayCount property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDayCount() {
                return dayCount;
            }

            /**
             * Sets the value of the dayCount property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDayCount(String value) {
                this.dayCount = value;
            }

            /**
             * Gets the value of the collateralList property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList getCollateralList() {
                return collateralList;
            }

            /**
             * Sets the value of the collateralList property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList }
             *     
             */
            public void setCollateralList(CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList value) {
                this.collateralList = value;
            }

            /**
             * Gets the value of the loan property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan getLoan() {
                return loan;
            }

            /**
             * Sets the value of the loan property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan }
             *     
             */
            public void setLoan(CdmNewTradeWorkflow.Workflow.WorkflowStep.Loan value) {
                this.loan = value;
            }

            /**
             * Gets the value of the repoRate property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate getRepoRate() {
                return repoRate;
            }

            /**
             * Sets the value of the repoRate property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate }
             *     
             */
            public void setRepoRate(CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate value) {
                this.repoRate = value;
            }

            /**
             * Gets the value of the purchasePrice property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPurchasePrice() {
                return purchasePrice;
            }

            /**
             * Sets the value of the purchasePrice property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPurchasePrice(BigDecimal value) {
                this.purchasePrice = value;
            }

            /**
             * Gets the value of the projectedInterest property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getProjectedInterest() {
                return projectedInterest;
            }

            /**
             * Sets the value of the projectedInterest property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setProjectedInterest(BigDecimal value) {
                this.projectedInterest = value;
            }

            /**
             * Gets the value of the repurchasePrice property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRepurchasePrice() {
                return repurchasePrice;
            }

            /**
             * Sets the value of the repurchasePrice property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRepurchasePrice(BigDecimal value) {
                this.repurchasePrice = value;
            }

            /**
             * Gets the value of the optionality property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality getOptionality() {
                return optionality;
            }

            /**
             * Sets the value of the optionality property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality }
             *     
             */
            public void setOptionality(CdmNewTradeWorkflow.Workflow.WorkflowStep.Optionality value) {
                this.optionality = value;
            }

            /**
             * Gets the value of the deliveryMethod property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDeliveryMethod() {
                return deliveryMethod;
            }

            /**
             * Sets the value of the deliveryMethod property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDeliveryMethod(String value) {
                this.deliveryMethod = value;
            }

            /**
             * Gets the value of the substitution property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution getSubstitution() {
                return substitution;
            }

            /**
             * Sets the value of the substitution property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution }
             *     
             */
            public void setSubstitution(CdmNewTradeWorkflow.Workflow.WorkflowStep.Substitution value) {
                this.substitution = value;
            }

            /**
             * Gets the value of the agreement property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement getAgreement() {
                return agreement;
            }

            /**
             * Sets the value of the agreement property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement }
             *     
             */
            public void setAgreement(CdmNewTradeWorkflow.Workflow.WorkflowStep.Agreement value) {
                this.agreement = value;
            }

            /**
             * Gets the value of the initialMargin property.
             * 
             * @return
             *     possible object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin }
             *     
             */
            public CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin getInitialMargin() {
                return initialMargin;
            }

            /**
             * Sets the value of the initialMargin property.
             * 
             * @param value
             *     allowed object is
             *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin }
             *     
             */
            public void setInitialMargin(CdmNewTradeWorkflow.Workflow.WorkflowStep.InitialMargin value) {
                this.initialMargin = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="AgreementName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AgreementIssuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AgreementGoverningLaw" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AgreementDate" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
             *         &lt;element name="AgreementVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
             *         &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="AgreementEffectiveDate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="AgreementUrl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "agreementName",
                "agreementIssuer",
                "agreementGoverningLaw",
                "agreementDate",
                "agreementVersion",
                "agreementIdentifier",
                "agreementEffectiveDate",
                "agreementUrl"
            })
            public static class Agreement {

                @XmlElement(name = "AgreementName", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String agreementName;
                @XmlElement(name = "AgreementIssuer", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String agreementIssuer;
                @XmlElement(name = "AgreementGoverningLaw", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String agreementGoverningLaw;
                @XmlElement(name = "AgreementDate", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                @XmlSchemaType(name = "unsignedShort")
                protected int agreementDate;
                @XmlElement(name = "AgreementVersion", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                @XmlSchemaType(name = "unsignedShort")
                protected int agreementVersion;
                @XmlElement(name = "AgreementIdentifier", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected Object agreementIdentifier;
                @XmlElement(name = "AgreementEffectiveDate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected Object agreementEffectiveDate;
                @XmlElement(name = "AgreementUrl", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected Object agreementUrl;

                /**
                 * Gets the value of the agreementName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAgreementName() {
                    return agreementName;
                }

                /**
                 * Sets the value of the agreementName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAgreementName(String value) {
                    this.agreementName = value;
                }

                /**
                 * Gets the value of the agreementIssuer property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAgreementIssuer() {
                    return agreementIssuer;
                }

                /**
                 * Sets the value of the agreementIssuer property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAgreementIssuer(String value) {
                    this.agreementIssuer = value;
                }

                /**
                 * Gets the value of the agreementGoverningLaw property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAgreementGoverningLaw() {
                    return agreementGoverningLaw;
                }

                /**
                 * Sets the value of the agreementGoverningLaw property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAgreementGoverningLaw(String value) {
                    this.agreementGoverningLaw = value;
                }

                /**
                 * Gets the value of the agreementDate property.
                 * 
                 */
                public int getAgreementDate() {
                    return agreementDate;
                }

                /**
                 * Sets the value of the agreementDate property.
                 * 
                 */
                public void setAgreementDate(int value) {
                    this.agreementDate = value;
                }

                /**
                 * Gets the value of the agreementVersion property.
                 * 
                 */
                public int getAgreementVersion() {
                    return agreementVersion;
                }

                /**
                 * Sets the value of the agreementVersion property.
                 * 
                 */
                public void setAgreementVersion(int value) {
                    this.agreementVersion = value;
                }

                /**
                 * Gets the value of the agreementIdentifier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getAgreementIdentifier() {
                    return agreementIdentifier;
                }

                /**
                 * Sets the value of the agreementIdentifier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setAgreementIdentifier(Object value) {
                    this.agreementIdentifier = value;
                }

                /**
                 * Gets the value of the agreementEffectiveDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getAgreementEffectiveDate() {
                    return agreementEffectiveDate;
                }

                /**
                 * Sets the value of the agreementEffectiveDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setAgreementEffectiveDate(Object value) {
                    this.agreementEffectiveDate = value;
                }

                /**
                 * Gets the value of the agreementUrl property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getAgreementUrl() {
                    return agreementUrl;
                }

                /**
                 * Sets the value of the agreementUrl property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setAgreementUrl(Object value) {
                    this.agreementUrl = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "leiName",
                "lei"
            })
            public static class Buyer {

                @XmlElement(name = "LEIName", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String leiName;
                @XmlElement(name = "LEI", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String lei;

                /**
                 * Gets the value of the leiName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLEIName() {
                    return leiName;
                }

                /**
                 * Sets the value of the leiName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLEIName(String value) {
                    this.leiName = value;
                }

                /**
                 * Gets the value of the lei property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLEI() {
                    return lei;
                }

                /**
                 * Sets the value of the lei property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLEI(String value) {
                    this.lei = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Collateral">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="ISIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="NominalPerUnitAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
             *                   &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="InterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="InterestPaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="NominalAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
             *                   &lt;element name="CleanPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="AccruedInterestPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="AccruedInterestAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="DirtyPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="UnAdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="Margin" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="AdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="Haircut" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
             *                   &lt;element name="SettlementAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "collateral"
            })
            public static class CollateralList {

                @XmlElement(name = "Collateral", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral collateral;

                /**
                 * Gets the value of the collateral property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral }
                 *     
                 */
                public CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral getCollateral() {
                    return collateral;
                }

                /**
                 * Sets the value of the collateral property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral }
                 *     
                 */
                public void setCollateral(CdmNewTradeWorkflow.Workflow.WorkflowStep.CollateralList.Collateral value) {
                    this.collateral = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="ISIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="NominalPerUnitAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
                 *         &lt;element name="DayCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="InterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="InterestPaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="NominalAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
                 *         &lt;element name="CleanPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="AccruedInterestPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="AccruedInterestAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="DirtyPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="UnAdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="Margin" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="AdjustedSettlementValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="Haircut" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
                 *         &lt;element name="SettlementAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "description",
                    "isin",
                    "currency",
                    "nominalPerUnitAmount",
                    "dayCount",
                    "interestRate",
                    "interestPaymentFrequency",
                    "nominalAmount",
                    "cleanPrice",
                    "accruedInterestPrice",
                    "accruedInterestAmount",
                    "dirtyPrice",
                    "unAdjustedSettlementValue",
                    "margin",
                    "adjustedSettlementValue",
                    "haircut",
                    "settlementAmount"
                })
                public static class Collateral {

                    @XmlElement(name = "Description", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected String description;
                    @XmlElement(name = "ISIN", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected String isin;
                    @XmlElement(name = "Currency", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected String currency;
                    @XmlElement(name = "NominalPerUnitAmount", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                    @XmlSchemaType(name = "unsignedShort")
                    protected int nominalPerUnitAmount;
                    @XmlElement(name = "DayCount", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected String dayCount;
                    @XmlElement(name = "InterestRate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal interestRate;
                    @XmlElement(name = "InterestPaymentFrequency", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected String interestPaymentFrequency;
                    @XmlElement(name = "NominalAmount", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                    @XmlSchemaType(name = "unsignedShort")
                    protected int nominalAmount;
                    @XmlElement(name = "CleanPrice", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal cleanPrice;
                    @XmlElement(name = "AccruedInterestPrice", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal accruedInterestPrice;
                    @XmlElement(name = "AccruedInterestAmount", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal accruedInterestAmount;
                    @XmlElement(name = "DirtyPrice", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal dirtyPrice;
                    @XmlElement(name = "UnAdjustedSettlementValue", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal unAdjustedSettlementValue;
                    @XmlElement(name = "Margin", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal margin;
                    @XmlElement(name = "AdjustedSettlementValue", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal adjustedSettlementValue;
                    @XmlElement(name = "Haircut", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                    @XmlSchemaType(name = "unsignedByte")
                    protected short haircut;
                    @XmlElement(name = "SettlementAmount", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal settlementAmount;

                    /**
                     * Gets the value of the description property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescription() {
                        return description;
                    }

                    /**
                     * Sets the value of the description property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescription(String value) {
                        this.description = value;
                    }

                    /**
                     * Gets the value of the isin property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getISIN() {
                        return isin;
                    }

                    /**
                     * Sets the value of the isin property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setISIN(String value) {
                        this.isin = value;
                    }

                    /**
                     * Gets the value of the currency property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCurrency() {
                        return currency;
                    }

                    /**
                     * Sets the value of the currency property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCurrency(String value) {
                        this.currency = value;
                    }

                    /**
                     * Gets the value of the nominalPerUnitAmount property.
                     * 
                     */
                    public int getNominalPerUnitAmount() {
                        return nominalPerUnitAmount;
                    }

                    /**
                     * Sets the value of the nominalPerUnitAmount property.
                     * 
                     */
                    public void setNominalPerUnitAmount(int value) {
                        this.nominalPerUnitAmount = value;
                    }

                    /**
                     * Gets the value of the dayCount property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDayCount() {
                        return dayCount;
                    }

                    /**
                     * Sets the value of the dayCount property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDayCount(String value) {
                        this.dayCount = value;
                    }

                    /**
                     * Gets the value of the interestRate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getInterestRate() {
                        return interestRate;
                    }

                    /**
                     * Sets the value of the interestRate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setInterestRate(BigDecimal value) {
                        this.interestRate = value;
                    }

                    /**
                     * Gets the value of the interestPaymentFrequency property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInterestPaymentFrequency() {
                        return interestPaymentFrequency;
                    }

                    /**
                     * Sets the value of the interestPaymentFrequency property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInterestPaymentFrequency(String value) {
                        this.interestPaymentFrequency = value;
                    }

                    /**
                     * Gets the value of the nominalAmount property.
                     * 
                     */
                    public int getNominalAmount() {
                        return nominalAmount;
                    }

                    /**
                     * Sets the value of the nominalAmount property.
                     * 
                     */
                    public void setNominalAmount(int value) {
                        this.nominalAmount = value;
                    }

                    /**
                     * Gets the value of the cleanPrice property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getCleanPrice() {
                        return cleanPrice;
                    }

                    /**
                     * Sets the value of the cleanPrice property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setCleanPrice(BigDecimal value) {
                        this.cleanPrice = value;
                    }

                    /**
                     * Gets the value of the accruedInterestPrice property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getAccruedInterestPrice() {
                        return accruedInterestPrice;
                    }

                    /**
                     * Sets the value of the accruedInterestPrice property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setAccruedInterestPrice(BigDecimal value) {
                        this.accruedInterestPrice = value;
                    }

                    /**
                     * Gets the value of the accruedInterestAmount property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getAccruedInterestAmount() {
                        return accruedInterestAmount;
                    }

                    /**
                     * Sets the value of the accruedInterestAmount property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setAccruedInterestAmount(BigDecimal value) {
                        this.accruedInterestAmount = value;
                    }

                    /**
                     * Gets the value of the dirtyPrice property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getDirtyPrice() {
                        return dirtyPrice;
                    }

                    /**
                     * Sets the value of the dirtyPrice property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setDirtyPrice(BigDecimal value) {
                        this.dirtyPrice = value;
                    }

                    /**
                     * Gets the value of the unAdjustedSettlementValue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getUnAdjustedSettlementValue() {
                        return unAdjustedSettlementValue;
                    }

                    /**
                     * Sets the value of the unAdjustedSettlementValue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setUnAdjustedSettlementValue(BigDecimal value) {
                        this.unAdjustedSettlementValue = value;
                    }

                    /**
                     * Gets the value of the margin property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getMargin() {
                        return margin;
                    }

                    /**
                     * Sets the value of the margin property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setMargin(BigDecimal value) {
                        this.margin = value;
                    }

                    /**
                     * Gets the value of the adjustedSettlementValue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getAdjustedSettlementValue() {
                        return adjustedSettlementValue;
                    }

                    /**
                     * Sets the value of the adjustedSettlementValue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setAdjustedSettlementValue(BigDecimal value) {
                        this.adjustedSettlementValue = value;
                    }

                    /**
                     * Gets the value of the haircut property.
                     * 
                     */
                    public short getHaircut() {
                        return haircut;
                    }

                    /**
                     * Sets the value of the haircut property.
                     * 
                     */
                    public void setHaircut(short value) {
                        this.haircut = value;
                    }

                    /**
                     * Gets the value of the settlementAmount property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSettlementAmount() {
                        return settlementAmount;
                    }

                    /**
                     * Sets the value of the settlementAmount property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSettlementAmount(BigDecimal value) {
                        this.settlementAmount = value;
                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class InitialMargin {

                @XmlElement(name = "Value", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected BigDecimal value;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setValue(BigDecimal value) {
                    this.value = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="LoanAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="LoanCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "loanAmount",
                "loanCurrency"
            })
            public static class Loan {

                @XmlElement(name = "LoanAmount", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected BigDecimal loanAmount;
                @XmlElement(name = "LoanCurrency", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String loanCurrency;

                /**
                 * Gets the value of the loanAmount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getLoanAmount() {
                    return loanAmount;
                }

                /**
                 * Sets the value of the loanAmount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setLoanAmount(BigDecimal value) {
                    this.loanAmount = value;
                }

                /**
                 * Gets the value of the loanCurrency property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLoanCurrency() {
                    return loanCurrency;
                }

                /**
                 * Sets the value of the loanCurrency property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLoanCurrency(String value) {
                    this.loanCurrency = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="TerminationOnDemand" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="NoticePeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "terminationOnDemand",
                "noticePeriod"
            })
            public static class Optionality {

                @XmlElement(name = "TerminationOnDemand", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String terminationOnDemand;
                @XmlElement(name = "NoticePeriod", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                @XmlSchemaType(name = "unsignedByte")
                protected short noticePeriod;

                /**
                 * Gets the value of the terminationOnDemand property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTerminationOnDemand() {
                    return terminationOnDemand;
                }

                /**
                 * Sets the value of the terminationOnDemand property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTerminationOnDemand(String value) {
                    this.terminationOnDemand = value;
                }

                /**
                 * Gets the value of the noticePeriod property.
                 * 
                 */
                public short getNoticePeriod() {
                    return noticePeriod;
                }

                /**
                 * Sets the value of the noticePeriod property.
                 * 
                 */
                public void setNoticePeriod(short value) {
                    this.noticePeriod = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="FixedRate">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Floating">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="FloatingRateIndex" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *                   &lt;element name="FloatingRateIndexFreq" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *                   &lt;element name="FloatingRateIndexRate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *                   &lt;element name="FloatingRateIndexSpread" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "fixedRate",
                "floating"
            })
            public static class RepoRate {

                @XmlElement(name = "FixedRate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate fixedRate;
                @XmlElement(name = "Floating", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating floating;

                /**
                 * Gets the value of the fixedRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate }
                 *     
                 */
                public CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate getFixedRate() {
                    return fixedRate;
                }

                /**
                 * Sets the value of the fixedRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate }
                 *     
                 */
                public void setFixedRate(CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.FixedRate value) {
                    this.fixedRate = value;
                }

                /**
                 * Gets the value of the floating property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating }
                 *     
                 */
                public CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating getFloating() {
                    return floating;
                }

                /**
                 * Sets the value of the floating property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating }
                 *     
                 */
                public void setFloating(CdmNewTradeWorkflow.Workflow.WorkflowStep.RepoRate.Floating value) {
                    this.floating = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class FixedRate {

                    @XmlElement(name = "Value", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected BigDecimal value;

                    /**
                     * Gets the value of the value property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValue(BigDecimal value) {
                        this.value = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="FloatingRateIndex" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
                 *         &lt;element name="FloatingRateIndexFreq" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
                 *         &lt;element name="FloatingRateIndexRate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
                 *         &lt;element name="FloatingRateIndexSpread" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "floatingRateIndex",
                    "floatingRateIndexFreq",
                    "floatingRateIndexRate",
                    "floatingRateIndexSpread"
                })
                public static class Floating {

                    @XmlElement(name = "FloatingRateIndex", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected Object floatingRateIndex;
                    @XmlElement(name = "FloatingRateIndexFreq", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected Object floatingRateIndexFreq;
                    @XmlElement(name = "FloatingRateIndexRate", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected Object floatingRateIndexRate;
                    @XmlElement(name = "FloatingRateIndexSpread", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                    protected Object floatingRateIndexSpread;

                    /**
                     * Gets the value of the floatingRateIndex property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Object }
                     *     
                     */
                    public Object getFloatingRateIndex() {
                        return floatingRateIndex;
                    }

                    /**
                     * Sets the value of the floatingRateIndex property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Object }
                     *     
                     */
                    public void setFloatingRateIndex(Object value) {
                        this.floatingRateIndex = value;
                    }

                    /**
                     * Gets the value of the floatingRateIndexFreq property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Object }
                     *     
                     */
                    public Object getFloatingRateIndexFreq() {
                        return floatingRateIndexFreq;
                    }

                    /**
                     * Sets the value of the floatingRateIndexFreq property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Object }
                     *     
                     */
                    public void setFloatingRateIndexFreq(Object value) {
                        this.floatingRateIndexFreq = value;
                    }

                    /**
                     * Gets the value of the floatingRateIndexRate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Object }
                     *     
                     */
                    public Object getFloatingRateIndexRate() {
                        return floatingRateIndexRate;
                    }

                    /**
                     * Sets the value of the floatingRateIndexRate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Object }
                     *     
                     */
                    public void setFloatingRateIndexRate(Object value) {
                        this.floatingRateIndexRate = value;
                    }

                    /**
                     * Gets the value of the floatingRateIndexSpread property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Object }
                     *     
                     */
                    public Object getFloatingRateIndexSpread() {
                        return floatingRateIndexSpread;
                    }

                    /**
                     * Sets the value of the floatingRateIndexSpread property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Object }
                     *     
                     */
                    public void setFloatingRateIndexSpread(Object value) {
                        this.floatingRateIndexSpread = value;
                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="LEIName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="LEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "leiName",
                "lei"
            })
            public static class Seller {

                @XmlElement(name = "LEIName", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String leiName;
                @XmlElement(name = "LEI", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String lei;

                /**
                 * Gets the value of the leiName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLEIName() {
                    return leiName;
                }

                /**
                 * Sets the value of the leiName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLEIName(String value) {
                    this.leiName = value;
                }

                /**
                 * Gets the value of the lei property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLEI() {
                    return lei;
                }

                /**
                 * Sets the value of the lei property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLEI(String value) {
                    this.lei = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Allowed" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="NumberAllowed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "allowed",
                "numberAllowed"
            })
            public static class Substitution {

                @XmlElement(name = "Allowed", namespace = "urn:icma:xsd:ICMARepoNewTrade", required = true)
                protected String allowed;
                @XmlElement(name = "NumberAllowed", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                @XmlSchemaType(name = "unsignedByte")
                protected short numberAllowed;

                /**
                 * Gets the value of the allowed property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAllowed() {
                    return allowed;
                }

                /**
                 * Sets the value of the allowed property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAllowed(String value) {
                    this.allowed = value;
                }

                /**
                 * Gets the value of the numberAllowed property.
                 * 
                 */
                public short getNumberAllowed() {
                    return numberAllowed;
                }

                /**
                 * Sets the value of the numberAllowed property.
                 * 
                 */
                public void setNumberAllowed(short value) {
                    this.numberAllowed = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="UTI" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "uti"
            })
            public static class TradeIdentifier {

                @XmlElement(name = "UTI", namespace = "urn:icma:xsd:ICMARepoNewTrade")
                @XmlSchemaType(name = "unsignedShort")
                protected int uti;

                /**
                 * Gets the value of the uti property.
                 * 
                 */
                public int getUTI() {
                    return uti;
                }

                /**
                 * Sets the value of the uti property.
                 * 
                 */
                public void setUTI(int value) {
                    this.uti = value;
                }

            }

        }

    }

}
