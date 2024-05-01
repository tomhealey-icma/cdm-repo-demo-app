package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.workflow.WorkflowStep;

public class ContractFormed implements WorkflowStateMgr {

    private static ContractFormed instance = new ContractFormed();

    private ContractFormed(){};

    public static ContractFormed instance(){
        return instance;
    }

    @Override
    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs) {
        System.out.println("Contract Formed");
        ewf.setCurrentState(Settled.instance());
        ewf.setWorkFlowStep(wfs);
    }
}
