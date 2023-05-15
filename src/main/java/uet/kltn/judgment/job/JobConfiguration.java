package uet.kltn.judgment.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uet.kltn.judgment.config.AppProperties;

@Configuration
public class JobConfiguration {
    @Autowired
    private AppProperties appProperties;

    @Bean
    public JobDetail printCrawlJudgmentsJobDetail() {
        return JobBuilder.newJob(CrawlJudgment.class)
                .withIdentity(JobKey.jobKey("crawl-judgment"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger printCrawlJudgmentsTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(printCrawlJudgmentsJobDetail())
                .withIdentity("crawl-judgment")
                .withPriority(1)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 1))
                .build();
    }

}
