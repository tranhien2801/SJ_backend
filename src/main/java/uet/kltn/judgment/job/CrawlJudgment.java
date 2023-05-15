package uet.kltn.judgment.job;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;
import uet.kltn.judgment.dto.request.crawler.DateCrawl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CrawlJudgment extends QuartzJobBean {
    public static Logger logger = LoggerFactory.getLogger(CrawlJudgment.class);

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-dd-MM");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try{
            logger.info("Job ** {} ** starting at {}", context.getJobDetail().getKey().getName(), context.getFireTime());
            String uri = "http://localhost:5000/crawler";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            LocalDateTime date = LocalDateTime.now();
            date = date.minusDays(1);
            System.out.println(date.format(CUSTOM_FORMATTER));
//            JSONObject object = new JSONObject();
//            object.put("date_from", date.format(CUSTOM_FORMATTER));
//            object.put("date_to", date.format(CUSTOM_FORMATTER));
            DateCrawl dateCrawl = new DateCrawl(date.format(CUSTOM_FORMATTER), date.format(CUSTOM_FORMATTER));
            HttpEntity<DateCrawl> request = new HttpEntity<>(dateCrawl, headers);
            System.out.println(request.getBody().toString());
            ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                System.out.println("Response: " + responseBody);
            } else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
            logger.info("Jobs. {}", response);
            logger.info("Completed.  Next job scheduled @ {}", context.getNextFireTime());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
