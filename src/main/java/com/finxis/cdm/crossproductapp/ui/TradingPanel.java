package com.finxis.cdm.crossproductapp.ui;

import com.finxis.cdm.crossproductapp.ActionPanelModel;
import com.finxis.cdm.crossproductapp.CdmTradingDemoApplication;
import com.finxis.cdm.crossproductapp.TradeEntryModel;
import com.finxis.cdm.crossproductapp.TradeTableModel;

import java.awt.*;

import javax.swing.*;

public class TradingPanel extends JPanel{

    private final TradePanel tradePanel;
    private final TradeEntryPanel tradeEntryPanel;

    private final ActionPanel actionPanel;
    public TradingPanel(TradeTableModel tradeTableModel, TradeEntryModel tradeEntryModel,
                        ActionPanelModel actionPanelModel, CdmTradingDemoApplication application) {

        setName("Trade Training App");

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        //constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        tradeEntryPanel = new TradeEntryPanel(tradeEntryModel, application);
        constraints.insets = new Insets(0, 0, 5, 0);
        add(tradeEntryPanel, constraints);

        constraints.gridx++;
        constraints.weighty = 10;

        actionPanel = new ActionPanel(actionPanelModel, tradeEntryPanel, application);
        add(actionPanel, constraints);


        JTabbedPane tabbedPane = new JTabbedPane();
        tradePanel = new TradePanel(tradeTableModel, tradeEntryModel, actionPanelModel, application);

        tabbedPane.add("Trades", tradePanel);
        add(tabbedPane, constraints);


    }



}
