package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.workflow.WorkflowStep;

public class Settled implements WorkflowStateMgr {

    private static Settled instance = new Settled();

    private Settled (){};

    public static Settled instance(){
        return instance;
    }

    @Override
    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs) {
        System.out.println("Trade Settled");
        ewf.setCurrentState(Matured.instance());
        ewf.setWorkFlowStep(wfs);
    }
}
