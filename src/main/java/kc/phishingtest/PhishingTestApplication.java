package kc.phishingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class PhishingTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingTestApplication.class, args);
    }

}
