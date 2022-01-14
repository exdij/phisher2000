package kc.phishingtest.dto;

import kc.phishingtest.entity.UniqueVisit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UniqueVisitDTO {

    private LocalDate date;

    private Long uniqueVisitsCount;

    public UniqueVisitDTO(UniqueVisit entity) {
        date = entity.getDate();
        uniqueVisitsCount = entity.getUniqueVisitsCount();
    }
}
