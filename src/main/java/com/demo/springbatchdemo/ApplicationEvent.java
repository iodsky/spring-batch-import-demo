package com.demo.springbatchdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationEvent {

    private final JobOperator jobOperator;
    private final Job csvImporterJob;

    @EventListener(ApplicationEvent.class)
    public void applicationEvent() throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException {
        jobOperator.start(csvImporterJob, new JobParameters());
    }

}
