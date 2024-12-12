package com.nakji.myapp.trend.service;

import com.nakji.myapp.common.util.NakjiUtil;
import com.nakji.myapp.trend.model.TrendGoogleDto;
import com.nakji.myapp.trend.repo.TrendGoogleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendGoogleService {
    private final TrendGoogleRepository trendGoogleRepository;

    public List<TrendGoogleDto> getTrendGoogleByCollectedDate(String date) {
        LocalDate formattedDate = NakjiUtil.formatDateString(date);
        return trendGoogleRepository.findAllByCollectedDate(formattedDate);
    }

    public List<TrendGoogleDto> getTrendGoogleByCollectedDateBetween(String start, String end) {
        LocalDate formattedStartDate = NakjiUtil.formatDateString(start);
        LocalDate formattedEndDate = NakjiUtil.formatDateString(end);
        return trendGoogleRepository.findAllByCollectedDateBetweenOrderByCollectedDateAsc(formattedStartDate, formattedEndDate);
    }
}
