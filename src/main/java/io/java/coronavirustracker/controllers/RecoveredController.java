package io.java.coronavirustracker.controllers;

import io.java.coronavirustracker.models.LocationRecoveredStats;
import io.java.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecoveredController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/recovered")
    public String recovered(Model model) {
        List<LocationRecoveredStats> allRecoveredStats = coronavirusDataService.getRecoveredStats();
        int totalReportedRecovered = allRecoveredStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewRecovered = allRecoveredStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("locationStats", coronavirusDataService.getRecoveredStats());
        model.addAttribute("totalReportedRecovered", totalReportedRecovered);
        model.addAttribute("totalNewRecovered", totalNewRecovered);

        return "recovered";
    }
}
