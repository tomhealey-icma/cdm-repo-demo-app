package com.finxis.cdm.crossproductapp.ui;


import cdm.event.common.Trade;
import com.finxis.cdm.crossproductapp.*;
import com.finxis.cdm.crossproductapp.util.ValidateDoubleTextField;
import com.finxis.cdm.crossproductapp.util.ValidateIntegerTextField;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.SocketChannel;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.parsers.ParserConfigurationException;

import cdm.base.datetime.*;
import cdm.base.math.QuantifierEnum;
import cdm.base.math.UnitType;
import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.identifier.Identifier;
import cdm.base.staticdata.party.*;
import cdm.base.staticdata.asset.common.*;
import cdm.event.common.*;

import cdm.product.asset.*;
import cdm.product.collateral.*;
import cdm.product.common.schedule.RateSchedule;
import cdm.observable.asset.*;
import cdm.observable.asset.metafields.ReferenceWithMetaPriceSchedule;
import cdm.product.template.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.finxis.cdm.crossproductapp.util.*;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;

@SuppressWarnings("unchecked")
public class TradeEntryPanel extends JPanel{

    public static CdmTradingDemo trainingApp;

    public final JComboBox symbolComboBox = new JComboBox(TradeSymbol.toArray());
    public final ValidateIntegerTextField quantityTextField = new ValidateIntegerTextField();

    public final JComboBox sideComboBox = new JComboBox(TradeSide.toArray());

    public final ValidateDoubleTextField priceTextField = new ValidateDoubleTextField();

    public final JComboBox sessionComboBox = new JComboBox();

    public final JLabel messageLabel = new JLabel(" ");
    public final JButton submitButton = new JButton("Submit");

    public TradeTableModel tradeTableModel = null;

    public TradeEntryModel tradeEntryModel = null;
    public transient CdmTradingDemoApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();

    public final GridBagConstraints constraints2 = new GridBagConstraints();

    public JPanel repoTradePanel;

    public JPanel fxTradePanel;

    public JPanel swapTradePanel;

    public JPanel repoTradePanelCol1;
    public JPanel repoTradePanelCol2;
    public JPanel repoTradePanelCol3;

    public JPanel fxTradePanelCol1;
    public JPanel fxTradePanelCol2;
    public JPanel fxTradePanelCol3;

    public JPanel swapTradePanelCol1;
    public JPanel swapTradePanelCol2;
    public JPanel swapTradePanelCol3;

    //Defaults
    public String defaultLocalTimeZone = "UTC";

    public String tradeStateStr = null;
    public String beforeTradeStateStr = null;
    public String afterTradeStateStr = null;


    public List<? extends TradeState> afterTradeStateList = null;
    public List<String> businessEventList;

    public ZonedDateTime TDzonedDateTime;

    public ZonedDateTime LDzonedDateTime;
    public String TDformattedDateTimeString;
    public ZonedDateTime PDzonedDateTime;
    public String PDformattedDateTimeString;
    public ZonedDateTime RDzonedDateTime;
    public String RDformattedDateTimeString;

    public String ETformattedDateTimeString;
    public DateTimeFormatter formatter;

    public ZonedDateTime ETzonedDateTime;
    public DateTimeFormatter ETformatter;

    public DateTimeFormatter eventFileformatter;
    public JComboBox transactionTypeField;

    public JComboBox tradeDirectionField;
    public JTextField tradeDateField;
    public JTextField purchaseDateField;
    public JTextField repurchaseDateField;
    public JTextField tradeUTIField;

    public JComboBox firmLEIField;
    public JComboBox buyerLEIField;
    public JComboBox sellerLEIField;
    public JComboBox collateralTypeField;
    public JTextField collateralDescriptionField;
    public JTextField collateralISINField;
    public JTextField collateralQuantityField;
    public JTextField collateralCleanPriceField;
    public JTextField collateralDirtyPriceField;
    public JTextField collateralAdjustedValueField;
    public JTextField collateralCurrencyField;
    public JTextField repoRateField;
    public JTextField cashCurrencyField;
    public JTextField cashQuantityField;
    public JTextField haircutField;
    public JComboBox termTypeField;
    public JComboBox terminationOptionField;
    public JTextField noticePeriodField;
    public JComboBox deliveryMethodField;
    public JComboBox substitutionAllowedField;
    public JComboBox rateTypeField;
    public JTextField dayCountFractionField;
    public JTextField termDaysField;
    public JTextField purchasePriceField;
    public JTextField repurchasePriceField;
    public JPanel actionPanel;

    public JTextField floatingRateReferenceField;
    public JComboBox floatingRateReferencePeriodField;
    public JTextField floatingRateReferenceMultiplierField;
    public JComboBox floatingRatePaymentFreqField;
    public JTextField floatingRatePaymentMultiplierField;
    public JComboBox floatingRateResetFreqField;
    public JTextField floatingRateResetMultiplierField;
    public JTextField floatingRateField;
    public JTextField floatingRateSpreadField;

    public JComboBox tradingBookOptionField;
    public JComboBox businessCenterOptionField;
    public JComboBox timeZoneOptionField;
    public JTextField executionTimeField;

    public JComboBox venueCodeOptionField;

    public JTextField traderNameField;
    public JTextField traderLocationField;
    public JTextField traderLocalDateField;
    public JTextField traderLocalTimeField;
    public JComboBox brokerField;
    public JComboBox tripartyField;
    public JComboBox beneficiaryField;

    public JComboBox settlementAgentOptionField;

    public JComboBox clearingMemberField;

    public JComboBox agentLenderField;
    public JComboBox centralClearingCounterpartyOptionField;
    public JComboBox csdOptionField;
    public JTextField firmNameField;
    public JTextField firmLeiField;
    public JTextField firmCapacityField;

    public JComboBox agreementNameField;

    public JTextField agreementVersionField;

    public JTextField otherAgreementNameField;

    public JComboBox reportingEntityOptionField;

    public JComboBox reportingSideField;

    public JTextField tradeIdField;
    public JTextField statusField;

    public JComboBox collateralMaturityTypeField;

    public JComboBox issuerTypeField;

    public JComboBox issuerCountryField;


    public JComboBox ownIssuePermittedField;


    public JComboBox issuerCountryOfOriginField;

    public JComboBox issuerNameField;

    public JComboBox issuerAgencyRatingField;

    public JComboBox sovereignAgencyRatingField;

    public JComboBox counterpartyOwnIssuePermittedField;

    public JComboBox collateralAssetTypeField;

    public JComboBox debtTypeField;

    public JComboBox debtInterestTypeField;

    public JComboBox debtSeniorityField;

    public JComboBox debtPrincipalField;

    public JComboBox collateralCriteriaCurrencyField;

    public JComboBox collateralCriteriaAgencyRatingField;

    public JComboBox collateralMinMaturityRangeField;

    public JComboBox collateralMaxMaturityRangeField;

    public JComboBox assetCountryOfOriginField;

    public JComboBox denominatedCurrencyField;

    public JComboBox maturityRangeField;

    public JComboBox productIdentifierField;

