package kc.phishingtest.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import kc.phishingtest.service.UniqueVisitsService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UniqueVisitsEntityCreatorScheduler {

    @Value("${schedule.unique-visits-entity-creator.enabled}")
    private boolean isLdapSchedulerEnabled;

    private final UniqueVisitsService uniqueVisitsService;

    @Scheduled(cron = "${schedule.unique-visits-entity-creator.cron}")
    public void scheduled() {
        if (isLdapSchedulerEnabled) {
            log.info("Missing days for unique visits counter creation started");
            uniqueVisitsService.createEntitiesForNext7Days();
            log.info("Missing days for unique visits counter creation finished");

        } else {
            log.info("Missing days for unique visits counter scheduler disabled");
        }
    }
}
