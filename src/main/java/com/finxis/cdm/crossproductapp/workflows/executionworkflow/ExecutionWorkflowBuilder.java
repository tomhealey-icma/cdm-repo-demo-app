package com.finxis.cdm.crossproductapp.workflows.executionworkflow;

import cdm.event.common.EventIntentEnum;
import cdm.event.workflow.EventInstruction;
import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;

import java.util.List;

public class ExecutionWorkflowBuilder {

    public WorkflowStep executed;
    public WorkflowStep confirmed;
    public WorkflowStep formed;
    public WorkflowStep transferred;
    public WorkflowStep terminated;

    public Workflow executionWorkflowBuilder(){

        this.executed = WorkflowStep.builder()
                .setProposedEvent(EventInstruction.builder())
                .setPreviousWorkflowStepValue(null)
                .build();

        this.confirmed = WorkflowStep.builder()
                .setProposedEvent(EventInstruction.builder())
                .setPreviousWorkflowStepValue(executed)
                .build();

        this.formed = WorkflowStep.builder()
                .setProposedEvent(EventInstruction.builder()
                        .setIntent(EventIntentEnum.CONTRACT_FORMATION))
                .setPreviousWorkflowStepValue(confirmed)
                .build();

        this.transferred = WorkflowStep.builder()
                .setProposedEvent(EventInstruction.builder()
                        .setIntent(EventIntentEnum.CLEARING))
                .setPreviousWorkflowStepValue(formed)
                .build();

        this.terminated = WorkflowStep.builder()
                .setProposedEvent(EventInstruction.builder()
                        .setIntent(EventIntentEnum.REPURCHASE))
                .setPreviousWorkflowStepValue(transferred)
                .build();

        List<WorkflowStep> wfs = List.of(executed, confirmed, formed, transferred, terminated);

        Workflow wf = Workflow.builder()
                .addSteps(wfs);

        return wf;

    }


}
