package com.nakji.myapp.trend.repo;

import com.nakji.myapp.trend.model.TrendGoogle;
import com.nakji.myapp.trend.model.TrendGoogleDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrendGoogleRepository extends JpaRepository<TrendGoogle, Long> {
    List<TrendGoogleDto> findAllByCollectedDate(LocalDate collectedDate);
    List<TrendGoogleDto> findAllByCollectedDateBetweenOrderByCollectedDateAsc(LocalDate start, LocalDate end);
}
