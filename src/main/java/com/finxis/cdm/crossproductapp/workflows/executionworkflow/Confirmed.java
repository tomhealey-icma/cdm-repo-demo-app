package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.workflow.*;

import java.util.List;

public class Confirmed implements WorkflowStateMgr {

    private static Confirmed instance = new Confirmed();

    private Confirmed(){};

    public static Confirmed instance(){
        return instance;
    }

    @Override
    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs) {
        System.out.println("Trade Confirmed");
        ewf.setCurrentState(ContractFormed.instance());
        ewf.setWorkFlowStep(wfs);
    }
}
