package com.finxis.cdm.crossproductapp;

import cdm.event.common.BusinessEvent;
import cdm.event.common.CollateralPortfolio;
import cdm.event.common.TradeState;
import cdm.event.common.metafields.ReferenceWithMetaCollateralPortfolio;
import cdm.product.template.EconomicTerms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finxis.cdm.crossproductapp.util.CdmTradeElements;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;

public class TradeTableModel extends AbstractTableModel {

    private final static int COLLATERAL_DESCRIPTION = 0;
    private final static int SIDE = 1;
    private final static int START_DATE = 2;
    private final static int PURCHASE_PRICE = 3;
    private final static int REPO_RATE = 4;

    private final HashMap<Integer, Trade> rowToTrade;
    private final HashMap<String, Integer> idToRow;
    private final HashMap<String, Trade> idToTrade;

    private final String[] headers;

    public TradeTableModel() {
        rowToTrade = new HashMap<>();
        idToRow = new HashMap<>();
        idToTrade = new HashMap<>();

        headers = new String[]
                  {"Collateral", "Direction", "Start Date", "Purchase Price", "Repo Rate"};
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void addTrade(String businessEventStr) throws JsonProcessingException {

        CdmTradeElements cdmTradeElements = new CdmTradeElements();

        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        BusinessEvent businessEvent = new BusinessEvent.BusinessEventBuilderImpl();
        businessEvent = rosettaObjectMapper.readValue(businessEventStr, businessEvent.getClass());

        cdm.event.common.Trade cdmTrade = businessEvent.getAfter().get(0).getTrade();

        String purchasePrice = cdmTradeElements.getPurchasePrice(businessEvent);
        String repurchasePrice = cdmTradeElements.getRepurchasePrice(businessEvent);
        String repoInterest = cdmTradeElements.getRepoInterest(businessEvent);
        String repoRate = cdmTradeElements.getRepoRate(businessEvent);

        EconomicTerms ecomonicTerms = cdmTrade.getTradableProduct().getProduct().getContractualProduct().getEconomicTerms();
        String tradeIdentifier = cdmTrade.getTradeIdentifier().get(0).getAssignedIdentifier().get(0).getIdentifier().getValue().toString();
        String startDate = ecomonicTerms.getEffectiveDate().getAdjustableDate().getUnadjustedDate().toString();
        String collateralId = ecomonicTerms.getCollateral().getCollateralPortfolio().get(0).getValue().getCollateralPosition().get(0).getProduct().getSecurity().getProductIdentifier().get(0).getValue().getIdentifier().getValue().toString();

        Trade trade = new Trade();
        trade.setPurchasePrice(purchasePrice);
        trade.setRepurchasePrice(repurchasePrice);
        trade.setStartDate(startDate);
        CollateralDescription collateralDescription = new CollateralDescription(collateralId);
        trade.setRepoRate(repoRate);

        trade.setCollateralDescription(collateralDescription);


        int row = rowToTrade.size();

        rowToTrade.put(row, trade);
        idToRow.put(tradeIdentifier, row);
        idToTrade.put(tradeIdentifier, trade);

        fireTableRowsInserted(row, row);
    }

    public void updateTrade(Trade trade, String id) {

        if (!id.equals(trade.getID())) {
            String originalID = trade.getID();
            trade.setID(id);
            replaceTrade(trade, originalID);
            return;
        }

        Integer row = idToRow.get(trade.getID());
        if (row == null)
            return;
        fireTableRowsUpdated(row, row);
    }

    public void replaceTrade(Trade trade, String originalID) {

        Integer row = idToRow.get(originalID);
        if (row == null)
            return;

        rowToTrade.put(row, trade);
        idToRow.put(trade.getID(), row);
        idToTrade.put(trade.getID(), trade);

        fireTableRowsUpdated(row, row);
    }

    public void addID(Trade trade, String newID) {
        idToTrade.put(newID, trade);
    }

    public Trade getTrade(String id) {
        return idToTrade.get(id);
    }

    public Trade getTrade(int row) {
        return rowToTrade.get(row);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) { }

    public Class<String> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getRowCount() {
        return rowToTrade.size();
    }

    public int getColumnCount() {
        return headers.length;
    }

    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Trade trade = rowToTrade.get(rowIndex);
        switch (columnIndex) {
        case COLLATERAL_DESCRIPTION:
            return trade.getCollateralDescription().getName().toString();
        case PURCHASE_PRICE:
                return trade.getPurchasePrice();
        case START_DATE:
                return trade.getStartDate();
        case SIDE:
            return trade.getSide();
        case REPO_RATE:
            return trade.getRepoRate();
        }
        return "";
    }
}
