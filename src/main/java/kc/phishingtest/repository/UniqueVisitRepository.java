package kc.phishingtest.repository;

import kc.phishingtest.entity.UniqueVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface UniqueVisitRepository extends JpaRepository<UniqueVisit, Long> {

    boolean existsByDate(LocalDate date);

    @Modifying
    @Query("update UniqueVisit u set u.uniqueVisitsCount = u.uniqueVisitsCount + 1 where u.date = :date")
    void incrementCounterForDay(LocalDate date);

    @Query("select sum(u.uniqueVisitsCount) from UniqueVisit u")
    Long getSumFromAllDays();
}
