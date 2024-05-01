package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.workflow.WorkflowStep;

public class Matured implements WorkflowStateMgr{

    private static Matured instance = new Matured();

    private Matured (){};

    public static Matured instance(){
        return instance;
    }

    @Override
    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs) {
        System.out.println("Trade Matured");
        ewf.setWorkFlowStep(wfs);
    }
}
