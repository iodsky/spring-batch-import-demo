package com.demo.springbatchdemo;

import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${batch.employee.chunk-size:10}")
    private int chunkSize;

    @Value("${batch.employee.skip-limit:100}")
    private int skipLimit;

    @Bean
    public FlatFileItemReader<EmployeeDto> employeeCsvReader() {
        return new FlatFileItemReaderBuilder<EmployeeDto>()
                .linesToSkip(1)
                .name("csvItemReader")
                .resource(new ClassPathResource("data/employees.csv"))
                .delimited()
                .delimiter(",")
                .names(
                        "lastName", "firstName", "birthday", "address", "phoneNumber",
                        "sssNumber", "philhealthNumber", "tinNumber", "pagIbigNumber",
                        "status", "position", "supervisor", "startShift", "endShift",
                        "basicSalary", "mealAllowance", "phoneAllowance", "clothingAllowance",
                        "semiMonthlyRate", "hourlyRate"
                )
                .targetType(EmployeeDto.class)
                .build();
    }

    @Bean
    public ItemProcessor<EmployeeDto, Employee> employeeProcessor() {
        return EmployeeMapper::toEntity;
    }

    @Bean
    public JpaItemWriter<Employee> employeeWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Employee>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public SkipListener<EmployeeDto, Employee> skipListener() {
        return new SkipListener<EmployeeDto, Employee>() {

            @Override
            public void onSkipInProcess(EmployeeDto item, Throwable t) {
                log.warn("Skipped mapping employee: {} {}. Reason: {}",
                        item.getFirstName(), item.getLastName(), t.getMessage());
            }

            @Override
            public void onSkipInWrite(Employee item, Throwable t) {
               log.warn("Skipped persisting employee: {} {} (ID: {}). Reason: {}",
                       item.getFirstName(), item.getLastName(), item.getId(), t.getMessage());
            }

        };
    }

    @Bean
    public Step importEmployeeStep(ItemReader<EmployeeDto> reader,
                                   ItemProcessor<EmployeeDto, Employee> processor,
                                   JpaItemWriter<Employee> writer,
                                   JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager) {
        return new StepBuilder("csvImporterStep", jobRepository)
                .<EmployeeDto, Employee>chunk(chunkSize)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(DataIntegrityViolationException.class)
                .skip(ValidationException.class)
                .skipLimit(skipLimit)
                .retryLimit(3)
                .listener(skipListener())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job importEmployeeJob(Step csvImporterStep, JobRepository repository) {
        return new JobBuilder("csvImporterJob", repository)
                .incrementer(new RunIdIncrementer())
                .flow(csvImporterStep)
                .end()
                .build();
    }

}
