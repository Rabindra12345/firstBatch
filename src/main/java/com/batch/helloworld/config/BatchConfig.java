package com.batch.helloworld.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.Ste/pBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Bean
    public Tasklet myTasklet() {
       return new Tasklet() {

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			System.out.println("HELLLOOOOOOOOOOOOOOFUCKING HELL LETS GET RUN");
			return RepeatStatus.FINISHED;
		}
    	   
       };
    }

    @Bean
    public Step myStep(JobRepository jobRepository, Tasklet myTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep", jobRepository)
                .tasklet(myTasklet, transactionManager) // or .chunk(chunkSize, transactionManager)
                .build();
    }
	
	@Bean
	public Job myJob() {
		return new JobBuilder("myfirstjob",jobRepository)
				.start(myStep(jobRepository, myTasklet(), transactionManager))
				.build();
	}
}
