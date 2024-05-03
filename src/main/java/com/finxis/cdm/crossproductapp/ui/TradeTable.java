package com.finxis.cdm.crossproductapp.ui;

import com.finxis.cdm.crossproductapp.Trade;
import com.finxis.cdm.crossproductapp.TradeTableModel;
import com.finxis.cdm.crossproductapp.CdmTradingDemo;
import com.finxis.cdm.crossproductapp.CdmTradingDemoApplication;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TradeTable extends JTable implements MouseListener {
    private final transient CdmTradingDemoApplication application;

    public TradeTable(TradeTableModel tradeTableModel, CdmTradingDemoApplication application) {
        super(tradeTableModel);
        this.application = application;
        addMouseListener(this);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Trade trade= ((TradeTableModel) dataModel).getTrade(row);

        boolean canceled = trade.getCanceled();

        DefaultTableCellRenderer r = (DefaultTableCellRenderer) renderer;
        r.setForeground(Color.black);
        r.setBackground(Color.white);

        return super.prepareRenderer(renderer, row, column);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2)
            return;
        int row = rowAtPoint(e.getPoint());
        Trade trade = ((TradeTableModel) dataModel).getTrade(row);
        //application.cancel(trade);
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
