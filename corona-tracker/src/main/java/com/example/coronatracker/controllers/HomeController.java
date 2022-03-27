package com.example.coronatracker.controllers;

import com.example.coronatracker.services.CoronaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronaDataService coronaDataService = new CoronaDataService();
    
    @GetMapping("/")
    public String coronaGlobal(Model model){
        model.addAttribute("locationStats", coronaDataService.getAllStats());
        model.addAttribute("applicationName", "Corona Tracker Application");
        return "global_stats";
    }

    @GetMapping("/us-stats")
    public String coronaUS(Model model){
        model.addAttribute("usStats", coronaDataService.getAllStatsUS());
        model.addAttribute("applicationName", "Corona Tracker Application");
        return "us_stats";
    }
}
