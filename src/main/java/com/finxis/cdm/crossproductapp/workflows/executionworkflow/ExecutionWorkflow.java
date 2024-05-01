package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.base.staticdata.identifier.Identifier;
import cdm.event.workflow.WorkflowState;
import cdm.event.workflow.WorkflowStep;

public class ExecutionWorkflow  {

    private WorkflowStateMgr currentState;
    private String wfIdentifier;

    private WorkflowStep wfstep;

    public ExecutionWorkflow(WorkflowStateMgr currentState, WorkflowStep wfstep){

        super();
        this.currentState = currentState;
        this.wfstep = wfstep;


        if(currentState == null) {
            this.currentState = Executed.instance();
            this.wfstep = wfstep;
        }
    }

    public WorkflowStateMgr getCurrentState(){
        return currentState;
    }

    public void setCurrentState(WorkflowStateMgr currentState){
        this.currentState = currentState;
    }

    public void setWorkFlowStep(WorkflowStep wfs){
        this.wfstep = wfs;
    }

    public void nextStep(WorkflowStep wfs){
        currentState.updateState(this, wfs);
    }

}
