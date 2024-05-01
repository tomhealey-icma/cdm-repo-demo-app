package com.finxis.cdm.crossproductapp.ui;

import com.finxis.cdm.crossproductapp.*;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActionPanel extends JPanel implements ActionListener {

    public Integer btnWidth;

    public Integer btnHeight;


    public JTabbedPane tabbedPane;

    public JButton newTradeBtn;
    public JButton bookTradeBtn;

    public JPanel actionPanel;

    public TradeEntryPanel tep;

    public transient CdmTradingDemoApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();

    public ActionPanel(final ActionPanelModel actionPanelModel,
                       final TradeEntryPanel tep,
                       final CdmTradingDemoApplication application) {



        setName("TradeEntryPanel");
        this.actionPanel = actionPanelModel;
        this.application = application;


        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setLayout(new GridBagLayout());
        createComponents(tep);

    }
    private void createComponents(TradeEntryPanel tep) {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill= GridBagConstraints.HORIZONTAL;
        gc.weightx=1;
        btnWidth = 150;
        btnHeight = 30;

        this.tep = tep;
        actionPanel.setLayout(new GridBagLayout());
        GridBagConstraints apgbc = new GridBagConstraints();
        apgbc.gridy = 0;
        apgbc.anchor = apgbc.PAGE_START;

        setSize(600, 100);

        Border blueline = BorderFactory.createLineBorder(Color.blue);
        setBorder(blueline);
        setVisible(true);

        JPanel actionBtnPanel = new JPanel(new GridLayout(1, 6));
        actionBtnPanel.setSize(600, 100);

        newTradeBtn = new JButton("New Trade");
        newTradeBtn.addActionListener(this);
        newTradeBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(newTradeBtn);

        bookTradeBtn = new JButton("Book Trade");
        bookTradeBtn.addActionListener(this);
        newTradeBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(bookTradeBtn);


        actionBtnPanel.setVisible(true);

        add(actionBtnPanel,constraints);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        BookTrade bt = new BookTrade();
        RepoLifeCycle rlc = new RepoLifeCycle();


        if (ae.getSource() == this.newTradeBtn) {
            JOptionPane.showMessageDialog(this, "Create New Trade", "Alert", JOptionPane.INFORMATION_MESSAGE);
            try {
                bt.loadNewTrade(this.tep);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if (ae.getSource() == this.bookTradeBtn) {
            JOptionPane.showMessageDialog(this, "Book Trade", "Alert", JOptionPane.INFORMATION_MESSAGE);
            try {
                String businessEvent = bt.bookTrade(tep);
                tep.statusField.setText("EXECUTED");
                System.out.println(businessEvent);

                IcmaRepoUtil ru = new IcmaRepoUtil();
                tep.tradeStateStr = ru.getAfterTradeState(businessEvent);
                tep.afterTradeStateStr = tep.tradeStateStr;


                DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime localDateTime = LocalDateTime.now();
                String eventDateTime = localDateTime.format(eventDateFormat);

                ru.writeEventToFile("execution-business-event", eventDateTime, businessEvent);


            } catch (IOException ignored) {
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}