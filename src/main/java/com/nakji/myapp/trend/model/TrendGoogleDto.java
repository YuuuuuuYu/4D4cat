package com.nakji.myapp.trend.model;

import java.time.LocalDate;

public record TrendGoogleDto(LocalDate collectedDate, String keywordName, String imgUrl, String articleUrl, int rank) {
}
