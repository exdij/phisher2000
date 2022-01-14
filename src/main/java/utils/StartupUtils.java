package utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.UniqueVisitsService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class StartupUtils {

    private final UniqueVisitsService uniqueVisitsService;

    @PostConstruct
    public void onStartUp() {
        uniqueVisitsService.createEntitiesForNext7Days();
    }
}
