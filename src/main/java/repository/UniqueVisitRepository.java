package repository;

import entity.UniqueVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UniqueVisitRepository extends JpaRepository<UniqueVisit, Long> {

    boolean existsByDate(LocalDate date);

    void incrementCounterForDay(LocalDate date);
}
