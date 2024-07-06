package com.finxis.cdm.crossproductapp.ui;

import com.finxis.cdm.crossproductapp.*;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil;
import com.finxis.cdm.crossproductapp.util.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
public class ActionPanel extends JPanel implements Observer {

    public Integer btnWidth;

    public Integer btnHeight;


    public JTabbedPane tabbedPane;

    public JButton newTradeBtn;
    public JButton bookTradeBtn;

    public JButton chooseFileBtn;

    public JPanel actionPanel;

    public TradeEntryPanel tep;

    private TradeTableModel tradeTableModel = null;

    public transient CdmTradingDemoApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();

    public ActionPanel(final TradeTableModel tradeTableModel,
                        final ActionPanelModel actionPanelModel,
                       final TradeEntryPanel tep,
                       final CdmTradingDemoApplication application) {


        setName("TradeEntryPanel");
        this.actionPanel = actionPanelModel;
        this.tradeTableModel = tradeTableModel;
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
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        btnWidth = 150;
        btnHeight = 30;

        this.tep = tep;
        actionPanel.setLayout(new GridBagLayout());
        GridBagConstraints apgbc = new GridBagConstraints();
        apgbc.gridy = 0;
        apgbc.anchor = apgbc.PAGE_START;

        setSize(600, 50);
        setPreferredSize(new Dimension(600,50));

        Border blueline = BorderFactory.createLineBorder(Color.blue);
        setBorder(blueline);
        setVisible(true);

        JPanel actionBtnPanel = new JPanel(new GridLayout(1, 6));
        actionBtnPanel.setSize(600, 50);
        actionBtnPanel.setPreferredSize(new Dimension(600,50));

        newTradeBtn = new JButton("New Trade");
        addActionListener(new ActionPanelListener() , newTradeBtn);
        newTradeBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(newTradeBtn);

        bookTradeBtn = new JButton("Book Trade");
        addActionListener(new ActionPanelListener() , bookTradeBtn);
        bookTradeBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(bookTradeBtn);

        chooseFileBtn = new JButton("Select File");
        addActionListener(new ActionPanelListener() , chooseFileBtn);
        chooseFileBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(chooseFileBtn);


        actionBtnPanel.setVisible(true);

        add(actionBtnPanel, constraints);
        setVisible(true);

    }

    public void addActionListener(ActionListener listener, JButton jButton) {
        jButton.addActionListener(listener);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private class ActionPanelListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            BookTrade bt = new BookTrade();
            RepoLifeCycle rlc = new RepoLifeCycle();


            if (ae.getSource() == newTradeBtn) {
                JOptionPane.showMessageDialog(tep, "Create New Trade", "Alert", JOptionPane.INFORMATION_MESSAGE);
                try {
                    bt.loadNewTrade(tep);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else if (ae.getSource() == bookTradeBtn) {
                JOptionPane.showMessageDialog(tep, "Book Trade", "Alert", JOptionPane.INFORMATION_MESSAGE);
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

                    tradeTableModel.addTrade(businessEvent);

                } catch (IOException ignored) {
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }  else if (ae.getSource() == chooseFileBtn) {
            JOptionPane.showMessageDialog(tep, "Select File", "Alert", JOptionPane.INFORMATION_MESSAGE);

            JFileChooser fc  = new JFileChooser();
            File cd = new File("./upload");
            fc.setCurrentDirectory(cd);
            int returnVal = fc.showDialog(tep, "Attach");
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File loadFile = fc.getSelectedFile();
                String name = loadFile.getName();
                int lastIndexOf = name.lastIndexOf(".");
                if (lastIndexOf == -1) {
                    JOptionPane.showMessageDialog(tep, "Select File", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                String fileType = name.substring(lastIndexOf);
                //This is where a real application would open the file.
                System.out.println("Opening: " + loadFile.getName());
                //Convert XML file to object

                if(fileType.equals(".xml")) {
                    LoadXmlNewTrade lxml = new LoadXmlNewTrade();
                    lxml.createNewTradeFromXML(loadFile);
                }else if (fileType.equals(".json")){
                    LoadXmlNewTrade lxml = new LoadXmlNewTrade();
                    Document xmlDoc= null;
                    try {
                        xmlDoc= new LoadJsonNewTrade().convertJsontoXml(loadFile);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        lxml.createNewTradeFromXMLDoc(xmlDoc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    }
                }else if (fileType.equals(".csv")){
                    LoadCsvNewTrade lcsv = new LoadCsvNewTrade();
                    try {
                        lcsv.createNewTradeFromCsv1(loadFile);
                        lcsv.createNewTradeFromCsv2(loadFile);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    JOptionPane.showMessageDialog(tep, "Cannot load file type " + fileType , "Alert", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        }
        }

    }
    private class ActionActivator implements KeyListener, ItemListener {


        @Override
        public void itemStateChanged(ItemEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}