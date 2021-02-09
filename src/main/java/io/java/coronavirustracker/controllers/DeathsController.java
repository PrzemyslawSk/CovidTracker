package io.java.coronavirustracker.controllers;

import io.java.coronavirustracker.models.LocationDeathStats;
import io.java.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeathsController {


    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/deaths")
    public String deaths(Model model) {
        List<LocationDeathStats> allDeathStats = coronavirusDataService.getDeathStats();
        int totalReportedDeaths = allDeathStats.stream().mapToInt(stat -> stat.getLastTotalCases()).sum();
        int totalNewDeaths = allDeathStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("locationStats", coronavirusDataService.getDeathStats());
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        model.addAttribute("totalNewDeaths", totalNewDeaths);

        return "deaths";
    }
}
