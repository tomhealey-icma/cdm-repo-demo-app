package com.finxis.cdm.crossproductapp.ui;

import com.finxis.cdm.crossproductapp.ActionPanelModel;
import com.finxis.cdm.crossproductapp.CdmTradingDemoApplication;
import com.finxis.cdm.crossproductapp.TradeEntryModel;
import com.finxis.cdm.crossproductapp.TradeTableModel;

import javax.swing.*;
import java.awt.*;

public class TradingFrame extends JFrame {

    public TradingFrame(TradeTableModel tradeTableModel, TradeEntryModel tradeEntryModel, ActionPanelModel actionPanelModel, CdmTradingDemoApplication application){
        super();
        setTitle("CDM Demo Trade Booking App");
        setSize(800,800);

        createMenuBar(application);
        getContentPane().add(new TradingPanel(tradeTableModel, tradeEntryModel, actionPanelModel, application), BorderLayout.CENTER);
        setVisible(true);
    }

    private void createMenuBar(final CdmTradingDemoApplication application) {
        JMenuBar menubar = new JMenuBar();
    }
}
