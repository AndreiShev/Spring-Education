package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.services.impl.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    @GetMapping
    public void getStatisticCSV() {
        statisticService.createBookingCsv();
        statisticService.createAuthCsv();
    }
}
