package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.base.staticdata.identifier.Identifier;
import cdm.base.staticdata.party.Account;
import cdm.base.staticdata.party.Party;
import cdm.event.common.*;
import cdm.event.workflow.*;
import cdm.event.workflow.metafields.ReferenceWithMetaWorkflowStep;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.process.BuilderMerger;
import com.rosetta.model.metafields.MetaFields;

import java.util.List;

public class Executed implements WorkflowStateMgr{

    private static Executed instance = new Executed();

    private Executed(){}

    static Executed instance(){
        return instance;

    }

    @Override
    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs) {
        System.out.println("Trade Executed");
        ewf.setCurrentState(Confirmed.instance());
        ewf.setWorkFlowStep(wfs);
    }
}
