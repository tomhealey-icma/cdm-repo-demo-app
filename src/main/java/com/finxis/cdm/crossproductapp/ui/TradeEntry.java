package com.finxis.cdm.crossproductapp.ui;


import com.finxis.cdm.crossproductapp.TradeEntryModel;
import com.finxis.cdm.crossproductapp.CdmTradingDemoApplication;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TradeEntry extends JPanel implements MouseListener {
    private final transient CdmTradingDemoApplication application;

    public TradeEntry(TradeEntryModel tradeEntryModel, CdmTradingDemoApplication application) {
        //super(tradeEntryModel);
        //add(tradeEntryModel);
        this.application = application;
        addMouseListener(this);
    }



    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2)
            return;
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
