package io.java.coronavirustracker.controllers;

import io.java.coronavirustracker.models.LocationInfectedStats;
import io.java.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InfectedController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/infected")
    public String infected(Model model) {
        List<LocationInfectedStats> allStats = coronavirusDataService.getInfectedStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("locationStats", coronavirusDataService.getInfectedStats());
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "infected";
    }
}
