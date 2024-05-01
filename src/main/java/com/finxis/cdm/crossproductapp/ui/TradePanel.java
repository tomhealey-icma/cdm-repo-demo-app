package com.finxis.cdm.crossproductapp.ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.finxis.cdm.crossproductapp.ActionPanelModel;
import com.finxis.cdm.crossproductapp.CdmTradingDemoApplication;
import com.finxis.cdm.crossproductapp.TradeEntryModel;
import com.finxis.cdm.crossproductapp.TradeTableModel;

public class TradePanel extends JPanel {


    private JTable tradeTable = null;

    private JPanel tradeEntry = null;

    private JPanel actionPanel = null;
    public TradePanel(TradeTableModel tradeTableModel, TradeEntryModel tradeEntryModel, ActionPanelModel actionPanelModel, CdmTradingDemoApplication application) {


        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 2;


       tradeTable = new TradeTable(tradeTableModel, application);
       add(new JScrollPane(tradeTable), constraints);
    }

    public JTable tradeTable() {
        return tradeTable;
    }
    public JPanel tradeEntry() {
        return tradeEntry;
    }

    public JPanel actionPanel() {
        return actionPanel;
    }
}
