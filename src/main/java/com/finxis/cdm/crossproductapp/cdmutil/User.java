package com.finxis.cdm.crossproductapp.cdmutil;

import cdm.base.datetime.BusinessCenterEnum;
import cdm.base.datetime.BusinessCenters;
import cdm.base.datetime.metafields.FieldWithMetaBusinessCenterEnum;
import cdm.base.staticdata.party.Party;
import cdm.base.staticdata.party.PartyIdentifier;
import com.rosetta.model.metafields.FieldWithMetaString;

import java.util.List;

public class User {

    public Party userParty = null;
    public BusinessCenters userLocalBusinessCenter = null;
    public BusinessCenters userPartyBusinessCenter = null;

    public void createUserBusinessCenter(BusinessCenterEnum businessCenterEnum){

        this.userLocalBusinessCenter = BusinessCenters.builder()
                .setBusinessCenter(List.of(FieldWithMetaBusinessCenterEnum.builder()
                        .setValue(businessCenterEnum)))
                .build();

    }

    public void createUserParty(String lei){

        userParty = Party.builder()
                .setPartyId(List.of(PartyIdentifier.builder()
                                .setIdentifier(FieldWithMetaString.builder()
                                                .setValue(lei))))
                        .build();
    }

    public void createUserPartyBusinessCenter(BusinessCenterEnum businessCenterEnum){

        this.userPartyBusinessCenter = BusinessCenters.builder()
                .setBusinessCenter(List.of(FieldWithMetaBusinessCenterEnum.builder()
                        .setValue(businessCenterEnum)))
                .build();

    }
}
