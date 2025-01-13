package md.hajji.springbatchex.configurations;


import jakarta.persistence.EntityManagerFactory;
import md.hajji.springbatchex.models.Sale;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.persistence.StepExecution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {


    @Bean
    public FlatFileItemReader<Sale> reader(LineMapper<Sale> lineMapper){
        return new FlatFileItemReaderBuilder<Sale>()
                .name("sales-reader")
                .resource(new ClassPathResource("sales.csv"))
                .lineMapper(lineMapper)
                .linesToSkip(1)
                .build();
    }


    @Bean
    public ItemProcessor<Sale, Sale> processor(){
        return new FunctionItemProcessor<>(Sale::computeTotalPrice);
    }

    @Bean
    public ItemProcessor<Sale, Sale> totalPricePerCategory(StepExecution stepExecution){
        return new FunctionItemProcessor<>(Sale::computeTotalPrice);
    }




    @Bean
    public JpaItemWriter<Sale> writer(EntityManagerFactory emf) {
        return  new JpaItemWriterBuilder<Sale>()
                .entityManagerFactory(emf)
                .build();
    }



    @Bean
    public Step totalPriceStep(
            ItemReader<Sale> reader,
            ItemProcessor<Sale, Sale> processor,
            ItemWriter<Sale> writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ){
        return new StepBuilder("total-price-step", jobRepository)
                .<Sale, Sale>chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job saleJob(
            JobRepository jobRepository,
            Step totalPriceStep
    ){
        return new JobBuilder("total-Price-Job", jobRepository)
                .start(totalPriceStep)
                .build();
    }


    @Bean
    public JobParameters jobParameters(){
        return new JobParametersBuilder()
                .addLong("startTime", System.currentTimeMillis())
                .toJobParameters();
    }


}
