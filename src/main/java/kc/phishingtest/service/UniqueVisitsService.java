package kc.phishingtest.service;

import kc.phishingtest.entity.UniqueVisit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kc.phishingtest.repository.UniqueVisitRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class UniqueVisitsService {

    private final UniqueVisitRepository uniqueVisitRepository;

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
        if(alreadyVisited) {
            return "I've seen you before";
        } else {
            Cookie cookie = new Cookie("visited", "true");
            cookie.setMaxAge(30 * 24 * 60 * 60); // expires in 30 days
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            LocalDate now = LocalDate.now();
            uniqueVisitRepository.incrementCounterForDay(now);
            return "I've never seen this man in my life :)";
        }
    }
}
