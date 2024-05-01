package com.finxis.cdm.crossproductapp;

import com.finxis.cdm.crossproductapp.ui.ActionPanel;
import com.finxis.cdm.crossproductapp.ui.TradingFrame;
import com.rosetta.model.lib.process.PostProcessStep;

import javax.swing.*;

public class CdmTradingDemo {

    private PostProcessStep keyProcessor = null;
    private JFrame frame = null;
    private static CdmTradingDemo repodemoapp;

    public CdmTradingDemo(String[] args) throws Exception {

        TradeEntryModel tradeEntryModel = tradeEntryModel();
        ActionPanelModel actionPanelModel = actionPanelModel();
        TradeTableModel tradeTableModel = tradeTableModel();
        CdmTradingDemoApplication application = application();

        frame = new TradingFrame(tradeTableModel, tradeEntryModel, actionPanelModel, application);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    protected TradeTableModel tradeTableModel() {
        return new TradeTableModel();
    }
    protected TradeEntryModel tradeEntryModel() {
        return new TradeEntryModel();
    }

    protected ActionPanelModel actionPanelModel() {
        return new ActionPanelModel();
    }
    protected CdmTradingDemoApplication application() {
        return new CdmTradingDemoApplication();
    }

    public JFrame getFrame() {
        return frame;
    }

    public static CdmTradingDemo get() {
        return repodemoapp;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("CDM Application Demo");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        repodemoapp = new CdmTradingDemo(args);


    }

}