    public JComboBox collateralTaxonomyField;

    public JComboBox domesticCurrencyIssuedField;

    public JComboBox listingTypeField;

    public Map<String, String> cdmMap = new HashMap<>();

    public Integer labelWidth;
    public Integer labelHeight;

    public Integer columnWidth;

    public Integer fieldWidth;
    public Integer fieldHeight;



    public TradeEntryPanel(final TradeEntryModel tradeEntryModel,
                           final CdmTradingDemoApplication application) {
        setName("TradeEntryPanel");
        this.tradeEntryModel = tradeEntryModel;
        this.application = application;


        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setLayout(new GridBagLayout());
        createComponents();
    }


    private void createComponents() {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill= GridBagConstraints.HORIZONTAL;
        gc.weightx=1.0;
        gc.weighty=1.0;

        labelWidth = 150;
        labelHeight = 15;
        fieldWidth = 170;
        fieldHeight = 20;
        columnWidth = 15;



        repoTradePanel = new JPanel();
        repoTradePanel.setAlignmentY(1.0f);
        repoTradePanelCol1 = new JPanel();
        repoTradePanelCol1.setLayout(new BoxLayout(repoTradePanelCol1, BoxLayout.Y_AXIS));
        repoTradePanelCol1.setAlignmentY(1.0f);
        repoTradePanelCol2 = new JPanel();
        repoTradePanelCol2.setLayout(new BoxLayout(repoTradePanelCol2, BoxLayout.Y_AXIS));
        repoTradePanelCol2.setAlignmentY(1.0f);
        repoTradePanelCol3 = new JPanel();
        repoTradePanelCol3.setLayout(new BoxLayout(repoTradePanelCol3, BoxLayout.Y_AXIS));



        //Border redline = BorderFactory.createLineBorder(Color.red);
        Border blueline = BorderFactory.createLineBorder(Color.blue);
        //repoTradePanelCol1.setBorder(blueline);
        //repoTradePanelCol2.setBorder(blueline);
        //repoTradePanelCol3.setBorder(blueline);
        repoTradePanel.setBorder(blueline);
        repoTradePanel.add(repoTradePanelCol1,constraints);
        repoTradePanel.add(repoTradePanelCol2,constraints);
        //repoTradePanel.add(repoTradePanelCol3);



        IcmaRepoUtil ru = new IcmaRepoUtil();

        //Firm
        JPanel firmLEIPanel = new JPanel(new GridBagLayout());
        JLabel firmLEILabel = new JLabel("Firm", JLabel.LEFT);
        firmLEILabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        firmLEIField = new JComboBox();
        ru.addParties(firmLEIField);

        firmLEIField.setAlignmentX(Component.LEFT_ALIGNMENT);
        firmLEIField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        firmLEIPanel.add(firmLEILabel);
        firmLEIPanel.add(firmLEIField,gc);
        repoTradePanelCol1.add(firmLEIPanel);
        firmLEIField.setSelectedIndex(1);

        //Type
        String[] typechoices = {"Repurchase Agreement", "Buy/Sell-back Agreement"};
        JPanel transactionTypePanel = new JPanel(new GridBagLayout());
        JLabel transactionTypeLabel = new JLabel("Type", JLabel.LEFT);
        transactionTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        transactionTypeField = new JComboBox<String>(typechoices);
        transactionTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        transactionTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        transactionTypePanel.add(transactionTypeLabel);
        transactionTypePanel.add(transactionTypeField,gc);
        repoTradePanelCol1.add(transactionTypePanel);

        //Direction
        String[] directionchoices = {"Buy (Reverse Repo)", "Sell (Repo)"};
        JPanel tradeDirectionPanel = new JPanel(new GridBagLayout());
        JLabel tradeDirectionLabel = new JLabel("Direction (B/S)", JLabel.LEFT);
        tradeDirectionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        tradeDirectionField = new JComboBox<String>(directionchoices);
        tradeDirectionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        tradeDirectionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        tradeDirectionPanel.add(tradeDirectionLabel);
        tradeDirectionPanel.add(tradeDirectionField,gc);
        repoTradePanelCol1.add(tradeDirectionPanel);


        //Trade Date

        JPanel tradeDatePanel = new JPanel(new GridBagLayout());
        JLabel tradeDateLabel = new JLabel("Trade Date", JLabel.LEFT);
        tradeDateLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        tradeDateField = new JTextField(labelHeight);
        LocalDateTime localDateTime = LocalDateTime.now();
        TDzonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(this.defaultLocalTimeZone));
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TDformattedDateTimeString = TDzonedDateTime.format(formatter);

        tradeDateField.setText(TDformattedDateTimeString);
        tradeDatePanel.add(tradeDateLabel);
        tradeDatePanel.add(tradeDateField,gc);
        repoTradePanelCol1.add(tradeDatePanel);


        //Purchase Date
        JPanel purchaseDatePanel = new JPanel(new GridBagLayout());
        JLabel purchaseDateLabel = new JLabel("Purchase Date", JLabel.LEFT);
        purchaseDateLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        purchaseDateField = new JTextField(labelHeight);

        PDzonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(this.defaultLocalTimeZone));
        PDformattedDateTimeString = PDzonedDateTime.format(formatter);
        purchaseDateField.setText(PDformattedDateTimeString);
        purchaseDatePanel.add(purchaseDateLabel);
        purchaseDatePanel.add(purchaseDateField,gc);
        repoTradePanelCol1.add(purchaseDatePanel);


        //Repurchase Date
        JPanel repurchaseDatePanel = new JPanel(new GridBagLayout());
        JLabel repurchaseDateLabel = new JLabel("Repurchase Date", JLabel.LEFT);
        repurchaseDateLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        repurchaseDateField = new JTextField(labelHeight);

        RDzonedDateTime = TDzonedDateTime.plusDays(1);
        RDformattedDateTimeString = RDzonedDateTime.format(formatter);

        repurchaseDateField.setText(RDformattedDateTimeString);
        repurchaseDatePanel.add(repurchaseDateLabel);
        repurchaseDatePanel.add(repurchaseDateField,gc);
        repoTradePanelCol1.add(repurchaseDatePanel);

        repurchaseDateField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        repurchaseDateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });

        //UTI
        JPanel tradeUTIPanel = new JPanel(new GridBagLayout());
        JLabel tradeUTILabel = new JLabel("Trade UTI", JLabel.LEFT);
        tradeUTILabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        tradeUTIField = new JTextField(labelHeight);
        String tdt = this.tradeDateField.getText();
        String tradeUTIStr="";

        tradeUTIPanel.add(tradeUTILabel);
        tradeUTIPanel.add(tradeUTIField,gc);
        repoTradePanelCol1.add(tradeUTIPanel);

        //Buyer
        JPanel buyerLEIPanel = new JPanel(new GridBagLayout());
        JLabel buyerLEILabel = new JLabel("Buyer", JLabel.LEFT);
        buyerLEILabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        buyerLEIField = new JComboBox();
        ru.addParties(buyerLEIField);

        buyerLEIField.setAlignmentX(Component.LEFT_ALIGNMENT);
        buyerLEIField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        buyerLEIPanel.add(buyerLEILabel);
        buyerLEIPanel.add(buyerLEIField,gc);
        repoTradePanelCol1.add(buyerLEIPanel);

        //Seller
        JPanel sellerLEIPanel = new JPanel(new GridBagLayout());
        JLabel sellerLEILabel = new JLabel("Seller", JLabel.LEFT);
        sellerLEILabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        sellerLEIField = new JComboBox();
        ru.addParties(sellerLEIField);

        sellerLEIField.setAlignmentX(Component.LEFT_ALIGNMENT);
        sellerLEIField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        sellerLEIPanel.add(sellerLEILabel);
        sellerLEIPanel.add(sellerLEIField,gc);
        repoTradePanelCol1.add(sellerLEIPanel);

        //Collateral Type

        String[] collateralChoices = {"Special", "General Collateral"};
        JPanel collateralTypePanel = new JPanel(new GridBagLayout());
        JLabel collateralTypeLabel = new JLabel("Collateral Type", JLabel.LEFT);
        collateralTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        collateralTypeField = new JComboBox<String>(collateralChoices);
        collateralTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralTypePanel.add(collateralTypeLabel);
        collateralTypePanel.add(collateralTypeField,gc);

        collateralTypeField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        collateralTypeFieldEvent(collateralTypeField.getSelectedItem().toString());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        repoTradePanelCol1.add(collateralTypePanel);

        //Collateral
        JPanel collateralDescriptionPanel = new JPanel(new GridBagLayout());
        JLabel collateralDescriptionLabel = new JLabel("Security", JLabel.LEFT);
        collateralDescriptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralDescriptionField = new JTextField(labelHeight);
        collateralDescriptionField.setText("GILT .5 22/07/2022");
        collateralDescriptionPanel.add(collateralDescriptionLabel);
        collateralDescriptionPanel.add(collateralDescriptionField,gc);
        repoTradePanelCol1.add(collateralDescriptionPanel);

        JPanel collateralISINPanel = new JPanel(new GridBagLayout());
        JLabel collateralISINLabel = new JLabel("Collateral ISIN", JLabel.LEFT);
        collateralISINLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralISINField = new JTextField(labelHeight);
        collateralISINField.setText("GB00BD0PCK97");
        collateralISINPanel.add(collateralISINLabel);
        collateralISINPanel.add(collateralISINField,gc);
        repoTradePanelCol1.add(collateralISINPanel);

        JPanel collateralQuantityPanel = new JPanel(new GridBagLayout());
        JLabel collateralQuantityLabel = new JLabel("Quantity", JLabel.LEFT);
        collateralQuantityLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralQuantityField = new JTextField(labelHeight);
        collateralQuantityField.setText("1000");
        collateralQuantityPanel.add(collateralQuantityLabel);
        collateralQuantityPanel.add(collateralQuantityField,gc);
        repoTradePanelCol1.add(collateralQuantityPanel);

        collateralQuantityField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        collateralQuantityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });


        JPanel collateralCleanPricePanel = new JPanel(new GridBagLayout());
        JLabel collateralCleanPriceLabel = new JLabel("Clean Price", JLabel.LEFT);
        collateralCleanPriceLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralCleanPriceField = new JTextField(labelHeight);
        collateralCleanPriceField.setText("100.75");
        collateralCleanPricePanel.add(collateralCleanPriceLabel);
        collateralCleanPricePanel.add(collateralCleanPriceField,gc);
        repoTradePanelCol1.add(collateralCleanPricePanel);

        JPanel collateralDirtyPricePanel = new JPanel(new GridBagLayout());
        JLabel collateralDirtyPriceLabel = new JLabel("Dirty Price", JLabel.LEFT);
        collateralDirtyPriceLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralDirtyPriceField = new JTextField(labelHeight);
        collateralDirtyPriceField.setText("100.8066");
        collateralDirtyPricePanel.add(collateralDirtyPriceLabel);
        collateralDirtyPricePanel.add(collateralDirtyPriceField,gc);
        repoTradePanelCol1.add(collateralDirtyPricePanel);


        collateralDirtyPriceField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        collateralDirtyPriceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });

        JPanel collateralAdjustedValuePanel = new JPanel(new GridBagLayout());
        JLabel collateralAdjustedValueLabel = new JLabel("Adjusted Value", JLabel.LEFT);
        collateralAdjustedValueLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralAdjustedValueField = new JTextField(labelHeight);
        collateralAdjustedValueField.setText("1008066.00");
        collateralAdjustedValuePanel.add(collateralAdjustedValueLabel);
        collateralAdjustedValuePanel.add(collateralAdjustedValueField,gc);
        repoTradePanelCol1.add(collateralAdjustedValuePanel);

        JPanel collateralCurrencyPanel = new JPanel(new GridBagLayout());
        JLabel collateralCurrencyLabel = new JLabel("Currency", JLabel.LEFT);
        collateralCurrencyLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        collateralCurrencyField = new JTextField(labelHeight);
        collateralCurrencyField.setText("GBP");
        collateralCurrencyPanel.add(collateralCurrencyLabel);
        collateralCurrencyPanel.add(collateralCurrencyField,gc);
        repoTradePanelCol1.add(collateralCurrencyPanel);


        //Repo Rate Type
        String[] choices = {"FIXED", "FLOAT"};
        JPanel rateTypePanel = new JPanel(new GridBagLayout());
        JLabel rateTypeLabel = new JLabel("Rate Type", JLabel.LEFT);
        rateTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        rateTypeField = new JComboBox<String>(choices);
        rateTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rateTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        rateTypePanel.add(rateTypeLabel);
        rateTypePanel.add(rateTypeField,gc);

        rateTypeField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rateTypeFieldEvent(rateTypeField.getSelectedItem().toString());
                }
            }
        });

        repoTradePanelCol1.add(rateTypePanel);

        //Repo Rate
        JPanel repoRatePanel = new JPanel(new GridBagLayout());
        JLabel repoRateLabel = new JLabel("Repo Rate", JLabel.LEFT);
        repoRateLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        repoRateField = new JTextField(labelHeight);
        repoRateField.setText("4.65");
        repoRatePanel.add(repoRateLabel);
        repoRatePanel.add(repoRateField,gc);
        repoTradePanelCol1.add(repoRatePanel);

        repoRateField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        repoRateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });


        JPanel cashCurrencyPanel = new JPanel(new GridBagLayout());
        JLabel cashCurrencyLabel = new JLabel("Loan Currency", JLabel.LEFT);
        cashCurrencyLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        cashCurrencyField = new JTextField(labelHeight);
        cashCurrencyField.setText("GBP");
        cashCurrencyPanel.add(cashCurrencyLabel);
        cashCurrencyPanel.add(cashCurrencyField,gc);
        repoTradePanelCol1.add(cashCurrencyPanel);


        JPanel haircutPanel = new JPanel(new GridBagLayout());
        JLabel haircutLabel = new JLabel("Haircut %", JLabel.LEFT);
        haircutLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        haircutField = new JTextField(labelHeight);
        haircutField.setText("2");
        haircutPanel.add(haircutLabel);
        haircutPanel.add(haircutField,gc);
        repoTradePanelCol1.add(haircutPanel);

        haircutField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        haircutField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });


        //Term Type

        JPanel termTypePanel = new JPanel(new GridBagLayout());
        JLabel termTypeLabel = new JLabel("Term Type", JLabel.LEFT);
        termTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        String[] termTypeChoices = {"FIXED", "OPEN"};
        termTypeField = new JComboBox<String>(termTypeChoices);
        termTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        termTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        termTypeField.setSelectedItem("FIXED");
        termTypePanel.add(termTypeLabel);
        termTypePanel.add(termTypeField,gc);
        repoTradePanelCol1.add(termTypePanel);


        termTypeField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        termTypeField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    termTypeFieldEvent(termTypeField.getSelectedItem().toString());
                }
            }
        });


        //Termination Option
        String[] terminationChoices = {"", "Early Termination", "Evergreen", "Extendible"};
        JPanel terminationOptionPanel = new JPanel(new GridBagLayout());
        JLabel terminationOptionLabel = new JLabel("Termination Option", JLabel.LEFT);
        terminationOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        terminationOptionField = new JComboBox<String>(terminationChoices);

        terminationOptionField = new JComboBox<String>(terminationChoices);
        terminationOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        terminationOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        terminationOptionPanel.add(terminationOptionLabel);
        terminationOptionPanel.add(terminationOptionField,gc);
        repoTradePanelCol1.add(terminationOptionPanel);


        JPanel noticePeriodPanel = new JPanel(new GridBagLayout());
        JLabel noticePeriodLabel = new JLabel("Notice Period", JLabel.LEFT);
        noticePeriodLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        noticePeriodField = new JTextField(labelHeight);
        noticePeriodField.setText("0");
        noticePeriodPanel.add(noticePeriodLabel);
        noticePeriodPanel.add(noticePeriodField,gc);
        repoTradePanelCol1.add(noticePeriodPanel);

        //Delivery Method
        String[] deliveryMethodChoices = {"DVP", "TP"};
        JPanel deliveryMethodPanel = new JPanel(new GridBagLayout());
        JLabel deliveryMethodLabel = new JLabel("Delivery Method", JLabel.LEFT);
        deliveryMethodLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        deliveryMethodField = new JComboBox<String>(deliveryMethodChoices);
        deliveryMethodField.setAlignmentX(Component.LEFT_ALIGNMENT);
        deliveryMethodField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        deliveryMethodPanel.add(deliveryMethodLabel);
        deliveryMethodPanel.add(deliveryMethodField,gc);
        repoTradePanelCol1.add(deliveryMethodPanel);

        //Substitution
        String[] substitutionChoices = {"N", "Y"};
        JPanel substitutionAllowedPanel = new JPanel(new GridBagLayout());
        JLabel substitutionAllowedLabel = new JLabel("Substitution", JLabel.LEFT);
        substitutionAllowedLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        substitutionAllowedField = new JComboBox<String>(substitutionChoices);
        substitutionAllowedField.setAlignmentX(Component.LEFT_ALIGNMENT);
        substitutionAllowedField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        substitutionAllowedPanel.add(substitutionAllowedLabel);
        substitutionAllowedPanel.add(substitutionAllowedField,gc);
        repoTradePanelCol1.add(substitutionAllowedPanel);


        //Day Count
        JPanel dayCountFractionPanel = new JPanel(new GridBagLayout());
        JLabel dayCountFractionLabel = new JLabel("Day Count", JLabel.LEFT);
        dayCountFractionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        dayCountFractionField = new JTextField(labelHeight);
        dayCountFractionField.setText("ACT/360");
        dayCountFractionPanel.add(dayCountFractionLabel);
        dayCountFractionPanel.add(dayCountFractionField,gc);
        repoTradePanelCol1.add(dayCountFractionPanel);

        //Day Count
        JPanel termDaysPanel = new JPanel(new GridBagLayout());
        JLabel termDaysLabel = new JLabel("Term Days", JLabel.LEFT);
        termDaysLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        termDaysField = new JTextField(labelHeight);
        termDaysField.setText("1");
        termDaysPanel.add(termDaysLabel);
        termDaysPanel.add(termDaysField,gc);
        repoTradePanelCol1.add(termDaysPanel);

        long daysBetween = Duration.between(PDzonedDateTime, RDzonedDateTime).toDays();
        DecimalFormat intFormat = new DecimalFormat("###");
        String termsDaysStr = intFormat.format(daysBetween);
        termDaysField.setText(termsDaysStr);

        //Purchase Price
        JPanel purchasePricePanel = new JPanel(new GridBagLayout());
        JLabel purchasePriceLabel = new JLabel("Purchase Price", JLabel.LEFT);
        purchasePriceLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        purchasePriceField = new JTextField(labelHeight);

        Double cv = Double.valueOf(collateralAdjustedValueField.getText());
        Double hc = Double.valueOf(haircutField.getText());
        Double pp = cv * (1 - hc / 100);

        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String purchasePriceStr = formatter.format(pp);
        purchasePriceField.setText(purchasePriceStr);
        purchasePricePanel.add(purchasePriceLabel);
        purchasePricePanel.add(purchasePriceField,gc);
        repoTradePanelCol1.add(purchasePricePanel);

        //Repurchase Price
        JPanel repurchasePricePanel = new JPanel(new GridBagLayout());
        JLabel repurchasePriceLabel = new JLabel("Repurchase Price", JLabel.LEFT);
        repurchasePriceLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        repurchasePriceField = new JTextField(labelHeight);

        Double rr = Double.valueOf(repoRateField.getText());
        Double rp = pp + (pp * (1.00 / 360.00 * rr / 100.00));
        String repurchasePriceStr = formatter.format(rp);
        repurchasePriceField.setText(repurchasePriceStr);

        repurchasePricePanel.add(repurchasePriceLabel);
        repurchasePricePanel.add(repurchasePriceField,gc);
        repoTradePanelCol1.add(repurchasePricePanel);



        //Panel 2

        //Trading Book
        String[] tradingBookChoices = {"UKBOOK", "USBOOK"};
        JPanel tradingBookOptionPanel = new JPanel(new GridBagLayout());
        JLabel tradingBookOptionLabel = new JLabel("Trading Book", JLabel.LEFT);
        tradingBookOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        tradingBookOptionField = new JComboBox<String>(tradingBookChoices);
        tradingBookOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        tradingBookOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        tradingBookOptionPanel.add(tradingBookOptionLabel);
        tradingBookOptionPanel.add(tradingBookOptionField,gc);
        repoTradePanelCol2.add(tradingBookOptionPanel);


        //Business Center Option
        String[] businessCenterChoices = {"GBLO", "NYFD"};
        JPanel businessCenterOptionPanel = new JPanel(new GridBagLayout());
        JLabel businessCenterOptionLabel = new JLabel("Business Center", JLabel.LEFT);
        businessCenterOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        businessCenterOptionField = new JComboBox<String>(businessCenterChoices);
        businessCenterOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        businessCenterOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        businessCenterOptionPanel.add(businessCenterOptionLabel);
        businessCenterOptionPanel.add(businessCenterOptionField,gc);
        repoTradePanelCol2.add(businessCenterOptionPanel);

        //TimeZone
        String[] timeZoneChoices = {"UTC"};
        JPanel timeZoneOptionPanel = new JPanel(new GridBagLayout());
        JLabel timeZoneOptionLabel = new JLabel("Time Zone", JLabel.LEFT);
        timeZoneOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        timeZoneOptionField = new JComboBox<String>(timeZoneChoices);
        timeZoneOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeZoneOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        timeZoneOptionPanel.add(timeZoneOptionLabel);
        timeZoneOptionPanel.add(timeZoneOptionField,gc);
        repoTradePanelCol2.add(timeZoneOptionPanel);

        //Execution Time
        JPanel executionTimePanel = new JPanel(new GridBagLayout());
        JLabel executionTimeLabel = new JLabel("Execution Time", JLabel.LEFT);
        executionTimeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        executionTimeField = new JTextField(labelHeight);

        DateTimeFormatter ETformatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        ETzonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(this.defaultLocalTimeZone));
        ETformattedDateTimeString = ETzonedDateTime.format(ETformatter);
        executionTimeField.setText(ETformattedDateTimeString);
        executionTimePanel.add(executionTimeLabel);
        executionTimePanel.add(executionTimeField,gc);
        repoTradePanelCol2.add(executionTimePanel);

        //Set the UTI now that time is set
        tradeUTIStr = "ICMA"+ tdt.trim().replaceAll("-", "") + executionTimeField.getText().trim().replaceAll("\\s", "");
        tradeUTIField.setText(tradeUTIStr);

        //Venue
        String[] venueCodeChoices = {"OTC", "FINX"};
        JPanel venueCodeOptionPanel = new JPanel(new GridBagLayout());
        JLabel venueCodeOptionLabel = new JLabel("Execution Venue", JLabel.LEFT);
        venueCodeOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        venueCodeOptionField = new JComboBox<String>(venueCodeChoices);
        venueCodeOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        venueCodeOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        venueCodeOptionPanel.add(venueCodeOptionLabel);
        venueCodeOptionPanel.add(venueCodeOptionField,gc);
        repoTradePanelCol2.add(venueCodeOptionPanel);

        //Settlement Agent
        JPanel settlementAgentOptionPanel = new JPanel(new GridBagLayout());
        JLabel settlementAgentOptionLabel = new JLabel("Settlement Agent", JLabel.LEFT);
        settlementAgentOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        settlementAgentOptionField = new JComboBox();
        //settlementAgentOptionField.addItem(new CItem("", ""));
        settlementAgentOptionField.addItem(new CItem("Euroclear Bank", "549300OZ46BRLZ8Y6F65"));
        settlementAgentOptionField.addItem(new CItem("BNP Paribas Securities Services", "549300WCGB70D06XZS54"));
        settlementAgentOptionField.addItem(new CItem("Clearstream Bank Frankfurt", "549300298FD7AS4PPU70"));
        settlementAgentOptionField.addItem(new CItem("Euroclear UKI (CREST)", "549300OZ46BRLZ8Y6F65"));
        settlementAgentOptionField.addItem(new CItem("Bank of New York Mellon", "HPFHU0OQ28E4N0NFVK49"));


        settlementAgentOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        settlementAgentOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        settlementAgentOptionPanel.add(settlementAgentOptionLabel);
        settlementAgentOptionPanel.add(settlementAgentOptionField,gc);
        repoTradePanelCol2.add(settlementAgentOptionPanel);

        //CCP
        JPanel centralClearingCounterpartyOptionPanel = new JPanel(new GridBagLayout());
        JLabel centralClearingCounterpartyOptionLabel = new JLabel("CCP", JLabel.LEFT);
        centralClearingCounterpartyOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        centralClearingCounterpartyOptionField = new JComboBox();
        centralClearingCounterpartyOptionField.addItem(new CItem("", ""));
        centralClearingCounterpartyOptionField.addItem(new CItem("LCH SA", "R1IO4YJ0O79SMWVCHB58"));

        centralClearingCounterpartyOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        centralClearingCounterpartyOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        centralClearingCounterpartyOptionPanel.add(centralClearingCounterpartyOptionLabel);
        centralClearingCounterpartyOptionPanel.add(centralClearingCounterpartyOptionField,gc);
        repoTradePanelCol2.add(centralClearingCounterpartyOptionPanel);


        //CSD Participant
        JPanel csdOptionPanel = new JPanel(new GridBagLayout());
        JLabel csdOptionLabel = new JLabel("CSD Particpant", JLabel.LEFT);
        csdOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        csdOptionField = new JComboBox();
        ru.addParties(csdOptionField);

        csdOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        csdOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        csdOptionPanel.add(csdOptionLabel);
        csdOptionPanel.add(csdOptionField,gc);
        repoTradePanelCol2.add(csdOptionPanel);

        //Clearing Member
        JPanel clearingMemberPanel = new JPanel(new GridBagLayout());
        JLabel clearingMemberLabel = new JLabel("Clearing Member", JLabel.LEFT);
        clearingMemberLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        clearingMemberField = new JComboBox();
        ru.addParties(clearingMemberField);

        clearingMemberField.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearingMemberField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        clearingMemberPanel.add(clearingMemberLabel);
        clearingMemberPanel.add(clearingMemberField,gc);
        repoTradePanelCol2.add(clearingMemberPanel);


        //Agent Lender
        JPanel agentLenderPanel = new JPanel(new GridBagLayout());
        JLabel agentLenderLabel = new JLabel("Agent Lender", JLabel.LEFT);
        agentLenderLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        agentLenderField = new JComboBox();
        ru.addParties(agentLenderField);

        agentLenderField.setAlignmentX(Component.LEFT_ALIGNMENT);
        agentLenderField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        agentLenderPanel.add(agentLenderLabel);
        agentLenderPanel.add(agentLenderField,gc);
        repoTradePanelCol2.add(agentLenderPanel);

        //Broker
        JPanel brokerPanel = new JPanel(new GridBagLayout());
        JLabel brokerLabel = new JLabel("Broker", JLabel.LEFT);
        brokerLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        brokerField = new JComboBox();
        brokerField.addItem(new CItem("", "", "", ""));
        brokerField.addItem(new CItem("Tullet Prebon Securities", "549300BMVW85YF9FGN67", "TSRE", "MIC"));

        brokerField.setAlignmentX(Component.LEFT_ALIGNMENT);
        brokerField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        brokerPanel.add(brokerLabel);
        brokerPanel.add(brokerField,gc);
        repoTradePanelCol2.add(brokerPanel);

        //TriParty Agent
        JPanel tripartyPanel = new JPanel(new GridBagLayout());
        JLabel tripartyLabel = new JLabel("Tri-Party Agent", JLabel.LEFT);
        tripartyLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        tripartyField = new JComboBox();
        tripartyField.addItem(new CItem("", ""));
        tripartyField.addItem(new CItem("Euroclear Bank", "549300OZ46BRLZ8Y6F65"));
        tripartyField.addItem(new CItem("BNP Paribas Securities Services", "549300WCGB70D06XZS54"));

        tripartyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        tripartyField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        tripartyPanel.add(tripartyLabel);
        tripartyPanel.add(tripartyField,gc);
        repoTradePanelCol2.add(tripartyPanel);

        //Beneficiary
        JPanel beneficiaryPanel = new JPanel(new GridBagLayout());
        JLabel beneficiaryLabel = new JLabel("Beneficiary", JLabel.LEFT);
        beneficiaryLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        beneficiaryField = new JComboBox();
        beneficiaryField = ru.addParties(beneficiaryField);

        beneficiaryField.setAlignmentX(Component.LEFT_ALIGNMENT);
        beneficiaryField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        beneficiaryPanel.add(beneficiaryLabel);
        beneficiaryPanel.add(beneficiaryField,gc);
        repoTradePanelCol2.add(beneficiaryPanel);


        //Floating Rate Reference
        JPanel floatingRateReferencePanel = new JPanel(new GridBagLayout());
        JLabel floatingRateReferenceLabel = new JLabel("Floating Rate Ref", JLabel.LEFT);
        floatingRateReferenceLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateReferenceField = new JTextField(labelHeight);

        floatingRateReferencePanel.add(floatingRateReferenceLabel);
        floatingRateReferencePanel.add(floatingRateReferenceField,gc);
        floatingRateReferenceField.setEnabled(false);
        repoTradePanelCol2.add(floatingRateReferencePanel);

        //Floating Rate Reference Period
        JPanel floatingRateReferencePeriodPanel = new JPanel(new GridBagLayout());
        JLabel floatingRateReferencePeriodLabel = new JLabel("Floating Rate Period", JLabel.LEFT);
        floatingRateReferencePeriodLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        String[] referencePeriods = {"DAYS", "WEEKS", "MONTHS"};
        floatingRateReferencePeriodField = new JComboBox<String>(referencePeriods);
        floatingRateReferencePeriodField.setAlignmentX(Component.LEFT_ALIGNMENT);
        floatingRateReferencePeriodField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));


        floatingRateReferencePeriodPanel.add(floatingRateReferencePeriodLabel);
        floatingRateReferencePeriodPanel.add(floatingRateReferencePeriodField,gc);
        floatingRateReferencePeriodField.setEnabled(false);
        repoTradePanelCol2.add(floatingRateReferencePeriodPanel);


        //Floating Rate Reference Multiplier
        JPanel floatingRateReferenceMultiplierPanel = new JPanel(new GridBagLayout());
        JLabel floatingRateReferenceMultiplierLabel = new JLabel("Floating Rate Freq", JLabel.LEFT);
        floatingRateReferenceMultiplierLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateReferenceMultiplierField = new JTextField(labelHeight);

        floatingRateReferenceMultiplierPanel.add(floatingRateReferenceMultiplierLabel);
        floatingRateReferenceMultiplierPanel.add(floatingRateReferenceMultiplierField,gc);
        repoTradePanelCol2.add(floatingRateReferenceMultiplierPanel);

        //Floating Payment Frequency
        JPanel floatingRatePaymentFreqPanel = new JPanel(new GridBagLayout());
        JLabel floatingRatePaymentFreqLabel = new JLabel("Floating Payment Freq", JLabel.LEFT);
        floatingRatePaymentFreqLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        String[] paymentPeriods = {"DAY", "WEEK", "MONTH"};
        floatingRatePaymentFreqField = new JComboBox<String>(paymentPeriods);
        floatingRatePaymentFreqField.setAlignmentX(Component.LEFT_ALIGNMENT);
        floatingRatePaymentFreqField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        floatingRatePaymentFreqPanel.add(floatingRatePaymentFreqLabel);
        floatingRatePaymentFreqPanel.add(floatingRatePaymentFreqField,gc);
        floatingRatePaymentFreqField.setEnabled(false);
        repoTradePanelCol2.add(floatingRatePaymentFreqPanel);

        //Floating Payment Multiplier
        JPanel floatingRatePaymentMultiplierPanel = new JPanel(new GridBagLayout());
        JLabel floatingRatePaymentMultiplierLabel = new JLabel("Floating Payment Multi", JLabel.LEFT);
        floatingRatePaymentMultiplierLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRatePaymentMultiplierField = new JTextField(labelHeight);

        floatingRatePaymentMultiplierPanel.add(floatingRatePaymentMultiplierLabel);
        floatingRatePaymentMultiplierPanel.add(floatingRatePaymentMultiplierField,gc);
        floatingRatePaymentMultiplierField.setEnabled(false);
        repoTradePanelCol2.add(floatingRatePaymentMultiplierPanel);

        //Floating Reset Frequency
        JPanel floatingRateResetFreqPanel = new JPanel(new GridBagLayout());
        JLabel floatingRateResetFreqLabel = new JLabel("Floating Reset Freq", JLabel.LEFT);
        floatingRateResetFreqLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateResetFreqField = new JComboBox<String>(referencePeriods);
        floatingRateResetFreqField.setAlignmentX(Component.LEFT_ALIGNMENT);
        floatingRateResetFreqField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        floatingRateResetFreqPanel.add(floatingRateResetFreqLabel);
        floatingRateResetFreqPanel.add(floatingRateResetFreqField,gc);
        floatingRateResetFreqField.setEnabled(false);
        repoTradePanelCol2.add(floatingRateResetFreqPanel);

        //Floating Reset Multiplier
        JPanel floatingRateResetMultiplierPanel = new JPanel(new GridBagLayout());
        JLabel floatingRateResetMultiplierLabel = new JLabel("Floating Reset Multi", JLabel.LEFT);
        floatingRateResetMultiplierLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateResetMultiplierField = new JTextField(labelHeight);

        floatingRateResetMultiplierPanel.add(floatingRateResetMultiplierLabel);
        floatingRateResetMultiplierPanel.add(floatingRateResetMultiplierField,gc);
        floatingRateResetMultiplierField.setEnabled(false);
        repoTradePanelCol2.add(floatingRateResetMultiplierPanel);

        //Floating Reference Rate
        JPanel floatingRatePanel = new JPanel(new GridBagLayout());
        JLabel floatingRateLabel = new JLabel("Reference Rate", JLabel.LEFT);
        floatingRateLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateField = new JTextField(labelHeight);

        floatingRatePanel.add(floatingRateLabel);
        floatingRatePanel.add(floatingRateField,gc);
        floatingRateField.setEnabled(false);
        repoTradePanelCol2.add(floatingRatePanel);

        floatingRateField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        floatingRateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });


        //Spread
        JPanel floatingRateSpreadPanel = new JPanel(new GridBagLayout());
        JLabel floatingRateSpreadLabel = new JLabel("Floating Spread", JLabel.LEFT);
        floatingRateSpreadLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        floatingRateSpreadField = new JTextField(labelHeight);

        floatingRateSpreadPanel.add(floatingRateSpreadLabel);
        floatingRateSpreadPanel.add(floatingRateSpreadField,gc);
        floatingRateSpreadField.setEnabled(false);
        repoTradePanelCol2.add(floatingRateSpreadPanel);

        floatingRateSpreadField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        floatingRateSpreadField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if ((e.getKeyChar() == KeyEvent.VK_TAB) || (e.getKeyChar() == KeyEvent.VK_ENTER)) {
                    //System.out.print("Tab Pressed");
                    updateTotalsXPrice();
                }
            }
        });


        //AgreementName
        String[] agreementChoices = {"GMRA", "MMRA", "BIAG", "GMSLA", "OTHER"};
        JPanel agreementNamePanel = new JPanel(new GridBagLayout());
        JLabel agreementNameLabel = new JLabel("Agreement Name", JLabel.LEFT);
        agreementNameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        agreementNameField = new JComboBox<String>(agreementChoices);
        agreementNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        agreementNameField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        agreementNamePanel.add(agreementNameLabel);
        agreementNamePanel.add(agreementNameField,gc);
        repoTradePanelCol2.add(agreementNamePanel);


        //Agreement Version
        JPanel agreementVersionPanel = new JPanel(new GridBagLayout());
        JLabel agreementVersionLabel = new JLabel("Agreement Version", JLabel.LEFT);
        agreementVersionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        agreementVersionField = new JTextField(labelHeight);
        agreementVersionField.setText("2011");
        agreementVersionPanel.add(agreementVersionLabel);
        agreementVersionPanel.add(agreementVersionField,gc);
        repoTradePanelCol2.add(agreementVersionPanel);

        //Other AgreementName
        JPanel otherAgreementNamePanel = new JPanel(new GridBagLayout());
        JLabel otherAgreementNameLabel = new JLabel("Other Agreement", JLabel.LEFT);
        otherAgreementNameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        otherAgreementNameField = new JTextField(labelHeight);

        otherAgreementNameField.setText("");
        otherAgreementNamePanel.add(otherAgreementNameLabel);
        otherAgreementNamePanel.add(otherAgreementNameField,gc);
        repoTradePanelCol2.add(otherAgreementNamePanel);

        //Trade ID
        JPanel tradeIdPanel = new JPanel(new GridBagLayout());
        JLabel tradeIdLabel = new JLabel("Trade ID", JLabel.LEFT);
        tradeIdLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        tradeIdField = new JTextField(labelHeight);
        tradeIdField.setText("");
        tradeIdPanel.add(tradeIdLabel);
        tradeIdPanel.add(tradeIdField,gc);
        repoTradePanelCol2.add(tradeIdPanel);

        //Trade Status
        JPanel statusPanel = new JPanel(new GridBagLayout());
        JLabel statusLabel = new JLabel("Status", JLabel.LEFT);
        statusLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        statusField = new JTextField(labelHeight);
        statusField.setText("NEW");
        statusPanel.add(statusLabel);
        statusPanel.add(statusField,gc);
        repoTradePanelCol2.add(statusPanel);


        //Reporting Entity
        JPanel reportingEntityOptionPanel = new JPanel(new GridBagLayout());
        JLabel reportingEntityOptionLabel = new JLabel("Reporting Entity", JLabel.LEFT);
        reportingEntityOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        reportingEntityOptionField = new JComboBox();
        reportingEntityOptionField.addItem(new CItem("", ""));
        reportingEntityOptionField.addItem(new CItem("Euroclear Bank", "549300OZ46BRLZ8Y6F65"));
        reportingEntityOptionField.addItem(new CItem("BNP Paribas Securities Services", "549300WCGB70D06XZS54"));
        reportingEntityOptionField.addItem(new CItem("Clearstream Bank Frankfurt", "549300298FD7AS4PPU70"));
        reportingEntityOptionField.addItem(new CItem("Euroclear UKI (CREST)", "549300OZ46BRLZ8Y6F65"));
        reportingEntityOptionField.addItem(new CItem("Bank of New York Mellon", "HPFHU0OQ28E4N0NFVK49"));


        reportingEntityOptionField.setAlignmentX(Component.LEFT_ALIGNMENT);
        reportingEntityOptionField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        reportingEntityOptionPanel.add(reportingEntityOptionLabel);
        reportingEntityOptionPanel.add(reportingEntityOptionField,gc);
        repoTradePanelCol2.add(reportingEntityOptionPanel);


        //Report Side
        String[] reportingChoices = {"SELLER", "BUYER", "BOTH"};
        JPanel reportingSidePanel = new JPanel(new GridBagLayout());
        JLabel reportingSideLabel = new JLabel("Reporting Side", JLabel.LEFT);
        reportingSideLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        reportingSideField = new JComboBox<String>(reportingChoices);
        reportingSideField.setAlignmentX(Component.LEFT_ALIGNMENT);
        reportingSideField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        reportingSidePanel.add(reportingSideLabel);
        reportingSidePanel.add(reportingSideField,gc);
        repoTradePanelCol2.add(reportingSidePanel);

        JPanel fillerPanel = new JPanel(new GridBagLayout());
        JLabel fillerLabel = new JLabel("", JLabel.LEFT);
        fillerLabel.setPreferredSize(new Dimension(labelWidth+fieldWidth, fieldHeight));
        fillerPanel.add(fillerLabel,gc);
        repoTradePanelCol2.add(fillerPanel,gc);


        tradeEntryModel.add(repoTradePanel,constraints);
        add(tradeEntryModel,0,0);


    }

    private JComponent add(JComponent component, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
        return component;
    }

    private void activateSubmit() {
        //TradeType type = (TradeType) typeComboBox.getSelectedItem();

    }

    public void update(Observable o, Object arg) {

    }

    public void rateTypeFieldEvent(String selectedRateType) {

        if (selectedRateType.equals("FLOAT")) {

            this.floatingRateReferenceField.setText("SONIA");
            this.floatingRateReferenceField.setEnabled(true);
            this.floatingRateReferenceField.setBackground(Color.WHITE);

            this.floatingRateReferencePeriodField.setSelectedItem("DAYS");
            this.floatingRateReferencePeriodField.setEnabled(true);
            this.floatingRateReferencePeriodField.setBackground(Color.WHITE);

            this.floatingRateReferenceMultiplierField.setText("1");
            this.floatingRateReferenceMultiplierField.setEnabled(true);
            this.floatingRateReferenceMultiplierField.setBackground(Color.WHITE);

            this.floatingRatePaymentFreqField.setSelectedItem("MONTH");
            this.floatingRatePaymentFreqField.setEnabled(true);
            this.floatingRatePaymentFreqField.setBackground(Color.WHITE);

            this.floatingRatePaymentMultiplierField.setText("1");
            this.floatingRatePaymentMultiplierField.setEnabled(true);
            this.floatingRatePaymentMultiplierField.setBackground(Color.WHITE);

            this.floatingRateResetFreqField.setSelectedItem("DAYS");
            this.floatingRateResetFreqField.setEnabled(true);
            this.floatingRateResetFreqField.setBackground(Color.WHITE);

            this.floatingRateResetMultiplierField.setText("1");
            this.floatingRateResetMultiplierField.setEnabled(true);
            this.floatingRateResetMultiplierField.setBackground(Color.WHITE);

            this.floatingRateField.setText("4.43");
            this.floatingRateField.setEnabled(true);
            this.floatingRateField.setBackground(Color.WHITE);

            this.floatingRateSpreadField.setText("2");
            this.floatingRateSpreadField.setEnabled(true);
            this.floatingRateSpreadField.setBackground(Color.WHITE);

            Double refrate = Double.parseDouble(this.floatingRateField.getText());
            Double spread = Double.parseDouble(this.floatingRateSpreadField.getText());

            Double reporate = (double) Math.round((refrate + spread / 100.0) * 100) / 100;

            this.repoRateField.setText(reporate.toString());


        } else {
            this.floatingRateReferenceField.setText("");
            this.floatingRateReferenceField.setEnabled(false);
            this.floatingRateField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateReferencePeriodField.setSelectedItem("");
            this.floatingRateReferencePeriodField.setEnabled(false);
            this.floatingRateReferencePeriodField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateReferenceMultiplierField.setText("");
            this.floatingRateReferenceMultiplierField.setEnabled(false);
            this.floatingRateReferenceMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRatePaymentFreqField.setSelectedItem("");
            this.floatingRatePaymentFreqField.setEnabled(false);
            this.floatingRatePaymentFreqField.setBackground(Color.LIGHT_GRAY);

            this.floatingRatePaymentMultiplierField.setText("");
            this.floatingRatePaymentMultiplierField.setEnabled(false);
            this.floatingRatePaymentMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateResetFreqField.setSelectedItem("");
            this.floatingRateResetFreqField.setEnabled(false);
            this.floatingRateResetFreqField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateResetMultiplierField.setText("");
            this.floatingRateResetMultiplierField.setEnabled(false);
            this.floatingRateResetMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateField.setText("");
            this.floatingRateField.setEnabled(false);
            this.floatingRateField.setBackground(Color.WHITE);

            this.floatingRateSpreadField.setText("");
            this.floatingRateSpreadField.setEnabled(false);
            this.floatingRateSpreadField.setBackground(Color.WHITE);

            this.repoRateField.setText("4.65");

        }

    }

    public void collateralTypeFieldEvent(String selectedCollateralType) throws IOException, SQLException {

        if (selectedCollateralType.equals("Special")) {
            //Collateral
            collateralDescriptionField.setText("GILT .625 31/07/2035");

            //Collateral ISIN
            collateralISINField.setText("GB00BMGR2916");

            //Collateral Quantity
            collateralQuantityField.setText("1000");

            //Collateral Price
            collateralCleanPriceField.setText("91.06");

            //Collateral Dirty Price
            collateralDirtyPriceField.setText("91.8066");

            //Collateral Adjusted Value
            collateralAdjustedValueField.setText("918066.00");


            updateTotalsXPrice();

        } else {
            //Collateral
            collateralDescriptionField.setText("ICMA EU 10 Year Bond GC Basket");

            //Collateral ISIN
            collateralISINField.setText("IC000A3CT441");

            //Collateral Quantity
            collateralQuantityField.setText("50000");

            //Collateral Price
            collateralCleanPriceField.setText("100.00");

            //Collateral Dirty Price
            collateralDirtyPriceField.setText("100.00");

            //Collateral Adjusted Value
            collateralAdjustedValueField.setText("50000000.00");

            haircutField.setText("0");


        }

    }

    public void termTypeFieldEvent(String selectedTermType) {

        if (selectedTermType.equals("FIXED")) {
            this.repurchasePriceField.setText("");
            this.repurchasePriceField.setEnabled(true);
            this.repurchasePriceField.setBackground(Color.WHITE);

            //Repurchase Date
            this.RDzonedDateTime = PDzonedDateTime.plusDays(1);
            this.RDformattedDateTimeString = RDzonedDateTime.format(formatter);
            this.repurchaseDateField.setText(RDformattedDateTimeString);

            this.repurchaseDateField.setEnabled(true);
            this.repurchaseDateField.setBackground(Color.WHITE);


            updateTotalsXPrice();
        } else {

            this.repurchaseDateField.setText("");
            this.repurchaseDateField.setEnabled(false);
            this.repurchaseDateField.setBackground(Color.LIGHT_GRAY);

            this.repurchasePriceField.setEnabled(false);
            this.repurchasePriceField.setBackground(Color.LIGHT_GRAY);

        }
    }
    public void updateTotalsXPrice() {

        //System.out.println("Update Totals");

        DecimalFormat formatter = new DecimalFormat("#,###.00");

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

        String pdt = purchaseDateField.getText();
        String purchaseDateStr = pdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";
        ZonedDateTime startDateTime = ZonedDateTime.parse(purchaseDateStr, dtFormat);

        String rpdt = repurchaseDateField.getText();
        String repurchaseDateStr = rpdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";
        ZonedDateTime endDateTime = ZonedDateTime.parse(repurchaseDateStr, dtFormat);

        long daysBetween = Duration.between(startDateTime, endDateTime).toDays();
        DecimalFormat intFormat = new DecimalFormat("###");
        String termsDaysStr = intFormat.format(daysBetween);
        termDaysField.setText(termsDaysStr);

        Double dp = Double.valueOf(collateralDirtyPriceField.getText().replaceAll(",", "").trim());
        Double cc = Double.valueOf(collateralQuantityField.getText().replaceAll(",", "").trim());
        Double cap = cc * dp / 100.00 * 1000;
        String collateralAdjustedValueStr = formatter.format(cap);
        collateralAdjustedValueField.setText(collateralAdjustedValueStr);

        //s.replaceAll(",","").trim();

        Double cv = Double.valueOf(collateralAdjustedValueField.getText().replaceAll(",", "").trim());
        Double hc = Double.valueOf(haircutField.getText().replaceAll(",", "").trim());
        Double pp = cv * (1 - hc / 100.00);


        String purchasePriceStr = formatter.format(pp);
        purchasePriceField.setText(purchasePriceStr);

        if (this.rateTypeField.getSelectedItem().toString().equals("FLOAT")) {

            Double refrate = Double.parseDouble(this.floatingRateField.getText());
            Double spread = Double.parseDouble(this.floatingRateSpreadField.getText());
            Double reporate = (double) Math.round((refrate + spread / 100.0) * 100) / 100;
            this.repoRateField.setText(reporate.toString());

        }


        Double rr = Double.valueOf(repoRateField.getText().replaceAll(",", "").trim());
        Double rp = pp + (pp * (daysBetween / 360.00 * rr / 100.00));
        String repurchasePriceStr = formatter.format(rp);
        repurchasePriceField.setText(repurchasePriceStr);


    }

}
