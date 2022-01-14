package kc.phishingtest.controller;

import kc.phishingtest.dto.TestResultsDTO;
import kc.phishingtest.service.UniqueVisitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LandingController {

    private final UniqueVisitsService uniqueVisitsService;

    @GetMapping("/security-info")
    public String incrementCounterAndReturnMessage(HttpServletResponse response,
                                                   @CookieValue(value = "visited", defaultValue = "false") boolean visited) {
        return uniqueVisitsService.incrementCounterIfUniqueForDate(visited, response);
    }

    @GetMapping("/results")
    public TestResultsDTO getTestResults(@RequestHeader(value = "key", required = false, defaultValue = " ") String key) {
        return uniqueVisitsService.getTestResults(key);
    }

    @DeleteMapping("/all")
    public void resetDatabase(@RequestHeader(value = "key", required = false, defaultValue = " ") String key) {
        uniqueVisitsService.resetDatabase(key);
    }
}
