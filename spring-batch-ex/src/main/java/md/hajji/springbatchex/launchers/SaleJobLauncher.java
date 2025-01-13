package md.hajji.springbatchex.launchers;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job saleJob;
    private final JobParameters jobParameters;


    @Bean
    public CommandLineRunner launchSaleJob() {
        return args -> {
            jobLauncher.run(saleJob, jobParameters);
        };
    }
}
