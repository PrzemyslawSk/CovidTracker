package io.java.coronavirustracker.controllers;

import io.java.coronavirustracker.models.LocationDeathStats;
import io.java.coronavirustracker.models.LocationInfectedStats;
import io.java.coronavirustracker.models.LocationRecoveredStats;
import io.java.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationInfectedStats> allInfectedStats = coronavirusDataService.getInfectedStats();
        int totalReportedCases = allInfectedStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewCases = allInfectedStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        List<LocationDeathStats> allDeathStats = coronavirusDataService.getDeathStats();
        int totalReportedDeaths = allDeathStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewDeaths = allDeathStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        model.addAttribute("totalNewDeaths", totalNewDeaths);

        List<LocationRecoveredStats> allRecoveredStats = coronavirusDataService.getRecoveredStats();
        int totalReportedRecovered = allRecoveredStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewRecovered = allRecoveredStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("totalReportedRecovered", totalReportedRecovered);
        model.addAttribute("totalNewRecovered", totalNewRecovered);

        return "home";
    }
}
