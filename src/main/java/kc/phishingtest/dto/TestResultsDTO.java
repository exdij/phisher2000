package kc.phishingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class TestResultsDTO {

    private List<UniqueVisitDTO> visitsPerDay;

    private Long sum;
}
