package com.nakji.myapp.trend.controller;

import com.nakji.myapp.trend.model.TrendGoogleDto;
import com.nakji.myapp.trend.service.TrendGoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trend")
public class TrendGoogleController {
    private final TrendGoogleService trendGoogleService;

    @GetMapping("/day")
    public List<TrendGoogleDto> selectTableByCollectedDate(@RequestParam(name="start") String start
                                                        , @RequestParam(name="end", required = false) String end) {

        return end == null ? trendGoogleService.getTrendGoogleByCollectedDate(start)
                : trendGoogleService.getTrendGoogleByCollectedDateBetween(start, end);
    }
}
