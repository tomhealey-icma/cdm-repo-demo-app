package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.workflow.WorkflowStep;

public interface WorkflowStateMgr {

    public void updateState(ExecutionWorkflow ewf, WorkflowStep wfs);
}
