package kc.phishingtest.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import kc.phishingtest.service.UniqueVisitsService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartupUtils {

    private final UniqueVisitsService uniqueVisitsService;

    @PostConstruct
    public void onStartUp() {
        log.info("Started up, creating entities for the next 7 days");
        uniqueVisitsService.createEntitiesForNext7Days();
    }
}
