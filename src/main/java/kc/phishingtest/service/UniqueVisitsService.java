package kc.phishingtest.service;

import kc.phishingtest.dto.TestResultsDTO;
import kc.phishingtest.dto.UniqueVisitDTO;
import kc.phishingtest.entity.UniqueVisit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kc.phishingtest.repository.UniqueVisitRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UniqueVisitsService {

    private final UniqueVisitRepository uniqueVisitRepository;

    @Value("${secret.key}")
    private String secretKey;


    public void createEntitiesForNext7Days() {
        LocalDate dateToCheck = LocalDate.now();
        for (int i = 1; i < 7; i++) {
            if(!uniqueVisitRepository.existsByDate(dateToCheck)) {
                createNewEntity(dateToCheck);
            }
            dateToCheck = dateToCheck.plusDays(1);
        }
    }

    private UniqueVisit createNewEntity(LocalDate date) {
        UniqueVisit uniqueVisit = new UniqueVisit(date);
        return uniqueVisitRepository.save(uniqueVisit);
    }

    public String incrementCounterIfUniqueForDate(boolean alreadyVisited, HttpServletResponse response) {
        if(!alreadyVisited) {
            Cookie cookie = new Cookie("visited", "true");
            cookie.setMaxAge(30 * 24 * 60 * 60); // expires in 30 days
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            LocalDate now = LocalDate.now();
            uniqueVisitRepository.incrementCounterForDay(now);
        }

        return getWebPageContent();
    }

    public TestResultsDTO getTestResults(String key) {
        if(key == null || !key.equals(secretKey)) {
            return null;
        }

        List<UniqueVisitDTO> list = uniqueVisitRepository.findAll().stream()
                .map(UniqueVisitDTO::new)
                .collect(Collectors.toList());
        Long uniqueVisitsSum = getAllDaysSum();

        return new TestResultsDTO(list, uniqueVisitsSum);
    }

    private Long getAllDaysSum() {
        return uniqueVisitRepository.getSumFromAllDays();

    }

    public void resetDatabase(String key) {
        if(key == null || !key.equals(secretKey)) {
            return;
        }

        uniqueVisitRepository.deleteAll();
        createEntitiesForNext7Days();
    }

    private String getWebPageContent() {
        String content = "";
        try {
            InputStream templateInputStream = new ClassPathResource("/templates/LandingPage.html").getInputStream();
            byte[] templateAsBytes = IOUtils.toByteArray(templateInputStream);
            content = new String(templateAsBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return content;
    }
}
