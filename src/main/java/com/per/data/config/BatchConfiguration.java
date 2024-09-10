package com.per.data.config;

import com.per.data.entity.PortFolioDetails;
import com.per.data.listner.JobCompletionNotificationListener;
import com.per.data.proessor.PortfolioDetailProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier(value = "h2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public FlatFileItemReader<PortFolioDetails> reader() {
        return new FlatFileItemReaderBuilder<PortFolioDetails>()
                .name("portfolioDetailsDataLoading")
                .resource(new ClassPathResource("portfolio-details-data.csv"))
                .delimited()
                .names("id", "portfolio")
                .targetType(PortFolioDetails.class)
                .build();
    }

    @Bean
    public PortfolioDetailProcessor processor() {
        return new PortfolioDetailProcessor();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "mySqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcBatchItemWriter<PortFolioDetails> writer(@Qualifier(value = "mySqlDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PortFolioDetails>()
                .sql("INSERT INTO ADVENTURE_WORKS.PORTFOLIO_DETAILS (id, portfolio) VALUES (:id, :portfolio)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step, JobCompletionNotificationListener listener) {
        return new JobBuilder("importPortfolioDetailsData", jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                     FlatFileItemReader<PortFolioDetails> reader, PortfolioDetailProcessor processor,
                     JdbcBatchItemWriter<PortFolioDetails> writer) {
        return new StepBuilder("step1", jobRepository)
                .<PortFolioDetails, PortFolioDetails>chunk(500, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
