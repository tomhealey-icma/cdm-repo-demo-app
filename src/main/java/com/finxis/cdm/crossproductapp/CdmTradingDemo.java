package com.finxis.cdm.crossproductapp;

import cdm.base.datetime.BusinessCenterEnum;
import cdm.base.datetime.BusinessCenters;
import com.finxis.cdm.crossproductapp.ui.ActionPanel;
import com.finxis.cdm.crossproductapp.ui.TradingFrame;
import com.finxis.cdmutil.User;
import com.rosetta.model.lib.process.PostProcessStep;
import com.sun.jdi.Field;


import javax.swing.*;
import java.util.List;

public class CdmTradingDemo {

    private PostProcessStep keyProcessor = null;
    private JFrame frame = null;
    private static CdmTradingDemo repodemoapp;

    User user = new User();
    public CdmTradingDemo(String[] args) throws Exception {

        user.createUserBusinessCenter(BusinessCenterEnum.GBLO);
        user.createUserPartyBusinessCenter(BusinessCenterEnum.GBLO);
        user.createUserParty("XX6I5TESTEU3UXPYFY54");

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
