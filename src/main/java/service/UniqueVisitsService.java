package service;

import entity.UniqueVisit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UniqueVisitRepository;

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

    public void incrementCounterIfUniqueForDate(boolean alreadyVisited) {
        if(!alreadyVisited) {
            LocalDate now = LocalDate.now();
            uniqueVisitRepository.incrementCounterForDay(now);
        }
    }
}
