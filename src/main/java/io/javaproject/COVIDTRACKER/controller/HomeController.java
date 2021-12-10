package io.javaproject.COVIDTRACKER.controller;

import io.javaproject.COVIDTRACKER.models.LocationStats;
import io.javaproject.COVIDTRACKER.services.CoronaVirusDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// this is what is accesed when we call home url
@Controller
public class HomeController {
    @Autowired
    CoronaVirusDataServices coronaVirusService;
// all of this class method retun rest responses in the term of JSON
    // but we dont want that , we want UI in the terms of html
    @GetMapping("/")

    // Spring is giving me object and telling put this object what ever you weant
    // It will accessible when to HTML when you want
    public String home(Model model){
        // when you call the controller and fetch the data
        // you have cahnce to modify the data in here
        //---------------------------------------------------

        // we gona Autowire
        List<LocationStats> allStats = coronaVirusService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCase()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
